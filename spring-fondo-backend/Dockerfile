FROM openjdk:17-jdk-slim

LABEL org.opencontainers.image.authors="jmantillap@gmail.com"
#host.docker.internal --> maquina fisica donde esta la base de data. host anfitrion
#ENV JDBC_URL_BD=jdbc:mysql://host.docker.internal:3306/test
#ENV USERNAME_BD=root
#ENV PASSWORD_BD=root

COPY target/fondo-backend-1.0.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

#$ docker build fondo-app:v1 .
#$ docker run -it --name fondo-contenedor-app -p 8080:8080 fondo-app:v1

#$ docker run -it --rm --name fondo-contenedor-app -p 8080:8080 fondo-app:v1
