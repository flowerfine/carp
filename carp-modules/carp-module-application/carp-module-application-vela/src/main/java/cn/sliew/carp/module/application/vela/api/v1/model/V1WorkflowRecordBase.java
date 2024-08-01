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
 * V1WorkflowRecordBase
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1WorkflowRecordBase   {
  @JsonProperty("applicationRevision")
  private String applicationRevision = null;

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

  @JsonProperty("startTime")
  private OffsetDateTime startTime = null;

  @JsonProperty("status")
  private String status = null;

  @JsonProperty("workflowAlias")
  private String workflowAlias = null;

  @JsonProperty("workflowName")
  private String workflowName = null;

  public V1WorkflowRecordBase applicationRevision(String applicationRevision) {
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

  public V1WorkflowRecordBase endTime(OffsetDateTime endTime) {
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

  public V1WorkflowRecordBase message(String message) {
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

  public V1WorkflowRecordBase mode(String mode) {
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

  public V1WorkflowRecordBase name(String name) {
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

  public V1WorkflowRecordBase namespace(String namespace) {
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

  public V1WorkflowRecordBase startTime(OffsetDateTime startTime) {
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

  public V1WorkflowRecordBase status(String status) {
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

  public V1WorkflowRecordBase workflowAlias(String workflowAlias) {
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

  public V1WorkflowRecordBase workflowName(String workflowName) {
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
    V1WorkflowRecordBase v1WorkflowRecordBase = (V1WorkflowRecordBase) o;
    return Objects.equals(this.applicationRevision, v1WorkflowRecordBase.applicationRevision) &&
        Objects.equals(this.endTime, v1WorkflowRecordBase.endTime) &&
        Objects.equals(this.message, v1WorkflowRecordBase.message) &&
        Objects.equals(this.mode, v1WorkflowRecordBase.mode) &&
        Objects.equals(this.name, v1WorkflowRecordBase.name) &&
        Objects.equals(this.namespace, v1WorkflowRecordBase.namespace) &&
        Objects.equals(this.startTime, v1WorkflowRecordBase.startTime) &&
        Objects.equals(this.status, v1WorkflowRecordBase.status) &&
        Objects.equals(this.workflowAlias, v1WorkflowRecordBase.workflowAlias) &&
        Objects.equals(this.workflowName, v1WorkflowRecordBase.workflowName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(applicationRevision, endTime, message, mode, name, namespace, startTime, status, workflowAlias, workflowName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1WorkflowRecordBase {\n");
    
    sb.append("    applicationRevision: ").append(toIndentedString(applicationRevision)).append("\n");
    sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    mode: ").append(toIndentedString(mode)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    namespace: ").append(toIndentedString(namespace)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

