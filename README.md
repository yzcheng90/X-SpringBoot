<h1> X-SpringBoot </h1>

![Image text](https://img.shields.io/badge/x--springboot-v5.0-green.svg)
![Image text](https://img.shields.io/badge/springboot-2.7.7-green.svg)
![Image text](https://img.shields.io/badge/MyBatis%20Plus-3.5.2-green.svg)

[更新日志](https://github.com/yzcheng90/X-SpringBoot/tree/master/doc/updateLog.md) | [项目地址](https://github.com/yzcheng90)  | [SpringCloud版本](https://github.com/yzcheng90/ms) |[前台项目地址](https://github.com/yzcheng90/x-springboot-ui)



**项目说明** 
- X-SpringBoot 是一个轻量级的Java快速开发平台，基于各大开源项目组合而来，用于快速构建中小型API、RESTful API项目，该项目已经有过多个真实项目的实践，稳定、简单、快速，使我们摆脱那些重复劳动。
- 本项目已大量重构,精简了大量代码减少第三方依赖，最干净的脚手架。
- 引入了lombok 大量简化了代码
- 引入了MyBatis Plus 大量简化了SQL
- 引入hutool 工具包 规范工具类
- 引入minio 分布式文件系统
- 引入[autoFull](https://github.com/yzcheng90/zhjg-common-autofull) 自动填充绑定框架,多表关联不用写sql
- 前后端完全脱离，前端代码可单独部署
- 自定义Spring Security 支持获取token
- 账号密码：admin/admin

 
**版本信息** 
- 核心框架：Spring Boot 2.7.7
- 安全框架：Spring Security 5.7.x
- 持久层框架：MyBatis Plus 3.5.2
- 日志管理：SLF4J 1.7、Log4j
- 页面交互：Vue2.x 


**环境** 
- jdk 1.8
- mysql 5.7+
- redis
- nginx


**项目结构** 
```
X-SpringBoot
├─doc  
│  ├─db.sql 项目SQL语句
│  ├─nginx.confi nginx 配置文件
│  ├─updateLog 更新日志
│
├─authentication 权限认证
├─common 公共模块
│  ├─annotation 自定义注解
│  ├─aspect 系统日志
│  ├─base base包
│  ├─exception 异常处理
│  ├─utils 一些工具类
│  └─xss XSS过滤
│ 
├─config 配置信息
├─interceptor token拦截器
│ 
├─modules 功能模块
│  ├─oss 文件服务模块
│  ├─sys 权限模块
│  └─gen 代码生成
│ 
├─Application 项目启动类
│  
├──resources 
│  ├─mapper SQL对应的XML文件


```

**系统截图**
![Image text](https://github.com/yzcheng90/X-SpringBoot/blob/master/pic/20230122174113.png)
![Image text](https://github.com/yzcheng90/X-SpringBoot/blob/master/pic/20230122174148.png)
![Image text](https://github.com/yzcheng90/X-SpringBoot/blob/master/pic/20230122174204.png)

**常见问题**

1、启动报错
```
 是因为依赖没有引入  maven --> 先clear 再reimport 重新引入
```

2、数据库连接不上（mysql 5.7）

```
1) 看看application.yml 配置文件中 spring.profiles.active: dev  
   当前配置的是dev ,就修改application-dev.yml 中的数据库连接IP用户密码
2) 如果改完了还是不行，看看你mysql版本8.0以上 须要修改pom.xml中的 mysql-connector-java 的版本
```

 **最后**

- 交流QQ群：17470566
- 本人QQ：913624256
- 如果喜欢，记得star fork 谢谢您的关注 x-springboot会持续维护


