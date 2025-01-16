# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the project JAR file into the container at /app
COPY target/app-0.0.1-SNAPSHOT.jar app.jar

# Make port 4000 available to the world outside this container
EXPOSE 4000

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]