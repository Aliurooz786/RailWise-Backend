package com.example.demo.services.utility;

import com.example.demo.entity.Booking;
import com.example.demo.entity.Train;
import com.example.demo.entity.User;

public class BookingEmailBuilder {

    public static String buildSubject(String pnr) {
        return "RailWise | Ticket Booking Confirmation - PNR: " + pnr;
    }
    public static String buildCancellationSubject(String pnr) {
        return "RailWise | Ticket Cancellation Confirmation - PNR: " + pnr;
    }

    public static String buildBody(Booking booking, User user, Train train) {
        String fullName = user.getFirstName() + " " + user.getLastName();

        return String.format("""
                Dear %s,

                Your ticket has been successfully booked. Below are your booking details:

                PNR: %s
                Train: %s (%d)
                Travel Date: %s
                Departure: %s | Arrival: %s
                Seat Number: %s | Class: %s
                Passenger: %s
                From: %s → To: %s
                Fare: ₹%.2f
                Status: %s

                Thank you for choosing RailWise.
                We wish you a safe and pleasant journey!

                Regards,
                RailWise Team
                """,
                fullName,
                booking.getPnr(),
                train.getTrainName(), train.getTrainNumber(),
                booking.getBookingDate().toString(),
                train.getDepartureTime(), train.getArrivalTime(),
                booking.getSeatNumber(), booking.getClassType(),
                fullName,
                train.getFromStation(), train.getToStation(),
                booking.getFare(),
                booking.getStatus()
        );
    }


    public static String buildCancellationBody(Booking booking, User user, Train train) {
        String fullName = user.getFirstName() + " " + user.getLastName();

        return String.format("""
            Dear %s,

            Your ticket with the following details has been successfully cancelled:

            PNR: %s
            Train: %s (%d)
            Travel Date: %s
            Seat Number: %s | Class: %s
            From: %s → To: %s
           

            We're sorry to see you cancel your journey. Hope to serve you again!

            Regards,
            RailWise Team
            """,
                fullName,
                booking.getPnr(),
                train.getTrainName(), train.getTrainNumber(),
                booking.getBookingDate(),
                booking.getSeatNumber(), booking.getClassType(),
                train.getFromStation(), train.getToStation(),
                booking.getFare()
        );
    }
}