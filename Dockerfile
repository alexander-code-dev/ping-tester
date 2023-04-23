FROM eclipse-temurin:17-jdk-alpine
COPY target/ping-tester-1.0.1.jar ping-tester-1.0.1.jar
ENTRYPOINT ["java", "-jar","ping-tester-1.0.1.jar"]
EXPOSE 8080