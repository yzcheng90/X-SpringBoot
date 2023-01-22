### v5.0
- springboot 版本升级到 2.7.7
- mybatis-plus 版本升级到 3.5.2
- 项目重构，精简代码
- 前台UI换成 vue-cli 

### v3.2
- 修复上传文件时报匿名无权限
- 修复一些删除报错问题
- 修复  [issues #15](https://github.com/yzcheng90/X-SpringBoot/issues/15)

### v3.1
- 修复代码生成一些错误依赖 [issues #10](https://github.com/yzcheng90/X-SpringBoot/issues/10)
- 修改 security token 保存方式 

### v3.0
- 项目部分重构，精简部分代码
- springboot 版本升级到 2.1.8
- 移除shiro 安全框架
- 新增spring security 安全框架

### v2.1.2
- 修复配置sso信息的时候更新不成功

### v2.1.1
- 群里及issues中提到的一些bug修复

### v2.1 
- 加入代码生成模块
- 加入APP管理模块

### v2.0 
- 大量重构代码,精简了大量代码减少第三方依赖。
- 引入lombok 大量简化了代码
- 引入MyBatis Plus 大量简化了SQL
- 引入hutool 工具包 规范工具类
- 引入minio 分布式文件系统
- 前后端完全脱离，前端代码可单独部署
- 支持密码和手机号 获取token
- 核心框架升级到Spring Boot 2.1.3
