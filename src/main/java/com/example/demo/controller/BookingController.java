package com.example.demo.controller;

import com.example.demo.entity.Booking;
import com.example.demo.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        try {
            Booking savedBooking = bookingService.createBooking(booking);
            return ResponseEntity.ok(savedBooking);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Booking failed: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @GetMapping("/pnr/{pnr}")
    public ResponseEntity<?> getBookingByPnr(@PathVariable String pnr) {
        Optional<Booking> booking = bookingService.getByPnr(pnr);
        if (booking.isPresent()) {
            return ResponseEntity.ok(booking.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Booking not found with PNR: " + pnr);
        }
    }

    @DeleteMapping("/cancel/{pnr}")
    public ResponseEntity<String> cancelBookingByPnr(@PathVariable String pnr) {
        boolean cancelled = bookingService.cancelByPnr(pnr);

        if (cancelled) {
            return ResponseEntity.ok("Booking cancelled successfully with PNR: " + pnr);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Booking not found with PNR: " + pnr);
        }
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getBookingsByUser(@PathVariable String userId) {
        List<Booking> bookings = bookingService.getBookingsByUser(userId);

        if (bookings.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No bookings found for user ID: " + userId);
        }

        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/train/{trainId}")
    public ResponseEntity<List<Booking>> getByTrain(@PathVariable String trainId) {
        return ResponseEntity.ok(bookingService.getBookingsByTrain(trainId));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();

        if (bookings.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No bookings found.");
        }

        return ResponseEntity.ok(bookings);
    }


}