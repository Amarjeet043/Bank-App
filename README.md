# 🏦 Banking System in Java (Console Application)

A complete **Banking System project built using Core Java**, demonstrating real-world banking operations without using a database.  


---

## 📌 Project Overview

This console-based Banking System simulates essential banking operations such as account creation, deposits, withdrawals, fund transfers, and transaction tracking.  
The project focuses on **clean architecture**, **object-oriented principles**, and **Java best practices**.

---

## ✨ Features

### 🔐 Account Management
- Open new bank accounts
- Close existing accounts
- Unique account number generation
- Customer-based account management

### 💰 Banking Operations
- Deposit money
- Withdraw money with balance validation
- Transfer funds between accounts
- Minimum balance checks

### 📜 Transactions & Statements
- Transaction history tracking
- Account statement generation
- Timestamped transactions using `LocalDateTime`

### 🔍 Search & Filter
- Search accounts by account number
- Filter transactions by type
- Customer-based account lookup

### ⚠️ Exception Handling
- Custom banking exceptions
- Meaningful error messages
- Business rule enforcement

### ✅ Data Validation
- Input validation for amounts
- Account existence checks
- Insufficient balance handling
- Safe console input handling

---

## 🧠 Concepts Applied

- Object-Oriented Programming (Encapsulation, Abstraction)
- Java Records (Modern Java)
- Collections Framework (`ArrayList`, `HashMap`)
- Custom Exception Handling
- Repository Pattern
- Service Layer Architecture
- Separation of Concerns
- Scanner for console input
- `LocalDateTime` for timestamps

---

## 🏗️ Project Structure

```plaintext
banking-system-java/
│
├── model/
│   ├── Account.java
│   └── Transaction.java
│
├── repository/
│   └── AccountRepository.java
│
├── service/
│   └── BankingService.java
│
├── exception/
│   ├── AccountNotFoundException.java
│   └── InsufficientBalanceException.java
│
├── util/
│   └── InputValidator.java
│
├── Main.java
└── README.md


---

## ▶️ How to Run the Project

### Prerequisites
- Java 17 or above
- Any IDE (IntelliJ IDEA / Eclipse / VS Code)

### Steps
```bash
git clone https://github.com/your-username/banking-system-java.git
cd banking-system-java
javac Main.java
java Main

