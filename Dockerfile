FROM eclipse-temurin:21-jdk-jammy AS build

WORKDIR /app

# Copy gradle files
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Copy source code for all modules
COPY cliente ./cliente
COPY orcamento ./orcamento
COPY peca_insumo ./peca_insumo
COPY ordem_servico ./ordem_servico
COPY servico ./servico
COPY monitoramento ./monitoramento
COPY src ./src

RUN chmod +x gradlew
RUN ./gradlew clean build -x test bootJar --no-daemon


FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copy the application jar from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 9091

ENTRYPOINT ["java", "-jar", "app.jar"]