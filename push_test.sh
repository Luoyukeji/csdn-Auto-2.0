#!/usr/bin/env bash

#版本
VERSION=latest

mvn clean package -P test
export DOCKER_SCAN_SUGGEST=false
#需要内网vpn的,一般人访问不了
docker login deploy.deepexi.com -u datasense -p Datasense@deepexi2021
docker build -t deepexi-dsc-belle-insight-kwan:$VERSION .
docker tag deepexi-dsc-belle-insight-kwan:$VERSION deploy.deepexi.com/datasense-test/deepexi-dsc-belle-insight-kwan:$VERSION
docker push deploy.deepexi.com/datasense-test/deepexi-dsc-belle-insight-kwan:$VERSION
docker rmi deepexi-dsc-belle-insight-kwan:$VERSION deploy.deepexi.com/datasense-test/deepexi-dsc-belle-insight-kwan:$VERSION
#打包时间
echo "打包时间为="$(date +%F%n%T)