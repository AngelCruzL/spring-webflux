FROM eclipse-temurin:17
WORKDIR /app

LABEL authors="angelcruz"

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]