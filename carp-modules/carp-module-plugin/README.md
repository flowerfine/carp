# Plugin Module

plugin 模块，参考 [kork](https://github.com/spinnaker/kork) 项目。基于 [pf4j](https://github.com/pf4j/pf4j) 开发，依赖项目列表：

* [pf4j](https://github.com/pf4j/pf4j)
* [pf4j-spring](https://github.com/pf4j/pf4j-spring)
* [pf4j-update](https://github.com/pf4j/pf4j-update)

类似项目：[devops-framework/devops-plugin](https://github.com/bkdevops-projects/devops-framework/tree/master/devops-boot-project/devops-boot-core/devops-plugin)、[devops-boot-starter-plugin](https://bkdevops-projects.github.io/devops-framework/#/starter/devops-boot-starter-plugin)。

支持功能如下：

* 插件管理。
  * 插件查看。查看已加载插件、卸载插件、重新加载插件
  * 动态管理。上传、下载、启用、禁用插件
* 插件注册为 spring bean
* 插件自动更新

## pf4j 介绍

### `PluginManager`

