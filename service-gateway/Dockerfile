FROM openjdk:19-jdk-alpine

LABEL authors="otavio.silva@inpe.br"

RUN apk update

RUN apk add bash

WORKDIR /app

EXPOSE 8080

COPY ./target/service-gateway-0.0.1-SNAPSHOT.jar .

COPY wait-for-it.sh .

ENTRYPOINT ["./wait-for-it.sh", "discovery:8761", "--", "java", "-jar", "service-gateway-0.0.1-SNAPSHOT.jar"]