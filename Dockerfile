# Etapa de build: compila el JAR usando Maven y Java 21
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copiamos solo el pom primero para aprovechar la caché de dependencias
COPY pom.xml .
RUN mvn -q -e -B dependency:go-offline

# Ahora copiamos el código fuente y construimos el JAR
COPY src ./src
RUN mvn -q -e -B clean package -DskipTests

# Etapa de runtime: usa una imagen ligera de Java 21 para ejecutar el JAR
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copiamos el JAR generado desde la etapa de build
COPY --from=build /app/target/examen-0.0.1-SNAPSHOT.jar app.jar

# Puerto por defecto de Spring Boot (configurado en application.properties)
EXPOSE 8080

# Permitir pasar flags a la JVM mediante JAVA_OPTS
ENV JAVA_OPTS=""

# Comando de arranque
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
