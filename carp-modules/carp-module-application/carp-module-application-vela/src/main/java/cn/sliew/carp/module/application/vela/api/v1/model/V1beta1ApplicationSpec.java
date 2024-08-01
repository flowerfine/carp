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
 * V1beta1ApplicationSpec
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1beta1ApplicationSpec   {
  @JsonProperty("components")
  @Valid
  private List<CommonApplicationComponent> components = new ArrayList<CommonApplicationComponent>();

  @JsonProperty("policies")
  @Valid
  private List<V1beta1AppPolicy> policies = null;

  @JsonProperty("workflow")
  private V1beta1Workflow workflow = null;

  public V1beta1ApplicationSpec components(List<CommonApplicationComponent> components) {
    this.components = components;
    return this;
  }

  public V1beta1ApplicationSpec addComponentsItem(CommonApplicationComponent componentsItem) {
    this.components.add(componentsItem);
    return this;
  }

  /**
   * Get components
   * @return components
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<CommonApplicationComponent> getComponents() {
    return components;
  }

  public void setComponents(List<CommonApplicationComponent> components) {
    this.components = components;
  }

  public V1beta1ApplicationSpec policies(List<V1beta1AppPolicy> policies) {
    this.policies = policies;
    return this;
  }

  public V1beta1ApplicationSpec addPoliciesItem(V1beta1AppPolicy policiesItem) {
    if (this.policies == null) {
      this.policies = new ArrayList<V1beta1AppPolicy>();
    }
    this.policies.add(policiesItem);
    return this;
  }

  /**
   * Get policies
   * @return policies
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<V1beta1AppPolicy> getPolicies() {
    return policies;
  }

  public void setPolicies(List<V1beta1AppPolicy> policies) {
    this.policies = policies;
  }

  public V1beta1ApplicationSpec workflow(V1beta1Workflow workflow) {
    this.workflow = workflow;
    return this;
  }

  /**
   * Get workflow
   * @return workflow
  **/
  @ApiModelProperty(value = "")

  @Valid

  public V1beta1Workflow getWorkflow() {
    return workflow;
  }

  public void setWorkflow(V1beta1Workflow workflow) {
    this.workflow = workflow;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1beta1ApplicationSpec v1beta1ApplicationSpec = (V1beta1ApplicationSpec) o;
    return Objects.equals(this.components, v1beta1ApplicationSpec.components) &&
        Objects.equals(this.policies, v1beta1ApplicationSpec.policies) &&
        Objects.equals(this.workflow, v1beta1ApplicationSpec.workflow);
  }

  @Override
  public int hashCode() {
    return Objects.hash(components, policies, workflow);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1beta1ApplicationSpec {\n");
    
    sb.append("    components: ").append(toIndentedString(components)).append("\n");
    sb.append("    policies: ").append(toIndentedString(policies)).append("\n");
    sb.append("    workflow: ").append(toIndentedString(workflow)).append("\n");
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

