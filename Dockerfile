FROM openjdk:17-ea-11-jdk-slim

COPY build/libs/*.jar app.jar
#ENV SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/advertisement?useSSL=false&useUnicode=true&serverTimezone=Asia/Seoul
#ENV SPRING_DATASOURCE_USERNAME=root
#ENV SPRING_DATASOURCE_PASSWORD=1234
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]