# Stock Exchange Application

This is a stock exchange application built using Spring Boot and Spring Security, following the hexagonal architecture
pattern.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

## Introduction

The Stock Exchange Application is designed to provide a platform for managing stocks and stock exchanges. It is built
with Spring Boot, Spring Security, and follows the hexagonal architecture pattern to ensure a clean separation of
concerns.

## Features

- CRUD operations for managing stocks and stock exchanges
- Authentication and authorization using Spring Security
- Hexagonal architecture for better maintainability and testability

## Installation

1. Clone the repository / Copy the project
2. Navigate to the project directory:
   cd stock-exchange-application
3. Build the project: `./gradlew build`
4. Run the application: `java -jar build/libs/stock-exchange-application.jar`

## Usage

Once the application is running, you can access the endpoints to manage stocks and stock exchanges.

## Testing

The application includes unit tests and integration tests to ensure its correctness. You can run the tests using the
following command:
`./gradlew test`

## Notes

- **Spring Security Integration Tests**: Due to the time it takes to run integration tests with Spring Security enabled,
  some tests have been temporarily disabled. These tests can be fixed in the future to include proper authentication and
  authorization checks.

- **Secure Storage of Credentials**: Currently, username and password credentials are stored in-memory for testing
  purposes. In a production environment, it's crucial to store these credentials securely, such as using encrypted
  databases or external authentication services.



