#!/bin/bash
cd /kwan/chatbot-vue/end/csdn-automatic-triplet/
git pull
mvn package -Dmaven.test.skip=true
yes | mv /kwan/chatbot-vue/end/vue-automatic-kwan/target/vue-automatic-kwan-0.0.1-SNAPSHOT.jar /kwan/chatbot-vue/end
cd /kwan/chatbot-vue/end
docker build -t chatbox-vue-8888 .
docker rm -f chatbox-vue-8888
docker run -d -p 8888:80 --restart=always --name chatbox-vue-8888 -v /kwan/img:/kwan/img chatbox-vue-8888