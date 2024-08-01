package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1alpha1WorkflowRunSpec
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1alpha1WorkflowRunSpec   {
  @JsonProperty("context")
  private String context = null;

  @JsonProperty("mode")
  private V1alpha1WorkflowExecuteMode mode = null;

  @JsonProperty("workflowRef")
  private String workflowRef = null;

  @JsonProperty("workflowSpec")
  private V1alpha1WorkflowSpec workflowSpec = null;

  public V1alpha1WorkflowRunSpec context(String context) {
    this.context = context;
    return this;
  }

  /**
   * Get context
   * @return context
  **/
  @ApiModelProperty(value = "")


  public String getContext() {
    return context;
  }

  public void setContext(String context) {
    this.context = context;
  }

  public V1alpha1WorkflowRunSpec mode(V1alpha1WorkflowExecuteMode mode) {
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

  public V1alpha1WorkflowRunSpec workflowRef(String workflowRef) {
    this.workflowRef = workflowRef;
    return this;
  }

  /**
   * Get workflowRef
   * @return workflowRef
  **/
  @ApiModelProperty(value = "")


  public String getWorkflowRef() {
    return workflowRef;
  }

  public void setWorkflowRef(String workflowRef) {
    this.workflowRef = workflowRef;
  }

  public V1alpha1WorkflowRunSpec workflowSpec(V1alpha1WorkflowSpec workflowSpec) {
    this.workflowSpec = workflowSpec;
    return this;
  }

  /**
   * Get workflowSpec
   * @return workflowSpec
  **/
  @ApiModelProperty(value = "")

  @Valid

  public V1alpha1WorkflowSpec getWorkflowSpec() {
    return workflowSpec;
  }

  public void setWorkflowSpec(V1alpha1WorkflowSpec workflowSpec) {
    this.workflowSpec = workflowSpec;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1alpha1WorkflowRunSpec v1alpha1WorkflowRunSpec = (V1alpha1WorkflowRunSpec) o;
    return Objects.equals(this.context, v1alpha1WorkflowRunSpec.context) &&
        Objects.equals(this.mode, v1alpha1WorkflowRunSpec.mode) &&
        Objects.equals(this.workflowRef, v1alpha1WorkflowRunSpec.workflowRef) &&
        Objects.equals(this.workflowSpec, v1alpha1WorkflowRunSpec.workflowSpec);
  }

  @Override
  public int hashCode() {
    return Objects.hash(context, mode, workflowRef, workflowSpec);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1alpha1WorkflowRunSpec {\n");
    
    sb.append("    context: ").append(toIndentedString(context)).append("\n");
    sb.append("    mode: ").append(toIndentedString(mode)).append("\n");
    sb.append("    workflowRef: ").append(toIndentedString(workflowRef)).append("\n");
    sb.append("    workflowSpec: ").append(toIndentedString(workflowSpec)).append("\n");
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

