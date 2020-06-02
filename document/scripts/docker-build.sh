#!/bin/bash

VERSION=$(ls target/ | sed 's/.*violet-gateway-//' | sed 's/.jar$//')

echo "Violet cloud version: $VERSION"

echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
docker build --build-arg JAR_FILE=target/violet-gateway-$VERSION.jar -t $DOCKER_USERNAME/violet -t $DOCKER_USERNAME/violet:$VERSION .
docker images
