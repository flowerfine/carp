package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1TargetBase
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1TargetBase   {
  @JsonProperty("alias")
  private String alias = null;

  @JsonProperty("appNum")
  private Long appNum = null;

  @JsonProperty("cluster")
  private V1ClusterTarget cluster = null;

  @JsonProperty("clusterAlias")
  private String clusterAlias = null;

  @JsonProperty("createTime")
  private OffsetDateTime createTime = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("project")
  private V1NameAlias project = null;

  @JsonProperty("updateTime")
  private OffsetDateTime updateTime = null;

  @JsonProperty("variable")
  private Object variable = null;

  public V1TargetBase alias(String alias) {
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

  public V1TargetBase appNum(Long appNum) {
    this.appNum = appNum;
    return this;
  }

  /**
   * Get appNum
   * @return appNum
  **/
  @ApiModelProperty(value = "")


  public Long getAppNum() {
    return appNum;
  }

  public void setAppNum(Long appNum) {
    this.appNum = appNum;
  }

  public V1TargetBase cluster(V1ClusterTarget cluster) {
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

  public V1TargetBase clusterAlias(String clusterAlias) {
    this.clusterAlias = clusterAlias;
    return this;
  }

  /**
   * Get clusterAlias
   * @return clusterAlias
  **/
  @ApiModelProperty(value = "")


  public String getClusterAlias() {
    return clusterAlias;
  }

  public void setClusterAlias(String clusterAlias) {
    this.clusterAlias = clusterAlias;
  }

  public V1TargetBase createTime(OffsetDateTime createTime) {
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

  public V1TargetBase description(String description) {
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

  public V1TargetBase name(String name) {
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

  public V1TargetBase project(V1NameAlias project) {
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

  public V1NameAlias getProject() {
    return project;
  }

  public void setProject(V1NameAlias project) {
    this.project = project;
  }

  public V1TargetBase updateTime(OffsetDateTime updateTime) {
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

  public V1TargetBase variable(Object variable) {
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
    V1TargetBase v1TargetBase = (V1TargetBase) o;
    return Objects.equals(this.alias, v1TargetBase.alias) &&
        Objects.equals(this.appNum, v1TargetBase.appNum) &&
        Objects.equals(this.cluster, v1TargetBase.cluster) &&
        Objects.equals(this.clusterAlias, v1TargetBase.clusterAlias) &&
        Objects.equals(this.createTime, v1TargetBase.createTime) &&
        Objects.equals(this.description, v1TargetBase.description) &&
        Objects.equals(this.name, v1TargetBase.name) &&
        Objects.equals(this.project, v1TargetBase.project) &&
        Objects.equals(this.updateTime, v1TargetBase.updateTime) &&
        Objects.equals(this.variable, v1TargetBase.variable);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, appNum, cluster, clusterAlias, createTime, description, name, project, updateTime, variable);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1TargetBase {\n");
    
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    appNum: ").append(toIndentedString(appNum)).append("\n");
    sb.append("    cluster: ").append(toIndentedString(cluster)).append("\n");
    sb.append("    clusterAlias: ").append(toIndentedString(clusterAlias)).append("\n");
    sb.append("    createTime: ").append(toIndentedString(createTime)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    project: ").append(toIndentedString(project)).append("\n");
    sb.append("    updateTime: ").append(toIndentedString(updateTime)).append("\n");
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

