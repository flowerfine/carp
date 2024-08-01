package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1ApplicationDeployRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ApplicationDeployRequest   {
  @JsonProperty("codeInfo")
  private ModelCodeInfo codeInfo = null;

  @JsonProperty("force")
  private Boolean force = null;

  @JsonProperty("imageInfo")
  private ModelImageInfo imageInfo = null;

  @JsonProperty("note")
  private String note = null;

  @JsonProperty("triggerType")
  private String triggerType = null;

  @JsonProperty("workflowName")
  private String workflowName = null;

  public V1ApplicationDeployRequest codeInfo(ModelCodeInfo codeInfo) {
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

  public V1ApplicationDeployRequest force(Boolean force) {
    this.force = force;
    return this;
  }

  /**
   * Get force
   * @return force
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Boolean isForce() {
    return force;
  }

  public void setForce(Boolean force) {
    this.force = force;
  }

  public V1ApplicationDeployRequest imageInfo(ModelImageInfo imageInfo) {
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

  public V1ApplicationDeployRequest note(String note) {
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

  public V1ApplicationDeployRequest triggerType(String triggerType) {
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

  public V1ApplicationDeployRequest workflowName(String workflowName) {
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
    V1ApplicationDeployRequest v1ApplicationDeployRequest = (V1ApplicationDeployRequest) o;
    return Objects.equals(this.codeInfo, v1ApplicationDeployRequest.codeInfo) &&
        Objects.equals(this.force, v1ApplicationDeployRequest.force) &&
        Objects.equals(this.imageInfo, v1ApplicationDeployRequest.imageInfo) &&
        Objects.equals(this.note, v1ApplicationDeployRequest.note) &&
        Objects.equals(this.triggerType, v1ApplicationDeployRequest.triggerType) &&
        Objects.equals(this.workflowName, v1ApplicationDeployRequest.workflowName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codeInfo, force, imageInfo, note, triggerType, workflowName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ApplicationDeployRequest {\n");
    
    sb.append("    codeInfo: ").append(toIndentedString(codeInfo)).append("\n");
    sb.append("    force: ").append(toIndentedString(force)).append("\n");
    sb.append("    imageInfo: ").append(toIndentedString(imageInfo)).append("\n");
    sb.append("    note: ").append(toIndentedString(note)).append("\n");
    sb.append("    triggerType: ").append(toIndentedString(triggerType)).append("\n");
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

