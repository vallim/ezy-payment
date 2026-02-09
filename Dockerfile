FROM eclipse-temurin:17-jdk-jammy AS builder

WORKDIR /app

COPY . .

# Garante permissão de execução no Windows
RUN chmod +x ./gradlew

RUN ./gradlew clean bootJar

# -------- imagem final --------
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]
