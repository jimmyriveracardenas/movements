# ------------------------------------------------
# 1) Build stage: Maven + JDK 17
FROM maven:3.9.9-openjdk-17 AS build
WORKDIR /app

# Copiamos pom y wrapper para aprovechar cache de dependencias
COPY pom.xml mvnw* ./
COPY .mvn .mvn

# Descargamos dependencias
RUN mvn dependency:go-offline -B

# Copiamos el código fuente y empaquetamos
COPY src src
RUN mvn clean package -DskipTests -B

# ------------------------------------------------
# 2) Runtime stage: sólo JRE para ejecutar el JAR
FROM openjdk:17-slim
WORKDIR /app

# Traemos el JAR compilado
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto de la app
EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=prod

# Arrancamos la aplicación
ENTRYPOINT ["java","-jar","app.jar"]




# # Usa una imagen liviana de Java para correr el JAR
# FROM openjdk:17-slim

# # Directorio de trabajo dentro del contenedor
# WORKDIR /app

# # Copia el JAR que generaste localmente
# COPY target/technicaltest-0.0.1-SNAPSHOT.jar app.jar

# # Exponer el puerto
# EXPOSE 8080

# # Perfil de Spring Boot
# ENV SPRING_PROFILES_ACTIVE=prod

# # Comando para iniciar la app
# ENTRYPOINT ["java","-jar","app.jar"]


