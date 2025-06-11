

# -------- Build Stage --------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
COPY src ./src
RUN ./mvnw clean package -DskipTests

# -------- Run Stage --------
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/Hospital-Management-2025-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]

