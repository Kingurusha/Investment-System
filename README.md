<<<<<<< README.md
# Investment Management System

## Overview

The Investment Management System is a robust and scalable platform designed to manage investment portfolios, execute orders, and handle various market data. It is built using Java and Spring Boot, following a microservice architecture. The system leverages Hazelcast for distributed caching, interceptors for logging, and follows RESTful API principles in its controllers.

## Technologies Used

1. **Java/SpringBoot**
    - The core of the application is built using Java and Spring Boot, providing a strong foundation for building scalable and maintainable applications.

2. **Hazelcast**
    - We used Hazelcast for distributed caching to improve the performance and scalability of our application. For more details, see [MarketDataService](src/main/java/cz/cvut/nss/investmentmanagementsystem/service/MarketDataService.java).

3. **Interceptors**
    - Interceptors were implemented for logging purposes, primarily for basic CRUD operations and other significant actions within the application.

4. **RESTful API**
    - All controllers were designed following RESTful API principles to ensure a standardized and scalable approach to handling HTTP requests and responses.

5. **Microservice Architecture**
    - The application is designed with a microservice architecture, allowing for modular development, deployment, and scaling of individual services.

## Initialization Process

### Deployment Instructions
1. Clone the repository to your local machine.
   ```bash
   git clone https://gitlab.fel.cvut.cz/kovylole/semester-project-nss.git
   cd investment-management-system
### Ensure you have Java 17 and Gradle installed.

1. **Build the project using Gradle.**

    ```bash
    ./gradlew clean build
    ```

2. **Run the application.**

    ```bash
    ./gradlew bootRun
    ```

### Database Initialization
- The application uses an in-memory H2 database for development purposes.
- Basic data including admin user and sample portfolios are initialized automatically.
- For production, configure your database settings in `application.properties`.

### Design Patterns Used

#### Repository Pattern
- The repository pattern is used extensively throughout the application to abstract the data access layer. It provides a clean separation between the business logic and data access logic, promoting a more modular and testable codebase.

#### Service Layer Pattern
- The service layer encapsulates the business logic of the application. It sits between the controllers and repositories, ensuring that all business rules are applied consistently. This layer is responsible for orchestrating multiple repository calls and performing any necessary transformations or calculations.

#### Factory Pattern
- The factory pattern is used to manage the creation of orders, specifically to differentiate between buy and sell transactions. For more details, see the [Factory Order Service](src/main/java/cz/cvut/nss/investmentmanagementsystem/service/factoryorder).

### Note on Design Patterns
- We implemented three of the five required design patterns. Additional patterns such as Singleton could have been applied to configuration files, but Spring Boot's `@Configuration` annotation already provides this functionality.

### Use Cases
- Detailed use cases can be found in the [diagrams folder](diagrams).

### Javadoc
- Javadoc is provided for all public methods and classes, ensuring comprehensive documentation for developers.
