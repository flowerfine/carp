package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;

import cn.sliew.carp.module.application.vela.api.v1.model.common.CommonWorkloadTypeDescriptor;
import cn.sliew.carp.module.application.vela.api.v1.model.common.ModelApplicationTrait;
import cn.sliew.carp.module.application.vela.api.v1.model.common.ModelJSONStruct;
import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1alpha1InputItem;
import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1alpha1OutputItem;
import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1beta1ComponentDefinitionSpec;
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
 * V1DetailComponentResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1DetailComponentResponse   {
  @JsonProperty("alias")
  private String alias = null;

  @JsonProperty("appPrimaryKey")
  private String appPrimaryKey = null;

  @JsonProperty("createTime")
  private OffsetDateTime createTime = null;

  @JsonProperty("creator")
  private String creator = null;

  @JsonProperty("definition")
  private V1beta1ComponentDefinitionSpec definition = null;

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

  public V1DetailComponentResponse alias(String alias) {
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

  public V1DetailComponentResponse appPrimaryKey(String appPrimaryKey) {
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

  public V1DetailComponentResponse createTime(OffsetDateTime createTime) {
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

  public V1DetailComponentResponse creator(String creator) {
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

  public V1DetailComponentResponse definition(V1beta1ComponentDefinitionSpec definition) {
    this.definition = definition;
    return this;
  }

  /**
   * Get definition
   * @return definition
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public V1beta1ComponentDefinitionSpec getDefinition() {
    return definition;
  }

  public void setDefinition(V1beta1ComponentDefinitionSpec definition) {
    this.definition = definition;
  }

  public V1DetailComponentResponse dependsOn(List<String> dependsOn) {
    this.dependsOn = dependsOn;
    return this;
  }

  public V1DetailComponentResponse addDependsOnItem(String dependsOnItem) {
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

  public V1DetailComponentResponse description(String description) {
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

  public V1DetailComponentResponse externalRevision(String externalRevision) {
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

  public V1DetailComponentResponse icon(String icon) {
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

  public V1DetailComponentResponse inputs(List<V1alpha1InputItem> inputs) {
    this.inputs = inputs;
    return this;
  }

  public V1DetailComponentResponse addInputsItem(V1alpha1InputItem inputsItem) {
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

  public V1DetailComponentResponse labels(Map<String, String> labels) {
    this.labels = labels;
    return this;
  }

  public V1DetailComponentResponse putLabelsItem(String key, String labelsItem) {
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

  public V1DetailComponentResponse main(Boolean main) {
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

  public V1DetailComponentResponse name(String name) {
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

  public V1DetailComponentResponse outputs(List<V1alpha1OutputItem> outputs) {
    this.outputs = outputs;
    return this;
  }

  public V1DetailComponentResponse addOutputsItem(V1alpha1OutputItem outputsItem) {
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

  public V1DetailComponentResponse properties(ModelJSONStruct properties) {
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

  public V1DetailComponentResponse scopes(Map<String, String> scopes) {
    this.scopes = scopes;
    return this;
  }

  public V1DetailComponentResponse putScopesItem(String key, String scopesItem) {
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

  public V1DetailComponentResponse traits(List<ModelApplicationTrait> traits) {
    this.traits = traits;
    return this;
  }

  public V1DetailComponentResponse addTraitsItem(ModelApplicationTrait traitsItem) {
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

  public V1DetailComponentResponse type(String type) {
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

  public V1DetailComponentResponse updateTime(OffsetDateTime updateTime) {
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

  public V1DetailComponentResponse workloadType(CommonWorkloadTypeDescriptor workloadType) {
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
    V1DetailComponentResponse v1DetailComponentResponse = (V1DetailComponentResponse) o;
    return Objects.equals(this.alias, v1DetailComponentResponse.alias) &&
        Objects.equals(this.appPrimaryKey, v1DetailComponentResponse.appPrimaryKey) &&
        Objects.equals(this.createTime, v1DetailComponentResponse.createTime) &&
        Objects.equals(this.creator, v1DetailComponentResponse.creator) &&
        Objects.equals(this.definition, v1DetailComponentResponse.definition) &&
        Objects.equals(this.dependsOn, v1DetailComponentResponse.dependsOn) &&
        Objects.equals(this.description, v1DetailComponentResponse.description) &&
        Objects.equals(this.externalRevision, v1DetailComponentResponse.externalRevision) &&
        Objects.equals(this.icon, v1DetailComponentResponse.icon) &&
        Objects.equals(this.inputs, v1DetailComponentResponse.inputs) &&
        Objects.equals(this.labels, v1DetailComponentResponse.labels) &&
        Objects.equals(this.main, v1DetailComponentResponse.main) &&
        Objects.equals(this.name, v1DetailComponentResponse.name) &&
        Objects.equals(this.outputs, v1DetailComponentResponse.outputs) &&
        Objects.equals(this.properties, v1DetailComponentResponse.properties) &&
        Objects.equals(this.scopes, v1DetailComponentResponse.scopes) &&
        Objects.equals(this.traits, v1DetailComponentResponse.traits) &&
        Objects.equals(this.type, v1DetailComponentResponse.type) &&
        Objects.equals(this.updateTime, v1DetailComponentResponse.updateTime) &&
        Objects.equals(this.workloadType, v1DetailComponentResponse.workloadType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, appPrimaryKey, createTime, creator, definition, dependsOn, description, externalRevision, icon, inputs, labels, main, name, outputs, properties, scopes, traits, type, updateTime, workloadType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1DetailComponentResponse {\n");
    
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    appPrimaryKey: ").append(toIndentedString(appPrimaryKey)).append("\n");
    sb.append("    createTime: ").append(toIndentedString(createTime)).append("\n");
    sb.append("    creator: ").append(toIndentedString(creator)).append("\n");
    sb.append("    definition: ").append(toIndentedString(definition)).append("\n");
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

