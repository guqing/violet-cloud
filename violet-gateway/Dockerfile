FROM openjdk:11
MAINTAINER guqing <1484563614@qq.com>

WORKDIR /app

COPY target/violet-gateway.jar ./

ENTRYPOINT ["java", "-Xmx256m", "-jar", "violet-gateway.jar"]

EXPOSE 8301