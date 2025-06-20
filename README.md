# RailWise-Backend

**RailWise** is a full-featured backend system for an online train reservation platform. It is built using Spring Boot and MongoDB, and is designed with security, performance, and extensibility in mind.

The system supports user and admin roles, secure authentication using JWT, automatic seat assignment, email notifications, and daily scheduled tasks.

---

## Features

### User Module
- Register and login with validation
- Secure authentication using JWT
- Manage personal profile
- View personal bookings
- Receive confirmation and cancellation emails with PDF ticket attachment

### Admin Module
- Admin registration (admin-only)
- Manage train records (add, update, delete)
- View all users and their bookings

### Train Module
- Add and manage trains
- Search for trains by source and destination
- View train schedule, fare, and seat availability

### Booking Module
- Book tickets with automatic seat assignment
- Generate unique PNR and booking timestamps
- Cancel bookings by PNR
- View booking details by PNR, user, or train
- Send PDF tickets via email on booking
- Send cancellation confirmation via email

### Email and CRON Features
- SMTP email integration (Gmail)
- Send:
  - Booking confirmation emails with PDF tickets
  - Cancellation emails
  - Welcome emails on registration
- Scheduled task (CRON) to send a daily morning message to all users at 7:00 AM

---

## Security

- JWT-based authentication and authorization
- Role-based access control (User/Admin)
- BCrypt hashing for passwords
- Global exception handling for API errors
- Logging of all critical actions (booking, email, authentication, etc.)

---

## Technology Stack

| Layer         | Technology         |
|---------------|--------------------|
| Language      | Java 17            |
| Framework     | Spring Boot        |
| Database      | MongoDB            |
| Security      | Spring Security + JWT |
| Email         | Spring Mail (SMTP) |
| PDF           | OpenPDF            |
| Scheduling    | Spring Scheduler (CRON) |
| Logging       | SLF4J + Logback    |
| Build Tool    | Maven              |
| API Testing   | Postman / Swagger UI |

---

## Screenshots



