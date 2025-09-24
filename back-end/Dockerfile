FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY gradlew .
COPY gradle ./gradle
COPY build.gradle settings.gradle ./

RUN ./gradlew build -x test --stacktrace || return 0

COPY src ./src

RUN ./gradlew bootJar --stacktrace

EXPOSE 8080

CMD ["java", "-jar", "build/libs/back-end-0.0.1-SNAPSHOT.jar"]
