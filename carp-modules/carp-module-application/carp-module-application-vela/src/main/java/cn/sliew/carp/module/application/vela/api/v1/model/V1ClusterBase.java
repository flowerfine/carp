package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;

import cn.sliew.carp.module.application.vela.api.v1.model.common.ModelProviderInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.Map;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1ClusterBase
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ClusterBase   {
  @JsonProperty("alias")
  private String alias = null;

  @JsonProperty("apiServerURL")
  private String apiServerURL = null;

  @JsonProperty("dashboardURL")
  private String dashboardURL = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("icon")
  private String icon = null;

  @JsonProperty("labels")
  @Valid
  private Map<String, String> labels = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("providerInfo")
  private ModelProviderInfo providerInfo = null;

  @JsonProperty("reason")
  private String reason = null;

  @JsonProperty("status")
  private String status = null;

  public V1ClusterBase alias(String alias) {
    this.alias = alias;
    return this;
  }

  /**
   * Get alias
   * @return alias
  **/
  @ApiModelProperty(value = "")


  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public V1ClusterBase apiServerURL(String apiServerURL) {
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

  public V1ClusterBase dashboardURL(String dashboardURL) {
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

  public V1ClusterBase description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  **/
  @ApiModelProperty(value = "")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public V1ClusterBase icon(String icon) {
    this.icon = icon;
    return this;
  }

  /**
   * Get icon
   * @return icon
  **/
  @ApiModelProperty(value = "")


  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public V1ClusterBase labels(Map<String, String> labels) {
    this.labels = labels;
    return this;
  }

  public V1ClusterBase putLabelsItem(String key, String labelsItem) {
    if (this.labels == null) {
      this.labels = new HashMap<String, String>();
    }
    this.labels.put(key, labelsItem);
    return this;
  }

  /**
   * Get labels
   * @return labels
  **/
  @ApiModelProperty(value = "")


  public Map<String, String> getLabels() {
    return labels;
  }

  public void setLabels(Map<String, String> labels) {
    this.labels = labels;
  }

  public V1ClusterBase name(String name) {
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

  public V1ClusterBase providerInfo(ModelProviderInfo providerInfo) {
    this.providerInfo = providerInfo;
    return this;
  }

  /**
   * Get providerInfo
   * @return providerInfo
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public ModelProviderInfo getProviderInfo() {
    return providerInfo;
  }

  public void setProviderInfo(ModelProviderInfo providerInfo) {
    this.providerInfo = providerInfo;
  }

  public V1ClusterBase reason(String reason) {
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

  public V1ClusterBase status(String status) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1ClusterBase v1ClusterBase = (V1ClusterBase) o;
    return Objects.equals(this.alias, v1ClusterBase.alias) &&
        Objects.equals(this.apiServerURL, v1ClusterBase.apiServerURL) &&
        Objects.equals(this.dashboardURL, v1ClusterBase.dashboardURL) &&
        Objects.equals(this.description, v1ClusterBase.description) &&
        Objects.equals(this.icon, v1ClusterBase.icon) &&
        Objects.equals(this.labels, v1ClusterBase.labels) &&
        Objects.equals(this.name, v1ClusterBase.name) &&
        Objects.equals(this.providerInfo, v1ClusterBase.providerInfo) &&
        Objects.equals(this.reason, v1ClusterBase.reason) &&
        Objects.equals(this.status, v1ClusterBase.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, apiServerURL, dashboardURL, description, icon, labels, name, providerInfo, reason, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ClusterBase {\n");
    
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    apiServerURL: ").append(toIndentedString(apiServerURL)).append("\n");
    sb.append("    dashboardURL: ").append(toIndentedString(dashboardURL)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    icon: ").append(toIndentedString(icon)).append("\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    providerInfo: ").append(toIndentedString(providerInfo)).append("\n");
    sb.append("    reason: ").append(toIndentedString(reason)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

