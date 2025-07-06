package com.example.demo.scheduler;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class DailyEmailScheduler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    // 🕖 Runs every day at 7:00 AM
    @Scheduled(cron = "0 0 7 * * ?")
    public void sendDailyEmails() {
        log.info("Starting daily email scheduler...");

        List<User> users = userRepository.findAll();

        for (User user : users) {
            try {
                String subject = "Good Morning, " + user.getFirstName() + "!";
                String body = String.format("""
                        Dear %s %s,

                        Wishing you a wonderful day ahead!

                        Thank you for being a part of RailWise.

                        If you have upcoming travel, make sure to check your bookings.

                        Regards,
                        The RailWise Team
                        """, user.getFirstName(), user.getLastName());

                emailService.sendBookingConfirmation(user.getEmail(), subject, body);
                log.info("Daily email sent to: {}", user.getEmail());
            } catch (Exception e) {
                log.error("Failed to send daily email to {}: {}", user.getEmail(), e.getMessage());
            }
        }
    }
}