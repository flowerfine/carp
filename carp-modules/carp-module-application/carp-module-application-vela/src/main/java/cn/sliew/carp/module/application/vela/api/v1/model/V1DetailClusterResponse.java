package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;

import cn.sliew.carp.module.application.vela.api.v1.model.common.ModelProviderInfo;
import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1ClusterResourceInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.Map;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1DetailClusterResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1DetailClusterResponse   {
  @JsonProperty("alias")
  private String alias = null;

  @JsonProperty("apiServerURL")
  private String apiServerURL = null;

  @JsonProperty("createTime")
  private OffsetDateTime createTime = null;

  @JsonProperty("dashboardURL")
  private String dashboardURL = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("icon")
  private String icon = null;

  @JsonProperty("kubeConfig")
  private String kubeConfig = null;

  @JsonProperty("kubeConfigSecret")
  private String kubeConfigSecret = null;

  @JsonProperty("labels")
  @Valid
  private Map<String, String> labels = new HashMap<String, String>();

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("provider")
  private ModelProviderInfo provider = null;

  @JsonProperty("reason")
  private String reason = null;

  @JsonProperty("resourceInfo")
  private V1ClusterResourceInfo resourceInfo = null;

  @JsonProperty("status")
  private String status = null;

  @JsonProperty("updateTime")
  private OffsetDateTime updateTime = null;

  public V1DetailClusterResponse alias(String alias) {
    this.alias = alias;
    return this;
  }

  /**
   * Get alias
   * @return alias
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public V1DetailClusterResponse apiServerURL(String apiServerURL) {
    this.apiServerURL = apiServerURL;
    return this;
  }

  /**
   * Get apiServerURL
   * @return apiServerURL
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getApiServerURL() {
    return apiServerURL;
  }

  public void setApiServerURL(String apiServerURL) {
    this.apiServerURL = apiServerURL;
  }

  public V1DetailClusterResponse createTime(OffsetDateTime createTime) {
    this.createTime = createTime;
    return this;
  }

  /**
   * Get createTime
   * @return createTime
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public OffsetDateTime getCreateTime() {
    return createTime;
  }

  public void setCreateTime(OffsetDateTime createTime) {
    this.createTime = createTime;
  }

  public V1DetailClusterResponse dashboardURL(String dashboardURL) {
    this.dashboardURL = dashboardURL;
    return this;
  }

  /**
   * Get dashboardURL
   * @return dashboardURL
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getDashboardURL() {
    return dashboardURL;
  }

  public void setDashboardURL(String dashboardURL) {
    this.dashboardURL = dashboardURL;
  }

  public V1DetailClusterResponse description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public V1DetailClusterResponse icon(String icon) {
    this.icon = icon;
    return this;
  }

  /**
   * Get icon
   * @return icon
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public V1DetailClusterResponse kubeConfig(String kubeConfig) {
    this.kubeConfig = kubeConfig;
    return this;
  }

  /**
   * Get kubeConfig
   * @return kubeConfig
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getKubeConfig() {
    return kubeConfig;
  }

  public void setKubeConfig(String kubeConfig) {
    this.kubeConfig = kubeConfig;
  }

  public V1DetailClusterResponse kubeConfigSecret(String kubeConfigSecret) {
    this.kubeConfigSecret = kubeConfigSecret;
    return this;
  }

  /**
   * Get kubeConfigSecret
   * @return kubeConfigSecret
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getKubeConfigSecret() {
    return kubeConfigSecret;
  }

  public void setKubeConfigSecret(String kubeConfigSecret) {
    this.kubeConfigSecret = kubeConfigSecret;
  }

  public V1DetailClusterResponse labels(Map<String, String> labels) {
    this.labels = labels;
    return this;
  }

  public V1DetailClusterResponse putLabelsItem(String key, String labelsItem) {
    this.labels.put(key, labelsItem);
    return this;
  }

  /**
   * Get labels
   * @return labels
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Map<String, String> getLabels() {
    return labels;
  }

  public void setLabels(Map<String, String> labels) {
    this.labels = labels;
  }

  public V1DetailClusterResponse name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public V1DetailClusterResponse provider(ModelProviderInfo provider) {
    this.provider = provider;
    return this;
  }

  /**
   * Get provider
   * @return provider
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public ModelProviderInfo getProvider() {
    return provider;
  }

  public void setProvider(ModelProviderInfo provider) {
    this.provider = provider;
  }

  public V1DetailClusterResponse reason(String reason) {
    this.reason = reason;
    return this;
  }

  /**
   * Get reason
   * @return reason
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public V1DetailClusterResponse resourceInfo(V1ClusterResourceInfo resourceInfo) {
    this.resourceInfo = resourceInfo;
    return this;
  }

  /**
   * Get resourceInfo
   * @return resourceInfo
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public V1ClusterResourceInfo getResourceInfo() {
    return resourceInfo;
  }

  public void setResourceInfo(V1ClusterResourceInfo resourceInfo) {
    this.resourceInfo = resourceInfo;
  }

  public V1DetailClusterResponse status(String status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public V1DetailClusterResponse updateTime(OffsetDateTime updateTime) {
    this.updateTime = updateTime;
    return this;
  }

  /**
   * Get updateTime
   * @return updateTime
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public OffsetDateTime getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(OffsetDateTime updateTime) {
    this.updateTime = updateTime;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1DetailClusterResponse v1DetailClusterResponse = (V1DetailClusterResponse) o;
    return Objects.equals(this.alias, v1DetailClusterResponse.alias) &&
        Objects.equals(this.apiServerURL, v1DetailClusterResponse.apiServerURL) &&
        Objects.equals(this.createTime, v1DetailClusterResponse.createTime) &&
        Objects.equals(this.dashboardURL, v1DetailClusterResponse.dashboardURL) &&
        Objects.equals(this.description, v1DetailClusterResponse.description) &&
        Objects.equals(this.icon, v1DetailClusterResponse.icon) &&
        Objects.equals(this.kubeConfig, v1DetailClusterResponse.kubeConfig) &&
        Objects.equals(this.kubeConfigSecret, v1DetailClusterResponse.kubeConfigSecret) &&
        Objects.equals(this.labels, v1DetailClusterResponse.labels) &&
        Objects.equals(this.name, v1DetailClusterResponse.name) &&
        Objects.equals(this.provider, v1DetailClusterResponse.provider) &&
        Objects.equals(this.reason, v1DetailClusterResponse.reason) &&
        Objects.equals(this.resourceInfo, v1DetailClusterResponse.resourceInfo) &&
        Objects.equals(this.status, v1DetailClusterResponse.status) &&
        Objects.equals(this.updateTime, v1DetailClusterResponse.updateTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, apiServerURL, createTime, dashboardURL, description, icon, kubeConfig, kubeConfigSecret, labels, name, provider, reason, resourceInfo, status, updateTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1DetailClusterResponse {\n");
    
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    apiServerURL: ").append(toIndentedString(apiServerURL)).append("\n");
    sb.append("    createTime: ").append(toIndentedString(createTime)).append("\n");
    sb.append("    dashboardURL: ").append(toIndentedString(dashboardURL)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    icon: ").append(toIndentedString(icon)).append("\n");
    sb.append("    kubeConfig: ").append(toIndentedString(kubeConfig)).append("\n");
    sb.append("    kubeConfigSecret: ").append(toIndentedString(kubeConfigSecret)).append("\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    provider: ").append(toIndentedString(provider)).append("\n");
    sb.append("    reason: ").append(toIndentedString(reason)).append("\n");
    sb.append("    resourceInfo: ").append(toIndentedString(resourceInfo)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    updateTime: ").append(toIndentedString(updateTime)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

