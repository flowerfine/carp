package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
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
 * ModelCluster
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class ModelCluster   {
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

  @JsonProperty("status")
  private String status = null;

  @JsonProperty("updateTime")
  private OffsetDateTime updateTime = null;

  public ModelCluster alias(String alias) {
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

  public ModelCluster apiServerURL(String apiServerURL) {
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

  public ModelCluster createTime(OffsetDateTime createTime) {
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

  public ModelCluster dashboardURL(String dashboardURL) {
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

  public ModelCluster description(String description) {
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

  public ModelCluster icon(String icon) {
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

  public ModelCluster kubeConfig(String kubeConfig) {
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

  public ModelCluster kubeConfigSecret(String kubeConfigSecret) {
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

  public ModelCluster labels(Map<String, String> labels) {
    this.labels = labels;
    return this;
  }

  public ModelCluster putLabelsItem(String key, String labelsItem) {
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

  public ModelCluster name(String name) {
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

  public ModelCluster provider(ModelProviderInfo provider) {
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

  public ModelCluster reason(String reason) {
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

  public ModelCluster status(String status) {
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

  public ModelCluster updateTime(OffsetDateTime updateTime) {
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
    ModelCluster modelCluster = (ModelCluster) o;
    return Objects.equals(this.alias, modelCluster.alias) &&
        Objects.equals(this.apiServerURL, modelCluster.apiServerURL) &&
        Objects.equals(this.createTime, modelCluster.createTime) &&
        Objects.equals(this.dashboardURL, modelCluster.dashboardURL) &&
        Objects.equals(this.description, modelCluster.description) &&
        Objects.equals(this.icon, modelCluster.icon) &&
        Objects.equals(this.kubeConfig, modelCluster.kubeConfig) &&
        Objects.equals(this.kubeConfigSecret, modelCluster.kubeConfigSecret) &&
        Objects.equals(this.labels, modelCluster.labels) &&
        Objects.equals(this.name, modelCluster.name) &&
        Objects.equals(this.provider, modelCluster.provider) &&
        Objects.equals(this.reason, modelCluster.reason) &&
        Objects.equals(this.status, modelCluster.status) &&
        Objects.equals(this.updateTime, modelCluster.updateTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, apiServerURL, createTime, dashboardURL, description, icon, kubeConfig, kubeConfigSecret, labels, name, provider, reason, status, updateTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ModelCluster {\n");
    
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

