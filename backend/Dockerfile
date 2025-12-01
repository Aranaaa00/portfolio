# Stage 1: Build con Maven
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copiar archivos de Maven
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Descargar dependencias (cache layer)
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

# Copiar código fuente y compilar
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Stage 2: Runtime ligero
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiar solo el JAR compilado
COPY --from=build /app/target/*.jar app.jar

# Exponer puerto interno (NO visible fuera de Docker)
EXPOSE 8080

# Ejecutar aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
