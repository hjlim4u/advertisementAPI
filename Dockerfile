#FROM gradle:7.6-jdk17-alpine as BUILD
#ENV APP_HOME=/Assignment2
#WORKDIR $APP_HOME
#COPY build.gradle settings.gradle gradlew $APP_HOME
#COPY gradle $APP_HOME/gradle
#
#RUN chmod +x gradlew
#RUN ./gradlew build || return 0
#
#COPY src $APP_HOME/src
#RUN ./gradlew clean build

FROM openjdk:17.0.2-jdk

#ENV APP_HOME=/Assignment2
#ARG ARTIFACT_NAME=app2.jar
#ARG JAR_FILE_PATH=build/libs/Assignment2-0.0.1-SNAPSHOT.jar
#WORKDIR $APP_HOME
#COPY --from=build $APP_HOME/$JAR_FILE_PATH $ARTIFACT_NAME
COPY build/libs/*.jar app2.jar
#ENV SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/advertisement?useSSL=false&useUnicode=true&serverTimezone=Asia/Seoul
#ENV SPRING_DATASOURCE_USERNAME=root
#ENV SPRING_DATASOURCE_PASSWORD=1234
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app2.jar"]