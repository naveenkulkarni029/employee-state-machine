FROM openjdk:11.0.13-jdk-slim AS base
WORKDIR /employee-state-machine
EXPOSE 8080

FROM maven:3.8.4-openjdk-11-slim as maven
COPY ./pom.xml ./pom.xml
COPY ./src ./src
RUN mvn clean install 
RUN mvn package spring-boot:repackage

FROM base
COPY --from=maven target/employee-state-machine-1.0.0.jar ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]