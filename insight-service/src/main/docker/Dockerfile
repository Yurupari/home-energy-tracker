FROM eclipse-temurin:25-jdk-alpine

ADD insight-service/build/libs/insight-service.jar /
ENTRYPOINT ["java","-server","-Djava.security.egd=file:/dev/./urandom","-jar","/insight-service.jar"]
