package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;

import cn.sliew.carp.module.application.vela.api.v1.model.common.ModelCodeInfo;
import cn.sliew.carp.module.application.vela.api.v1.model.common.ModelImageInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1ApplicationDeployResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ApplicationDeployResponse   {
  @JsonProperty("codeInfo")
  private ModelCodeInfo codeInfo = null;

  @JsonProperty("createTime")
  private OffsetDateTime createTime = null;

  @JsonProperty("deployUser")
  private V1NameAlias deployUser = null;

  @JsonProperty("envName")
  private String envName = null;

  @JsonProperty("imageInfo")
  private ModelImageInfo imageInfo = null;

  @JsonProperty("note")
  private String note = null;

  @JsonProperty("reason")
  private String reason = null;

  @JsonProperty("record")
  private V1WorkflowRecordBase record = null;

  @JsonProperty("status")
  private String status = null;

  @JsonProperty("triggerType")
  private String triggerType = null;

  @JsonProperty("version")
  private String version = null;

  @JsonProperty("workflowName")
  private String workflowName = null;

  public V1ApplicationDeployResponse codeInfo(ModelCodeInfo codeInfo) {
    this.codeInfo = codeInfo;
    return this;
  }

  /**
   * Get codeInfo
   * @return codeInfo
  **/
  @ApiModelProperty(value = "")

  @Valid

  public ModelCodeInfo getCodeInfo() {
    return codeInfo;
  }

  public void setCodeInfo(ModelCodeInfo codeInfo) {
    this.codeInfo = codeInfo;
  }

  public V1ApplicationDeployResponse createTime(OffsetDateTime createTime) {
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

  public V1ApplicationDeployResponse deployUser(V1NameAlias deployUser) {
    this.deployUser = deployUser;
    return this;
  }

  /**
   * Get deployUser
   * @return deployUser
  **/
  @ApiModelProperty(value = "")

  @Valid

  public V1NameAlias getDeployUser() {
    return deployUser;
  }

  public void setDeployUser(V1NameAlias deployUser) {
    this.deployUser = deployUser;
  }

  public V1ApplicationDeployResponse envName(String envName) {
    this.envName = envName;
    return this;
  }

  /**
   * Get envName
   * @return envName
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getEnvName() {
    return envName;
  }

  public void setEnvName(String envName) {
    this.envName = envName;
  }

  public V1ApplicationDeployResponse imageInfo(ModelImageInfo imageInfo) {
    this.imageInfo = imageInfo;
    return this;
  }

  /**
   * Get imageInfo
   * @return imageInfo
  **/
  @ApiModelProperty(value = "")

  @Valid

  public ModelImageInfo getImageInfo() {
    return imageInfo;
  }

  public void setImageInfo(ModelImageInfo imageInfo) {
    this.imageInfo = imageInfo;
  }

  public V1ApplicationDeployResponse note(String note) {
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

  public V1ApplicationDeployResponse reason(String reason) {
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

  public V1ApplicationDeployResponse record(V1WorkflowRecordBase record) {
    this.record = record;
    return this;
  }

  /**
   * Get record
   * @return record
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public V1WorkflowRecordBase getRecord() {
    return record;
  }

  public void setRecord(V1WorkflowRecordBase record) {
    this.record = record;
  }

  public V1ApplicationDeployResponse status(String status) {
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

  public V1ApplicationDeployResponse triggerType(String triggerType) {
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

  public V1ApplicationDeployResponse version(String version) {
    this.version = version;
    return this;
  }

  /**
   * Get version
   * @return version
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public V1ApplicationDeployResponse workflowName(String workflowName) {
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
    V1ApplicationDeployResponse v1ApplicationDeployResponse = (V1ApplicationDeployResponse) o;
    return Objects.equals(this.codeInfo, v1ApplicationDeployResponse.codeInfo) &&
        Objects.equals(this.createTime, v1ApplicationDeployResponse.createTime) &&
        Objects.equals(this.deployUser, v1ApplicationDeployResponse.deployUser) &&
        Objects.equals(this.envName, v1ApplicationDeployResponse.envName) &&
        Objects.equals(this.imageInfo, v1ApplicationDeployResponse.imageInfo) &&
        Objects.equals(this.note, v1ApplicationDeployResponse.note) &&
        Objects.equals(this.reason, v1ApplicationDeployResponse.reason) &&
        Objects.equals(this.record, v1ApplicationDeployResponse.record) &&
        Objects.equals(this.status, v1ApplicationDeployResponse.status) &&
        Objects.equals(this.triggerType, v1ApplicationDeployResponse.triggerType) &&
        Objects.equals(this.version, v1ApplicationDeployResponse.version) &&
        Objects.equals(this.workflowName, v1ApplicationDeployResponse.workflowName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codeInfo, createTime, deployUser, envName, imageInfo, note, reason, record, status, triggerType, version, workflowName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ApplicationDeployResponse {\n");
    
    sb.append("    codeInfo: ").append(toIndentedString(codeInfo)).append("\n");
    sb.append("    createTime: ").append(toIndentedString(createTime)).append("\n");
    sb.append("    deployUser: ").append(toIndentedString(deployUser)).append("\n");
    sb.append("    envName: ").append(toIndentedString(envName)).append("\n");
    sb.append("    imageInfo: ").append(toIndentedString(imageInfo)).append("\n");
    sb.append("    note: ").append(toIndentedString(note)).append("\n");
    sb.append("    reason: ").append(toIndentedString(reason)).append("\n");
    sb.append("    record: ").append(toIndentedString(record)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    triggerType: ").append(toIndentedString(triggerType)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
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

