package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.Map;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1DefinitionBase
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1DefinitionBase   {
  @JsonProperty("alias")
  private String alias = null;

  @JsonProperty("category")
  private String category = null;

  @JsonProperty("component")
  private V1beta1ComponentDefinitionSpec component = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("icon")
  private String icon = null;

  @JsonProperty("labels")
  @Valid
  private Map<String, String> labels = new HashMap<String, String>();

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("ownerAddon")
  private String ownerAddon = null;

  @JsonProperty("policy")
  private V1beta1PolicyDefinitionSpec policy = null;

  @JsonProperty("status")
  private String status = null;

  @JsonProperty("trait")
  private V1beta1TraitDefinitionSpec trait = null;

  @JsonProperty("workflowStep")
  private V1beta1WorkflowStepDefinitionSpec workflowStep = null;

  @JsonProperty("workloadType")
  private String workloadType = null;

  public V1DefinitionBase alias(String alias) {
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

  public V1DefinitionBase category(String category) {
    this.category = category;
    return this;
  }

  /**
   * Get category
   * @return category
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public V1DefinitionBase component(V1beta1ComponentDefinitionSpec component) {
    this.component = component;
    return this;
  }

  /**
   * Get component
   * @return component
  **/
  @ApiModelProperty(value = "")

  @Valid

  public V1beta1ComponentDefinitionSpec getComponent() {
    return component;
  }

  public void setComponent(V1beta1ComponentDefinitionSpec component) {
    this.component = component;
  }

  public V1DefinitionBase description(String description) {
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

  public V1DefinitionBase icon(String icon) {
    this.icon = icon;
    return this;
  }

  /**
   * Get icon
   * @return icon
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public V1DefinitionBase labels(Map<String, String> labels) {
    this.labels = labels;
    return this;
  }

  public V1DefinitionBase putLabelsItem(String key, String labelsItem) {
    this.labels.put(key, labelsItem);
    return this;
  }

  /**
   * Get labels
   * @return labels
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Map<String, String> getLabels() {
    return labels;
  }

  public void setLabels(Map<String, String> labels) {
    this.labels = labels;
  }

  public V1DefinitionBase name(String name) {
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

  public V1DefinitionBase ownerAddon(String ownerAddon) {
    this.ownerAddon = ownerAddon;
    return this;
  }

  /**
   * Get ownerAddon
   * @return ownerAddon
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getOwnerAddon() {
    return ownerAddon;
  }

  public void setOwnerAddon(String ownerAddon) {
    this.ownerAddon = ownerAddon;
  }

  public V1DefinitionBase policy(V1beta1PolicyDefinitionSpec policy) {
    this.policy = policy;
    return this;
  }

  /**
   * Get policy
   * @return policy
  **/
  @ApiModelProperty(value = "")

  @Valid

  public V1beta1PolicyDefinitionSpec getPolicy() {
    return policy;
  }

  public void setPolicy(V1beta1PolicyDefinitionSpec policy) {
    this.policy = policy;
  }

  public V1DefinitionBase status(String status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public V1DefinitionBase trait(V1beta1TraitDefinitionSpec trait) {
    this.trait = trait;
    return this;
  }

  /**
   * Get trait
   * @return trait
  **/
  @ApiModelProperty(value = "")

  @Valid

  public V1beta1TraitDefinitionSpec getTrait() {
    return trait;
  }

  public void setTrait(V1beta1TraitDefinitionSpec trait) {
    this.trait = trait;
  }

  public V1DefinitionBase workflowStep(V1beta1WorkflowStepDefinitionSpec workflowStep) {
    this.workflowStep = workflowStep;
    return this;
  }

  /**
   * Get workflowStep
   * @return workflowStep
  **/
  @ApiModelProperty(value = "")

  @Valid

  public V1beta1WorkflowStepDefinitionSpec getWorkflowStep() {
    return workflowStep;
  }

  public void setWorkflowStep(V1beta1WorkflowStepDefinitionSpec workflowStep) {
    this.workflowStep = workflowStep;
  }

  public V1DefinitionBase workloadType(String workloadType) {
    this.workloadType = workloadType;
    return this;
  }

  /**
   * Get workloadType
   * @return workloadType
  **/
  @ApiModelProperty(value = "")


  public String getWorkloadType() {
    return workloadType;
  }

  public void setWorkloadType(String workloadType) {
    this.workloadType = workloadType;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1DefinitionBase v1DefinitionBase = (V1DefinitionBase) o;
    return Objects.equals(this.alias, v1DefinitionBase.alias) &&
        Objects.equals(this.category, v1DefinitionBase.category) &&
        Objects.equals(this.component, v1DefinitionBase.component) &&
        Objects.equals(this.description, v1DefinitionBase.description) &&
        Objects.equals(this.icon, v1DefinitionBase.icon) &&
        Objects.equals(this.labels, v1DefinitionBase.labels) &&
        Objects.equals(this.name, v1DefinitionBase.name) &&
        Objects.equals(this.ownerAddon, v1DefinitionBase.ownerAddon) &&
        Objects.equals(this.policy, v1DefinitionBase.policy) &&
        Objects.equals(this.status, v1DefinitionBase.status) &&
        Objects.equals(this.trait, v1DefinitionBase.trait) &&
        Objects.equals(this.workflowStep, v1DefinitionBase.workflowStep) &&
        Objects.equals(this.workloadType, v1DefinitionBase.workloadType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, category, component, description, icon, labels, name, ownerAddon, policy, status, trait, workflowStep, workloadType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1DefinitionBase {\n");
    
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
    sb.append("    component: ").append(toIndentedString(component)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    icon: ").append(toIndentedString(icon)).append("\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    ownerAddon: ").append(toIndentedString(ownerAddon)).append("\n");
    sb.append("    policy: ").append(toIndentedString(policy)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    trait: ").append(toIndentedString(trait)).append("\n");
    sb.append("    workflowStep: ").append(toIndentedString(workflowStep)).append("\n");
    sb.append("    workloadType: ").append(toIndentedString(workloadType)).append("\n");
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

