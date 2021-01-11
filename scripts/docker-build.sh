#!/bin/bash
gateway_dir="../violet-gateway"
auth_dir="../violet-auth"
app_admin_dir="../violet-auth"

#echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
# build violet-gateway
docker build --build-arg JAR_FILE=${gateway_dir}/target/violet-gateway.jar -t guching/violet-gateway ${gateway_dir}
# build violet-auth
docker build --build-arg JAR_FILE=${auth_dir}/target/violet-auth.jar -t guching/violet-auth ${auth_dir}
#build violet-app-admin
docker build --build-arg JAR_FILE=${app_admin_dir}/target/violet-app-admin.jar -t guching/violet-app-admin ${app_admin_dir}
docker images
