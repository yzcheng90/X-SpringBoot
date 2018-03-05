

**项目说明** 
- X-SpringBoot 是一个轻量级的Java快速开发平台，基于各大开源项目组合而来，用于快速构建中小型API、RESTful API项目，该项目已经有过多个真实项目的实践，稳定、简单、快速，使我们摆脱那些重复劳动。

- 专为X-SpringBoot 量身定制《接口》代码生成工具https://github.com/yzcheng90/x_springboot-inteface-generatorper

- 专为X-SpringBoot 量身定制《后台管理》代码生成工具https://github.com/yzcheng90/x_springboot-generator

- 专为X-SpringBoot 量身定制《安卓快速开发框架》https://github.com/yzcheng90/x-android

<br> 
 


**具有如下特点** 
- 友好的代码结构及注释，便于阅读及二次开发
- 实现前后端分离，通过token进行数据交互，前端再也不用关注后端技术
- 灵活的权限控制，可控制到页面或按钮，满足绝大部分的权限需求
- 页面交互使用Vue2.x，极大的提高了开发效率
- 统一异常处理
- 使用Druid Spring Boot Starter 集成Druid数据库连接池与监控
- 完善的代码生成机制，可在线生成entity、xml、dao、service、html、js、sql代码，减少70%以上的开发任务
- 引入quartz定时任务，可动态完成任务的添加、修改、删除、暂停、恢复及日志查看等功能
- 引入API模板，根据token作为登录令牌，极大的方便了APP接口开发
- 引入快速API代码生成,减少90%以上的开发任务，复杂逻辑只须要修改mapper.xml，一个模块只须几分钟
- 引入接口数据加密传输
- 引入Hibernate Validator校验框架，轻松实现后端校验
- 引入云存储服务，已支持：七牛云、阿里云、腾讯云等
- 引入swagger文档支持，方便编写API接口文档
- 引入路由机制，刷新页面会停留在当前页
<br> 

**项目结构** 
```
X-SpringBoot
├─doc  项目SQL语句
│
├─common 公共模块
│  ├─aspect 系统日志
│  ├─exception 异常处理
│  ├─validator 后台校验
│  └─xss XSS过滤
│ 
├─config 配置信息
│ 
├─modules 功能模块
│  ├─app API接口模块(APP调用)
│  ├─job 定时任务模块
│  ├─oss 文件服务模块
│  └─sys 权限模块
│ 
├─Application 项目启动类
│  
├──resources 
│  ├─mapper SQL对应的XML文件
│  ├─static 第三方库、插件等静态资源
│  └─views  项目静态页面

```
<br> 

**技术选型：** 
- 核心框架：Spring Boot 1.5
- 安全框架：Apache Shiro 1.3
- 视图框架：Spring MVC 4.3
- 持久层框架：MyBatis 3.3
- 定时器：Quartz 2.3
- 数据库连接池：Druid 1.0
- 日志管理：SLF4J 1.7、Log4j
- 页面交互：Vue2.x 
<br> 

![image](https://github.com/yzcheng90/X-SpringBoot/blob/master/pic/AppBaseResult_img.png)
<br>
<h3>统一接口请求和返回工具类 <a href="https://github.com/yzcheng90/X-SpringBoot/blob/master/src/main/java/com/suke/czx/common/utils/AppBaseResult.java">AppBaseResult.java</a></h3>
<br>
<h3>数据返回加解密工具类 <a href="https://github.com/yzcheng90/X-SpringBoot/blob/master/src/main/java/com/suke/czx/common/utils/CDESCrypt.java">CDESCrypt.java</a></h3>

---
> 接口controller类示例 
>
```javascript

@ApiOperation(value="列表", notes="列表")  //swagger2的接口显示
@ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true,dataType = "string", paramType = "query", defaultValue = "")})
@PostMapping("/appUpdate/list")
public AppBaseResult list(@RequestBody AppBaseResult appBaseResult)throws Exception{ //使用AppBaseResult类接口转过来的封装参数
    logger.info("AppUpdateController 列表",appBaseResult.decryptData());
    HashMap<String,Object> params = new Gson().fromJson(appBaseResult.decryptData(),HashMap.class); //解密后反序列化为MAP
	//查询列表数据
    Query query = new Query(params);
	query.isPaging(true);　//开启拦截器分页，不需要多写一条SQL去查询条数
	List<HashMap<String,Object>> appUpdateList = appUpdateService.queryList(query);
	PageUtils pageUtil = new PageUtils(appUpdateList, query.getTotle(), query.getLimit(), query.getPage());
    return AppBaseResult.success().setEncryptData(pageUtil); //数据加密返回
}


```
---
>接口数据『未加密』返回
>
```javascript

{
	"code": 200,
	"message": "请求成功",
	"data": "[{\"id\":\"20171130104836867615\",\"name\":\"吕经理\",\"phone\":\"13888888888\",\"car\":\"湘A12345\",\"address\":\"A栋\",\"tplanarrivaltime\":\"2017-11-30 10:45:46\",\"sintervieweename\":\"张小曼\",\"iintervieweephone\":\"15625448521\",\"tcreatetime\":\"2017-11-30 10:48:36\",\"suserid\":\"test01\",\"susername\":\"章涛宁\",\"svisitorintend\":\"\",\"tplanleavetime\":\"2017-12-21 09:49:09\",\"saccessibleid\":\"20171221949935675\",\"tentertime\":\"2017-12-21 09:49:09\"}]",
	"version": "1.0",
	"mobile": ""
}

```
---
>接口数据『加密』返回
>
```javascript

{
    "code": 200,
    "message": "请求成功",
    "data": "BJ5EH8YbByGQ8ZyxAB8WEWUlj1rpZbvOSVzScAPyKk5B8WMw8NhjfrZeLROzgsM/ag2RJr33B9ZfS5xEJd5ORHig/NLmIAt2LcElMuUA1FwIPn3BOJu4CAohwtZEX4rSYRGSVv5LEpyXJNTqlZ/R5OeDTyTYdE9aAD1D3++LmspFq7Us8r33+6rc6PkTqnHGBI6rSCL1yugP0mdMIRPgrguMknbE9TloDPP1AuncomTYiRs4jfwv6kRVk9uWbSgFjjGFChGwtyHnVrSttxRNi0o9YS51NwoL2ZK8/0kNlnHxHImwWLq2394sOf9Jy4X0XcUdjuJalSPyRMxyHhglWmFwMYnIEc3dOYdB/snMc0ESXdXvzyxlpT/IwB5T4QdcmBmQIsMyEIr2ZKDR6fO/KUH+QvQJT2oSYNz+0gr4IqgBYzhIOPmE/f0tzhI/fHyMNOoH+2WRxL4gYgi3nzYZBudK8Hb0U/ddGc8gasreTWnW2Z3BrO2T9uK+0LWdR9Uq/6QccqlPmUJlMdRuaVyKcPz65LQn/f9qlASnKNbkVK+LmJi/JeP5lkBdK089l9vRX3y5YRWwuGrfJcc9VjvSp89vNhTAFtnEN9ChqG2iuiJgUhsEZtFluDoeycC8eBoncFpWXK4cnD2BZZIJPowEOs37u8HvHPOjaeqkctU1OVA=",
    "version": "1.0",
    "mobile": ""
}

```

---
>系统示例图

![image](https://github.com/yzcheng90/X-SpringBoot/blob/master/pic/20180108172123_1.png)
![image](https://github.com/yzcheng90/X-SpringBoot/blob/master/pic/20180108172123_2.png)
![image](https://github.com/yzcheng90/X-SpringBoot/blob/master/pic/20180108172123_3.png)

 **本地部署**
- 下载源码
- 创建数据库x_springboot，数据库编码为UTF-8
- 执行doc/db.sql文件，初始化数据
- 修改application-dev.yml，更新MySQL账号和密码
- Eclipse、IDEA运行Application.java，则可启动项目
- 项目访问路径：http://localhost:8080/x_springboot
- 账号密码：admin/admin
- Swagger路径：http://localhost:8080/x_springboot/swagger/index.html
- 交流QQ群：17470566
- 本人QQ：913624256
- 如果喜欢，记得star fork 谢谢您的关注 x_springboot会持续维护


