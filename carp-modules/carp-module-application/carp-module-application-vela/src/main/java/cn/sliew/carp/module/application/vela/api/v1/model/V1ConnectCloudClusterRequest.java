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
 * V1ConnectCloudClusterRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ConnectCloudClusterRequest   {
  @JsonProperty("accessKeyID")
  private String accessKeyID = null;

  @JsonProperty("accessKeySecret")
  private String accessKeySecret = null;

  @JsonProperty("alias")
  private String alias = null;

  @JsonProperty("clusterID")
  private String clusterID = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("icon")
  private String icon = null;

  @JsonProperty("labels")
  @Valid
  private Map<String, String> labels = null;

  @JsonProperty("name")
  private String name = null;

  public V1ConnectCloudClusterRequest accessKeyID(String accessKeyID) {
    this.accessKeyID = accessKeyID;
    return this;
  }

  /**
   * Get accessKeyID
   * @return accessKeyID
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getAccessKeyID() {
    return accessKeyID;
  }

  public void setAccessKeyID(String accessKeyID) {
    this.accessKeyID = accessKeyID;
  }

  public V1ConnectCloudClusterRequest accessKeySecret(String accessKeySecret) {
    this.accessKeySecret = accessKeySecret;
    return this;
  }

  /**
   * Get accessKeySecret
   * @return accessKeySecret
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getAccessKeySecret() {
    return accessKeySecret;
  }

  public void setAccessKeySecret(String accessKeySecret) {
    this.accessKeySecret = accessKeySecret;
  }

  public V1ConnectCloudClusterRequest alias(String alias) {
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

  public V1ConnectCloudClusterRequest clusterID(String clusterID) {
    this.clusterID = clusterID;
    return this;
  }

  /**
   * Get clusterID
   * @return clusterID
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getClusterID() {
    return clusterID;
  }

  public void setClusterID(String clusterID) {
    this.clusterID = clusterID;
  }

  public V1ConnectCloudClusterRequest description(String description) {
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

  public V1ConnectCloudClusterRequest icon(String icon) {
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

  public V1ConnectCloudClusterRequest labels(Map<String, String> labels) {
    this.labels = labels;
    return this;
  }

  public V1ConnectCloudClusterRequest putLabelsItem(String key, String labelsItem) {
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

  public V1ConnectCloudClusterRequest name(String name) {
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
    V1ConnectCloudClusterRequest v1ConnectCloudClusterRequest = (V1ConnectCloudClusterRequest) o;
    return Objects.equals(this.accessKeyID, v1ConnectCloudClusterRequest.accessKeyID) &&
        Objects.equals(this.accessKeySecret, v1ConnectCloudClusterRequest.accessKeySecret) &&
        Objects.equals(this.alias, v1ConnectCloudClusterRequest.alias) &&
        Objects.equals(this.clusterID, v1ConnectCloudClusterRequest.clusterID) &&
        Objects.equals(this.description, v1ConnectCloudClusterRequest.description) &&
        Objects.equals(this.icon, v1ConnectCloudClusterRequest.icon) &&
        Objects.equals(this.labels, v1ConnectCloudClusterRequest.labels) &&
        Objects.equals(this.name, v1ConnectCloudClusterRequest.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accessKeyID, accessKeySecret, alias, clusterID, description, icon, labels, name);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ConnectCloudClusterRequest {\n");
    
    sb.append("    accessKeyID: ").append(toIndentedString(accessKeyID)).append("\n");
    sb.append("    accessKeySecret: ").append(toIndentedString(accessKeySecret)).append("\n");
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    clusterID: ").append(toIndentedString(clusterID)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    icon: ").append(toIndentedString(icon)).append("\n");
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

