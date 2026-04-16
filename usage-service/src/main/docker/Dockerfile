FROM eclipse-temurin:25-jdk-alpine

ADD usage-service/build/libs/usage-service.jar /
ENTRYPOINT ["java","-server","-Djava.security.egd=file:/dev/./urandom","-jar","/usage-service.jar"]
