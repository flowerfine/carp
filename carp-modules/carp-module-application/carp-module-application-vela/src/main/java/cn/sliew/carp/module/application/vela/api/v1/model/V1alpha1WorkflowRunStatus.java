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
 * V1alpha1WorkflowRunStatus
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1alpha1WorkflowRunStatus   {
  @JsonProperty("conditions")
  @Valid
  private List<ConditionCondition> conditions = null;

  @JsonProperty("contextBackend")
  private V1ObjectReference contextBackend = null;

  @JsonProperty("endTime")
  private String endTime = null;

  @JsonProperty("finished")
  private Boolean finished = null;

  @JsonProperty("message")
  private String message = null;

  @JsonProperty("mode")
  private V1alpha1WorkflowExecuteMode mode = null;

  @JsonProperty("startTime")
  private String startTime = null;

  @JsonProperty("status")
  private String status = null;

  @JsonProperty("steps")
  @Valid
  private List<V1alpha1WorkflowStepStatus> steps = null;

  @JsonProperty("suspend")
  private Boolean suspend = null;

  @JsonProperty("suspendState")
  private String suspendState = null;

  @JsonProperty("terminated")
  private Boolean terminated = null;

  public V1alpha1WorkflowRunStatus conditions(List<ConditionCondition> conditions) {
    this.conditions = conditions;
    return this;
  }

  public V1alpha1WorkflowRunStatus addConditionsItem(ConditionCondition conditionsItem) {
    if (this.conditions == null) {
      this.conditions = new ArrayList<ConditionCondition>();
    }
    this.conditions.add(conditionsItem);
    return this;
  }

  /**
   * Get conditions
   * @return conditions
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<ConditionCondition> getConditions() {
    return conditions;
  }

  public void setConditions(List<ConditionCondition> conditions) {
    this.conditions = conditions;
  }

  public V1alpha1WorkflowRunStatus contextBackend(V1ObjectReference contextBackend) {
    this.contextBackend = contextBackend;
    return this;
  }

  /**
   * Get contextBackend
   * @return contextBackend
  **/
  @ApiModelProperty(value = "")

  @Valid

  public V1ObjectReference getContextBackend() {
    return contextBackend;
  }

  public void setContextBackend(V1ObjectReference contextBackend) {
    this.contextBackend = contextBackend;
  }

  public V1alpha1WorkflowRunStatus endTime(String endTime) {
    this.endTime = endTime;
    return this;
  }

  /**
   * Get endTime
   * @return endTime
  **/
  @ApiModelProperty(value = "")


  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public V1alpha1WorkflowRunStatus finished(Boolean finished) {
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

  public V1alpha1WorkflowRunStatus message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   * @return message
  **/
  @ApiModelProperty(value = "")


  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public V1alpha1WorkflowRunStatus mode(V1alpha1WorkflowExecuteMode mode) {
    this.mode = mode;
    return this;
  }

  /**
   * Get mode
   * @return mode
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public V1alpha1WorkflowExecuteMode getMode() {
    return mode;
  }

  public void setMode(V1alpha1WorkflowExecuteMode mode) {
    this.mode = mode;
  }

  public V1alpha1WorkflowRunStatus startTime(String startTime) {
    this.startTime = startTime;
    return this;
  }

  /**
   * Get startTime
   * @return startTime
  **/
  @ApiModelProperty(value = "")


  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public V1alpha1WorkflowRunStatus status(String status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public V1alpha1WorkflowRunStatus steps(List<V1alpha1WorkflowStepStatus> steps) {
    this.steps = steps;
    return this;
  }

  public V1alpha1WorkflowRunStatus addStepsItem(V1alpha1WorkflowStepStatus stepsItem) {
    if (this.steps == null) {
      this.steps = new ArrayList<V1alpha1WorkflowStepStatus>();
    }
    this.steps.add(stepsItem);
    return this;
  }

  /**
   * Get steps
   * @return steps
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<V1alpha1WorkflowStepStatus> getSteps() {
    return steps;
  }

  public void setSteps(List<V1alpha1WorkflowStepStatus> steps) {
    this.steps = steps;
  }

  public V1alpha1WorkflowRunStatus suspend(Boolean suspend) {
    this.suspend = suspend;
    return this;
  }

  /**
   * Get suspend
   * @return suspend
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Boolean isSuspend() {
    return suspend;
  }

  public void setSuspend(Boolean suspend) {
    this.suspend = suspend;
  }

  public V1alpha1WorkflowRunStatus suspendState(String suspendState) {
    this.suspendState = suspendState;
    return this;
  }

  /**
   * Get suspendState
   * @return suspendState
  **/
  @ApiModelProperty(value = "")


  public String getSuspendState() {
    return suspendState;
  }

  public void setSuspendState(String suspendState) {
    this.suspendState = suspendState;
  }

  public V1alpha1WorkflowRunStatus terminated(Boolean terminated) {
    this.terminated = terminated;
    return this;
  }

  /**
   * Get terminated
   * @return terminated
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Boolean isTerminated() {
    return terminated;
  }

  public void setTerminated(Boolean terminated) {
    this.terminated = terminated;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1alpha1WorkflowRunStatus v1alpha1WorkflowRunStatus = (V1alpha1WorkflowRunStatus) o;
    return Objects.equals(this.conditions, v1alpha1WorkflowRunStatus.conditions) &&
        Objects.equals(this.contextBackend, v1alpha1WorkflowRunStatus.contextBackend) &&
        Objects.equals(this.endTime, v1alpha1WorkflowRunStatus.endTime) &&
        Objects.equals(this.finished, v1alpha1WorkflowRunStatus.finished) &&
        Objects.equals(this.message, v1alpha1WorkflowRunStatus.message) &&
        Objects.equals(this.mode, v1alpha1WorkflowRunStatus.mode) &&
        Objects.equals(this.startTime, v1alpha1WorkflowRunStatus.startTime) &&
        Objects.equals(this.status, v1alpha1WorkflowRunStatus.status) &&
        Objects.equals(this.steps, v1alpha1WorkflowRunStatus.steps) &&
        Objects.equals(this.suspend, v1alpha1WorkflowRunStatus.suspend) &&
        Objects.equals(this.suspendState, v1alpha1WorkflowRunStatus.suspendState) &&
        Objects.equals(this.terminated, v1alpha1WorkflowRunStatus.terminated);
  }

  @Override
  public int hashCode() {
    return Objects.hash(conditions, contextBackend, endTime, finished, message, mode, startTime, status, steps, suspend, suspendState, terminated);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1alpha1WorkflowRunStatus {\n");
    
    sb.append("    conditions: ").append(toIndentedString(conditions)).append("\n");
    sb.append("    contextBackend: ").append(toIndentedString(contextBackend)).append("\n");
    sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
    sb.append("    finished: ").append(toIndentedString(finished)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    mode: ").append(toIndentedString(mode)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    steps: ").append(toIndentedString(steps)).append("\n");
    sb.append("    suspend: ").append(toIndentedString(suspend)).append("\n");
    sb.append("    suspendState: ").append(toIndentedString(suspendState)).append("\n");
    sb.append("    terminated: ").append(toIndentedString(terminated)).append("\n");
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

