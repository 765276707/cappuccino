# straws <a href='https://gitee.com/xu_zhibin/cappuccino/stargazers'><img src='https://gitee.com/xu_zhibin/cappuccino/badge/star.svg?theme=white' alt='star'></img></a> <a href='https://gitee.com/xu_zhibin/shield/members'><img src='https://gitee.com/xu_zhibin/shield/badge/fork.svg?theme=dark' alt='fork'></img></a>

#### 介绍
Cappuccino是一款开源的分布式配置中心，其具有部署简单、接入方便、支持灰度、性能不俗的特性，即可一个Jar包直接单机部署，也支持nignx+server+mysql集群化部署（仅在小规模集群中测试过），也方便开发人员对其进行深度定制化。

#### 环境要求
- JDK8
- Maven 3.2以上版本
- MySQL 8以上版本
- Windows/MacOS/Linux系统

#### 应用场景
- 对需要进行远程配置的项目
- 对需要经常性更改某些配置的项目
- 对需要对配置进行集中管理的项目
- 对需要灰度发布场景下的项目集群，等等


#### 服务端部署
##### 单机部署
1.  安装JDK8、安装Maven3.2以上版本
2.  安装Mysql8，创建名为cappuccinov3的数据库
3.  克隆项目到本地
4.  将server模块内的schema文件夹内的sql文件导入到cappuccinov3数据库内
5.  需要将配置文件修改以下配置, 单机模式 cluster-enabled 必须设为false
~~~yaml
# 应用配置
spring:
  cloud:
    cappuccino:
      server:
        # 主机名
        hostname: http://127.0.0.1
        # 开启客户端认证
        client-auth-enabled: true
        client-access-key: 'client_user_key'
        client-access-secret: 'client_user_secret'
        # 前端Token认证的秘钥
        token-header: 'Authorization'
        token-secret: '12345678ABCDEFGH'
        # 单机部署
        cluster-enabled: false
~~~
6.  编译、打包、部署server模块
7.  需要拓展的tx可以自行拓展，但请遵守项目指定的开源协议


##### 集群部署
集群部署与单机部署有以下几点不同
1.  需要增加nginx作为负载均衡器
2.  配置中必须配置集群中的其他节点地址
~~~yaml
# 应用配置
spring:
  cloud:
    cappuccino:
      server:
        # 主机名
        hostname: http://127.0.0.1
        # 开启客户端认证
        client-auth-enabled: true
        client-access-key: 'client_user_key'
        client-access-secret: 'client_user_secret'
        # 前端Token认证的秘钥
        token-header: 'Authorization'
        token-secret: '12345678ABCDEFGH'
        # 集群部署
        cluster-enabled: true
        cluster-heartbeat-interval: 30000
        cluster-addr-list:
          - http://127.0.0.1:8617
          - http://127.0.0.1:8618
          - http://127.0.0.1:8619
~~~
3.建议对mysql数据库也进行集群化部署

### 灰度规则
ip:port
示例： 127.0.0.1:8080 为一个灰度规则，其代表一个某个客户端的一个实例

#### 客户端使用
客户端必须导入客户端的依赖
~~~xml
<dependency>
    <groupId>com.github.xzb617</groupId>
    <artifactId>cappuccino-client</artifactId>
    <version>0.3.1.beta</version>
</dependency>
~~~
在bootstrap.yml配置相关参数
~~~yaml
server:
  port: 8081
spring:
  application:
    name: cappuccino-client-demo

  # 配置中心客户端的配置
  cloud:
    cappuccino:
      client:
        # 集群示例： http://127.0.0.1:8617&wgt=8,http://127.0.0.1:8618&wgt=1,http://127.0.0.1:8619&wgt=1
        server-addr: http://127.0.0.1:8617
        load-balance-strategy: round_robin
        # 是否开启自动刷新配置
        refresh-enabled: true
        # 配置中心开启了对客户端的认证时，所需的配置
        access-key: 'client_user_key'
        access-secret: 'client_user_secret'
~~~

#### 界面展示
![Cappuccino](https://gitee.com/xu_zhibin/cappuccino/raw/master/docs/1.png)
![Cappuccino](https://gitee.com/xu_zhibin/cappuccino/raw/master/docs/2.png)
![Cappuccino](https://gitee.com/xu_zhibin/cappuccino/raw/master/docs/3.png)
![Cappuccino](https://gitee.com/xu_zhibin/cappuccino/raw/master/docs/4.png)


#### 参与贡献
1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request

