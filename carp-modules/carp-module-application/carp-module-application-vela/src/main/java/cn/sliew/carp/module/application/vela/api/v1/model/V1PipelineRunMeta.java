package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1PipelineRunMeta
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1PipelineRunMeta   {
  @JsonProperty("pipelineName")
  private String pipelineName = null;

  @JsonProperty("pipelineRunName")
  private String pipelineRunName = null;

  @JsonProperty("project")
  private V1NameAlias project = null;

  public V1PipelineRunMeta pipelineName(String pipelineName) {
    this.pipelineName = pipelineName;
    return this;
  }

  /**
   * Get pipelineName
   * @return pipelineName
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getPipelineName() {
    return pipelineName;
  }

  public void setPipelineName(String pipelineName) {
    this.pipelineName = pipelineName;
  }

  public V1PipelineRunMeta pipelineRunName(String pipelineRunName) {
    this.pipelineRunName = pipelineRunName;
    return this;
  }

  /**
   * Get pipelineRunName
   * @return pipelineRunName
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getPipelineRunName() {
    return pipelineRunName;
  }

  public void setPipelineRunName(String pipelineRunName) {
    this.pipelineRunName = pipelineRunName;
  }

  public V1PipelineRunMeta project(V1NameAlias project) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1PipelineRunMeta v1PipelineRunMeta = (V1PipelineRunMeta) o;
    return Objects.equals(this.pipelineName, v1PipelineRunMeta.pipelineName) &&
        Objects.equals(this.pipelineRunName, v1PipelineRunMeta.pipelineRunName) &&
        Objects.equals(this.project, v1PipelineRunMeta.project);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pipelineName, pipelineRunName, project);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1PipelineRunMeta {\n");
    
    sb.append("    pipelineName: ").append(toIndentedString(pipelineName)).append("\n");
    sb.append("    pipelineRunName: ").append(toIndentedString(pipelineRunName)).append("\n");
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

