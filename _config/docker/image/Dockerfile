FROM adoptopenjdk/openjdk16:ubuntu-jre

LABEL base.image="adoptopenjdk/openjdk16:ubuntu-jre"
LABEL version="16.0.0"
LABEL software="java"
LABEL software.version="16.0.0"
LABEL description="assignment123 user service"

ENV TERM=xterm-256color
ENV LANG=C.UTF-8
ENV AB_OFF=true
ENV JAVA_APP_JAR=/app/user-service-0.0.1-SNAPSHOT.jar
ENV JAVA_MAX_MEM_RATIO=60

COPY _config/docker/image/files/ /
COPY build/libs/ /app/

ENTRYPOINT ["/scripts/run-java.sh"]