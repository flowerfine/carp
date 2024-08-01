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
 * V1CreateEnvRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1CreateEnvRequest   {
  @JsonProperty("alias")
  private String alias = null;

  @JsonProperty("allowTargetConflict")
  private Boolean allowTargetConflict = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("namespace")
  private String namespace = null;

  @JsonProperty("project")
  private String project = null;

  @JsonProperty("targets")
  @Valid
  private List<String> targets = null;

  public V1CreateEnvRequest alias(String alias) {
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

  public V1CreateEnvRequest allowTargetConflict(Boolean allowTargetConflict) {
    this.allowTargetConflict = allowTargetConflict;
    return this;
  }

  /**
   * Get allowTargetConflict
   * @return allowTargetConflict
  **/
  @ApiModelProperty(value = "")


  public Boolean isAllowTargetConflict() {
    return allowTargetConflict;
  }

  public void setAllowTargetConflict(Boolean allowTargetConflict) {
    this.allowTargetConflict = allowTargetConflict;
  }

  public V1CreateEnvRequest description(String description) {
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

  public V1CreateEnvRequest name(String name) {
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

  public V1CreateEnvRequest namespace(String namespace) {
    this.namespace = namespace;
    return this;
  }

  /**
   * Get namespace
   * @return namespace
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getNamespace() {
    return namespace;
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public V1CreateEnvRequest project(String project) {
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

  public V1CreateEnvRequest targets(List<String> targets) {
    this.targets = targets;
    return this;
  }

  public V1CreateEnvRequest addTargetsItem(String targetsItem) {
    if (this.targets == null) {
      this.targets = new ArrayList<String>();
    }
    this.targets.add(targetsItem);
    return this;
  }

  /**
   * Get targets
   * @return targets
  **/
  @ApiModelProperty(value = "")


  public List<String> getTargets() {
    return targets;
  }

  public void setTargets(List<String> targets) {
    this.targets = targets;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1CreateEnvRequest v1CreateEnvRequest = (V1CreateEnvRequest) o;
    return Objects.equals(this.alias, v1CreateEnvRequest.alias) &&
        Objects.equals(this.allowTargetConflict, v1CreateEnvRequest.allowTargetConflict) &&
        Objects.equals(this.description, v1CreateEnvRequest.description) &&
        Objects.equals(this.name, v1CreateEnvRequest.name) &&
        Objects.equals(this.namespace, v1CreateEnvRequest.namespace) &&
        Objects.equals(this.project, v1CreateEnvRequest.project) &&
        Objects.equals(this.targets, v1CreateEnvRequest.targets);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, allowTargetConflict, description, name, namespace, project, targets);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1CreateEnvRequest {\n");
    
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    allowTargetConflict: ").append(toIndentedString(allowTargetConflict)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    namespace: ").append(toIndentedString(namespace)).append("\n");
    sb.append("    project: ").append(toIndentedString(project)).append("\n");
    sb.append("    targets: ").append(toIndentedString(targets)).append("\n");
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

