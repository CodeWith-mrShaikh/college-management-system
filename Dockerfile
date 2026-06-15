# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /workspace
COPY pom.xml .
COPY src ./src
RUN mvn -B -DskipTests package

# Run stage
FROM eclipse-temurin:21-jre
WORKDIR /app
# copy any built jar (artifactId-version.jar) to app.jar to make Dockerfile robust
COPY --from=builder /workspace/target/*college-management-system*.jar /app/app.jar
ENV JAVA_OPTS=""
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar --spring.profiles.active=prod"]
