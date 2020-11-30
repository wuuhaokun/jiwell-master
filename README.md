### 一、架構圖

![img](https://img-blog.csdnimg.cn/20181212215151153.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2x5ajIwMThneXE=,size_16,color_FFFFFF,t_70)


### 二、包含的微服務

![img](https://img-blog.csdnimg.cn/20181212215548926.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2x5ajIwMThneXE=,size_16,color_FFFFFF,t_70)


### 2.1 網關微服務

> 架構圖

![img](https://img-blog.csdnimg.cn/20181214102311483.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2x5ajIwMThneXE=,size_16,color_FFFFFF,t_70)


不管是來自於客戶端（PC或移動端）的請求，還是服務內部調用。一切對服務的請求都會經過Zuul這個網關，然後再由網關來實現 鑑權、動態路由等等操作。 Zuul就是我們服務的統一入口。

> 配置

![img](https://img-blog.csdnimg.cn/20181214102758667.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2x5ajIwMThneXE=,size_16,color_FFFFFF,t_70)

服務網關是微服務架構中一個不可或缺的部分。通過服務網關統一向外系統提供REST API的過程中，除了具備服務路由、均衡負載功能之外，它還具備了`權限控制`等功能。為微服務架構提供了前門保護的作用，同時將權限控制這些較重的非業務邏輯內容遷移到服務路由層面，使得服務集群主體能夠具備更高的可複用性和可測試性。

> 主要功能

身份認證與安全：識別每個資源的驗證要求，並拒絕那些與要求不相符的請求。 （對jwt鑑權）

動態路由：動態地將請求路由到不同的後端集群。

負載均衡和熔斷

### 2.2 授權中心微服務

> 結合RSA的鑑權

![img](https://img-blog.csdnimg.cn/20181214104033784.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2x5ajIwMThneXE=,size_16,color_FFFFFF,t_70)

- 首先利用RSA生成公鑰和私鑰。私鑰保存在授權中心，公鑰保存在Zuul和各個微服務
- 用戶請求登錄
- 授權中心校驗，通過後用私鑰對JWT進行簽名加密
- 返回jwt給用戶
- 用戶攜帶JWT訪問
- Zuul直接通過公鑰解密JWT，進行驗證，驗證通過則放行
- 請求到達微服務，微服務直接用公鑰解析JWT，獲取用戶信息，無需訪問授權中心

> 授權中心的主要職責

1. 用戶鑑權：接收用戶的登錄請求，通過用戶中心的接口進行校驗，通過後生成JWT。使用私鑰生成JWT並返回
2. 服務鑑權：微服務間的調用不經過Zuul，會有風險，需要鑑權中心進行認證。原理與用戶鑑權類似，但邏輯稍微複雜一些（未實現）。

### 2.3 購物車微服務

> 功能需求

- 用戶可以在登錄狀態下將商品添加到購物車

放入數據庫

放入redis（採用）

- 用戶可以在未登錄狀態下將商品添加到購物車
- 放入localstorage
- 用戶可以使用購物車一起結算下單
- 用戶可以查詢自己的購物車
- 用戶可以在購物車中修改購買商品的數量。
- 用戶可以在購物車中刪除商品。
- 在購物車中展示商品優惠信息
- 提示購物車商品價格變化

> 流程圖

![img](https://img-blog.csdnimg.cn/20181214104500266.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2x5ajIwMThneXE=,size_16,color_FFFFFF,t_70)


這幅圖主要描述了兩個功能：新增商品到購物車、查詢購物車。

- 新增商品：

  - 判斷是否登錄
    - 是：則添加商品到後台Redis中
    - 否：則添加商品到本地的Localstorage

- 無論哪種新增，完成後都需要查詢購物車列表：

  - 判斷是否登錄
    - 否：直接查詢localstorage中數據並展示
    - 是：已登錄，則需要先看本地是否有數據，
      - 有：需要提交到後台添加到redis，合併數據，而後查詢
      - 否：直接去後台查詢redis，而後返回

### 2.4 評論微服務（新增）

> 功能需求

1. 用戶在確認收貨後可以對商品進行評價，每個用戶對訂單中的商品只能發布一次頂級評論，可以追評，也可以回复別人的評論。
2. 當用戶確認收貨後沒有進行手動評價時，3天后自動五星好評

> 表結構設計

parent和isparent字段是用來實現評論嵌套的。

> 實現

使用MongoDB存儲評論，基本的CRUD。

### 2.5 配置中心微服務

> 需求

在分佈式系統中，由於服務數量巨多，為了方便服務配置文件統一管理，實時更新，所以需要分佈式配置中心組件。在Spring Cloud中，有分佈式配置中心組件spring cloudconfig ，它支持配置服務放在配置服務的內存中（即本地），也支持放在遠程Git倉庫中。

使用SpringCloudBus來實現配置的自動更新。

> 組成結構

在spring cloud config 組件中，分兩個角色，一是config server，二是config client。

Config Server是一個可橫向擴展、集中式的配置服務器，它用於集中管理應用程序各個環境下的配置，默認使用Git存儲配置文件內容，也可以使用SVN存儲，或者是本地文件存存儲

Config Client是Config Server的客戶端，用於操作存儲在Config Server中的配置內容。微服務在啟動時會請求Config Server獲取配置文件的內容，請求到後再啟動容器

> 實現

創建配置中心，對Config Server進行配置，然後在其它微服務中配置Config Client。最後使用Github上的Webhooks進行配置的動態刷新，所以還要使用內網穿透工具，同時要在配置中心中添加過濾器，因為使用Webhooks提交請求時會加上一段Payload，而本地是無法解析這個Payload的，所以要將它過濾掉。

### 2.6 頁面靜態化微服務

商品詳情瀏覽量比較大，並發高，所以單獨開啟一個微服務用來展示商品詳情，並且對其進行靜態化處理，保存為靜態html文件。在用戶訪問商品詳情頁面時，讓nginx對商品請求進行監聽，指向本地靜態頁面，如果本地沒找到，才反向代理到頁面詳情微服務端口。

### 2.7 後台管理微服務

主要是對商品分類、品牌、商品的規格參數以及商品的CRUD，為後台管理提供各種接口。

### 2.8 訂單微服務

主要接口有：

- 創建訂單
- 查詢訂單
- 更新訂單狀態
- 根據訂單號生成微信付款鏈接
- 根據訂單號查詢支付狀態

### 2.9 註冊中心

> 基本架構

![img](https://img-blog.csdnimg.cn/2018121514422783.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2x5ajIwMThneXE=,size_16,color_FFFFFF,t_70)


- Eureka：就是服務註冊中心（可以是一個集群），對外暴露自己的地址
- 提供者：啟動後向Eureka註冊自己信息（地址，提供什麼服務）
- 消費者：向Eureka訂閱服務，Eureka會將對應服務的所有提供者地址列表發送給消費者，並且定期更新
- 心跳(續約)：提供者定期通過http方式向Eureka刷新自己的狀態

主要功能就是對各種服務進行管理。

### 2.10 搜索微服務

主要是對Elasticsearch的應用，將所有商品數據封裝好後添加到Elasticsearch的索引庫中，然後進行搜索過濾，查詢相應的商品信息。

### 2.11 秒殺微服務

主要接口有：

- 添加參加秒殺的商品
- 查詢秒殺商品
- 創建秒殺地址
- 驗證秒殺地址
- 秒殺

秒殺的實現及其優化：

前端：秒殺地址的隱藏、使用圖形驗證碼

後端：接口限流，使用消息隊列，調用訂單微服務執行下單操作。

TODO：需要改進~~~~~~~~~~~~~！ ！ ！ ！ ！ ！ ！ ！ ！ ！ ！ ！ ！

### 2.12 短信微服務

因為系統中不止註冊一個地方需要短信發送，因此將短信發送抽取為微服務：`jiwell-sms-service`，凡是需要的地方都可以使用。

另外，因為短信發送API調用時長的不確定性，為了提高程序的響應速度，短信發送我們都將採用異步發送方式，即：

- 短信服務監聽MQ消息，收到消息後發送短信。
- 其它服務要發送短信時，通過MQ通知短信微服務。

### 2.13 文件上傳微服務

使用分佈式文件系統FastDFS實現圖片上傳。

> FastDFS架構

![img](https://img-blog.csdnimg.cn/20181215150632856.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2x5ajIwMThneXE=,size_16,color_FFFFFF,t_70)

FastDFS兩個主要的角色：Tracker Server 和 Storage Server 。

- Tracker Server：跟踪服務器，主要負責調度storage節點與client通信，在訪問上起負載均衡的作用，和記錄storage節點的運行狀態，是連接client和storage節點的樞紐。
- Storage Server：存儲服務器，保存文件和文件的meta data（元數據），每個storage server會啟動一個單獨的線程主動向Tracker cluster中每個tracker server報告其狀態信息，包括磁盤使用情況，文件同步情況及文件上傳下載次數統計等信息
- Group：文件組，多台Storage Server的集群。上傳一個文件到同組內的一台機器上後，FastDFS會將該文件即時同步到同組內的其它所有機器上，起到備份的作用。不同組的服務器，保存的數據不同，而且相互獨立，不進行通信。
- Tracker Cluster：跟踪服務器的集群，有一組Tracker Server（跟踪服務器）組成。
- Storage Cluster ：存儲集群，有多個Group組成。

> 上傳流程

![img](https://img-blog.csdnimg.cn/2018121515081720.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2x5ajIwMThneXE=,size_16,color_FFFFFF,t_70)


1. Client通過Tracker server查找可用的Storage server。
2. Tracker server向Client返回一台可用的Storage server的IP地址和端口號。
3. Client直接通過Tracker server返回的IP地址和端口與其中一台Storage server建立連接並進行文件上傳。
4. 上傳完成，Storage server返回Client一個文件ID，文件上傳結束。

> 下載流程

![img](https://img-blog.csdnimg.cn/20181215150912300.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2x5ajIwMThneXE=,size_16,color_FFFFFF,t_70)


1. Client通過Tracker server查找要下載文件所在的的Storage server。
2. Tracker server向Client返回包含指定文件的某個Storage server的IP地址和端口號。
3. Client直接通過Tracker server返回的IP地址和端口與其中一台Storage server建立連接並指定要下載文件。
4. 下載文件成功。

### 2.14 用戶中心微服務

提供的接口：

- 檢查用戶名和手機號是否可用
- 發送短信驗證碼
- 用戶註冊
- 用戶查詢
- 修改用戶個人資料

### 三、如何啟動項目

在虛擬機中進行以下中間件的配置：

- ES：搜索
- FDFS：文件上傳
- nginx：代理FDFS中的圖片及靜態圖片
- Rabbitmq：數據同步
- Redis：緩存

並將配置文件中所有和虛擬機相關的ip進行修改

本機中需要的配置：

- nginx：前端所有請求統一代理到網關，域名的反向代理
             - host：實現域名訪問

具體請參照：https://blog.csdn.net/lyj2018gyq/article/details/83654179#2.1%20Nginx

### 四、數據庫

我的版本是最老的一般，所以數據庫可能會和新的不一致，關鍵就是在商品詳情頁面的顯示上，可以參考我`jiwell-goods-web`中的寫法，最終效果一致。

另外在數據庫中又多了幾張表：`tb_address`、`tb_seckill_order`、`tb_seckill_sku`，地址表建議保留，其他的可以連同秒殺微服務一起刪掉（如果你不需要的話）

### 五、博客地址

[傳送門](https://blog.csdn.net/lyj2018gyq/article/category/7963560)

mac 打開docker port
docker run -d -v /var/run/docker.sock:/var/run/docker.sock -p 10.140.0.3:2375:2375 bobrik/socat TCP-LISTEN:2375,fork UNIX-CONNECT:/var/run /docker.sock
在Mac OSX系統的Docker機上啟用Docker遠程API功能
https://blog.csdn.net/chszs/article/details/50650214


////////////////////////////////////////////////////////////////////////////////////////////////////////////////

### 開發環境

| 工具           | 版本號  | 下载                                                         |
| ------------- | ------ | ------------------------------------------------------------ |
| JDK           | 1.8    | https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html |
| Mysql         | 5.7    | https://www.mysql.com/                                       |
| Redis         | 5.0.5  | https://redis.io/download                                    |
| Elasticsearch | 6.8.5  | https://www.elastic.co/downloads
| MongoDb       | 4.2.6  | https://www.mongodb.com/download-center                      |
| RabbitMq      | 3.7.14 | http://www.rabbitmq.com/download.html                        |
| Nginx         | 1.17.0 | http://nginx.org/en/download.html                            |


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
訪問地址查看是否安裝成功：http://35.194.253.254:15672/
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

//local 使用
docker run -p 9200:9200 -p 9300:9300 --name elasticsearch \
-e "discovery.type=single-node" \
-e "cluster.name=elasticsearch" \
-v /Users/chao-kun.wu/Documents/jiwell_setting/mydata:/usr/share/elasticsearch/plugins \
-v /Users/chao-kun.wu/Documents/jiwell_setting/mydata:/usr/share/elasticsearch/data \
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
  
  
docker run -p 27017:27017 --name mongo \
-v /Users/chao-kun.wu/Documents/jiwell_setting/mydata/mongo/db:/data/db \
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
修改文件名稱：cd 
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
-v /mydata/conf/html:/etc/nginx/html \
-v /mydata/conf/logs:/var/log/nginx \
-v /mydata/conf/nginx:/etc/nginx \
-d nginx:1.17.0

Mac 啟動/重啟/關閉/停止ngin方式(需在root權限下 sudo su -)
先切換到root權限下
sudo su -

啟動nginx：
ngxin

重啟nginx：
nginx -s reload

關閉/停止nginx：
先查詢nginx對應端口號
ps -ef | grep nginx
501 53536 1 0 3:34下午 ?? 0:00.00 nginx: master process nginx
501 53569 53536 0 3:36下午 ?? 0:00.00 nginx: worker process
501 53611 52121 0 3:38下午 ttys000 0:00.00 grep nginx
kill nginx: master 端口號，即53536
kill -QUIT 53536

+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

#fastdfs安裝(內含nginx 所以要安裝到獨立一台VM，要不含nginx的自行上網找)

1.下載 fastdfs 從docker （使用delron/fastdfs）
docker pull delron/fastdfs

2.使用docker鏡像構建tracker容器，用於啟動跟踪服務器，起到調度的作用。
docker run -d --network=host --name tracker \
-v /mydata/fdfs/tracker:/var/fdfs delron/fastdfs tracker
上面的啟動命令是在Linux下，如果是Mac或Windows操作系統network=host（容器與主機享受相同的network namespace）會失效，此時需要指定對應的端口映射。本教程的Docker宿主機為Mac。
實際我們自行對應如下：
docker run -d --network=host --name tracker \
-v /mydata/fdfs/tracker:/var/fdfs delron/fastdfs tracker
或（建議使用上面的方法，下面的方式有時會有問題 可參考 https://github.com/happyfish100/fastdfs/issues/336）
docker run -d --name tracker -p 22122:22122 \
-v /mydata/fdfs/tracker:/var/fdfs delron/fastdfs tracker

3.使用docker鏡像構建storage容器，用於啟動存儲服務器，提供容量和備份服務。

在執行下面命令時特別需要提醒的時，對應的IP地址，需要修改為tracker服務的IP地址，由於是在同一台電腦上操作，這裡使用本機的內網地址即可，22122是tracker對應的端口。

示例，下面命令需要替換IP地址。

替換IP地址之後對應Mac下的的具體執行操作：（注意在docker中使用TRACKER_SERVER=localhost:22122會有問題)
docker run -d --network=host --name storage -e TRACKER_SERVER=10.140.0.4:22122 \
-v /mydata/fdfs/storage:/var/fdfs -e GROUP_NAME=group1 delron/fastdfs storage

其中8888為Nginx對應的訪問端口，23000是storage服務端口。

4.經過上面的步驟，tracker和storage都啟動完成。我們可以進入對應的docker容器查看一下默認的配置情況
docker exec -it ($CONTAINER ID) bash 例如：docker exec -it 2bc9f8268eda bash
其中參數值storage CONTAINER ID =“2bc9f8268eda”為我們上面看到的要進入的容器的CONTAINER ID。

先進入storage，查看其對應配置文件中關於http訪問的配置，配置文件在/etc/fdfs目錄下的storage.conf。在最後一行可以看到如下配置：

# the port of the web server on this storage server
http.server_port=8888
也就是說，這個docker鏡像中默認監聽的是8888端口，當然此配置是需要修改的。如果修改為其他端口，對應的Nginx配置也需要修改。

那麼Nginx配置在哪裡呢？也在當前容器當中。 Nginx配置文件的根目錄為：

usr/local/nginx/conf/
可以對其下的nginx.conf進行查看和修改。先來看一下默認配置：

docker 內nginx.conf設定如下：

   server {
        listen       80;
        server_name  image.ji-well.com;
        proxy_set_header X-Forwarded-Host $host;
        proxy_set_header X-Forwarded-Server $host;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        location / {
                proxy_pass http://localhost:8888;
                proxy_connect_timeout 1000;
                proxy_read_timeout 1000;
        }
   }
   server {
        listen       8888;
        server_name  localhost;
        location ~/group[0-9]/ {
            ngx_fastdfs_module;
        }
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root html;
        }
   }

5.測試一下

經過上面的步驟已經完成了FastDFS的安裝和配置，下面我們來放一張圖片驗證一下。

a.上傳一張圖至GCP VM（可參考 ），如上傳test.png 則可在/home/w_sirius/test.png
w_sirius 為我的使用者名稱，你的會不一樣的。

b.之後，將一張圖片放在本機掛載目錄/mydata/fdfs/storage下（上面命令中用到的目錄）。
mv /home/w_sirius/test.png /mydata/fdfs/storage

c.然後，進入storage容器，進入/var/fdfs目錄，執行如下命令：
docker exec -it ($CONTAINER ID) bash 

d.後執行如下命令
/usr/bin/fdfs_upload_file /etc/fdfs/client.conf test.png

其中test.png是前面存放在本機storage目錄下的圖片的名稱。

相關執行命令及目錄如下：

[root@2bc9f8268eda fdfs]# pwd
/var/fdfs
[root@2bc9f8268eda fdfs]# /usr/bin/fdfs_upload_file /etc/fdfs/client.conf test.png

可得到如下返回（你的返回值會跟我的不太一樣的）此時，文件已經上傳成功，會返回在storage存儲文件的路徑信息。
group1/M00/00/00/rBEAA18X7ZWAfAPiAABrsFVlX6U142.jpg

e.離開 容器
exit

f.在主機下執行
http://image.ji-well.com/group1/M00/00/00/rBEACV8o-XSAP8erAAAbo5WgO4U286.png

應可看到上傳的圖

如在VM 命令列中則可打

curl http://10.140.0.4:8888/group1/M00/00/00/rBEACV8o-XSAP8erAAAbo5WgO4U286.png

成功會回傳圖檔。但是二進位碼，看不出圖就是了。

參考：https://www.codenong.com/c3125732/

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
在一般模式中按下 :wq 儲存後離開 vi。 離開不儲存更動一般模式中按下 :q!

echo '{ "insecure-registries":["35.236.129.34:5000"] }' > /etc/docker/daemon.json
echo '{ "insecure-registries":["34.80.78.203:5000"] }' > /etc/docker/daemon.json
//35.236.174.253 echo '{ "insecure-registries":["34.80.78.203:5000"] }' > /etc/docker/daemon.json
修改配置後需要使用如下命令使配置生效
systemctl daemon-reload

重新啟動Docker服務
systemctl stop docker
systemctl start docker

開啟防火牆的Docker構建端口
firewall-cmd --zone=public --add-port=2375/tcp --permanent
firewall-cmd --reload

Linux 測試命令
curl  10.140.0.3:5000/v2/_catalog

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

****************** jiwell-upload部署 *********************

docker run -p 8082:8082 --name jiwell-upload \
-v /etc/localtime:/etc/localtime \
-v /mydata/app/upload/logs:/var/logs \
-d jiwell/jiwell-upload:1.0.0-SNAPSHOT

****************** jiwell-sms部署 *********************

docker run -p 8086:8086 --name jiwell-sms \
-v /etc/localtime:/etc/localtime \
-v /mydata/app/sms/logs:/var/logs \
-d jiwell/jiwell-sms:1.0.0-SNAPSHOT

****************** jiwell-fcm部署 *********************

docker run -p 10080:10080 --name jiwell-fcm \
-v /etc/localtime:/etc/localtime \
-v /mydata/app/fcm/logs:/var/logs \
-d jiwell/jiwell-fcm:1.0.0-SNAPSHOT

****************** jiwell-mail部署 *********************

docker run -p 10081:10081 --name jiwell-mail \
-v /etc/localtime:/etc/localtime \
-v /mydata/app/mail/logs:/var/logs \
-d jiwell/jiwell-mail:1.0.0-SNAPSHOT

*********** 前瑞管理程式jiwell-manage-web-master **********

Linux下的部署（前瑞管理程式jiwell-manage-web-master）
環境安裝
1.下载nodejs并安装 下载地址：https://nodejs.org/dist/v8.9.4/node-v8.9.4-x64.msi

2.於jiwell-manage-web-master 目錄下，打开控制台输入命令安装相关依赖
npm install

3.測試執行可直接使用
npm run dev

4.正式環境可用（建完後即可使用。但需注意要有webserver無法直接打開）
npm run build

4.安裝zip程式
yum install zip unzip
apt-get zip unzip

5.壓縮 dist(使用zip或其他即可)
套件名稱：zip。
壓縮：
zip -r FileName.zip DirName
解壓縮：
unzip FileName.zip

6.將dist.zip上傳到linux服務器（即mydata/nginx相關目錄）

7.解壓縮(使用該命令進行解壓操作)
unzip dist.zip

8.刪除nginx的html文件夾
rm -rf html

9.移動dist文件夾到html文件夾
mv /home/w_sirius/dist /mydata/conf/dist

10.移動dist文件夾到html文件夾
mv dist html

11.重啟nginx
docker restart nginx

如何在 CentOS 安装 node.js
https://blog.csdn.net/luckydarcy/article/details/79138650


********************* Jenkins简介 ************************

Jenkins是開源CI&CD軟件領導者，提供超過1000個插件來支持構建、部署、自動化，滿足任何項目的需要。我們可以用Jenkins來構建和部署我們的項目，比如說從我們的代碼倉庫獲取代碼，然後將我們的代碼打包成可執行的文件，之後通過遠程的ssh工具執行腳本來運行我們的項目。
參考 https://mp.weixin.qq.com/s/tQqvgSc9cHBtnqRQSbI4aw

Docker环境下的安装
1.下载Jenkins的Docker镜像
docker pull jenkins/jenkins:lts

2.在Docker容器中运行Jenkins：
docker run -p 8080:8080 -p 50000:5000 --name jenkins \
-u root \
-v /mydata/jenkins_home:/var/jenkins_home \
-d jenkins/jenkins:lts

Jenkins的配置
运行成功后访问该地址登录Jenkins，第一次登录需要输入管理员密码：http://35.194.253.254:8080
在GCP上使用要打開 8080 Port防火牆，不然會開不了

使用管理员密码进行登录，可以使用以下命令从容器启动日志中获取管理密码

从日志中获取管理员密码：

目前設定帳號：ji-well  密碼:ji-well


準備工作
ssh 產生
ssh-keygen -t rsa
後可到以下路徑下觀看
cat /root/.ssh/id_rsa.pub

cat /root/.ssh/id_rsa.pub

ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQC3mqhj0+ShiWnLdhI7EXJKJ72Wvv/XQoxuqtSAkOI2IynsgnpC+PLULB74FlchkZBtYnngqhLEusE
kNcTbObma3Xni6UtK7N4lUQCjSKg6QSqeQZ9mxuBqDbdTn3u05/asUQiHtKlZ1ifkgkjwz9FAUOMKTGb8sOmpT04HH0iFg/oqNq2MDajxBCu4AXI6nE
bJDOJ7sxkgwC4ky3mrqLAEtxTkHIrJh6IMmbpa81hoGcd7j3YTHu54EHzrqn+HAPMPR6k9ExKmXUDIlHtbRty8MSl1v0ndjVkq5Au4t93NH/e09CH1n
GzB51W2/3TFGTmHTFFAQlM8aO91KewAR7Sr root@d4e12b008090



ssh-keygen -f ~/.ssh/jenkins -C "w.sirius@gmail.com"

cat /root/.ssh/jenkins.pub


cat /root/.ssh/jenkins


1.將jiwell-sms.sh上傳至GCP VM主機上（會放在你的home/user-name目錄)
2.執行 mv /home/w_sirius/jiwell-sms.sh /mydata/sh/jiwell-sms.sh
3.给.sh脚本添加可执行权限: chmod +x ./mall-tiny-jenkins.sh
4.


ssh 建立參考 Google Cloud Platform(GCP)] SSH 連線到 VM(虛擬機器)
http://blog.ufirst.tw/google-cloud-platformgcp-ssh-連線到-vm虛擬機器/
Jenkins ssh 憑證 設定
https://juejin.im/post/6844903895823548430


為 Jenkins User 建立 SSH Keys
https://readbook-tw.gitbooks.io/git-jenkins-docker-workshop/content/jenkins/setup/ssh.html

https://my.oschina.net/u/4324548/blog/4069294/print

docker下的Jenkins安装和体验【转】

使用 Jenkins 部署 Spring Boot

原文網址：https://kknews.cc/code/x6elxxq.html
https://blog.yowko.com/jenkins-ssh-ad-ldap-git-server/

使用Jenkins配置SpringBoot的自动化构建
https://blog.csdn.net/u010870518/article/details/78733729

https://kknews.cc/zh-tw/code/x6elxxq.html

Jenkins SSH 远程执行 Shell 脚本
https://fanlychie.github.io/post/jenkins-remote-ssh.html

參考這個建立GCP
https://98goto.com/bitblog/blog/2020/02/27/我的gcp學習日誌3/

還沒看可能可以。。。。
https://zhuanlan.zhihu.com/p/40983101
=========================================================

********************* GCP instance 位置 ******************
1.instance name : mysql-data-server
  data 的位置含 mysql,mango,redis,rabbitmq,jenkins,FastDFS          
外網IP:35.194.253.254 內網IP：10.140.0.4

2.instance name : server
  data 的位置含所有微服器及rabbitMQ及redis 外網IP:34.80.79.6  內網IP：10.140.0.3
防火牆port 為 10010

=========================================================
GCP網域及子網域設定（Godaddy網域指向GCP虛擬機）主要參考 https://medium.com/@jamesshieh0510/godaddy網域指向gcp虛擬機教學-32a8d01155f5
 
 1.在GCP中建立虛擬機，並取得External IP：目前我的IP為 192.0.1.123
 2.在Cloud DNS頁面中，點選CREATE ZONE：
 3.點選ADD RECORD SET，加入設定，欄位說明如下：
   DNS Name：可設定子網域名稱，預設為主網域（空白）
   Resource Record Type：紀錄類型，A紀錄用以指向虛擬機
   IPv4 Address：欲指向的虛擬機ＩＰ，請貼上剛剛取得的虛擬機IP 
 4.這裏我們設定了4組A紀錄，依序是將ji-well.com、api.ji-well.com、manage.ji-well.com、www.ji-well.com等網域指向對應的虛擬機IP。
   其中有個NS Type（名稱伺服器）的紀錄，請將Data欄位的名稱伺服器複製下來。
   ns-cloud-e1.googledomains.com
   ns-cloud-e2.googledomains.com
   ns-cloud-e3.googledomains.com
   ns-cloud-e4.googledomains.com
 5.設定Godaddy：進入DNS管理頁面，在網域名稱伺服器區塊，可變更其設定，將剛剛複製的名稱伺服器依序填入：
 至此已完成所有的設定。約1小時設定即可使用。
 
=========================================================

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


Hearder 不見的問題
http://blog.itpub.net/69926045/viewspace-2698810/
https://github.com/gf-huanchupk/SpringCloudLearning
https://github.com/huacke/mq-kafka
 
35.236.155.182:10010

微服务架构下的自动化部署，使用Jenkins来实现
http://www.macrozheng.com/#/deploy/mall_swarm_deploy_jenkins
https://mp.weixin.qq.com/s/tQqvgSc9cHBtnqRQSbI4aw

http://{{Domain}}{{BrandItem}}/page?page=1&rows=10

fogein 沒有header的問題，目前參考第二個方式。（Hearder 不見的問題 正解是這個）
https://blog.csdn.net/lidai352710967/article/details/88680173
實作方式是寫在呼叫方。(原版即在jiwell-secskill中的OrderConfig.java己有實作)

參考資料
GCP設置DNS Server
https://snoopy30485.github.io/2018/06/20/GCP-設定DNS網域/
GCP子網域設定
https://medium.com/@jamesshieh0510/godaddy網域指向gcp虛擬機教學-32a8d01155f5

curl -X GET "http://api.ji-well.com/api/item/brand/page?page=1&rows=10"

GCP NS Type
ns-cloud-e1.googledomains.com
ns-cloud-e2.googledomains.com
ns-cloud-e3.googledomains.com
ns-cloud-e4.googledomains.com

Godaddy 網域名稱伺服器區塊
原本的兩個
ns27.domaincontrol.com
ns28.domaincontrol.com

##問題及參考
maven 上架遇到 spring-boot-maven-plugin not found的解决方案
https://www.cnblogs.com/vevy/p/12246679.html

#查詢及工具
My IP - 外部實體IP查詢程式、網路IP偵測工具
https://www.rus.net.tw/myip.php

我的IP：101.12.5.226

ssh -i ~/.ssh/id_rsa w_sirius@10.140.0.3


Docker 常用的清理 images、container 招式
把 tag 是 <none> 沒 build 成功的 images 砍掉
$ docker rmi $(docker images | grep "^<none>" | awk "{print $3}")
把所有 container 砍掉
$ docker rm `docker ps -f "status=exited"`
用 exited 的狀態碼來過濾
$ docker rm `docker ps -a -f 'exited=2'`

Managing SSH keys in metadata
https://cloud.google.com/compute/docs/instances/adding-removing-ssh-keys#createsshkeys

华夏ERP基
https://gitee.com/jishenghua/JSH_ERP

FCM推播參考 https://kknews.cc/zh-tw/tech/ljjaqz2.html

Best Practice for DevOps on GitLab and GCP
https://ithelp.ithome.com.tw/articles/10213139

properties 轉 yml工具，也支持yml轉properties
https://www.toyaml.com/index.html

SSH 及GCP SSH設定參考（正在試一直失敗）
產生SSH Key並且透過KEY進行免密碼登入
https://xenby.com/b/220-教學-產生ssh-key並且透過key進行免密碼登入

https://readbook-tw.gitbooks.io/git-jenkins-docker-workshop/content/jenkins/setup/ssh.html
Docker Jenkins Pipeline配置ssh key从gitlab拉取代码
https://blog.csdn.net/qq_38983728/article/details/85223685

b5647f6320064e64885aeeced6a5cab6cd

為 Jenkins User 建立 SSH Keys

SSH 在自動化的過程扮演很重要的角色，因此我們需要先將 jenkins 的 ssh key 產生出來

透過 jenkins user 建立 ssh key

首先透過下面指令切換到 jenkins 這個 user

sudo su - jenkins

接著透過下面指令產生 ssh key

ssh-keygen -t rsa

default 會產生在 ~/.ssh 將會有 id_rsa, id_rsa.pub 這兩個檔案

如下：
Your identification has been saved in /var/jenkins_home/.ssh/id_rsa.
Your public key has been saved in /var/jenkins_home/.ssh/id_rsa.pub.
The key fingerprint is:
SHA256:ykb0jADnrwNo8nLzvSGWeiUp2aX8Gy8DavHWvcD9CwE jenkins@d4e12b008090
The key's randomart image is:
+---[RSA 2048]----+
|  . .            |
|   +             |
|    o E          |
| .   +.=         |
|o..+ ++ S        |
|o.+.B*.o .       |
|. +=**O.o        |
| oo=+===.o       |
| ..o. +=o.o.     |
+----[SHA256]-----+
public key 如下
echo "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDenqehH1uIKFIcnjVVU+lYooKA+uzwfvxZHUvlhGHWOwf04Yh5bfqw0kpvZZ57uAufzAVlQHJjoos
      E4SaMivYYJvZnubVHTfQfecU8ghsR1BmD0dLlW59Lhtu2NNzl0b+/eo5LwCW3K0+3epBiAPYqotHYjk4zUb3EmPOYr5jVf4g6amlWFo/gZYQOHpbAVh
      51ZcdKljRI8ytQlwm0S7B8/3vFnHoA+dHf6te60GCkRJ1MQc7iIOWqDQd+eoRDZLrs53QSW5hDtW0cQCx6650Cu8/3K5CcKcTH8NXrkCpp+quBlADwL
      IFqwiZCV2f54Cb1+QByZuxyG2NoMr5loD/7 jenkins@d4e12b008090" >> ~/.ssh/authorized_keys

passwd root
New password:W8786107u

ssh-copy-id -i ~/.ssh/id_rsa.pub azureuser@myserver

private key

-----BEGIN RSA PRIVATE KEY-----
MIIEpAIBAAKCAQEA3p6noR9biChSHJ41VVPpWKKCgPrs8H78WR1L5YRh1jsH9OGI
eW36sNJKb2Wee7gLn8wFZUByY6KLBOEmjIr2GCb2Z7m1R030H3nFPIIbEdQZg9HS
5VufS4bbtjTc5dG/v3qOS8AltytPt3qQYgD2KqLR2I5OM1G9xJjzmK+Y1X+IOmpp
VhaP4GWEDh6WwFYedWXHSpY0SPMrUJcJtEuwfP97xZx6APnR3+rXutBgpESdTEHO
4iDlqg0HfnqEQ2S67Od0EluYQ7VtHEAseuudArvP9yuQnCnEx/DV65AqafqrgZQA
8CyBasImQldn+eAm9fkAcmbschtjaDK+ZaA/+wIDAQABAoIBAHSgrbVNlkhox0vS
3qILSe5zhOdJjiQYgt+053QgvJjdaDe0iCkFoxZLtU9S74plS0G2QwVelA76stYl
lmp+ypqwntqMghoPDtwGkXw3tTLL6WoT3Obn7zZEOorkeu2zyz7nV/D7g0bI3ASF
o2qUkmKX1lQbiYB9TGvYrZXKOMlwAoiJBBj8HmkTXngYd++4VkDwmpuDXZuf/Ek7
4SWmYAdUfRB7R82xotRzHcCqBV4anEkOwcS1uXizVoQ29Pp1jtX1qoTSPeINGsTa
Iz3wTSTDW2mQrsSmrO2t6btwuysHpzRzEBRSJ0NLIBZn0zMH67B1JbKGP4GBGUmM
6i0fPTECgYEA9wAkI7j/3yEcMnD9e5gIhFEL1fCMVJuKzneiIVaDMW0oOYtqiQqP
RNV4fB7T1tf0jPE1xHPTGkXzejBYt7hDNMOZtCglVc6L6h/rn7HqsG4uiFrsjuCe
vUkaxkhK9YwOLpJGVMYpc5WPKehu+vDLJMwKAG2GpcdzNUnZ7f2jSXcCgYEA5rsb
AaxML3psQwTDVkxfwCivNwGfoUMKgVBflnEGDozvwbh0iaC0Ta17z43pMl2868c2
m/qtbQmbEkgkJXCzyhsDknkvizZ2ngHTf3fHkDDBO6TOGl9rOXL5f0a8YZ7eSiwk
yzsozN0o0fV9KhpAsjsbOpjd0LZBFdYgAAkx3p0CgYEAyJsRvv2iuqrehs0j4nyA
9k4IqdI7dv/5BWU+hYsI9FyuXcYwWWr9Hy+tMkmrTYOJd+rz+0ECxATqEWQwuc3q
r3DpZdtxLzaYhic0rDfI31AtdMs783LVGfDE0SOn1bPRVNuySWnEAr8GTkgb0q08
n/8jZGOQBxZtKGt5lwP32VMCgYA9E2I+uyEfoERwKR6cBXODJkHbSa67vUdWm7Px
2tFDoMMGgJE4rTWNKlMPyfzkvDN6Ji2qdFzb9CL8X+RRlNfCtAvqBfIz46LaiJk3
sLk+zekYpLN5/7AecPTiYBMVtDwbXjwPIAXY1OItUdJkBrcBduvqQvTUfqoT4a5u
1ABDcQKBgQDcnJSolQCIXPLJtcnonlThTo9EiQKUK1otk0uq2tWTe/KFVdpi+tUe
LV2TgxmaHOLFPg55m3IjIx4r6QCgurHLV96nQ5I5Mug/ffcmceeZB4S8vAL95x3H
bZUhXdJ0tKdZjwYb9eVff5Y3pVtb5jKp71++f43EgdYDXjz9baYmXg==
-----END RSA PRIVATE KEY-----

谷歌云（GCP)开启密码与root用户登陆
https://zhuanlan.zhihu.com/p/75014852

重開SSH
1.service sshd restart
2.systemctl restart sshd.service
3.systemctl enable sshd.service

ssh 評證設定可參考如下：
如何使用Jenkins部署maven項目到遠端伺服器？只需要這簡單幾步
原文網址：https://kknews.cc/code/e64qexy.html

遠端連線ssh可成功。其他帳號不行耶？
ssh jenkins@10.140.0.6 
jenkins內的所有帳號，己可連線至GCP

https://www.itread01.com/p/159517.html
SSH遠端登入配置檔案sshd_config詳解


Jenkins 打包設定
1.將上傳的jiwell-config.sh 拷背至/mydata/sh
cp /home/w_sirius/jiwell-config.sh /mydata/sh/jiwell-config.sh
2.給.sh腳本添加可執行權限：
chmod +x ./jiwell-config.sh
3.執行.sh腳本，測試使用，可以不執行：
./jiwell-config.sh

4.在Jenkins中創建執行任務(請參考 使用Jenkins一键打包部署SpringBoot应用 https://juejin.cn/post/6844904022097264648)

執行Jenkins如出現，以下錯誤可參考如下 http://andy51002000.blogspot.com/2019/02/docker-permission-denied.html
docker: Got permission denied while trying to connect to the Docker daemon socket at unix
docker: permission denied
因為問題是出在權限不足, 如果以上方法都不管用的話, 可以手動修改權限來解決這個問題
sudo chmod 777 /var/run/docker.sock

#Jenkins 打包設定
希望達成功能：使用Jenkins直接打包上傳GCP後直接執行jar開啓伺服器（即自動打包功能）.

Jenkins端工作事項

為 Jenkins User 建立 SSH Keys
SSH 在自動化的過程扮演很重要的角色，因此我們需要先將 jenkins 的 ssh key 產生出來
透過 jenkins user 建立 ssh key
首先透過下面指令切換到 jenkins 這個 user
sudo su - jenkins
接著透過下面指令產生 ssh key
ssh-keygen -t rsa
（default 會產生在 ~/.ssh 將會有 id_rsa, id_rsa.pub 這兩個檔案)
完成後如下：
Your identification has been saved in /var/jenkins_home/.ssh/id_rsa.
Your public key has been saved in /var/jenkins_home/.ssh/id_rsa.pub.
The key fingerprint is:
SHA256:ykb0jADnrwNo8nLzvSGWeiUp2aX8Gy8DavHWvcD9CwE jenkins@d4e12b008090
The key's randomart image is:
+---[RSA 2048]----+
|  . .            |
|   +             |
|    o E          |
| .   +.=         |
|o..+ ++ S        |
|o.+.B*.o .       |
|. +=**O.o        |
| oo=+===.o       |
| ..o. +=o.o.     |
+----[SHA256]-----+

public key 如下
ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDenqehH1uIKFIcnjVVU+lYooKA+uzwfvxZHUvlhGHWOwf04Yh5bfqw0kpvZZ57uAufzAVlQHJjoos
E4SaMivYYJvZnubVHTfQfecU8ghsR1BmD0dLlW59Lhtu2NNzl0b+/eo5LwCW3K0+3epBiAPYqotHYjk4zUb3EmPOYr5jVf4g6amlWFo/gZYQOHpbAVh
51ZcdKljRI8ytQlwm0S7B8/3vFnHoA+dHf6te60GCkRJ1MQc7iIOWqDQd+eoRDZLrs53QSW5hDtW0cQCx6650Cu8/3K5CcKcTH8NXrkCpp+quBlADwL
IFqwiZCV2f54Cb1+QByZuxyG2NoMr5loD/7 jenkins@d4e12b008090

passwd root
New password:W8786107u

將產出的public key，設定到GCP上
方法如下

登入GCP → Compute Engine → 中繼資料 → SSH 金鑰(將public key內容加入即可)
可參考 GCP-ubuntu-連線進入VM 設定 https://snoopy30485.github.io/2018/06/21/GCP-ubuntu-連線進入VM/

完畢後，可以進入Jenkins機器內，執行 ssh jenkins@10.140.0.6 即列連線成功
如何進Jenkins機器內如下：
打開GCP->打開ssh連線-》進入ssh 連線後，先切換至root帳號：
sudo su - 
後執行 docker exec -it jenkins /bin/bash 進入jenkins container內 在切換到jenkins帳號如下
su - jenkins 後執行 ssh jenkins@10.140.0.6看是否成功。沒有就是設定有問題？

完成後就進行。Jenkins端設定。4.在Jenkins中創建執行任務(請參考 使用Jenkins一键打包部署SpringBoot应用 https://juejin.cn/post/6844904022097264648)
設定

執行Jenkins如出現，以下錯誤可參考如下 http://andy51002000.blogspot.com/2019/02/docker-permission-denied.html
docker: Got permission denied while trying to connect to the Docker daemon socket at unix
docker: permission denied
因為問題是出在權限不足, 如果以上方法都不管用的話, 可以手動修改權限來解決這個問題
可在連線的server上執行如下指令：
sudo chmod 777 /var/run/docker.sock

private key

-----BEGIN RSA PRIVATE KEY-----
MIIEpAIBAAKCAQEA3p6noR9biChSHJ41VVPpWKKCgPrs8H78WR1L5YRh1jsH9OGI
eW36sNJKb2Wee7gLn8wFZUByY6KLBOEmjIr2GCb2Z7m1R030H3nFPIIbEdQZg9HS
5VufS4bbtjTc5dG/v3qOS8AltytPt3qQYgD2KqLR2I5OM1G9xJjzmK+Y1X+IOmpp
VhaP4GWEDh6WwFYedWXHSpY0SPMrUJcJtEuwfP97xZx6APnR3+rXutBgpESdTEHO
4iDlqg0HfnqEQ2S67Od0EluYQ7VtHEAseuudArvP9yuQnCnEx/DV65AqafqrgZQA
8CyBasImQldn+eAm9fkAcmbschtjaDK+ZaA/+wIDAQABAoIBAHSgrbVNlkhox0vS
3qILSe5zhOdJjiQYgt+053QgvJjdaDe0iCkFoxZLtU9S74plS0G2QwVelA76stYl
lmp+ypqwntqMghoPDtwGkXw3tTLL6WoT3Obn7zZEOorkeu2zyz7nV/D7g0bI3ASF
o2qUkmKX1lQbiYB9TGvYrZXKOMlwAoiJBBj8HmkTXngYd++4VkDwmpuDXZuf/Ek7
4SWmYAdUfRB7R82xotRzHcCqBV4anEkOwcS1uXizVoQ29Pp1jtX1qoTSPeINGsTa
Iz3wTSTDW2mQrsSmrO2t6btwuysHpzRzEBRSJ0NLIBZn0zMH67B1JbKGP4GBGUmM
6i0fPTECgYEA9wAkI7j/3yEcMnD9e5gIhFEL1fCMVJuKzneiIVaDMW0oOYtqiQqP
RNV4fB7T1tf0jPE1xHPTGkXzejBYt7hDNMOZtCglVc6L6h/rn7HqsG4uiFrsjuCe
vUkaxkhK9YwOLpJGVMYpc5WPKehu+vDLJMwKAG2GpcdzNUnZ7f2jSXcCgYEA5rsb
AaxML3psQwTDVkxfwCivNwGfoUMKgVBflnEGDozvwbh0iaC0Ta17z43pMl2868c2
m/qtbQmbEkgkJXCzyhsDknkvizZ2ngHTf3fHkDDBO6TOGl9rOXL5f0a8YZ7eSiwk
yzsozN0o0fV9KhpAsjsbOpjd0LZBFdYgAAkx3p0CgYEAyJsRvv2iuqrehs0j4nyA
9k4IqdI7dv/5BWU+hYsI9FyuXcYwWWr9Hy+tMkmrTYOJd+rz+0ECxATqEWQwuc3q
r3DpZdtxLzaYhic0rDfI31AtdMs783LVGfDE0SOn1bPRVNuySWnEAr8GTkgb0q08
n/8jZGOQBxZtKGt5lwP32VMCgYA9E2I+uyEfoERwKR6cBXODJkHbSa67vUdWm7Px
2tFDoMMGgJE4rTWNKlMPyfzkvDN6Ji2qdFzb9CL8X+RRlNfCtAvqBfIz46LaiJk3
sLk+zekYpLN5/7AecPTiYBMVtDwbXjwPIAXY1OItUdJkBrcBduvqQvTUfqoT4a5u
1ABDcQKBgQDcnJSolQCIXPLJtcnonlThTo9EiQKUK1otk0uq2tWTe/KFVdpi+tUe
LV2TgxmaHOLFPg55m3IjIx4r6QCgurHLV96nQ5I5Mug/ffcmceeZB4S8vAL95x3H
bZUhXdJ0tKdZjwYb9eVff5Y3pVtb5jKp71++f43EgdYDXjz9baYmXg==
-----END RSA PRIVATE KEY-----

Jiwell Jenkins上傳打包設定：當Jenkins及GCP兩端環境設定完畢，且ssh連線可通狀況下，即可進行上架設定

jiwell-config 打包設定
    1.將jiwell-config.sh上傳到GCP上 
    2.將上傳的jiwell-config.sh 拷背至/mydata/sh 指令如下：
        cp /home/w_sirius/jiwell-config.sh /mydata/sh/jiwell-config.sh
    3.給.sh腳本添加可執行權限 如下
        chmod +x ./jiwell-config.sh 或使用chmod +x ./jiwell-*

jiwell-config 打包設定
    1.將jiwell-config.sh上傳到GCP上 
    2.將上傳的jiwell-config.sh 拷背至/mydata/sh 指令如下：
        cp /home/w_sirius/jiwell-config.sh /mydata/sh/jiwell-config.sh
    3.給.sh腳本添加可執行權限 如下
        chmod +x ./jiwell-config.sh 或使用chmod +x ./jiwell-*
        
jiwell-registry
Jenkins 打包設定
    1.將上傳的jiwell-registry.sh 拷背至/mydata/sh
    cp /home/w_sirius/jiwell-registry.sh /mydata/sh/jiwell-registry.sh
    2.給.sh腳本添加可執行權限：
    使用chmod +x ./jiwell-* 或 chmod +x ./jiwell-registry.sh
    3.執行.sh腳本，測試使用，可以不執行：
    ./jiwell-registry.sh
其他參照如上。

jiwell-api-gateway Jenkins 打包設定
    1.cp /home/w_sirius/jiwell-api-gateway.sh /mydata/sh/jiwell-api-gateway.sh
    2.chmod +x ./jiwell-*


jiwell-user-service
Jenkins 打包設定
1.cp /home/w_sirius/jiwell-user-service.sh /mydata/sh/jiwell-user-service.sh
2.chmod +x ./jiwell-* 


jiwell-authentication-service
Jenkins 打包設定
1.cp /home/w_sirius/jiwell-authentication-service.sh /mydata/sh/jiwell-authentication-service.sh
2.chmod +x ./jiwell-* 

打包遇到 server 無法註冊成功Eureka ，檢查一下 mydata/rsa有沒有rsa.pri & rsa.pub兩個檔案。沒有要想辦法生出來。
不然會無註冊到Eureka
目前暫時解法，直接從local傳上去

找到原因 jiwell-authentication-service.sh 執行docker時，沒加到rsa路徑 -v /mydata/rsa:/mydata/rsa \

#!/usr/bin/env bash
app_name='jiwell-authentication-service'
app_port='8087'
docker stop ${app_name}
echo '----stop container----'
docker rm ${app_name}
echo '----rm container----'
docker rmi `docker images | grep none | awk '{print $3}'`
echo '----rm none images----'
docker run -p ${app_port}:${app_port} --name ${app_name} \
-v /etc/localtime:/etc/localtime \
-v /mydata/app/${app_name}/logs:/var/logs \
#-v /mydata/rsa:/mydata/rsa \ 原本沒加到這行？？
-d jiwell/${app_name}:1.0.0-SNAPSHOT
echo '----start container----'

cp /home/w_sirius/rsa.pri /mydata/rsa/rsa.pri
cp /home/w_sirius/rsa.pub /mydata/rsa/rsa.pub

chmod +x ./rsa.pri
chmod +x ./rsa.pub




jiwell-item-service
Jenkins 打包設定
1.cp /home/w_sirius/jiwell-item-service.sh /mydata/sh/jiwell-item-service.sh
2.chmod +x ./jiwell-* 

jiwell-order-service
Jenkins 打包設定
1.cp /home/w_sirius/jiwell-order-service.sh /mydata/sh/jiwell-order-service.sh
2.chmod +x ./jiwell-* 

jiwell-car
Jenkins 打包設定
1.cp /home/w_sirius/jiwell-car.sh /mydata/sh/jiwell-car.sh
2.chmod +x ./jiwell-* 

jiwell-search
Jenkins 打包設定
1.cp /home/w_sirius/jiwell-search.sh /mydata/sh/jiwell-search.sh
2.chmod +x ./jiwell-* 

jiwell-mail
Jenkins 打包設定
1.cp /home/w_sirius/jiwell-mail.sh /mydata/sh/jiwell-mail.sh
2.chmod +x ./jiwell-* 

jiwell-fcm
Jenkins 打包設定
1.cp /home/w_sirius/jiwell-fcm.sh /mydata/sh/jiwell-fcm.sh
2.chmod +x ./jiwell-* 

打包戈
http://www.mydlq.club/article/16/
