# Metro Ticket Booking System

## 🔹 Task 1: Backend (Spring Boot)

Features:
- Book metro tickets between stations
- Price calculation using JSON configuration
- Ticket ID generation
- Entry and Exit validation (max 2 uses)
- Ticket expiry after 18 hours
- Simple UI included

Tech Stack:
- Java (Spring Boot)
- Spring Data JPA
- H2 Database

APIs:
- POST /ticket
- POST /ticket/entry/{id}
- POST /ticket/exit/{id}

How to Run:
1. mvn spring-boot:run
2. Open browser: http://localhost:8080

---

## 🔹 Task 2: Gmail Script

- Fetch last 200 emails
- Display sender and subject

How to Run:
1. cd gmail-task
2. python gmail_script.py

Note:
credentials.json and token.json are not included for security reasons.