package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;

import cn.sliew.carp.module.application.vela.api.v1.model.common.ModelJSONStruct;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1PolicyBase
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1PolicyBase   {
  @JsonProperty("alias")
  private String alias = null;

  @JsonProperty("createTime")
  private OffsetDateTime createTime = null;

  @JsonProperty("creator")
  private String creator = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("envName")
  private String envName = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("properties")
  private ModelJSONStruct properties = null;

  @JsonProperty("type")
  private String type = null;

  @JsonProperty("updateTime")
  private OffsetDateTime updateTime = null;

  public V1PolicyBase alias(String alias) {
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

  public V1PolicyBase createTime(OffsetDateTime createTime) {
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

  public V1PolicyBase creator(String creator) {
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

  public V1PolicyBase description(String description) {
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

  public V1PolicyBase envName(String envName) {
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

  public V1PolicyBase name(String name) {
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

  public V1PolicyBase properties(ModelJSONStruct properties) {
    this.properties = properties;
    return this;
  }

  /**
   * Get properties
   * @return properties
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public ModelJSONStruct getProperties() {
    return properties;
  }

  public void setProperties(ModelJSONStruct properties) {
    this.properties = properties;
  }

  public V1PolicyBase type(String type) {
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

  public V1PolicyBase updateTime(OffsetDateTime updateTime) {
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
    V1PolicyBase v1PolicyBase = (V1PolicyBase) o;
    return Objects.equals(this.alias, v1PolicyBase.alias) &&
        Objects.equals(this.createTime, v1PolicyBase.createTime) &&
        Objects.equals(this.creator, v1PolicyBase.creator) &&
        Objects.equals(this.description, v1PolicyBase.description) &&
        Objects.equals(this.envName, v1PolicyBase.envName) &&
        Objects.equals(this.name, v1PolicyBase.name) &&
        Objects.equals(this.properties, v1PolicyBase.properties) &&
        Objects.equals(this.type, v1PolicyBase.type) &&
        Objects.equals(this.updateTime, v1PolicyBase.updateTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, createTime, creator, description, envName, name, properties, type, updateTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1PolicyBase {\n");
    
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    createTime: ").append(toIndentedString(createTime)).append("\n");
    sb.append("    creator: ").append(toIndentedString(creator)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    envName: ").append(toIndentedString(envName)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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

