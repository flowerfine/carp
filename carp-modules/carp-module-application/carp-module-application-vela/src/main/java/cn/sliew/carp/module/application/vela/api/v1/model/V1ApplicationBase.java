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
 * V1ApplicationBase
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ApplicationBase   {
  @JsonProperty("alias")
  private String alias = null;

  @JsonProperty("annotations")
  @Valid
  private Map<String, String> annotations = null;

  @JsonProperty("createTime")
  private OffsetDateTime createTime = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("icon")
  private String icon = null;

  @JsonProperty("labels")
  @Valid
  private Map<String, String> labels = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("project")
  private V1ProjectBase project = null;

  @JsonProperty("readOnly")
  private Boolean readOnly = null;

  @JsonProperty("updateTime")
  private OffsetDateTime updateTime = null;

  public V1ApplicationBase alias(String alias) {
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

  public V1ApplicationBase annotations(Map<String, String> annotations) {
    this.annotations = annotations;
    return this;
  }

  public V1ApplicationBase putAnnotationsItem(String key, String annotationsItem) {
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

  public V1ApplicationBase createTime(OffsetDateTime createTime) {
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

  public V1ApplicationBase description(String description) {
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

  public V1ApplicationBase icon(String icon) {
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

  public V1ApplicationBase labels(Map<String, String> labels) {
    this.labels = labels;
    return this;
  }

  public V1ApplicationBase putLabelsItem(String key, String labelsItem) {
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

  public V1ApplicationBase name(String name) {
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

  public V1ApplicationBase project(V1ProjectBase project) {
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

  public V1ApplicationBase readOnly(Boolean readOnly) {
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

  public V1ApplicationBase updateTime(OffsetDateTime updateTime) {
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
    V1ApplicationBase v1ApplicationBase = (V1ApplicationBase) o;
    return Objects.equals(this.alias, v1ApplicationBase.alias) &&
        Objects.equals(this.annotations, v1ApplicationBase.annotations) &&
        Objects.equals(this.createTime, v1ApplicationBase.createTime) &&
        Objects.equals(this.description, v1ApplicationBase.description) &&
        Objects.equals(this.icon, v1ApplicationBase.icon) &&
        Objects.equals(this.labels, v1ApplicationBase.labels) &&
        Objects.equals(this.name, v1ApplicationBase.name) &&
        Objects.equals(this.project, v1ApplicationBase.project) &&
        Objects.equals(this.readOnly, v1ApplicationBase.readOnly) &&
        Objects.equals(this.updateTime, v1ApplicationBase.updateTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, annotations, createTime, description, icon, labels, name, project, readOnly, updateTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ApplicationBase {\n");
    
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    annotations: ").append(toIndentedString(annotations)).append("\n");
    sb.append("    createTime: ").append(toIndentedString(createTime)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    icon: ").append(toIndentedString(icon)).append("\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    project: ").append(toIndentedString(project)).append("\n");
    sb.append("    readOnly: ").append(toIndentedString(readOnly)).append("\n");
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

