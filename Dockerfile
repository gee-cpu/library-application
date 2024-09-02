
FROM openjdk:22

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} library-application.jar

ENTRYPOINT ["java", "-jar", "/library-application.jar"]