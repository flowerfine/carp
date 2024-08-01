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
 * V1PermissionBase
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1PermissionBase   {
  @JsonProperty("actions")
  @Valid
  private List<String> actions = new ArrayList<String>();

  @JsonProperty("alias")
  private String alias = null;

  @JsonProperty("createTime")
  private OffsetDateTime createTime = null;

  @JsonProperty("effect")
  private String effect = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("resources")
  @Valid
  private List<String> resources = new ArrayList<String>();

  @JsonProperty("updateTime")
  private OffsetDateTime updateTime = null;

  public V1PermissionBase actions(List<String> actions) {
    this.actions = actions;
    return this;
  }

  public V1PermissionBase addActionsItem(String actionsItem) {
    this.actions.add(actionsItem);
    return this;
  }

  /**
   * Get actions
   * @return actions
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public List<String> getActions() {
    return actions;
  }

  public void setActions(List<String> actions) {
    this.actions = actions;
  }

  public V1PermissionBase alias(String alias) {
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

  public V1PermissionBase createTime(OffsetDateTime createTime) {
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

  public V1PermissionBase effect(String effect) {
    this.effect = effect;
    return this;
  }

  /**
   * Get effect
   * @return effect
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getEffect() {
    return effect;
  }

  public void setEffect(String effect) {
    this.effect = effect;
  }

  public V1PermissionBase name(String name) {
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

  public V1PermissionBase resources(List<String> resources) {
    this.resources = resources;
    return this;
  }

  public V1PermissionBase addResourcesItem(String resourcesItem) {
    this.resources.add(resourcesItem);
    return this;
  }

  /**
   * Get resources
   * @return resources
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public List<String> getResources() {
    return resources;
  }

  public void setResources(List<String> resources) {
    this.resources = resources;
  }

  public V1PermissionBase updateTime(OffsetDateTime updateTime) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1PermissionBase v1PermissionBase = (V1PermissionBase) o;
    return Objects.equals(this.actions, v1PermissionBase.actions) &&
        Objects.equals(this.alias, v1PermissionBase.alias) &&
        Objects.equals(this.createTime, v1PermissionBase.createTime) &&
        Objects.equals(this.effect, v1PermissionBase.effect) &&
        Objects.equals(this.name, v1PermissionBase.name) &&
        Objects.equals(this.resources, v1PermissionBase.resources) &&
        Objects.equals(this.updateTime, v1PermissionBase.updateTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(actions, alias, createTime, effect, name, resources, updateTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1PermissionBase {\n");
    
    sb.append("    actions: ").append(toIndentedString(actions)).append("\n");
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    createTime: ").append(toIndentedString(createTime)).append("\n");
    sb.append("    effect: ").append(toIndentedString(effect)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    resources: ").append(toIndentedString(resources)).append("\n");
    sb.append("    updateTime: ").append(toIndentedString(updateTime)).append("\n");
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

