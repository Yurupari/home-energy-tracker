FROM eclipse-temurin:25-jdk-alpine

ADD device-service/build/libs/device-service.jar /
ENTRYPOINT ["java","-server","-Djava.security.egd=file:/dev/./urandom","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8086","-jar","/device-service.jar"]
