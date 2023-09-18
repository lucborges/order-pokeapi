FROM openjdk:17
MAINTAINER Lucas Borges
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]