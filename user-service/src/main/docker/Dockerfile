FROM eclipse-temurin:25-jdk-alpine

ADD user-service/build/libs/user-service.jar /
ENTRYPOINT ["java","-server","-Djava.security.egd=file:/dev/./urandom","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8085","-jar","/user-service.jar"]
