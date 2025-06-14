package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "trains")
public class Train {

    @Id
    private String id;

    @Indexed(unique = true)
    private Long trainNumber;

    private String trainName;
    private String fromStation;
    private String toStation;
    private String departureTime;
    private String arrivalTime;

    private int availableSeats;
    private double fare;
    private int totalSeats;
    private int bookedSeats;
}