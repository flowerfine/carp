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
 * V1UpdateWorkflowRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1UpdateWorkflowRequest   {
  @JsonProperty("alias")
  private String alias = null;

  @JsonProperty("default")
  private Boolean _default = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("mode")
  private String mode = null;

  @JsonProperty("steps")
  @Valid
  private List<V1WorkflowStep> steps = null;

  @JsonProperty("subMode")
  private String subMode = null;

  public V1UpdateWorkflowRequest alias(String alias) {
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

  public V1UpdateWorkflowRequest _default(Boolean _default) {
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

  public V1UpdateWorkflowRequest description(String description) {
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

  public V1UpdateWorkflowRequest mode(String mode) {
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

  public V1UpdateWorkflowRequest steps(List<V1WorkflowStep> steps) {
    this.steps = steps;
    return this;
  }

  public V1UpdateWorkflowRequest addStepsItem(V1WorkflowStep stepsItem) {
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

  public V1UpdateWorkflowRequest subMode(String subMode) {
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
    V1UpdateWorkflowRequest v1UpdateWorkflowRequest = (V1UpdateWorkflowRequest) o;
    return Objects.equals(this.alias, v1UpdateWorkflowRequest.alias) &&
        Objects.equals(this._default, v1UpdateWorkflowRequest._default) &&
        Objects.equals(this.description, v1UpdateWorkflowRequest.description) &&
        Objects.equals(this.mode, v1UpdateWorkflowRequest.mode) &&
        Objects.equals(this.steps, v1UpdateWorkflowRequest.steps) &&
        Objects.equals(this.subMode, v1UpdateWorkflowRequest.subMode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, _default, description, mode, steps, subMode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1UpdateWorkflowRequest {\n");
    
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    _default: ").append(toIndentedString(_default)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    mode: ").append(toIndentedString(mode)).append("\n");
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

