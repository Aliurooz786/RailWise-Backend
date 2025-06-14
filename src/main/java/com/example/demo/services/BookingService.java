package com.example.demo.services;

import com.example.demo.entity.Booking;
import com.example.demo.repository.BookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TrainService trainService;

    public Booking createBooking(Booking booking) {
        log.info("Booking request received for userId: {}, trainId: {}", booking.getUserId(), booking.getTrainId());

        boolean available = trainService.isSeatAvailable(booking.getTrainId());

        if (!available) {
            log.warn("No seats available for trainId: {}", booking.getTrainId());
            throw new RuntimeException("No seats available for this train.");
        }

        int seat = trainService.assignNextSeat(booking.getTrainId());

        booking.setPnr(generatePnr());
        booking.setSeatNumber(seat);
        booking.setBookingTime(LocalDateTime.now());
        booking.setStatus("CONFIRMED");

        Booking saved = bookingRepository.save(booking);

        log.info("Booking confirmed | PNR: {}, Seat: {}, Class: {}, Fare: {}",
                saved.getPnr(), saved.getSeatNumber(), saved.getClassType(), saved.getFare());

        return saved;
    }

    public boolean cancelByPnr(String pnr) {
        log.info("Cancel request received for PNR: {}", pnr);

        Optional<Booking> bookingOpt = bookingRepository.findByPnr(pnr);

        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();

            trainService.releaseSeat(booking.getTrainId());
            bookingRepository.delete(booking);

            log.info("Booking cancelled successfully for PNR: {}", pnr);
            return true;
        }

        log.warn("No booking found with PNR: {}", pnr);
        return false;
    }

    public String generatePnr() {
        String prefix = String.valueOf(System.currentTimeMillis()).substring(4, 10); // 6-digit
        int suffix = new Random().nextInt(9000) + 1000; // 4-digit
        String pnr = prefix + suffix;
        log.info("Generated PNR: {}", pnr);
        return pnr;
    }

    public Optional<Booking> getByPnr(String pnr) {
        log.info("Fetching booking by PNR: {}", pnr);
        return bookingRepository.findByPnr(pnr);
    }

    public List<Booking> getBookingsByUser(String userId) {
        log.info("Fetching bookings for userId: {}", userId);
        return bookingRepository.findByUserId(userId);
    }

    public List<Booking> getBookingsByTrain(String trainId) {
        log.info("Fetching bookings for trainId: {}", trainId);
        return bookingRepository.findByTrainId(trainId);
    }

    public List<Booking> getAllBookings() {
        log.info("Fetching all bookings (admin)");
        return bookingRepository.findAll();
    }
}