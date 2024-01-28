# GD Cosmetic Server

This is a project about company management

## Main Function

- Authentication, authorization, login, password recovery via email
- Manage departments and positions in the company 
- Manage projects and tasks
- Manage employees and accounts
- Manage employee salaries
- Timekeeping and payroll management
- Management of rewards and discipline
- Chat and send relevant notifications

## Getting Started

Instructions on how to install and run the project on your local machine.

## Requirements

- Java Development Kit (JDK)
- Maven
- MySQL

## Installation

**1. Clone repository:**

  ```bash
  https://github.com/duongphan10/gdcosmetic-server.git
  ```

**2. Configure the project:**  

  Open `src/main/resources/application.properties` and set up your database connection.
 
**3. Navigate to the project directory:**

  ```bash
  cd gdcosmetic-server
  ```
     
**4. Run the application:**

  ```bash
  mvn spring-boot:run
  ```
The application will be accessible at http://localhost:8080

The socket io server can connect at http://localhost:9090
