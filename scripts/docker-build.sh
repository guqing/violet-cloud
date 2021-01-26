#!/bin/bash
image_version=${VERSION:latest}
#echo "$DOCKERHUB_TOKEN" | docker login -u "$DOCKERHUB_USERNAME" --password-stdin
# build violet-gateway
docker build -f violet-gateway/Dockerfile  -t "$DOCKERHUB_USERNAME"/violet-gateway:"$image_version" ./violet-gateway

# build violet-auth
docker build -f violet-auth/Dockerfile  -t "$DOCKERHUB_USERNAME"/violet-auth:"$image_version" ./violet-auth

#build violet-app-admin
violet_app_admin="violet-app/violet-app-admin"
docker build -f $violet_app_admin/Dockerfile -t "$DOCKERHUB_USERNAME"/violet-app-admin:"$image_version" $violet_app_admin

