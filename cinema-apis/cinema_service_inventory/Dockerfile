FROM openjdk:17-jdk-alpine

LABEL authors="LUIS FIDEL A. V."

COPY target/cinema_service_inventory-0.0.1-SNAPSHOT.jar java-cinema-inventory-service.jar

ENTRYPOINT ["java", "-jar", "java-cinema-inventory-service.jar"]