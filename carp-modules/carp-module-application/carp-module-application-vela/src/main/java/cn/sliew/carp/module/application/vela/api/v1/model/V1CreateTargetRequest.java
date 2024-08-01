package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1CreateTargetRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1CreateTargetRequest   {
  @JsonProperty("alias")
  private String alias = null;

  @JsonProperty("cluster")
  private V1ClusterTarget cluster = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("project")
  private String project = null;

  @JsonProperty("variable")
  private Object variable = null;

  public V1CreateTargetRequest alias(String alias) {
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

  public V1CreateTargetRequest cluster(V1ClusterTarget cluster) {
    this.cluster = cluster;
    return this;
  }

  /**
   * Get cluster
   * @return cluster
  **/
  @ApiModelProperty(value = "")

  @Valid

  public V1ClusterTarget getCluster() {
    return cluster;
  }

  public void setCluster(V1ClusterTarget cluster) {
    this.cluster = cluster;
  }

  public V1CreateTargetRequest description(String description) {
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

  public V1CreateTargetRequest name(String name) {
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

  public V1CreateTargetRequest project(String project) {
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

  public V1CreateTargetRequest variable(Object variable) {
    this.variable = variable;
    return this;
  }

  /**
   * Get variable
   * @return variable
  **/
  @ApiModelProperty(value = "")


  public Object getVariable() {
    return variable;
  }

  public void setVariable(Object variable) {
    this.variable = variable;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1CreateTargetRequest v1CreateTargetRequest = (V1CreateTargetRequest) o;
    return Objects.equals(this.alias, v1CreateTargetRequest.alias) &&
        Objects.equals(this.cluster, v1CreateTargetRequest.cluster) &&
        Objects.equals(this.description, v1CreateTargetRequest.description) &&
        Objects.equals(this.name, v1CreateTargetRequest.name) &&
        Objects.equals(this.project, v1CreateTargetRequest.project) &&
        Objects.equals(this.variable, v1CreateTargetRequest.variable);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, cluster, description, name, project, variable);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1CreateTargetRequest {\n");
    
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    cluster: ").append(toIndentedString(cluster)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    project: ").append(toIndentedString(project)).append("\n");
    sb.append("    variable: ").append(toIndentedString(variable)).append("\n");
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

