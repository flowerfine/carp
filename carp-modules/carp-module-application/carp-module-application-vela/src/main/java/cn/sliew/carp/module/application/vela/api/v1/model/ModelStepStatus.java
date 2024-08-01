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
 * ModelStepStatus
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class ModelStepStatus   {
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

  @JsonProperty("type")
  private String type = null;

  public ModelStepStatus alias(String alias) {
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

  public ModelStepStatus firstExecuteTime(OffsetDateTime firstExecuteTime) {
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

  public ModelStepStatus id(String id) {
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

  public ModelStepStatus lastExecuteTime(OffsetDateTime lastExecuteTime) {
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

  public ModelStepStatus message(String message) {
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

  public ModelStepStatus name(String name) {
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

  public ModelStepStatus phase(String phase) {
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

  public ModelStepStatus reason(String reason) {
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

  public ModelStepStatus type(String type) {
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
    ModelStepStatus modelStepStatus = (ModelStepStatus) o;
    return Objects.equals(this.alias, modelStepStatus.alias) &&
        Objects.equals(this.firstExecuteTime, modelStepStatus.firstExecuteTime) &&
        Objects.equals(this.id, modelStepStatus.id) &&
        Objects.equals(this.lastExecuteTime, modelStepStatus.lastExecuteTime) &&
        Objects.equals(this.message, modelStepStatus.message) &&
        Objects.equals(this.name, modelStepStatus.name) &&
        Objects.equals(this.phase, modelStepStatus.phase) &&
        Objects.equals(this.reason, modelStepStatus.reason) &&
        Objects.equals(this.type, modelStepStatus.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, firstExecuteTime, id, lastExecuteTime, message, name, phase, reason, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ModelStepStatus {\n");
    
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
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

