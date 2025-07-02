# Barangay Document Management System

A Java-based application designed to manage barangay documents across multiple barangays. This system integrates both **Command-Line Interface (CLI)** and **Graphical User Interface (GUI)** components to support different types of user interactions. It uses a **MySQL database** for persistent storage and is built with **Maven** for dependency and build management.

---

## 🚀 Features

- **Multi-Barangay Support**  
  Handle documents and users for multiple barangays within one centralized system.

- **User and Admin Login/Registration**  
  Secure authentication for both residents (users) and barangay staff (admins).

- **Document Request Workflow**  
  Users can submit requests for official barangay documents based on available types.

- **Admin Verification System**  
  Admins can review, approve, or reject requests with status tracking.

- **Automatic Document Generation**  
  Approved requests generate official documents populated with system data and user information.

- **Mixed CLI and GUI Interface**  
  Certain parts of the system use CLI (e.g., admin-level batch operations), while GUI components provide a user-friendly experience for residents and admin dashboards.

- **Payment Module (Under Development)**  
  A built-in module is in progress to allow users to process payment transactions within the system.

---

## 🛠️ Technologies Used

- **Programming Language:** Java  
- **Database:** MySQL  
- **Build Tool:** Maven  
- **User Interface:** CLI + GUI (Java Swing or JavaFX)  
- **Database Connector:** MySQL Connector/J
