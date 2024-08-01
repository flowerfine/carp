package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1alpha1WorkflowExecuteMode
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1alpha1WorkflowExecuteMode   {
  @JsonProperty("steps")
  private String steps = null;

  @JsonProperty("subSteps")
  private String subSteps = null;

  public V1alpha1WorkflowExecuteMode steps(String steps) {
    this.steps = steps;
    return this;
  }

  /**
   * Get steps
   * @return steps
  **/
  @ApiModelProperty(value = "")


  public String getSteps() {
    return steps;
  }

  public void setSteps(String steps) {
    this.steps = steps;
  }

  public V1alpha1WorkflowExecuteMode subSteps(String subSteps) {
    this.subSteps = subSteps;
    return this;
  }

  /**
   * Get subSteps
   * @return subSteps
  **/
  @ApiModelProperty(value = "")


  public String getSubSteps() {
    return subSteps;
  }

  public void setSubSteps(String subSteps) {
    this.subSteps = subSteps;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1alpha1WorkflowExecuteMode v1alpha1WorkflowExecuteMode = (V1alpha1WorkflowExecuteMode) o;
    return Objects.equals(this.steps, v1alpha1WorkflowExecuteMode.steps) &&
        Objects.equals(this.subSteps, v1alpha1WorkflowExecuteMode.subSteps);
  }

  @Override
  public int hashCode() {
    return Objects.hash(steps, subSteps);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1alpha1WorkflowExecuteMode {\n");
    
    sb.append("    steps: ").append(toIndentedString(steps)).append("\n");
    sb.append("    subSteps: ").append(toIndentedString(subSteps)).append("\n");
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

