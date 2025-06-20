package com.example.demo.services;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.utility.BookingEmailBuilder;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;

    public void saveUser(User user){
        log.info("Saving user with email: {}", user.getEmail());
        userRepository.save(user);
    }

    @PostConstruct
    public void debugBean() {
        log.info("UserService initialized. Repo is null? {}", (userRepository == null));
    }

    public List<User> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    public User updateUser(String id, User updatedUser) {
        log.info("Updating user with id: {}", id);
        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(updatedUser.getFirstName());
                    user.setLastName(updatedUser.getLastName());
                    user.setEmail(updatedUser.getEmail());
                    user.setPassWord(updatedUser.getPassWord());
                    user.setAge(updatedUser.getAge());
                    user.setRole("ROLE_USER");  // Default role for new users
                    return userRepository.save(user);
                }).orElse(null);
    }

    public boolean deleteUser(String id) {
        log.info("Deleting user with id: {}", id);
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<User> getUserByEmailId(String email) {
        log.info("Finding user by email: {}", email);
        return userRepository.findByEmail(email);
    }

    public String registerUser(User user) {
        log.info("Registering user with email: {}", user.getEmail());

        if (!isValidPassword(user.getPassWord())) {
            return "Password must be at least 8 characters, include a number and special character.";
        }

        Optional<User> existing = userRepository.findByEmail(user.getEmail());
        if (existing.isPresent()) {
            log.warn("Email already registered: {}", user.getEmail());
            return "Email already registered!";
        }

        String rawPassword = user.getPassWord();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPassWord(encodedPassword);
        user.setRole("ROLE_USER");

        userRepository.save(user);

        log.info("Plain password: {}", rawPassword);
        log.info("Encoded password: {}", encodedPassword);
        log.info("User registered successfully: {}", user.getEmail());


        try {
            String subject = BookingEmailBuilder.buildWelcomeSubject(user.getFirstName());
            String body = BookingEmailBuilder.buildWelcomeBody(user);
            emailService.sendBookingConfirmation(user.getEmail(), subject, body);
            log.info("Welcome email sent to: {}", user.getEmail());
        } catch (Exception e) {
            log.error("Failed to send welcome email: {}", e.getMessage());
        }

        return "User registered successfully!";
    }

    public String registerAdmin(User user) {
        log.info("Registering admin: {}", user.getEmail());

        if (!isValidPassword(user.getPassWord())) {
            return "Password must be at least 8 characters, include a number and special character.";
        }

        Optional<User> existing = userRepository.findByEmail(user.getEmail());
        if (existing.isPresent()) {
            log.warn("Email already registered: {}", user.getEmail());
            return "Email already registered!";
        }

        user.setRole("ROLE_ADMIN");
        user.setPassWord(passwordEncoder.encode(user.getPassWord()));
        userRepository.save(user);
        log.info("Admin registered successfully: {}", user.getEmail());
        return "Admin registered successfully!";
    }

    public String loginUser(String email, String password) {
        log.info("Login attempt for email: {}", email);

        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            String encodedPassword = userOpt.get().getPassWord();
            if (passwordEncoder.matches(password, encodedPassword)) {
                log.info("Login successful for {}", email);
                return "Login successful!";
            }
        }

        log.warn("Invalid login attempt for email: {}", email);
        return "Invalid email or password!";
    }

    private boolean isValidPassword(String password) {
        String pattern = "^(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]{8,}$";
        return password.matches(pattern);
    }

    public User getUserById(String id) {
        log.info("Fetching user by id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }
}