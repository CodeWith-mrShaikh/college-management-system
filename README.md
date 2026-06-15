# College Management System (Console-Based)

Project Objective

The objective of this project is to develop a console-based College Management System that manages Students, Staff, Departments, and Books using Spring Boot and MongoDB. The system will provide CRUD (Create, Read, Update, Delete) operations for all entities, maintain relationships between collections, and demonstrate backend development concepts such as layered architecture, dependency injection, repository pattern, exception handling, logging, and MongoDB data modeling.

The application will operate through a console menu without a frontend interface and will use MongoDB as the primary database for storing and managing data.

Key Features : 

Student Management
Add Student
View Student
Update Student
Delete Student
Staff Management
Add Staff
View Staff
Update Staff
Delete Staff
Department Management
Add Department
View Department
Update Department
Delete Department
Book Management
Add Book
View Book
Update Book
Delete Book
Relationships
Department → Students (One-to-Many)
Department → Staff (One-to-Many)
Department ↔ HOD Staff (One-to-One)
Student ↔ Books (Many-to-Many)
Additional Features
Exception Handling
Logging
Reports Module
MongoDB Integration
Menu Driven Console Application

Spring Boot + MongoDB console application scaffolding.

Quick start

1. Ensure MongoDB is running on localhost:27017
2. Build and run:

```powershell
./mvnw -DskipTests package
./mvnw test
```

The application will start and display a console menu.

Notes:
- Requires Java 21 (configured in `pom.xml`)
- CI workflow is provided at `.github/workflows/maven-ci.yml`

Docker

Build the container image locally:

```bash
docker build -t college-management-system:latest .
```

Run the container (app uses production profile which disables the interactive console):

```bash
docker run -e MONGODB_URI="mongodb://host.docker.internal:27017/college_db" -p 8080:8080 college-management-system:latest
```

Or use Docker Compose (starts MongoDB + app):

```bash
docker-compose up --build
```

Health endpoint: `http://localhost:8080/actuator/health`
