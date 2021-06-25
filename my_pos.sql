-- --------------------------------------------------------
-- 主機:                           127.0.0.1
-- 伺服器版本:                        10.5.9-MariaDB - mariadb.org binary distribution
-- 伺服器作業系統:                      Win64
-- HeidiSQL 版本:                  11.2.0.6213
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- 傾印 my_pos 的資料庫結構
CREATE DATABASE IF NOT EXISTS `my_pos` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `my_pos`;

-- 傾印  資料表 my_pos.order_detail 結構
CREATE TABLE IF NOT EXISTS `order_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_num` varchar(20) NOT NULL,
  `product_id` varchar(20) NOT NULL,
  `product_name` varchar(150) DEFAULT NULL,
  `quantity` int(11) NOT NULL DEFAULT 0,
  `finished` varchar(20) NOT NULL DEFAULT '未出餐',
  `customer_name` varchar(150) DEFAULT NULL,
  `customer_phone` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_order_detail_product` (`product_id`) USING BTREE,
  KEY `FK_order_detail_order` (`order_num`) USING BTREE,
  CONSTRAINT `FK_order_detail_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  CONSTRAINT `FK_order_detail_sale_order` FOREIGN KEY (`order_num`) REFERENCES `sale_order` (`order_num`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4;

-- 正在傾印表格  my_pos.order_detail 的資料：~18 rows (近似值)
DELETE FROM `order_detail`;
/*!40000 ALTER TABLE `order_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_detail` ENABLE KEYS */;

-- 傾印  資料表 my_pos.product 結構
CREATE TABLE IF NOT EXISTS `product` (
  `product_id` varchar(20) NOT NULL,
  `category` varchar(50) NOT NULL,
  `name` varchar(150) NOT NULL,
  `price` int(11) NOT NULL,
  `photo` varchar(200) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 正在傾印表格  my_pos.product 的資料：~19 rows (近似值)
DELETE FROM `product`;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` (`product_id`, `category`, `name`, `price`, `photo`, `description`) VALUES
	('p-j-101', '吐司', '香蒜吐司', 15, 'pestotoast.png', '好吃的香蒜吐司'),
	('p-j-102', '吐司', '草莓吐司', 15, 'strawberrytoast.png', '好吃的草莓吐司'),
	('p-j-103', '吐司', '花生吐司', 15, 'peanuttoast.png', '好吃的花生吐司'),
	('p-j-104', '吐司', '培根蛋吐司', 25, 'baconandeggtoast.png', '培根加蛋'),
	('p-j-105', '吐司', '鮪魚蛋吐司', 25, 'tunaandeggtoast.png', '鮪魚加蛋'),
	('p-j-107', '吐司', '里肌蛋吐司', 35, 'limeeggtoast.png', '里肌肉加蛋'),
	('p-j-108', '吐司', '總匯吐司', 55, 'sandwichtoast.png', '好吃的總匯吐司'),
	('p-j-109', '蛋餅', '玉米蛋餅', 30, 'cornquiche.png', '玉米加蛋餅'),
	('p-j-110', '蛋餅', '肉鬆蛋餅', 30, 'mincedporkquiche.png', '肉鬆加蛋餅'),
	('p-j-112', '蛋餅', '里肌蛋餅', 40, 'omelette.png', '里肌肉加蛋餅'),
	('p-j-113', '蛋餅', '鮪魚蛋餅', 30, 'tunaomelette.png', '鮪魚加蛋餅'),
	('p-j-114', '蛋餅', '起司蛋餅', 35, 'cheesequiche.png', '起司加蛋餅'),
	('p-j-115', '蛋餅', '總匯蛋餅', 50, 'confluencequiche.png', '好吃的總匯蛋餅'),
	('p-j-116', '飲料', '紅茶', 20, 'blacktea.png', '好喝的紅茶'),
	('p-j-117', '飲料', '綠茶', 20, 'greentea.png', '好喝的綠茶'),
	('p-j-118', '飲料', '珍珠奶茶', 50, 'pearlmilktea.png', '好喝的珍珠奶茶'),
	('p-j-119', '飲料', '凍檸茶', 50, 'frozenlemontea.png', '好喝的凍檸茶'),
	('p-j-220', '飲料', '巧克力冰沙', 60, 'chocolatesmoothie.png', '好喝的巧克力冰沙');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;

-- 傾印  資料表 my_pos.sale_order 結構
CREATE TABLE IF NOT EXISTS `sale_order` (
  `order_num` varchar(20) NOT NULL,
  `order_date` datetime NOT NULL DEFAULT current_timestamp(),
  `total_price` double(22,0) NOT NULL DEFAULT 0,
  `customer_name` varchar(150) DEFAULT NULL,
  `customer_address` varchar(250) DEFAULT NULL,
  `customer_phone` varchar(100) DEFAULT NULL,
  `table_num` varchar(20) DEFAULT '0',
  PRIMARY KEY (`order_num`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 正在傾印表格  my_pos.sale_order 的資料：~3 rows (近似值)
DELETE FROM `sale_order`;
/*!40000 ALTER TABLE `sale_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `sale_order` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
