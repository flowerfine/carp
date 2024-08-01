package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1EnvBindingBase
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1EnvBindingBase   {
  @JsonProperty("alias")
  private String alias = null;

  @JsonProperty("appDeployName")
  private String appDeployName = null;

  @JsonProperty("appDeployNamespace")
  private String appDeployNamespace = null;

  @JsonProperty("componentSelector")
  private V1ComponentSelector componentSelector = null;

  @JsonProperty("createTime")
  private OffsetDateTime createTime = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("targetNames")
  @Valid
  private List<String> targetNames = new ArrayList<String>();

  @JsonProperty("targets")
  @Valid
  private List<V1EnvBindingTarget> targets = null;

  @JsonProperty("updateTime")
  private OffsetDateTime updateTime = null;

  @JsonProperty("workflow")
  private V1NameAlias workflow = null;

  public V1EnvBindingBase alias(String alias) {
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

  public V1EnvBindingBase appDeployName(String appDeployName) {
    this.appDeployName = appDeployName;
    return this;
  }

  /**
   * Get appDeployName
   * @return appDeployName
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getAppDeployName() {
    return appDeployName;
  }

  public void setAppDeployName(String appDeployName) {
    this.appDeployName = appDeployName;
  }

  public V1EnvBindingBase appDeployNamespace(String appDeployNamespace) {
    this.appDeployNamespace = appDeployNamespace;
    return this;
  }

  /**
   * Get appDeployNamespace
   * @return appDeployNamespace
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getAppDeployNamespace() {
    return appDeployNamespace;
  }

  public void setAppDeployNamespace(String appDeployNamespace) {
    this.appDeployNamespace = appDeployNamespace;
  }

  public V1EnvBindingBase componentSelector(V1ComponentSelector componentSelector) {
    this.componentSelector = componentSelector;
    return this;
  }

  /**
   * Get componentSelector
   * @return componentSelector
  **/
  @ApiModelProperty(value = "")

  @Valid

  public V1ComponentSelector getComponentSelector() {
    return componentSelector;
  }

  public void setComponentSelector(V1ComponentSelector componentSelector) {
    this.componentSelector = componentSelector;
  }

  public V1EnvBindingBase createTime(OffsetDateTime createTime) {
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

  public V1EnvBindingBase description(String description) {
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

  public V1EnvBindingBase name(String name) {
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

  public V1EnvBindingBase targetNames(List<String> targetNames) {
    this.targetNames = targetNames;
    return this;
  }

  public V1EnvBindingBase addTargetNamesItem(String targetNamesItem) {
    this.targetNames.add(targetNamesItem);
    return this;
  }

  /**
   * Get targetNames
   * @return targetNames
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public List<String> getTargetNames() {
    return targetNames;
  }

  public void setTargetNames(List<String> targetNames) {
    this.targetNames = targetNames;
  }

  public V1EnvBindingBase targets(List<V1EnvBindingTarget> targets) {
    this.targets = targets;
    return this;
  }

  public V1EnvBindingBase addTargetsItem(V1EnvBindingTarget targetsItem) {
    if (this.targets == null) {
      this.targets = new ArrayList<V1EnvBindingTarget>();
    }
    this.targets.add(targetsItem);
    return this;
  }

  /**
   * Get targets
   * @return targets
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<V1EnvBindingTarget> getTargets() {
    return targets;
  }

  public void setTargets(List<V1EnvBindingTarget> targets) {
    this.targets = targets;
  }

  public V1EnvBindingBase updateTime(OffsetDateTime updateTime) {
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

  public V1EnvBindingBase workflow(V1NameAlias workflow) {
    this.workflow = workflow;
    return this;
  }

  /**
   * Get workflow
   * @return workflow
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public V1NameAlias getWorkflow() {
    return workflow;
  }

  public void setWorkflow(V1NameAlias workflow) {
    this.workflow = workflow;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1EnvBindingBase v1EnvBindingBase = (V1EnvBindingBase) o;
    return Objects.equals(this.alias, v1EnvBindingBase.alias) &&
        Objects.equals(this.appDeployName, v1EnvBindingBase.appDeployName) &&
        Objects.equals(this.appDeployNamespace, v1EnvBindingBase.appDeployNamespace) &&
        Objects.equals(this.componentSelector, v1EnvBindingBase.componentSelector) &&
        Objects.equals(this.createTime, v1EnvBindingBase.createTime) &&
        Objects.equals(this.description, v1EnvBindingBase.description) &&
        Objects.equals(this.name, v1EnvBindingBase.name) &&
        Objects.equals(this.targetNames, v1EnvBindingBase.targetNames) &&
        Objects.equals(this.targets, v1EnvBindingBase.targets) &&
        Objects.equals(this.updateTime, v1EnvBindingBase.updateTime) &&
        Objects.equals(this.workflow, v1EnvBindingBase.workflow);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, appDeployName, appDeployNamespace, componentSelector, createTime, description, name, targetNames, targets, updateTime, workflow);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1EnvBindingBase {\n");
    
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    appDeployName: ").append(toIndentedString(appDeployName)).append("\n");
    sb.append("    appDeployNamespace: ").append(toIndentedString(appDeployNamespace)).append("\n");
    sb.append("    componentSelector: ").append(toIndentedString(componentSelector)).append("\n");
    sb.append("    createTime: ").append(toIndentedString(createTime)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    targetNames: ").append(toIndentedString(targetNames)).append("\n");
    sb.append("    targets: ").append(toIndentedString(targets)).append("\n");
    sb.append("    updateTime: ").append(toIndentedString(updateTime)).append("\n");
    sb.append("    workflow: ").append(toIndentedString(workflow)).append("\n");
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

