FROM openjdk:11
MAINTAINER guqing <1484563614@qq.com>

WORKDIR /app

COPY target/violet-auth.jar ./

ENTRYPOINT ["java", "-Xmx256m", "-jar", "violet-auth.jar"]

EXPOSE 8101