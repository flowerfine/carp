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
 * ModelApplicationComponent
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class ModelApplicationComponent   {
  @JsonProperty("alias")
  private String alias = null;

  @JsonProperty("appPrimaryKey")
  private String appPrimaryKey = null;

  @JsonProperty("createTime")
  private OffsetDateTime createTime = null;

  @JsonProperty("creator")
  private String creator = null;

  @JsonProperty("dependsOn")
  @Valid
  private List<String> dependsOn = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("externalRevision")
  private String externalRevision = null;

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

  @JsonProperty("properties")
  private ModelJSONStruct properties = null;

  @JsonProperty("scopes")
  @Valid
  private Map<String, String> scopes = null;

  @JsonProperty("traits")
  @Valid
  private List<ModelApplicationTrait> traits = null;

  @JsonProperty("type")
  private String type = null;

  @JsonProperty("updateTime")
  private OffsetDateTime updateTime = null;

  @JsonProperty("workloadType")
  private CommonWorkloadTypeDescriptor workloadType = null;

  public ModelApplicationComponent alias(String alias) {
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

  public ModelApplicationComponent appPrimaryKey(String appPrimaryKey) {
    this.appPrimaryKey = appPrimaryKey;
    return this;
  }

  /**
   * Get appPrimaryKey
   * @return appPrimaryKey
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getAppPrimaryKey() {
    return appPrimaryKey;
  }

  public void setAppPrimaryKey(String appPrimaryKey) {
    this.appPrimaryKey = appPrimaryKey;
  }

  public ModelApplicationComponent createTime(OffsetDateTime createTime) {
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

  public ModelApplicationComponent creator(String creator) {
    this.creator = creator;
    return this;
  }

  /**
   * Get creator
   * @return creator
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  public ModelApplicationComponent dependsOn(List<String> dependsOn) {
    this.dependsOn = dependsOn;
    return this;
  }

  public ModelApplicationComponent addDependsOnItem(String dependsOnItem) {
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

  public ModelApplicationComponent description(String description) {
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

  public ModelApplicationComponent externalRevision(String externalRevision) {
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

  public ModelApplicationComponent icon(String icon) {
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

  public ModelApplicationComponent inputs(List<V1alpha1InputItem> inputs) {
    this.inputs = inputs;
    return this;
  }

  public ModelApplicationComponent addInputsItem(V1alpha1InputItem inputsItem) {
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

  public ModelApplicationComponent labels(Map<String, String> labels) {
    this.labels = labels;
    return this;
  }

  public ModelApplicationComponent putLabelsItem(String key, String labelsItem) {
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

  public ModelApplicationComponent main(Boolean main) {
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

  public ModelApplicationComponent name(String name) {
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

  public ModelApplicationComponent outputs(List<V1alpha1OutputItem> outputs) {
    this.outputs = outputs;
    return this;
  }

  public ModelApplicationComponent addOutputsItem(V1alpha1OutputItem outputsItem) {
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

  public ModelApplicationComponent properties(ModelJSONStruct properties) {
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

  public ModelApplicationComponent scopes(Map<String, String> scopes) {
    this.scopes = scopes;
    return this;
  }

  public ModelApplicationComponent putScopesItem(String key, String scopesItem) {
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

  public ModelApplicationComponent traits(List<ModelApplicationTrait> traits) {
    this.traits = traits;
    return this;
  }

  public ModelApplicationComponent addTraitsItem(ModelApplicationTrait traitsItem) {
    if (this.traits == null) {
      this.traits = new ArrayList<ModelApplicationTrait>();
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

  public List<ModelApplicationTrait> getTraits() {
    return traits;
  }

  public void setTraits(List<ModelApplicationTrait> traits) {
    this.traits = traits;
  }

  public ModelApplicationComponent type(String type) {
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

  public ModelApplicationComponent updateTime(OffsetDateTime updateTime) {
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

  public ModelApplicationComponent workloadType(CommonWorkloadTypeDescriptor workloadType) {
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
    ModelApplicationComponent modelApplicationComponent = (ModelApplicationComponent) o;
    return Objects.equals(this.alias, modelApplicationComponent.alias) &&
        Objects.equals(this.appPrimaryKey, modelApplicationComponent.appPrimaryKey) &&
        Objects.equals(this.createTime, modelApplicationComponent.createTime) &&
        Objects.equals(this.creator, modelApplicationComponent.creator) &&
        Objects.equals(this.dependsOn, modelApplicationComponent.dependsOn) &&
        Objects.equals(this.description, modelApplicationComponent.description) &&
        Objects.equals(this.externalRevision, modelApplicationComponent.externalRevision) &&
        Objects.equals(this.icon, modelApplicationComponent.icon) &&
        Objects.equals(this.inputs, modelApplicationComponent.inputs) &&
        Objects.equals(this.labels, modelApplicationComponent.labels) &&
        Objects.equals(this.main, modelApplicationComponent.main) &&
        Objects.equals(this.name, modelApplicationComponent.name) &&
        Objects.equals(this.outputs, modelApplicationComponent.outputs) &&
        Objects.equals(this.properties, modelApplicationComponent.properties) &&
        Objects.equals(this.scopes, modelApplicationComponent.scopes) &&
        Objects.equals(this.traits, modelApplicationComponent.traits) &&
        Objects.equals(this.type, modelApplicationComponent.type) &&
        Objects.equals(this.updateTime, modelApplicationComponent.updateTime) &&
        Objects.equals(this.workloadType, modelApplicationComponent.workloadType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, appPrimaryKey, createTime, creator, dependsOn, description, externalRevision, icon, inputs, labels, main, name, outputs, properties, scopes, traits, type, updateTime, workloadType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ModelApplicationComponent {\n");
    
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    appPrimaryKey: ").append(toIndentedString(appPrimaryKey)).append("\n");
    sb.append("    createTime: ").append(toIndentedString(createTime)).append("\n");
    sb.append("    creator: ").append(toIndentedString(creator)).append("\n");
    sb.append("    dependsOn: ").append(toIndentedString(dependsOn)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    externalRevision: ").append(toIndentedString(externalRevision)).append("\n");
    sb.append("    icon: ").append(toIndentedString(icon)).append("\n");
    sb.append("    inputs: ").append(toIndentedString(inputs)).append("\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
    sb.append("    main: ").append(toIndentedString(main)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    outputs: ").append(toIndentedString(outputs)).append("\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
    sb.append("    scopes: ").append(toIndentedString(scopes)).append("\n");
    sb.append("    traits: ").append(toIndentedString(traits)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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

