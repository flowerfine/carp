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
 * CommonAppStatus
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class CommonAppStatus   {
  @JsonProperty("appliedResources")
  @Valid
  private List<CommonClusterObjectReference> appliedResources = null;

  @JsonProperty("components")
  @Valid
  private List<V1ObjectReference> components = null;

  @JsonProperty("conditions")
  @Valid
  private List<ConditionCondition> conditions = null;

  @JsonProperty("latestRevision")
  private CommonRevision latestRevision = null;

  @JsonProperty("observedGeneration")
  private Long observedGeneration = null;

  @JsonProperty("policy")
  @Valid
  private List<CommonPolicyStatus> policy = null;

  @JsonProperty("services")
  @Valid
  private List<CommonApplicationComponentStatus> services = null;

  @JsonProperty("status")
  private String status = null;

  @JsonProperty("workflow")
  private CommonWorkflowStatus workflow = null;

  public CommonAppStatus appliedResources(List<CommonClusterObjectReference> appliedResources) {
    this.appliedResources = appliedResources;
    return this;
  }

  public CommonAppStatus addAppliedResourcesItem(CommonClusterObjectReference appliedResourcesItem) {
    if (this.appliedResources == null) {
      this.appliedResources = new ArrayList<CommonClusterObjectReference>();
    }
    this.appliedResources.add(appliedResourcesItem);
    return this;
  }

  /**
   * Get appliedResources
   * @return appliedResources
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<CommonClusterObjectReference> getAppliedResources() {
    return appliedResources;
  }

  public void setAppliedResources(List<CommonClusterObjectReference> appliedResources) {
    this.appliedResources = appliedResources;
  }

  public CommonAppStatus components(List<V1ObjectReference> components) {
    this.components = components;
    return this;
  }

  public CommonAppStatus addComponentsItem(V1ObjectReference componentsItem) {
    if (this.components == null) {
      this.components = new ArrayList<V1ObjectReference>();
    }
    this.components.add(componentsItem);
    return this;
  }

  /**
   * Get components
   * @return components
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<V1ObjectReference> getComponents() {
    return components;
  }

  public void setComponents(List<V1ObjectReference> components) {
    this.components = components;
  }

  public CommonAppStatus conditions(List<ConditionCondition> conditions) {
    this.conditions = conditions;
    return this;
  }

  public CommonAppStatus addConditionsItem(ConditionCondition conditionsItem) {
    if (this.conditions == null) {
      this.conditions = new ArrayList<ConditionCondition>();
    }
    this.conditions.add(conditionsItem);
    return this;
  }

  /**
   * Get conditions
   * @return conditions
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<ConditionCondition> getConditions() {
    return conditions;
  }

  public void setConditions(List<ConditionCondition> conditions) {
    this.conditions = conditions;
  }

  public CommonAppStatus latestRevision(CommonRevision latestRevision) {
    this.latestRevision = latestRevision;
    return this;
  }

  /**
   * Get latestRevision
   * @return latestRevision
  **/
  @ApiModelProperty(value = "")

  @Valid

  public CommonRevision getLatestRevision() {
    return latestRevision;
  }

  public void setLatestRevision(CommonRevision latestRevision) {
    this.latestRevision = latestRevision;
  }

  public CommonAppStatus observedGeneration(Long observedGeneration) {
    this.observedGeneration = observedGeneration;
    return this;
  }

  /**
   * Get observedGeneration
   * @return observedGeneration
  **/
  @ApiModelProperty(value = "")


  public Long getObservedGeneration() {
    return observedGeneration;
  }

  public void setObservedGeneration(Long observedGeneration) {
    this.observedGeneration = observedGeneration;
  }

  public CommonAppStatus policy(List<CommonPolicyStatus> policy) {
    this.policy = policy;
    return this;
  }

  public CommonAppStatus addPolicyItem(CommonPolicyStatus policyItem) {
    if (this.policy == null) {
      this.policy = new ArrayList<CommonPolicyStatus>();
    }
    this.policy.add(policyItem);
    return this;
  }

  /**
   * Get policy
   * @return policy
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<CommonPolicyStatus> getPolicy() {
    return policy;
  }

  public void setPolicy(List<CommonPolicyStatus> policy) {
    this.policy = policy;
  }

  public CommonAppStatus services(List<CommonApplicationComponentStatus> services) {
    this.services = services;
    return this;
  }

  public CommonAppStatus addServicesItem(CommonApplicationComponentStatus servicesItem) {
    if (this.services == null) {
      this.services = new ArrayList<CommonApplicationComponentStatus>();
    }
    this.services.add(servicesItem);
    return this;
  }

  /**
   * Get services
   * @return services
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<CommonApplicationComponentStatus> getServices() {
    return services;
  }

  public void setServices(List<CommonApplicationComponentStatus> services) {
    this.services = services;
  }

  public CommonAppStatus status(String status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  **/
  @ApiModelProperty(value = "")


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public CommonAppStatus workflow(CommonWorkflowStatus workflow) {
    this.workflow = workflow;
    return this;
  }

  /**
   * Get workflow
   * @return workflow
  **/
  @ApiModelProperty(value = "")

  @Valid

  public CommonWorkflowStatus getWorkflow() {
    return workflow;
  }

  public void setWorkflow(CommonWorkflowStatus workflow) {
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
    CommonAppStatus commonAppStatus = (CommonAppStatus) o;
    return Objects.equals(this.appliedResources, commonAppStatus.appliedResources) &&
        Objects.equals(this.components, commonAppStatus.components) &&
        Objects.equals(this.conditions, commonAppStatus.conditions) &&
        Objects.equals(this.latestRevision, commonAppStatus.latestRevision) &&
        Objects.equals(this.observedGeneration, commonAppStatus.observedGeneration) &&
        Objects.equals(this.policy, commonAppStatus.policy) &&
        Objects.equals(this.services, commonAppStatus.services) &&
        Objects.equals(this.status, commonAppStatus.status) &&
        Objects.equals(this.workflow, commonAppStatus.workflow);
  }

  @Override
  public int hashCode() {
    return Objects.hash(appliedResources, components, conditions, latestRevision, observedGeneration, policy, services, status, workflow);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommonAppStatus {\n");
    
    sb.append("    appliedResources: ").append(toIndentedString(appliedResources)).append("\n");
    sb.append("    components: ").append(toIndentedString(components)).append("\n");
    sb.append("    conditions: ").append(toIndentedString(conditions)).append("\n");
    sb.append("    latestRevision: ").append(toIndentedString(latestRevision)).append("\n");
    sb.append("    observedGeneration: ").append(toIndentedString(observedGeneration)).append("\n");
    sb.append("    policy: ").append(toIndentedString(policy)).append("\n");
    sb.append("    services: ").append(toIndentedString(services)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

