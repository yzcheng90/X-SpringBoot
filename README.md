**目前版本更新到V2.0** 

**项目说明** 
- X-SpringBoot 是一个轻量级的Java快速开发平台，基于各大开源项目组合而来，用于快速构建中小型API、RESTful API项目，该项目已经有过多个真实项目的实践，稳定、简单、快速，使我们摆脱那些重复劳动。
- 本项目已大量重构,精简了大量代码减少第三方依赖，最干净的脚手架。
- 引入了lombok 大量简化了代码
- 引入了MyBatis Plus 大量简化了SQL
- 引入hutool 工具包 规范工具类
- 引入minio 分布式文件系统
- 前后端完全脱离，前端代码可单独部署
- 支持密码和手机号 获取token
- 账号密码：admin/admin

 
**版本信息** 
- 核心框架：Spring Boot 2.1.3
- 安全框架：Apache Shiro 1.4
- 视图框架：Spring MVC 5.1
- 持久层框架：MyBatis Plus 3.1.0
- 日志管理：SLF4J 1.7、Log4j
- 页面交互：Vue2.x 


**环境** 
- jdk 1.8
- mysql 5.7
- redis
- nginx


**项目结构** 
```
X-SpringBoot
├─doc  项目SQL语句
│
├─authentication 权限认证
├─common 公共模块
│  ├─annotation 自定义注解
│  ├─aspect 系统日志
│  ├─base base包
│  ├─exception 异常处理
│  ├─utils 一些工具类
│  ├─validator 后台校验
│  └─xss XSS过滤
│ 
├─config 配置信息
├─interceptor token拦截器
│ 
├─modules 功能模块
│  ├─app API接口模块(APP调用)
│  ├─oss 文件服务模块
│  └─sys 权限模块
│ 
├─Application 项目启动类
├─Swagger2 swagger2类
│  
├──resources 
│  ├─mapper SQL对应的XML文件


```
**部署** 
- 后台部署
 ```
   1、 $git clong https://github.com/yzcheng90/X-SpringBoot.git
   
   2 、IDEA 打开项目引入依赖
   
   3、 创建数据库x_springboot，数据库编码为UTF-8，执行doc/db.sql文件，初始化数据
   
   4、 IDEA运行Application.java，则可启动项目 http://localhost:8080
 ```
- 前台部署
 ```
    1、 打开nginx 目录 /conf/nginx.conf 
    
    2、 在server中修改 root 和 index  
        
        ...
        server {
            ....
            #静态页面目录
            root  E:\github\X-SpringBoot\x-springboot-ui;
            #默认首页
            index  login.html;
            ....
            
            location ^~// {
                 proxy_pass   http://127.0.0.1:8080; #这里为后台服务地址
            }
        }
        ...
        
     3、启动nginx 访问 localhost
 ```

**系统截图**
![Image text](https://github.com/yzcheng90/X-SpringBoot/blob/master/pic/20190419153826.jpg)
![Image text](https://github.com/yzcheng90/X-SpringBoot/blob/master/pic/20190422163826.jpg)
![Image text](https://github.com/yzcheng90/X-SpringBoot/blob/master/pic/20190422163331.jpg)

**常见问题**

1、启动报错
```
 是因为依赖没有引入  maven --> reimport 重新引入
```
2、验证码失败
```
 是因为redis 没有启动
```
3、数据库连接不上（mysql 5.7）

```
1) 看看application.yml 配置文件中 spring.profiles.active: dev  
   当前配置的是dev ,就修改application-dev.yml 中的数据库连接IP用户密码
2) 如果改完了还是不行，看看你mysql版本8.0以上 须要修改pom.xml中的 mysql-connector-java 的版本

```


 **最后**

- 交流QQ群：17470566
- 本人QQ：913624256
- 如果喜欢，记得star fork 谢谢您的关注 x_springboot会持续维护


