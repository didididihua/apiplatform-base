# 简单的API开放平台项目

> by didididi(didididihua@gmail.com)

## 项目结构：
**技术栈： Spring Boot + Mybatis Plus + MySQL + gateway + Dubbo**
* apiplatform-base:基础的用户登录、注册和管理，以及用于给在线调用的免费接口的controller, 
* apiplatform-common: 基础通用模块
* apiplatform-gateway: api网关模块，有请求转发，请求鉴权，统一签名校验，流量染色，接口调用次数增加，日志记录等功能 
* apiplatform-mook: 免费接口的后端，现在里面有根据搜索词返回bing的图片与其标题、随机数，Hello Wold等一些接口，以待之后不断的更新
* apiplatform-SDK: 提供了给用户调用免费接口的方法，封装了加签的步骤，方便用户的使用，也被apiplatform的在线接口使用
* 在线演示：http://www.didididih.club/

## 启动：
* 先将项目的mysql,nacos,redis(可用可不用，应为本项目可用不用分布式session)配置完成
* 再启动apiplatform-base,apiplatform-gateway,apiplatform-mook


### 此项目使用了使用鱼皮（by https://yupi.icu/）的通用模板，模板功能:

- Spring Boot 2.7.0（贼新）
- Spring MVC
- MySQL 驱动
- MyBatis
- MyBatis Plus
- Spring Session Redis 分布式登录
- Spring AOP
- Apache Commons Lang3 工具类
- Lombok 注解
- Swagger + Knife4j 接口文档
- Spring Boot 调试工具和项目处理器
- 全局请求响应拦截器（记录日志）
- 全局异常处理器
- 自定义错误码
- 封装通用响应类
- 示例用户注册、登录、搜索功能
- 示例单元测试类
- 示例 SQL（用户表）

访问 localhost:7529/api/doc.html 就能在线调试接口了，不需要前端配合啦~