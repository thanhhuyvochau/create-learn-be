# Step 1: Build stage (optional if you already built JAR)
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Run stage
FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=builder /app/target/*.jar app.jar

# Environment variable for Spring Boot profile
ENV SPRING_PROFILES_ACTIVE=prod

# Expose the port (change if your app uses a different one)
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
