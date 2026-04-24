# FinSafe Wallet

A secure, console-based Java banking application that simulates real-world wallet operations — including multi-user account management, authentication, deposits, withdrawals, and mini-statement generation.

---

## Overview

**FinSafe Wallet** is a lightweight Java console application that models the core functionalities of a digital wallet.
It supports **multiple users**, enforces secure password policies, tracks recent transactions, and uses clean separation of logic via a service layer.

---

## Project Structure

* FinSafeApp.java
* AccountService.java
* Account.java
* InSufficientFundsException.java

---

## Features

* Multi-user account system using HashMap
* Unique username enforcement
* Secure password validation
* Login/authentication system
* Deposit and withdrawal operations
* Custom exception for insufficient funds (InSufficientFundsException)
* Mini-statement showing last 5 transactions (CREDIT/DEBIT)
* Real-time balance tracking
* Clean separation of UI and business logic

---

## Getting Started

### 1. Compile all Java files

```bash
javac *.java
```

### 2. Run the application

```bash
java FinSafeApp
```

---

## Usage

Once running, the application presents a menu-driven interface:

```
FinSafe Wallet
1. Create Account
2. Login
3. Deposit
4. Withdraw
5. Mini Statement
6. Exit
Choice:
```

---

## Class Reference

### FinSafeApp

Handles user interaction and menu flow.

* createAccount() – Takes input and delegates account creation
* login() – Authenticates user via service layer
* deposit() – Deposits money into logged-in account
* withdraw() – Withdraws money with validation
* statement() – Displays mini statement
* start() – Runs the menu loop
* main(String[]) – Entry point

---

### AccountService

Handles business logic and manages multiple accounts.

* createAccount(String, double, String) – Creates account with unique username
* login(String, String) – Validates credentials and returns account

---

### Account

Represents a wallet account.

* deposit(double) – Adds money and records transaction
* processTransaction(double) – Withdraws money and checks balance
* printMiniStatement() – Shows last 5 transactions
* recordTransaction(double) – Maintains transaction history
* getAccountHolder() – Returns account holder name
* getBalance() – Returns current balance
* getPassword() – Returns password

---

### InSufficientFundsException

Custom exception thrown when withdrawal exceeds available balance.

---

## Exception Handling

* Withdrawal exceeds balance → InSufficientFundsException
* Invalid number input → handled using NumberFormatException
* Negative/zero amount → IllegalArgumentException
* Wrong login → shows error message

---

## Password Policy

Password must satisfy:

* Minimum 8 characters
* At least one uppercase letter
* At least one lowercase letter
* At least one digit
* At least one special character (@#$%^&+=!)

Example: Kiran@123

---


## Demo

### 1. Create Account

Enter your name, an opening balance, and a password that meets the security policy.

![Create Account](img/ss1.png)


### 2. Login

Authenticate using your registered name and password.

![Login](img/ss2.png)


### 3. Deposit

Add funds to your wallet after logging in.

![Deposit](img/ss3.png)


### 4. Withdraw

Withdraw funds. The application prevents overdrafts via `InSufficientFundsException`.

![Withdraw](img/ss4.png)


### 5. Mini Statement

View a summary of your last 5 transactions along with the current balance.

![Mini Statement](img/ss5.png)

