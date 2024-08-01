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
 * V1GetPipelineResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1GetPipelineResponse   {
  @JsonProperty("alias")
  private String alias = null;

  @JsonProperty("createTime")
  private OffsetDateTime createTime = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("info")
  private V1PipelineInfo info = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("project")
  private V1NameAlias project = null;

  @JsonProperty("spec")
  private ModelWorkflowSpec spec = null;

  public V1GetPipelineResponse alias(String alias) {
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

  public V1GetPipelineResponse createTime(OffsetDateTime createTime) {
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

  public V1GetPipelineResponse description(String description) {
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

  public V1GetPipelineResponse info(V1PipelineInfo info) {
    this.info = info;
    return this;
  }

  /**
   * Get info
   * @return info
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public V1PipelineInfo getInfo() {
    return info;
  }

  public void setInfo(V1PipelineInfo info) {
    this.info = info;
  }

  public V1GetPipelineResponse name(String name) {
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

  public V1GetPipelineResponse project(V1NameAlias project) {
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

  public V1GetPipelineResponse spec(ModelWorkflowSpec spec) {
    this.spec = spec;
    return this;
  }

  /**
   * Get spec
   * @return spec
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public ModelWorkflowSpec getSpec() {
    return spec;
  }

  public void setSpec(ModelWorkflowSpec spec) {
    this.spec = spec;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1GetPipelineResponse v1GetPipelineResponse = (V1GetPipelineResponse) o;
    return Objects.equals(this.alias, v1GetPipelineResponse.alias) &&
        Objects.equals(this.createTime, v1GetPipelineResponse.createTime) &&
        Objects.equals(this.description, v1GetPipelineResponse.description) &&
        Objects.equals(this.info, v1GetPipelineResponse.info) &&
        Objects.equals(this.name, v1GetPipelineResponse.name) &&
        Objects.equals(this.project, v1GetPipelineResponse.project) &&
        Objects.equals(this.spec, v1GetPipelineResponse.spec);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, createTime, description, info, name, project, spec);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1GetPipelineResponse {\n");
    
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    createTime: ").append(toIndentedString(createTime)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    info: ").append(toIndentedString(info)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    project: ").append(toIndentedString(project)).append("\n");
    sb.append("    spec: ").append(toIndentedString(spec)).append("\n");
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

