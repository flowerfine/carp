package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1CreateWorkflowRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1CreateWorkflowRequest   {
  @JsonProperty("alias")
  private String alias = null;

  @JsonProperty("default")
  private Boolean _default = null;

  @JsonProperty("description")
  private String description = null;

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

  public V1CreateWorkflowRequest alias(String alias) {
    this.alias = alias;
    return this;
  }

  /**
   * Get alias
   * @return alias
  **/
  @ApiModelProperty(value = "")


  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public V1CreateWorkflowRequest _default(Boolean _default) {
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

  public V1CreateWorkflowRequest description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  **/
  @ApiModelProperty(value = "")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public V1CreateWorkflowRequest envName(String envName) {
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

  public V1CreateWorkflowRequest mode(String mode) {
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

  public V1CreateWorkflowRequest name(String name) {
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

  public V1CreateWorkflowRequest steps(List<V1WorkflowStep> steps) {
    this.steps = steps;
    return this;
  }

  public V1CreateWorkflowRequest addStepsItem(V1WorkflowStep stepsItem) {
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

  public V1CreateWorkflowRequest subMode(String subMode) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1CreateWorkflowRequest v1CreateWorkflowRequest = (V1CreateWorkflowRequest) o;
    return Objects.equals(this.alias, v1CreateWorkflowRequest.alias) &&
        Objects.equals(this._default, v1CreateWorkflowRequest._default) &&
        Objects.equals(this.description, v1CreateWorkflowRequest.description) &&
        Objects.equals(this.envName, v1CreateWorkflowRequest.envName) &&
        Objects.equals(this.mode, v1CreateWorkflowRequest.mode) &&
        Objects.equals(this.name, v1CreateWorkflowRequest.name) &&
        Objects.equals(this.steps, v1CreateWorkflowRequest.steps) &&
        Objects.equals(this.subMode, v1CreateWorkflowRequest.subMode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, _default, description, envName, mode, name, steps, subMode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1CreateWorkflowRequest {\n");
    
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    _default: ").append(toIndentedString(_default)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    envName: ").append(toIndentedString(envName)).append("\n");
    sb.append("    mode: ").append(toIndentedString(mode)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    steps: ").append(toIndentedString(steps)).append("\n");
    sb.append("    subMode: ").append(toIndentedString(subMode)).append("\n");
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

