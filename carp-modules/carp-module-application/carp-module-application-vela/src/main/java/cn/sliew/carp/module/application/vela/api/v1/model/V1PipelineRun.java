package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;

import cn.sliew.carp.module.application.vela.api.v1.model.common.ModelValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1PipelineRun
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1PipelineRun   {
  @JsonProperty("contextName")
  private String contextName = null;

  @JsonProperty("contextValues")
  @Valid
  private List<ModelValue> contextValues = new ArrayList<ModelValue>();

  @JsonProperty("pipelineName")
  private String pipelineName = null;

  @JsonProperty("pipelineRunName")
  private String pipelineRunName = null;

  @JsonProperty("project")
  private V1NameAlias project = null;

  @JsonProperty("record")
  private Long record = null;

  @JsonProperty("spec")
  private V1alpha1WorkflowRunSpec spec = null;

  @JsonProperty("status")
  private V1alpha1WorkflowRunStatus status = null;

  public V1PipelineRun contextName(String contextName) {
    this.contextName = contextName;
    return this;
  }

  /**
   * Get contextName
   * @return contextName
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getContextName() {
    return contextName;
  }

  public void setContextName(String contextName) {
    this.contextName = contextName;
  }

  public V1PipelineRun contextValues(List<ModelValue> contextValues) {
    this.contextValues = contextValues;
    return this;
  }

  public V1PipelineRun addContextValuesItem(ModelValue contextValuesItem) {
    this.contextValues.add(contextValuesItem);
    return this;
  }

  /**
   * Get contextValues
   * @return contextValues
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<ModelValue> getContextValues() {
    return contextValues;
  }

  public void setContextValues(List<ModelValue> contextValues) {
    this.contextValues = contextValues;
  }

  public V1PipelineRun pipelineName(String pipelineName) {
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

  public V1PipelineRun pipelineRunName(String pipelineRunName) {
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

  public V1PipelineRun project(V1NameAlias project) {
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

  public V1PipelineRun record(Long record) {
    this.record = record;
    return this;
  }

  /**
   * Get record
   * @return record
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getRecord() {
    return record;
  }

  public void setRecord(Long record) {
    this.record = record;
  }

  public V1PipelineRun spec(V1alpha1WorkflowRunSpec spec) {
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

  public V1alpha1WorkflowRunSpec getSpec() {
    return spec;
  }

  public void setSpec(V1alpha1WorkflowRunSpec spec) {
    this.spec = spec;
  }

  public V1PipelineRun status(V1alpha1WorkflowRunStatus status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public V1alpha1WorkflowRunStatus getStatus() {
    return status;
  }

  public void setStatus(V1alpha1WorkflowRunStatus status) {
    this.status = status;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1PipelineRun v1PipelineRun = (V1PipelineRun) o;
    return Objects.equals(this.contextName, v1PipelineRun.contextName) &&
        Objects.equals(this.contextValues, v1PipelineRun.contextValues) &&
        Objects.equals(this.pipelineName, v1PipelineRun.pipelineName) &&
        Objects.equals(this.pipelineRunName, v1PipelineRun.pipelineRunName) &&
        Objects.equals(this.project, v1PipelineRun.project) &&
        Objects.equals(this.record, v1PipelineRun.record) &&
        Objects.equals(this.spec, v1PipelineRun.spec) &&
        Objects.equals(this.status, v1PipelineRun.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(contextName, contextValues, pipelineName, pipelineRunName, project, record, spec, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1PipelineRun {\n");
    
    sb.append("    contextName: ").append(toIndentedString(contextName)).append("\n");
    sb.append("    contextValues: ").append(toIndentedString(contextValues)).append("\n");
    sb.append("    pipelineName: ").append(toIndentedString(pipelineName)).append("\n");
    sb.append("    pipelineRunName: ").append(toIndentedString(pipelineRunName)).append("\n");
    sb.append("    project: ").append(toIndentedString(project)).append("\n");
    sb.append("    record: ").append(toIndentedString(record)).append("\n");
    sb.append("    spec: ").append(toIndentedString(spec)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

