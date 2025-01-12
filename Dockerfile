# Etapa 1: Construcción del proyecto
FROM maven:3.9.4-eclipse-temurin-21 AS mavenimg

# Crear y configurar directorio de trabajo
RUN mkdir -p /workspace
WORKDIR /workspace

# Copiar configuraciones de Maven y archivos del proyecto
COPY pom.xml /workspace

# Descargar dependencias para evitar descargas repetitivas
RUN mvn dependency:go-offline -B

# Copiar el código fuente
COPY src /workspace/src

# Construir el proyecto sin pruebas
RUN mvn -f pom.xml clean package -DskipTests

# Etapa 2: Imagen final para la ejecución
FROM openjdk:21-slim AS finalimg

# Copiar el archivo JAR generado
COPY --from=mavenimg /workspace/target/*.jar /app.jar

# Exponer el puerto de la aplicación
EXPOSE 8130

# Configurar el comando de entrada
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=pru", "/app.jar"]
