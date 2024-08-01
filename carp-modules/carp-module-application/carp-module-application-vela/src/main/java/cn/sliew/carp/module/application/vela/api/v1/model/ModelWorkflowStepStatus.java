package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ModelWorkflowStepStatus
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class ModelWorkflowStepStatus   {
  @JsonProperty("alias")
  private String alias = null;

  @JsonProperty("firstExecuteTime")
  private OffsetDateTime firstExecuteTime = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("lastExecuteTime")
  private OffsetDateTime lastExecuteTime = null;

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
  private List<ModelStepStatus> subSteps = null;

  @JsonProperty("type")
  private String type = null;

  public ModelWorkflowStepStatus alias(String alias) {
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

  public ModelWorkflowStepStatus firstExecuteTime(OffsetDateTime firstExecuteTime) {
    this.firstExecuteTime = firstExecuteTime;
    return this;
  }

  /**
   * Get firstExecuteTime
   * @return firstExecuteTime
  **/
  @ApiModelProperty(value = "")

  @Valid

  public OffsetDateTime getFirstExecuteTime() {
    return firstExecuteTime;
  }

  public void setFirstExecuteTime(OffsetDateTime firstExecuteTime) {
    this.firstExecuteTime = firstExecuteTime;
  }

  public ModelWorkflowStepStatus id(String id) {
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

  public ModelWorkflowStepStatus lastExecuteTime(OffsetDateTime lastExecuteTime) {
    this.lastExecuteTime = lastExecuteTime;
    return this;
  }

  /**
   * Get lastExecuteTime
   * @return lastExecuteTime
  **/
  @ApiModelProperty(value = "")

  @Valid

  public OffsetDateTime getLastExecuteTime() {
    return lastExecuteTime;
  }

  public void setLastExecuteTime(OffsetDateTime lastExecuteTime) {
    this.lastExecuteTime = lastExecuteTime;
  }

  public ModelWorkflowStepStatus message(String message) {
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

  public ModelWorkflowStepStatus name(String name) {
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

  public ModelWorkflowStepStatus phase(String phase) {
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

  public ModelWorkflowStepStatus reason(String reason) {
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

  public ModelWorkflowStepStatus subSteps(List<ModelStepStatus> subSteps) {
    this.subSteps = subSteps;
    return this;
  }

  public ModelWorkflowStepStatus addSubStepsItem(ModelStepStatus subStepsItem) {
    if (this.subSteps == null) {
      this.subSteps = new ArrayList<ModelStepStatus>();
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

  public List<ModelStepStatus> getSubSteps() {
    return subSteps;
  }

  public void setSubSteps(List<ModelStepStatus> subSteps) {
    this.subSteps = subSteps;
  }

  public ModelWorkflowStepStatus type(String type) {
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
    ModelWorkflowStepStatus modelWorkflowStepStatus = (ModelWorkflowStepStatus) o;
    return Objects.equals(this.alias, modelWorkflowStepStatus.alias) &&
        Objects.equals(this.firstExecuteTime, modelWorkflowStepStatus.firstExecuteTime) &&
        Objects.equals(this.id, modelWorkflowStepStatus.id) &&
        Objects.equals(this.lastExecuteTime, modelWorkflowStepStatus.lastExecuteTime) &&
        Objects.equals(this.message, modelWorkflowStepStatus.message) &&
        Objects.equals(this.name, modelWorkflowStepStatus.name) &&
        Objects.equals(this.phase, modelWorkflowStepStatus.phase) &&
        Objects.equals(this.reason, modelWorkflowStepStatus.reason) &&
        Objects.equals(this.subSteps, modelWorkflowStepStatus.subSteps) &&
        Objects.equals(this.type, modelWorkflowStepStatus.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, firstExecuteTime, id, lastExecuteTime, message, name, phase, reason, subSteps, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ModelWorkflowStepStatus {\n");
    
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
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

