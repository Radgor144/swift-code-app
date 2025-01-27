# SwiftCodeAPI

## Table of Contents

- [General info](#Generalinfo)
- [Requirements](#Requirements)
- [Technologies](#Technologies)

## General info <a name = "Generalinfo"></a>

This is a <a href="https://spring.io/projects/spring-boot" target="blank"> Spring Boot</a> application designed to manage and expose <a href="https://www.remitly.com/us/en/swift-codes">SWIFT</a> code data through a REST API. The application enables users to:

- Retrieve detailed information about specific SWIFT codes (headquarters or branches).
- Fetch all SWIFT codes for a specific country using the ISO-2 country code.
- Add new SWIFT code entries to the database.
- Delete existing SWIFT codes if matching details are provided.

The application processes data from a provided file, parses it, and stores it in a database. It is containerized and ensures fast, low-latency querying for optimal performance.

## API Endpoints <a name="APIendpoints"></a>

| Method   | URL                                   | Description                                                                                  |
| -------- | ------------------------------------- | -------------------------------------------------------------------------------------------- |
| `GET`    | `/v1/swift-codes/{swift-code}`        | Retrieves details of a single SWIFT code (headquarter or branch).                            |
| `GET`    | `/v1/swift-codes/country/{countryISO2code}` | Retrieves all SWIFT codes (headquarters and branches) for a specific country by ISO-2 code.  |
| `POST`   | `/v1/swift-codes/`                    | Adds a new SWIFT code entry to the database.                                                 |
| `DELETE` | `/v1/swift-codes/{swift-code}`        | Deletes a SWIFT code entry if the details match those in the database.                       |


## Requirements <a name = "Requirements"></a>

- <a href="https://spring.io/projects/spring-boot" target="blank"> JDK 21 </a>
- <a href="https://maven.apache.org" target="blank"> Maven 3.x </a>
- <a href="https://www.docker.com"> Docker </a>

## How to run locally <a name = "How to run locally"></a>


## Technologies <a name = "Technologies"></a>

### Project Created with:

- **Java 21** - The core programming language for building the project.
- **Spring Boot** - Supports the rapid development of microservices-based applications and includes built-in features for configuration, dependency management, and application bootstrapping.
- **Docker** - Used to containerize the application and database for consistent deployment and environment setup.
- **PostgreSQL** - The relational database used to store and manage SWIFT code data.

### Documentation:
- **swagger** - Auto-generate API documentation for better interactivity, comprehension and testing of API features.

### Tests:
- **JUnit** - Unit testing was implemented to check the correctness of individual code fragments in isolation, enabling faster error detection and facilitating refactoring.
- **Mockito** - It allows you to simulate the behaviour of real objects in a controlled test environment, making it easier to test code in isolation and verify interactions between objects.
- **wiremock** - WireMock enables the creation of mock HTTP servers that can be used to test applications that communicate using the HTTP protocol. It is used to create dummy servers that can pretend to be real services. With WireMock, it is possible to create pre-defined responses to specific HTTP requests.

### Build tools:
- **Maven** - Tool for managing dependencies, building, and managing projects.

### Additional Features:
- **Cache** - Used to improve performance by storing JSON data with frequently accessed SWIFT code details.
- **Global Exception Handling** - Centralized mechanism for managing exceptions across the application, ensuring consistent and user-friendly error responses for:
    - Missing or invalid SWIFT codes.
    - Country codes not found in the database.
    - Duplicate SWIFT code entries.
    - Validation errors for incoming requests.
    - General runtime exceptions.