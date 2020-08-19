# Alpine Linux with OpenJDK JRE
FROM openjdk:8-jre-alpine

# Copy the war file
COPY YaraAgroAPI-0.0.1-SNAPSHOT.jar /yaraAgro.war

# run the app
CMD ["java", "-jar", "/yaraAgro.war"]
