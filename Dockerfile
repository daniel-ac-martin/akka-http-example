FROM openjdk:8-jre-alpine

COPY target/scala-2.12/example-assembly-0.1.0.jar /app.jar

CMD ["java", "-jar", "/app.jar"]