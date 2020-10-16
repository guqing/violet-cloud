FROM openjdk:11
MAINTAINER guqing <1484563614@qq.com>

COPY ./target/violet-app-admin.jar /app/violet-app-admin.jar

ENTRYPOINT ["java", "-Xmx256m", "-jar", "/app/violet-app-admin.jar"]

EXPOSE 8201