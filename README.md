# Veterinary Management System
## Overview
- The Veterinary Management System is a RESTful API designed to manage various aspects of a veterinary practice, including doctors, customers, animals, available dates, and vaccines. The system provides endpoints to perform CRUD operations and manage relationships between entities.
## Technologies
- **Spring Boot:** A framework for creating stand-alone, production-grade Spring-based applications.
- **Spring Data JPA:** Provides data access with JPA and simplifies database interactions.
- **ModelMapper:** A Java library for object mapping, used to map between entities and DTOs.
- **Hibernate:** An ORM framework used for data persistence.
- **Jakarta Validation:** Provides validation annotations to ensure data integrity.
- **Lombok:** Library to reduce boilerplate code in Java.
## Project Setup
### Prerequisites
- Java 17
- PostgreSQL
- Maven
### Configuration

Configure your database and Hibernate settings in the **src/main/resources/application.properties** file:
#### Spring Boot Application Settings
```
spring.application.name=VeterinaryManagementSystem
```
#### PostgreSQL Database Settings
```
spring.datasource.url=jdbc:postgresql://localhost:5432/VeterinaryManagementSystem
spring.datasource.username=postgres
spring.datasource.password=yasarcan
```
#### Hibernate Settings
```
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.hibernate.show-sql=true
```
### Project Dependencies
The project uses Maven for dependency management. The **pom.xml** file includes the following dependencies:
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.modelmapper</groupId>
        <artifactId>modelmapper</artifactId>
        <version>3.2.0</version>
    </dependency>
</dependencies>
```
### Running the Application
**1. Build the Project**

Use Maven to build the project:
```
mvn clean install
```
**2. Run the Application**

Start the application using Maven:
```
mvn spring-boot:run
```
## UML Diagram
![VeterinaryManagementSystem_UML_Diagram.png](VeterinaryManagementSystem_UML_Diagram.png)
## API Endpoints
### Customer

| HTTP Method | Endpoint                          | Description                              | Request Body                | Response Body                |
|-------------|-----------------------------------|------------------------------------------|------------------------------|------------------------------|
| POST        | `/v1/customers`                    | Create a new customer                    | `CustomerSaveRequest`        | `CustomerResponse`           |
| GET         | `/v1/customers`                    | Get a paginated list of customers        | Query Parameters: `page`, `pageSize` | `CursorResponse<CustomerResponse>` |
| GET         | `/v1/customers/{id}`                | Get a customer by ID                     |                              | `CustomerResponse`           |
| GET         | `/v1/customers/filterByCustomerName` | Get customers by name                   | Query Parameter: `customerName` | `List<CustomerResponse>`     |
| GET         | `/v1/customers/allAnimalsOfCustomer/{customerId}` | Get all animals of a customer       |                              | `List<AnimalResponse>`       |
| PUT         | `/v1/customers`                    | Update an existing customer              | `CustomerUpdateRequest`      | `CustomerResponse`           |
| DELETE      | `/v1/customers/{id}`                | Delete a customer by ID                  |                              | `Result`                     |

### Animal

| HTTP Method | Endpoint                          | Description                              | Request Body                | Response Body                |
|-------------|-----------------------------------|------------------------------------------|------------------------------|------------------------------|
| POST        | `/v1/animals`                     | Create a new animal                      | `AnimalSaveRequest`          | `AnimalResponse`             |
| GET         | `/v1/animals`                     | Get a paginated list of animals          | Query Parameters: `page`, `pageSize` | `CursorResponse<AnimalResponse>` |
| GET         | `/v1/animals/{id}`                 | Get an animal by ID                      |                              | `AnimalResponse`             |
| GET         | `/v1/animals/filterByAnimalName`   | Get animals by name                      | Query Parameter: `animalName` | `List<AnimalResponse>`       |
| GET         | `/v1/animals/filterByCustomerId/{customerId}` | Get animals by customer ID             |                              | `List<AnimalResponse>`       |
| PUT         | `/v1/animals`                     | Update an existing animal                | `AnimalUpdateRequest`        | `AnimalResponse`             |
| DELETE      | `/v1/animals/{id}`                 | Delete an animal by ID                  |                              | `Result`                     |

### Vaccine

| HTTP Method | Endpoint                          | Description                              | Request Body                | Response Body                |
|-------------|-----------------------------------|------------------------------------------|------------------------------|------------------------------|
| POST        | `/v1/vaccines`                    | Create a new vaccine                     | `VaccineSaveRequest`         | `VaccineResponse`            |
| GET         | `/v1/vaccines`                    | Get a paginated list of vaccines         | Query Parameters: `page`, `pageSize` | `CursorResponse<VaccineResponse>` |
| GET         | `/v1/vaccines/{id}`                | Get a vaccine by ID                      |                              | `VaccineResponse`            |
| GET         | `/v1/vaccines/allVaccinesOfAnimal/{animalId}` | Get all vaccines of an animal         |                              | `List<VaccineResponse>`      |
| GET         | `/v1/vaccines/expiring`            | Get vaccines by protection end date range | Query Parameters: `startDate`, `endDate` | `List<VaccineResponse>`     |
| PUT         | `/v1/vaccines`                    | Update an existing vaccine               | `VaccineUpdateRequest`       | `VaccineResponse`            |
| DELETE      | `/v1/vaccines/{id}`                | Delete a vaccine by ID                   |                              | `Result`                     |

### Doctor

| HTTP Method | Endpoint                          | Description                              | Request Body                | Response Body                |
|-------------|-----------------------------------|------------------------------------------|------------------------------|------------------------------|
| POST        | `/v1/doctors`                     | Create a new doctor                      | `DoctorSaveRequest`          | `DoctorResponse`             |
| GET         | `/v1/doctors`                     | Get a paginated list of doctors          | Query Parameters: `page`, `pageSize` | `CursorResponse<DoctorResponse>` |
| GET         | `/v1/doctors/{id}`                 | Get a doctor by ID                      |                              | `DoctorResponse`             |
| PUT         | `/v1/doctors`                     | Update an existing doctor                | `DoctorUpdateRequest`        | `DoctorResponse`             |
| DELETE      | `/v1/doctors/{id}`                 | Delete a doctor by ID                    |                              | `Result`                     |

### AvailableDate

| HTTP Method | Endpoint                          | Description                              | Request Body                | Response Body                |
|-------------|-----------------------------------|------------------------------------------|------------------------------|------------------------------|
| POST        | `/v1/available_dates`             | Create a new available date              | `AvailableDateSaveRequest`   | `AvailableDateResponse`      |
| GET         | `/v1/available_dates`             | Get a paginated list of available dates  | Query Parameters: `page`, `pageSize` | `CursorResponse<AvailableDateResponse>` |
| GET         | `/v1/available_dates/{id}`         | Get an available date by ID              |                              | `AvailableDateResponse`      |
| GET         | `/v1/available_dates/filterByDoctorId/{doctorId}` | Get available dates by doctor ID       |                              | `List<AvailableDateResponse>` |
| PUT         | `/v1/available_dates`             | Update an existing available date        | `AvailableDateUpdateRequest` | `AvailableDateResponse`      |
| DELETE      | `/v1/available_dates/{id}`         | Delete an available date by ID           |                              | `Result`                     |

### Appointment

| HTTP Method | Endpoint                          | Description                              | Request Body                | Response Body                |
|-------------|-----------------------------------|------------------------------------------|------------------------------|------------------------------|
| POST        | `/v1/appointments`                | Create a new appointment                | `AppointmentSaveRequest`     | `AppointmentResponse`       |
| GET         | `/v1/appointments`                | Get a paginated list of appointments    | Query Parameters: `page`, `pageSize` | `CursorResponse<AppointmentResponse>` |
| GET         | `/v1/appointments/{id}`            | Get an appointment by ID                |                              | `AppointmentResponse`       |
| PUT         | `/v1/appointments`                | Update an existing appointment          | `AppointmentUpdateRequest`   | `AppointmentResponse`       |
| DELETE      | `/v1/appointments/{id}`            | Delete an appointment by ID             |                              | `Result`                     |
## Code Structure
- **api Package:** Contains REST controllers for handling HTTP requests.
- **business Package:** Contains service interfaces and their implementations.
- **core Package:** Contains utility classes, result wrappers, and configuration.
- **dto Package:** Contains data transfer objects for request and response payloads.
- **entity Package:** Contains JPA entity classes representing database tables.
- **dao Package**: Contains data access objects (DAOs) for interacting with the database.