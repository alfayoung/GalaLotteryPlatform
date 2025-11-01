DROP DATABASE IF EXISTS Lottery;

CREATE DATABASE Lottery;

USE Lottery;

DROP TABLE IF EXISTS `awards`;

CREATE TABLE `awards`
(
    `id`         int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `jAccount`   varchar(64)  UNIQUE NOT NULL DEFAULT '' COMMENT 'jAccount标识符',
    `name`       varchar(64)  NOT NULL DEFAULT '' COMMENT '用户名',
    `prize`      varchar(128) DEFAULT NULL COMMENT '获得的奖品',
    `enrolled`   bool         NOT NULL DEFAULT FALSE COMMENT '是否参与抽奖',
    `profile`    longblob     COMMENT '用户头像',
    `create_time`      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='中奖结果表';

INSERT INTO `awards` (`jAccount`, `name`, `prize`, `enrolled`, `profile`)
VALUES
    ('jAccount1', 'User1', 'Prize1', TRUE, NULL),
    ('jAccount2', 'User2', NULL, TRUE, NULL),
    ('jAccount3', 'User3', NULL, FALSE, NULL),
    ('jAccount4', 'User4', NULL, TRUE, NULL),
    ('jAccount5', 'User5', NULL, TRUE, NULL),
    ('jAccount6', 'User6', NULL, TRUE, NULL),
    ('jAccount7', 'User7', NULL, TRUE, NULL),
    ('jAccount8', 'User8', NULL, TRUE, NULL),
    ('jAccount9', 'User9', NULL, TRUE, NULL);