FROM openjdk:21-ea-1-jdk-slim
WORKDIR /app
COPY build/libs/mm-0.0.1-SNAPSHOT.jar application.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "application.jar"]