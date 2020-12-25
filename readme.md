#### 阿里云
1. 阿里云短信和推送服务
2. 阿里云oss
3. 阿里云的人脸活体检测
4. 阿里云 达摩院的认证识别接口
5. 支付宝支付

#### 微信
1. 微信的授权登录
2. 微信支付


#### redis
1. StringRedisTemplate结合Lettuce 
2. redis提供的限流技术 limiter
3. 缓存公共数据 解决缓存击穿 和 雪崩
4. 每个包的命名规则


#### mysql
1. Hikari连接池的配置解析
2. 表名的设计规范 表的设计 冗余 索引操作
3. B+tree的原理 可以快速搜索百万数据
4. 支持多租户设计业务上
5. 有关第三方 支持多应用

#### http请求
1. restTemplate和okHttp3


#### 自定义注解简化开发


#### 定时任务quartz


#### netty4


#### 接口权限 security
1. RBAC权限校验
2. 如果通过抓包 破解入侵一些web服务器
3. 基本的服务


#### ioc bean的整个生命周期
1. 下载spring framework 源码看

#### 统一处理返回值参数
1. Spring MVC 的执行流程 扩展返回值 统一异常 分页统一参数等
2. Dispatcher 这个类是关键 spring mvc的请求处理流程 越详细越好

3. 2020/12/23 required: debug spring mvc的流程 然后扩展返回值 统一返回一个结构体

#### 自定义分页对象



#### curl
1. -v 详细信息  -X POST 指定请求方法
2. 规范 用于文件的长传下载
3. basic authorization:  
   curl -v -u 'username:password' http://localhost:8080/login
   curl -v http://username:password@localhost:8080/login             [automatically identify]
   都是 header   Authorization basic base64(username:password)
   -d '{"username": "admin"}' -H 'Content-Type: application/json' 
