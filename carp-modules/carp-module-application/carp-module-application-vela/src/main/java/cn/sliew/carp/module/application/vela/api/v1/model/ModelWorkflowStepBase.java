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
 * ModelWorkflowStepBase
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class ModelWorkflowStepBase   {
  @JsonProperty("alias")
  private String alias = null;

  @JsonProperty("dependsOn")
  @Valid
  private List<String> dependsOn = new ArrayList<String>();

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

  @JsonProperty("orderIndex")
  private Integer orderIndex = null;

  @JsonProperty("outputs")
  @Valid
  private List<V1alpha1OutputItem> outputs = null;

  @JsonProperty("properties")
  private ModelJSONStruct properties = null;

  @JsonProperty("timeout")
  private String timeout = null;

  @JsonProperty("type")
  private String type = null;

  public ModelWorkflowStepBase alias(String alias) {
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

  public ModelWorkflowStepBase dependsOn(List<String> dependsOn) {
    this.dependsOn = dependsOn;
    return this;
  }

  public ModelWorkflowStepBase addDependsOnItem(String dependsOnItem) {
    this.dependsOn.add(dependsOnItem);
    return this;
  }

  /**
   * Get dependsOn
   * @return dependsOn
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public List<String> getDependsOn() {
    return dependsOn;
  }

  public void setDependsOn(List<String> dependsOn) {
    this.dependsOn = dependsOn;
  }

  public ModelWorkflowStepBase description(String description) {
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

  public ModelWorkflowStepBase _if(String _if) {
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

  public ModelWorkflowStepBase inputs(List<V1alpha1InputItem> inputs) {
    this.inputs = inputs;
    return this;
  }

  public ModelWorkflowStepBase addInputsItem(V1alpha1InputItem inputsItem) {
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

  public ModelWorkflowStepBase meta(V1alpha1WorkflowStepMeta meta) {
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

  public ModelWorkflowStepBase name(String name) {
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

  public ModelWorkflowStepBase orderIndex(Integer orderIndex) {
    this.orderIndex = orderIndex;
    return this;
  }

  /**
   * Get orderIndex
   * @return orderIndex
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getOrderIndex() {
    return orderIndex;
  }

  public void setOrderIndex(Integer orderIndex) {
    this.orderIndex = orderIndex;
  }

  public ModelWorkflowStepBase outputs(List<V1alpha1OutputItem> outputs) {
    this.outputs = outputs;
    return this;
  }

  public ModelWorkflowStepBase addOutputsItem(V1alpha1OutputItem outputsItem) {
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

  public ModelWorkflowStepBase properties(ModelJSONStruct properties) {
    this.properties = properties;
    return this;
  }

  /**
   * Get properties
   * @return properties
  **/
  @ApiModelProperty(value = "")

  @Valid

  public ModelJSONStruct getProperties() {
    return properties;
  }

  public void setProperties(ModelJSONStruct properties) {
    this.properties = properties;
  }

  public ModelWorkflowStepBase timeout(String timeout) {
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

  public ModelWorkflowStepBase type(String type) {
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
    ModelWorkflowStepBase modelWorkflowStepBase = (ModelWorkflowStepBase) o;
    return Objects.equals(this.alias, modelWorkflowStepBase.alias) &&
        Objects.equals(this.dependsOn, modelWorkflowStepBase.dependsOn) &&
        Objects.equals(this.description, modelWorkflowStepBase.description) &&
        Objects.equals(this._if, modelWorkflowStepBase._if) &&
        Objects.equals(this.inputs, modelWorkflowStepBase.inputs) &&
        Objects.equals(this.meta, modelWorkflowStepBase.meta) &&
        Objects.equals(this.name, modelWorkflowStepBase.name) &&
        Objects.equals(this.orderIndex, modelWorkflowStepBase.orderIndex) &&
        Objects.equals(this.outputs, modelWorkflowStepBase.outputs) &&
        Objects.equals(this.properties, modelWorkflowStepBase.properties) &&
        Objects.equals(this.timeout, modelWorkflowStepBase.timeout) &&
        Objects.equals(this.type, modelWorkflowStepBase.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, dependsOn, description, _if, inputs, meta, name, orderIndex, outputs, properties, timeout, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ModelWorkflowStepBase {\n");
    
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    dependsOn: ").append(toIndentedString(dependsOn)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    _if: ").append(toIndentedString(_if)).append("\n");
    sb.append("    inputs: ").append(toIndentedString(inputs)).append("\n");
    sb.append("    meta: ").append(toIndentedString(meta)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    orderIndex: ").append(toIndentedString(orderIndex)).append("\n");
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

