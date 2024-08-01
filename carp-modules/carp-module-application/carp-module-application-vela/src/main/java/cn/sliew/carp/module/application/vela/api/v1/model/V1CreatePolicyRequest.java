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
 * V1CreatePolicyRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1CreatePolicyRequest   {
  @JsonProperty("alias")
  private String alias = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("envName")
  private String envName = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("properties")
  private String properties = null;

  @JsonProperty("type")
  private String type = null;

  @JsonProperty("workflowPolicyBind")
  @Valid
  private List<V1WorkflowPolicyBinding> workflowPolicyBind = new ArrayList<V1WorkflowPolicyBinding>();

  public V1CreatePolicyRequest alias(String alias) {
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

  public V1CreatePolicyRequest description(String description) {
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

  public V1CreatePolicyRequest envName(String envName) {
    this.envName = envName;
    return this;
  }

  /**
   * Get envName
   * @return envName
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getEnvName() {
    return envName;
  }

  public void setEnvName(String envName) {
    this.envName = envName;
  }

  public V1CreatePolicyRequest name(String name) {
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

  public V1CreatePolicyRequest properties(String properties) {
    this.properties = properties;
    return this;
  }

  /**
   * Get properties
   * @return properties
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getProperties() {
    return properties;
  }

  public void setProperties(String properties) {
    this.properties = properties;
  }

  public V1CreatePolicyRequest type(String type) {
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

  public V1CreatePolicyRequest workflowPolicyBind(List<V1WorkflowPolicyBinding> workflowPolicyBind) {
    this.workflowPolicyBind = workflowPolicyBind;
    return this;
  }

  public V1CreatePolicyRequest addWorkflowPolicyBindItem(V1WorkflowPolicyBinding workflowPolicyBindItem) {
    this.workflowPolicyBind.add(workflowPolicyBindItem);
    return this;
  }

  /**
   * Get workflowPolicyBind
   * @return workflowPolicyBind
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1WorkflowPolicyBinding> getWorkflowPolicyBind() {
    return workflowPolicyBind;
  }

  public void setWorkflowPolicyBind(List<V1WorkflowPolicyBinding> workflowPolicyBind) {
    this.workflowPolicyBind = workflowPolicyBind;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1CreatePolicyRequest v1CreatePolicyRequest = (V1CreatePolicyRequest) o;
    return Objects.equals(this.alias, v1CreatePolicyRequest.alias) &&
        Objects.equals(this.description, v1CreatePolicyRequest.description) &&
        Objects.equals(this.envName, v1CreatePolicyRequest.envName) &&
        Objects.equals(this.name, v1CreatePolicyRequest.name) &&
        Objects.equals(this.properties, v1CreatePolicyRequest.properties) &&
        Objects.equals(this.type, v1CreatePolicyRequest.type) &&
        Objects.equals(this.workflowPolicyBind, v1CreatePolicyRequest.workflowPolicyBind);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, description, envName, name, properties, type, workflowPolicyBind);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1CreatePolicyRequest {\n");
    
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    envName: ").append(toIndentedString(envName)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    workflowPolicyBind: ").append(toIndentedString(workflowPolicyBind)).append("\n");
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

