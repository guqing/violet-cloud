#!/bin/bash
image_version=${VERSION:latest}
docker_username="guching"
echo "--->$docker_username---->$image_version--->$DOCKERHUB_USERNAME"
#echo "$DOCKERHUB_TOKEN" | docker login -u "$DOCKERHUB_USERNAME" --password-stdin
# build violet-gateway
docker build -f violet-gateway/Dockerfile  -t $docker_username/violet-gateway:"$image_version" ./violet-gateway

# build violet-auth
docker build -f violet-auth/Dockerfile  -t $docker_username/violet-auth:"$image_version" ./violet-auth

#build violet-app-admin
violet_app_admin="violet-app/violet-app-admin"
docker build -f $violet_app_admin/Dockerfile -t $docker_username/violet-app-admin:"$image_version" $violet_app_admin

