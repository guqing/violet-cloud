FROM openjdk:11
MAINTAINER guqing <1484563614@qq.com>

WORKDIR /app

COPY target/violet-app-admin.jar ./

ENTRYPOINT ["java", "-Xmx256m", "-jar", "violet-app-admin.jar"]

EXPOSE 8201