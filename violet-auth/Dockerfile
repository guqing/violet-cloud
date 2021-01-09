FROM openjdk:11
MAINTAINER guqing <1484563614@qq.com>

COPY ./target/violet-auth.jar /app/violet-auth.jar

ENV JVM_XMS=256m JVM_XMX=256m
ENV NACOS_URL=127.0.0.1:8848

CMD java -Xms$JVM_XMS -Xmx$JVM_XMX -jar /app/violet-auth.jar -Dnacos.url=$NACOS_URL

EXPOSE 8101