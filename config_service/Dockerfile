FROM openjdk:17-jdk-alpine

LABEL authors="LUIS FIDEL A. V."

COPY target/config_service-0.0.1-SNAPSHOT.jar java-config-server.jar

EXPOSE 9999

ENTRYPOINT ["java", "-jar", "java-config-server.jar"]