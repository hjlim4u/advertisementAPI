FROM openjdk:18 AS builder
COPY . /tmp
WORKDIR /tmp
# COPY ./test-application /test-application
# WORKDIR /Assig

# CMD [ "./gradlew", "bootRun" ]

# RUN sed -i 's/\r$//' ./gradlew
# RUN ./gradlew build


# ARG JAR_FILE="build/libs/simple-*.jar"

FROM openjdk:18
COPY --from=builder /tmp/build/libs/*.jar ./

CMD ["java", "-jar", "spring-boot-0.0.1-SNAPSHOT.jar"]