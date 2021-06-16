#!/bin/sh
docker run -it --rm --name edt-maven-project -v "$PWD":/usr/src/app -v "$HOME"/.m2:/root/.m2 -w /usr/src/app \ 
	maven:3.6.3-openjdk-8-slim mvn clean install -Dmaven.test.skip=true
