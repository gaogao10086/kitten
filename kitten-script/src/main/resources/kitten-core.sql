/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50533
Source Host           : localhost:3306
Source Database       : kitten-core

Target Server Type    : MYSQL
Target Server Version : 50533
File Encoding         : 65001

Date: 2014-10-10 18:15:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `adminId` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(80) NOT NULL,
  `legacyId` varchar(40) NOT NULL,
  `password` varchar(128) NOT NULL,
  `status` varchar(80) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime DEFAULT NULL,
  `createdBy` varchar(80) NOT NULL,
  `updatedBy` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`adminId`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COMMENT='The admin of kitten service system';

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('16', '路明燕', 'lumingyan', 'e99a18c428cb38d5f260853678922e03', 'ACTIVE', '2014-06-07 11:09:26', '2014-06-07 11:09:26', 'GaoYS', 'GaoYS');
INSERT INTO `admin` VALUES ('17', '巨蟹座', 'juxiezuo', 'e99a18c428cb38d5f260853678922e03', 'ACTIVE', '2014-06-07 11:09:41', '2014-06-07 11:09:41', 'GaoYS', 'GaoYS');
INSERT INTO `admin` VALUES ('18', '天蝎座', 'tianxiezuo', 'e99a18c428cb38d5f260853678922e03', 'INACTIVE', '2014-06-07 11:09:52', '2014-06-08 00:22:46', 'GaoYS', 'GaoYS');
INSERT INTO `admin` VALUES ('19', '里昂', 'liang', 'e99a18c428cb38d5f260853678922e03', 'ACTIVE', '2014-06-07 12:04:28', '2014-06-07 12:04:28', 'GaoYS', 'GaoYS');
INSERT INTO `admin` VALUES ('20', '皮尔斯', 'price', 'e99a18c428cb38d5f260853678922e03', 'ACTIVE', '2014-06-07 12:04:38', '2014-06-07 12:04:38', 'GaoYS', 'GaoYS');
INSERT INTO `admin` VALUES ('21', '威克斯', 'wekis', 'e99a18c428cb38d5f260853678922e03', 'ACTIVE', '2014-06-07 12:05:03', '2014-06-07 12:05:03', 'GaoYS', 'GaoYS');
INSERT INTO `admin` VALUES ('22', '吉尔', 'jill', 'e99a18c428cb38d5f260853678922e03', 'ACTIVE', '2014-06-07 12:05:38', '2014-06-07 12:05:38', 'GaoYS', 'GaoYS');
INSERT INTO `admin` VALUES ('23', '谢娃', 'shawa', 'e99a18c428cb38d5f260853678922e03', 'ACTIVE', '2014-06-07 12:06:04', '2014-06-07 12:06:04', 'GaoYS', 'GaoYS');
INSERT INTO `admin` VALUES ('24', '艾诗丽', 'aishili', 'e99a18c428cb38d5f260853678922e03', 'ACTIVE', '2014-06-07 12:06:34', '2014-06-07 12:06:34', 'GaoYS', 'GaoYS');
INSERT INTO `admin` VALUES ('25', '詹姆斯', 'james', 'e99a18c428cb38d5f260853678922e03', 'ACTIVE', '2014-06-07 12:06:55', '2014-06-07 12:06:55', 'GaoYS', 'GaoYS');
INSERT INTO `admin` VALUES ('26', '用户11', 'user11', 'e99a18c428cb38d5f260853678922e03', 'ACTIVE', '2014-07-02 09:15:37', '2014-07-02 09:15:37', 'GaoYS', 'GaoYS');
INSERT INTO `admin` VALUES ('27', '用户12', 'user12', 'e99a18c428cb38d5f260853678922e03', 'ACTIVE', '2014-07-02 09:15:49', '2014-07-02 09:16:12', 'GaoYS', 'GaoYS');
INSERT INTO `admin` VALUES ('28', '用户13', 'user13', 'e99a18c428cb38d5f260853678922e03', 'ACTIVE', '2014-07-02 09:16:03', '2014-07-02 09:16:03', 'GaoYS', 'GaoYS');
INSERT INTO `admin` VALUES ('29', '用户14', 'user14', 'e99a18c428cb38d5f260853678922e03', 'ACTIVE', '2014-07-02 09:16:28', '2014-07-02 09:16:28', 'GaoYS', 'GaoYS');
INSERT INTO `admin` VALUES ('30', '用户15', 'user15', 'e99a18c428cb38d5f260853678922e03', 'ACTIVE', '2014-07-02 09:16:48', '2014-07-02 09:16:48', 'GaoYS', 'GaoYS');
INSERT INTO `admin` VALUES ('31', '用户16', 'user16', 'e99a18c428cb38d5f260853678922e03', 'ACTIVE', '2014-07-02 09:17:21', '2014-07-02 09:17:21', 'GaoYS', 'GaoYS');
INSERT INTO `admin` VALUES ('32', '用户17', 'user17', 'e99a18c428cb38d5f260853678922e03', 'ACTIVE', '2014-07-02 09:17:34', '2014-07-02 09:17:34', 'GaoYS', 'GaoYS');
INSERT INTO `admin` VALUES ('33', '用户18', 'user18', 'e99a18c428cb38d5f260853678922e03', 'ACTIVE', '2014-07-02 09:17:43', '2014-07-02 09:17:43', 'GaoYS', 'GaoYS');
INSERT INTO `admin` VALUES ('34', '用户19', 'user19', 'e99a18c428cb38d5f260853678922e03', 'ACTIVE', '2014-07-02 09:18:01', '2014-07-02 09:18:01', 'GaoYS', 'GaoYS');
INSERT INTO `admin` VALUES ('35', '用户20', 'user20', 'e99a18c428cb38d5f260853678922e03', 'ACTIVE', '2014-07-02 09:18:16', '2014-07-02 09:18:16', 'GaoYS', 'GaoYS');
INSERT INTO `admin` VALUES ('36', '用户21', 'user21', 'e99a18c428cb38d5f260853678922e03', 'ACTIVE', '2014-07-02 09:18:30', '2014-07-02 09:18:30', 'GaoYS', 'GaoYS');
INSERT INTO `admin` VALUES ('37', '用户22', 'user22', 'e99a18c428cb38d5f260853678922e03', 'ACTIVE', '2014-07-02 09:18:47', '2014-07-02 09:18:47', 'GaoYS', 'GaoYS');
INSERT INTO `admin` VALUES ('38', '用户23', 'user23', 'e99a18c428cb38d5f260853678922e03', 'ACTIVE', '2014-07-02 09:18:56', '2014-07-02 09:18:56', 'GaoYS', 'GaoYS');
INSERT INTO `admin` VALUES ('39', '用户24', 'user24', 'e99a18c428cb38d5f260853678922e03', 'ACTIVE', '2014-07-02 09:19:10', '2014-07-02 09:19:10', 'GaoYS', 'GaoYS');
INSERT INTO `admin` VALUES ('40', '用户25', 'user25', 'e99a18c428cb38d5f260853678922e03', 'ACTIVE', '2014-07-02 09:19:19', '2014-07-02 09:19:19', 'GaoYS', 'GaoYS');
INSERT INTO `admin` VALUES ('41', 'GaoYS', 'gaoyuansheng', 'e99a18c428cb38d5f260853678922e03', 'ACTIVE', '2014-07-03 11:22:49', '2014-07-03 11:22:49', 'GaoYS', 'GaoYS');

-- ----------------------------
-- Table structure for adminrole
-- ----------------------------
DROP TABLE IF EXISTS `adminrole`;
CREATE TABLE `adminrole` (
  `roleId` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(80) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` varchar(80) DEFAULT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` timestamp NULL DEFAULT NULL,
  `createdBy` varchar(80) NOT NULL,
  `updatedBy` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`roleId`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='The admin role of kitten service system';

-- ----------------------------
-- Records of adminrole
-- ----------------------------
INSERT INTO `adminrole` VALUES ('13', '管理员', '管理员负责管理系统配置，最高权限', 'ACTIVE', '2013-12-19 17:53:58', '2014-06-04 23:07:20', 'admin', 'GaoYS');
INSERT INTO `adminrole` VALUES ('14', '浏览者', '可浏览部分报表', 'ACTIVE', '2014-06-07 12:34:43', '2014-06-07 12:34:52', 'GaoYS', 'GaoYS');

-- ----------------------------
-- Table structure for adminrolemember
-- ----------------------------
DROP TABLE IF EXISTS `adminrolemember`;
CREATE TABLE `adminrolemember` (
  `adminId` bigint(20) NOT NULL,
  `roleId` bigint(20) NOT NULL,
  PRIMARY KEY (`roleId`,`adminId`),
  KEY `fk_admin_roleId` (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of adminrolemember
-- ----------------------------
INSERT INTO `adminrolemember` VALUES ('1', '13');
INSERT INTO `adminrolemember` VALUES ('12', '13');
INSERT INTO `adminrolemember` VALUES ('15', '13');
INSERT INTO `adminrolemember` VALUES ('41', '13');
INSERT INTO `adminrolemember` VALUES ('185', '13');
INSERT INTO `adminrolemember` VALUES ('188', '13');
INSERT INTO `adminrolemember` VALUES ('4', '14');
INSERT INTO `adminrolemember` VALUES ('5', '14');
INSERT INTO `adminrolemember` VALUES ('7', '14');
INSERT INTO `adminrolemember` VALUES ('9', '14');
INSERT INTO `adminrolemember` VALUES ('11', '14');
INSERT INTO `adminrolemember` VALUES ('111', '14');
INSERT INTO `adminrolemember` VALUES ('138', '14');
INSERT INTO `adminrolemember` VALUES ('186', '14');
INSERT INTO `adminrolemember` VALUES ('7', '15');
INSERT INTO `adminrolemember` VALUES ('4', '16');
INSERT INTO `adminrolemember` VALUES ('5', '16');
INSERT INTO `adminrolemember` VALUES ('7', '16');
INSERT INTO `adminrolemember` VALUES ('9', '16');
INSERT INTO `adminrolemember` VALUES ('11', '16');
INSERT INTO `adminrolemember` VALUES ('186', '16');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `menuID` bigint(20) NOT NULL AUTO_INCREMENT,
  `menuName` varchar(20) NOT NULL,
  `parentID` bigint(20) NOT NULL,
  `url` varchar(50) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime DEFAULT NULL,
  `createdBy` varchar(80) NOT NULL,
  `updatedBy` varchar(80) DEFAULT NULL,
  `status` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`menuID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '首页', '0', 'platform/main/index', '2013-12-19 17:29:15', '2013-12-19 17:29:15', 'admin', 'admin', 'ACTIVE');
INSERT INTO `menu` VALUES ('2', '权限管理', '0', '', '2014-01-07 10:28:14', '2014-01-07 10:30:56', 'admin', 'admin', 'ACTIVE');
INSERT INTO `menu` VALUES ('3', '用户管理', '2', 'platform/permission/admin/index', '2014-01-07 10:13:00', '2014-01-07 10:13:02', 'admin', 'admin', 'ACTIVE');
INSERT INTO `menu` VALUES ('4', '角色管理', '2', 'platform/permission/role/index', '2014-01-07 10:12:17', '2014-01-07 10:12:19', 'admin', 'admin', 'ACTIVE');
INSERT INTO `menu` VALUES ('5', '菜单管理', '2', 'platform/permission/menu/index', '2014-01-07 10:11:46', '2014-01-07 10:11:49', 'admin', 'admin', 'ACTIVE');
INSERT INTO `menu` VALUES ('6', 'Demo', '0', '', '2014-07-02 09:23:18', '2014-07-02 10:46:57', 'GaoYS', 'GaoYS', 'ACTIVE');
INSERT INTO `menu` VALUES ('7', '主面板', '6', 'portal', '2014-07-02 09:24:12', '2014-07-02 09:24:12', 'GaoYS', 'GaoYS', 'ACTIVE');

-- ----------------------------
-- Table structure for rolemenumember
-- ----------------------------
DROP TABLE IF EXISTS `rolemenumember`;
CREATE TABLE `rolemenumember` (
  `roleID` bigint(20) NOT NULL,
  `menuID` bigint(20) NOT NULL,
  PRIMARY KEY (`roleID`,`menuID`),
  KEY `FK133F560FA38F720B` (`menuID`),
  KEY `FK133F560FB0E4D1C4` (`roleID`),
  CONSTRAINT `FK133F560FA38F720B` FOREIGN KEY (`menuID`) REFERENCES `menu` (`menuID`),
  CONSTRAINT `FK133F560FB0E4D1C4` FOREIGN KEY (`roleID`) REFERENCES `adminrole` (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rolemenumember
-- ----------------------------
INSERT INTO `rolemenumember` VALUES ('13', '1');
INSERT INTO `rolemenumember` VALUES ('13', '2');
INSERT INTO `rolemenumember` VALUES ('13', '3');
INSERT INTO `rolemenumember` VALUES ('13', '4');
INSERT INTO `rolemenumember` VALUES ('13', '5');
INSERT INTO `rolemenumember` VALUES ('13', '6');
INSERT INTO `rolemenumember` VALUES ('13', '7');
