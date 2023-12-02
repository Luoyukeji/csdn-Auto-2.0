#!/usr/bin/env bash

#设置容器名称
CONTAINER_NAME=csdn-automatic-triplet

#镜像位置与名称
IMAGE_NAME=csdn-automatic-triplet:latest

#删除容器
docker rm -f ${CONTAINER_NAME}

#删除镜像
docker rmi ${IMAGE_NAME}

#启动容器
docker run -d --name ${CONTAINER_NAME} --privileged=true  -e PROFILE=test -w /home -p 8888:80 \
 -v $PWD/logs:/home/logs -v /home/uploads:/home/uploads --restart=always ${IMAGE_NAME}

#打印日志
docker logs -f  --tail 500  ${CONTAINER_NAME}