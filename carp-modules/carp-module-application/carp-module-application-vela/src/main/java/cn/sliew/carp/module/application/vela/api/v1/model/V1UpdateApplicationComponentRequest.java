package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1UpdateApplicationComponentRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1UpdateApplicationComponentRequest   {
  @JsonProperty("alias")
  private String alias = null;

  @JsonProperty("dependsOn")
  @Valid
  private List<String> dependsOn = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("icon")
  private String icon = null;

  @JsonProperty("labels")
  private V1UpdateApplicationComponentRequestLabels labels = null;

  @JsonProperty("properties")
  private String properties = null;

  public V1UpdateApplicationComponentRequest alias(String alias) {
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

  public V1UpdateApplicationComponentRequest dependsOn(List<String> dependsOn) {
    this.dependsOn = dependsOn;
    return this;
  }

  public V1UpdateApplicationComponentRequest addDependsOnItem(String dependsOnItem) {
    if (this.dependsOn == null) {
      this.dependsOn = new ArrayList<String>();
    }
    this.dependsOn.add(dependsOnItem);
    return this;
  }

  /**
   * Get dependsOn
   * @return dependsOn
  **/
  @ApiModelProperty(value = "")


  public List<String> getDependsOn() {
    return dependsOn;
  }

  public void setDependsOn(List<String> dependsOn) {
    this.dependsOn = dependsOn;
  }

  public V1UpdateApplicationComponentRequest description(String description) {
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

  public V1UpdateApplicationComponentRequest icon(String icon) {
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

  public V1UpdateApplicationComponentRequest labels(V1UpdateApplicationComponentRequestLabels labels) {
    this.labels = labels;
    return this;
  }

  /**
   * Get labels
   * @return labels
  **/
  @ApiModelProperty(value = "")

  @Valid

  public V1UpdateApplicationComponentRequestLabels getLabels() {
    return labels;
  }

  public void setLabels(V1UpdateApplicationComponentRequestLabels labels) {
    this.labels = labels;
  }

  public V1UpdateApplicationComponentRequest properties(String properties) {
    this.properties = properties;
    return this;
  }

  /**
   * Get properties
   * @return properties
  **/
  @ApiModelProperty(value = "")


  public String getProperties() {
    return properties;
  }

  public void setProperties(String properties) {
    this.properties = properties;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1UpdateApplicationComponentRequest v1UpdateApplicationComponentRequest = (V1UpdateApplicationComponentRequest) o;
    return Objects.equals(this.alias, v1UpdateApplicationComponentRequest.alias) &&
        Objects.equals(this.dependsOn, v1UpdateApplicationComponentRequest.dependsOn) &&
        Objects.equals(this.description, v1UpdateApplicationComponentRequest.description) &&
        Objects.equals(this.icon, v1UpdateApplicationComponentRequest.icon) &&
        Objects.equals(this.labels, v1UpdateApplicationComponentRequest.labels) &&
        Objects.equals(this.properties, v1UpdateApplicationComponentRequest.properties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, dependsOn, description, icon, labels, properties);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1UpdateApplicationComponentRequest {\n");
    
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    dependsOn: ").append(toIndentedString(dependsOn)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    icon: ").append(toIndentedString(icon)).append("\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
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

