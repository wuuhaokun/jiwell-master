### 開發環境

| 工具           | 版本號  | 下载                                                         |
| ------------- | ------ | ------------------------------------------------------------ |
| JDK           | 1.8    | https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html |
| Mysql         | 5.7    | https://www.mysql.com/                                       |
| Redis         | 5.0.5  | https://redis.io/download                                    |
| Elasticsearch | 6.8.5  | https://www.elastic.co/downloads                             |
| MongoDb       | 4.2.6  | https://www.mongodb.com/download-center                      |
| RabbitMq      | 3.7.14 | http://www.rabbitmq.com/download.html                        |
| Nginx         | 1.10   | http://nginx.org/en/download.html                            |


安裝部置於GCP Linux環境下，系統為CenterOS 7.6 使用Docker容器中安裝Mysql、Redis、Nginx、RabbitMQ、Elasticsearch、Mongodb，以及SpringBoot應用部署

切換到root(安裝比較方便)
sudo su -

Docker環境安裝
安裝yum-utils：
yum install -y yum-utils device-mapper-persistent-data lvm2

為yum源添加docker倉庫位置：
yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo

安裝docker：
yum install docker-ce

啟動docker：
systemctl start docker

+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
Mysql安裝
下載mysql5.7的docker鏡像：
docker pull mysql:5.7

使用docker命令啟動：
docker run -p 3306:3306 --name mysql \
-v /mydata/mysql/log:/var/log/mysql \
-v /mydata/mysql/data:/var/lib/mysql \
-v /mydata/mysql/conf:/etc/mysql \
-e MYSQL_ROOT_PASSWORD=1234567890 \
-d mysql:5.7

參數說明
-p 3306:3306                         ：將容器的3306端口映射到主機的3306端口
-v /mydata/mysql/conf:/etc/mysql     ：將配置文件夾掛在到主機
-v /mydata/mysql/log:/var/log/mysql  ：將日誌文件夾掛載到主機
-v /mydata/mysql/data:/var/lib/mysql/：將數據文件夾掛載到主機
-e MYSQL_ROOT_PASSWORD=1234567890    ：初始化root用戶的密碼,這點設為1234567890

進入運行mysql的docker容器：
docker exec -it mysql /bin/bash

使用mysql命令打開客戶端：
mysql -uroot -p1234567890 --default-character-set=utf8

創建jiwell數據庫：
create database mall character set utf8

安裝上傳下載插件，並將docment/sql/jiwell.sql上傳到Linux服務器上：
yum -y install lrzsz

將jiwell.sql文件拷貝到mysql容器的/目錄下：
docker cp /mydata/jiwell.sql mysql:/

將sql文件導入到數據庫：
use mall;
source /jiwell.sql;

創建一個reader帳號並修改權限，使得任何ip都能訪問：
grant all privileges on *.* to 'reader' @'%' identified by '123456';
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

Redis安裝
下載redis5.0.5的docker鏡像：
docker pull redis:5.0.5

使用docker命令啟動：
docker run -p 6379:6379 --name redis \
-v /mydata/redis/data:/data \
-d redis:5.0.5 redis-server --appendonly yes

進入redis容器使用redis-cli命令進行連接：
docker exec -it redis redis-cli

+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

RabbitMQ安裝
下載rabbitmq3.7.15的docker鏡像：
docker pull rabbitmq:3.7.15

使用docker命令啟動：
docker run -d --name rabbitmq \
--publish 5671:5671 --publish 5672:5672 --publish 4369:4369 \
--publish 25672:25672 --publish 15671:15671 --publish 15672:15672 \
rabbitmq:3.7.15

進入容器並開啟管理功能：
docker exec -it rabbitmq /bin/bash
rabbitmq-plugins enable rabbitmq_management

要退回root權限下做的
開啟防火牆：
firewall-cmd --zone=public --add-port=15672/tcp --permanent
firewall-cmd --reload

訪問地址查看是否安裝成功：http://35.236.155.182:15672/
輸入賬號密碼並登錄：guest guest
創建帳號並設置其角色為管理員：/ji-well ji-well
創建一個新的虛擬host為：/ji-well
點擊/ji-well用戶進入用戶配置頁面
給/ji-well用戶配置該虛擬host的權限

+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

Elasticsearch安裝
下載elasticsearch6.8.5的docker鏡像：
docker pull elasticsearch:6.8.5

修改虛擬內存區域大小，否則會因為過小而無法啟動:
sysctl -w vm.max_map_count=262144

使用docker命令啟動：
docker run -p 9200:9200 -p 9300:9300 --name elasticsearch \
-e "discovery.type=single-node" \
-e "cluster.name=elasticsearch" \
-v /mydata/elasticsearch/plugins:/usr/share/elasticsearch/plugins \
-v /mydata/elasticsearch/data:/usr/share/elasticsearch/data \
-d elasticsearch:6.8.5

啟動時會發現/usr/share/elasticsearch/data目錄沒有訪問權限，只需要修改/mydata/elasticsearch/data目錄的權限，再重新啟動。
chmod 777 /mydata/elasticsearch/data/

安裝中文分詞器IKAnalyzer，並重新啟動：
docker exec -it elasticsearch /bin/bash
#此命令需要在容器中運行
elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v6.8.5/elasticsearch-analysis-ik-6.8.5.zip
docker restart elasticsearch

開啟防火牆：
firewall-cmd --zone=public --add-port=9200/tcp --permanent
firewall-cmd --reload

訪問會返回版本信息：http://35.236.155.182:9200/

kibana安裝
下載kibana6.8.5的docker鏡像：
docker pull kibana:6.8.5

使用docker命令啟動：
docker run --name kibana -p 5601:5601 \
--link elasticsearch:es \
-e "elasticsearch.hosts=http://es:9200" \
-d kibana:6.8.5

開啟防火牆：y
firewall-cmd --zone=public --add-port=5601/tcp --permanent
firewall-cmd --reload

訪問地址進行測試：http://35.236.155.182:5601


Mongodb安装
下载mongo4.2.6的docker镜像：
docker pull mongo:4.2.6
使用docker命令启动：
docker run -p 27017:27017 --name mongo \
-v /mydata/mongo/db:/data/db \
-d mongo:4.2.6
  
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
Nginx安裝

下載nginx1.17.6的docker鏡像：

docker pull nginx:1.17.6
從容器中拷貝nginx配置

先運行一次容器（為了拷貝配置文件）：
  docker run -p 80:80 --name nginx \
  -v /mydata/nginx/html:/usr/share/nginx/html \
  -v /mydata/nginx/logs:/var/log/nginx \
  -d nginx:1.17.6
將容器內的配置文件拷貝到指定目錄：
docker container cp nginx:/etc/nginx /mydata/nginx/
修改文件名稱：
mv nginx conf
終止並刪除容器：
docker stop nginx
docker rm nginx
使用docker命令啟動：

docker run -p 80:80 --name nginx \
-v /mydata/nginx/html:/usr/share/nginx/html \
-v /mydata/nginx/logs:/var/log/nginx \
-v /mydata/nginx/conf:/etc/nginx \
-d nginx:1.17.6


+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
本文主要介紹如何使用Maven插件將SpringBoot應用打包為Docker鏡像，並上傳到私有鏡像倉庫Docker Registry的過程。

Docker Registry
Docker Registry 2.0搭建
docker run -d -p 5000:5000 --restart=always --name registry2 registry:2
Copy to clipboardErrorCopied
如果遇到鏡像下載不下來的情況，需要修改 /etc/docker/daemon.json 文件並添加上 registry-mirrors 鍵值，然後重啟docker服務：

{
  "registry-mirrors": ["https://registry.docker-cn.com"]
}

Docker開啟遠程API
用vim編輯器修改docker.service文件

vi /usr/lib/systemd/system/docker.service

需要修改的部分：

ExecStart=/usr/bin/dockerd -H fd:// --containerd=/run/containerd/containerd.sock

修改後的部分：

ExecStart=/usr/bin/dockerd -H tcp://0.0.0.0:2375 -H unix://var/run/docker.sock

讓Docker支持http上傳鏡像

使用 vi 進入一般模式
[dywang@dywOffice tmp]$ vim test.txt
按下 i 進入編輯模式，開始編輯文字。
按下 [Esc] 按鈕回到一般模式。
在一般模式中按下 :wq 儲存後離開 vi。

echo '{ "insecure-registries":["35.236.174.253:5000"] }' > /etc/docker/daemon.json

修改配置後需要使用如下命令使配置生效
systemctl daemon-reload
Copy to clipboardErrorCopied

重新啟動Docker服務
systemctl stop docker
systemctl start docker

開啟防火牆的Docker構建端口
firewall-cmd --zone=public --add-port=2375/tcp --permanent
firewall-cmd --reload

Linux 測試命令
curl  127.0.0.1:5000/v2/_catalog
