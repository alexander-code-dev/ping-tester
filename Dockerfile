FROM eclipse-temurin:17-jdk-alpine
ADD target/ping-tester-1.0.0.jar ping-tester-1.0.0.jar
ENTRYPOINT ["java", "-jar","ping-tester-1.0.0.jar"]
EXPOSE 8080