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
 * V1ListWorkflowResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ListWorkflowResponse   {
  @JsonProperty("workflows")
  @Valid
  private List<V1WorkflowBase> workflows = new ArrayList<V1WorkflowBase>();

  public V1ListWorkflowResponse workflows(List<V1WorkflowBase> workflows) {
    this.workflows = workflows;
    return this;
  }

  public V1ListWorkflowResponse addWorkflowsItem(V1WorkflowBase workflowsItem) {
    this.workflows.add(workflowsItem);
    return this;
  }

  /**
   * Get workflows
   * @return workflows
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1WorkflowBase> getWorkflows() {
    return workflows;
  }

  public void setWorkflows(List<V1WorkflowBase> workflows) {
    this.workflows = workflows;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1ListWorkflowResponse v1ListWorkflowResponse = (V1ListWorkflowResponse) o;
    return Objects.equals(this.workflows, v1ListWorkflowResponse.workflows);
  }

  @Override
  public int hashCode() {
    return Objects.hash(workflows);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ListWorkflowResponse {\n");
    
    sb.append("    workflows: ").append(toIndentedString(workflows)).append("\n");
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

