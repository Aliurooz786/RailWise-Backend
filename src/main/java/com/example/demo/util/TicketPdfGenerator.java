package com.example.demo.util;

import com.example.demo.entity.Booking;
import com.example.demo.entity.Train;
import com.example.demo.entity.User;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;

public class TicketPdfGenerator {

    public static byte[] generateTicketPdf(Booking booking, User user, Train train) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("RailWise Ticket Confirmation", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph("\nPNR: " + booking.getPnr()));
            document.add(new Paragraph("Passenger: " + user.getFirstName() + " " + user.getLastName()));
            document.add(new Paragraph("Train: " + train.getTrainName() + " (" + train.getTrainNumber() + ")"));
            document.add(new Paragraph("From: " + train.getFromStation() + " → To: " + train.getToStation()));
            document.add(new Paragraph("Class: " + booking.getClassType()));
            document.add(new Paragraph("Seat: " + booking.getSeatNumber()));
            document.add(new Paragraph("Date: " + booking.getBookingDate()));
            document.add(new Paragraph("Fare: ₹" + booking.getFare()));
            document.add(new Paragraph("\nStatus: " + booking.getStatus()));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }
}