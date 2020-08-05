#!/usr/bin/env bash
app_name='jiwell-config'
docker stop ${app_name}
echo '----stop container----'
docker rm ${app_name}
echo '----rm container----'
docker rmi `docker images | grep none | awk '{print $3}'`
echo '----rm none images----'
docker run -p 10011:10011 --name ${app_name} \
-v /etc/localtime:/etc/localtime \
-v /mydata/app/${app_name}/logs:/var/logs \
-v /mydata/rsa:/mydata/rsa \
-d jiwell/jiwell-config:1.0.0-SNAPSHOT
echo '----start container----'
