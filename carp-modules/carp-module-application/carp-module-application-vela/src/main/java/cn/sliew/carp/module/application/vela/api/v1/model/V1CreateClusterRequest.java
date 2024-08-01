package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;
import java.util.Map;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1CreateClusterRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1CreateClusterRequest   {
  @JsonProperty("alias")
  private String alias = null;

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
  private Map<String, String> labels = null;

  @JsonProperty("name")
  private String name = null;

  public V1CreateClusterRequest alias(String alias) {
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

  public V1CreateClusterRequest dashboardURL(String dashboardURL) {
    this.dashboardURL = dashboardURL;
    return this;
  }

  /**
   * Get dashboardURL
   * @return dashboardURL
  **/
  @ApiModelProperty(value = "")


  public String getDashboardURL() {
    return dashboardURL;
  }

  public void setDashboardURL(String dashboardURL) {
    this.dashboardURL = dashboardURL;
  }

  public V1CreateClusterRequest description(String description) {
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

  public V1CreateClusterRequest icon(String icon) {
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

  public V1CreateClusterRequest kubeConfig(String kubeConfig) {
    this.kubeConfig = kubeConfig;
    return this;
  }

  /**
   * Get kubeConfig
   * @return kubeConfig
  **/
  @ApiModelProperty(value = "")


  public String getKubeConfig() {
    return kubeConfig;
  }

  public void setKubeConfig(String kubeConfig) {
    this.kubeConfig = kubeConfig;
  }

  public V1CreateClusterRequest kubeConfigSecret(String kubeConfigSecret) {
    this.kubeConfigSecret = kubeConfigSecret;
    return this;
  }

  /**
   * Get kubeConfigSecret
   * @return kubeConfigSecret
  **/
  @ApiModelProperty(value = "")


  public String getKubeConfigSecret() {
    return kubeConfigSecret;
  }

  public void setKubeConfigSecret(String kubeConfigSecret) {
    this.kubeConfigSecret = kubeConfigSecret;
  }

  public V1CreateClusterRequest labels(Map<String, String> labels) {
    this.labels = labels;
    return this;
  }

  public V1CreateClusterRequest putLabelsItem(String key, String labelsItem) {
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

  public V1CreateClusterRequest name(String name) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1CreateClusterRequest v1CreateClusterRequest = (V1CreateClusterRequest) o;
    return Objects.equals(this.alias, v1CreateClusterRequest.alias) &&
        Objects.equals(this.dashboardURL, v1CreateClusterRequest.dashboardURL) &&
        Objects.equals(this.description, v1CreateClusterRequest.description) &&
        Objects.equals(this.icon, v1CreateClusterRequest.icon) &&
        Objects.equals(this.kubeConfig, v1CreateClusterRequest.kubeConfig) &&
        Objects.equals(this.kubeConfigSecret, v1CreateClusterRequest.kubeConfigSecret) &&
        Objects.equals(this.labels, v1CreateClusterRequest.labels) &&
        Objects.equals(this.name, v1CreateClusterRequest.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, dashboardURL, description, icon, kubeConfig, kubeConfigSecret, labels, name);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1CreateClusterRequest {\n");
    
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    dashboardURL: ").append(toIndentedString(dashboardURL)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    icon: ").append(toIndentedString(icon)).append("\n");
    sb.append("    kubeConfig: ").append(toIndentedString(kubeConfig)).append("\n");
    sb.append("    kubeConfigSecret: ").append(toIndentedString(kubeConfigSecret)).append("\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
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

