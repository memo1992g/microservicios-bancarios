# ====== STAGE 1: Build ======
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn -e -B dependency:resolve

COPY src ./src

RUN mvn -e -B clean package -DskipTests

# ====== STAGE 2: Runtime ======
FROM eclipse-temurin:17-jdk
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
