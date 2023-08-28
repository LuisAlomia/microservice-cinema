FROM openjdk:17-jdk-alpine

LABEL authors="LUIS FIDEL A. V."

COPY target/monitoring-service-0.0.1-SNAPSHOT.jar java-monitoring-service.jar

EXPOSE 10001

ENTRYPOINT ["java", "-jar", "java-monitoring-service.jar"]