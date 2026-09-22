FROM openjdk:17.0.2-jdk

COPY build/libs/*.jar app2.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app2.jar"]