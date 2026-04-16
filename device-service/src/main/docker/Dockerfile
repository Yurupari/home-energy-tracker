FROM eclipse-temurin:25-jdk-alpine

ADD device-service/build/libs/device-service.jar /
ENTRYPOINT ["java","-server","-Djava.security.egd=file:/dev/./urandom","-jar","/device-service.jar"]
