/*
Navicat MySQL Data Transfer

Source Server         : 股票
Source Server Version : 50555
Source Host           : 47.94.37.25:3306
Source Database       : shares

Target Server Type    : MYSQL
Target Server Version : 50555
File Encoding         : 65001

Date: 2017-07-14 17:27:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for accountinfo
-- ----------------------------
DROP TABLE IF EXISTS `accountinfo`;
CREATE TABLE `accountinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键，自增，非空',
  `username` varchar(16) DEFAULT NULL COMMENT '账号',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `mobile` varchar(16) DEFAULT NULL COMMENT '手机',
  `name` varchar(16) DEFAULT NULL COMMENT '名称',
  `type` int(11) DEFAULT NULL COMMENT '所属类型 1平台 2客户',
  `role` int(11) DEFAULT NULL COMMENT '角色 \r\n0 平台管理员 1平台运维\r\n2公司管理员 3公司运维\r\n4经理 5业务员',
  `fcompanyid` bigint(20) DEFAULT NULL COMMENT '外键关联公司id',
  `proportion` double DEFAULT NULL COMMENT '分成比例',
  `extensionurl` varchar(128) DEFAULT NULL COMMENT '推广链接（role为5时）',
  `fparentid` bigint(20) DEFAULT NULL COMMENT '上级id，外键关联账户id',
  `createtime` datetime DEFAULT NULL COMMENT '时间',
  `state` int(11) DEFAULT NULL COMMENT '帐户状态 0正常 -1异常',
  `defaultaccount` int(11) DEFAULT NULL COMMENT '默认帐户 0否 1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COMMENT='后台帐户角色表';

-- ----------------------------
-- Table structure for accountpermission
-- ----------------------------
DROP TABLE IF EXISTS `accountpermission`;
CREATE TABLE `accountpermission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键，自增，非空',
  `faccountid` bigint(20) DEFAULT NULL COMMENT '外键关联管理账户表',
  `fmenuid` bigint(20) DEFAULT NULL COMMENT '外键关联菜单表',
  `state` int(11) DEFAULT NULL COMMENT '状态 0正常  -1异常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='帐户权限表 ';

-- ----------------------------
-- Table structure for capitaldetail
-- ----------------------------
DROP TABLE IF EXISTS `capitaldetail`;
CREATE TABLE `capitaldetail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键，自增，非空',
  `fuserinfoid` bigint(20) DEFAULT NULL COMMENT '外键关联用户id',
  `type` int(11) DEFAULT NULL COMMENT '-2外汇购买 -1股票购买 ',
  `capital` double DEFAULT NULL COMMENT '资金',
  `createtime` datetime DEFAULT NULL COMMENT '时间',
  `state` int(11) DEFAULT NULL COMMENT '状态 0正常  -1失效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资金明细表';

-- ----------------------------
-- Table structure for cashinfo
-- ----------------------------
DROP TABLE IF EXISTS `cashinfo`;
CREATE TABLE `cashinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  `fuserinfoid` bigint(20) DEFAULT NULL COMMENT '外键关联用户表',
  `amount` double DEFAULT NULL COMMENT '提现金额',
  `createtime` datetime DEFAULT NULL COMMENT '申请时间',
  `state` int(11) DEFAULT NULL COMMENT '0 新申请 1已通过 2已打款 -1 未通过',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='提现表';

-- ----------------------------
-- Table structure for company
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键，自增，非空',
  `company` varchar(32) DEFAULT NULL COMMENT '公司名称',
  `state` int(11) DEFAULT NULL COMMENT '0正常  -1解约',
  `createtime` datetime DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='公司信息表 ';

-- ----------------------------
-- Table structure for documentary
-- ----------------------------
DROP TABLE IF EXISTS `documentary`;
CREATE TABLE `documentary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fuserinfoid` bigint(20) DEFAULT NULL COMMENT '外键关联用户表',
  `ffollowuserinfoid` bigint(20) DEFAULT NULL COMMENT '跟单牛人id',
  `money` int(11) DEFAULT NULL COMMENT '跟单金额',
  `state` int(11) DEFAULT NULL COMMENT '0正常 -1取消',
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='跟单表';

-- ----------------------------
-- Table structure for exampleapply
-- ----------------------------
DROP TABLE IF EXISTS `exampleapply`;
CREATE TABLE `exampleapply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fuserinfoid` bigint(20) DEFAULT NULL COMMENT '外键关联用户表',
  `time` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL COMMENT '状态 \r\n0新申请 1已通过 -1已拒绝\r\n',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='牛人申请表';

-- ----------------------------
-- Table structure for exampleplan
-- ----------------------------
DROP TABLE IF EXISTS `exampleplan`;
CREATE TABLE `exampleplan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fuserinfoid` bigint(20) DEFAULT NULL COMMENT '外键关联用户表',
  `targetprofit` int(11) DEFAULT NULL COMMENT '目标收益',
  `stopline` int(11) DEFAULT NULL COMMENT '止损线',
  `starttime` datetime DEFAULT NULL COMMENT '计划开始时间',
  `endtime` datetime DEFAULT NULL COMMENT '计划结束时间',
  `createtime` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL COMMENT '0新发布 1运行中 2已结束',
  `ischoiceness` int(11) DEFAULT NULL COMMENT '是否精选0否1是',
  `type` int(11) DEFAULT NULL COMMENT '0股票 1外汇',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='牛人计划表';

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fuserinfoid` bigint(20) DEFAULT NULL COMMENT '外键关联用户id',
  `title` varchar(16) DEFAULT NULL COMMENT '标题',
  `content` varchar(128) DEFAULT NULL COMMENT '内容',
  `createtime` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `replayto` varchar(128) DEFAULT NULL COMMENT '回复用户的内容（可空）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='意见反馈';

-- ----------------------------
-- Table structure for fileinfo
-- ----------------------------
DROP TABLE IF EXISTS `fileinfo`;
CREATE TABLE `fileinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(64) DEFAULT NULL COMMENT '图片url地址',
  `state` int(11) DEFAULT NULL COMMENT '-1已删除  0新上传未被引用 1已引用',
  `time` datetime DEFAULT NULL COMMENT '时间',
  `filename` varchar(64) DEFAULT NULL COMMENT '图片名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='图片信息表';

-- ----------------------------
-- Table structure for follow
-- ----------------------------
DROP TABLE IF EXISTS `follow`;
CREATE TABLE `follow` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fuserinfoid` bigint(20) DEFAULT NULL COMMENT '外键关联用户id 关注人',
  `ffollowedid` bigint(20) DEFAULT NULL COMMENT '被关注人id',
  `state` int(11) DEFAULT NULL COMMENT '0生效 -1取消',
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='关注表';

-- ----------------------------
-- Table structure for foreignexchange
-- ----------------------------
DROP TABLE IF EXISTS `foreignexchange`;
CREATE TABLE `foreignexchange` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  `fuserinfoid` bigint(20) DEFAULT NULL COMMENT '外键关联用户表',
  `foreignexchangename` varchar(32) DEFAULT NULL COMMENT '外汇名称',
  `amount` double DEFAULT NULL COMMENT '金额',
  `warehousenums` int(11) DEFAULT NULL COMMENT '持仓手数',
  `purchase` double(255,0) DEFAULT NULL COMMENT '买入',
  `sellout` double DEFAULT NULL COMMENT '卖出',
  `profitloss` double DEFAULT NULL COMMENT '盈亏',
  `time` datetime DEFAULT NULL COMMENT '时间',
  `state` int(11) DEFAULT NULL COMMENT '状态 0持仓  -1卖出',
  `isplan` int(11) DEFAULT NULL COMMENT '0非计划 1计划（计划中为纯数据）是否为牛人计划中购买股票标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户外汇表';

-- ----------------------------
-- Table structure for foreignexchangeentrust
-- ----------------------------
DROP TABLE IF EXISTS `foreignexchangeentrust`;
CREATE TABLE `foreignexchangeentrust` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fuserinfoid` bigint(20) DEFAULT NULL COMMENT '外键关联用户表',
  `foreignexchangename` varchar(32) DEFAULT NULL COMMENT '外汇名称',
  `presentprice` double(10,2) DEFAULT NULL COMMENT '现价',
  `entrustprice` double(10,2) DEFAULT NULL COMMENT '委托价',
  `entrustnums` int(11) DEFAULT NULL COMMENT '委托数量',
  `frozenamount` double DEFAULT NULL COMMENT '冻结资金',
  `time` datetime DEFAULT NULL COMMENT 'time',
  `state` int(11) DEFAULT NULL COMMENT '状态',
  `isplan` int(11) DEFAULT NULL COMMENT '0非计划 1计划（计划中为纯数据）是否为牛人计划中购买股票标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户外汇委托挂单表';

-- ----------------------------
-- Table structure for goldsdetail
-- ----------------------------
DROP TABLE IF EXISTS `goldsdetail`;
CREATE TABLE `goldsdetail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键，自增，非空',
  `fuserinfoid` bigint(20) DEFAULT NULL COMMENT '外键关联用户id',
  `type` int(11) DEFAULT NULL COMMENT '-5 打赏别人 -4观摩计划 -3抢购计划 -2提现 -1订阅 1充值 2被人打赏 3邀请好友',
  `golds` double DEFAULT NULL COMMENT '金币',
  `createtime` datetime DEFAULT NULL COMMENT '时间',
  `state` int(11) DEFAULT NULL COMMENT '状态  0正常  -1失效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='金币明细表 ';

-- ----------------------------
-- Table structure for menuinfo
-- ----------------------------
DROP TABLE IF EXISTS `menuinfo`;
CREATE TABLE `menuinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键，自增，非空',
  `menuname` varchar(16) DEFAULT NULL COMMENT '菜单名',
  `url` varchar(32) DEFAULT NULL COMMENT '链接',
  `img` varchar(16) DEFAULT NULL COMMENT '图标',
  `order` int(11) DEFAULT NULL COMMENT '序号',
  `type` int(11) DEFAULT NULL COMMENT '所属类型 ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='菜单配置表';

-- ----------------------------
-- Table structure for messageinfo
-- ----------------------------
DROP TABLE IF EXISTS `messageinfo`;
CREATE TABLE `messageinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fuserinfoid` bigint(20) DEFAULT NULL COMMENT '接收人 外键关联用户id',
  `fsenduserid` bigint(20) DEFAULT NULL COMMENT '发送人 外键关联用户id',
  `content` varchar(512) DEFAULT NULL COMMENT '消息内容',
  `state` int(11) DEFAULT NULL COMMENT '0未读  1已读',
  `fnotifyid` bigint(20) DEFAULT NULL COMMENT '外键关联消息id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='私信表';

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `target` int(11) DEFAULT NULL COMMENT '发送对象0所有人 1所有用户 2所有公司 3指定公司',
  `fcompanyid` bigint(20) DEFAULT NULL COMMENT '公司id',
  `noticename` varchar(32) DEFAULT NULL COMMENT '公告标题',
  `noticecontent` text COMMENT '公告内容',
  `state` int(11) DEFAULT NULL COMMENT '0即时1定时',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `sendtime` datetime DEFAULT NULL COMMENT '发送时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公告表';

-- ----------------------------
-- Table structure for notifyinfo
-- ----------------------------
DROP TABLE IF EXISTS `notifyinfo`;
CREATE TABLE `notifyinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` int(11) DEFAULT NULL COMMENT '0系统消息  1私信',
  `fuserinfoid` bigint(20) DEFAULT NULL COMMENT '接收人 外键关联用户id',
  `fsenduserid` bigint(20) DEFAULT NULL COMMENT '发送人 外键关联用户id\r\n(系统消息时值为0)\r\n',
  `title` varchar(32) DEFAULT NULL COMMENT '标题',
  `content` varchar(512) DEFAULT NULL COMMENT '消息内容',
  `createtime` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL COMMENT '0未读  1已读',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息表';

-- ----------------------------
-- Table structure for panicbuying
-- ----------------------------
DROP TABLE IF EXISTS `panicbuying`;
CREATE TABLE `panicbuying` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fexampleplanid` bigint(20) DEFAULT NULL COMMENT '外键关联计划表',
  `fgoldsdetailid` bigint(20) DEFAULT NULL COMMENT '外键关联金币明细表',
  `fuserinfoid` bigint(20) DEFAULT NULL COMMENT '外键关联用户表',
  `createtime` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL COMMENT '0抢购 1 观摩',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='抢购表';

-- ----------------------------
-- Table structure for payinfobywx
-- ----------------------------
DROP TABLE IF EXISTS `payinfobywx`;
CREATE TABLE `payinfobywx` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `issubscribe` varchar(4) DEFAULT NULL,
  `appid` varchar(32) DEFAULT NULL,
  `feetype` varchar(8) DEFAULT NULL,
  `noncestr` varchar(64) DEFAULT NULL,
  `outtradeno` varchar(64) DEFAULT NULL,
  `transactionid` varchar(64) DEFAULT NULL,
  `tradetype` varchar(16) DEFAULT NULL,
  `sign` varchar(64) DEFAULT NULL,
  `resultcode` varchar(16) DEFAULT NULL,
  `mchid` varchar(32) DEFAULT NULL,
  `totalfee` int(11) DEFAULT NULL COMMENT '单位  分',
  `attach` varchar(32) DEFAULT NULL,
  `timeend` varchar(16) DEFAULT NULL,
  `openid` varchar(32) DEFAULT NULL,
  `banktype` varchar(16) DEFAULT NULL,
  `returncode` varchar(16) DEFAULT NULL,
  `cashfee` int(11) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `outtradeno` (`outtradeno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信支付记录表';

-- ----------------------------
-- Table structure for postings
-- ----------------------------
DROP TABLE IF EXISTS `postings`;
CREATE TABLE `postings` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fuserinfoid` bigint(20) DEFAULT NULL COMMENT '发帖人   外键关联用户id',
  `postingstitle` varchar(32) DEFAULT NULL COMMENT '帖子标题',
  `postingscontent` text COMMENT '内容',
  `reward` int(11) DEFAULT NULL COMMENT '打赏数',
  `comments` int(11) DEFAULT NULL COMMENT '评论数',
  `praise` int(11) DEFAULT NULL COMMENT '点赞数',
  `share` int(11) DEFAULT NULL COMMENT '分享数',
  `state` int(11) DEFAULT NULL COMMENT ' 新帖  -1失效',
  `ishot` int(11) DEFAULT NULL COMMENT '是否热门0否1是',
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='帖子';

-- ----------------------------
-- Table structure for postingscomments
-- ----------------------------
DROP TABLE IF EXISTS `postingscomments`;
CREATE TABLE `postingscomments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fpostingsid` bigint(20) DEFAULT NULL COMMENT '帖子id 外键关联帖子表',
  `fuserinfoid` bigint(20) DEFAULT NULL COMMENT '评论人   外键关联用户id',
  `comment` varchar(512) DEFAULT NULL COMMENT '评论内容',
  `state` int(11) DEFAULT NULL COMMENT '0正常 -1失效',
  `createtime` datetime DEFAULT NULL,
  `fcommentid` bigint(20) DEFAULT NULL COMMENT '评论id   外键关联评论id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='帖子评论表';

-- ----------------------------
-- Table structure for postingsphotos
-- ----------------------------
DROP TABLE IF EXISTS `postingsphotos`;
CREATE TABLE `postingsphotos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fpostingsid` bigint(20) DEFAULT NULL COMMENT '帖子id 外键关联帖子表',
  `photourl` varchar(64) DEFAULT NULL COMMENT '图片地址',
  `createtime` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL COMMENT '0正常 -1失效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='帖子图片表';

-- ----------------------------
-- Table structure for postingspraise
-- ----------------------------
DROP TABLE IF EXISTS `postingspraise`;
CREATE TABLE `postingspraise` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fpostingsid` bigint(20) DEFAULT NULL COMMENT '帖子id 外键关联帖子表',
  `fuserinfoid` bigint(20) DEFAULT NULL COMMENT '点赞人   外键关联用户id',
  `state` int(11) DEFAULT NULL COMMENT '0正常 -1取消',
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='帖子点赞表';

-- ----------------------------
-- Table structure for rewardlog
-- ----------------------------
DROP TABLE IF EXISTS `rewardlog`;
CREATE TABLE `rewardlog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fpostingsid` bigint(20) DEFAULT NULL COMMENT '帖子id',
  `fuserinfoid` bigint(20) DEFAULT NULL COMMENT '打赏人id',
  `money` int(11) DEFAULT NULL COMMENT '打赏金额',
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='打赏记录';

-- ----------------------------
-- Table structure for rewardsetting
-- ----------------------------
DROP TABLE IF EXISTS `rewardsetting`;
CREATE TABLE `rewardsetting` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `position` int(11) DEFAULT NULL COMMENT '档位',
  `state` int(11) DEFAULT NULL COMMENT '0正常 -1失效',
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='打赏设置';

-- ----------------------------
-- Table structure for setting
-- ----------------------------
DROP TABLE IF EXISTS `setting`;
CREATE TABLE `setting` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sharesproportion` float(5,2) DEFAULT NULL COMMENT '股票资金配比',
  `foreignexchangeproportion` float(5,2) DEFAULT NULL COMMENT '外汇资金配比',
  `purchasegold` int(11) DEFAULT NULL COMMENT '抢购金币',
  `viewgold` int(11) DEFAULT NULL COMMENT '观摩金币',
  `subscriptiongold` int(11) DEFAULT NULL COMMENT '订阅金币',
  `plansuccess` float(5,2) DEFAULT NULL COMMENT '计划成功牛人分成',
  `planfail` float(5,2) DEFAULT NULL COMMENT '计划失败牛人分成',
  `returnuser` float(5,2) DEFAULT NULL COMMENT '返还用户分成',
  `invitefriends` int(11) DEFAULT NULL COMMENT '邀请好友获得金币数',
  `friends` int(11) DEFAULT NULL COMMENT '好友获得金币数',
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设置表';

-- ----------------------------
-- Table structure for sharesentrust
-- ----------------------------
DROP TABLE IF EXISTS `sharesentrust`;
CREATE TABLE `sharesentrust` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  `fuserinfoid` bigint(20) DEFAULT NULL COMMENT '外键关联用户表',
  `sharesname` varchar(32) DEFAULT NULL COMMENT '股票名称',
  `code` varchar(32) DEFAULT NULL COMMENT '股票代码',
  `presentprice` double(10,2) DEFAULT NULL COMMENT '现价',
  `entrustprice` double(10,2) DEFAULT NULL COMMENT '委托价',
  `entrustnums` int(11) DEFAULT NULL COMMENT '委托数量',
  `frozenamount` double DEFAULT NULL COMMENT '冻结资金',
  `time` datetime DEFAULT NULL COMMENT '时间',
  `state` int(11) DEFAULT NULL COMMENT '状态',
  `isplan` int(11) DEFAULT NULL COMMENT '0非计划 1计划（计划中为纯数据）是否为牛人计划中购买股票标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户股票委托挂单表';

-- ----------------------------
-- Table structure for shareswarehouse
-- ----------------------------
DROP TABLE IF EXISTS `shareswarehouse`;
CREATE TABLE `shareswarehouse` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  `fuserinfoid` bigint(20) DEFAULT NULL COMMENT '外键关联用户表',
  `sharesname` varchar(32) DEFAULT NULL COMMENT '股票名称',
  `code` varchar(32) DEFAULT NULL COMMENT '股票代码',
  `marketvalue` double DEFAULT NULL COMMENT '市值',
  `warehousenums` int(11) DEFAULT NULL COMMENT '持仓手数',
  `couldusenums` int(11) DEFAULT NULL COMMENT '可用手数',
  `cost` double(10,2) DEFAULT NULL COMMENT '成本',
  `presentprice` double(10,2) DEFAULT NULL COMMENT '现价',
  `profitloss` double DEFAULT NULL COMMENT '盈亏',
  `time` datetime DEFAULT NULL COMMENT '时间',
  `state` int(11) DEFAULT NULL COMMENT '状态 0持仓  -1卖出',
  `isplan` int(11) DEFAULT NULL COMMENT ' 0非计划 1计划（计划中为纯数据） 是否为牛人计划中购买股票标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户股票表';

-- ----------------------------
-- Table structure for subscription
-- ----------------------------
DROP TABLE IF EXISTS `subscription`;
CREATE TABLE `subscription` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fsubscribedid` bigint(20) DEFAULT NULL COMMENT '外键关联用户id订阅牛人id',
  `fuserinfoid` bigint(20) DEFAULT NULL COMMENT '订阅人id',
  `state` int(11) DEFAULT NULL COMMENT '0生效 -1取消',
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订阅表';

-- ----------------------------
-- Table structure for useremployeerelation
-- ----------------------------
DROP TABLE IF EXISTS `useremployeerelation`;
CREATE TABLE `useremployeerelation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `faccountid` bigint(20) DEFAULT NULL COMMENT '外键关联推广业务员id',
  `fuserinfoid` bigint(20) DEFAULT NULL COMMENT '外键关联用户id',
  `state` int(11) DEFAULT NULL COMMENT '关系状态 0正常 -1解除',
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COMMENT='用户与业务员关系表 ';

-- ----------------------------
-- Table structure for userinfo
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键，自增，非空',
  `username` varchar(16) DEFAULT NULL COMMENT '账号',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `mobile` varchar(16) DEFAULT NULL COMMENT '手机',
  `nickname` varchar(16) DEFAULT NULL COMMENT '昵称',
  `name` varchar(16) DEFAULT NULL COMMENT '姓名',
  `wxid` varchar(16) DEFAULT NULL COMMENT '微信号',
  `golds` double DEFAULT NULL COMMENT '金币',
  `virtualcapital` double DEFAULT NULL COMMENT '累计充值',
  `email` varchar(32) DEFAULT NULL COMMENT '邮箱',
  `address` varchar(64) DEFAULT NULL COMMENT '地址',
  `bank` varchar(32) DEFAULT NULL COMMENT '开户支行',
  `bankaccount` varchar(8) DEFAULT NULL COMMENT '银行帐户',
  `cardno` varchar(32) DEFAULT NULL COMMENT '卡号',
  `paparno` varchar(32) DEFAULT NULL COMMENT '身份证',
  `createtime` datetime DEFAULT NULL COMMENT '时间',
  `state` int(11) DEFAULT NULL COMMENT '用户状态 0正常  -1禁用',
  `speech` int(11) DEFAULT NULL COMMENT '禁言状态 0正常 -1禁言',
  `faccountid` bigint(20) DEFAULT NULL COMMENT '推广业务员id',
  `finvateid` bigint(20) DEFAULT NULL COMMENT '邀请人id 外键关联用户表',
  `isexample` int(11) DEFAULT NULL COMMENT '是否是牛人 0否1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Table structure for userrecharge
-- ----------------------------
DROP TABLE IF EXISTS `userrecharge`;
CREATE TABLE `userrecharge` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键，自增，非空',
  `fuserinfoid` bigint(20) DEFAULT NULL COMMENT '外键关联用户id',
  `outtradno` varchar(64) DEFAULT NULL COMMENT '充值单号',
  `amount` double DEFAULT NULL COMMENT '充值金额',
  `golds` double DEFAULT NULL COMMENT '对应金币',
  `paystate` int(11) DEFAULT NULL COMMENT '支付状态 0未支付 1已支付',
  `createtime` datetime DEFAULT NULL COMMENT '时间',
  `state` int(255) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户充值表';
