/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.21-log : Database - echoweb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`echoweb` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `echoweb`;

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `comment_id` varchar(40) NOT NULL,
  `user_id` varchar(40) NOT NULL,
  `content` text NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `comment` */

/*Table structure for table `memo` */

DROP TABLE IF EXISTS `memo`;

CREATE TABLE `memo` (
  `memo_id` varchar(50) NOT NULL,
  `edit_time` datetime NOT NULL,
  `send_time` datetime NOT NULL,
  `user_id` varchar(50) NOT NULL,
  `state` int(2) NOT NULL,
  `memo_content` varchar(255) NOT NULL,
  PRIMARY KEY (`memo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `memo` */

insert  into `memo`(`memo_id`,`edit_time`,`send_time`,`user_id`,`state`,`memo_content`) values ('23ce3168-3a4d-456f-8ec3-3511a991a5d0','2018-06-22 21:41:37','2018-06-22 21:55:00','beeid',0,'hey'),('62bcf6f6-c0f3-4695-a3b2-b8b0cd69d8ff','2018-06-19 22:40:55','2018-06-19 23:44:00','beeid',0,'吃海底捞'),('8028f928-4583-4595-bdac-77f0fb0c46a5','2018-06-22 19:23:14','2018-06-22 19:25:00','beeid',0,'看剧看剧'),('95e9eb7a-e81b-4efb-9bfa-4bba03db54c4','2018-06-22 19:39:25','2018-06-22 19:41:00','beeid',0,'回去洗澡'),('a80dd82d-9240-4803-9a4e-4eb0ced5b8ab','2018-06-22 18:38:10','2018-06-22 18:43:00','beeid',0,'测试一下'),('b37c8c9c-7a03-4436-8cde-7caf10762c4d','2018-06-22 18:32:05','2018-06-22 18:39:00','beeid',0,'吃饭吃饭');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` varchar(50) NOT NULL COMMENT '用户Id',
  `user_nickname` varchar(20) NOT NULL COMMENT '用户昵称',
  `user_password` varchar(32) NOT NULL COMMENT '用户密码',
  `user_email` varchar(50) NOT NULL COMMENT '用户邮箱',
  `acti_state` int(11) NOT NULL COMMENT '激活状态，0表示未激活，1表示激活',
  `acti_code` varchar(50) NOT NULL COMMENT '随机验证码',
  `salt` varchar(50) NOT NULL COMMENT '随机盐，用于加密密码',
  `token_exptime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用于判断邮箱链接有效时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`user_id`,`user_nickname`,`user_password`,`user_email`,`acti_state`,`acti_code`,`salt`,`token_exptime`) values ('beanId','bean1234','abcfaad0e86d7df4d502b7fe54e600ea','280533456@qq.com',1,'760ca059-20b5-4f1d-9a9c-fc7d31b21168','59cefb4c-c09d-4b58-a64a-c423aca2a8d1','2018-05-31 22:40:00'),('beeid','bee','27cc4daddac839f467e2b6cd9449803d','hzf_echo@163.com',1,'8cca7301-824a-4c6c-8600-07cf96ec73ba','582c9b57-250c-46c5-9024-f0d96bc2f99a','2018-06-01 11:19:54'),('heiheihei','heiheihei','45253e76169e655778edbb1cd221bab8','280533456@qq.com',0,'1677575a-577b-4050-a69c-4191a8cf0011','95ff02e0-1cce-4464-8ea8-eff21c2dc02e','2018-06-24 20:08:40'),('jiayu','kokoyunnnn','45e072aaac5c0824722626d8ca43c32e','280533456@qq.com',0,'59d08b94-f746-4901-b3c6-7181359bb6d0','bfb959c7-19f5-46b9-9998-4b9da8c3ec15','2018-06-24 13:49:19'),('jiayunnn','kokoyunn','000eed3863bad567384cba3fb7801688','928576206@qq.com',0,'6a5158a1-56dd-4140-aab1-22b28cba7104','eef5c418-5b28-4e04-bdc6-293171e3ce8e','2018-06-17 11:17:43');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
