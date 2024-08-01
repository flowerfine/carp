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
 * V1beta1Workflow
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1beta1Workflow   {
  @JsonProperty("mode")
  private V1alpha1WorkflowExecuteMode mode = null;

  @JsonProperty("ref")
  private String ref = null;

  @JsonProperty("steps")
  @Valid
  private List<V1alpha1WorkflowStep> steps = null;

  public V1beta1Workflow mode(V1alpha1WorkflowExecuteMode mode) {
    this.mode = mode;
    return this;
  }

  /**
   * Get mode
   * @return mode
  **/
  @ApiModelProperty(value = "")

  @Valid

  public V1alpha1WorkflowExecuteMode getMode() {
    return mode;
  }

  public void setMode(V1alpha1WorkflowExecuteMode mode) {
    this.mode = mode;
  }

  public V1beta1Workflow ref(String ref) {
    this.ref = ref;
    return this;
  }

  /**
   * Get ref
   * @return ref
  **/
  @ApiModelProperty(value = "")


  public String getRef() {
    return ref;
  }

  public void setRef(String ref) {
    this.ref = ref;
  }

  public V1beta1Workflow steps(List<V1alpha1WorkflowStep> steps) {
    this.steps = steps;
    return this;
  }

  public V1beta1Workflow addStepsItem(V1alpha1WorkflowStep stepsItem) {
    if (this.steps == null) {
      this.steps = new ArrayList<V1alpha1WorkflowStep>();
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

  public List<V1alpha1WorkflowStep> getSteps() {
    return steps;
  }

  public void setSteps(List<V1alpha1WorkflowStep> steps) {
    this.steps = steps;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1beta1Workflow v1beta1Workflow = (V1beta1Workflow) o;
    return Objects.equals(this.mode, v1beta1Workflow.mode) &&
        Objects.equals(this.ref, v1beta1Workflow.ref) &&
        Objects.equals(this.steps, v1beta1Workflow.steps);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mode, ref, steps);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1beta1Workflow {\n");
    
    sb.append("    mode: ").append(toIndentedString(mode)).append("\n");
    sb.append("    ref: ").append(toIndentedString(ref)).append("\n");
    sb.append("    steps: ").append(toIndentedString(steps)).append("\n");
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

