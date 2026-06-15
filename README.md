# College Management System (Console-Based)

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
