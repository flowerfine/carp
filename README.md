# Carp

可插拔通用技术&业务模块。通过将技术&业务模块按照模块划分，发布至 maven 仓库，供其他需要类似功能的应用用之即取，简化类似功能在不同应用间重复开发。

* 复用，减少重复。
  * 技术框架选型。不同应用在技术选型上具有通用性，如使用 springboot、mybatis、swagger、slf4j、json、redis 和 mysql。
  * 接口设计。开发者搭建应用基础框时也会有相同的行为：统一的接口响应参数，全局异常处理
  * 通用模块。权限认证，操作日志，
  * 监控。micrometer，数据库连接池、线程池
* 统一升级、迭代
  * 一次迭代，到处升级。应用开发需要持续迭代，功能不断调整以满足业务需求。即使无业务需求也需应对不断增长的数据和请求。

`carp` 提供众多的业务模块，通过精心规划的模块划分，在业务开发时需要类似功能时，可直接引入 maven 依赖，创建数据库表，即可实现业务功能

## 通用模块

### 系统管理

* 权限管理
  - [x] RBAC（用户，角色，权限）
  - [ ] 组织架构。部门、岗位管理
  - [ ] 认证、授权
    - [x] SSO
    - [ ] OAuth2
  - [ ] 多租户
  - [ ] 在线用户
* 系统管理
  - [ ] 系统日志，操作日志
  - [ ] 字典管理
    - [x] 基于枚举
    - [ ] 基于数据库
  - [ ] 监控告警（告警组，联系人，告警记录）
* 消息中心
  - [ ] 站内信管理
  - [ ] 短信管理
  - [ ] 邮件管理
* 文件管理
  - [ ] 存储功能。hdfs、minio、oss
  - [ ] 文件管理。图片、视频、文件、垃圾箱
* 导入导出
  - [ ] 基于 excel、csv 的导入&导出功能
  - [ ] 格式化转化。导入时将是否文件转化为1｜0，导出时将 1|0转化为是否。
    - [ ] 数据格式。

### 大数据模块

* 数据源管理
  - [x] 数据源。数据源分类、数据源管理
  - [ ] 数据元。查看数据库表、字段、索引信息
* 数据同步
  - [ ] 基于 http 接口的数据同步
    - [ ] 授权管理
    - [ ] 同步任务管理
* 数据服务
  * [x] 模板管理
  * [ ] 接口管理
* 调度管理
  * 通用管理。
    * [x] 分组管理
    * [x] 配置管理
    * [x] 实例管理
    * [ ] 日志管理
  * workflow 管理。基于 [orca](https://github.com/spinnaker/orca) 实现，复制重写了一版
    * [ ] 队列管理
      * [ ] 存储。基于内存、Redis 和 SQL 实现
      * [ ] web 页面
    * [ ] 编排管理
      * [ ] 编排存储。基于内存、Redis 和 SQL 实现
      * [ ] web 页面


### 技术模块

* 通用功能
  - [ ] websocket，sse
  - [ ] 队列。延迟队列
  - [x] dag
  - [ ] 线程池增强。线程池任务
* 接口开发框架
  - [ ] 通用 service & controller
  - [ ] 加解密。数据在数据库中加密存储，访问时自动解密
  - [ ] 格式化
* 插件
  - [x] 动态插件
  - [ ] 插件管理
  - [ ] 插件接入 spring
  - [ ] 内部接入
* 监控告警。集成 prometheus 技术栈，集成 alert-manager 和 grafana
  * [ozhera](https://github.com/XiaoMi/ozhera)
  * [应用实时监控服务ARMS](https://help.aliyun.com/zh/arms/)。阿里云产品
* kubernetes 管理
  * [KubePi](https://github.com/1Panel-dev/KubePi)

## Code of Conduct

This project adheres to the Contributor Covenant [code of conduct](https://www.contributor-covenant.org/version/2/1/code_of_conduct/)

## Contributing

For contributions, please refer [CONTRIBUTING](https://github.com/flowerfine/carp)

Thanks for all people who already contributed to Carp!

<a href="https://github.com/flowerfine/carp/graphs/contributors">
    <img src="https://contrib.rocks/image?repo=flowerfine/carp" /></a>

## Contact

* Bugs and Features: [Issues](https://github.com/flowerfine/carp/issues)

## License

Carp is licenced under the Apache License Version 2.0, link is [here](https://www.apache.org/licenses/LICENSE-2.0.txt).