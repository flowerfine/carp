package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1ComponentBase
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ComponentBase   {
  @JsonProperty("alias")
  private String alias = null;

  @JsonProperty("componentType")
  private String componentType = null;

  @JsonProperty("createTime")
  private OffsetDateTime createTime = null;

  @JsonProperty("creator")
  private String creator = null;

  @JsonProperty("dependsOn")
  @Valid
  private List<String> dependsOn = new ArrayList<String>();

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("icon")
  private String icon = null;

  @JsonProperty("inputs")
  @Valid
  private List<V1alpha1InputItem> inputs = null;

  @JsonProperty("labels")
  @Valid
  private Map<String, String> labels = null;

  @JsonProperty("main")
  private Boolean main = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("outputs")
  @Valid
  private List<V1alpha1OutputItem> outputs = null;

  @JsonProperty("traits")
  @Valid
  private List<V1ApplicationTrait> traits = new ArrayList<V1ApplicationTrait>();

  @JsonProperty("updateTime")
  private OffsetDateTime updateTime = null;

  @JsonProperty("workloadType")
  private CommonWorkloadTypeDescriptor workloadType = null;

  public V1ComponentBase alias(String alias) {
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

  public V1ComponentBase componentType(String componentType) {
    this.componentType = componentType;
    return this;
  }

  /**
   * Get componentType
   * @return componentType
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getComponentType() {
    return componentType;
  }

  public void setComponentType(String componentType) {
    this.componentType = componentType;
  }

  public V1ComponentBase createTime(OffsetDateTime createTime) {
    this.createTime = createTime;
    return this;
  }

  /**
   * Get createTime
   * @return createTime
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public OffsetDateTime getCreateTime() {
    return createTime;
  }

  public void setCreateTime(OffsetDateTime createTime) {
    this.createTime = createTime;
  }

  public V1ComponentBase creator(String creator) {
    this.creator = creator;
    return this;
  }

  /**
   * Get creator
   * @return creator
  **/
  @ApiModelProperty(value = "")


  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  public V1ComponentBase dependsOn(List<String> dependsOn) {
    this.dependsOn = dependsOn;
    return this;
  }

  public V1ComponentBase addDependsOnItem(String dependsOnItem) {
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

  public V1ComponentBase description(String description) {
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

  public V1ComponentBase icon(String icon) {
    this.icon = icon;
    return this;
  }

  /**
   * Get icon
   * @return icon
  **/
  @ApiModelProperty(value = "")


  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public V1ComponentBase inputs(List<V1alpha1InputItem> inputs) {
    this.inputs = inputs;
    return this;
  }

  public V1ComponentBase addInputsItem(V1alpha1InputItem inputsItem) {
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

  public V1ComponentBase labels(Map<String, String> labels) {
    this.labels = labels;
    return this;
  }

  public V1ComponentBase putLabelsItem(String key, String labelsItem) {
    if (this.labels == null) {
      this.labels = new HashMap<String, String>();
    }
    this.labels.put(key, labelsItem);
    return this;
  }

  /**
   * Get labels
   * @return labels
  **/
  @ApiModelProperty(value = "")


  public Map<String, String> getLabels() {
    return labels;
  }

  public void setLabels(Map<String, String> labels) {
    this.labels = labels;
  }

  public V1ComponentBase main(Boolean main) {
    this.main = main;
    return this;
  }

  /**
   * Get main
   * @return main
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Boolean isMain() {
    return main;
  }

  public void setMain(Boolean main) {
    this.main = main;
  }

  public V1ComponentBase name(String name) {
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

  public V1ComponentBase outputs(List<V1alpha1OutputItem> outputs) {
    this.outputs = outputs;
    return this;
  }

  public V1ComponentBase addOutputsItem(V1alpha1OutputItem outputsItem) {
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

  public V1ComponentBase traits(List<V1ApplicationTrait> traits) {
    this.traits = traits;
    return this;
  }

  public V1ComponentBase addTraitsItem(V1ApplicationTrait traitsItem) {
    this.traits.add(traitsItem);
    return this;
  }

  /**
   * Get traits
   * @return traits
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1ApplicationTrait> getTraits() {
    return traits;
  }

  public void setTraits(List<V1ApplicationTrait> traits) {
    this.traits = traits;
  }

  public V1ComponentBase updateTime(OffsetDateTime updateTime) {
    this.updateTime = updateTime;
    return this;
  }

  /**
   * Get updateTime
   * @return updateTime
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public OffsetDateTime getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(OffsetDateTime updateTime) {
    this.updateTime = updateTime;
  }

  public V1ComponentBase workloadType(CommonWorkloadTypeDescriptor workloadType) {
    this.workloadType = workloadType;
    return this;
  }

  /**
   * Get workloadType
   * @return workloadType
  **/
  @ApiModelProperty(value = "")

  @Valid

  public CommonWorkloadTypeDescriptor getWorkloadType() {
    return workloadType;
  }

  public void setWorkloadType(CommonWorkloadTypeDescriptor workloadType) {
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
    V1ComponentBase v1ComponentBase = (V1ComponentBase) o;
    return Objects.equals(this.alias, v1ComponentBase.alias) &&
        Objects.equals(this.componentType, v1ComponentBase.componentType) &&
        Objects.equals(this.createTime, v1ComponentBase.createTime) &&
        Objects.equals(this.creator, v1ComponentBase.creator) &&
        Objects.equals(this.dependsOn, v1ComponentBase.dependsOn) &&
        Objects.equals(this.description, v1ComponentBase.description) &&
        Objects.equals(this.icon, v1ComponentBase.icon) &&
        Objects.equals(this.inputs, v1ComponentBase.inputs) &&
        Objects.equals(this.labels, v1ComponentBase.labels) &&
        Objects.equals(this.main, v1ComponentBase.main) &&
        Objects.equals(this.name, v1ComponentBase.name) &&
        Objects.equals(this.outputs, v1ComponentBase.outputs) &&
        Objects.equals(this.traits, v1ComponentBase.traits) &&
        Objects.equals(this.updateTime, v1ComponentBase.updateTime) &&
        Objects.equals(this.workloadType, v1ComponentBase.workloadType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, componentType, createTime, creator, dependsOn, description, icon, inputs, labels, main, name, outputs, traits, updateTime, workloadType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ComponentBase {\n");
    
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    componentType: ").append(toIndentedString(componentType)).append("\n");
    sb.append("    createTime: ").append(toIndentedString(createTime)).append("\n");
    sb.append("    creator: ").append(toIndentedString(creator)).append("\n");
    sb.append("    dependsOn: ").append(toIndentedString(dependsOn)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    icon: ").append(toIndentedString(icon)).append("\n");
    sb.append("    inputs: ").append(toIndentedString(inputs)).append("\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
    sb.append("    main: ").append(toIndentedString(main)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    outputs: ").append(toIndentedString(outputs)).append("\n");
    sb.append("    traits: ").append(toIndentedString(traits)).append("\n");
    sb.append("    updateTime: ").append(toIndentedString(updateTime)).append("\n");
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

