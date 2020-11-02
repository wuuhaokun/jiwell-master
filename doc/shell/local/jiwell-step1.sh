#!/bin/sh
echo "------------------------FastDFS trackerd------------------"
#sudo service fdfs_trackerd start
sudo /usr/local/bin/fdfs_trackerd /etc/fdfs/tracker.conf start

echo "------------------------FastDFS storaged------------------"
#sudo service fdfs_storaged start
sudo /usr/local/bin/fdfs_storaged /etc/fdfs/storage.conf start

echo "==========================nginx==========================="
sudo nginx

#echo "==========================mongod==========================="
#mongod

echo "========================rabbitmq=========================="
cd /usr/local/Cellar/rabbitmq/3.8.0/ebin
sudo rabbitmq-server

#echo "----------------------redis----------------"
#/etc/init.d/redis start
#echo "----------------------redis----------------"
#sudo /usr/local/redis-5.0.5/src/redis-server /usr/local/redis-5.0.5/etc/redis.conf

#echo "=======================elasticsearch========================"
##cd /Users/chao-kun.wu/Desktop/jiwell_shopmaoll/elasticsearch-7.5.0/bin
#sudo elasticsearch
