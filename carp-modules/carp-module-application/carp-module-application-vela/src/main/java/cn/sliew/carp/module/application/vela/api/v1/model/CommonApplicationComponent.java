package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CommonApplicationComponent
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class CommonApplicationComponent   {
  @JsonProperty("dependsOn")
  @Valid
  private List<String> dependsOn = null;

  @JsonProperty("externalRevision")
  private String externalRevision = null;

  @JsonProperty("inputs")
  @Valid
  private List<V1alpha1InputItem> inputs = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("outputs")
  @Valid
  private List<V1alpha1OutputItem> outputs = null;

  @JsonProperty("properties")
  private String properties = null;

  @JsonProperty("scopes")
  @Valid
  private Map<String, String> scopes = null;

  @JsonProperty("traits")
  @Valid
  private List<CommonApplicationTrait> traits = null;

  @JsonProperty("type")
  private String type = null;

  public CommonApplicationComponent dependsOn(List<String> dependsOn) {
    this.dependsOn = dependsOn;
    return this;
  }

  public CommonApplicationComponent addDependsOnItem(String dependsOnItem) {
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

  public CommonApplicationComponent externalRevision(String externalRevision) {
    this.externalRevision = externalRevision;
    return this;
  }

  /**
   * Get externalRevision
   * @return externalRevision
  **/
  @ApiModelProperty(value = "")


  public String getExternalRevision() {
    return externalRevision;
  }

  public void setExternalRevision(String externalRevision) {
    this.externalRevision = externalRevision;
  }

  public CommonApplicationComponent inputs(List<V1alpha1InputItem> inputs) {
    this.inputs = inputs;
    return this;
  }

  public CommonApplicationComponent addInputsItem(V1alpha1InputItem inputsItem) {
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

  public CommonApplicationComponent name(String name) {
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

  public CommonApplicationComponent outputs(List<V1alpha1OutputItem> outputs) {
    this.outputs = outputs;
    return this;
  }

  public CommonApplicationComponent addOutputsItem(V1alpha1OutputItem outputsItem) {
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

  public CommonApplicationComponent properties(String properties) {
    this.properties = properties;
    return this;
  }

  /**
   * Get properties
   * @return properties
  **/
  @ApiModelProperty(value = "")


  public String getProperties() {
    return properties;
  }

  public void setProperties(String properties) {
    this.properties = properties;
  }

  public CommonApplicationComponent scopes(Map<String, String> scopes) {
    this.scopes = scopes;
    return this;
  }

  public CommonApplicationComponent putScopesItem(String key, String scopesItem) {
    if (this.scopes == null) {
      this.scopes = new HashMap<String, String>();
    }
    this.scopes.put(key, scopesItem);
    return this;
  }

  /**
   * Get scopes
   * @return scopes
  **/
  @ApiModelProperty(value = "")


  public Map<String, String> getScopes() {
    return scopes;
  }

  public void setScopes(Map<String, String> scopes) {
    this.scopes = scopes;
  }

  public CommonApplicationComponent traits(List<CommonApplicationTrait> traits) {
    this.traits = traits;
    return this;
  }

  public CommonApplicationComponent addTraitsItem(CommonApplicationTrait traitsItem) {
    if (this.traits == null) {
      this.traits = new ArrayList<CommonApplicationTrait>();
    }
    this.traits.add(traitsItem);
    return this;
  }

  /**
   * Get traits
   * @return traits
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<CommonApplicationTrait> getTraits() {
    return traits;
  }

  public void setTraits(List<CommonApplicationTrait> traits) {
    this.traits = traits;
  }

  public CommonApplicationComponent type(String type) {
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
    CommonApplicationComponent commonApplicationComponent = (CommonApplicationComponent) o;
    return Objects.equals(this.dependsOn, commonApplicationComponent.dependsOn) &&
        Objects.equals(this.externalRevision, commonApplicationComponent.externalRevision) &&
        Objects.equals(this.inputs, commonApplicationComponent.inputs) &&
        Objects.equals(this.name, commonApplicationComponent.name) &&
        Objects.equals(this.outputs, commonApplicationComponent.outputs) &&
        Objects.equals(this.properties, commonApplicationComponent.properties) &&
        Objects.equals(this.scopes, commonApplicationComponent.scopes) &&
        Objects.equals(this.traits, commonApplicationComponent.traits) &&
        Objects.equals(this.type, commonApplicationComponent.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dependsOn, externalRevision, inputs, name, outputs, properties, scopes, traits, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommonApplicationComponent {\n");
    
    sb.append("    dependsOn: ").append(toIndentedString(dependsOn)).append("\n");
    sb.append("    externalRevision: ").append(toIndentedString(externalRevision)).append("\n");
    sb.append("    inputs: ").append(toIndentedString(inputs)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    outputs: ").append(toIndentedString(outputs)).append("\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
    sb.append("    scopes: ").append(toIndentedString(scopes)).append("\n");
    sb.append("    traits: ").append(toIndentedString(traits)).append("\n");
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

