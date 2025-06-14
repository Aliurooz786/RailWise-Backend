package com.example.demo.repository;

import com.example.demo.entity.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends MongoRepository<Booking, String> {
    List<Booking> findByUserId(String userId);
    List<Booking> findByTrainId(String trainId);
    Optional<Booking> findByPnr(String pnr);
}