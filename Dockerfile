FROM eclipse-temurin:latest
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} swift-code-app-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/swift-code-app-0.0.1-SNAPSHOT.jar"]