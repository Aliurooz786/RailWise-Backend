package com.example.demo.services;

import com.example.demo.entity.Train;
import com.example.demo.entity.User;
import com.example.demo.repository.TrainRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TrainService {

    @Autowired
    private TrainRepository trainRepository;

    public Train addTrain(Train train) {
        log.info("Adding new train: {}", train.getTrainName());
        return trainRepository.save(train);
    }

    public List<Train> getAllTrains() {
        log.info("Fetching all trains");
        return trainRepository.findAll();
    }

    public List<Train> searchTrains(String from, String to) {
        log.info("Searching trains from {} to {}", from, to);
        return trainRepository.findByFromStationAndToStation(from, to);
    }

    public boolean deleteTrainByTrainNumber(Long trainNumber) {
        log.info("Delete request for trainNumber: {}", trainNumber);

        Optional<Train> trainOpt = trainRepository.findByTrainNumber(trainNumber);
        if (trainOpt.isPresent()) {
            trainRepository.delete(trainOpt.get());
            log.info("Train deleted: {}", trainNumber);
            return true;
        } else {
            log.warn("Train not found for deletion: {}", trainNumber);
            return false;
        }
    }

    public Train updateTrainByNumber(Long trainNumber, Train updatedTrain) {
        log.info("Update request for trainNumber: {}", trainNumber);

        Optional<Train> trainOpt = trainRepository.findByTrainNumber(trainNumber);
        if (trainOpt.isEmpty()) {
            log.warn("Train not found: {}", trainNumber);
            throw new RuntimeException("Train not found with trainNumber: " + trainNumber);
        }

        Train existingTrain = trainOpt.get();

        Train saved = trainRepository.save(existingTrain);
        log.info("Train updated successfully: {}", saved.getTrainNumber());
        return saved;
    }

    public boolean isSeatAvailable(String trainId) {
        Optional<Train> trainOpt = trainRepository.findById(trainId);
        boolean available = trainOpt.map(train -> train.getBookedSeats() < train.getTotalSeats()).orElse(false);

        log.info("Seat availability for trainId {}: {}", trainId, available ? "Available" : "Full");
        return available;
    }

    public int assignNextSeat(String trainId) {
        Train train = trainRepository.findById(trainId).orElseThrow();
        int nextSeat = train.getBookedSeats() + 1;

        train.setBookedSeats(nextSeat);
        trainRepository.save(train);

        log.info("Seat assigned for trainId {}: Seat {}", trainId, nextSeat);
        return nextSeat;
    }

    public void releaseSeat(String trainId) {
        Train train = trainRepository.findById(trainId).orElseThrow();

        train.setBookedSeats(train.getBookedSeats() - 1);
        trainRepository.save(train);

        log.info("Seat released for trainId {}, Booked now: {}", trainId, train.getBookedSeats());
    }

    public Train getTrainById(String id) {
        log.info("Fetching train by id: {}", id);
        return trainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Train not found with ID: " + id));
    }
}