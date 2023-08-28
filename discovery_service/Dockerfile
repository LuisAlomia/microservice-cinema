FROM openjdk:17-jdk-alpine

LABEL authors="LUIS FIDEL A. V."

COPY target/discovery_service-0.0.1-SNAPSHOT.jar java-discovery-server.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "java-discovery-server.jar"]