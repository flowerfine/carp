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
 * V1DetailRevisionResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1DetailRevisionResponse   {
  @JsonProperty("appPrimaryKey")
  private String appPrimaryKey = null;

  @JsonProperty("applyAppConfig")
  private String applyAppConfig = null;

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

  public V1DetailRevisionResponse appPrimaryKey(String appPrimaryKey) {
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

  public V1DetailRevisionResponse applyAppConfig(String applyAppConfig) {
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

  public V1DetailRevisionResponse codeInfo(ModelCodeInfo codeInfo) {
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

  public V1DetailRevisionResponse createTime(OffsetDateTime createTime) {
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

  public V1DetailRevisionResponse deployUser(V1NameAlias deployUser) {
    this.deployUser = deployUser;
    return this;
  }

  /**
   * Get deployUser
   * @return deployUser
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public V1NameAlias getDeployUser() {
    return deployUser;
  }

  public void setDeployUser(V1NameAlias deployUser) {
    this.deployUser = deployUser;
  }

  public V1DetailRevisionResponse envName(String envName) {
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

  public V1DetailRevisionResponse imageInfo(ModelImageInfo imageInfo) {
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

  public V1DetailRevisionResponse note(String note) {
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

  public V1DetailRevisionResponse reason(String reason) {
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

  public V1DetailRevisionResponse revisionCRName(String revisionCRName) {
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

  public V1DetailRevisionResponse rollbackVersion(String rollbackVersion) {
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

  public V1DetailRevisionResponse status(String status) {
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

  public V1DetailRevisionResponse triggerType(String triggerType) {
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

  public V1DetailRevisionResponse updateTime(OffsetDateTime updateTime) {
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

  public V1DetailRevisionResponse version(String version) {
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

  public V1DetailRevisionResponse workflowName(String workflowName) {
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
    V1DetailRevisionResponse v1DetailRevisionResponse = (V1DetailRevisionResponse) o;
    return Objects.equals(this.appPrimaryKey, v1DetailRevisionResponse.appPrimaryKey) &&
        Objects.equals(this.applyAppConfig, v1DetailRevisionResponse.applyAppConfig) &&
        Objects.equals(this.codeInfo, v1DetailRevisionResponse.codeInfo) &&
        Objects.equals(this.createTime, v1DetailRevisionResponse.createTime) &&
        Objects.equals(this.deployUser, v1DetailRevisionResponse.deployUser) &&
        Objects.equals(this.envName, v1DetailRevisionResponse.envName) &&
        Objects.equals(this.imageInfo, v1DetailRevisionResponse.imageInfo) &&
        Objects.equals(this.note, v1DetailRevisionResponse.note) &&
        Objects.equals(this.reason, v1DetailRevisionResponse.reason) &&
        Objects.equals(this.revisionCRName, v1DetailRevisionResponse.revisionCRName) &&
        Objects.equals(this.rollbackVersion, v1DetailRevisionResponse.rollbackVersion) &&
        Objects.equals(this.status, v1DetailRevisionResponse.status) &&
        Objects.equals(this.triggerType, v1DetailRevisionResponse.triggerType) &&
        Objects.equals(this.updateTime, v1DetailRevisionResponse.updateTime) &&
        Objects.equals(this.version, v1DetailRevisionResponse.version) &&
        Objects.equals(this.workflowName, v1DetailRevisionResponse.workflowName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(appPrimaryKey, applyAppConfig, codeInfo, createTime, deployUser, envName, imageInfo, note, reason, revisionCRName, rollbackVersion, status, triggerType, updateTime, version, workflowName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1DetailRevisionResponse {\n");
    
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

