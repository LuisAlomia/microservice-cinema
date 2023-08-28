FROM openjdk:17-jdk-alpine

LABEL authors="LUIS FIDEL A. V."

COPY target/cinema_service_movie-0.0.1-SNAPSHOT.jar java-cinema-movies-service.jar

ENTRYPOINT ["java", "-jar", "java-cinema-movies-service.jar"]