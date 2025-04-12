# Alert Module

随着 prometheus 成为监控领域的主流方案，越来越多的系统运维使用 prometheus 进行指标采集，通过 alertmanager 进行告警分发，使用 grafana 进行监控可视化。

应用在考虑监控、告警时也越来越多地开始考虑将监控、告警功能代理给 prometheus 和 alertmanager，甚至开始进行监控、告警参与决策，自动化执行某些业务。

## 集成方案

在使用 prometheus 和 alertmanager 进行监控、告警时，需逐步解决 3 个问题：

* 采集配置。如何接入 metrics 采集。当应用、任务启动时，如何对接 prometheus 进行指标采集
* 告警规则。如何创建告警规则。当应用、任务启动时，如何对接 prometheus 进行监控告警
* 告警分发。如何对告警进行多渠道分发。当收到告警时如何推送至邮箱、钉钉、企微、飞书等工具

### 采集配置



## 开源参考

* [prometheus](https://github.com/prometheus/prometheus)
* [alertmanager](https://github.com/prometheus/alertmanager)
* [grafana](https://github.com/grafana/grafana)
* [kube-prometheus](https://github.com/prometheus-operator/kube-prometheus)
* [VictoriaMetrics](https://github.com/VictoriaMetrics/VictoriaMetrics)
* [OzHera](https://ozhera.apache.org/)。小米开源
* [HertzBeat](https://hertzbeat.apache.org/zh-cn/)
* [nightingale](https://github.com/ccfos/nightingale)。文档：[夜莺](https://flashcat.cloud/docs/content/flashcat-monitor/nightingale-v7/introduction/)
* [keep](https://github.com/keephq/keep)
* [rundeck](https://github.com/rundeck/rundeck)

metrics 采集

* [metricshub-community](https://github.com/MetricsHub/metricshub-community)
* [telegraf](https://github.com/influxdata/telegraf)
* [node_exporter](https://github.com/prometheus/node_exporter)
* [categraf](https://github.com/flashcatcloud/categraf)
* [opentelemetry-collector](https://github.com/open-telemetry/opentelemetry-collector)
* [beats](https://github.com/elastic/beats)
* [logstash](https://github.com/elastic/logstash)
* [loongcollector](https://github.com/alibaba/loongcollector)