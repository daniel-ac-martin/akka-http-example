NAME=akka-http-example

all: jar docker

compile:
	sbt compile

test: compile
	sbt test

jar: compile
	sbt assembly

docker: jar
	docker build -t "${NAME}" .
