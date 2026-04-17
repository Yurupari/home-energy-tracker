FROM eclipse-temurin:25-jdk-alpine

ADD alert-service/build/libs/alert-service.jar /
ENTRYPOINT ["java","-server","-Djava.security.egd=file:/dev/./urandom","-jar","/alert-service.jar"]
