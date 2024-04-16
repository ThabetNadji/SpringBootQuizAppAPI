FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/spring_quiz_app_api-1.0.2.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]
