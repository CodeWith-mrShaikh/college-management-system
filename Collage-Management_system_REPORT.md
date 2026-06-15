# College Management System — Project Report


**Prepared By:** TARIQ SHAIKH — 24BCE10760

**Course / Semester:** [Winter Internship]

**Date:** 2026-06-13

---

## Abstract

This document is a professional project report for the College Management System — a Spring Boot application that demonstrates core CRUD operations, lightweight reporting and an optional console-based UI backed by MongoDB. The system is designed for small college setups and provides modular services for managing students, staff, departments and books. This report describes the architecture, data model, implementation details, testing strategy, build and deployment instructions, and recommendations for future improvement.

**Keywords:** Spring Boot, MongoDB, Java 21, Docker, Maven, Actuator, JaCoCo, Mockito

---

**Acknowledgements**

Thanks to the project guide and resources used (Spring Boot documentation, MongoDB docs, Maven) during implementation.

---

## Table of Contents

1. Introduction
2. Objectives
3. System Overview and Architecture
4. Data Model
5. Key Components and Code Structure
6. Functional Description and User Flows
7. Implementation Details
8. Build, Test and Deployment
9. Continuous Integration
10. Testing and Quality Assurance
11. Security Considerations
12. Limitations and Future Work
13. Appendix — File Map & Commands

---

## 1. Introduction

The College Management System is a compact, maintainable application developed with modern Java tooling and best practices. It provides a simple console-based management UI for campus administrators, with a production profile that allows headless operation in a containerized environment. The project demonstrates backend service layering, Spring Data integration with MongoDB and container-friendly packaging.

## 2. Objectives

- Provide a modular system to manage Students, Staff, Departments and Books.
- Use Spring Boot with idiomatic service and repository layers.
- Persist data using MongoDB (document store) with clear collection design.
- Make the console UI optional to allow headless deployment (Docker).
- Provide reproducible builds (Maven wrapper) and a CI workflow.
- Include unit testing and code coverage reporting.

## 3. System Overview and Architecture

High-level architecture (components):

- Presentation: Optional Console menu (interactive) — `ConsoleMenu`.
- Service Layer: `StudentService`, `StaffService`, `DepartmentService`, `BookService`.
- Persistence: Spring Data MongoDB repositories (`MongoRepository` implementations).
- Infrastructure: MongoDB, Docker, Docker Compose, GitHub Actions CI.

Mermaid architecture diagram:

```mermaid
graph LR
  Console[Console / CLI (optional)] -->|calls| Services[Service Layer]
  Services -->|uses| Repos[Mongo Repositories]
  Repos --> MongoDB[(MongoDB)]
  App[Spring Boot Application] --> Actuator[Actuator (health, info)]
  DockerCompose[Docker Compose] --> MongoDB
  DockerCompose --> App
```

Notes:

- The console UI is guarded by configuration `app.console.enabled` and disabled in the `prod` profile.
- Actuator endpoints are enabled for health checks in the `prod` profile.

## 4. Data Model

Collections and entities:

- `students` (collection) — `Student` entity
  - Fields: `id` (String), `name` (String), `email` (String), `phone` (String), `departmentId` (String), `borrowedBookIds` (List<String>)

- `staff` (collection) — `Staff` entity
  - Fields: `id`, `name`, `designation`, `email`, `departmentId`

- `departments` (collection) — `Department` entity
  - Fields: `id`, `departmentName`, `hodName`, `location`

- `books` (collection) — `Book` entity
  - Fields: `id`, `title`, `author`, `isbn`

Relationships (logical):

- Students and Staff reference `departmentId` which links to `departments`.`id`.
- Students maintain a list of borrowed book IDs (`borrowedBookIds`) referencing `books`.`id`.

Sample JSON documents (examples):

```json
// Student
{
  "_id": "S1001",
  "name": "Alice Kumar",
  "email": "alice@example.edu",
  "phone": "9876543210",
  "departmentId": "D01",
  "borrowedBookIds": ["B100","B105"]
}

// Book
{
  "_id": "B100",
  "title": "Introduction to Algorithms",
  "author": "Cormen et al.",
  "isbn": "9780262033848"
}
```

## 5. Key Components and Code Structure

- Main application: [src/main/java/com/college/CollegeManagementApplication.java](src/main/java/com/college/CollegeManagementApplication.java#L1)
- Console UI: [src/main/java/com/college/menu/ConsoleMenu.java](src/main/java/com/college/menu/ConsoleMenu.java#L1)
- Entities: [src/main/java/com/college/entity/](src/main/java/com/college/entity/)
- Repositories: [src/main/java/com/college/repository/](src/main/java/com/college/repository/)
- Services: [src/main/java/com/college/service/](src/main/java/com/college/service/)
- Docker: [Dockerfile](Dockerfile), [docker-compose.yml](docker-compose.yml)
- Build: [pom.xml](pom.xml)

The code follows a simple service → repository pattern: services validate inputs and call Spring Data repositories for persistence. Exceptions such as `ResourceNotFoundException` are used to represent missing resources.

## 6. Functional Description and User Flows

Primary features exposed via the console menu (or usable programmatically when headless):

- Student Management: Add, View, List, Update, Delete
- Staff Management: Add, View, List, Update, Delete
- Department Management: Add, View, List, Update, Delete
- Book Management: Add, View, List, Update, Delete, Issue to Student
- Reports: Students per Department, Books Issued, Staff per Department

Book issue flow (sequence):

1. Operator selects "Issue Book" in Book Menu.
2. Console reads Student ID and verifies existence via `StudentService.getStudent(id)`.
3. Console reads Book ID and verifies existence via `BookService.getBook(id)`.
4. If both exist, book ID is appended to the student's `borrowedBookIds` and `StudentService.updateStudent(student)` is called to persist the change.

Edge cases: the code does not currently validate book availability counts (a book can be issued multiple times by ID); add availability tracking in future extensions.

## 7. Implementation Details

- Language and Frameworks: Java 21, Spring Boot 3.2
- Build: Maven (Maven Wrapper included for reproducible builds)
- Database: MongoDB (official images supported by Docker Compose)
- Logging: SLF4J (via Spring Boot starter)
- Testing: JUnit 5, Mockito (unit tests), JaCoCo (coverage report)

Important implementation notes:

- The project enforces Java 21 via the Maven Enforcer plugin declared in `pom.xml`.
- `application-prod.yml` uses `MONGODB_URI` environment variable allowing containerized deployments to point to the correct MongoDB instance.
- The `ConsoleMenu` is annotated with `@ConditionalOnProperty(value = "app.console.enabled", havingValue = "true", matchIfMissing = true)` so the console can be disabled in production (`app.console.enabled=false`).

## 8. Build, Test and Deployment

Prerequisites:

- Java 21 SDK installed (set `JAVA_HOME`).
- Docker and Docker Compose for containerized runs.
- (Optional) Docker Hub or another container registry for image publishing.

Build and run locally (Windows PowerShell examples):

```powershell
cd college-management-system
# Build (packaging without running tests):
.\mvnw.cmd -DskipTests package

# Run tests and generate JaCoCo report:
.\mvnw.cmd test

# Build Docker image (if Docker available):
docker build -t college-management-system:latest .

# Run in container (example using host MongoDB):
docker run -e MONGODB_URI="mongodb://host.docker.internal:27017/college_db" -p 8080:8080 college-management-system:latest

# Or start local stack with MongoDB (recommended for integration checks):
docker-compose up --build
```

Notes:

- The `Dockerfile` uses a multi-stage build and copies the built jar using a wildcard to be resilient to artifact name changes.
- The application exposes port `8080` (container), and the production profile disables the interactive console.

Environment variables:

- `MONGODB_URI` — MongoDB connection string (fallback in `application-prod.yml`: `mongodb://localhost:27017/college_db`).
- `JAVA_OPTS` — extra JVM options supported by the Docker entrypoint.

## 9. Continuous Integration

- A GitHub Actions workflow is present for building and testing the code. See `.github/workflows/maven-ci.yml` in the repository for details.
- The CI runs on JDK 21, runs tests and the build, and can be extended to build-and-push Docker images to a registry (set credentials as secrets).

## 10. Testing and Quality Assurance

- Unit tests: Example unit test `BookServiceTest` uses Mockito to stub `BookRepository` and validate `BookService` behaviour. See: [src/test/java/com/college/service/BookServiceTest.java](src/test/java/com/college/service/BookServiceTest.java#L1).
- Coverage: JaCoCo reports are generated under `target/site/jacoco/` after running the test phase (`mvn test`).

To run tests and open the coverage report (Linux/macOS):

```bash
./mvnw test
# then open target/site/jacoco/index.html in a browser
```

On Windows PowerShell:

```powershell
.\mvnw.cmd test
Start-Process target/site/jacoco/index.html
```

## 11. Security Considerations

- Do not expose actuator endpoints to the public without authentication. Consider enabling Spring Security and restricting `management.endpoints.web.exposure` in production.
- Avoid embedding credentials in the image — supply `MONGODB_URI` via environment variables or a secret management solution at deploy time.
- For production MongoDB use: enable authentication, TLS, and a database user with least privilege.

## 12. Limitations and Future Work

Immediate improvements recommended for a production-ready submission:

1. Add integration tests using Testcontainers to validate behavior against a real MongoDB instance in CI.
2. Add availability tracking for books (number of copies) to prevent duplicate issuance.
3. Add REST controllers and a simple frontend UI to allow non-console use.
4. Add authentication and role-based access control for administrative operations.
5. Add structured logging and observability (metrics + tracing).

## 13. Appendix — File Map & Useful Links

- Main class: [src/main/java/com/college/CollegeManagementApplication.java](src/main/java/com/college/CollegeManagementApplication.java#L1)
- Console UI: [src/main/java/com/college/menu/ConsoleMenu.java](src/main/java/com/college/menu/ConsoleMenu.java#L1)
- Entities folder: [src/main/java/com/college/entity/](src/main/java/com/college/entity/)
- Services folder: [src/main/java/com/college/service/](src/main/java/com/college/service/)
- Repositories folder: [src/main/java/com/college/repository/](src/main/java/com/college/repository/)
- Build: [pom.xml](pom.xml)
- Docker: [Dockerfile](Dockerfile), [docker-compose.yml](docker-compose.yml)
- Tests: [src/test/java/com/college/service/BookServiceTest.java](src/test/java/com/college/service/BookServiceTest.java#L1)

### Quick commands (copy-paste)

PowerShell (Windows):

```powershell
cd college-management-system
.\mvnw.cmd -DskipTests package
.\mvnw.cmd test
docker-compose up --build
```

Bash (Linux/macOS):

```bash
./mvnw -DskipTests package
./mvnw test
docker-compose up --build
```
