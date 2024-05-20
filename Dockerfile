FROM openjdk:17-oracle
LABEL authors="aleksandr"
EXPOSE 8081
ADD target/CloudService-0.0.1-SNAPSHOT.jar cloud-service-app.jar
ENTRYPOINT ["java", "-jar", "cloud-service-app.jar"]