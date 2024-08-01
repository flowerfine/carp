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
 * V1WorkflowStepBase
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1WorkflowStepBase   {
  @JsonProperty("alias")
  private String alias = null;

  @JsonProperty("dependsOn")
  @Valid
  private List<String> dependsOn = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("if")
  private String _if = null;

  @JsonProperty("inputs")
  @Valid
  private List<V1alpha1InputItem> inputs = null;

  @JsonProperty("meta")
  private V1alpha1WorkflowStepMeta meta = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("outputs")
  @Valid
  private List<V1alpha1OutputItem> outputs = null;

  @JsonProperty("properties")
  private Object properties = null;

  @JsonProperty("timeout")
  private String timeout = null;

  @JsonProperty("type")
  private String type = null;

  public V1WorkflowStepBase alias(String alias) {
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

  public V1WorkflowStepBase dependsOn(List<String> dependsOn) {
    this.dependsOn = dependsOn;
    return this;
  }

  public V1WorkflowStepBase addDependsOnItem(String dependsOnItem) {
    if (this.dependsOn == null) {
      this.dependsOn = new ArrayList<String>();
    }
    this.dependsOn.add(dependsOnItem);
    return this;
  }

  /**
   * Get dependsOn
   * @return dependsOn
  **/
  @ApiModelProperty(value = "")


  public List<String> getDependsOn() {
    return dependsOn;
  }

  public void setDependsOn(List<String> dependsOn) {
    this.dependsOn = dependsOn;
  }

  public V1WorkflowStepBase description(String description) {
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

  public V1WorkflowStepBase _if(String _if) {
    this._if = _if;
    return this;
  }

  /**
   * Get _if
   * @return _if
  **/
  @ApiModelProperty(value = "")


  public String getIf() {
    return _if;
  }

  public void setIf(String _if) {
    this._if = _if;
  }

  public V1WorkflowStepBase inputs(List<V1alpha1InputItem> inputs) {
    this.inputs = inputs;
    return this;
  }

  public V1WorkflowStepBase addInputsItem(V1alpha1InputItem inputsItem) {
    if (this.inputs == null) {
      this.inputs = new ArrayList<V1alpha1InputItem>();
    }
    this.inputs.add(inputsItem);
    return this;
  }

  /**
   * Get inputs
   * @return inputs
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<V1alpha1InputItem> getInputs() {
    return inputs;
  }

  public void setInputs(List<V1alpha1InputItem> inputs) {
    this.inputs = inputs;
  }

  public V1WorkflowStepBase meta(V1alpha1WorkflowStepMeta meta) {
    this.meta = meta;
    return this;
  }

  /**
   * Get meta
   * @return meta
  **/
  @ApiModelProperty(value = "")

  @Valid

  public V1alpha1WorkflowStepMeta getMeta() {
    return meta;
  }

  public void setMeta(V1alpha1WorkflowStepMeta meta) {
    this.meta = meta;
  }

  public V1WorkflowStepBase name(String name) {
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

  public V1WorkflowStepBase outputs(List<V1alpha1OutputItem> outputs) {
    this.outputs = outputs;
    return this;
  }

  public V1WorkflowStepBase addOutputsItem(V1alpha1OutputItem outputsItem) {
    if (this.outputs == null) {
      this.outputs = new ArrayList<V1alpha1OutputItem>();
    }
    this.outputs.add(outputsItem);
    return this;
  }

  /**
   * Get outputs
   * @return outputs
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<V1alpha1OutputItem> getOutputs() {
    return outputs;
  }

  public void setOutputs(List<V1alpha1OutputItem> outputs) {
    this.outputs = outputs;
  }

  public V1WorkflowStepBase properties(Object properties) {
    this.properties = properties;
    return this;
  }

  /**
   * Get properties
   * @return properties
  **/
  @ApiModelProperty(value = "")


  public Object getProperties() {
    return properties;
  }

  public void setProperties(Object properties) {
    this.properties = properties;
  }

  public V1WorkflowStepBase timeout(String timeout) {
    this.timeout = timeout;
    return this;
  }

  /**
   * Get timeout
   * @return timeout
  **/
  @ApiModelProperty(value = "")


  public String getTimeout() {
    return timeout;
  }

  public void setTimeout(String timeout) {
    this.timeout = timeout;
  }

  public V1WorkflowStepBase type(String type) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1WorkflowStepBase v1WorkflowStepBase = (V1WorkflowStepBase) o;
    return Objects.equals(this.alias, v1WorkflowStepBase.alias) &&
        Objects.equals(this.dependsOn, v1WorkflowStepBase.dependsOn) &&
        Objects.equals(this.description, v1WorkflowStepBase.description) &&
        Objects.equals(this._if, v1WorkflowStepBase._if) &&
        Objects.equals(this.inputs, v1WorkflowStepBase.inputs) &&
        Objects.equals(this.meta, v1WorkflowStepBase.meta) &&
        Objects.equals(this.name, v1WorkflowStepBase.name) &&
        Objects.equals(this.outputs, v1WorkflowStepBase.outputs) &&
        Objects.equals(this.properties, v1WorkflowStepBase.properties) &&
        Objects.equals(this.timeout, v1WorkflowStepBase.timeout) &&
        Objects.equals(this.type, v1WorkflowStepBase.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, dependsOn, description, _if, inputs, meta, name, outputs, properties, timeout, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1WorkflowStepBase {\n");
    
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    dependsOn: ").append(toIndentedString(dependsOn)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    _if: ").append(toIndentedString(_if)).append("\n");
    sb.append("    inputs: ").append(toIndentedString(inputs)).append("\n");
    sb.append("    meta: ").append(toIndentedString(meta)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    outputs: ").append(toIndentedString(outputs)).append("\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
    sb.append("    timeout: ").append(toIndentedString(timeout)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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

