FROM eclipse-temurin:21-jre

WORKDIR /app

COPY build/libs/mock-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 5000

ENTRYPOINT ["java", "-jar", "app.jar"]