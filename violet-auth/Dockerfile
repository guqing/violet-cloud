FROM openjdk:11
MAINTAINER guqing <1484563614@qq.com>

COPY ./target/violet-auth.jar /app/violet-auth.jar

ENTRYPOINT ["java", "-Xmx256m", "-jar", "/app/violet-auth.jar"]

EXPOSE 8101