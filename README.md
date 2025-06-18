# RailWise-Backend
# 🚆 RailWise - Online Train Reservation System (Backend)

**RailWise** is a secure and feature-rich **online train reservation backend system** built with **Spring Boot** and **MongoDB**. It supports user and admin roles, JWT authentication, booking workflows, and role-based access control.

---

## ✨ Features

### 👤 User Module
- Register with validation (password policy enforced)
- Login with JWT authentication
- View/update/delete user profile
- View personal bookings

### 🔐 Admin Module
- Register admins (only accessible by admins)
- View all users
- Manage train data (Add, Update, Delete)
- View all bookings

### 🚄 Train Module
- Add new trains (admin only)
- Search trains by source and destination
- View all trains

### 🎟️ Booking Module
- Book ticket (with seat auto-assignment)
- Cancel booking by PNR
- View booking by:
    - PNR
    - Train ID
    - User ID
- View all bookings (admin only)

---

## 🔒 Security
- **JWT Authentication** for all protected endpoints
- **Role-based access** for user/admin features
- Passwords securely hashed with **BCrypt**
- Centralized **Global Exception Handling**
- Logging with **SLF4J**

---

## 🛠️ Tech Stack

| Layer        | Technology       |
|--------------|------------------|
| Backend      | Java 17 + Spring Boot |
| Database     | MongoDB          |
| Security     | Spring Security + JWT |
| Logging      | SLF4J + Logback  |
| Build Tool   | Maven      |
| Testing Tool | Postman / Swagger |

---

## 📂 Project Structure

```
com.urooz.railwise
├── controller          # REST Controllers (UserController, AdminController, etc.)
├── entity              # MongoDB document models (User, Train, Booking)
├── repository          # MongoDB repositories (UserRepository, TrainRepository, etc.)
├── service             # Business logic interfaces and implementations
├── services.serviceImpl# Service implementation classes
├── config              # Spring Security, MongoDB, and other config classes
├── filter              # JWT authentication filter
├── util                # Utility classes (e.g. JWT Util, Token Generator)
├── exception           # Custom exceptions and global exception handlers
└── DemoApplication.java # Main Spring Boot application runner
```

#ScreenShots


