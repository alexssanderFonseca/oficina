FROM eclipse-temurin:21-jdk-jammy AS build

WORKDIR /app

COPY gradlew .
COPY gradle ./gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .


RUN ./gradlew clean build --no-daemon --dry-run


COPY src ./src


RUN ./gradlew clean bootJar --no-daemon


FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copia o jar do estágio anterior
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
