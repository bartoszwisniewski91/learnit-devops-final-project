# Uproszczony Stage 1
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
# Kopiujemy całą zawartość contextu zamiast pojedynczego pliku
COPY . . 
RUN mvn clean package -DskipTests

# Stage 2 pozostaje bez zmian
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]