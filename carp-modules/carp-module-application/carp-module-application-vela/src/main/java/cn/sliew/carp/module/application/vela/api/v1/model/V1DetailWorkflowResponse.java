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
 * V1DetailWorkflowResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1DetailWorkflowResponse   {
  @JsonProperty("alias")
  private String alias = null;

  @JsonProperty("createTime")
  private OffsetDateTime createTime = null;

  @JsonProperty("default")
  private Boolean _default = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("enable")
  private Boolean enable = null;

  @JsonProperty("envName")
  private String envName = null;

  @JsonProperty("mode")
  private String mode = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("steps")
  @Valid
  private List<V1WorkflowStep> steps = null;

  @JsonProperty("subMode")
  private String subMode = null;

  @JsonProperty("updateTime")
  private OffsetDateTime updateTime = null;

  public V1DetailWorkflowResponse alias(String alias) {
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

  public V1DetailWorkflowResponse createTime(OffsetDateTime createTime) {
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

  public V1DetailWorkflowResponse _default(Boolean _default) {
    this._default = _default;
    return this;
  }

  /**
   * Get _default
   * @return _default
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Boolean isDefault() {
    return _default;
  }

  public void setDefault(Boolean _default) {
    this._default = _default;
  }

  public V1DetailWorkflowResponse description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public V1DetailWorkflowResponse enable(Boolean enable) {
    this.enable = enable;
    return this;
  }

  /**
   * Get enable
   * @return enable
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Boolean isEnable() {
    return enable;
  }

  public void setEnable(Boolean enable) {
    this.enable = enable;
  }

  public V1DetailWorkflowResponse envName(String envName) {
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

  public V1DetailWorkflowResponse mode(String mode) {
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

  public V1DetailWorkflowResponse name(String name) {
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

  public V1DetailWorkflowResponse steps(List<V1WorkflowStep> steps) {
    this.steps = steps;
    return this;
  }

  public V1DetailWorkflowResponse addStepsItem(V1WorkflowStep stepsItem) {
    if (this.steps == null) {
      this.steps = new ArrayList<V1WorkflowStep>();
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

  public List<V1WorkflowStep> getSteps() {
    return steps;
  }

  public void setSteps(List<V1WorkflowStep> steps) {
    this.steps = steps;
  }

  public V1DetailWorkflowResponse subMode(String subMode) {
    this.subMode = subMode;
    return this;
  }

  /**
   * Get subMode
   * @return subMode
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getSubMode() {
    return subMode;
  }

  public void setSubMode(String subMode) {
    this.subMode = subMode;
  }

  public V1DetailWorkflowResponse updateTime(OffsetDateTime updateTime) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1DetailWorkflowResponse v1DetailWorkflowResponse = (V1DetailWorkflowResponse) o;
    return Objects.equals(this.alias, v1DetailWorkflowResponse.alias) &&
        Objects.equals(this.createTime, v1DetailWorkflowResponse.createTime) &&
        Objects.equals(this._default, v1DetailWorkflowResponse._default) &&
        Objects.equals(this.description, v1DetailWorkflowResponse.description) &&
        Objects.equals(this.enable, v1DetailWorkflowResponse.enable) &&
        Objects.equals(this.envName, v1DetailWorkflowResponse.envName) &&
        Objects.equals(this.mode, v1DetailWorkflowResponse.mode) &&
        Objects.equals(this.name, v1DetailWorkflowResponse.name) &&
        Objects.equals(this.steps, v1DetailWorkflowResponse.steps) &&
        Objects.equals(this.subMode, v1DetailWorkflowResponse.subMode) &&
        Objects.equals(this.updateTime, v1DetailWorkflowResponse.updateTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, createTime, _default, description, enable, envName, mode, name, steps, subMode, updateTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1DetailWorkflowResponse {\n");
    
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    createTime: ").append(toIndentedString(createTime)).append("\n");
    sb.append("    _default: ").append(toIndentedString(_default)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    enable: ").append(toIndentedString(enable)).append("\n");
    sb.append("    envName: ").append(toIndentedString(envName)).append("\n");
    sb.append("    mode: ").append(toIndentedString(mode)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    steps: ").append(toIndentedString(steps)).append("\n");
    sb.append("    subMode: ").append(toIndentedString(subMode)).append("\n");
    sb.append("    updateTime: ").append(toIndentedString(updateTime)).append("\n");
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

