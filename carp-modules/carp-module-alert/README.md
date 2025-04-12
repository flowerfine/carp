# Alert Module

随着 prometheus 成为监控领域的主流方案，越来越多的系统运维使用 prometheus 进行指标采集，通过 alertmanager 进行告警分发，使用 grafana 进行监控可视化。

应用在考虑监控、告警时也越来越多地开始考虑将监控、告警功能代理给 prometheus 和 alertmanager，甚至开始进行监控、告警参与决策，自动化执行某些业务。

## 集成方案

在使用 prometheus 和 alertmanager 进行监控、告警时，需逐步解决 3 个问题：

* 采集配置。如何接入 metrics 采集。当应用、任务启动时，如何对接 prometheus 进行指标采集
* 告警规则。如何创建告警规则。当应用、任务启动时，如何对接 prometheus 进行监控告警
* 告警分发。如何对告警进行多渠道分发。当收到告警时如何推送至邮箱、钉钉、企微、飞书等工具

### 采集配置：自动发现

通过调研，可以很容易得出怎么快速启动一个 prometheus，并对 springboot 进行监控：

```yaml
global:
  scrape_interval: 30s # 每 30s 采集一次数据
  evaluation_interval: 10s # 每 10s 检测一次告警

scrape_configs:
  - job_name: 'springboot'
    metrics_path: /actuator/prometheus
    scheme: http
    static_configs:
      - targets:
          - 'localhost:8080'
        labels:
          group: 'spring-boot-metrics'
```

prometheus 提供了 service discovery （sd）功能，解决如何自动识别和监控新的目标，无需手动配置每个目标。上述配置中的 `static_configs` 就是其中一种，直接提供应用地址。

自动发现通常用于监控动态变化的环境，如 Kubernetes、云服务（AWS、Azure）以及服务发现系统（Zookeeper、Consul）

prometheus 支持多种 service discoverty（sd）：

* [static_config](https://prometheus.io/docs/prometheus/latest/configuration/configuration/#static_config)
* [file_sd_config](https://prometheus.io/docs/prometheus/latest/configuration/configuration/#file_sd_config)
* [http_sd_config](https://prometheus.io/docs/prometheus/latest/configuration/configuration/#http_sd_config)
* [docker_sd_config](https://prometheus.io/docs/prometheus/latest/configuration/configuration/#docker_sd_config)
* [kubernetes_sd_config](https://prometheus.io/docs/prometheus/latest/configuration/configuration/#kubernetes_sd_config)
* [dns_sd_config](https://prometheus.io/docs/prometheus/latest/configuration/configuration/#dns_sd_config)
* [consul_sd_config](https://prometheus.io/docs/prometheus/latest/configuration/configuration/#consul_sd_config)
* [eureka_sd_config](https://prometheus.io/docs/prometheus/latest/configuration/configuration/#eureka_sd_config)
* [azure_sd_config](https://prometheus.io/docs/prometheus/latest/configuration/configuration/#azure_sd_config)
* [gce_sd_config](https://prometheus.io/docs/prometheus/latest/configuration/configuration/#gce_sd_config)
* [ec2_sd_config](https://prometheus.io/docs/prometheus/latest/configuration/configuration/#ec2_sd_config)
* [digitalocean_sd_config](https://prometheus.io/docs/prometheus/latest/configuration/configuration/#digitalocean_sd_config)
* [dockerswarm_sd_config](https://prometheus.io/docs/prometheus/latest/configuration/configuration/#dockerswarm_sd_config)
* [openstack_sd_config](https://prometheus.io/docs/prometheus/latest/configuration/configuration/#openstack_sd_config)
* [ovhcloud_sd_config](https://prometheus.io/docs/prometheus/latest/configuration/configuration/#ovhcloud_sd_config)
* [puppetdb_sd_config](https://prometheus.io/docs/prometheus/latest/configuration/configuration/#puppetdb_sd_config)
* [hetzner_sd_config](https://prometheus.io/docs/prometheus/latest/configuration/configuration/#hetzner_sd_config)
* [ionos_sd_config](https://prometheus.io/docs/prometheus/latest/configuration/configuration/#ionos_sd_config)
* [kuma_sd_config](https://prometheus.io/docs/prometheus/latest/configuration/configuration/#kuma_sd_config)
* [lightsail_sd_config](https://prometheus.io/docs/prometheus/latest/configuration/configuration/#lightsail_sd_config)
* [linode_sd_config](https://prometheus.io/docs/prometheus/latest/configuration/configuration/#linode_sd_config)
* [marathon_sd_config](https://prometheus.io/docs/prometheus/latest/configuration/configuration/#marathon_sd_config)
* [nerve_sd_config](https://prometheus.io/docs/prometheus/latest/configuration/configuration/#nerve_sd_config)
* [nomad_sd_config](https://prometheus.io/docs/prometheus/latest/configuration/configuration/#nomad_sd_config)
* [serverset_sd_config](https://prometheus.io/docs/prometheus/latest/configuration/configuration/#serverset_sd_config)
* [triton_sd_config](https://prometheus.io/docs/prometheus/latest/configuration/configuration/#triton_sd_config)
* [scaleway_sd_config](https://prometheus.io/docs/prometheus/latest/configuration/configuration/#scaleway_sd_config)
* [uyuni_sd_config](https://prometheus.io/docs/prometheus/latest/configuration/configuration/#uyuni_sd_config)
* [vultr_sd_config](https://prometheus.io/docs/prometheus/latest/configuration/configuration/#vultr_sd_config)

#### kubernetes_sd_config

在 `kubernetes_sd_config` 实现中，可以按照 `node`、`service`、`pod`、`endpoints`、`endpointslice`、`ingress` 等方式动态识别新的目标。在 kube-prometheus-stack 中提供了 `ServiceMonitor` 和 `PodMonitor` 可以根据 `service` 和 `pod` 进行自动发现。

##### ServiceMonitor

参考链接：

* [ServiceMonitor](https://github.com/prometheus-operator/prometheus-operator/blob/main/Documentation/api-reference/api.md#servicemonitor)
* [通过ServiceMonitor创建服务发现](https://help.aliyun.com/zh/prometheus/use-cases/use-servicemonitors-to-discover-and-monitor-services?spm=a2c4g.11186623.help-menu-122122.d_3_2.744920adIEPMGp&scm=20140722.H_260895._.OR_help-T_cn~zh-V_1)

##### PodMonitor

参考链接：[PodMonitor](https://github.com/prometheus-operator/prometheus-operator/blob/main/Documentation/api-reference/api.md#podmonitor)

### 告警规则

参考链接：[Alerting rules](https://prometheus.io/docs/prometheus/latest/configuration/alerting_rules/)

prometheus 根据配置的 promQL 进行监控，当命中 promQL 后发送告警事件。因此告警规则和告警是 prometheus 是

### 告警分发



## 开源参考

* [prometheus](https://github.com/prometheus/prometheus)
* [alertmanager](https://github.com/prometheus/alertmanager)
* [grafana](https://github.com/grafana/grafana)
* [kube-prometheus](https://github.com/prometheus-operator/kube-prometheus)
* [VictoriaMetrics](https://github.com/VictoriaMetrics/VictoriaMetrics)
* [OzHera](https://ozhera.apache.org/)。小米开源
* [HertzBeat](https://hertzbeat.apache.org/zh-cn/)
* [nightingale](https://github.com/ccfos/nightingale)。
  * 文档：[夜莺](https://flashcat.cloud/docs/content/flashcat-monitor/nightingale-v7/introduction/)
  * [夜莺监控手把手配置 Prometheus 告警](https://mp.weixin.qq.com/s?__biz=MzU3ODAxNTIzMQ==&mid=2247488155&idx=1&sn=8f364dc3e80899d87f143b3864ff91e5&chksm=fc7958c8b7d050e711f8f32f863bde3c1380438775f6fb8ca602d35e3cfc83d4047de180bfa2&mpshare=1&scene=1&srcid=0412H1MEa39VsdcWteToU0br&sharer_shareinfo=832da5f6d7fd6ad90ce8ae07675b08fe&sharer_shareinfo_first=832da5f6d7fd6ad90ce8ae07675b08fe&version=4.1.10.99312&platform=mac#rd)
  * [今日话题：Grafana告警如何接管Prometheus体系告警](https://mp.weixin.qq.com/s?__biz=MzIzNjU5NDE2MA==&mid=2247488779&idx=1&sn=290b23e82f8c0754ecd866bd9405d7ca&chksm=e934fda9738011e04ac46411b2bbdf646c57ed69dfe3bf6c6562376ad7826dd705ab7ec36983&mpshare=1&scene=1&srcid=0412PKXhzjdlHZzLadku3ZWz&sharer_shareinfo=213a0c9e22707f0f465dd0047b6f8b62&sharer_shareinfo_first=213a0c9e22707f0f465dd0047b6f8b62&version=4.1.10.99312&platform=mac#rd)
* [PrometheusAlert](https://github.com/feiyu563/PrometheusAlert)
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