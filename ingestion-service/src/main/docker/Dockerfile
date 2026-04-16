FROM eclipse-temurin:25-jdk-alpine

ADD ingestion-service/build/libs/ingestion-service.jar /
ENTRYPOINT ["java","-server","-Djava.security.egd=file:/dev/./urandom","-jar","/ingestion-service.jar"]
