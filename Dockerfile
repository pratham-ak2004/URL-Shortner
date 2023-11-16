#FROM ubuntu:latest
#LABEL authors="prath"
#
#ENTRYPOINT ["top", "-b"]

FROM eclipse-temurin:17-jdk-alpine
VOLUME /temp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080


#FROM eclipse-tenurin:21-jdk-alpine
#VOLUME /temp
#COPY target/*.jar app.jar
#
#ENTRYPOINT ["java" , ".jar" , "/app.jar"]
#EXPOSE 8080
