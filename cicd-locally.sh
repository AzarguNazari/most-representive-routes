#!/bin/bash

JAVA_VERSION=$(java -version 2>&1 | head -1 | cut -d'"' -f2 | sed '/^1\./s///' | cut -d'.' -f1)

echo "|--------------------------------------------------------------------------|"
echo "|  Note: We expect you have already installed Docker and Docker-Compose    |"
echo "|        If you don't have docker, please install it                       |"
echo "|--------------------------------------------------------------------------|"

if [ "$JAVA_VERSION" = "17" ]
then

  # Run the tests
  ./mvnw test

  # Build Docker Image
  ./mvnw spring-boot:build-image

  ARTIFACT="$(./mvnw org.apache.maven.plugins:maven-help-plugin:3.2.0:evaluate -Dexpression=project.artifactId -q -DforceStdout)"
  VERSION="$(./mvnw org.apache.maven.plugins:maven-help-plugin:3.2.0:evaluate -Dexpression=project.version -q -DforceStdout)"

  docker tag "$ARTIFACT:$VERSION" "nazariazargul/most-representative-route:latest"

  # Push to DockerHub
  docker push "nazariazargul/most-representative-route:latest"


  # Run the container of application using docker-compose
  docker-compose up

  echo "service are running..."

else
  echo "Please change your java version to 17"
fi