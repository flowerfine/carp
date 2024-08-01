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
 * V1alpha1WorkflowStepStatus
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1alpha1WorkflowStepStatus   {
  @JsonProperty("firstExecuteTime")
  private String firstExecuteTime = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("lastExecuteTime")
  private String lastExecuteTime = null;

  @JsonProperty("message")
  private String message = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("phase")
  private String phase = null;

  @JsonProperty("reason")
  private String reason = null;

  @JsonProperty("subSteps")
  @Valid
  private List<V1alpha1StepStatus> subSteps = null;

  @JsonProperty("type")
  private String type = null;

  public V1alpha1WorkflowStepStatus firstExecuteTime(String firstExecuteTime) {
    this.firstExecuteTime = firstExecuteTime;
    return this;
  }

  /**
   * Get firstExecuteTime
   * @return firstExecuteTime
  **/
  @ApiModelProperty(value = "")


  public String getFirstExecuteTime() {
    return firstExecuteTime;
  }

  public void setFirstExecuteTime(String firstExecuteTime) {
    this.firstExecuteTime = firstExecuteTime;
  }

  public V1alpha1WorkflowStepStatus id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public V1alpha1WorkflowStepStatus lastExecuteTime(String lastExecuteTime) {
    this.lastExecuteTime = lastExecuteTime;
    return this;
  }

  /**
   * Get lastExecuteTime
   * @return lastExecuteTime
  **/
  @ApiModelProperty(value = "")


  public String getLastExecuteTime() {
    return lastExecuteTime;
  }

  public void setLastExecuteTime(String lastExecuteTime) {
    this.lastExecuteTime = lastExecuteTime;
  }

  public V1alpha1WorkflowStepStatus message(String message) {
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

  public V1alpha1WorkflowStepStatus name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(value = "")


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public V1alpha1WorkflowStepStatus phase(String phase) {
    this.phase = phase;
    return this;
  }

  /**
   * Get phase
   * @return phase
  **/
  @ApiModelProperty(value = "")


  public String getPhase() {
    return phase;
  }

  public void setPhase(String phase) {
    this.phase = phase;
  }

  public V1alpha1WorkflowStepStatus reason(String reason) {
    this.reason = reason;
    return this;
  }

  /**
   * Get reason
   * @return reason
  **/
  @ApiModelProperty(value = "")


  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public V1alpha1WorkflowStepStatus subSteps(List<V1alpha1StepStatus> subSteps) {
    this.subSteps = subSteps;
    return this;
  }

  public V1alpha1WorkflowStepStatus addSubStepsItem(V1alpha1StepStatus subStepsItem) {
    if (this.subSteps == null) {
      this.subSteps = new ArrayList<V1alpha1StepStatus>();
    }
    this.subSteps.add(subStepsItem);
    return this;
  }

  /**
   * Get subSteps
   * @return subSteps
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<V1alpha1StepStatus> getSubSteps() {
    return subSteps;
  }

  public void setSubSteps(List<V1alpha1StepStatus> subSteps) {
    this.subSteps = subSteps;
  }

  public V1alpha1WorkflowStepStatus type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
  **/
  @ApiModelProperty(value = "")


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1alpha1WorkflowStepStatus v1alpha1WorkflowStepStatus = (V1alpha1WorkflowStepStatus) o;
    return Objects.equals(this.firstExecuteTime, v1alpha1WorkflowStepStatus.firstExecuteTime) &&
        Objects.equals(this.id, v1alpha1WorkflowStepStatus.id) &&
        Objects.equals(this.lastExecuteTime, v1alpha1WorkflowStepStatus.lastExecuteTime) &&
        Objects.equals(this.message, v1alpha1WorkflowStepStatus.message) &&
        Objects.equals(this.name, v1alpha1WorkflowStepStatus.name) &&
        Objects.equals(this.phase, v1alpha1WorkflowStepStatus.phase) &&
        Objects.equals(this.reason, v1alpha1WorkflowStepStatus.reason) &&
        Objects.equals(this.subSteps, v1alpha1WorkflowStepStatus.subSteps) &&
        Objects.equals(this.type, v1alpha1WorkflowStepStatus.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstExecuteTime, id, lastExecuteTime, message, name, phase, reason, subSteps, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1alpha1WorkflowStepStatus {\n");
    
    sb.append("    firstExecuteTime: ").append(toIndentedString(firstExecuteTime)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    lastExecuteTime: ").append(toIndentedString(lastExecuteTime)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    phase: ").append(toIndentedString(phase)).append("\n");
    sb.append("    reason: ").append(toIndentedString(reason)).append("\n");
    sb.append("    subSteps: ").append(toIndentedString(subSteps)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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

