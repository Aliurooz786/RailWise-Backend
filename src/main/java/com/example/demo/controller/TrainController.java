package com.example.demo.controller;

import com.example.demo.entity.Train;
import com.example.demo.services.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trains")
public class TrainController {

    @Autowired
    private TrainService trainService;

    @PostMapping("/add")
    public ResponseEntity<Train> addTrain(@RequestBody Train train) {
        return ResponseEntity.ok(trainService.addTrain(train));
    }

        @GetMapping("/all")
    public ResponseEntity<List<Train>> getAllTrains() {
        return ResponseEntity.ok(trainService.getAllTrains());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Train>> searchTrains(
            @RequestParam String from,
            @RequestParam String to) {
        return ResponseEntity.ok(trainService.searchTrains(from, to));
    }

    @DeleteMapping("/number/{trainNumber}")
    public ResponseEntity<String> deleteTrain(@PathVariable Long trainNumber) {
        boolean deleted = trainService.deleteTrainByTrainNumber(trainNumber);

        if (deleted) {
            return ResponseEntity.ok("Train deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Train not found");
        }
    }


    @PutMapping("/number/{trainNumber}")
    public ResponseEntity<?> updateTrainByNumber(@PathVariable Long trainNumber, @RequestBody Train updatedTrain) {
        try {
            Train train = trainService.updateTrainByNumber(trainNumber, updatedTrain);
            return ResponseEntity.ok(train);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
