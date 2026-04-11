FROM eclipse-temurin:25-jdk-alpine

ADD ingestion-service/build/libs/ingestion-service.jar /
ENTRYPOINT ["java","-server","-Djava.security.egd=file:/dev/./urandom","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8087","-jar","/ingestion-service.jar"]
