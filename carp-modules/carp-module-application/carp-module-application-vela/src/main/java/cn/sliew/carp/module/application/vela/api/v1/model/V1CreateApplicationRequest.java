package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1CreateApplicationRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1CreateApplicationRequest   {
  @JsonProperty("alias")
  private String alias = null;

  @JsonProperty("component")
  private V1CreateComponentRequest component = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("envBinding")
  @Valid
  private List<V1EnvBinding> envBinding = null;

  @JsonProperty("icon")
  private String icon = null;

  @JsonProperty("labels")
  @Valid
  private Map<String, String> labels = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("project")
  private String project = null;

  public V1CreateApplicationRequest alias(String alias) {
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

  public V1CreateApplicationRequest component(V1CreateComponentRequest component) {
    this.component = component;
    return this;
  }

  /**
   * Get component
   * @return component
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public V1CreateComponentRequest getComponent() {
    return component;
  }

  public void setComponent(V1CreateComponentRequest component) {
    this.component = component;
  }

  public V1CreateApplicationRequest description(String description) {
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

  public V1CreateApplicationRequest envBinding(List<V1EnvBinding> envBinding) {
    this.envBinding = envBinding;
    return this;
  }

  public V1CreateApplicationRequest addEnvBindingItem(V1EnvBinding envBindingItem) {
    if (this.envBinding == null) {
      this.envBinding = new ArrayList<V1EnvBinding>();
    }
    this.envBinding.add(envBindingItem);
    return this;
  }

  /**
   * Get envBinding
   * @return envBinding
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<V1EnvBinding> getEnvBinding() {
    return envBinding;
  }

  public void setEnvBinding(List<V1EnvBinding> envBinding) {
    this.envBinding = envBinding;
  }

  public V1CreateApplicationRequest icon(String icon) {
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

  public V1CreateApplicationRequest labels(Map<String, String> labels) {
    this.labels = labels;
    return this;
  }

  public V1CreateApplicationRequest putLabelsItem(String key, String labelsItem) {
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

  public V1CreateApplicationRequest name(String name) {
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

  public V1CreateApplicationRequest project(String project) {
    this.project = project;
    return this;
  }

  /**
   * Get project
   * @return project
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getProject() {
    return project;
  }

  public void setProject(String project) {
    this.project = project;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1CreateApplicationRequest v1CreateApplicationRequest = (V1CreateApplicationRequest) o;
    return Objects.equals(this.alias, v1CreateApplicationRequest.alias) &&
        Objects.equals(this.component, v1CreateApplicationRequest.component) &&
        Objects.equals(this.description, v1CreateApplicationRequest.description) &&
        Objects.equals(this.envBinding, v1CreateApplicationRequest.envBinding) &&
        Objects.equals(this.icon, v1CreateApplicationRequest.icon) &&
        Objects.equals(this.labels, v1CreateApplicationRequest.labels) &&
        Objects.equals(this.name, v1CreateApplicationRequest.name) &&
        Objects.equals(this.project, v1CreateApplicationRequest.project);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, component, description, envBinding, icon, labels, name, project);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1CreateApplicationRequest {\n");
    
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    component: ").append(toIndentedString(component)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    envBinding: ").append(toIndentedString(envBinding)).append("\n");
    sb.append("    icon: ").append(toIndentedString(icon)).append("\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    project: ").append(toIndentedString(project)).append("\n");
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

