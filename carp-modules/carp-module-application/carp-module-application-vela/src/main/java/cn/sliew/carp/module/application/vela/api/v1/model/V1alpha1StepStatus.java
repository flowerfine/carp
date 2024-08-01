package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1alpha1StepStatus
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1alpha1StepStatus   {
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

  @JsonProperty("type")
  private String type = null;

  public V1alpha1StepStatus firstExecuteTime(String firstExecuteTime) {
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

  public V1alpha1StepStatus id(String id) {
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

  public V1alpha1StepStatus lastExecuteTime(String lastExecuteTime) {
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

  public V1alpha1StepStatus message(String message) {
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

  public V1alpha1StepStatus name(String name) {
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

  public V1alpha1StepStatus phase(String phase) {
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

  public V1alpha1StepStatus reason(String reason) {
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

  public V1alpha1StepStatus type(String type) {
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
    V1alpha1StepStatus v1alpha1StepStatus = (V1alpha1StepStatus) o;
    return Objects.equals(this.firstExecuteTime, v1alpha1StepStatus.firstExecuteTime) &&
        Objects.equals(this.id, v1alpha1StepStatus.id) &&
        Objects.equals(this.lastExecuteTime, v1alpha1StepStatus.lastExecuteTime) &&
        Objects.equals(this.message, v1alpha1StepStatus.message) &&
        Objects.equals(this.name, v1alpha1StepStatus.name) &&
        Objects.equals(this.phase, v1alpha1StepStatus.phase) &&
        Objects.equals(this.reason, v1alpha1StepStatus.reason) &&
        Objects.equals(this.type, v1alpha1StepStatus.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstExecuteTime, id, lastExecuteTime, message, name, phase, reason, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1alpha1StepStatus {\n");
    
    sb.append("    firstExecuteTime: ").append(toIndentedString(firstExecuteTime)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    lastExecuteTime: ").append(toIndentedString(lastExecuteTime)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    phase: ").append(toIndentedString(phase)).append("\n");
    sb.append("    reason: ").append(toIndentedString(reason)).append("\n");
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

