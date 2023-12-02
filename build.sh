#!/usr/bin/env bash

#版本
VERSION=latest

mvn clean package -P test
export DOCKER_SCAN_SUGGEST=false
docker rmi csdn-automatic-triplet:$VERSION
docker build -t csdn-automatic-triplet:$VERSION .
#打包时间
echo "打包时间为="$(date +%F%n%T)