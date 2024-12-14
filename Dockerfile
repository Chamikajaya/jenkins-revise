# Stage 1: Build the application
FROM maven:3.9.2-eclipse-temurin-17-alpine AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:resolve dependency:resolve-plugins -B -ntp

# Copy the source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests -B -ntp

# Stage 2: Create the runtime image
FROM eclipse-temurin:17-jre-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
