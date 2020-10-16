FROM openjdk:11
MAINTAINER guqing <1484563614@qq.com>

COPY ./target/violet-gateway.jar /app/violet-gateway.jar

ENTRYPOINT ["java", "-Xmx256m", "-jar", "/app/violet-gateway.jar"]

EXPOSE 8301