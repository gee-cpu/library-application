
## Author

- **Name:** George Onchaba
- **Email:** ggonchaba@gmail.com
- **GitHub:** https://github.com/gee-cpu
- **PhoneNumber:** 0728238210

**#Book Service - LibraryManagementApplication**
## Overview

This is a Spring Boot application designed to manage books in a library.
The application follows a layered architecture with a strong emphasis on SOLID principles,
clean code, and maintainable design. The application provides a RESTful API for creating,
retrieving, updating, and deleting books, with integration tests to ensure the correct behavior of the API.

**#High-Level Design**

### 1. **Controller Layer**
- **BookController**: Exposes the RESTful API endpoints for managing books. 
It handles HTTP requests and delegates the business logic to the service layer.

### 2. **Service Layer**
- **BookService**: An interface defining the contract for book-related operations.
- **BookServiceImpl**: Implements the `BookService` interface, encapsulating the business logic for 
managing books. It interacts with the repository layer to perform CRUD operations.

### 3. **Repository Layer**
- **BookRepository**: Extends `JpaRepository` to provide database access for `Book` entities.
- It abstracts the data persistence layer and allows the service layer to interact with the database 
- seamlessly.

### 4. **Data Transfer Objects (DTOs)**
- **BookDTO**: A data transfer object that abstracts the book data structure for update operations. 
- It is used to transfer data between the client and the server without exposing the internal details of the `Book` entity.

**#Low-Level Design**

### 1. **Entity**
- **Book**: Represents the book entity with fields for `id`, `title`, `author`, and `year`.
- Basic validation is enforced on the `title` and `author` fields to ensure they are not empty.

### 2. **Validation**
- Validation is performed using annotations on the `Book` entity to ensure data integrity. For example:
    - `@NotNull` on the `title` and `author` fields ensures that these fields are not empty when creating or updating a book.

### 3. **Exception Handling**
- The service layer handles exceptions gracefully by catching them and returning appropriate responses.
- This approach ensures that the application is resilient and provides meaningful error messages to the client.

### 4. **DTO Abstraction**
- DTOs are used to decouple the internal representation of entities from the external API. 
- This abstraction allows the service to evolve without breaking the API contract with the clients.

### 5. **SOLID Principles**
- **Single Responsibility Principle**: Each class in the application has a single responsibility,
- making the code easier to maintain and extend.
- **Open/Closed Principle**: The service and repository interfaces are open for extension but closed for modification, 
- ensuring that new functionalities can be added without altering existing code.
- **Liskov Substitution Principle**: The service implementations can be substituted without affecting the correctness of the program.
- **Interface Segregation Principle**: The `BookService` interface provides a clear contract for book-related operations,
- ensuring that clients are not forced to depend on methods they do not use.
- **Dependency Inversion Principle**: The service layer depends on abstractions (interfaces) rather than concrete implementations,
- promoting loose coupling between components.

### 6. **Testing**
- **Integration Tests**: Comprehensive integration tests are provided for the `BookController`, using `MockMvc` 
- to simulate HTTP requests and validate the responses. These tests ensure that the entire stack
- (controller, service, repository) works as expected.

## API Endpoints

- **POST /books**: Create a new book.
- **GET /books/{id}**: Retrieve a book by its ID.
- **GET /books**: Retrieve all books.
- **PUT /books/{id}**: Update an existing book.
- **DELETE /books/{id}**: Delete a book by its ID.

## How to Run the Application

1. **Clone the Repository**:
 open the application using any java IDE preferrably intellij, ensure JDK 22 is installed.
   ```
2. **Build the Application**:
   ```bash
   mvn clean install
   ```
3. **Run the Application**:
   ```bash
   mvn spring-boot:run
   ```
4. **Access the API**:
    - The API can be accessed at `http://localhost:8080/books`.

## Swagger Documentation

Swagger is integrated into the project for API documentation. You can view the API documentation at `http://localhost:8080/swagger-ui.html`.

## Future Enhancements

- **Security**: Implement role-based access control using Spring Security.
- **Pagination**: Add pagination to the `GET /books` endpoint to handle large datasets.
- **Caching**: Introduce caching to optimize database queries for frequently accessed data.
- **Asynchronous Processing**: Handle long-running tasks asynchronously to improve performance.

