package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1DetailApplicationResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1DetailApplicationResponse   {
  @JsonProperty("alias")
  private String alias = null;

  @JsonProperty("annotations")
  @Valid
  private Map<String, String> annotations = null;

  @JsonProperty("createTime")
  private OffsetDateTime createTime = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("envBindings")
  @Valid
  private List<String> envBindings = new ArrayList<String>();

  @JsonProperty("icon")
  private String icon = null;

  @JsonProperty("labels")
  @Valid
  private Map<String, String> labels = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("policies")
  @Valid
  private List<String> policies = new ArrayList<String>();

  @JsonProperty("project")
  private V1ProjectBase project = null;

  @JsonProperty("readOnly")
  private Boolean readOnly = null;

  @JsonProperty("resourceInfo")
  private V1ApplicationResourceInfo resourceInfo = null;

  @JsonProperty("updateTime")
  private OffsetDateTime updateTime = null;

  public V1DetailApplicationResponse alias(String alias) {
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

  public V1DetailApplicationResponse annotations(Map<String, String> annotations) {
    this.annotations = annotations;
    return this;
  }

  public V1DetailApplicationResponse putAnnotationsItem(String key, String annotationsItem) {
    if (this.annotations == null) {
      this.annotations = new HashMap<String, String>();
    }
    this.annotations.put(key, annotationsItem);
    return this;
  }

  /**
   * Get annotations
   * @return annotations
  **/
  @ApiModelProperty(value = "")


  public Map<String, String> getAnnotations() {
    return annotations;
  }

  public void setAnnotations(Map<String, String> annotations) {
    this.annotations = annotations;
  }

  public V1DetailApplicationResponse createTime(OffsetDateTime createTime) {
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

  public V1DetailApplicationResponse description(String description) {
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

  public V1DetailApplicationResponse envBindings(List<String> envBindings) {
    this.envBindings = envBindings;
    return this;
  }

  public V1DetailApplicationResponse addEnvBindingsItem(String envBindingsItem) {
    this.envBindings.add(envBindingsItem);
    return this;
  }

  /**
   * Get envBindings
   * @return envBindings
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public List<String> getEnvBindings() {
    return envBindings;
  }

  public void setEnvBindings(List<String> envBindings) {
    this.envBindings = envBindings;
  }

  public V1DetailApplicationResponse icon(String icon) {
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

  public V1DetailApplicationResponse labels(Map<String, String> labels) {
    this.labels = labels;
    return this;
  }

  public V1DetailApplicationResponse putLabelsItem(String key, String labelsItem) {
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

  public V1DetailApplicationResponse name(String name) {
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

  public V1DetailApplicationResponse policies(List<String> policies) {
    this.policies = policies;
    return this;
  }

  public V1DetailApplicationResponse addPoliciesItem(String policiesItem) {
    this.policies.add(policiesItem);
    return this;
  }

  /**
   * Get policies
   * @return policies
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public List<String> getPolicies() {
    return policies;
  }

  public void setPolicies(List<String> policies) {
    this.policies = policies;
  }

  public V1DetailApplicationResponse project(V1ProjectBase project) {
    this.project = project;
    return this;
  }

  /**
   * Get project
   * @return project
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public V1ProjectBase getProject() {
    return project;
  }

  public void setProject(V1ProjectBase project) {
    this.project = project;
  }

  public V1DetailApplicationResponse readOnly(Boolean readOnly) {
    this.readOnly = readOnly;
    return this;
  }

  /**
   * Get readOnly
   * @return readOnly
  **/
  @ApiModelProperty(value = "")


  public Boolean isReadOnly() {
    return readOnly;
  }

  public void setReadOnly(Boolean readOnly) {
    this.readOnly = readOnly;
  }

  public V1DetailApplicationResponse resourceInfo(V1ApplicationResourceInfo resourceInfo) {
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

  public V1ApplicationResourceInfo getResourceInfo() {
    return resourceInfo;
  }

  public void setResourceInfo(V1ApplicationResourceInfo resourceInfo) {
    this.resourceInfo = resourceInfo;
  }

  public V1DetailApplicationResponse updateTime(OffsetDateTime updateTime) {
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
    V1DetailApplicationResponse v1DetailApplicationResponse = (V1DetailApplicationResponse) o;
    return Objects.equals(this.alias, v1DetailApplicationResponse.alias) &&
        Objects.equals(this.annotations, v1DetailApplicationResponse.annotations) &&
        Objects.equals(this.createTime, v1DetailApplicationResponse.createTime) &&
        Objects.equals(this.description, v1DetailApplicationResponse.description) &&
        Objects.equals(this.envBindings, v1DetailApplicationResponse.envBindings) &&
        Objects.equals(this.icon, v1DetailApplicationResponse.icon) &&
        Objects.equals(this.labels, v1DetailApplicationResponse.labels) &&
        Objects.equals(this.name, v1DetailApplicationResponse.name) &&
        Objects.equals(this.policies, v1DetailApplicationResponse.policies) &&
        Objects.equals(this.project, v1DetailApplicationResponse.project) &&
        Objects.equals(this.readOnly, v1DetailApplicationResponse.readOnly) &&
        Objects.equals(this.resourceInfo, v1DetailApplicationResponse.resourceInfo) &&
        Objects.equals(this.updateTime, v1DetailApplicationResponse.updateTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, annotations, createTime, description, envBindings, icon, labels, name, policies, project, readOnly, resourceInfo, updateTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1DetailApplicationResponse {\n");
    
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    annotations: ").append(toIndentedString(annotations)).append("\n");
    sb.append("    createTime: ").append(toIndentedString(createTime)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    envBindings: ").append(toIndentedString(envBindings)).append("\n");
    sb.append("    icon: ").append(toIndentedString(icon)).append("\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    policies: ").append(toIndentedString(policies)).append("\n");
    sb.append("    project: ").append(toIndentedString(project)).append("\n");
    sb.append("    readOnly: ").append(toIndentedString(readOnly)).append("\n");
    sb.append("    resourceInfo: ").append(toIndentedString(resourceInfo)).append("\n");
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

