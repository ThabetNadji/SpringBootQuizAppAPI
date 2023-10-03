FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar SpringQuizAppAPI-1.0.2.jar
ENTRYPOINT ["java","-jar","/SpringQuizAppAPI-1.0.2.jar"]
EXPOSE 8080
