### 一、架构图

![img](https://img-blog.csdnimg.cn/20181212215151153.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2x5ajIwMThneXE=,size_16,color_FFFFFF,t_70)


### 二、包含的微服务

![img](https://img-blog.csdnimg.cn/20181212215548926.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2x5ajIwMThneXE=,size_16,color_FFFFFF,t_70)


### 2.1 网关微服务

> 架构图

![img](https://img-blog.csdnimg.cn/20181214102311483.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2x5ajIwMThneXE=,size_16,color_FFFFFF,t_70)


不管是来自于客户端（PC或移动端）的请求，还是服务内部调用。一切对服务的请求都会经过Zuul这个网关，然后再由网关来实现 鉴权、动态路由等等操作。Zuul就是我们服务的统一入口。

> 配置

![img](https://img-blog.csdnimg.cn/20181214102758667.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2x5ajIwMThneXE=,size_16,color_FFFFFF,t_70)

服务网关是微服务架构中一个不可或缺的部分。通过服务网关统一向外系统提供REST API的过程中，除了具备服务路由、均衡负载功能之外，它还具备了`权限控制`等功能。为微服务架构提供了前门保护的作用，同时将权限控制这些较重的非业务逻辑内容迁移到服务路由层面，使得服务集群主体能够具备更高的可复用性和可测试性。

> 主要功能

身份认证与安全：识别每个资源的验证要求，并拒绝那些与要求不相符的请求。（对jwt鉴权）

动态路由：动态地将请求路由到不同的后端集群。

负载均衡和熔断

### 2.2 授权中心微服务

> 结合RSA的鉴权

![img](https://img-blog.csdnimg.cn/20181214104033784.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2x5ajIwMThneXE=,size_16,color_FFFFFF,t_70)

- 首先利用RSA生成公钥和私钥。私钥保存在授权中心，公钥保存在Zuul和各个微服务
- 用户请求登录
- 授权中心校验，通过后用私钥对JWT进行签名加密
- 返回jwt给用户
- 用户携带JWT访问
- Zuul直接通过公钥解密JWT，进行验证，验证通过则放行
- 请求到达微服务，微服务直接用公钥解析JWT，获取用户信息，无需访问授权中心

> 授权中心的主要职责

1. 用户鉴权：接收用户的登录请求，通过用户中心的接口进行校验，通过后生成JWT。使用私钥生成JWT并返回
2. 服务鉴权：微服务间的调用不经过Zuul，会有风险，需要鉴权中心进行认证。原理与用户鉴权类似，但逻辑稍微复杂一些（未实现）。

### 2.3 购物车微服务

> 功能需求

- 用户可以在登录状态下将商品添加到购物车

放入数据库

放入redis（采用）

- 用户可以在未登录状态下将商品添加到购物车
- 放入localstorage
- 用户可以使用购物车一起结算下单
- 用户可以查询自己的购物车
- 用户可以在购物车中修改购买商品的数量。
- 用户可以在购物车中删除商品。
- 在购物车中展示商品优惠信息
- 提示购物车商品价格变化

> 流程图

![img](https://img-blog.csdnimg.cn/20181214104500266.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2x5ajIwMThneXE=,size_16,color_FFFFFF,t_70)


这幅图主要描述了两个功能：新增商品到购物车、查询购物车。

- 新增商品：

  - 判断是否登录
    - 是：则添加商品到后台Redis中
    - 否：则添加商品到本地的Localstorage

- 无论哪种新增，完成后都需要查询购物车列表：

  - 判断是否登录
    - 否：直接查询localstorage中数据并展示
    - 是：已登录，则需要先看本地是否有数据，
      - 有：需要提交到后台添加到redis，合并数据，而后查询
      - 否：直接去后台查询redis，而后返回

### 2.4 评论微服务（新增）

> 功能需求

1. 用户在确认收货后可以对商品进行评价，每个用户对订单中的商品只能发布一次顶级评论，可以追评，也可以回复别人的评论。
2. 当用户确认收货后没有进行手动评价时，3天后自动五星好评

> 表结构设计

parent和isparent字段是用来实现评论嵌套的。

> 实现

使用MongoDB存储评论，基本的CRUD。

### 2.5 配置中心微服务

> 需求

在分布式系统中，由于服务数量巨多，为了方便服务配置文件统一管理，实时更新，所以需要分布式配置中心组件。在Spring Cloud中，有分布式配置中心组件spring cloudconfig ，它支持配置服务放在配置服务的内存中（即本地），也支持放在远程Git仓库中。

使用SpringCloudBus来实现配置的自动更新。

> 组成结构

在spring cloud config 组件中，分两个角色，一是config server，二是config client。

Config Server是一个可横向扩展、集中式的配置服务器，它用于集中管理应用程序各个环境下的配置，默认使用Git存储配置文件内容，也可以使用SVN存储，或者是本地文件存存储

Config Client是Config Server的客户端，用于操作存储在Config Server中的配置内容。微服务在启动时会请求Config Server获取配置文件的内容，请求到后再启动容器

> 实现

创建配置中心，对Config Server进行配置，然后在其它微服务中配置Config Client。最后使用Github上的Webhooks进行配置的动态刷新，所以还要使用内网穿透工具，同时要在配置中心中添加过滤器，因为使用Webhooks提交请求时会加上一段Payload，而本地是无法解析这个Payload的，所以要将它过滤掉。

### 2.6 页面静态化微服务

商品详情浏览量比较大，并发高，所以单独开启一个微服务用来展示商品详情，并且对其进行静态化处理，保存为静态html文件。在用户访问商品详情页面时，让nginx对商品请求进行监听，指向本地静态页面，如果本地没找到，才反向代理到页面详情微服务端口。 

### 2.7 后台管理微服务

主要是对商品分类、品牌、商品的规格参数以及商品的CRUD，为后台管理提供各种接口。 

### 2.8 订单微服务

主要接口有：

- 创建订单
- 查询订单
- 更新订单状态
- 根据订单号生成微信付款链接
- 根据订单号查询支付状态

### 2.9 注册中心

> 基本架构

![img](https://img-blog.csdnimg.cn/2018121514422783.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2x5ajIwMThneXE=,size_16,color_FFFFFF,t_70)


- Eureka：就是服务注册中心（可以是一个集群），对外暴露自己的地址
- 提供者：启动后向Eureka注册自己信息（地址，提供什么服务）
- 消费者：向Eureka订阅服务，Eureka会将对应服务的所有提供者地址列表发送给消费者，并且定期更新
- 心跳(续约)：提供者定期通过http方式向Eureka刷新自己的状态

主要功能就是对各种服务进行管理。

### 2.10 搜索微服务

主要是对Elasticsearch的应用，将所有商品数据封装好后添加到Elasticsearch的索引库中，然后进行搜索过滤，查询相应的商品信息。 

### 2.11 秒杀微服务

主要接口有：

- 添加参加秒杀的商品
- 查询秒杀商品
- 创建秒杀地址
- 验证秒杀地址
- 秒杀

秒杀的实现及其优化：

前端：秒杀地址的隐藏、使用图形验证码

后端：接口限流，使用消息队列，调用订单微服务执行下单操作。

TODO：需要改进~~~~~~~~~~~~~！！！！！！！！！！！！！

### 2.12 短信微服务

因为系统中不止注册一个地方需要短信发送，因此将短信发送抽取为微服务：`jiwell-sms-service`，凡是需要的地方都可以使用。

另外，因为短信发送API调用时长的不确定性，为了提高程序的响应速度，短信发送我们都将采用异步发送方式，即：

- 短信服务监听MQ消息，收到消息后发送短信。
- 其它服务要发送短信时，通过MQ通知短信微服务。

### 2.13 文件上传微服务

使用分布式文件系统FastDFS实现图片上传。

> FastDFS架构

![img](https://img-blog.csdnimg.cn/20181215150632856.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2x5ajIwMThneXE=,size_16,color_FFFFFF,t_70)


FastDFS两个主要的角色：Tracker Server 和 Storage Server 。

- Tracker Server：跟踪服务器，主要负责调度storage节点与client通信，在访问上起负载均衡的作用，和记录storage节点的运行状态，是连接client和storage节点的枢纽。
- Storage Server：存储服务器，保存文件和文件的meta data（元数据），每个storage server会启动一个单独的线程主动向Tracker cluster中每个tracker server报告其状态信息，包括磁盘使用情况，文件同步情况及文件上传下载次数统计等信息
- Group：文件组，多台Storage Server的集群。上传一个文件到同组内的一台机器上后，FastDFS会将该文件即时同步到同组内的其它所有机器上，起到备份的作用。不同组的服务器，保存的数据不同，而且相互独立，不进行通信。
- Tracker Cluster：跟踪服务器的集群，有一组Tracker Server（跟踪服务器）组成。
- Storage Cluster ：存储集群，有多个Group组成。

> 上传流程

![img](https://img-blog.csdnimg.cn/2018121515081720.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2x5ajIwMThneXE=,size_16,color_FFFFFF,t_70)


1. Client通过Tracker server查找可用的Storage server。
2. Tracker server向Client返回一台可用的Storage server的IP地址和端口号。
3. Client直接通过Tracker server返回的IP地址和端口与其中一台Storage server建立连接并进行文件上传。
4. 上传完成，Storage server返回Client一个文件ID，文件上传结束。

> 下载流程

![img](https://img-blog.csdnimg.cn/20181215150912300.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2x5ajIwMThneXE=,size_16,color_FFFFFF,t_70)


1. Client通过Tracker server查找要下载文件所在的的Storage server。
2. Tracker server向Client返回包含指定文件的某个Storage server的IP地址和端口号。
3. Client直接通过Tracker server返回的IP地址和端口与其中一台Storage server建立连接并指定要下载文件。
4. 下载文件成功。

### 2.14 用户中心微服务

提供的接口：

- 检查用户名和手机号是否可用
- 发送短信验证码
- 用户注册
- 用户查询
- 修改用户个人资料

### 三、如何启动项目

在虚拟机中进行以下中间件的配置：

- ES：搜索
- FDFS：文件上传
- nginx：代理FDFS中的图片及静态图片
- Rabbitmq：数据同步
- Redis：缓存

并将配置文件中所有和虚拟机相关的ip进行修改

本机中需要的配置：

- nginx：前端所有请求统一代理到网关，域名的反向代理
             - host：实现域名访问

具体请参照：https://blog.csdn.net/lyj2018gyq/article/details/83654179#2.1%20Nginx

### 四、数据库

我的版本是最老的一般，所以数据库可能会和新的不一致，关键就是在商品详情页面的显示上，可以参考我`jiwell-goods-web`中的写法，最终效果一致。

另外在数据库中又多了几张表：`tb_address`、`tb_seckill_order`、`tb_seckill_sku`，地址表建议保留，其他的可以连同秒杀微服务一起删掉（如果你不需要的话）

### 五、博客地址

[传送门](https://blog.csdn.net/lyj2018gyq/article/category/7963560)

mac 打開docker port
docker run -d -v /var/run/docker.sock:/var/run/docker.sock -p 127.0.0.1:2375:2375 bobrik/socat TCP-LISTEN:2375,fork UNIX-CONNECT:/var/run/docker.sock
在Mac OSX系统的Docker机上启用Docker远程API功能
https://blog.csdn.net/chszs/article/details/50650214


////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
//http://35.236.174.253:15672/
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

elasticsearch 的docker會無法起動， 重新執行 docker start elasticsearchID 即可

安裝中文分詞器IKAnalyzer，並重新啟動：
docker exec -it elasticsearch /bin/bash
#此命令需要在容器中運行
elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v6.8.5/elasticsearch-analysis-ik-6.8.5.zip

切換至根目錄下執行
docker restart elasticsearch

開啟防火牆：
firewall-cmd --zone=public --add-port=9200/tcp --permanent
firewall-cmd --reload

訪問會返回版本信息：http://35.194.244.197:9200/

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

訪問地址進行測試：http://35.194.244.197:5601


Mongodb安装
下载mongo4.2.6的docker镜像：
docker pull mongo:4.2.6
使用docker命令启动：
docker run -p 27017:27017 --name mongo \
-v /mydata/mongo/db:/data/db \
-d mongo:4.2.6
  
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
Nginx安裝

下載nginx1.17.0的docker鏡像：

docker pull nginx:1.17.0
從容器中拷貝nginx配置

先運行一次容器（為了拷貝配置文件）：
docker run -p 80:80 --name nginx \
-v /mydata/nginx/html:/usr/share/nginx/html \
-v /mydata/nginx/logs:/var/log/nginx \
-d nginx:1.17.0  
                   
將容器內的配置文件拷貝到指定目錄：
docker container cp nginx:/etc/nginx /mydata/nginx/
修改文件名稱：
1.切換到根目錄 
cd..
2.切換到mydata 
cd mydata
修改文件名稱：
mv nginx conf
3.上傳自己的nginx.conf檔案，應該傳到你的home目錄下的你的user name 如我的是w_sirius
4.copy nginx.conf 到 /mydata/conf/nginx/
cp /home/w_sirius/nginx.conf /mydata/conf/nginx/nginx.conf
或直接更動conf內設定

終止並刪除容器：
docker stop nginx
docker rm nginx
使用docker命令啟動：

docker run -p 80:80 --name nginx \
-v /mydata/conf/html:/usr/share/nginx/html \
-v /mydata/conf/logs:/var/log/nginx \
-v /mydata/conf/nginx:/etc/nginx \
-d nginx:1.17.0


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
//目前我們這里不需要呼叫的

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

echo '{ "insecure-registries":["35.201.214.141:5000"] }' > /etc/docker/daemon.json
echo '{ "insecure-registries":["35.236.155.182:5000"] }' > /etc/docker/daemon.json
//35.236.174.253 echo '{ "insecure-registries":["35.236.174.253:5000"] }' > /etc/docker/daemon.json
修改配置後需要使用如下命令使配置生效
systemctl daemon-reload

重新啟動Docker服務
systemctl stop docker
systemctl start docker

開啟防火牆的Docker構建端口
firewall-cmd --zone=public --add-port=2375/tcp --permanent
firewall-cmd --reload

Linux 測試命令
curl  127.0.0.1:5000/v2/_catalog

+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
jiwell程式部置及順序

****************** config部署 *********************

docker run -p 10011:10011 --name jiwell-config \
-v /etc/localtime:/etc/localtime \
-v /mydata/app/config/logs:/var/logs \
-v /mydata/rsa:/mydata/rsa \
-d jiwell/jiwell-config:1.0.0-SNAPSHOT

-v /etc/timezone:/etc/timezone \
注意：CenterOS7.2版本需要加入此行，否则容器时区和宿主机无法同步

****************** registry部署 *********************

docker run -p 10086:10086 --name jiwell-registry \
-v /etc/localtime:/etc/localtime \
-v /mydata/app/registry/logs:/var/logs \
-d jiwell/jiwell-registry:1.0.0-SNAPSHOT

****************** api-gateway部署 *********************

docker run -p 10010:10010 --name jiwell-api-gateway \
-v /etc/localtime:/etc/localtime \
-v /mydata/app/api-gateway/logs:/var/logs \
-v /mydata/rsa:/mydata/rsa \
-d jiwell/jiwell-api-gateway:1.0.0-SNAPSHOT

****************** user-service部署 *********************
docker run -p 8085:8085 --name jiwell-user-service \
-v /etc/localtime:/etc/localtime \
-v /mydata/app/user-service/logs:/var/logs \
-d jiwell/jiwell-user-service:1.0.0-SNAPSHOT

****************** authentication部署 *********************

docker run -p 8087:8087 --name jiwell-authentication-service \
-v /etc/localtime:/etc/localtime \
-v /mydata/app/authentication-service/logs:/var/logs \
-v /mydata/rsa:/mydata/rsa \
-d jiwell/jiwell-authentication-service:1.0.0-SNAPSHOT

****************** item-service部署 *********************

docker run -p 8081:8081 --name jiwell-item-service \
-v /etc/localtime:/etc/localtime \
-v /mydata/app/item-service/logs:/var/logs \
-v /mydata/rsa:/mydata/rsa \
-d jiwell/jiwell-item-service:1.0.0-SNAPSHOT

****************** order-service部署 *********************

docker run -p 8089:8089 --name jiwell-order-service \
-v /etc/localtime:/etc/localtime \
-v /mydata/app/order-service/logs:/var/logs \
-v /mydata/rsa:/mydata/rsa \
-d jiwell/jiwell-order-service:1.0.0-SNAPSHOT

****************** jiwell-cart部署 *********************

docker run -p 8088:8088 --name jiwell-cart \
-v /etc/localtime:/etc/localtime \
-v /mydata/app/cart/logs:/var/logs \
-v /mydata/rsa:/mydata/rsa \
-d jiwell/jiwell-cart:1.0.0-SNAPSHOT

****************** jiwell-search部署 *********************

docker run -p 8083:8083 --name jiwell-search \
-v /etc/localtime:/etc/localtime \
-v /mydata/app/search/logs:/var/logs \
-v /mydata/rsa:/mydata/rsa \
-d jiwell/jiwell-search:1.0.0-SNAPSHOT

****************** GCP Instance 位置 *********************

1.instance name : mysql-data-server
data 的位置含 mysql 及 mango             外網IP:35.194.244.197 內網IP：10.140.0.6

2.instance name : server
  data 的位置含所有微服器及rabbitMQ及redis 外網IP:35.236.155.182  內網IP：10.140.0.10
防火牆port 為 10010



參考資料
GCloud] 使用 gcloud 連線到 Google Cloud Platform 上的 VM
https://ephrain.net/gcp-使用-gcloud-連線到-google-cloud-platform-上的-vm/
Linux：SSH 无密码连接到 Google Cloud 实例, FileZilla连接 Google Cloud, IntelliJ IDEA连接 Google Cloud, google cloud compute instance, google cloud platform
https://justcode.ikeepstudying.com/2018/02/linux：ssh-无密码连接到-google-cloud-实例-filezilla连接-google-cloud-intellij-idea连接-google-cloud-goog/
Docker系列
https://ithelp.ithome.com.tw/articles/10190824

Docker 建立 Nginx 基礎分享
https://medium.com/@xroms123/docker-建立-nginx-基礎分享-68c0771457fb
https://codertw.com/程式語言/558518/
https://blog.csdn.net/qq_43227967/article/details/90373916?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-14.nonecase&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-14.nonecase

DNS 子網域的設定
https://ithelp.ithome.com.tw/questions/10029001

停止所有docker containers 命令
# docker stop $(docker ps -a -q) // Stop all containers
# docker rm $(docker ps -a -q) // Remove all containers


35.236.155.182:10010
