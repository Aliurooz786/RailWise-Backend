package com.example.demo.controller;

import com.example.demo.entity.Booking;
import com.example.demo.services.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        log.info("Booking creation requested for Train ID: {} by User ID: {}", booking.getTrainId(), booking.getUserId());
        try {
            Booking savedBooking = bookingService.createBooking(booking);
            log.info("Booking confirmed with PNR: {}", savedBooking.getPnr());
            return ResponseEntity.ok(savedBooking);
        } catch (RuntimeException e) {
            log.warn("Booking failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Booking failed: " + e.getMessage());
        } catch (Exception e) {
            log.error("Server error during booking: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @GetMapping("/pnr/{pnr}")
    public ResponseEntity<?> getBookingByPnr(@PathVariable String pnr) {
        log.info("Fetching booking with PNR: {}", pnr);
        Optional<Booking> booking = bookingService.getByPnr(pnr);
        if (booking.isPresent()) {
            return ResponseEntity.ok(booking.get());
        } else {
            log.warn("No booking found with PNR: {}", pnr);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Booking not found with PNR: " + pnr);
        }
    }

    @DeleteMapping("/cancel/{pnr}")
    public ResponseEntity<String> cancelBookingByPnr(@PathVariable String pnr) {
        log.info("Request to cancel booking with PNR: {}", pnr);
        boolean cancelled = bookingService.cancelByPnr(pnr);

        if (cancelled) {
            log.info("Booking cancelled with PNR: {}", pnr);
            return ResponseEntity.ok("Booking cancelled successfully with PNR: " + pnr);
        } else {
            log.warn("Booking not found to cancel with PNR: {}", pnr);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Booking not found with PNR: " + pnr);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getBookingsByUser(@PathVariable String userId) {
        log.info("Fetching bookings for User ID: {}", userId);
        List<Booking> bookings = bookingService.getBookingsByUser(userId);

        if (bookings.isEmpty()) {
            log.warn("No bookings found for User ID: {}", userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No bookings found for user ID: " + userId);
        }

        log.info("{} bookings found for user {}", bookings.size(), userId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/train/{trainId}")
    public ResponseEntity<List<Booking>> getByTrain(@PathVariable String trainId) {
        log.info("Fetching bookings for Train ID: {}", trainId);
        return ResponseEntity.ok(bookingService.getBookingsByTrain(trainId));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllBookings() {
        log.info("Fetching all bookings from the system");
        List<Booking> bookings = bookingService.getAllBookings();

        if (bookings.isEmpty()) {
            log.warn("No bookings found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No bookings found.");
        }

        log.info("{} bookings found in total", bookings.size());
        return ResponseEntity.ok(bookings);
    }
}