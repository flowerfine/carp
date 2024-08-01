package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1CreateApplicationTriggerRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1CreateApplicationTriggerRequest   {
  @JsonProperty("alias")
  private String alias = null;

  @JsonProperty("componentName")
  private String componentName = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("payloadType")
  private String payloadType = null;

  @JsonProperty("registry")
  private String registry = null;

  @JsonProperty("type")
  private String type = null;

  @JsonProperty("workflowName")
  private String workflowName = null;

  public V1CreateApplicationTriggerRequest alias(String alias) {
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

  public V1CreateApplicationTriggerRequest componentName(String componentName) {
    this.componentName = componentName;
    return this;
  }

  /**
   * Get componentName
   * @return componentName
  **/
  @ApiModelProperty(value = "")


  public String getComponentName() {
    return componentName;
  }

  public void setComponentName(String componentName) {
    this.componentName = componentName;
  }

  public V1CreateApplicationTriggerRequest description(String description) {
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

  public V1CreateApplicationTriggerRequest name(String name) {
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

  public V1CreateApplicationTriggerRequest payloadType(String payloadType) {
    this.payloadType = payloadType;
    return this;
  }

  /**
   * Get payloadType
   * @return payloadType
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getPayloadType() {
    return payloadType;
  }

  public void setPayloadType(String payloadType) {
    this.payloadType = payloadType;
  }

  public V1CreateApplicationTriggerRequest registry(String registry) {
    this.registry = registry;
    return this;
  }

  /**
   * Get registry
   * @return registry
  **/
  @ApiModelProperty(value = "")


  public String getRegistry() {
    return registry;
  }

  public void setRegistry(String registry) {
    this.registry = registry;
  }

  public V1CreateApplicationTriggerRequest type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public V1CreateApplicationTriggerRequest workflowName(String workflowName) {
    this.workflowName = workflowName;
    return this;
  }

  /**
   * Get workflowName
   * @return workflowName
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getWorkflowName() {
    return workflowName;
  }

  public void setWorkflowName(String workflowName) {
    this.workflowName = workflowName;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1CreateApplicationTriggerRequest v1CreateApplicationTriggerRequest = (V1CreateApplicationTriggerRequest) o;
    return Objects.equals(this.alias, v1CreateApplicationTriggerRequest.alias) &&
        Objects.equals(this.componentName, v1CreateApplicationTriggerRequest.componentName) &&
        Objects.equals(this.description, v1CreateApplicationTriggerRequest.description) &&
        Objects.equals(this.name, v1CreateApplicationTriggerRequest.name) &&
        Objects.equals(this.payloadType, v1CreateApplicationTriggerRequest.payloadType) &&
        Objects.equals(this.registry, v1CreateApplicationTriggerRequest.registry) &&
        Objects.equals(this.type, v1CreateApplicationTriggerRequest.type) &&
        Objects.equals(this.workflowName, v1CreateApplicationTriggerRequest.workflowName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, componentName, description, name, payloadType, registry, type, workflowName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1CreateApplicationTriggerRequest {\n");
    
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    componentName: ").append(toIndentedString(componentName)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    payloadType: ").append(toIndentedString(payloadType)).append("\n");
    sb.append("    registry: ").append(toIndentedString(registry)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    workflowName: ").append(toIndentedString(workflowName)).append("\n");
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

