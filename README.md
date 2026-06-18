# College Management System

Overview

This project is a console-driven backend application for managing a small college's core entities: Students, Staff, Departments, and Books. Built with Spring Boot and MongoDB, the system demonstrates layered backend design, repository-based data access, and production-oriented features such as structured logging, standardized error handling, API documentation, and metrics exposure.

Key capabilities

- CRUD operations for Students, Staff, Departments, and Books
- Menu-driven console interface for local administration (no web UI required)
- REST endpoints and OpenAPI docs for integration with external clients
- Optional API-key protection for simple service-to-service requests
- Secure Actuator endpoints and Prometheus-compatible metrics
- Structured JSON logs (Logback) and centralized-friendly output

Domain relationships

- Department → Students (one-to-many)
- Department → Staff (one-to-many)
- Department ↔ HOD Staff (one-to-one)
- Student ↔ Books (many-to-many)

Additional features

- Exception handling with consistent `ApiError` responses
- Reports module for aggregated summaries (counts, distributions)
- MongoDB-backed persistence with Spring Data repositories
- CI workflows and Docker artifacts for reproducible builds and smoke tests

Technology stack

- Java 21
- Spring Boot 3.x (Web / Data MongoDB / Security / Actuator)
- Spring Data MongoDB
- Micrometer (Prometheus registry)
- springdoc OpenAPI
- Logback for structured logging

Prerequisites

- Java 21 installed and available on `PATH`
- MongoDB running locally (default: `localhost:27017`) or reachable via `MONGODB_URI`
- Docker & Docker Compose (optional, for containerized runs)

Quick start

1. Start MongoDB (if not using Docker): ensure it listens on `localhost:27017`.
2. Build the application using the Maven wrapper:

```bash
./mvnw -DskipTests package
./mvnw test
```

3. Run the packaged JAR (example using the production profile):

```bash
export MANAGEMENT_SECURITY_USER_NAME=admin
export MANAGEMENT_SECURITY_USER_PASSWORD=ChangeMeNow
java -jar target/college-management-system-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

> Note: the `prod` profile disables the interactive console; use the REST endpoints for integration in that mode.

Docker

Build the image locally:

```bash
docker build -t college-management-system:latest .
```

Run the container against a host MongoDB instance:

```bash
docker run -e MONGODB_URI="mongodb://host.docker.internal:27017/college_db" -p 8080:8080 college-management-system:latest
```

Or start both MongoDB and the app via Docker Compose:

```bash
docker-compose up --build
```

Useful endpoints

- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- Health: `http://localhost:8080/actuator/health` (requires management credentials in `prod`)
- Prometheus metrics: `http://localhost:8080/actuator/prometheus` (requires management credentials in `prod`)

Configuration & secrets

Sensitive values such as management credentials and optional API key should be provided through environment variables or a secrets manager. See `.env.template` for commonly used env vars (e.g., `MANAGEMENT_SECURITY_USER_NAME`, `MANAGEMENT_SECURITY_USER_PASSWORD`, `APP_API_KEY`, `SPRING_DATA_MONGODB_URI`).







