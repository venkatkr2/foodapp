# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the project files to the working directory
COPY . .

# Build the application using Maven
RUN ./mvnw package -DskipTests

# Expose port 8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "target/foodapp-0.0.1-SNAPSHOT.jar"]
