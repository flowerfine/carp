# Workflow Module

Workflow 是一种处理流程自动化管理工具，平台提供一组可重复的流程和步骤，经过编排建立上下游依赖后，平台会按照依赖串行/并行执行任务。

Workflow 由流程和流程前后顺序组成，流程包含待执行的步骤和状态。Workflow 平台可支持多种使用方式：

* 可视化平台。用户拖拉拽整合多种类型的流程节点，建立流程上下游依赖
* 编辑器。用户通过 yaml 或 json 编辑 Workflow 文件，构建 Workflow

Workflow 一般具有如下特征：

* Step。插件式 Step 实现，易实现
* DAG。支持上下游依赖
* Trigger。支持多种 Trigger，如手动、消息队列、请求或 Cron
* 自动重试、超时时间、并发限制、负载均衡、异常处理
* 上下文参数。
  * 全局变量&参数
  * 步骤运行结果
  * 表达式动态参数

引入 Workflow，可优化业务流程：

* 复用。将业务流程拆分成一个个可复用的 Step
* 扩展。新增新的业务流程只需实现新的步骤，并替换 Workflow 中的步骤
* 易维护。

Workflow 实现一般分为 2 类：依赖库和独立服务

* 依赖库。用户在应用中引入 Workflow 相关依赖，定义 Workflow 和 Step。简单、轻量，即通过 Workflow 优化业务逻辑，又不会因为引入 Workflow 增加请求延迟
* 独立服务。用户部署 Workflow 服务，在应用中引入 Workflow 相关 client，定义 Workflow 和 Step。Workflow 服务负责 Workflow   运行，应用作为 Step 执行者执行 Workflow 中的 Step。Workflow 服务提供相关自动重试、超时控制、并发限制、负载均衡和异常处理。

独立部署的 Workflow 服务，也存在

