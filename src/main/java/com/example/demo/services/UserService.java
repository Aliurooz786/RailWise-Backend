package com.example.demo.services;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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
        Optional<User> existing = userRepository.findByEmail(user.getEmail());

        if (existing.isPresent()) {
            log.warn("Email already registered: {}", user.getEmail());
            return "Email already registered!";
        }

        userRepository.save(user);
        log.info("User registered successfully: {}", user.getEmail());
        return "User registered successfully!";
    }

    public String loginUser(String email, String password) {
        log.info("Login attempt for email: {}", email);
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent() && userOpt.get().getPassWord().equals(password)) {
            log.info("Login successful for email: {}", email);
            return "Login successful!";
        }

        log.warn("Invalid login attempt for email: {}", email);
        return "Invalid email or password!";
    }
}