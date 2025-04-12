[![Gihub Actions](https://github.com/flowerfine/carp/actions/workflows/ci.yml/badge.svg?branch=dev)](https://github.com/flowerfine/carp/actions) [![Last commit](https://img.shields.io/github/last-commit/flowerfine/carp.svg)](https://github.com/flowerfine/carp) [![GitHub Tag](https://img.shields.io/github/v/tag/flowerfine/carp)](https://github.com/flowerfine/carp/tags) [![Maven Central](https://img.shields.io/maven-central/v/cn.sliew/carp)](https://maven-badges.herokuapp.com/maven-central/cn.sliew/carp) [![License](https://img.shields.io/github/license/flowerfine/carp.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

# Carp

通用技术&业务模块。通过将技术&业务模块按照模块划分，发布至 maven 仓库，供其他需要类似功能的应用用之即取，简化类似功能在不同应用间重复开发。

* 复用，减少重复。
  * 技术框架选型。不同应用在技术选型上具有通用性，如使用 springboot、mybatis、swagger、slf4j、json、redis 和 mysql。
  * 接口设计。开发者搭建应用基础框时也会有相同的行为：统一的接口响应参数，全局异常处理
  * 通用模块。权限认证，操作日志，
  * 监控。micrometer，数据库连接池、线程池
* 统一升级、迭代
  * 一次迭代，到处升级。应用开发需要持续迭代，功能不断调整以满足业务需求。即使无业务需求也需应对不断增长的数据和请求。

`carp` 提供众多的业务模块，通过精心规划的模块划分，在业务开发时需要类似功能时，可直接引入 maven 依赖，创建数据库表，即可实现业务功能

## Framework

已独立，参考 [carp-parent](https://github.com/flowerfine/carp-parent?tab=readme-ov-file#carp-framework)。

## Module

* 系统管理
  * [carp-module-security](./carp-modules/carp-module-security)。权限管理
  * [carp-module-system](./carp-modules/carp-module-system)。字典管理
* 调度&Workflow
  * [carp-module-scheduler](./carp-modules/carp-module-scheduler)。调度任务管理
  * [carp-module-workflow](./carp-modules/carp-module-workflow)。Workflow 管理
* 大数据
  * [carp-module-datasource](./carp-modules/carp-module-datasource)。数据源管理
  * [carp-module-dataservice](./carp-modules/carp-module-dataservice)。数据服务。基于 mybatis 实现，编写基于 mybatis xml sql，可快速生成一个 http 接口
  * [carp-module-http-sync](./carp-modules/carp-module-http-sync)。通过 http 大规模同步数据。如拥有 1万个快手、淘宝、抖音商家账号，通过快手、淘宝、抖音开放平台接口同步订单、售后单等数据，保证数据的稳定性和及时性
* 其他
  * [carp-module-kubernetes](./carp-modules/carp-module-kubernetes)
  * [carp-module-alert](./carp-modules/carp-module-alert)。集成 prometheus + alertmanager。主要支持 kubernetes 环境下的 prometheus 和 alertmanager，对于单独部署的 prometheus 和 alertmanager，因为监控和告警规则同步问题，仅做本地实例支持。
  * [carp-module-plugin](./carp-modules/carp-module-plugin)。插件模块

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