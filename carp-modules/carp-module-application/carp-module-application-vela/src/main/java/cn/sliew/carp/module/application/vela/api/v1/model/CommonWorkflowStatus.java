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
 * CommonWorkflowStatus
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class CommonWorkflowStatus   {
  @JsonProperty("appRevision")
  private String appRevision = null;

  @JsonProperty("contextBackend")
  private V1ObjectReference contextBackend = null;

  @JsonProperty("endTime")
  private String endTime = null;

  @JsonProperty("finished")
  private Boolean finished = null;

  @JsonProperty("message")
  private String message = null;

  @JsonProperty("mode")
  private String mode = null;

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

  public CommonWorkflowStatus appRevision(String appRevision) {
    this.appRevision = appRevision;
    return this;
  }

  /**
   * Get appRevision
   * @return appRevision
  **/
  @ApiModelProperty(value = "")


  public String getAppRevision() {
    return appRevision;
  }

  public void setAppRevision(String appRevision) {
    this.appRevision = appRevision;
  }

  public CommonWorkflowStatus contextBackend(V1ObjectReference contextBackend) {
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

  public CommonWorkflowStatus endTime(String endTime) {
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

  public CommonWorkflowStatus finished(Boolean finished) {
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

  public CommonWorkflowStatus message(String message) {
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

  public CommonWorkflowStatus mode(String mode) {
    this.mode = mode;
    return this;
  }

  /**
   * Get mode
   * @return mode
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getMode() {
    return mode;
  }

  public void setMode(String mode) {
    this.mode = mode;
  }

  public CommonWorkflowStatus startTime(String startTime) {
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

  public CommonWorkflowStatus status(String status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  **/
  @ApiModelProperty(value = "")


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public CommonWorkflowStatus steps(List<V1alpha1WorkflowStepStatus> steps) {
    this.steps = steps;
    return this;
  }

  public CommonWorkflowStatus addStepsItem(V1alpha1WorkflowStepStatus stepsItem) {
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

  public CommonWorkflowStatus suspend(Boolean suspend) {
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

  public CommonWorkflowStatus suspendState(String suspendState) {
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

  public CommonWorkflowStatus terminated(Boolean terminated) {
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
    CommonWorkflowStatus commonWorkflowStatus = (CommonWorkflowStatus) o;
    return Objects.equals(this.appRevision, commonWorkflowStatus.appRevision) &&
        Objects.equals(this.contextBackend, commonWorkflowStatus.contextBackend) &&
        Objects.equals(this.endTime, commonWorkflowStatus.endTime) &&
        Objects.equals(this.finished, commonWorkflowStatus.finished) &&
        Objects.equals(this.message, commonWorkflowStatus.message) &&
        Objects.equals(this.mode, commonWorkflowStatus.mode) &&
        Objects.equals(this.startTime, commonWorkflowStatus.startTime) &&
        Objects.equals(this.status, commonWorkflowStatus.status) &&
        Objects.equals(this.steps, commonWorkflowStatus.steps) &&
        Objects.equals(this.suspend, commonWorkflowStatus.suspend) &&
        Objects.equals(this.suspendState, commonWorkflowStatus.suspendState) &&
        Objects.equals(this.terminated, commonWorkflowStatus.terminated);
  }

  @Override
  public int hashCode() {
    return Objects.hash(appRevision, contextBackend, endTime, finished, message, mode, startTime, status, steps, suspend, suspendState, terminated);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommonWorkflowStatus {\n");
    
    sb.append("    appRevision: ").append(toIndentedString(appRevision)).append("\n");
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

