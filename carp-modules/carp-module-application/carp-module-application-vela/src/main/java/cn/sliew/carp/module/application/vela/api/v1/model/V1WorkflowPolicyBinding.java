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
 * V1WorkflowPolicyBinding
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1WorkflowPolicyBinding   {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("steps")
  @Valid
  private List<String> steps = new ArrayList<String>();

  public V1WorkflowPolicyBinding name(String name) {
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

  public V1WorkflowPolicyBinding steps(List<String> steps) {
    this.steps = steps;
    return this;
  }

  public V1WorkflowPolicyBinding addStepsItem(String stepsItem) {
    this.steps.add(stepsItem);
    return this;
  }

  /**
   * Get steps
   * @return steps
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public List<String> getSteps() {
    return steps;
  }

  public void setSteps(List<String> steps) {
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
    V1WorkflowPolicyBinding v1WorkflowPolicyBinding = (V1WorkflowPolicyBinding) o;
    return Objects.equals(this.name, v1WorkflowPolicyBinding.name) &&
        Objects.equals(this.steps, v1WorkflowPolicyBinding.steps);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, steps);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1WorkflowPolicyBinding {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
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

