# Build stage
FROM maven:3.9-eclipse-temurin-11 AS build

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source and build
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:11-jre

WORKDIR /app

# Copy JAR from build stage
COPY --from=build /app/target/dukaan-backend-1.0.0.jar app.jar

# Expose port
EXPOSE 8080

# Run with H2 profile
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=h2"]
