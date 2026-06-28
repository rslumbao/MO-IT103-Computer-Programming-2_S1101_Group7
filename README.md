# MotorPH Payroll System

Console-based payroll system written in Java that processes employee payroll data using CSV files.

The application reads employee and attendance records, computes total work hours, calculates gross salary and statutory deductions, and displays a payroll summary.

This project was developed by **Group 7** for the **MO-IT103 Computer Programming 2** course.

---

# Team Details

| Name                        | Role                                 |
| --------------------------- | -------------------------------      |
| **Roselyn lumbao**          | Project Manager, Developer           |
| **Marichu Morales**         | Developer, QA, Project Documentation |
| **Gideon Castillo**         | Developer, QA                        |

> Detailed contributions are available in the project documentation.

---

# Project Repository

**GitHub Repository**

https://github.com/rslumbao/MO-IT103-Computer-Programming-2_S1101_Group7

---

# Overview

The **MotorPH Payroll System** simulates a payroll processing workflow for MotorPH employees.

The application automates payroll computation by loading employee and attendance records from CSV files, calculating salaries, applying statutory deductions, and generating payroll summaries.

The system supports two user roles:

* Employee
* Payroll Staff

Payroll computations follow Philippine statutory deduction guidelines including:

* SSS
* PhilHealth
* Pag-IBIG
* Withholding Tax

The entire application runs in a console environment using Java.

---

# Features

## Authentication

Users must log in before accessing the system.

### Default Credentials

| Role          | Username        | Password |
| ------------- | --------------- | -------- |
| Employee      | `employee`      | `12345`  |
| Payroll Staff | `payroll_staff` | `12345`  |

---

## Employee Functions

Employees can:

* Log in securely
* Enter their Employee Number
* View personal information
* View payroll information

Employee information includes:

* Employee Number
* Full Name
* Birthday
* Position
* Basic Salary

---

## Payroll Staff Functions

Payroll staff can:

* Process payroll for an individual employee
* Process payroll for all employees
* View payroll summaries
* Calculate government deductions
* Generate payroll details

Payroll summaries include:

* Total Hours Worked
* Gross Salary
* SSS Deduction
* PhilHealth Deduction
* Pag-IBIG Deduction
* Withholding Tax
* Net Salary

---

# System Workflow

1. User logs in using valid credentials.
2. The system loads CSV files:

   * Employee Database
   * Attendance Records
3. User selects an operation depending on the assigned role.
4. Attendance records are processed.
5. Total work hours are calculated.
6. Gross salary is computed.
7. Government deductions are applied.
8. Payroll summary is displayed.

---

# Project Structure

```text
MO-IT103-Computer-Programming-2_S1101_Group7
│
├── src
│   ├── Main.java
│   ├── model/
│   ├── services/
│   ├── utilities/
│   └── resources/
│       ├── employee_database.csv
│       └── employee_attendance.csv
│
├── docs/
├── .gitignore
├── pom.xml
└── README.md
```

> *Note: Folder names may vary depending on the latest project structure.*

---

# CSV Data Files

## Employee Database

**employee_database.csv**

Contains employee information such as:

* Employee Number
* First Name
* Last Name
* Birthday
* Position
* Basic Salary
* Rice Subsidy
* Phone Allowance
* Clothing Allowance
* Hourly Rate

---

## Attendance Records

**employee_attendance.csv**

Contains employee attendance logs including:

* Employee Number
* Last Name
* First Name
* Date
* Time In
* Time Out

---

# Payroll Computation

## Work Hour Calculation

The system computes work hours using:

* Standard Work Schedule (8:00 AM – 5:00 PM)
* 10-minute grace period
* 1-hour lunch break deduction
* Late arrival adjustments

---

## Salary Computation

```
Gross Salary = Hours Worked × Hourly Rate
```

Payroll is computed in two cutoffs:

* **1st Cutoff:** Day 1 – 15
* **2nd Cutoff:** Day 16 – End of Month

---

# Government Deductions

The system calculates the following deductions according to Philippine payroll standards.

## SSS

Calculated using the official contribution table based on monthly salary.

## PhilHealth

* 3% of Monthly Salary
* Shared equally by employer and employee

## Pag-IBIG

* 1% for salary ≤ ₱1,500
* 2% for salary > ₱1,500
* Maximum employee contribution of ₱100

## Withholding Tax

Computed using progressive income tax brackets based on taxable income.

---

# Technologies Used

* Java
* Apache Commons CSV
* Java Collections Framework
* Java Time API
* Maven
* JUnit 5

### Dependencies

```
org.apache.commons:commons-csv
```

```
org.junit.jupiter
```

---

# How to Run the Program

## 1. Clone the Repository

```bash
git clone https://github.com/rslumbao/MO-IT103-Computer-Programming-2_S1101_Group7.git
```

---

## 2. Open the Project

Import the project into your preferred Java IDE:

* IntelliJ IDEA
* Eclipse
* NetBeans

---

## 3. Build the Project

If using Maven:

```bash
mvn clean install
```

Or compile manually:

```bash
javac Main.java
```

---

## 4. Run the Application

Execute the `Main.java` file and follow the on-screen prompts.

---

# Example Output

```text
===================================
        MotorPH Payroll System
===================================

Enter Your Credentials

Username:
Password:

Select an option

1. Process Payroll
2. Exit
```

### Sample Payroll Summary

```text
----------------------------------------
Cutoff 1: June 2024 (1–15)
----------------------------------------

Total Hours Worked : 80.00
Gross Salary       : ₱20,000.00

SSS                : ₱900.00
PhilHealth         : ₱300.00
Pag-IBIG           : ₱100.00
Withholding Tax    : ₱1,200.00

Net Salary         : ₱17,500.00
```

---

# Key Classes and Methods

| Method                          | Description                     |
| ------------------------------- | ------------------------------- |
| `employeeDataCSVReader()`       | Loads employee records from CSV |
| `employeeAttendanceCSVReader()` | Loads attendance records        |
| `calculateWorkHours()`          | Computes employee work hours    |
| `computeGrossSalary()`          | Calculates gross salary         |
| `computeSSS()`                  | Computes SSS deduction          |
| `computePhilHealth()`           | Computes PhilHealth deduction   |
| `computePagIbig()`              | Computes Pag-IBIG contribution  |
| `computeWithholdingTax()`       | Computes withholding tax        |

---

# Limitations

* User credentials are currently hardcoded.
* CSV files must exist in the resources directory.
* No database integration.
* Console-based interface only.
* No user account management.
* No PDF or Excel payroll export.

---

# Future Improvements

* JavaFX GUI
* MySQL Database Integration
* Employee Self-Service Portal
* Payroll Report Export (PDF/Excel)
* Role-Based Access Control
* Cloud Deployment
* REST API Integration

---

# License

This project was developed for educational purposes as part of the **MO-IT103 Computer Programming 2** course.

It may be used for academic reference and learning purposes only.
