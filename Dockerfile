# Use an official OpenJDK image as a base image
FROM amazoncorretto:17

# Set the working directory in the container
WORKDIR /app

COPY build/libs/tafdatastore.jar /app/tafdatastore.jar

# Expose the port the app will run on
EXPOSE 9090

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "tafdatastore.jar"]
