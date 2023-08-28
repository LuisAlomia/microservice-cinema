FROM openjdk:17-jdk-alpine

LABEL authors="LUIS FIDEL A. V."

COPY target/gateway-service-0.0.1-SNAPSHOT.jar java-gateway-services.jar

EXPOSE 10000

ENTRYPOINT ["java", "-jar", "java-gateway-services.jar"]