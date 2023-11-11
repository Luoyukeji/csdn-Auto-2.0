FROM deploy.deepexi.com/dce/d2sf-skywalking-base:jdk8-alpine-8.5.0

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone

WORKDIR /home
USER root

ENV PROFILE="dev"
ENV LC_ALL en_US.UTF-8
ENV LANG en_US.UTF-8

ENV PARAMS=""

COPY /target/*.jar /home/app.jar

EXPOSE 80

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone
RUN echo -e 'mkdir -p ./logs/gc && java $JAVA_OPTS -jar ./app.jar --spring.profiles.active=$PROFILE $PARAMS' > entrypoint.sh

ENTRYPOINT ["sh", "entrypoint.sh"]