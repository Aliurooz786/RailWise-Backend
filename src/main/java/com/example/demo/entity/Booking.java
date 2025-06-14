package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "bookings")
public class Booking {

    @Id
    private String id;

    @Indexed(unique = true)
    private String pnr;

    private String userId;
    private String trainId;

    private LocalDate bookingDate;
    private LocalDateTime bookingTime;

    private int seatNumber;
    private String status;     // CONFIRMED or CANCELLED
    private String classType;  // e.g., Sleeper, AC
    private double fare;
}