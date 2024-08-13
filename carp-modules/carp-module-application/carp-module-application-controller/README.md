# 应用控制器

参考链接：
 * [java-operator-sdk](https://github.com/operator-framework/java-operator-sdk)。

实现一个控制器，监听应用状态，并根据状态变更，进行相应处理。在 Kubernetes 中有成熟的实现方式：CR（Custom Resource）和 Operator。
这里实现一个可以脱离 Kubernetes 环境的，可独立运行的。核心以 OAM 为主。
