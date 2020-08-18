#!/bin/sh

echo "----------------------redis----------------"
sudo /usr/local/redis-5.0.5/src/redis-server /usr/local/redis-5.0.5/etc/redis.conf

echo "=======================elasticsearch========================"
#cd /Users/chao-kun.wu/Desktop/jiwell_shopmaoll/elasticsearch-7.5.0/bin
sudo elasticsearch
#查詢elasticsearch 1883是否己使用
#lsof -i:1883 如mosquitto 491 chao-kun.wu    5u  IPv6 0x4e5bfb35d012e249      0t0  TCP *:ibm-mqisdp (LISTEN)
#如己使用則使用kill 佔用ID = 491 如 則使用
#kill 491
#不然會無法打開 elasticsearch
