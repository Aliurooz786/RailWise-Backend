package com.example.demo.repository;

import com.example.demo.entity.Train;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface TrainRepository extends MongoRepository<Train, String> {
    List<Train> findByFromStationAndToStation(String fromStation, String toStation);
    Optional<Train> findByTrainNumber(Long trainNumber);

}