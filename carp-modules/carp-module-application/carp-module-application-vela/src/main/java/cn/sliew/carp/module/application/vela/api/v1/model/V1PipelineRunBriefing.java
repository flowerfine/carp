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
 * V1PipelineRunBriefing
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1PipelineRunBriefing   {
  @JsonProperty("contextName")
  private String contextName = null;

  @JsonProperty("contextValues")
  @Valid
  private List<ModelValue> contextValues = new ArrayList<ModelValue>();

  @JsonProperty("endTime")
  private String endTime = null;

  @JsonProperty("finished")
  private Boolean finished = null;

  @JsonProperty("message")
  private String message = null;

  @JsonProperty("phase")
  private String phase = null;

  @JsonProperty("pipelineRunName")
  private String pipelineRunName = null;

  @JsonProperty("startTime")
  private String startTime = null;

  public V1PipelineRunBriefing contextName(String contextName) {
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

  public V1PipelineRunBriefing contextValues(List<ModelValue> contextValues) {
    this.contextValues = contextValues;
    return this;
  }

  public V1PipelineRunBriefing addContextValuesItem(ModelValue contextValuesItem) {
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

  public V1PipelineRunBriefing endTime(String endTime) {
    this.endTime = endTime;
    return this;
  }

  /**
   * Get endTime
   * @return endTime
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public V1PipelineRunBriefing finished(Boolean finished) {
    this.finished = finished;
    return this;
  }

  /**
   * Get finished
   * @return finished
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Boolean isFinished() {
    return finished;
  }

  public void setFinished(Boolean finished) {
    this.finished = finished;
  }

  public V1PipelineRunBriefing message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   * @return message
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public V1PipelineRunBriefing phase(String phase) {
    this.phase = phase;
    return this;
  }

  /**
   * Get phase
   * @return phase
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getPhase() {
    return phase;
  }

  public void setPhase(String phase) {
    this.phase = phase;
  }

  public V1PipelineRunBriefing pipelineRunName(String pipelineRunName) {
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

  public V1PipelineRunBriefing startTime(String startTime) {
    this.startTime = startTime;
    return this;
  }

  /**
   * Get startTime
   * @return startTime
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1PipelineRunBriefing v1PipelineRunBriefing = (V1PipelineRunBriefing) o;
    return Objects.equals(this.contextName, v1PipelineRunBriefing.contextName) &&
        Objects.equals(this.contextValues, v1PipelineRunBriefing.contextValues) &&
        Objects.equals(this.endTime, v1PipelineRunBriefing.endTime) &&
        Objects.equals(this.finished, v1PipelineRunBriefing.finished) &&
        Objects.equals(this.message, v1PipelineRunBriefing.message) &&
        Objects.equals(this.phase, v1PipelineRunBriefing.phase) &&
        Objects.equals(this.pipelineRunName, v1PipelineRunBriefing.pipelineRunName) &&
        Objects.equals(this.startTime, v1PipelineRunBriefing.startTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(contextName, contextValues, endTime, finished, message, phase, pipelineRunName, startTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1PipelineRunBriefing {\n");
    
    sb.append("    contextName: ").append(toIndentedString(contextName)).append("\n");
    sb.append("    contextValues: ").append(toIndentedString(contextValues)).append("\n");
    sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
    sb.append("    finished: ").append(toIndentedString(finished)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    phase: ").append(toIndentedString(phase)).append("\n");
    sb.append("    pipelineRunName: ").append(toIndentedString(pipelineRunName)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
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

