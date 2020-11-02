/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : jiwell

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 07/01/2019 14:59:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_address
-- ----------------------------
DROP TABLE IF EXISTS `tb_address`;
CREATE TABLE `tb_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '地址id',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用戶id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收貨人姓名',
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收貨人電話',
  `zip_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '郵編',
  `state` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省份',
  `city` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '市/縣',
  `district` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '區/鄉/鎮',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '詳細地址',
  `default_address` tinyint(1) NULL DEFAULT NULL COMMENT '1：默認地址 0：非默認地址',
  `label` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址標籤',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `userId`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_address
-- ----------------------------
INSERT INTO `tb_address` VALUES (1, 1, '張曲', '18834883333', '244', '台灣省', '新北市', '林口區', '文化一路一段123號14樓', 0, '燦坤3C 林口文化店-1');
INSERT INTO `tb_address` VALUES (2, 1, '張三', '18834888888', '244', '台灣省', '新北市', '林口區', '文化二路一段269號2樓', 0, '燦坤3C 林口文化店-2');
INSERT INTO `tb_address` VALUES (3, 1, '王五', '16834888600', '131', '台灣省', '桃園市', '亀山區', '復興街5號', 0, '林口長庚紀念醫院');
INSERT INTO `tb_address` VALUES (4, 1, '老王', '13454888861', '940', '台灣省', '屏東縣', '枋寮鄉', '中山路139號', 1, '枋寮醫院');

INSERT INTO `tb_address` VALUES (5, 2, '小明', '18834888888', '830', '台灣省', '高雄市', '鳳山區', '曹公路68號', 1, '鳳山車站');
INSERT INTO `tb_address` VALUES (6, 2, '小愛', '18834885587', '830', '台灣省', '高雄市', '鳳山區', '文化路59號1樓', 0, '大潤發鳳山店');

INSERT INTO `tb_address` VALUES (7, 3, '楊六', '13835888611', '946', '台灣省', '屏東縣', '恆春鎮', '墾丁路596號', 0, '墾丁國家公園遊客中心');
INSERT INTO `tb_address` VALUES (8, 3, '小玉', '12464765851', '944', '台灣省', '屏東縣', '車城鄉', '後灣路2號', 1, '國立海洋生物博物館');

-- ----------------------------
-- Table structure for tb_brand
-- ----------------------------

DROP TABLE IF EXISTS `tb_brand`;
CREATE TABLE `tb_brand` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '品牌id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '品牌名稱',
  `image` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '品牌圖片地址',
  `letter` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '品牌的首字母',
  `title` char(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '品牌主標題',
  `incategory` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '品牌內產品分類，json格式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '品牌表，一個品牌下有多個商品（spu），一對多關係' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Records of tb_brand
-- 王品牛排 參考https://licavona.pixnet.net/blog/post/47394869
-- ----------------------------
INSERT INTO `tb_brand` VALUES (1, '50嵐', 'http://img12.360buyimg.com/popshop/jfs/t2989/240/151377693/3895/30ad9044/574d36dbN262ef26d.jpg', 'N','新鮮現調茶飲','{\"1\":\"找好茶\",\"2\":\"找新鮮\",\"3\":\"找奶茶\",\"4\":\"找拿鐵\"}');
INSERT INTO `tb_brand` VALUES (2, '王品牛排', 'http://img12.360buyimg.com/popshop/jfs/t2989/240/151377693/3895/30ad9044/574d36dbN262ef26d.jpg', 'W','王品牛排-尊貴服務 為您創造甜蜜記憶','{\"1\":\"經典牛小排\",\"2\":\"王品盛宴主餐\"}');
-- INSERT INTO `tb_brand` VALUES (3, '早安美芝城', 'http://img12.360buyimg.com/popshop/jfs/t2989/240/151377693/3895/30ad9044/574d36dbN262ef26d.jpg', 'M','美味早餐-活力滿滿','{\"1\":\"美式漢堡\",\"2\":\"現烤吐司\",\"3\":\"美式貝果\",\"4\":\"嚴選飲品\",\"5\":\"點心小品\"}');

-- ----------------------------
-- Table structure for tb_buy_type
-- ----------------------------

DROP TABLE IF EXISTS `tb_buy_type`;
CREATE TABLE `tb_buy_type` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '購買方式id',
                            `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '購買方式名稱',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '購買方式對應到品牌' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Records of tb_buy_type
-- 王品牛排 參考https://licavona.pixnet.net/blog/post/47394869
-- ----------------------------
 INSERT INTO `tb_buy_type` VALUES (1, '預定點餐');
 INSERT INTO `tb_buy_type` VALUES (2, '預定訂位');

-- ----------------------------
-- Table structure for tb_category_brand
-- ----------------------------
DROP TABLE IF EXISTS `tb_buytype_brand`;
CREATE TABLE `tb_buytype_brand` (
                                     `buy_type_id` bigint(20) NOT NULL COMMENT '商品類目id',
                                     `brand_id` bigint(20) NOT NULL COMMENT '品牌id',
                                     PRIMARY KEY (`buy_type_id`, `brand_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品分類和品牌的中間表，兩者是多對多關係' ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of tb_buytype_brand
-- ----------------------------

INSERT INTO `tb_buytype_brand` VALUES (1, 1);
INSERT INTO `tb_buytype_brand` VALUES (1, 2);
INSERT INTO `tb_buytype_brand` VALUES (2, 2);
-- INSERT INTO `tb_buytype_brand` VALUES (1, 3);
-- ----------------------------
-- Table structure for tb_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_category`;
CREATE TABLE `tb_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '類目id',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '類目名稱',
  `parent_id` bigint(20) NOT NULL COMMENT '父類目id,頂級類目填0',
  `is_parent` tinyint(1) NOT NULL COMMENT '是否為父節點，0為否，1為是',
  `sort` int(4) NOT NULL COMMENT '排序指數，越小越靠前',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `key_parent_id`(`parent_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1424 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品類目表，類目和商品(spu)是一對多關係，類目與品牌是多對多關係' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_category
-- 第一層--餐飲
--    第二層--飲品           第二層--餐廳
--      第三層--0.手搖飲1.咖啡廳       第三層--0.中華電信1.台灣電信2.遠傳電信
-- ----------------------------
-- 第一層
INSERT INTO `tb_category` VALUES (14, '餐飲', 0, 1, 0);

-- 第二層
INSERT INTO `tb_category` VALUES (15, '飲品', 14, 1, 0);
--      第三層
INSERT INTO `tb_category` VALUES (16, '手搖飲', 15, 0, 0);
INSERT INTO `tb_category` VALUES (17, '咖啡廳', 15, 0, 1);

-- 第二層
INSERT INTO `tb_category` VALUES (18, '餐廳', 14, 1, 1);
--      第三層
INSERT INTO `tb_category` VALUES (19, '牛排館', 18, 0, 0);
INSERT INTO `tb_category` VALUES (20, '日式料理', 18, 0, 1);
INSERT INTO `tb_category` VALUES (21, '義大利麵', 18, 0, 2);

-- 第二層
-- INSERT INTO `tb_category` VALUES (30, '早午餐', 14, 1, 0);
--      第三層
-- INSERT INTO `tb_category` VALUES (301, '早餐', 30, 0, 0);
-- ----------------------------
-- Table structure for tb_category_brand
-- ----------------------------
DROP TABLE IF EXISTS `tb_category_brand`;
CREATE TABLE `tb_category_brand` (
  `category_id` bigint(20) NOT NULL COMMENT '商品類目id',
  `brand_id` bigint(20) NOT NULL COMMENT '品牌id',
  PRIMARY KEY (`category_id`, `brand_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品分類和品牌的中間表，兩者是多對多關係' ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of tb_category_brand
-- ----------------------------

INSERT INTO `tb_category_brand` VALUES (16, 1);
-- 手搖飲(id=16)對應到50嵐(id=1)
INSERT INTO `tb_category_brand` VALUES (19, 2);
-- 牛排館(id=19)對應到王品牛排(id=2)
INSERT INTO `tb_category_brand` VALUES (30, 3);
-- 早午餐(id=30)對應到早安美芝城(id=2)

-- ----------------------------
-- Table structure for tb_order
-- ----------------------------
DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order` (
  `order_id` bigint(20) NOT NULL COMMENT '訂單id',
  `total_pay` bigint(20) NOT NULL COMMENT '總金額，單位為分',
  `actual_pay` bigint(20) NOT NULL COMMENT '實付金額。單位:分。如:20007，表示:200元7分',
  `promotion_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '優恵方案id',
  `payment_type` tinyint(1) UNSIGNED ZEROFILL NOT NULL COMMENT '支付類型，1、在線支付，2、貨到付款，3、現場付款',
  `post_fee` bigint(20) NOT NULL COMMENT '郵費。單位:分。如:20007，表示:200元7分',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '訂單創建時間',
  `shipping_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '物流名稱',
  `shipping_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '物流單號',
  `user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用戶id',
  `buyer_message` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '買家留言',
  `buyer_nick` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '買家暱稱',
  `buyer_rate` tinyint(1) NULL DEFAULT NULL COMMENT '買家是否已經評價,0未評價，1已評價',
  `receiver_state` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '收穫地址（省）',
  `receiver_city` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '收穫地址（市）',
  `receiver_district` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '收穫地址（區/縣）',
  `receiver_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '收穫地址（街道、住址等詳細地址）',
  `receiver_mobile` varchar(12) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '收貨人手機',
  `receiver_zip` varchar(15) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '收貨人郵編',
  `receiver` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '收貨人',
  `invoice_type` int(1) NULL DEFAULT 0 COMMENT '發票類型(0無發票1普通發票，2電子發票，3增值稅發票)',
  `source_type` int(1) NULL DEFAULT 2 COMMENT '訂單來源：1:app端，2：pc端，3：M端，4：通訊端(如Line等)',
  PRIMARY KEY (`order_id`) USING BTREE,
  INDEX `create_time`(`create_time`) USING BTREE,
  INDEX `buyer_nick`(`buyer_nick`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_order
-- ----------------------------
INSERT INTO `tb_order` VALUES (1072137062385324032, 30, 30, '', 1, 0, '2020-09-10 22:32:50', NULL, NULL, '1', NULL, 'jiwell', 0, '台灣省', '新北市', '林口區', '文化二路一段269號號2樓', '18834888888', '244', '張三', 0, 2);
INSERT INTO `tb_order` VALUES (1072137913355079680, 30, 30, '', 1, 0, '2020-09-11 22:36:13', NULL, NULL, '1', NULL, 'jiwell', 0, '台灣省', '桃園市', '亀山區', '復興街5號', '16834888600', '131', '王五', 0, 2);
INSERT INTO `tb_order` VALUES (1072139076506882048, 60, 60, '', 1, 0, '2020-09-11 22:40:50', NULL, NULL, '1', NULL, 'jiwell', 0, '台灣省', '桃園市', '亀山區', '復興街5號', '16834888600', '131', '王五', 0, 2);
INSERT INTO `tb_order` VALUES (1072139922451861504, 30, 30, '', 1, 0, '2020-09-12 22:44:12', NULL, NULL, '1', NULL, 'jiwell', 0, '台灣省', '屏東縣', '枋寮鄉', '中山路139號', '13454888861', '940', '老王', 0, 2);


-- Table structure for tb_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_detail`;
CREATE TABLE `tb_order_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '訂單詳情id ',
  `order_id` bigint(20) NOT NULL COMMENT '訂單id',
  `sku_id` bigint(20) NOT NULL COMMENT 'sku商品id',
  `num` int(11) NOT NULL COMMENT '購買數量',
  `title` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品標題',
  `own_spec` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '商品動態屬性鍵值集',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '商品sku相關描述',
  `price` bigint(20) NOT NULL COMMENT '價格,單位：分',
  `image` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '商品圖片',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `key_order_id`(`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '訂單詳情表' ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of tb_order_detail


-- ----------------------------
INSERT INTO `tb_order_detail` VALUES (1, 1072137062385324032, 101, 1, '茉莉綠茶-大杯(加椰果，蘆薈)', '{\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"正常\"}', '大杯-正常冰-全糖-不加料', 30, 'http://image.ji-well.com/images/ 4/11/1524297413085.jpg');
INSERT INTO `tb_order_detail` VALUES (2, 1072137913355079680, 126, 5, '奶茶-調味奶-大杯', '{\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"正常\"}' , '大杯-正常冰-全糖-不加料', 30, 'http://image.ji-well.com/images/ 4/11/1524297413085.jpg');
INSERT INTO `tb_order_detail` VALUES (3, 1072139076506882048, 126, 2, '奶茶-調味奶-大杯', '{\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"7分糖\"}', '大杯-正常冰-全糖-不加料', 60, 'http://image.ji-well.com/ images/6/0/1524297482931.jpg');
INSERT INTO `tb_order_detail` VALUES (4, 1072139922451861504, 122, 19, '檸檬汁-大杯', '{\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"7分糖\"}', '大杯-正常冰-全糖-不加料', 30, 'http://image.ji-well.com/ images/6/0/1524297482931.jpg');
-- ----------------------------
-- Table structure for tb_order_status
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_status`;
CREATE TABLE `tb_order_status` (
  `order_id` bigint(20) NOT NULL COMMENT '訂單id',
  `status` int(1) NULL DEFAULT NULL COMMENT '狀態：1、未付款 2、已付款,未發貨 3、已發貨,未確認 4、交易成功 5、交易關閉 6、已評價',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '訂單創建時間',
  `payment_time` datetime(0) NULL DEFAULT NULL COMMENT '付款時間',
  `consign_time` datetime(0) NULL DEFAULT NULL COMMENT '發貨時間',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '交易完成時間',
  `close_time` datetime(0) NULL DEFAULT NULL COMMENT '交易關閉時間',
  `comment_time` datetime(0) NULL DEFAULT NULL COMMENT '評論時間',
  PRIMARY KEY (`order_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '訂單狀態表' ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of tb_order_status
-- ----------------------------
INSERT INTO `tb_order_status` VALUES (1072137062385324032, 6, '2020-09-10 22:32:50', '2020-09-11 22:32:50','2020-09-13 22:35:14', '2020-09-14 22:35:15', NULL, NULL);
INSERT INTO `tb_order_status` VALUES (1072137913355079680, 2, '2020-09-11 22:36:13', '2020-09-14 18:40:50', NULL, NULL, NULL, NULL);
INSERT INTO `tb_order_status` VALUES (1072139076506882048, 2, '2020-09-11 22:40:50', '2020-09-13 10:40:50', NULL, NULL, NULL, NULL);
INSERT INTO `tb_order_status` VALUES (1072139922451861504, 2, '2020-09-12 22:44:12', '2020-09-13 08:44:12', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for tb_seckill_order
-- ----------------------------
DROP TABLE IF EXISTS `tb_seckill_order`;
CREATE TABLE `tb_seckill_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用戶id\r\n',
  `order_id` bigint(20) NOT NULL COMMENT '訂單id',
  `sku_id` bigint(20) NOT NULL COMMENT '商品id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `u_id_sku_id`(`user_id`, `sku_id`) USING BTREE COMMENT '一個用戶默認只能秒殺一件商品'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_seckill_sku
-- ----------------------------
DROP TABLE IF EXISTS `tb_seckill_sku`;
CREATE TABLE `tb_seckill_sku` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT '秒殺商品id',
  `start_time` datetime(0) NOT NULL COMMENT '秒殺開始時間',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '秒殺結束時間',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '標題',
  `seckill_price` bigint(15) NULL DEFAULT NULL COMMENT '秒殺價格，單位為分',
  `image` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品圖片',
  `enable` tinyint(1) NOT NULL COMMENT '是否可以秒殺',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_seckill_sku
-- ----------------------------
INSERT INTO `tb_seckill_sku` VALUES (8, 2600248, '2019-01-01 00:00:00', '2019-01-02 02:10:00', '華為G9 青春版金色移動聯通電信4G手機雙卡雙待', 16980, 'http://image.ji-well.com/images/9/5/1524297314398.jpg', 1);
INSERT INTO `tb_seckill_sku` VALUES (9, 2600242, '2019-01-01 00:01:00', '2019-01-02 01:05:00', '華為G9 青春版白色移動聯通電信4G手機雙卡雙待', 8490, 'http://image.ji-well.com/images/9/15/1524297313793.jpg', 1);
INSERT INTO `tb_seckill_sku` VALUES (10, 2600248, '2019-01-01 00:01:00', '2019-01-02 01:05:00', '華為G9 青春版金色移動聯通電信4G手機雙卡雙待', 16980, 'http://image.ji-well.com/images/9/5/1524297314398.jpg', 1);
INSERT INTO `tb_seckill_sku` VALUES (11, 3385376, '2019-01-01 00:01:00', '2019-01-02 01:05:00', '華為G9 青春版玫瑰金移動聯通電信4G手機雙卡雙待', 25470, 'http://image.ji-well.com/images/15/15/1524297314800.jpg', 1);

-- ----------------------------
-- Table structure for tb_spu
-- ----------------------------
DROP TABLE IF EXISTS `tb_spu`;
CREATE TABLE `tb_spu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'spu id',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '標題',
  `sub_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '子標題',
  `cid1` bigint(20) NOT NULL COMMENT '1級類目id',
  `cid2` bigint(20) NOT NULL COMMENT '2級類目id',
  `cid3` bigint(20) NOT NULL COMMENT '3級類目id',
  `brand_id` bigint(20) NOT NULL COMMENT '商品所屬品牌id',
  `saleable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否上架，0下架，1上架',
  `valid` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否有效，0已刪除，1有效',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '添加時間',
  `last_update_time` datetime(0) NULL DEFAULT NULL COMMENT '最後修改時間',
  `internal_category_id` bigint(20) NOT NULL COMMENT 'internal category id(品牌內產品分類對應id)',
  `image` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '品牌圖片地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 208 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'spu表，该表描述的是一个抽象性的商品，比如 iphone8' ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of tb_spu

-- 50嵐 找新鮮系列
INSERT INTO `tb_spu` VALUES (1, '茉莉綠茶', '茉莉綠茶', 14, 15, 16, 1, 1, 1, '2020-10-15 15:55:15', '2020-10-15 15:55:15', 1,'http://image.ji-well.com/images/9/5/1524297314398.jpg');
INSERT INTO `tb_spu` VALUES (2, '阿薩姆紅茶', '阿薩姆紅茶', 14, 15, 16, 1, 1, 1, '2020-10-15 15:55:15', '2020-10-15 15:55:15', 1,'http://image.ji-well.com/images/9/5/1524297314398.jpg');
-- INSERT INTO `tb_spu` VALUES (2, '四季春青茶', '四季春青茶', 14, 15, 16, 1, 1, 1, '2020-10-15 15:55:15', '2020-10-15 15:55:15', 1,'http://image.ji-well.com/images/9/5/1524297314398.jpg');
-- INSERT INTO `tb_spu` VALUES (3, '黃金烏龍', '黃金烏龍'   , 14, 15, 16, 1, 1, 1, '2020-10-15 15:55:15', '2020-10-15 15:55:15', 1,'http://image.ji-well.com/images/9/5/1524297314398.jpg');
-- 50嵐 找好茶系列
INSERT INTO `tb_spu` VALUES (4, '檸檬汁', '檸檬汁'      , 14, 15, 16, 1, 1, 1, '2020-10-15 15:55:15', '2020-10-15 15:55:15', 2,'http://image.ji-well.com/images/9/5/1524297314398.jpg');
-- INSERT INTO `tb_spu` VALUES (5, '檸檬蜜', '檸檬蜜'      , 14, 15, 16, 1, 1, 1, '2020-10-15 15:55:15', '2020-10-15 15:55:15', 2,'http://image.ji-well.com/images/9/5/1524297314398.jpg');
-- INSERT INTO `tb_spu` VALUES (6, '金桔檸檬', '金桔檸檬'   , 14, 15, 16, 1, 1, 1, '2020-10-15 15:55:15', '2020-10-15 15:55:15', 2,'http://image.ji-well.com/images/9/5/1524297314398.jpg');
-- INSERT INTO `tb_spu` VALUES (7, '金桔檸蜜', '金桔檸蜜'   , 14, 15, 16, 1, 1, 1, '2020-10-15 15:55:15', '2020-10-15 15:55:15', 2,'http://image.ji-well.com/images/9/5/1524297314398.jpg');
-- INSERT INTO `tb_spu` VALUES (8, '檸檬梅汁', '檸檬梅汁'   , 14, 15, 16, 1, 1, 1, '2020-10-15 15:55:15', '2020-10-15 15:55:15', 2,'http://image.ji-well.com/images/9/5/1524297314398.jpg');
-- INSERT INTO `tb_spu` VALUES (9, '檸檬多多蜜', '檸檬多多蜜', 14, 15, 16, 1, 1, 1, '2020-10-15 15:55:15', '2020-10-15 15:55:15', 2,'http://image.ji-well.com/images/9/5/1524297314398.jpg');
-- 50嵐 找奶茶系列
INSERT INTO `tb_spu` VALUES (10, '奶茶', '奶茶'                 , 14, 15, 16, 1, 1, 1, '2020-10-15 15:55:15', '2020-10-15 15:55:15', 3,'http://image.ji-well.com/images/9/5/1524297314398.jpg');
-- INSERT INTO `tb_spu` VALUES (11, '奶綠', '奶綠'                 , 14, 15, 16, 1, 1, 1, '2020-10-15 15:55:15', '2020-10-15 15:55:15', 3,'http://image.ji-well.com/images/9/5/1524297314398.jpg');
-- INSERT INTO `tb_spu` VALUES (12, '烏龍奶', '烏龍奶'              , 14, 15, 16, 1, 1, 1, '2020-10-15 15:55:15', '2020-10-15 15:55:15', 3,'http://image.ji-well.com/images/9/5/1524297314398.jpg');
-- INSERT INTO `tb_spu` VALUES (13, '珍珠奶茶(小顆)', '珍珠奶茶(小顆)', 14, 15, 16, 1, 1, 1, '2020-10-15 15:55:15', '2020-10-15 15:55:15', 3,'http://image.ji-well.com/images/9/5/1524297314398.jpg');
-- INSERT INTO `tb_spu` VALUES (14, '波霸奶茶(大顆)', '波霸奶茶(大顆)', 14, 15, 16, 1, 1, 1, '2020-10-15 15:55:15', '2020-10-15 15:55:15', 3,'http://image.ji-well.com/images/9/5/1524297314398.jpg');
-- INSERT INTO `tb_spu` VALUES (15, '燕麥奶茶', '燕麥奶茶'           , 14, 15, 16, 1, 1, 1, '2020-10-15 15:55:15', '2020-10-15 15:55:15', 3,'http://image.ji-well.com/images/9/5/1524297314398.jpg');
-- INSERT INTO `tb_spu` VALUES (16, '燕麥奶青', '燕麥奶青'           , 14, 15, 16, 1, 1, 1, '2020-10-15 15:55:15', '2020-10-15 15:55:15', 3,'http://image.ji-well.com/images/9/5/1524297314398.jpg');
-- INSERT INTO `tb_spu` VALUES (17, '蜂蜜奶茶', '蜂蜜奶茶'           , 14, 15, 16, 1, 1, 1, '2020-10-15 15:55:15', '2020-10-15 15:55:15', 3,'http://image.ji-well.com/images/9/5/1524297314398.jpg');
-- 50嵐 找拿鐵系列
INSERT INTO `tb_spu` VALUES (18, '紅茶/綠茶拿鐵', '紅茶/綠茶拿鐵' , 14, 15, 16, 1, 1, 1, '2020-10-15 15:55:15', '2020-10-15 15:55:15', 4,'http://image.ji-well.com/images/9/5/1524297314398.jpg');
-- INSERT INTO `tb_spu` VALUES (19, '烏龍拿鐵', '烏龍拿鐵'         , 14, 15, 16, 1, 1, 1, '2020-10-15 15:55:15', '2020-10-15 15:55:15', 4,'http://image.ji-well.com/images/9/5/1524297314398.jpg');
-- INSERT INTO `tb_spu` VALUES (20, '珍珠紅茶拿鐵', '珍珠紅茶拿鐵'   , 14, 15, 16, 1, 1, 1, '2020-10-15 15:55:15', '2020-10-15 15:55:15', 4,'http://image.ji-well.com/images/9/5/1524297314398.jpg');

-- INSERT INTO `tb_spu` VALUES (3, '三星 Galaxy C5（SM-C5000）4GB+32GB ', '5.2英寸AMOLED屏，800+1600万像素，金属机身，小巧便捷，续航能力强大！<a href=\'https://sale.jd.com/act/T15MnRgiasWe8L.html\' target=\'_blank\'>三星S9震撼上市详情猛戳>></a>', 74, 75, 76, 15127, 1, 1, '2018-04-21 15:55:18', '2018-04-21 15:55:18');

-- 王品王品牛排套餐
INSERT INTO `tb_spu` VALUES (30, '王品牛小排', '套餐包含氣泡飲、麵包、沙拉、湯品、主餐、甜點與飲料' , 14, 18, 19, 2, 1, 1, '2020-10-15 15:55:15', '2020-10-15 15:55:15', 1,'http://image.ji-well.com/images/9/5/1524297314398.jpg');
INSERT INTO `tb_spu` VALUES (31, '王品牛小排佐犢牛肋排', '王品牛小排 或 王品牛小排佐犢牛肋排 或 王品牛小排佐海大蝦' , 14, 18, 19, 2, 1, 1, '2020-10-15 15:55:15', '2020-10-15 15:55:15', 1,'http://image.ji-well.com/images/9/5/1524297314398.jpg');

-- 王者盛宴套餐
INSERT INTO `tb_spu` VALUES (32, '酥烤牛小排(8oz)', '酥烤牛小排(8oz)' , 14, 18, 19, 2, 1, 1, '2020-10-15 15:55:15', '2020-10-15 15:55:15', 2,'http://image.ji-well.com/images/9/5/1524297314398.jpg');
INSERT INTO `tb_spu` VALUES (33, '炙烤帶骨牛小排', '炙烤帶骨牛小排' , 14, 18, 19, 1, 2, 1, '2020-10-15 15:55:15', '2020-10-15 15:55:15', 2,'http://image.ji-well.com/images/9/5/1524297314398.jpg');
INSERT INTO `tb_spu` VALUES (34, '黑松露紐約客牛排', '黑松露紐約客牛排' , 14, 18, 19, 2, 1, 1, '2020-10-15 15:55:15', '2020-10-15 15:55:15', 2,'http://image.ji-well.com/images/9/5/1524297314398.jpg');

-- 早安美芝城
INSERT INTO `tb_spu` VALUES (50, '招牌豬肉堡', '使用精選台灣頂級國產豬肉--究好豬 100%的全豬肉' , 14, 15, 30, 3, 1, 1, '2020-10-15 15:55:15', '2020-10-15 15:55:15', 3,'http://image.ji-well.com/images/9/5/1524297314398.jpg');
INSERT INTO `tb_spu` VALUES (51, '黃金蝦餅堡', '黃金蝦餅堡' , 14, 15, 30, 3, 1, 1, '2020-10-15 15:55:15', '2020-10-15 15:55:15', 3,'http://image.ji-well.com/images/9/5/1524297314398.jpg');
-- 菜單參考 https://www.macc.com.tw/product.php#2

-- ----------------------------
-- Table structure for tb_spu_detail
-- ----------------------------
DROP TABLE IF EXISTS `tb_spu_detail`;
CREATE TABLE `tb_spu_detail` (
  `spu_id` bigint(20) NOT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '商品描述信息',
  `specifications` varchar(10000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '全部規格參數數據',
  `spec_template` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '特有規格參數及可選值信息，json格式',
  `packing_list` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '包裝清單',
  `after_service` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '售後服務',
  PRIMARY KEY (`spu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_spu_detail

-- ----------------------------{\"name\":\"正常冰\",\"price\":\"0\"} 正常冰\",\"少冰\",\"去冰\,\"熱\,\"溫
INSERT INTO `tb_spu_detail` VALUES (1, '茉莉綠茶', '{\"specifications\":[{\"group\":\"主体\",\"params\":[{\"k\":\"大小選擇\",\"searchable\":false,\"global\":false,\"options\":[{\"name\":\"大\",\"price\":\"10\"},{\"name\":\"中\",\"price\":\"0\"}]},{\"k\":\"冰熱選擇\",\"searchable\":false,\"global\":false,\"options\":[{\"name\":\"正常冰\",\"price\":\"0\"},{\"name\":\"少冰\",\"price\":\"0\"},{\"name\":\"去冰\",\"price\":\"0\"},{\"name\":\"熱\",\"price\":\"0\"},{\"name\":\"溫\",\"price\":\"0\"}]},{\"k\":\"甜度選擇\",\"searchable\":false,\"global\":false,\"options\":[{\"name\":\"正常\",\"price\":\"0\"},{\"name\":\"7分糖\",\"price\":\"0\"},{\"name\":\"半糖\",\"price\":\"0\"},{\"name\":\"3分糖\",\"price\":\"0\"},{\"name\":\"無糖\",\"price\":\"0\"}]},{\"k\":\"加料選擇\",\"searchable\":false,\"global\":false,\"multiple_options\":[{\"name\":\"椰果\",\"price\":\"10\"},{\"name\":\"蘆薈\",\"price\":\"10\"}]}]}]}','{\"加料選擇\":[{\"name\":\"不加選\",\"price\":\"0\"},{\"name\":\"椰果\",\"price\":\"10\"},{\"name\":\"蘆薈\",\"price\":\"10\"}]}','', '');
INSERT INTO `tb_spu_detail` VALUES (2, '阿薩姆紅茶', '{\"specifications\":[{\"group\":\"主体\",\"params\":[{\"k\":\"大小選擇\",\"searchable\":false,\"global\":false,\"options\":[{\"name\":\"大\",\"price\":\"10\"},{\"name\":\"中\",\"price\":\"0\"}]},{\"k\":\"冰熱選擇\",\"searchable\":false,\"global\":true,\"options\":[{\"name\":\"正常冰\",\"price\":\"0\"},{\"name\":\"少冰\",\"price\":\"0\"},{\"name\":\"去冰\",\"price\":\"0\"},{\"name\":\"熱\",\"price\":\"0\"},{\"name\":\"溫\",\"price\":\"0\"}]},{\"k\":\"甜度選擇\",\"searchable\":false,\"global\":true,\"options\":[{\"name\":\"正常\",\"price\":\"0\"},{\"name\":\"7分糖\",\"price\":\"0\"},{\"name\":\"半糖\",\"price\":\"0\"},{\"name\":\"3分糖\",\"price\":\"0\"},{\"name\":\"無糖\",\"price\":\"0\"}]},{\"k\":\"加料選擇\",\"searchable\":false,\"global\":false,\"multiple_options\":[{\"name\":\"椰果\",\"price\":\"10\"},{\"name\":\"蘆薈\",\"price\":\"10\"}]}]}]}','{\"加料選擇\":[{\"name\":\"不加選\",\"price\":\"0\"},{\"name\":\"椰果\",\"price\":\"10\"},{\"name\":\"蘆薈\",\"price\":\"10\"}]}', '', '');
INSERT INTO `tb_spu_detail` VALUES (4, '檸檬汁', '{\"specifications\":[{\"group\":\"主体\",\"params\":[{\"k\":\"大小選擇\",\"searchable\":false,\"global\":false,\"options\":[{\"name\":\"大\",\"price\":\"10\"},{\"name\":\"中\",\"price\":\"0\"}]},{\"k\":\"冰熱選擇\",\"searchable\":false,\"global\":true,\"options\":[{\"name\":\"正常冰\",\"price\":\"0\"},{\"name\":\"少冰\",\"price\":\"0\"},{\"name\":\"去冰\",\"price\":\"0\"},{\"name\":\"熱\",\"price\":\"0\"},{\"name\":\"溫\",\"price\":\"0\"}]},{\"k\":\"甜度選擇\",\"searchable\":false,\"global\":true,\"options\":[{\"name\":\"正常\",\"price\":\"0\"},{\"name\":\"7分糖\",\"price\":\"0\"},{\"name\":\"半糖\",\"price\":\"0\"},{\"name\":\"3分糖\",\"price\":\"0\"},{\"name\":\"無糖\",\"price\":\"0\"}]}]','{}', '', '');
INSERT INTO `tb_spu_detail` VALUES (10, '奶茶', '{\"specifications\":[{\"group\":\"主体\",\"params\":[{\"k\":\"牛奶選擇\",\"searchable\":false,\"global\":false,\"options\":[{\"name\":\"調味奶\",\"price\":\"0\"},{\"name\":\"牛奶\",\"price\":\"10\"}]},{,{\"k\":\"大小選擇\",\"searchable\":false,\"global\":true,\"options\":[{\"name\":\"大\",\"price\":\"10\"},{\"name\":\"中\",\"price\":\"0\"}]},{\"k\":\"冰熱選擇\",\"searchable\":false,\"global\":true,\"options\":[{\"name\":\"正常冰\",\"price\":\"0\"},{\"name\":\"少冰\",\"price\":\"0\"},{\"name\":\"去冰\",\"price\":\"0\"},{\"name\":\"熱\",\"price\":\"0\"},{\"name\":\"溫\",\"price\":\"0\"}]},{\"k\":\"甜度選擇\",\"searchable\":false,\"global\":true,\"options\":[{\"name\":\"正常\",\"price\":\"0\"},{\"name\":\"7分糖\",\"price\":\"0\"},{\"name\":\"半糖\",\"price\":\"0\"},{\"name\":\"3分糖\",\"price\":\"0\"},{\"name\":\"無糖\",\"price\":\"0\"}]},{\"k\":\"加料選擇\",\"searchable\":false,\"global\":false,\"multiple_options\":[{\"name\":\"椰果\",\"price\":\"10\"},{\"name\":\"蘆薈\",\"price\":\"10\"}]}]}]}','{\"加料選擇\":[{\"name\":\"椰果\",\"price\":\"10\"},{\"name\":\"蘆薈\",\"price\":\"10\"}]}', '', '');
INSERT INTO `tb_spu_detail` VALUES (13, '珍珠奶茶', '{\"specifications\":[{\"group\":\"主体\",\"params\":[{\"k\":\"牛奶選擇\",\"searchable\":false,\"global\":false,\"options\":[{\"name\":\"調味奶\",\"price\":\"0\"},{\"name\":\"牛奶\",\"price\":\"10\"}]},{\"k\":\"粉圓選擇\",\"searchable\":false,\"global\":true,\"options\":[{\"name\":\"大顆珍珠\",\"price\":\"0\"},{\"name\":\"小顆珍珠\",\"price\":\"0\"}]},{,{\"k\":\"大小選擇\",\"searchable\":false,\"global\":true,\"options\":[{\"name\":\"大\",\"price\":\"10\"},{\"name\":\"中\",\"price\":\"0\"}]},{\"k\":\"冰熱選擇\",\"searchable\":false,\"global\":true,\"options\":[{\"name\":\"正常冰\",\"price\":\"0\"},{\"name\":\"少冰\",\"price\":\"0\"},{\"name\":\"去冰\",\"price\":\"0\"},{\"name\":\"熱\",\"price\":\"0\"},{\"name\":\"溫\",\"price\":\"0\"}]},{\"k\":\"甜度選擇\",\"searchable\":false,\"global\":true,\"options\":[{\"name\":\"正常\",\"price\":\"0\"},{\"name\":\"7分糖\",\"price\":\"0\"},{\"name\":\"半糖\",\"price\":\"0\"},{\"name\":\"3分糖\",\"price\":\"0\"},{\"name\":\"無糖\",\"price\":\"0\"}]},{\"k\":\"加料選擇\",\"searchable\":false,\"global\":false,\"multiple_options\":[{\"name\":\"椰果\",\"price\":\"10\"},{\"name\":\"蘆薈\",\"price\":\"10\"}]}]}]}','{\"加料選擇\":[{\"name\":\"椰果\",\"price\":\"10\"},{\"name\":\"蘆薈\",\"price\":\"10\"}]}', '', '');
INSERT INTO `tb_spu_detail` VALUES (18, '紅茶/綠茶拿鐵', '{\"specifications\":[{\"group\":\"主体\",\"params\":[{,{\"k\":\"大小選擇\",\"searchable\":false,\"global\":false,\"options\":[{\"name\":\"大\",\"price\":\"10\"},{\"name\":\"中\",\"price\":\"0\"}]},{\"k\":\"冰熱選擇\",\"searchable\":false,\"global\":true,\"options\":[{\"name\":\"正常冰\",\"price\":\"0\"},{\"name\":\"少冰\",\"price\":\"0\"},{\"name\":\"去冰\",\"price\":\"0\"},{\"name\":\"熱\",\"price\":\"0\"},{\"name\":\"溫\",\"price\":\"0\"}]},{\"k\":\"甜度選擇\",\"searchable\":false,\"global\":true,\"options\":[\"正常\",\"7分糖\",\"半糖\",\"3分糖\",\"無糖\"]}]}]}','{}', '', '');

-- 王品王品牛排套餐
INSERT INTO `tb_spu_detail` VALUES (30, '王品牛小排', '{\"specifications\":[{\"group\":\"主体\",\"params\":[{\"k\":\"沙拉選擇\",\"searchable\":false,\"global\":true,\"options\":[{\"name\":\"香煎干貝杏鮑菇\",\"price\":\"0\"},{\"name\":\"蔬菜棒沙拉\",\"price\":\"0\"},{\"name\":\"香蘋蘿美鮭魚\",\"price\":\"0\"},{\"name\":\"菲達起司鮮果沙拉\",\"price\":\"0\"}]},{\"k\":\"湯選擇\",\"searchable\":false,\"global\":true,\"options\":[{\"name\":\"松露蕈菇濃湯\",\"price\":\"0\"},{\"name\":\"番茄海鮮濃湯\",\"price\":\"0\"},{\"name\":\"海鮮清湯\",\"price\":\"0\"},{\"name\":\"法式牛尾清湯\",\"price\":\"0\"}]}]}]}','{}', '', '');
INSERT INTO `tb_spu_detail` VALUES (31, '王品牛小排佐犢牛肋排', '{\"specifications\":[{\"group\":\"主体\",\"params\":[{\"k\":\"沙拉選擇\",\"searchable\":false,\"global\":true,\"options\":[{\"name\":\"香煎干貝杏鮑菇\",\"price\":\"0\"},{\"name\":\"蔬菜棒沙拉\",\"price\":\"0\"},{\"name\":\"香蘋蘿美鮭魚\",\"price\":\"0\"},{\"name\":\"菲達起司鮮果沙拉\",\"price\":\"0\"}]},{\"k\":\"湯選擇\",\"searchable\":false,\"global\":true,\"options\":[{\"name\":\"松露蕈菇濃湯\",\"price\":\"0\"},{\"name\":\"番茄海鮮濃湯\",\"price\":\"0\"},{\"name\":\"海鮮清湯\",\"price\":\"0\"},{\"name\":\"法式牛尾清湯\",\"price\":\"0\"}]}]}]}','{}', '', '');
-- 王者盛宴套餐
INSERT INTO `tb_spu_detail` VALUES (32, '酥烤牛小排(8oz)', '{\"specifications\":[{\"group\":\"主体\",\"params\":[{\"k\":\"甜點選擇\",\"searchable\":false,\"global\":true,\"options\":[{\"name\":\"熔岩巧克力冰淇淋\",\"price\":\"0\"},{\"name\":\"焦糖布蕾佐莓果冰\",\"price\":\"0\"},{\"name\":\"重乳酪蛋糕\",\"price\":\"0\"}]},{\"k\":\"飲料選擇\",\"searchable\":false,\"global\":true,\"options\":[{\"name\":\"唐寧果研茶\",\"price\":\"0\"},{\"name\":\"耶加雪菲(水洗)\",\"price\":\"0\"},{\"name\":\"皇家伯爵茶\",\"price\":\"0\"}]}]}]}','{}', '', '');
INSERT INTO `tb_spu_detail` VALUES (33, '炙烤帶骨牛小排', '{\"specifications\":[{\"group\":\"主体\",\"params\":[{\"k\":\"甜點選擇\",\"searchable\":false,\"global\":true,\"options\":[{\"name\":\"熔岩巧克力冰淇淋\",\"price\":\"0\"},{\"name\":\"焦糖布蕾佐莓果冰\",\"price\":\"0\"},{\"name\":\"重乳酪蛋糕\",\"price\":\"0\"}]},{\"k\":\"飲料選擇\",\"searchable\":false,\"global\":true,\"options\":[{\"name\":\"唐寧果研茶\",\"price\":\"0\"},{\"name\":\"耶加雪菲(水洗)\",\"price\":\"0\"},{\"name\":\"皇家伯爵茶\",\"price\":\"0\"}]}]}]}','{}', '', '');
INSERT INTO `tb_spu_detail` VALUES (34, '黑松露紐約客牛排', '{\"specifications\":[{\"group\":\"主体\",\"params\":[{\"k\":\"甜點選擇\",\"searchable\":false,\"global\":true,\"options\":[{\"name\":\"熔岩巧克力冰淇淋\",\"price\":\"0\"},{\"name\":\"焦糖布蕾佐莓果冰\",\"price\":\"0\"},{\"name\":\"重乳酪蛋糕\",\"price\":\"0\"}]},{\"k\":\"飲料選擇\",\"searchable\":false,\"global\":true,\"options\":[{\"name\":\"唐寧果研茶\",\"price\":\"0\"},{\"name\":\"耶加雪菲(水洗)\",\"price\":\"0\"},{\"name\":\"皇家伯爵茶\",\"price\":\"0\"}]}]}]}','{}', '', '');

-- INSERT INTO `tb_spu_detail` VALUES (7, '<div class=\"content_tpl\"><div class=\"formwork\">\n<div class=\"formwork_img\"><img src=\"//img20.360buyimg.com/vc/jfs/t4213/90/1925235383/1883395/257851f6/58ca51bfNab5c7d9d.jpg\" /></div>\n</div></div><br/>', '[{\"group\":\"主体\",\"params\":[{\"k\":\"品牌\",\"searchable\":false,\"global\":true,\"v\":null},{\"k\":\"型号\",\"searchable\":false,\"global\":true,\"v\":\"MLA-TL10（移动定制版）\"},{\"k\":\"上市年份\",\"searchable\":false,\"global\":true,\"numerical\":true,\"unit\":\"年\",\"v\":2016.0}]},{\"group\":\"基本信息\",\"params\":[{\"k\":\"机身颜色\",\"searchable\":false,\"global\":false,\"options\":[\"铂雅金\",\"月光银\"]},{\"k\":\"机身重量（g）\",\"searchable\":false,\"global\":true,\"numerical\":true,\"unit\":\"g\",\"v\":160.0},{\"k\":\"机身材质工艺\",\"searchable\":true,\"global\":true,\"v\":null}]},{\"group\":\"操作系统\",\"params\":[{\"k\":\"操作系统\",\"searchable\":true,\"global\":true,\"v\":\"Android\"}]},{\"group\":\"主芯片\",\"params\":[{\"k\":\"CPU品牌\",\"searchable\":true,\"global\":true,\"v\":\"骁龙（Snapdragon)\"},{\"k\":\"CPU型号\",\"searchable\":false,\"global\":true,\"v\":\"骁龙625（MSM8953）\"},{\"k\":\"CPU核数\",\"searchable\":true,\"global\":true,\"v\":\"八核\"},{\"k\":\"CPU频率\",\"searchable\":true,\"global\":true,\"numerical\":true,\"unit\":\"GHz\",\"v\":2.0}]},{\"group\":\"存储\",\"params\":[{\"k\":\"内存\",\"searchable\":true,\"global\":false,\"numerical\":false,\"unit\":\"GB\",\"options\":[\"3GB\"]},{\"k\":\"机身存储\",\"searchable\":true,\"global\":false,\"numerical\":false,\"unit\":\"GB\",\"options\":[\"32GB\"]}]},{\"group\":\"屏幕\",\"params\":[{\"k\":\"主屏幕尺寸（英寸）\",\"searchable\":true,\"global\":true,\"numerical\":true,\"unit\":\"英寸\",\"v\":5.5},{\"k\":\"分辨率\",\"searchable\":false,\"global\":true,\"v\":\"1920*1080(FHD)\"}]},{\"group\":\"摄像头\",\"params\":[{\"k\":\"前置摄像头\",\"searchable\":true,\"global\":true,\"numerical\":true,\"unit\":\"万\",\"v\":800.0},{\"k\":\"后置摄像头\",\"searchable\":true,\"global\":true,\"numerical\":true,\"unit\":\"万\",\"v\":1600.0}]},{\"group\":\"电池信息\",\"params\":[{\"k\":\"电池容量（mAh）\",\"searchable\":true,\"global\":true,\"numerical\":true,\"unit\":\"mAh\",\"v\":3340.0}]}]', '{\"机身颜色\":[\"铂雅金\",\"月光银\"],\"内存\":[\"3GB\"],\"机身存储\":[\"32GB\"]}', '手机X1、耳机X1、快速指南X1、USB 线X1、充电器X1、卡针X1', '本产品全国联保，享受三包服务，质保期为：一年质保');


-- ----------------------------
-- Table structure for tb_sku
-- ----------------------------
DROP TABLE IF EXISTS `tb_sku`;
CREATE TABLE `tb_sku` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'sku id',
  `spu_id` bigint(20) NOT NULL COMMENT 'spu id',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品標題',
  `images` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '商品的圖片，多個圖片以‘,’分割',
  `price` bigint(15) NOT NULL DEFAULT 0 COMMENT '銷售價格，單位為分',
  `indexes` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '特有規格屬性在spu屬性模板中的對應下標組合',
  `own_spec` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'sku的特有規格參數鍵值對，json格式，反序列化時請使用linkedHashMap，保證有序',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'sku相關描述',
  `enable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否有效，0無效，1有效',
  `create_time` datetime(0) NOT NULL COMMENT '添加時間',
  `last_update_time` datetime(0) NOT NULL COMMENT '最後修改時間',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `key_spu_id`(`spu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5000 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'sku表,該表表示具體的商品實體,如黑色的 64g的iphone 8' ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of tb_sku
-- ----------------------------

-- 冰熱選擇不同
-- 大小選擇 ＋ 冰熱選擇 + 甜度選擇 + 加料選擇
-- 茉莉綠茶
-- INSERT INTO `tb_sku` VALUES (101, 1, '茉莉綠茶-大杯', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_00', '{\"大小選擇\":\"大\",\"加料選擇\":\"\"}', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (102, 1, '茉莉綠茶-大杯(加蘆薈)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_01', '{\"大小選擇\":\"大\",\"加料選擇\":\"蘆薈\"}', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (103, 1, '茉莉綠茶-大杯(加椰果)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_10', '{\"大小選擇\":\"大\",\"加料選擇\":\"椰果\"}', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (104, 1, '茉莉綠茶-大杯(加椰果，蘆薈)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_11', '{\"大小選擇\":\"大\",\"加料選擇\":\"椰果＋蘆薈\"}', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

INSERT INTO `tb_sku` VALUES (101, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_0_0_00', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"正常\",\"加料選擇\":\"\"}', '大杯-正常冰-全糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (102, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_1_0_00', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"正常\",\"加料選擇\":\"\"}', '大杯-少冰-全糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (103, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_2_0_00', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"正常\",\"加料選擇\":\"\"}', '大杯-去冰-全糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (104, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_3_0_00', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"正常\",\"加料選擇\":\"\"}', '大杯-熱-全糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (105, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_4_0_00', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"正常\",\"加料選擇\":\"\"}' , '大杯-溫-全糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

INSERT INTO `tb_sku` VALUES (106, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_0_1_00', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"\"}','大杯-正常冰-7分糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (107, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_1_1_00', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"\"}','大杯-少冰-7分糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (108, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_2_1_00', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"\"}','大杯-去冰-7分糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (109, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_3_1_00', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"\"}','大杯-熱-7分糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (110, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_4_1_00', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"\"}','大杯-溫-7分糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

INSERT INTO `tb_sku` VALUES (111, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_0_2_00', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"\"}','大杯-正常冰-半糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (112, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_1_2_00', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"\"}', '大杯-少冰-半糖-不加料',1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (113, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_2_2_00', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"\"}', '大杯-去冰-半糖-不加料',1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (114, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_3_2_00', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"\"}', '大杯-熱-半糖-不加料',1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (115, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_4_2_00', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"\"}', '大杯-溫-半糖-不加料',1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

INSERT INTO `tb_sku` VALUES (116, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_0_3_00', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"\"}','大杯-正常冰-3分糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (117, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_1_3_00', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"\"}','大杯-少冰-3分糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (118, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_2_3_00', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"\"}', '大杯-去冰-3分糖-不加料',1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (119, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_3_3_00', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"\"}', '大杯-熱-3分糖-不加料',1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (120, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_4_3_00', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"\"}', '大杯-溫-3分糖-不加料',1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');


INSERT INTO `tb_sku` VALUES (121, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_0_4_00', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"\"}', '大杯-正常冰-無糖-不加料',1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (122, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_1_4_00', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"\"}', '大杯-少冰-無糖-不加料',1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (123, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_2_4_00', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"\"}', '大杯-去冰-無糖-不加料',1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (124, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_3_4_00', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"\"}', '大杯-熱-無糖-不加料',1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (125, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_4_4_00', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"\"}', '大杯-溫-無糖-不加料',1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');


INSERT INTO `tb_sku` VALUES (126, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_0_0_10', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"正常\",\"加料選擇\":\"椰果\"}', '大杯-正常冰-全糖-加椰果',1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (127, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_1_0_10', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"正常\",\"加料選擇\":\"椰果\"}', '大杯-少冰-全糖-加椰果',1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (128, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_2_0_10', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"正常\",\"加料選擇\":\"椰果\"}', '大杯-去冰-全糖-加椰果',1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (129, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_3_0_10', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"正常\",\"加料選擇\":\"椰果\"}', '大杯-熱-全糖-加椰果',1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (130, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_4_0_10', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"正常\",\"加料選擇\":\"椰果\"}', '大杯-溫-全糖-加椰果',1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

INSERT INTO `tb_sku` VALUES (131, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_0_1_10', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"椰果\"}', '大杯-正常冰-7分糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (132, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_1_1_10', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"椰果\"}', '大杯-少冰-7分糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (133, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_2_1_10', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"椰果\"}', '大杯-去冰-7分糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (134, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_3_1_10', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"椰果\"}', '大杯-熱-7分糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (135, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_4_1_10', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"椰果\"}', '大杯-溫-7分糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

INSERT INTO `tb_sku` VALUES (136, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_0_2_10', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"椰果\"}', '大杯-正常冰-半糖-加椰果',1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (137, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_1_2_10', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"椰果\"}', '大杯-少冰-半糖-加椰果',1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (138, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_2_2_10', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"椰果\"}', '大杯-去冰-半糖-加椰果',1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (139, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_3_2_10', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"椰果\"}', '大杯-熱-半糖-加椰果',1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (140, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_4_2_10', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"椰果\"}', '大杯-溫-半糖-加椰果',1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

INSERT INTO `tb_sku` VALUES (141, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_0_3_10', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"椰果\"}', '大杯-正常冰-3分糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (142, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_1_3_10', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"椰果\"}', '大杯-少冰-3分糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (143, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_2_3_10', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"椰果\"}', '大杯-去冰-3分糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (144, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_3_3_10', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"椰果\"}', '大杯-熱-3分糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (145, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_4_3_10', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"椰果\"}', '大杯-溫-3分糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');


INSERT INTO `tb_sku` VALUES (146, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_0_4_10', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"椰果\"}', '大杯-正常冰-無糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (147, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_1_4_10', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"椰果\"}', '大杯-少冰-無糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (148, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_2_4_10', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"椰果\"}', '大杯-去冰-無糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (149, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_3_4_10', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"椰果\"}', '大杯-熱-無糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (150, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_4_4_10', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"椰果\"}', '大杯-溫-無糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');



INSERT INTO `tb_sku` VALUES (151, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_0_0_01', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"正常\",\"加料選擇\":\"蘆薈\"}', '大杯-正常冰-全糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (152, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_1_0_01', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"正常\",\"加料選擇\":\"蘆薈\"}', '大杯-少冰-全糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (153, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_2_0_01', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"正常\",\"加料選擇\":\"蘆薈\"}', '大杯-去冰-全糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (154, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_3_0_01', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"正常\",\"加料選擇\":\"蘆薈\"}', '大杯-熱-全糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (155, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_4_0_01', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"正常\",\"加料選擇\":\"蘆薈\"}', '大杯-溫-全糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

INSERT INTO `tb_sku` VALUES (156, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_0_1_01', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"蘆薈\"}', '大杯-正常冰-7分糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (157, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_1_1_01', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"蘆薈\"}', '大杯-少冰-7分糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (158, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_2_1_01', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"蘆薈\"}', '大杯-去冰-7分糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (159, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_3_1_01', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"蘆薈\"}', '大杯-熱-7分糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (160, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_4_1_01', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"蘆薈\"}', '大杯-溫-7分糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

INSERT INTO `tb_sku` VALUES (161, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_0_2_01', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"蘆薈\"}', '大杯-正常冰-半糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (162, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_1_2_01', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"蘆薈\"}', '大杯-少冰-半糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (163, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_2_2_01', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"蘆薈\"}', '大杯-去冰-半糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (164, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_3_2_01', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"蘆薈\"}', '大杯-熱-半糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (165, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_4_2_01', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"蘆薈\"}', '大杯-溫-半糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

INSERT INTO `tb_sku` VALUES (166, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_0_3_01', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"蘆薈\"}', '大杯-正常冰-3分糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (167, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_1_3_01', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"蘆薈\"}', '大杯-少冰-3分糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (168, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_2_3_01', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"蘆薈\"}', '大杯-去冰-3分糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (169, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_3_3_01', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"蘆薈\"}', '大杯-熱-3分糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (170, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_4_3_01', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"蘆薈\"}', '大杯-溫-3分糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');


INSERT INTO `tb_sku` VALUES (171, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_0_4_01', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"蘆薈\"}', '大杯-正常冰-無糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (172, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_1_4_01', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"蘆薈\"}', '大杯-少冰-無糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (173, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_2_4_01', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"蘆薈\"}', '大杯-去冰-無糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (174, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_3_4_01', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"蘆薈\"}', '大杯-熱-無糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (175, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_4_4_01', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"蘆薈\"}', '大杯-溫-無糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

INSERT INTO `tb_sku` VALUES (176, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_0_0_11', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"正常\",\"加料選擇\":\"椰果＋蘆薈\"}', '大杯-正常冰-全糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (177, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_1_0_11', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"正常\",\"加料選擇\":\"椰果＋蘆薈\"}', '大杯-少冰-全糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (178, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_2_0_11', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"正常\",\"加料選擇\":\"椰果＋蘆薈\"}', '大杯-去冰-全糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (179, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_3_0_11', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"正常\",\"加料選擇\":\"椰果＋蘆薈\"}', '大杯-熱-全糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (180, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_4_0_11', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"正常\",\"加料選擇\":\"椰果＋蘆薈\"}', '大杯-溫-全糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

INSERT INTO `tb_sku` VALUES (181, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_0_1_11', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '大杯-正常冰-7分糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (182, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_1_1_11', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '大杯-少冰-7分糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (183, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_2_1_11', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '大杯-去冰-7分糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (184, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_3_1_11', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '大杯-熱-7分糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (185, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_4_1_11', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '大杯-溫-7分糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

INSERT INTO `tb_sku` VALUES (186, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_0_2_11', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '大杯-正常冰-半糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (187, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_1_2_11', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '大杯-少冰-半糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (188, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_2_2_11', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '大杯-去冰-半糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (189, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_3_2_11', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '大杯-熱-半糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (190, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_4_2_11', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '大杯-溫-半糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

INSERT INTO `tb_sku` VALUES (191, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_0_3_11', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '大杯-正常冰-3分糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (192, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_1_3_11', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '大杯-少冰-3分糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (193, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_2_3_11', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '大杯-去冰-3分糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (194, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_3_3_11', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '大杯-熱-3分糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (195, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_4_3_11', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '大杯-溫-3分糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');


INSERT INTO `tb_sku` VALUES (196, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_0_4_11', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '大杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (197, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_1_4_11', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '大杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (198, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_2_4_11', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '大杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (199, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_3_4_11', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '大杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (200, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_4_4_11', '{\"大小選擇\":\"大\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '大杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

-- ＋＋＋＋＋＋＋＋＋＋＋＋＋＋＋＋＋＋＋
INSERT INTO `tb_sku` VALUES (201, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 25, '1_0_0_00', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"正常\",\"加料選擇\":\"\"}', '中杯-正常冰-全糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (202, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 25, '1_1_0_00', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"正常\",\"加料選擇\":\"\"}', '中杯-少冰-全糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (203, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 25, '1_2_0_00', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"正常\",\"加料選擇\":\"\"}', '中杯-去冰-全糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (204, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 25, '1_3_0_00', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"正常\",\"加料選擇\":\"\"}', '中杯-熱-全糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (205, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 25, '1_4_0_00', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"正常\",\"加料選擇\":\"\"}', '中杯-溫-全糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

INSERT INTO `tb_sku` VALUES (206, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 25, '1_0_1_00', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"\"}', '中杯-正常冰-7分糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (207, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 25, '1_1_1_00', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"\"}', '中杯-少冰-7分糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (208, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 25, '1_2_1_00', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"\"}', '中杯-去冰-7分糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (209, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 25, '1_3_1_00', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"\"}', '中杯-熱-7分糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (210, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 25, '1_4_1_00', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"\"}', '中杯-溫-7分糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

INSERT INTO `tb_sku` VALUES (211, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 25, '1_0_2_00', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"\"}', '中杯-正常冰-半糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (212, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 25, '1_1_2_00', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"\"}', '中杯-少冰-半糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (213, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 25, '1_2_2_00', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"\"}', '中杯-去冰-半糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (214, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 25, '1_3_2_00', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"\"}', '中杯-熱-半糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (215, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 25, '1_4_2_00', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"\"}', '中杯-溫-半糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

INSERT INTO `tb_sku` VALUES (216, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 25, '1_0_3_00', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"\"}', '中杯-正常冰-3分糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (217, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 25, '1_1_3_00', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"\"}', '中杯-少冰-3分糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (218, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 25, '1_2_3_00', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"\"}', '中杯-去冰-3分糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (219, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 25, '1_3_3_00', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"\"}', '中杯-熱-3分糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (220, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 25, '1_4_3_00', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"\"}', '中杯-溫-3分糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');


INSERT INTO `tb_sku` VALUES (221, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 25, '1_0_4_00', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"\"}', '中杯-正常冰-無糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (222, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 25, '1_1_4_00', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"\"}', '中杯-少冰-無糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (223, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 25, '1_2_4_00', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"\"}', '中杯-去冰-無糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (224, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 25, '1_3_4_00', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"\"}', '中杯-熱-無糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (225, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 25, '1_4_4_00', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"\"}', '中杯-溫-無糖-不加料', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');


INSERT INTO `tb_sku` VALUES (226, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_0_0_10', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"正常\",\"加料選擇\":\"椰果\"}', '中杯-正常冰-全糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (227, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_1_0_10', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"正常\",\"加料選擇\":\"椰果\"}', '中杯-少冰-全糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (228, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_2_0_10', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"正常\",\"加料選擇\":\"椰果\"}', '中杯-去冰-全糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (229, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_3_0_10', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"正常\",\"加料選擇\":\"椰果\"}', '中杯-熱-全糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (230, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_4_0_10', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"正常\",\"加料選擇\":\"椰果\"}', '中杯-溫-全糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

INSERT INTO `tb_sku` VALUES (231, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_0_1_10', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"椰果\"}', '中杯-正常冰-7分糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (232, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_1_1_10', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"椰果\"}', '中杯-少冰-7分糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (233, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_2_1_10', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"椰果\"}', '中杯-去冰-7分糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (234, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_3_1_10', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"椰果\"}', '中杯-熱-7分糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (235, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_4_1_10', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"椰果\"}', '中杯-溫-7分糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

INSERT INTO `tb_sku` VALUES (236, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_0_2_10', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"椰果\"}', '中杯-正常冰-半糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (237, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_1_2_10', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"椰果\"}', '中杯-少冰-半糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (238, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_2_2_10', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"椰果\"}', '中杯-去冰-半糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (239, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_3_2_10', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"椰果\"}', '中杯-熱-半糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (240, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_4_2_10', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"椰果\"}', '中杯-溫-半糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

INSERT INTO `tb_sku` VALUES (241, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_0_3_10', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"椰果\"}', '中杯-正常冰-3分糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (242, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_1_3_10', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"椰果\"}', '中杯-少冰-3分糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (243, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_2_3_10', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"椰果\"}', '中杯-去冰-3分糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (244, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_3_3_10', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"椰果\"}', '中杯-熱-3分糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (245, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_4_3_10', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"椰果\"}', '中杯-溫-3分糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');


INSERT INTO `tb_sku` VALUES (246, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_0_4_10', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"椰果\"}', '中杯-正常冰-無糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (247, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_1_4_10', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"椰果\"}', '中杯-少冰-無糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (248, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_2_4_10', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"椰果\"}', '中杯-去冰-無糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (249, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_3_4_10', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"椰果\"}', '中杯-熱-無糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (250, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_4_4_10', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"椰果\"}', '中杯-溫-無糖-加椰果', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');



INSERT INTO `tb_sku` VALUES (251, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_0_0_01', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"正常\",\"加料選擇\":\"蘆薈\"}', '中杯-正常冰-全糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (252, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_1_0_01', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"正常\",\"加料選擇\":\"蘆薈\"}', '中杯-少冰-全糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (253, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_2_0_01', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"正常\",\"加料選擇\":\"蘆薈\"}', '中杯-去冰-全糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (254, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_3_0_01', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"正常\",\"加料選擇\":\"蘆薈\"}', '中杯-熱-全糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (255, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_4_0_01', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"正常\",\"加料選擇\":\"蘆薈\"}', '中杯-溫-全糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

INSERT INTO `tb_sku` VALUES (256, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_0_1_01', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"蘆薈\"}', '中杯-正常冰-7分糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (257, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_1_1_01', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"蘆薈\"}', '中杯-少冰-7分糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (258, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_2_1_01', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"蘆薈\"}', '中杯-去冰-7分糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (259, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_3_1_01', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"蘆薈\"}', '中杯-熱-7分糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (260, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_4_1_01', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"蘆薈\"}', '中杯-溫-7分糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

INSERT INTO `tb_sku` VALUES (261, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_0_2_01', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"蘆薈\"}', '中杯-正常冰-半糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (262, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_1_2_01', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"蘆薈\"}', '中杯-少冰-半糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (263, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_2_2_01', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"蘆薈\"}', '中杯-去冰-半糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (264, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_3_2_01', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"蘆薈\"}', '中杯-熱-半糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (265, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_4_2_01', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"蘆薈\"}', '中杯-溫-半糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

INSERT INTO `tb_sku` VALUES (266, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_0_3_01', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"蘆薈\"}', '中杯-正常冰-3分糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (267, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_1_3_01', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"蘆薈\"}', '中杯-少冰-3分糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (268, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_2_3_01', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"蘆薈\"}', '中杯-去冰-3分糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (269, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_3_3_01', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"蘆薈\"}', '中杯-熱-3分糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (270, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_4_3_01', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"蘆薈\"}', '中杯-溫-3分糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');


INSERT INTO `tb_sku` VALUES (271, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_0_4_01', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"蘆薈\"}', '中杯-正常冰-無糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (272, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_1_4_01', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"蘆薈\"}', '中杯-少冰-無糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (273, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_2_4_01', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"蘆薈\"}', '中杯-去冰-無糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (274, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_3_4_01', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"蘆薈\"}', '中杯-熱-無糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (275, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 35, '1_4_4_01', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"蘆薈\"}', '中杯-溫-無糖-加蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

INSERT INTO `tb_sku` VALUES (276, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 45, '1_0_0_11', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"正常\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-正常冰-全糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (277, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 45, '1_1_0_11', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"正常\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-少冰-全糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (278, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 45, '1_2_0_11', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"正常\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-去冰-全糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (279, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 45, '1_3_0_11', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"正常\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-熱-全糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (280, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 45, '1_4_0_11', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"正常\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-溫-全糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

INSERT INTO `tb_sku` VALUES (281, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 45, '1_0_1_11', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-正常冰-7分糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (282, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 45, '1_1_1_11', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-少冰-7分糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (283, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 45, '1_2_1_11', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-去冰-7分糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (284, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 45, '1_3_1_11', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-熱-7分糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (285, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 45, '1_4_1_11', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-溫-7分糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

INSERT INTO `tb_sku` VALUES (286, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 45, '1_0_2_11', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-正常冰-半糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (287, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 45, '1_1_2_11', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-少冰-半糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (288, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 45, '1_2_2_11', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-去冰-半糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (289, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 45, '1_3_2_11', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-熱-半糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (290, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 45, '1_4_2_11', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"半糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-溫-半糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

INSERT INTO `tb_sku` VALUES (291, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 45, '1_0_3_11', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-正常冰-3分糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (292, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 45, '1_1_3_11', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-少冰-3分糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (293, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 45, '1_2_3_11', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-去冰-3分糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (294, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 45, '1_3_3_11', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-熱-3分糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (295, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 45, '1_4_3_11', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-溫-3分糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');


INSERT INTO `tb_sku` VALUES (296, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 45, '1_0_4_11', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"正常冰\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (297, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 45, '1_1_4_11', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-少冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (298, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 45, '1_2_4_11', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-去冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (299, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 45, '1_3_4_11', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"熱\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-熱-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (300, 1, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 45, '1_4_4_11', '{\"大小選擇\":\"中\",\"冰熱選擇\":\"溫\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-溫-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

-- ////////////////////////////







-- INSERT INTO `tb_sku` VALUES (105, 1, '茉莉綠茶-中杯', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '1_00', '{\"大小選擇\":\"中\",\"加料選擇\":\"\"}', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (106, 1, '茉莉綠茶-中杯(加蘆薈)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '1_01', '{\"大小選擇\":\"中\",\"加料選擇\":\"蘆薈\"}', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (107, 1, '茉莉綠茶-中杯(加椰果)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '1_10', '{\"大小選擇\":\"中\",\"加料選擇\":\"椰果\"}', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (108, 1, '茉莉綠茶-中杯(加椰果，蘆薈)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '1_11', '{\"大小選擇\":\"中\",\"加料選擇\":\"椰果＋蘆薈\"}', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');



-- 阿薩姆紅茶
INSERT INTO `tb_sku` VALUES (1111, 2, '阿薩姆紅茶-大杯', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_00', '{\"大小選擇\":\"大\",\"加料選擇\":\"\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1112, 2, '阿薩姆紅茶-大杯(加蘆薈)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_01', '{\"大小選擇\":\"大\",\"加料選擇\":\"蘆薈\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1113, 2, '阿薩姆紅茶-大杯(加椰果)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_10', '{\"大小選擇\":\"大\",\"加料選擇\":\"椰果\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1114, 2, '阿薩姆紅茶-大杯(加椰果，蘆薈)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_11', '{\"大小選擇\":\"大\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');



INSERT INTO `tb_sku` VALUES (1115, 2, '阿薩姆紅茶-中杯', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '1_00', '{\"大小選擇\":\"中\",\"加料選擇\":\"\"}' , '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1116, 2, '阿薩姆紅茶-中杯(加蘆薈)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '1_01', '{\"大小選擇\":\"中\",\"加料選擇\":\"蘆薈\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1117, 2, '阿薩姆紅茶-中杯(加椰果)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '1_10', '{\"大小選擇\":\"中\",\"加料選擇\":\"椰果\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1118, 2, '阿薩姆紅茶-中杯(加椰果，蘆薈)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '1_11', '{\"大小選擇\":\"中\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');




-- 檸檬汁
INSERT INTO `tb_sku` VALUES (1121, 4, '檸檬汁-大杯', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, NULL, NULL, '中杯-正常冰-無糖-加椰果/蘆薈', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1122, 4, '檸檬汁-中杯', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 25, NULL, NULL, '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');


-- 奶茶
INSERT INTO `tb_sku` VALUES (1123, 10, '奶茶-調味奶-大杯', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_0_00', '{"牛奶選擇\":\"調味奶\",\"大小選擇\":\"大\",\"加料選擇\":\"\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1124, 10, '奶茶-調味奶-大杯(加蘆薈)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_0_01', '{"牛奶選擇\":\"調味奶\",\"大小選擇\":\"大\",\"加料選擇\":\"蘆薈\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1125, 10, '奶茶-調味奶-大杯(加椰果)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_0_10', '{"牛奶選擇\":\"調味奶\",\"大小選擇\":\"大\",\"加料選擇\":\"椰果\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1126, 10, '奶茶-調味奶-大杯(加椰果，蘆薈)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_0_11', '{"牛奶選擇\":\"調味奶\",\"大小選擇\":\"大\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

INSERT INTO `tb_sku` VALUES (1201, 10, '奶茶-牛奶-大杯', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '1_0_00', '{"牛奶選擇\":\"牛奶奶\",\"大小選擇\":\"大\",\"加料選擇\":\"\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1202, 10, '奶茶-牛奶-大杯(加蘆薈)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '1_0_01', '{"牛奶選擇\":\"牛奶奶\",\"大小選擇\":\"大\",\"加料選擇\":\"蘆薈\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1203, 10, '奶茶-牛奶-大杯(加椰果)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '1_0_10', '{"牛奶選擇\":\"牛奶奶\",\"大小選擇\":\"大\",\"加料選擇\":\"椰果\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1204, 10, '奶茶-牛奶-大杯(加椰果，蘆薈)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 60, '1_0_11', '{"牛奶選擇\":\"牛奶奶\",\"大小選擇\":\"大\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');



INSERT INTO `tb_sku` VALUES (1205, 10, '奶茶-調味奶-中杯', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_1_00', '{"牛奶選擇\":\"調味奶\",\"大小選擇\":\"中\",\"加料選擇\":\"\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1206, 10, '奶茶-調味奶-中杯(加蘆薈)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_1_01', '{"牛奶選擇\":\"調味奶\",\"大小選擇\":\"中\",\"加料選擇\":\"蘆薈\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1207, 10, '奶茶-調味奶-中杯(加椰果)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_1_10', '{"牛奶選擇\":\"調味奶\",\"大小選擇\":\"中\",\"加料選擇\":\"椰果\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1208, 10, '奶茶-調味奶-中杯(加椰果，蘆薈)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_1_11', '{"牛奶選擇\":\"調味奶\",\"大小選擇\":\"中\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');


INSERT INTO `tb_sku` VALUES (1209, 10, '奶茶-調味奶-中杯', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_1_00', '{"牛奶選擇\":\"調味奶\",\"大小選擇\":\"中\",\"加料選擇\":\"\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1210, 10, '奶茶-調味奶-中杯(加蘆薈)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_1_01', '{"牛奶選擇\":\"調味奶\",\"大小選擇\":\"中\",\"加料選擇\":\"蘆薈\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1211, 10, '奶茶-調味奶-中杯(加椰果)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_1_10', '{"牛奶選擇\":\"調味奶\",\"大小選擇\":\"中\",\"加料選擇\":\"椰果\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1212, 10, '奶茶-調味奶-中杯(加椰果，蘆薈)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_1_11', '{"牛奶選擇\":\"調味奶\",\"大小選擇\":\"中\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');


-- 珍珠奶茶--大珍珠
INSERT INTO `tb_sku` VALUES (1301, 13, '珍珠奶茶-調味奶-大珍珠-大杯', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_0_00', '{"牛奶選擇\":\"調味奶\","粉圓選擇\":\"大\",\"大小選擇\":\"大\",\"加料選擇\":\"\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1302, 13, '珍珠奶茶-調味奶-大珍珠-大杯(加蘆薈)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_0_01', '{"牛奶選擇\":\"調味奶\","粉圓選擇\":\"大\",\"大小選擇\":\"大\",\"加料選擇\":\"蘆薈\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1303, 13, '珍珠奶茶-調味奶-大珍珠-大杯(加椰果)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_0_10', '{"牛奶選擇\":\"調味奶\","粉圓選擇\":\"大\",\"大小選擇\":\"大\",\"加料選擇\":\"椰果\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1304, 13, '珍珠奶茶-調味奶-大珍珠-大杯(加椰果，蘆薈)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_0_11', '{"牛奶選擇\":\"調味奶\","粉圓選擇\":\"大\",\"大小選擇\":\"大\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

INSERT INTO `tb_sku` VALUES (1371, 13, '珍珠奶茶-牛奶-大珍珠-大杯', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '1_0_00', '{"牛奶選擇\":\"牛奶奶\","粉圓選擇\":\"大\",\"大小選擇\":\"大\",\"加料選擇\":\"\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1372, 13, '珍珠奶茶-牛奶-大珍珠-大杯(加蘆薈)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '1_0_01', '{"牛奶選擇\":\"牛奶奶\","粉圓選擇\":\"大\",\"大小選擇\":\"大\",\"加料選擇\":\"蘆薈\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1373, 13, '珍珠奶茶-牛奶-大珍珠-大杯(加椰果)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '1_0_10', '{"牛奶選擇\":\"牛奶奶\","粉圓選擇\":\"大\",\"大小選擇\":\"大\",\"加料選擇\":\"椰果\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1374, 13, '珍珠奶茶-牛奶-大珍珠-大杯(加椰果，蘆薈)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 60, '1_0_11', '{"牛奶選擇\":\"牛奶奶\","粉圓選擇\":\"大\",\"大小選擇\":\"大\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');



INSERT INTO `tb_sku` VALUES (1305, 13, '珍珠奶茶-調味奶-大珍珠-中杯', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_1_00', '{"牛奶選擇\":\"調味奶\","粉圓選擇\":\"大\",\"大小選擇\":\"中\",\"加料選擇\":\"\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1306, 13, '珍珠奶茶-調味奶-大珍珠-中杯(加蘆薈)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_1_01', '{"牛奶選擇\":\"調味奶\","粉圓選擇\":\"大\",\"大小選擇\":\"中\",\"加料選擇\":\"蘆薈\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1307, 13, '珍珠奶茶-調味奶-大珍珠-中杯(加椰果)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_1_10', '{"牛奶選擇\":\"調味奶\","粉圓選擇\":\"大\",\"大小選擇\":\"中\",\"加料選擇\":\"椰果\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1308, 13, '珍珠奶茶-調味奶-大珍珠-中杯(加椰果，蘆薈)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_1_11', '{"牛奶選擇\":\"調味奶\","粉圓選擇\":\"大\",\"大小選擇\":\"中\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');



INSERT INTO `tb_sku` VALUES (1309, 13, '珍珠奶茶-調味奶-大珍珠-中杯', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '1_1_00', '{"牛奶選擇\":\"調味奶\","粉圓選擇\":\"大\",\"大小選擇\":\"中\",\"加料選擇\":\"\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1310, 13, '珍珠奶茶-調味奶-大珍珠-中杯(加蘆薈)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '1_1_01', '{"牛奶選擇\":\"調味奶\","粉圓選擇\":\"大\",\"大小選擇\":\"中\",\"加料選擇\":\"蘆薈\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1311, 13, '珍珠奶茶-調味奶-大珍珠-中杯(加椰果)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '1_1_10', '{"牛奶選擇\":\"調味奶\","粉圓選擇\":\"大\",\"大小選擇\":\"中\",\"加料選擇\":\"椰果\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1312, 13, '珍珠奶茶-調味奶-大珍珠-中杯(加椰果，蘆薈)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '1_1_11', '{"牛奶選擇\":\"調味奶\","粉圓選擇\":\"大\",\"大小選擇\":\"中\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');


-- 珍珠奶茶--小珍珠
INSERT INTO `tb_sku` VALUES (1313, 13, '珍珠奶茶-調味奶-小珍珠-大杯', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_1_00', '{"牛奶選擇\":\"調味奶\","粉圓選擇\":\"小\",\"大小選擇\":\"大\",\"加料選擇\":\"\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1314, 13, '珍珠奶茶-調味奶-小珍珠-大杯(加蘆薈)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_1_01', '{"牛奶選擇\":\"調味奶\","粉圓選擇\":\"小\",\"大小選擇\":\"大\",\"加料選擇\":\"蘆薈\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1315, 13, '珍珠奶茶-調味奶-小珍珠-大杯(加椰果)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_1_10', '{"牛奶選擇\":\"調味奶\","粉圓選擇\":\"小\",\"大小選擇\":\"大\",\"加料選擇\":\"椰果\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1316, 13, '珍珠奶茶-調味奶-小珍珠-大杯(加椰果，蘆薈)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_1_11', '{"牛奶選擇\":\"調味奶\","粉圓選擇\":\"小\",\"大小選擇\":\"大\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

INSERT INTO `tb_sku` VALUES (1321, 13, '珍珠奶茶-牛奶-小珍珠-大杯', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '1_1_00', '{"牛奶選擇\":\"牛奶奶\","粉圓選擇\":\"小\",\"大小選擇\":\"大\",\"加料選擇\":\"\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1322, 13, '珍珠奶茶-牛奶-小珍珠-大杯(加蘆薈)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '1_1_01', '{"牛奶選擇\":\"牛奶奶\","粉圓選擇\":\"小\",\"大小選擇\":\"大\",\"加料選擇\":\"蘆薈\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1323, 13, '珍珠奶茶-牛奶-小珍珠-大杯(加椰果)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '1_1_10', '{"牛奶選擇\":\"牛奶奶\","粉圓選擇\":\"小\",\"大小選擇\":\"大\",\"加料選擇\":\"椰果\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1324, 13, '珍珠奶茶-牛奶-小珍珠-大杯(加椰果，蘆薈)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 60, '1_1_11', '{"牛奶選擇\":\"牛奶奶\","粉圓選擇\":\"小\",\"大小選擇\":\"大\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');



INSERT INTO `tb_sku` VALUES (1325, 13, '珍珠奶茶-調味奶-小珍珠-中杯', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '0_1_00', '{"牛奶選擇\":\"調味奶\","粉圓選擇\":\"小\",\"大小選擇\":\"中\",\"加料選擇\":\"\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1326, 13, '珍珠奶茶-調味奶-小珍珠-中杯(加蘆薈)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_1_01', '{"牛奶選擇\":\"調味奶\","粉圓選擇\":\"小\",\"大小選擇\":\"中\",\"加料選擇\":\"蘆薈\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1327, 13, '珍珠奶茶-調味奶-小珍珠-中杯(加椰果)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '0_1_10', '{"牛奶選擇\":\"調味奶\","粉圓選擇\":\"小\",\"大小選擇\":\"中\",\"加料選擇\":\"椰果\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1328, 13, '珍珠奶茶-調味奶-小珍珠-中杯(加椰果，蘆薈)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '0_1_11', '{"牛奶選擇\":\"調味奶\","粉圓選擇\":\"小\",\"大小選擇\":\"中\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');


INSERT INTO `tb_sku` VALUES (1329, 13, '珍珠奶茶-調味奶-小珍珠-中杯', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '1_1_00', '{"牛奶選擇\":\"調味奶\","粉圓選擇\":\"小\",\"大小選擇\":\"中\",\"加料選擇\":\"\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1330, 13, '珍珠奶茶-調味奶-小珍珠-中杯(加蘆薈)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '1_1_01', '{"牛奶選擇\":\"調味奶\","粉圓選擇\":\"小\",\"大小選擇\":\"中\",\"加料選擇\":\"蘆薈\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1331, 13, '珍珠奶茶-調味奶-小珍珠-中杯(加椰果)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '1_1_10', '{"牛奶選擇\":\"調味奶\","粉圓選擇\":\"小\",\"大小選擇\":\"中\",\"加料選擇\":\"椰果\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1332, 13, '珍珠奶茶-調味奶-小珍珠-中杯(加椰果，蘆薈)', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '1_1_11', '{"牛奶選擇\":\"調味奶\","粉圓選擇\":\"小\",\"大小選擇\":\"中\",\"加料選擇\":\"椰果＋蘆薈\"}', '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');




-- 紅茶/綠茶拿鐵', '紅茶/綠茶拿鐵

INSERT INTO `tb_sku` VALUES (1341, 18, '紅茶/綠茶拿鐵-大杯', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, NULL, NULL, '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1342, 18, '紅茶/綠茶拿鐵-中杯', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 25, NULL, NULL, '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');



-- 王品牛排套餐
INSERT INTO `tb_sku` VALUES (1408, 30, '王品牛小排', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 1390, NULL, NULL, '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1409, 31, '王品牛小排佐犢牛肋排', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 1390, NULL, NULL, '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- 王者盛宴套餐
INSERT INTO `tb_sku` VALUES (1410, 32, '王品牛小排', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 1990, NULL, NULL, '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1411, 33, '王品牛小排佐犢牛肋排', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 1990, NULL, NULL, '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
INSERT INTO `tb_sku` VALUES (1412, 34, '王品牛小排佐犢牛肋排', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 1990, NULL, NULL, '中杯-正常冰-無糖-加椰果/蘆薈', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');


-- ///////
-- INSERT INTO `tb_sku` VALUES (2600246, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '1_0_11', '{\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"正常\",\"加料選擇\":\"椰果＋蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600247, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '1_0_10', '{\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"正常\",\"加料選擇\":\"椰果\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600248, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '1_0_01', '{\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"正常\",\"加料選擇\":\"蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600249, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '1_0_00', '{\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"正常\",\"加料選擇\":\"\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

-- INSERT INTO `tb_sku` VALUES (2600250, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '2_0_11', '{\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"正常\",\"加料選擇\":\"椰果＋蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600251, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '2_0_10', '{\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"正常\",\"加料選擇\":\"椰果\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600252, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '2_0_01', '{\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"正常\",\"加料選擇\":\"蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600253, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '2_0_00', '{\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"正常\",\"加料選擇\":\"\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

-- INSERT INTO `tb_sku` VALUES (2600250, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '3_0_11', '{\"冰熱選擇\":\"熱\",\"甜度選擇\":\"正常\",\"加料選擇\":\"椰果＋蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600251, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '3_0_10', '{\"冰熱選擇\":\"熱\",\"甜度選擇\":\"正常\",\"加料選擇\":\"椰果\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600252, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '3_0_01', '{\"冰熱選擇\":\"熱\",\"甜度選擇\":\"正常\",\"加料選擇\":\"蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600253, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '3_0_00', '{\"冰熱選擇\":\"熱\",\"甜度選擇\":\"正常\",\"加料選擇\":\"\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

-- INSERT INTO `tb_sku` VALUES (2600254, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '4_0_11', '{\"冰熱選擇\":\"溫\",\"甜度選擇\":\"正常\",\"加料選擇\":\"椰果＋蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600255, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '4_0_10', '{\"冰熱選擇\":\"溫\",\"甜度選擇\":\"正常\",\"加料選擇\":\"椰果\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600256, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '4_0_01', '{\"冰熱選擇\":\"溫\",\"甜度選擇\":\"正常\",\"加料選擇\":\"蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600257, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '4_0_00', '{\"冰熱選擇\":\"溫\",\"甜度選擇\":\"正常\",\"加料選擇\":\"\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');


-- 甜度選擇 7分糖
-- INSERT INTO `tb_sku` VALUES (2600266, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '1_1_11', '{\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"椰果＋蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600267, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '1_1_10', '{\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"椰果\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600268, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '1_1_01', '{\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600269, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '1_1_00', '{\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

-- INSERT INTO `tb_sku` VALUES (2600260, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '2_1_11', '{\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"椰果＋蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600261, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '2_1_10', '{\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"椰果\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600262, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '2_1_01', '{\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600263, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '2_1_00', '{\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

-- INSERT INTO `tb_sku` VALUES (2600270, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '3_1_11', '{\"冰熱選擇\":\"熱\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"椰果＋蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600271, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '3_1_10', '{\"冰熱選擇\":\"熱\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"椰果\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600272, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '3_1_01', '{\"冰熱選擇\":\"熱\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600273, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '3_1_00', '{\"冰熱選擇\":\"熱\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

-- INSERT INTO `tb_sku` VALUES (2600274, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '4_1_11', '{\"冰熱選擇\":\"溫\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"椰果＋蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600275, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '4_1_10', '{\"冰熱選擇\":\"溫\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"椰果\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600276, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '4_1_01', '{\"冰熱選擇\":\"溫\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600277, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '4_1_00', '{\"冰熱選擇\":\"溫\",\"甜度選擇\":\"7分糖\",\"加料選擇\":\"\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

-- 甜度選擇 5分糖
-- INSERT INTO `tb_sku` VALUES (2600286, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '1_2_11', '{\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"5分糖\",\"加料選擇\":\"椰果＋蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600287, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '1_2_10', '{\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"5分糖\",\"加料選擇\":\"椰果\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600288, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '1_2_01', '{\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"5分糖\",\"加料選擇\":\"蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600289, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '1_2_00', '{\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"5分糖\",\"加料選擇\":\"\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

-- INSERT INTO `tb_sku` VALUES (2600290, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '2_2_11', '{\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"5分糖\",\"加料選擇\":\"椰果＋蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600291, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '2_2_10', '{\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"5分糖\",\"加料選擇\":\"椰果\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600292, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '2_2_01', '{\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"5分糖\",\"加料選擇\":\"蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600293, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '2_2_00', '{\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"5分糖\",\"加料選擇\":\"\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

-- INSERT INTO `tb_sku` VALUES (2600300, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '3_2_11', '{\"冰熱選擇\":\"熱\",\"甜度選擇\":\"5分糖\",\"加料選擇\":\"椰果＋蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600301, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '3_2_10', '{\"冰熱選擇\":\"熱\",\"甜度選擇\":\"5分糖\",\"加料選擇\":\"椰果\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600302, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '3_2_01', '{\"冰熱選擇\":\"熱\",\"甜度選擇\":\"5分糖\",\"加料選擇\":\"蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600303, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '3_2_00', '{\"冰熱選擇\":\"熱\",\"甜度選擇\":\"5分糖\",\"加料選擇\":\"\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

-- INSERT INTO `tb_sku` VALUES (2600304, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '4_2_11', '{\"冰熱選擇\":\"溫\",\"甜度選擇\":\"5分糖\",\"加料選擇\":\"椰果＋蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600305, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '4_2_10', '{\"冰熱選擇\":\"溫\",\"甜度選擇\":\"5分糖\",\"加料選擇\":\"椰果\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600306, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '4_2_01', '{\"冰熱選擇\":\"溫\",\"甜度選擇\":\"5分糖\",\"加料選擇\":\"蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600307, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '4_2_00', '{\"冰熱選擇\":\"溫\",\"甜度選擇\":\"5分糖\",\"加料選擇\":\"\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

-- 甜度選擇 3分糖
-- INSERT INTO `tb_sku` VALUES (2600286, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '1_3_11', '{\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"椰果＋蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600287, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '1_3_10', '{\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"椰果\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600288, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '1_3_01', '{\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600289, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '1_3_00', '{\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

-- INSERT INTO `tb_sku` VALUES (2600290, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '2_3_11', '{\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"椰果＋蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600291, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '2_3_10', '{\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"椰果\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600292, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '2_3_01', '{\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600293, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '2_3_00', '{\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

-- INSERT INTO `tb_sku` VALUES (2600300, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '3_3_11', '{\"冰熱選擇\":\"熱\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"椰果＋蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600301, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '3_3_10', '{\"冰熱選擇\":\"熱\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"椰果\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600302, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '3_3_01', '{\"冰熱選擇\":\"熱\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600303, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '3_3_00', '{\"冰熱選擇\":\"熱\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

-- INSERT INTO `tb_sku` VALUES (2600304, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '4_3_11', '{\"冰熱選擇\":\"溫\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"椰果＋蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600305, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '4_3_10', '{\"冰熱選擇\":\"溫\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"椰果\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600306, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '4_3_01', '{\"冰熱選擇\":\"溫\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600307, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '4_3_00', '{\"冰熱選擇\":\"溫\",\"甜度選擇\":\"3分糖\",\"加料選擇\":\"\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

-- 甜度選擇 無糖
-- INSERT INTO `tb_sku` VALUES (2600286, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '1_4_11', '{\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"椰果＋蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600287, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '1_4_10', '{\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"椰果\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600288, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '1_4_01', '{\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600289, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '1_4_00', '{\"冰熱選擇\":\"少冰\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

-- INSERT INTO `tb_sku` VALUES (2600290, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '2_4_11', '{\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"椰果＋蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600291, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '2_4_10', '{\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"椰果\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600292, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '2_4_01', '{\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600293, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '2_4_00', '{\"冰熱選擇\":\"去冰\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

-- INSERT INTO `tb_sku` VALUES (2600300, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '3_4_11', '{\"冰熱選擇\":\"熱\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"椰果＋蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600301, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '3_4_10', '{\"冰熱選擇\":\"熱\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"椰果\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600302, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '3_4_01', '{\"冰熱選擇\":\"熱\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600303, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '3_4_00', '{\"冰熱選擇\":\"熱\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');

-- INSERT INTO `tb_sku` VALUES (2600304, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 50, '4_4_11', '{\"冰熱選擇\":\"溫\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"椰果＋蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600305, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '4_4_10', '{\"冰熱選擇\":\"溫\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"椰果\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600306, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 40, '4_4_01', '{\"冰熱選擇\":\"溫\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"蘆薈\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2600307, 0, '茉莉綠茶', 'http://image.ji-well.com/images/9/15/1524297313793.jpg ', 30, '4_4_00', '{\"冰熱選擇\":\"溫\",\"甜度選擇\":\"無糖\",\"加料選擇\":\"\"} ', 1, '2020-09-11 15:55:14', '2020-09-11 15:55:14');



-- INSERT INTO `tb_sku` VALUES (2600248, 2, '華為G9 青春版金色移動聯通電信4G手機雙卡雙待', 'http://image.ji-well.com/images/9/5/1524297314398.jpg ', 84900, '1_0_0', '{\"機身顏色\":\"金色\",\"內存\":\"3GB\",\"機身存儲\":\"16GB\"} ', 1, '2018-04-21 15:55:14', '2018-04-21 15:55:14');
-- INSERT INTO `tb_sku` VALUES (2868393, 3, '三星Galaxy C5（SM-C5000）4GB+32GB 楓葉金移動聯通電信4G手機雙卡雙待', 'http://image.ji-well.com/images /12/15/1524297315534.jpg', 119900, '0_0_0', '{\"機身顏色\":\"金\",\"內存\":\"4GB\",\"機身存儲\ ":\"32GB\"}', 1, '2018-04-21 15:55:16', '2018-04-21 15:55:16');
-- INSERT INTO `tb_sku` VALUES (2868435, 3, '三星Galaxy C5（SM-C5000）4GB+32GB 薔薇粉移動聯通電信4G手機雙卡雙待', 'http://image.ji-well.com/images /9/11/1524297316400.jpg', 119900, '1_0_0', '{\"機身顏色\":\"粉\",\"內存\":\"4GB\",\"機身存儲\ ":\"32GB\"}', 1, '2018-04-21 15:55:16', '2018-04-21 15:55:16');
-- INSERT INTO `tb_sku` VALUES (2895136, 3, '三星Galaxy C5（SM-C5000）4GB+32GB 煙雨灰移動聯通電信4G手機雙卡雙待', 'http://image.ji-well.com/images /15/15/1524297316945.jpg', 119900, '2_0_0', '{\"機身顏色\":\"灰\",\"內存\":\"4GB\",\"機身存儲\ ":\"32GB\"}', 1, '2018-04-21 15:55:17', '2018-04-21 15:55:17');

-- ----------------------------
-- Table structure for tb_specification
-- ----------------------------
DROP TABLE IF EXISTS `tb_specification`;
CREATE TABLE `tb_specification` (
  `category_id` bigint(20) NOT NULL COMMENT '規格模板所屬商品分類id',
  `specifications` varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '規格參數模板，json格式',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品規格參數模板，json格式。 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_specification
-- ----------------------------
INSERT INTO `tb_specification` VALUES (8, '[{\"group\":\"1231232\",\"params\":[{\"k\":\"123123\",\"searchable\": false,\"global\":true,\"numerical\":false,\"unit\":\"\",\"options\":[]}]}]');
INSERT INTO `tb_specification` VALUES (9, '[{\"group\":\"13123123123\",\"params\":[{\"k\":\"123123\",\"searchable\": false,\"global\":true,\"numerical\":false,\"unit\":\"\",\"options\":[]}]}]');
INSERT INTO `tb_specification` VALUES (76, '[{\"group\":\"主體\",\"params\":[{\"k\":\"品牌\",\"searchable\": false,\"global\":true,\"options\":[]},{\"k\":\"型號\",\"searchable\":false,\"global\":true,\ "options\":[]},{\"k\":\"上市年份\",\"searchable\":false,\"global\":true,\"options\":[],\" numerical\":true,\"unit\":\"年\"}]},{\"group\":\"基本信息\",\"params\":[{\"k\":\ "機身顏色\",\"searchable\":false,\"global\":false,\"options\":[]},{\"k\":\"機身重量（g）\" ,\"searchable\":false,\"global\":true,\"options\":[],\"numerical\":true,\"unit\":\"g\"},{\" k\":\"機身材質工藝\",\"searchable\":false,\"global\":true,\"options\":[]}]},{\"group\":\"操作系統\",\"params\":[{\"k\":\"操作系統\",\"searchable\":true,\"global\":true,\"options\":[\ "Android\",\"IOS\",\"Windows\",\"功能機\"]}]},{\"group\":\"主芯片\",\"params\":[{ \"k\":\"CPU品牌\",\"searchable\":true,\"global\":true,\"options\":[\"驍龍（Snapdragon)\",\"麒麟\ "]},{\"k\":\"CPU型號\",\"searchable\":false,\"global\":true,\"options\":[]},{\"k\" :\"CPU核數\",\"searchable\":true,\"global\":true,\"options\":[\"一核\",\"二核\",\"四核\",\"六核\",\"八核\",\"十核\"]},{\"k\":\" CPU頻率\",\"searchable\":true,\"global\":true,\"options\":[],\"numerical\":true,\"unit\":\"GHz\"} ]},{\"group\":\"存儲\",\"params\":[{\"k\":\"內存\",\"searchable\":true,\"global\": false,\"options\":[\"1GB及以下\",\"2GB\",\"3GB\",\"4GB\",\"6GB\",\"8GB\"],\" numerical\":false,\"unit\":\"\"},{\"k\":\"機身存儲\",\"searchable\":true,\"global\":false,\ "options\":[\"8GB及以下\",\"16GB\",\"32GB\",\"64GB\",\"128GB\",\"256GB\"],\"numerical\" :false,\"unit\":\"\"}]},{\"group\":\"屏幕\",\"params\":[{\"k\":\"主屏幕尺寸（英寸）\",\"searchable\":true,\"global\":true,\"options\":[],\"numerical\":true,\"unit\":\"英寸\"} ,{\"k\":\"分辨率\",\"searchable\":false,\"global\":true,\"options\":[]}]},{\"group\": \"攝像頭\",\"params\":[{\"k\":\"前置攝像頭\",\"searchable\":true,\"global\":true,\"options\": [],\"numerical\":true,\"unit\":\"萬\"},{\"k\":\"後置攝像頭\",\"searchable\":true,\"global \":true,\"options\":[],\"numerical\":true,\"unit\":\"萬\"}]},{\"group\":\"電池信息\" ,\"params\":[{\"k\":\"電池容量（mAh）\",\"searcha ble\":true,\"global\":true,\"options\":[],\"numerical\":true,\"unit\":\"mAh\"}]}]');
INSERT INTO `tb_specification` VALUES (90, '[{\"group\":\"主體\",\"params\":[{\"k\":\"品牌\",\"searchable\": false,\"global\":true,\"numerical\":false,\"unit\":\"\",\"options\":[]},{\"k\":\"適用機型\",\"searchable\":false,\"global\":false,\"numerical\":false,\"unit\":\"\",\"options\":[]}]} ,{\"group\":\"規格尺寸\",\"params\":[{\"k\":\"貼膜尺寸\",\"searchable\":true,\"global\": true,\"numerical\":true,\"unit\":\"英寸\",\"options\":[]},{\"k\":\"材質\",\"searchable\" :true,\"global\":true,\"numerical\":false,\"unit\":\"\",\"options\":[\"鋼化玻璃\",\"水凝膜\ "]},{\"k\":\"類型\",\"searchable\":true,\"global\":true,\"numerical\":false,\"unit\":\"\ ",\"options\":[\"前膜\",\"後膜\",\"磨砂\",\"防指紋\",\"高透膜\"]}]}]') ;
INSERT INTO `tb_specification` VALUES (105, '[{\"group\":\"主體參數\",\"params\":[{\"k\":\"品牌\",\"searchable\" :false,\"global\":true,\"numerical\":false,\"unit\":\"\",\"options\":[]},{\"k\":\"型號\",\"searchable\":false,\"global\":false,\"numerical\":false,\"unit\":\"\",\"options\":[]},{\ "k\":\"產品顏色\",\"searchable\":false,\"global\":true,\"numerical\":false,\"unit\":\"\",\"options \":[]},{\"k\":\"上市日期\",\"searchable\":false,\"global\":true,\"numerical\":false,\"unit\" :\"\",\"options\":[]},{\"k\":\"能效等級\",\"searchable\":true,\"global\":true,\"numerical\ ":false,\"unit\":\"\",\"options\":[\"一級能效\",\"二級能效\",\"三級能效\",\"政府節能\"]}]},{\"group\":\"顯示參數\",\"params\":[{\"k\":\"屏幕尺寸\",\"searchable\":true, \"global\":false,\"numerical\":true,\"unit\":\"英寸\",\"options\":[]},{\"k\":\"屏幕分辨率\",\"searchable\":true,\"global\":true,\"numerical\":false,\"unit\":\"\",\"options\":[\"超高清\ ",\"全高清\",\"高清\"]}]}]');

-- ----------------------------
-- Table structure for tb_shop_favorite 我的最愛
-- ----------------------------
DROP TABLE IF EXISTS `tb_shop_favorite`;
CREATE TABLE `tb_shop_favorite` (
                                  `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '訂單創建時間',
                                  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '訂單創建時間',
                                  `spu_id` bigint(20) NOT NULL,
                                  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用戶id',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `tb_shop_favorite` (`id`, `create_time`, `modify_time`, `spu_id`, `user_id`) VALUES (1, '2020-1-24 22:34:19', '2020-1-27 22:34:19', 2600242, 1);
INSERT INTO `tb_shop_favorite` (`id`, `create_time`, `modify_time`, `spu_id`, `user_id`) VALUES (2, '2020-1-24 22:34:19', '2020-2-24 23:34:19', 2600242, 1);
INSERT INTO `tb_shop_favorite` (`id`, `create_time`, `modify_time`, `spu_id`, `user_id`) VALUES (3, '2018-04-21 15:55:15', '2020-3-24 22:34:19', 26002423, 1);
-- ----------------------------

-- ----------------------------
-- Table structure for tb_stock
-- ----------------------------
DROP TABLE IF EXISTS `tb_stock`;
CREATE TABLE `tb_stock` (
  `sku_id` bigint(20) NOT NULL COMMENT '庫存對應的商品sku id',
  `seckill_stock` int(9) NULL DEFAULT 0 COMMENT '可秒殺庫存',
  `seckill_total` int(9) NULL DEFAULT 0 COMMENT '秒殺總數量',
  `stock` int(9) NOT NULL COMMENT '庫存數量',
  PRIMARY KEY (`sku_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '庫存表，代表庫存，秒殺庫存等信息' ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of tb_stock
-- ----------------------------
INSERT INTO `tb_stock` VALUES (101, 20, 30, 9966);
INSERT INTO `tb_stock` VALUES (102, 20, 20, 9979);
INSERT INTO `tb_stock` VALUES (103, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (104, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (105, NULL, NULL, 9999);

INSERT INTO `tb_stock` VALUES (106, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (107, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (108, NULL, NULL, 9999);

INSERT INTO `tb_stock` VALUES (111, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (112, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (113, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (114, 10, 10, 9989);
INSERT INTO `tb_stock` VALUES (115, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (116, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (117, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (118, NULL, NULL, 9999);


INSERT INTO `tb_stock` VALUES (121, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (122, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (123, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (124, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (125, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (126, NULL, NULL, 9999);

INSERT INTO `tb_stock` VALUES (201, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (202, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (203, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (204, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (205, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (206, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (207, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (208, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (209, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (210, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (211, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (212, NULL, NULL, 9999);

INSERT INTO `tb_stock` VALUES (301, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (302, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (303, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (304, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (305, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (306, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (307, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (308, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (309, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (310, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (311, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (312, NULL, NULL, 9999);

INSERT INTO `tb_stock` VALUES (371, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (372, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (373, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (374, NULL, NULL, 9999);


INSERT INTO `tb_stock` VALUES (321, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (322, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (323, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (324, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (325, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (326, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (327, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (328, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (329, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (330, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (331, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (332, NULL, NULL, 9999);

INSERT INTO `tb_stock` VALUES (341, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (342, NULL, NULL, 9999);


INSERT INTO `tb_stock` VALUES (408, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (409, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (410, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (411, NULL, NULL, 9999);
INSERT INTO `tb_stock` VALUES (412, NULL, NULL, 9999);

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用戶名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密碼，加密存儲',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '註冊手機號',
  `created` datetime(0) NOT NULL COMMENT '創建時間',
  `gender` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'female' COMMENT '性別',
  `nickname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'nickname' COMMENT '別名',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用戶表' ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of tb_user
-- ----------------------------

INSERT INTO `tb_user` VALUES (1, 'jiwell','$2a$10$KUFMXKbXCo.WGS91Sr8VQO/FETVdNQkhne5J5GdIqgUhG0YY1tKc.', '15397089001', '2020-09-11 17:21:57', 'female', '極微');
-- INSERT INTO `tb_user` VALUES (2, 'wukun', '$2a$10$hcDzLMGnz96r7YCTzYwZwehZq7MOzAp13TUgPnsZX1ovhHN6aonJO', '13289393693', '2020-09-10 17:21:54', 'female', '阿坤');
-- INSERT INTO `tb_user` VALUES (3, 'guest', '$$2a$10$.FT5Yb5ZL/1VbWCqVCGAWOkcBX1ryv.IiamPo20sBFvtnBxvjNbQm', '18834820000', '2020-09-12 17:21:50', 'female', '訪客');

-- ----------------------------
-- View structure for cid3
-- ----------------------------
DROP VIEW IF EXISTS `cid3`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `cid3` AS select `tb_category`.`parent_id` AS `parent_id` from `tb_category` group by `tb_category`.`parent_id`;

SET FOREIGN_KEY_CHECKS = 1;
