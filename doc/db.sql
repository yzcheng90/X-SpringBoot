/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.201
Source Server Version : 50718
Source Host           : 192.168.0.201:3306
Source Database       : x_springboot

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2019-04-22 16:36:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `config_key` varchar(50) DEFAULT NULL COMMENT 'key',
  `config_value` varchar(2000) DEFAULT NULL COMMENT 'value',
  `config_status` tinyint(4) DEFAULT '1' COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `key` (`config_key`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='系统配置信息表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('1', 'CLOUD_STORAGE_CONFIG_KEY', '{\"type\":1,\"qiniuDomain\":\"http://oss.sukeintel.com.qiniudns.com\",\"qiniuPrefix\":\"upload\",\"qiniuAccessKey\":\"fRCR9phC7SE-V9ZHzYUSjVqB9pKPkgq2soRcSIid\",\"qiniuSecretKey\":\"HvlNtDjrYLhokEGn-idybYVpjM6fKxpW271gd7y1\",\"qiniuBucketName\":\"sukeintel\",\"aliyunDomain\":\"\",\"aliyunPrefix\":\"\",\"aliyunEndPoint\":\"\",\"aliyunAccessKeyId\":\"\",\"aliyunAccessKeySecret\":\"\",\"aliyunBucketName\":\"\",\"qcloudDomain\":\"\",\"qcloudPrefix\":\"\",\"qcloudSecretId\":\"\",\"qcloudSecretKey\":\"\",\"qcloudBucketName\":\"\"}', '0', '云存储配置信息');
INSERT INTO `sys_config` VALUES ('2', 'test', 'test', '1', '测试');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='系统日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES ('1', 'admin', '修改定时任务', 'com.suke.czx.modules.job.controller.ScheduleJobController.update()', '{\"jobId\":1,\"beanName\":\"testTask\",\"methodName\":\"test\",\"params\":\"test\",\"cronExpression\":\"0 0/30 * * * ?\",\"status\":0,\"remark\":\"有参数测试\",\"createTime\":\"Dec 1, 2016 11:16:46 PM\"}', '78', '0:0:0:0:0:0:0:1', '2018-01-08 17:22:23');
INSERT INTO `sys_log` VALUES ('2', 'sk', '用户登录', 'com.suke.czx.modules.sys.controller.SysLoginController.login()', '\"sk\"', '1713', '0:0:0:0:0:0:0:1', '2019-04-18 13:59:11');
INSERT INTO `sys_log` VALUES ('3', 'sk', '用户登录', 'com.suke.czx.modules.sys.controller.SysLoginController.login()', '\"sk\"', '11', '0:0:0:0:0:0:0:1', '2019-04-18 13:59:43');
INSERT INTO `sys_log` VALUES ('4', 'admin', '保存配置', 'com.suke.czx.modules.sys.controller.SysConfigController.save()', '{\"id\":2,\"configKey\":\"test\",\"configValue\":\"test\",\"remark\":\"测试\"}', '65', '0:0:0:0:0:0:0:1', '2019-04-18 15:07:25');
INSERT INTO `sys_log` VALUES ('5', 'admin', '保存菜单', 'com.suke.czx.modules.sys.controller.SysMenuController.save()', '{\"menuId\":31,\"parentId\":0,\"parentName\":\"一级菜单\",\"name\":\"测试\",\"type\":0,\"orderNum\":0}', '8', '0:0:0:0:0:0:0:1', '2019-04-18 15:23:03');
INSERT INTO `sys_log` VALUES ('6', 'admin', '删除菜单', 'com.suke.czx.modules.sys.controller.SysMenuController.delete()', '31', '7', '0:0:0:0:0:0:0:1', '2019-04-18 15:24:26');
INSERT INTO `sys_log` VALUES ('7', 'admin', '删除角色', 'com.suke.czx.modules.sys.controller.SysRoleController.delete()', '[5]', '9', '0:0:0:0:0:0:0:1', '2019-04-18 16:00:11');
INSERT INTO `sys_log` VALUES ('8', 'admin', '保存角色', 'com.suke.czx.modules.sys.controller.SysRoleController.save()', '{\"roleId\":6,\"roleName\":\"测试2\",\"remark\":\"测试2\",\"createUserId\":1,\"menuIdList\":[1,3,19,20,21,22],\"createTime\":\"Apr 18, 2019 4:02:00 PM\"}', '76', '0:0:0:0:0:0:0:1', '2019-04-18 16:02:01');
INSERT INTO `sys_log` VALUES ('9', 'admin', '删除角色', 'com.suke.czx.modules.sys.controller.SysRoleController.delete()', '[6]', '5', '0:0:0:0:0:0:0:1', '2019-04-18 16:02:09');
INSERT INTO `sys_log` VALUES ('10', 'admin', '删除用户', 'com.suke.czx.modules.sys.controller.SysUserController.delete()', '[7]', '8', '0:0:0:0:0:0:0:1', '2019-04-18 16:04:59');
INSERT INTO `sys_log` VALUES ('11', 'admin', '保存用户', 'com.suke.czx.modules.sys.controller.SysUserController.save()', '{\"userId\":8,\"username\":\"sk\",\"password\":\"f84d76f5e13503d2ef9580eb8472615c395ea2010ac582035d86a4bd7d9ac73c\",\"salt\":\"v4rnaIJ4zl29yGhXdjOO\",\"email\":\"sk@sk.com\",\"mobile\":\"12345678963\",\"status\":1,\"createUserId\":1,\"createTime\":\"Apr 18, 2019 4:08:20 PM\",\"roleIdList\":[]}', '66', '0:0:0:0:0:0:0:1', '2019-04-18 16:08:21');
INSERT INTO `sys_log` VALUES ('12', 'admin', '删除用户', 'com.suke.czx.modules.sys.controller.SysUserController.delete()', '[8]', '9', '0:0:0:0:0:0:0:1', '2019-04-18 16:08:25');
INSERT INTO `sys_log` VALUES ('13', 'admin', '保存角色', 'com.suke.czx.modules.sys.controller.SysRoleController.save()', '{\"roleId\":7,\"roleName\":\"1\",\"remark\":\"1\",\"createUserId\":1,\"menuIdList\":[1,2,15,16,17,18],\"createTime\":\"Apr 18, 2019 4:09:08 PM\"}', '23', '0:0:0:0:0:0:0:1', '2019-04-18 16:09:08');
INSERT INTO `sys_log` VALUES ('14', 'admin', '保存角色', 'com.suke.czx.modules.sys.controller.SysRoleController.save()', '{\"roleId\":8,\"roleName\":\"2\",\"remark\":\"2\",\"createUserId\":1,\"menuIdList\":[1,2,15,16,17,18],\"createTime\":\"Apr 18, 2019 4:09:15 PM\"}', '9', '0:0:0:0:0:0:0:1', '2019-04-18 16:09:15');
INSERT INTO `sys_log` VALUES ('15', 'admin', '保存角色', 'com.suke.czx.modules.sys.controller.SysRoleController.save()', '{\"roleId\":9,\"roleName\":\"3\",\"remark\":\"3\",\"createUserId\":1,\"menuIdList\":[1,6,7,8,9,10,11,12,13,14],\"createTime\":\"Apr 18, 2019 4:09:22 PM\"}', '8', '0:0:0:0:0:0:0:1', '2019-04-18 16:09:23');
INSERT INTO `sys_log` VALUES ('16', 'admin', '删除角色', 'com.suke.czx.modules.sys.controller.SysRoleController.delete()', '[7,8,9]', '5', '0:0:0:0:0:0:0:1', '2019-04-18 16:09:29');
INSERT INTO `sys_log` VALUES ('17', 'admin', '保存用户', 'com.suke.czx.modules.sys.controller.SysUserController.save()', '{\"userId\":2,\"username\":\"sk\",\"password\":\"cc523ea48e608b07a8ddc08aa774e07a0dc176bebe86bf528bb79775ea73a26b\",\"salt\":\"rKn3NKzdWEyuIqGaoyiZ\",\"email\":\"sk@sk.com\",\"mobile\":\"18365412365\",\"status\":1,\"createUserId\":1,\"createTime\":\"Apr 22, 2019 11:32:18 AM\",\"roleIdList\":[]}', '76', '0:0:0:0:0:0:0:1', '2019-04-22 11:32:18');
INSERT INTO `sys_log` VALUES ('18', 'admin', '删除用户', 'com.suke.czx.modules.sys.controller.SysUserController.delete()', '[2]', '8', '0:0:0:0:0:0:0:1', '2019-04-22 11:32:23');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '系统管理', null, null, '0', 'fa fa-cog', '0');
INSERT INTO `sys_menu` VALUES ('2', '1', '管理员列表', 'modules/sys/user.html', null, '1', 'fa fa-user', '1');
INSERT INTO `sys_menu` VALUES ('3', '1', '角色管理', 'modules/sys/role.html', null, '1', 'fa fa-user-secret', '2');
INSERT INTO `sys_menu` VALUES ('4', '1', '菜单管理', 'modules/sys/menu.html', null, '1', 'fa fa-th-list', '3');
INSERT INTO `sys_menu` VALUES ('15', '2', '查看', null, 'sys:user:list,sys:user:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('16', '2', '新增', null, 'sys:user:save,sys:role:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('17', '2', '修改', null, 'sys:user:update,sys:role:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('18', '2', '删除', null, 'sys:user:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('19', '3', '查看', null, 'sys:role:list,sys:role:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('20', '3', '新增', null, 'sys:role:save,sys:menu:list', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('21', '3', '修改', null, 'sys:role:update,sys:menu:list', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('22', '3', '删除', null, 'sys:role:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('23', '4', '查看', null, 'sys:menu:list,sys:menu:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('24', '4', '新增', null, 'sys:menu:save,sys:menu:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('25', '4', '修改', null, 'sys:menu:update,sys:menu:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('26', '4', '删除', null, 'sys:menu:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('27', '1', '参数管理', 'modules/sys/config.html', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', '1', 'fa fa-sun-o', '6');
INSERT INTO `sys_menu` VALUES ('29', '1', '系统日志', 'modules/sys/log.html', 'sys:log:list', '1', 'fa fa-file-text-o', '7');
INSERT INTO `sys_menu` VALUES ('30', '1', '文件上传', 'modules/oss/oss.html', 'sys:oss:all', '1', 'fa fa-file-image-o', '6');
INSERT INTO `sys_menu` VALUES ('31', '0', 'Swagger', null, null, '0', 'fa fa-cog', '0');
INSERT INTO `sys_menu` VALUES ('32', '31', '在线API', 'swagger/index.html', null, '1', 'fa fa-file-text-o', '1');

-- ----------------------------
-- Table structure for sys_oss
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss`;
CREATE TABLE `sys_oss` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='文件上传';

-- ----------------------------
-- Records of sys_oss
-- ----------------------------
INSERT INTO `sys_oss` VALUES ('1', 'http://oss.sukeintel.com.qiniudns.com/upload/20190401/1964f6d819b54852a7a87d855f240827.png', '2019-04-01 09:50:31');
INSERT INTO `sys_oss` VALUES ('2', 'http://oss.sukeintel.com/upload/20190401/35d86809a5564d1c82ec95f5ea33b0aa.png', '2019-04-01 11:01:18');
INSERT INTO `sys_oss` VALUES ('3', 'http://oss.sukeintel.com/upload/20190401/e0a49d8bd0ee4f66affc526c01e4fc11.png', '2019-04-01 11:03:05');
INSERT INTO `sys_oss` VALUES ('4', 'http://oss.sukeintel.com/upload/20190401/229117ef71a34d69b113f072f00f2b84.png', '2019-04-01 11:18:18');
INSERT INTO `sys_oss` VALUES ('5', 'http://oss.sukeintel.com/upload/20190401/4669fb66bea94dc48c75d9b0fbdcc1df.png', '2019-04-01 11:51:56');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('4', '管理员', '管理员', '1', '2019-04-18 10:12:05');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1', '4', '1');
INSERT INTO `sys_role_menu` VALUES ('2', '4', '2');
INSERT INTO `sys_role_menu` VALUES ('3', '4', '15');
INSERT INTO `sys_role_menu` VALUES ('4', '4', '16');
INSERT INTO `sys_role_menu` VALUES ('10', '4', '21');
INSERT INTO `sys_role_menu` VALUES ('11', '4', '22');
INSERT INTO `sys_role_menu` VALUES ('12', '5', '1');
INSERT INTO `sys_role_menu` VALUES ('13', '5', '3');
INSERT INTO `sys_role_menu` VALUES ('14', '5', '19');
INSERT INTO `sys_role_menu` VALUES ('15', '5', '20');
INSERT INTO `sys_role_menu` VALUES ('16', '5', '21');
INSERT INTO `sys_role_menu` VALUES ('17', '5', '22');
INSERT INTO `sys_role_menu` VALUES ('18', '5', '4');
INSERT INTO `sys_role_menu` VALUES ('19', '5', '23');
INSERT INTO `sys_role_menu` VALUES ('20', '5', '24');
INSERT INTO `sys_role_menu` VALUES ('21', '5', '25');
INSERT INTO `sys_role_menu` VALUES ('22', '5', '26');
INSERT INTO `sys_role_menu` VALUES ('23', '6', '1');
INSERT INTO `sys_role_menu` VALUES ('24', '6', '3');
INSERT INTO `sys_role_menu` VALUES ('25', '6', '19');
INSERT INTO `sys_role_menu` VALUES ('26', '6', '20');
INSERT INTO `sys_role_menu` VALUES ('27', '6', '21');
INSERT INTO `sys_role_menu` VALUES ('28', '6', '22');
INSERT INTO `sys_role_menu` VALUES ('29', '7', '1');
INSERT INTO `sys_role_menu` VALUES ('30', '7', '2');
INSERT INTO `sys_role_menu` VALUES ('31', '7', '15');
INSERT INTO `sys_role_menu` VALUES ('32', '7', '16');
INSERT INTO `sys_role_menu` VALUES ('33', '7', '17');
INSERT INTO `sys_role_menu` VALUES ('34', '7', '18');
INSERT INTO `sys_role_menu` VALUES ('35', '8', '1');
INSERT INTO `sys_role_menu` VALUES ('36', '8', '2');
INSERT INTO `sys_role_menu` VALUES ('37', '8', '15');
INSERT INTO `sys_role_menu` VALUES ('38', '8', '16');
INSERT INTO `sys_role_menu` VALUES ('39', '8', '17');
INSERT INTO `sys_role_menu` VALUES ('40', '8', '18');
INSERT INTO `sys_role_menu` VALUES ('41', '9', '1');
INSERT INTO `sys_role_menu` VALUES ('42', '9', '6');
INSERT INTO `sys_role_menu` VALUES ('43', '9', '7');
INSERT INTO `sys_role_menu` VALUES ('44', '9', '8');
INSERT INTO `sys_role_menu` VALUES ('45', '9', '9');
INSERT INTO `sys_role_menu` VALUES ('46', '9', '10');
INSERT INTO `sys_role_menu` VALUES ('47', '9', '11');
INSERT INTO `sys_role_menu` VALUES ('48', '9', '12');
INSERT INTO `sys_role_menu` VALUES ('49', '9', '13');
INSERT INTO `sys_role_menu` VALUES ('50', '9', '14');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) DEFAULT NULL COMMENT '盐',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', 'YzcmCZNvbXocrsz9dm8e', 'yzcheng90@qq.com', '13612345678', '1', '1', '2016-11-11 11:11:11');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '7', '5');

-- ----------------------------
-- Table structure for sys_user_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token` (
  `user_id` bigint(20) NOT NULL,
  `token` varchar(100) NOT NULL COMMENT 'token',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户Token';

-- ----------------------------
-- Records of sys_user_token
-- ----------------------------
INSERT INTO `sys_user_token` VALUES ('1', '152a9c69193ed60261c6feec3cce02e0', '2019-04-23 04:34:07', '2019-04-22 16:34:07');
INSERT INTO `sys_user_token` VALUES ('7', 'cc3c74959d0eaacce1a3c3410ccc105b', '2019-04-19 02:55:21', '2019-04-18 14:55:21');
