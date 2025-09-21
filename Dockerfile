# ======================
# 1. Build stage
# ======================
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /build

# Copy entire project
COPY . .

# Package the app module and all dependencies
RUN mvn -B clean package -DskipTests -pl app -am

# ======================
# 2. Runtime stage
# ======================
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy the built jar from builder
COPY --from=builder /build/app/target/app-0.0.1-SNAPSHOT.jar app.jar

# Expose Spring Boot default port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
