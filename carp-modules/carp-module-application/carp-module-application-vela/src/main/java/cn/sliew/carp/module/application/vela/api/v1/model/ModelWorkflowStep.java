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
 * ModelWorkflowStep
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class ModelWorkflowStep   {
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

  @JsonProperty("mode")
  private String mode = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("orderIndex")
  private Integer orderIndex = null;

  @JsonProperty("outputs")
  @Valid
  private List<V1alpha1OutputItem> outputs = null;

  @JsonProperty("properties")
  private ModelJSONStruct properties = null;

  @JsonProperty("subSteps")
  @Valid
  private List<ModelWorkflowStepBase> subSteps = null;

  @JsonProperty("timeout")
  private String timeout = null;

  @JsonProperty("type")
  private String type = null;

  public ModelWorkflowStep alias(String alias) {
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

  public ModelWorkflowStep dependsOn(List<String> dependsOn) {
    this.dependsOn = dependsOn;
    return this;
  }

  public ModelWorkflowStep addDependsOnItem(String dependsOnItem) {
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

  public ModelWorkflowStep description(String description) {
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

  public ModelWorkflowStep _if(String _if) {
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

  public ModelWorkflowStep inputs(List<V1alpha1InputItem> inputs) {
    this.inputs = inputs;
    return this;
  }

  public ModelWorkflowStep addInputsItem(V1alpha1InputItem inputsItem) {
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

  public ModelWorkflowStep meta(V1alpha1WorkflowStepMeta meta) {
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

  public ModelWorkflowStep mode(String mode) {
    this.mode = mode;
    return this;
  }

  /**
   * Get mode
   * @return mode
  **/
  @ApiModelProperty(value = "")


  public String getMode() {
    return mode;
  }

  public void setMode(String mode) {
    this.mode = mode;
  }

  public ModelWorkflowStep name(String name) {
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

  public ModelWorkflowStep orderIndex(Integer orderIndex) {
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

  public ModelWorkflowStep outputs(List<V1alpha1OutputItem> outputs) {
    this.outputs = outputs;
    return this;
  }

  public ModelWorkflowStep addOutputsItem(V1alpha1OutputItem outputsItem) {
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

  public ModelWorkflowStep properties(ModelJSONStruct properties) {
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

  public ModelWorkflowStep subSteps(List<ModelWorkflowStepBase> subSteps) {
    this.subSteps = subSteps;
    return this;
  }

  public ModelWorkflowStep addSubStepsItem(ModelWorkflowStepBase subStepsItem) {
    if (this.subSteps == null) {
      this.subSteps = new ArrayList<ModelWorkflowStepBase>();
    }
    this.subSteps.add(subStepsItem);
    return this;
  }

  /**
   * Get subSteps
   * @return subSteps
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<ModelWorkflowStepBase> getSubSteps() {
    return subSteps;
  }

  public void setSubSteps(List<ModelWorkflowStepBase> subSteps) {
    this.subSteps = subSteps;
  }

  public ModelWorkflowStep timeout(String timeout) {
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

  public ModelWorkflowStep type(String type) {
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
    ModelWorkflowStep modelWorkflowStep = (ModelWorkflowStep) o;
    return Objects.equals(this.alias, modelWorkflowStep.alias) &&
        Objects.equals(this.dependsOn, modelWorkflowStep.dependsOn) &&
        Objects.equals(this.description, modelWorkflowStep.description) &&
        Objects.equals(this._if, modelWorkflowStep._if) &&
        Objects.equals(this.inputs, modelWorkflowStep.inputs) &&
        Objects.equals(this.meta, modelWorkflowStep.meta) &&
        Objects.equals(this.mode, modelWorkflowStep.mode) &&
        Objects.equals(this.name, modelWorkflowStep.name) &&
        Objects.equals(this.orderIndex, modelWorkflowStep.orderIndex) &&
        Objects.equals(this.outputs, modelWorkflowStep.outputs) &&
        Objects.equals(this.properties, modelWorkflowStep.properties) &&
        Objects.equals(this.subSteps, modelWorkflowStep.subSteps) &&
        Objects.equals(this.timeout, modelWorkflowStep.timeout) &&
        Objects.equals(this.type, modelWorkflowStep.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, dependsOn, description, _if, inputs, meta, mode, name, orderIndex, outputs, properties, subSteps, timeout, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ModelWorkflowStep {\n");
    
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    dependsOn: ").append(toIndentedString(dependsOn)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    _if: ").append(toIndentedString(_if)).append("\n");
    sb.append("    inputs: ").append(toIndentedString(inputs)).append("\n");
    sb.append("    meta: ").append(toIndentedString(meta)).append("\n");
    sb.append("    mode: ").append(toIndentedString(mode)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    orderIndex: ").append(toIndentedString(orderIndex)).append("\n");
    sb.append("    outputs: ").append(toIndentedString(outputs)).append("\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
    sb.append("    subSteps: ").append(toIndentedString(subSteps)).append("\n");
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

