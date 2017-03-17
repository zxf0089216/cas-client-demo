/*
SQLyog v10.2 
MySQL - 5.6.10 : Database - wsriademo
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`wsriademo` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `wsriademo`;

/*Table structure for table `t_admin_user` */

DROP TABLE IF EXISTS `t_admin_user`;

CREATE TABLE `t_admin_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `login_name` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_name` (`login_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `t_admin_user` */

insert  into `t_admin_user`(`id`,`email`,`login_name`,`name`,`password`) values (1,'zhangxf21203@hundsun.com','zhangxf','张旭锋','e10adc3949ba59abbe56e057f20f883e');

/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `id` varchar(32) NOT NULL,
  `login_name` varchar(64) DEFAULT NULL,
  `real_name` varchar(64) DEFAULT NULL,
  `lock_flag` varchar(8) DEFAULT NULL COMMENT '0-正常\r\n            1-注销',
  `mobilephone` varchar(32) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `role_id` varchar(4000) DEFAULT NULL,
  `env_id` varchar(64) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL,
  `creator` varchar(64) DEFAULT NULL,
  `ext_field` varchar(128) DEFAULT NULL,
  `last_modified_by` varchar(225) DEFAULT NULL,
  `last_modified` datetime DEFAULT NULL,
  `auth_password` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_user` */

insert  into `tb_user`(`id`,`login_name`,`real_name`,`lock_flag`,`mobilephone`,`email`,`role_id`,`env_id`,`remark`,`creator`,`ext_field`,`last_modified_by`,`last_modified`,`auth_password`) values ('','user','普通用户',NULL,NULL,'user@hundsun.com','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('1','admin','张旭锋',NULL,NULL,'zhangxf21203@hundsun.com','1','001',NULL,NULL,NULL,NULL,NULL,'c4ca4238a0b923820dcc509a6f75849b'),('825439832506369','ssdfsd',NULL,NULL,NULL,'ssdf@163.com',NULL,'001',NULL,NULL,NULL,'admin','2016-10-09 16:24:21','24de13d52c8b3c4165c6928c8d8145a8'),('825822319476737','acm',NULL,NULL,NULL,'acm.@com','1','001002001',NULL,NULL,NULL,'admin','2016-10-17 14:23:00','24de13d52c8b3c4165c6928c8d8145a8'),('826299966816257','liqiao',NULL,NULL,NULL,'liqiao@hundsun.com','828672869138433','001002',NULL,NULL,NULL,'admin','2016-10-27 11:38:02','24de13d52c8b3c4165c6928c8d8145a8'),('A000000000093421','13738092486',NULL,NULL,NULL,'sdf@163.com','825007215214593','001001',NULL,NULL,NULL,'admin','2016-09-29 20:41:41','24de13d52c8b3c4165c6928c8d8145a8'),('A000000001555416','test11',NULL,NULL,NULL,'test@163.com','826689634435073','001',NULL,NULL,NULL,'13738092486','2016-11-04 13:21:12','8290f56e8a67f0e706d00e4a9e60fb92'),('A000000001712331','adminACM',NULL,NULL,NULL,NULL,NULL,'001',NULL,NULL,NULL,NULL,'2016-11-04 13:25:05','24de13d52c8b3c4165c6928c8d8145a8');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
