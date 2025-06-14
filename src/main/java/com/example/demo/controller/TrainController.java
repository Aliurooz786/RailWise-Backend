package com.example.demo.controller;

import com.example.demo.entity.Train;
import com.example.demo.services.TrainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/trains")
public class TrainController {

    @Autowired
    private TrainService trainService;

    @PostMapping("/add")
    public ResponseEntity<Train> addTrain(@RequestBody Train train) {
        log.info("Adding train: {}", train.getTrainName());
        Train savedTrain = trainService.addTrain(train);
        log.info("Train added: {}", savedTrain.getTrainNumber());
        return ResponseEntity.ok(savedTrain);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Train>> getAllTrains() {
        log.info("Fetching all trains");
        List<Train> trains = trainService.getAllTrains();
        log.info("Found {} trains", trains.size());
        return ResponseEntity.ok(trains);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Train>> searchTrains(@RequestParam String from, @RequestParam String to) {
        log.info("Searching trains from {} to {}", from, to);
        List<Train> trains = trainService.searchTrains(from, to);
        log.info("Found {} trains", trains.size());
        return ResponseEntity.ok(trains);
    }

    @DeleteMapping("/number/{trainNumber}")
    public ResponseEntity<String> deleteTrain(@PathVariable Long trainNumber) {
        log.info("Delete request for train number: {}", trainNumber);

        boolean deleted = trainService.deleteTrainByTrainNumber(trainNumber);

        if (!deleted) {
            log.warn("Train not found: {}", trainNumber);
            throw new RuntimeException("Train not found with number: " + trainNumber);
        }

        log.info("Train deleted: {}", trainNumber);
        return ResponseEntity.ok("Train deleted successfully");
    }

    @PutMapping("/number/{trainNumber}")
    public ResponseEntity<?> updateTrainByNumber(@PathVariable Long trainNumber, @RequestBody Train updatedTrain) {
        log.info("Update request for train number: {}", trainNumber);
        Train train = trainService.updateTrainByNumber(trainNumber, updatedTrain);

        log.info("Train updated: {}", train.getTrainNumber());
        return ResponseEntity.ok(train);
    }
}
