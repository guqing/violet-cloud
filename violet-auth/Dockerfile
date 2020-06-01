FROM openjdk:11
MAINTAINER guqing 1484563614@qq.com

ARG SOURCE_JAR_FILE=./target/violet-auth-0.0.1-SNAPSHOT.jar
ARG TARGET_JAR_FILE=/violet/violet-auth.jar

ARG TIME_ZONE=Asia/Shanghai

ENV TZ=${TIME_ZONE}
ENV JAVA_OPTS="-Xms256m -Xmx256m"

COPY ${SOURCE_JAR_FILE} ${TARGET_JAR_FILE}

ENTRYPOINT ["java", "${JAVA_OPTS}", "-jar", "${TARGET_JAR_FILE}"]