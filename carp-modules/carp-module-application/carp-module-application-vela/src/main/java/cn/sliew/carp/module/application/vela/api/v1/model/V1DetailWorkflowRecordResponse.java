package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;

import cn.sliew.carp.module.application.vela.api.v1.model.common.ModelWorkflowStepStatus;
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
 * V1DetailWorkflowRecordResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1DetailWorkflowRecordResponse   {
  @JsonProperty("applicationRevision")
  private String applicationRevision = null;

  @JsonProperty("deployTime")
  private OffsetDateTime deployTime = null;

  @JsonProperty("deployUser")
  private String deployUser = null;

  @JsonProperty("endTime")
  private OffsetDateTime endTime = null;

  @JsonProperty("message")
  private String message = null;

  @JsonProperty("mode")
  private String mode = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("namespace")
  private String namespace = null;

  @JsonProperty("note")
  private String note = null;

  @JsonProperty("startTime")
  private OffsetDateTime startTime = null;

  @JsonProperty("status")
  private String status = null;

  @JsonProperty("steps")
  @Valid
  private List<ModelWorkflowStepStatus> steps = null;

  @JsonProperty("triggerType")
  private String triggerType = null;

  @JsonProperty("workflowAlias")
  private String workflowAlias = null;

  @JsonProperty("workflowName")
  private String workflowName = null;

  public V1DetailWorkflowRecordResponse applicationRevision(String applicationRevision) {
    this.applicationRevision = applicationRevision;
    return this;
  }

  /**
   * Get applicationRevision
   * @return applicationRevision
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getApplicationRevision() {
    return applicationRevision;
  }

  public void setApplicationRevision(String applicationRevision) {
    this.applicationRevision = applicationRevision;
  }

  public V1DetailWorkflowRecordResponse deployTime(OffsetDateTime deployTime) {
    this.deployTime = deployTime;
    return this;
  }

  /**
   * Get deployTime
   * @return deployTime
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public OffsetDateTime getDeployTime() {
    return deployTime;
  }

  public void setDeployTime(OffsetDateTime deployTime) {
    this.deployTime = deployTime;
  }

  public V1DetailWorkflowRecordResponse deployUser(String deployUser) {
    this.deployUser = deployUser;
    return this;
  }

  /**
   * Get deployUser
   * @return deployUser
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getDeployUser() {
    return deployUser;
  }

  public void setDeployUser(String deployUser) {
    this.deployUser = deployUser;
  }

  public V1DetailWorkflowRecordResponse endTime(OffsetDateTime endTime) {
    this.endTime = endTime;
    return this;
  }

  /**
   * Get endTime
   * @return endTime
  **/
  @ApiModelProperty(value = "")

  @Valid

  public OffsetDateTime getEndTime() {
    return endTime;
  }

  public void setEndTime(OffsetDateTime endTime) {
    this.endTime = endTime;
  }

  public V1DetailWorkflowRecordResponse message(String message) {
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

  public V1DetailWorkflowRecordResponse mode(String mode) {
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

  public V1DetailWorkflowRecordResponse name(String name) {
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

  public V1DetailWorkflowRecordResponse namespace(String namespace) {
    this.namespace = namespace;
    return this;
  }

  /**
   * Get namespace
   * @return namespace
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getNamespace() {
    return namespace;
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public V1DetailWorkflowRecordResponse note(String note) {
    this.note = note;
    return this;
  }

  /**
   * Get note
   * @return note
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public V1DetailWorkflowRecordResponse startTime(OffsetDateTime startTime) {
    this.startTime = startTime;
    return this;
  }

  /**
   * Get startTime
   * @return startTime
  **/
  @ApiModelProperty(value = "")

  @Valid

  public OffsetDateTime getStartTime() {
    return startTime;
  }

  public void setStartTime(OffsetDateTime startTime) {
    this.startTime = startTime;
  }

  public V1DetailWorkflowRecordResponse status(String status) {
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

  public V1DetailWorkflowRecordResponse steps(List<ModelWorkflowStepStatus> steps) {
    this.steps = steps;
    return this;
  }

  public V1DetailWorkflowRecordResponse addStepsItem(ModelWorkflowStepStatus stepsItem) {
    if (this.steps == null) {
      this.steps = new ArrayList<ModelWorkflowStepStatus>();
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

  public List<ModelWorkflowStepStatus> getSteps() {
    return steps;
  }

  public void setSteps(List<ModelWorkflowStepStatus> steps) {
    this.steps = steps;
  }

  public V1DetailWorkflowRecordResponse triggerType(String triggerType) {
    this.triggerType = triggerType;
    return this;
  }

  /**
   * Get triggerType
   * @return triggerType
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getTriggerType() {
    return triggerType;
  }

  public void setTriggerType(String triggerType) {
    this.triggerType = triggerType;
  }

  public V1DetailWorkflowRecordResponse workflowAlias(String workflowAlias) {
    this.workflowAlias = workflowAlias;
    return this;
  }

  /**
   * Get workflowAlias
   * @return workflowAlias
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getWorkflowAlias() {
    return workflowAlias;
  }

  public void setWorkflowAlias(String workflowAlias) {
    this.workflowAlias = workflowAlias;
  }

  public V1DetailWorkflowRecordResponse workflowName(String workflowName) {
    this.workflowName = workflowName;
    return this;
  }

  /**
   * Get workflowName
   * @return workflowName
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getWorkflowName() {
    return workflowName;
  }

  public void setWorkflowName(String workflowName) {
    this.workflowName = workflowName;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1DetailWorkflowRecordResponse v1DetailWorkflowRecordResponse = (V1DetailWorkflowRecordResponse) o;
    return Objects.equals(this.applicationRevision, v1DetailWorkflowRecordResponse.applicationRevision) &&
        Objects.equals(this.deployTime, v1DetailWorkflowRecordResponse.deployTime) &&
        Objects.equals(this.deployUser, v1DetailWorkflowRecordResponse.deployUser) &&
        Objects.equals(this.endTime, v1DetailWorkflowRecordResponse.endTime) &&
        Objects.equals(this.message, v1DetailWorkflowRecordResponse.message) &&
        Objects.equals(this.mode, v1DetailWorkflowRecordResponse.mode) &&
        Objects.equals(this.name, v1DetailWorkflowRecordResponse.name) &&
        Objects.equals(this.namespace, v1DetailWorkflowRecordResponse.namespace) &&
        Objects.equals(this.note, v1DetailWorkflowRecordResponse.note) &&
        Objects.equals(this.startTime, v1DetailWorkflowRecordResponse.startTime) &&
        Objects.equals(this.status, v1DetailWorkflowRecordResponse.status) &&
        Objects.equals(this.steps, v1DetailWorkflowRecordResponse.steps) &&
        Objects.equals(this.triggerType, v1DetailWorkflowRecordResponse.triggerType) &&
        Objects.equals(this.workflowAlias, v1DetailWorkflowRecordResponse.workflowAlias) &&
        Objects.equals(this.workflowName, v1DetailWorkflowRecordResponse.workflowName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(applicationRevision, deployTime, deployUser, endTime, message, mode, name, namespace, note, startTime, status, steps, triggerType, workflowAlias, workflowName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1DetailWorkflowRecordResponse {\n");
    
    sb.append("    applicationRevision: ").append(toIndentedString(applicationRevision)).append("\n");
    sb.append("    deployTime: ").append(toIndentedString(deployTime)).append("\n");
    sb.append("    deployUser: ").append(toIndentedString(deployUser)).append("\n");
    sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    mode: ").append(toIndentedString(mode)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    namespace: ").append(toIndentedString(namespace)).append("\n");
    sb.append("    note: ").append(toIndentedString(note)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    steps: ").append(toIndentedString(steps)).append("\n");
    sb.append("    triggerType: ").append(toIndentedString(triggerType)).append("\n");
    sb.append("    workflowAlias: ").append(toIndentedString(workflowAlias)).append("\n");
    sb.append("    workflowName: ").append(toIndentedString(workflowName)).append("\n");
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

