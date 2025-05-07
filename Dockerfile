# Usa una imagen liviana de Java para correr el JAR
FROM openjdk:17-slim

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el JAR que generaste localmente
COPY target/technicaltest-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto
EXPOSE 8080

# Perfil de Spring Boot
ENV SPRING_PROFILES_ACTIVE=prod

# Comando para iniciar la app
ENTRYPOINT ["java","-jar","app.jar"]




# # 1) Etapa de build
# FROM openjdk:17-slim AS build
# WORKDIR /app
# COPY pom.xml mvnw* ./
# COPY .mvn .mvn
# COPY src src
# RUN mvn clean package -DskipTests

# # 2) Etapa de runtime
# FROM openjdk:17-slim
# WORKDIR /app
# COPY --from=build /app/target/*.jar app.jar
# EXPOSE 8080
# ENV SPRING_PROFILES_ACTIVE=prod
# ENTRYPOINT ["java","-jar","app.jar"]


