FROM openjdk:19-jdk-alpine

LABEL authors="otavio.silva@inpe.br"

WORKDIR /app

EXPOSE 8761

COPY ./target/service-discovery-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "service-discovery-0.0.1-SNAPSHOT.jar"]