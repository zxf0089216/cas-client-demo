产生keystore
keytool -genkeypair -alias cas-server -keyalg RSA -keysize 2048 -storepass changeit -keystore server.keystore

导出证书
keytool -exportcert -alias cas-server -file cas-server.crt -storepass changeit  -keystore server.keystore

导入证书
keytool -importcert -alias cas-server -file cas-server.crt -keystore "%JAVA_HOME%\jre\lib\security\cacerts" -storepass changeit -noprompt

keytool -importcert -alias cas-server -file cas-server.crt -keystore "C:\Program Files\Java\jdk1.7.0_51\jre\lib\security\cacerts" -storepass changeit -noprompt

查看 keypair：
keytool -list -storepass changeit -keystore server.keystore

keytool -list -storepass changeit -keystore "%JAVA_HOME%\jre\lib\security\cacerts"

删除 keypair：
keytool -delete -alias cas-server -storepass changeit

keytool -delete -trustcacerts -alias cas-server -keystore "C:\Program Files\Java\jdk1.7.0_51\jre\lib\security\cacerts" -storepass changeit


create table t_admin_user (
    id bigint not null auto_increment,
    email varchar(255),
    login_name varchar(255) not null unique,
    name varchar(255),
    password varchar(255),
    primary key (id)
) ENGINE=InnoDB;


CREATE TABLE `tb_user` (
  `id` VARCHAR(32) NOT NULL,
  `login_name` VARCHAR(64) DEFAULT NULL,
  `real_name` VARCHAR(64) DEFAULT NULL,
  `lock_flag` VARCHAR(8) DEFAULT NULL COMMENT '0-正常\r\n            1-注销',
  `mobilephone` VARCHAR(32) DEFAULT NULL,
  `email` VARCHAR(128) DEFAULT NULL,
  `role_id` VARCHAR(4000) DEFAULT NULL,
  `env_id` VARCHAR(64) DEFAULT NULL,
  `remark` VARCHAR(256) DEFAULT NULL,
  `creator` VARCHAR(64) DEFAULT NULL,
  `ext_field` VARCHAR(128) DEFAULT NULL,
  `last_modified_by` VARCHAR(225) DEFAULT NULL,
  `last_modified` DATETIME DEFAULT NULL,
  `auth_password` VARCHAR(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


INSERT  INTO `tb_user`(`id`,`login_name`,`real_name`,`lock_flag`,`mobilephone`,`email`,`role_id`,`env_id`,`remark`,`creator`,`ext_field`,`last_modified_by`,`last_modified`,`auth_password`) VALUES 
('1','admin',NULL,NULL,NULL,NULL,'1','001',NULL,NULL,NULL,NULL,NULL,'c4ca4238a0b923820dcc509a6f75849b');


