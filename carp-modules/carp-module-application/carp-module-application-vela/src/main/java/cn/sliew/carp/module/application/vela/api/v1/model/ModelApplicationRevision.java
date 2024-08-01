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
 * ModelApplicationRevision
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class ModelApplicationRevision   {
  @JsonProperty("appPrimaryKey")
  private String appPrimaryKey = null;

  @JsonProperty("applyAppConfig")
  private String applyAppConfig = null;

  @JsonProperty("codeInfo")
  private ModelCodeInfo codeInfo = null;

  @JsonProperty("createTime")
  private OffsetDateTime createTime = null;

  @JsonProperty("deployUser")
  private String deployUser = null;

  @JsonProperty("envName")
  private String envName = null;

  @JsonProperty("imageInfo")
  private ModelImageInfo imageInfo = null;

  @JsonProperty("note")
  private String note = null;

  @JsonProperty("reason")
  private String reason = null;

  @JsonProperty("revisionCRName")
  private String revisionCRName = null;

  @JsonProperty("rollbackVersion")
  private String rollbackVersion = null;

  @JsonProperty("status")
  private String status = null;

  @JsonProperty("triggerType")
  private String triggerType = null;

  @JsonProperty("updateTime")
  private OffsetDateTime updateTime = null;

  @JsonProperty("version")
  private String version = null;

  @JsonProperty("workflowName")
  private String workflowName = null;

  public ModelApplicationRevision appPrimaryKey(String appPrimaryKey) {
    this.appPrimaryKey = appPrimaryKey;
    return this;
  }

  /**
   * Get appPrimaryKey
   * @return appPrimaryKey
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getAppPrimaryKey() {
    return appPrimaryKey;
  }

  public void setAppPrimaryKey(String appPrimaryKey) {
    this.appPrimaryKey = appPrimaryKey;
  }

  public ModelApplicationRevision applyAppConfig(String applyAppConfig) {
    this.applyAppConfig = applyAppConfig;
    return this;
  }

  /**
   * Get applyAppConfig
   * @return applyAppConfig
  **/
  @ApiModelProperty(value = "")


  public String getApplyAppConfig() {
    return applyAppConfig;
  }

  public void setApplyAppConfig(String applyAppConfig) {
    this.applyAppConfig = applyAppConfig;
  }

  public ModelApplicationRevision codeInfo(ModelCodeInfo codeInfo) {
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

  public ModelApplicationRevision createTime(OffsetDateTime createTime) {
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

  public ModelApplicationRevision deployUser(String deployUser) {
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

  public ModelApplicationRevision envName(String envName) {
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

  public ModelApplicationRevision imageInfo(ModelImageInfo imageInfo) {
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

  public ModelApplicationRevision note(String note) {
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

  public ModelApplicationRevision reason(String reason) {
    this.reason = reason;
    return this;
  }

  /**
   * Get reason
   * @return reason
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public ModelApplicationRevision revisionCRName(String revisionCRName) {
    this.revisionCRName = revisionCRName;
    return this;
  }

  /**
   * Get revisionCRName
   * @return revisionCRName
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getRevisionCRName() {
    return revisionCRName;
  }

  public void setRevisionCRName(String revisionCRName) {
    this.revisionCRName = revisionCRName;
  }

  public ModelApplicationRevision rollbackVersion(String rollbackVersion) {
    this.rollbackVersion = rollbackVersion;
    return this;
  }

  /**
   * Get rollbackVersion
   * @return rollbackVersion
  **/
  @ApiModelProperty(value = "")


  public String getRollbackVersion() {
    return rollbackVersion;
  }

  public void setRollbackVersion(String rollbackVersion) {
    this.rollbackVersion = rollbackVersion;
  }

  public ModelApplicationRevision status(String status) {
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

  public ModelApplicationRevision triggerType(String triggerType) {
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

  public ModelApplicationRevision updateTime(OffsetDateTime updateTime) {
    this.updateTime = updateTime;
    return this;
  }

  /**
   * Get updateTime
   * @return updateTime
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public OffsetDateTime getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(OffsetDateTime updateTime) {
    this.updateTime = updateTime;
  }

  public ModelApplicationRevision version(String version) {
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

  public ModelApplicationRevision workflowName(String workflowName) {
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
    ModelApplicationRevision modelApplicationRevision = (ModelApplicationRevision) o;
    return Objects.equals(this.appPrimaryKey, modelApplicationRevision.appPrimaryKey) &&
        Objects.equals(this.applyAppConfig, modelApplicationRevision.applyAppConfig) &&
        Objects.equals(this.codeInfo, modelApplicationRevision.codeInfo) &&
        Objects.equals(this.createTime, modelApplicationRevision.createTime) &&
        Objects.equals(this.deployUser, modelApplicationRevision.deployUser) &&
        Objects.equals(this.envName, modelApplicationRevision.envName) &&
        Objects.equals(this.imageInfo, modelApplicationRevision.imageInfo) &&
        Objects.equals(this.note, modelApplicationRevision.note) &&
        Objects.equals(this.reason, modelApplicationRevision.reason) &&
        Objects.equals(this.revisionCRName, modelApplicationRevision.revisionCRName) &&
        Objects.equals(this.rollbackVersion, modelApplicationRevision.rollbackVersion) &&
        Objects.equals(this.status, modelApplicationRevision.status) &&
        Objects.equals(this.triggerType, modelApplicationRevision.triggerType) &&
        Objects.equals(this.updateTime, modelApplicationRevision.updateTime) &&
        Objects.equals(this.version, modelApplicationRevision.version) &&
        Objects.equals(this.workflowName, modelApplicationRevision.workflowName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(appPrimaryKey, applyAppConfig, codeInfo, createTime, deployUser, envName, imageInfo, note, reason, revisionCRName, rollbackVersion, status, triggerType, updateTime, version, workflowName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ModelApplicationRevision {\n");
    
    sb.append("    appPrimaryKey: ").append(toIndentedString(appPrimaryKey)).append("\n");
    sb.append("    applyAppConfig: ").append(toIndentedString(applyAppConfig)).append("\n");
    sb.append("    codeInfo: ").append(toIndentedString(codeInfo)).append("\n");
    sb.append("    createTime: ").append(toIndentedString(createTime)).append("\n");
    sb.append("    deployUser: ").append(toIndentedString(deployUser)).append("\n");
    sb.append("    envName: ").append(toIndentedString(envName)).append("\n");
    sb.append("    imageInfo: ").append(toIndentedString(imageInfo)).append("\n");
    sb.append("    note: ").append(toIndentedString(note)).append("\n");
    sb.append("    reason: ").append(toIndentedString(reason)).append("\n");
    sb.append("    revisionCRName: ").append(toIndentedString(revisionCRName)).append("\n");
    sb.append("    rollbackVersion: ").append(toIndentedString(rollbackVersion)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    triggerType: ").append(toIndentedString(triggerType)).append("\n");
    sb.append("    updateTime: ").append(toIndentedString(updateTime)).append("\n");
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

