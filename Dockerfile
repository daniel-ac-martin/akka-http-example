FROM openjdk:8-jre-alpine

RUN apk update \
 && apk add curl bash

RUN adduser -D -h /app/ app
WORKDIR /app
USER app

RUN curl https://repo1.maven.org/maven2/org/flywaydb/flyway-commandline/4.2.0/flyway-commandline-4.2.0.tar.gz | tar -xz \
 && mv flyway-4.2.0/ flyway/

COPY sql/ /app/flyway/sql/
COPY entrypoint.sh /app/
COPY target/scala-2.11/example-assembly-0.1.0.jar /app/app.jar

CMD ["./entrypoint.sh"]