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
 * V1PluginDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1PluginDTO   {
  @JsonProperty("baseURL")
  private String baseURL = null;

  @JsonProperty("category")
  private String category = null;

  @JsonProperty("defaultNavURL")
  private String defaultNavURL = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("includes")
  @Valid
  private List<TypesIncludes> includes = new ArrayList<TypesIncludes>();

  @JsonProperty("info")
  private TypesInfo info = null;

  @JsonProperty("module")
  private String module = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("subType")
  private String subType = null;

  @JsonProperty("type")
  private String type = null;

  public V1PluginDTO baseURL(String baseURL) {
    this.baseURL = baseURL;
    return this;
  }

  /**
   * Get baseURL
   * @return baseURL
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getBaseURL() {
    return baseURL;
  }

  public void setBaseURL(String baseURL) {
    this.baseURL = baseURL;
  }

  public V1PluginDTO category(String category) {
    this.category = category;
    return this;
  }

  /**
   * Get category
   * @return category
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public V1PluginDTO defaultNavURL(String defaultNavURL) {
    this.defaultNavURL = defaultNavURL;
    return this;
  }

  /**
   * Get defaultNavURL
   * @return defaultNavURL
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getDefaultNavURL() {
    return defaultNavURL;
  }

  public void setDefaultNavURL(String defaultNavURL) {
    this.defaultNavURL = defaultNavURL;
  }

  public V1PluginDTO id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public V1PluginDTO includes(List<TypesIncludes> includes) {
    this.includes = includes;
    return this;
  }

  public V1PluginDTO addIncludesItem(TypesIncludes includesItem) {
    this.includes.add(includesItem);
    return this;
  }

  /**
   * Get includes
   * @return includes
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<TypesIncludes> getIncludes() {
    return includes;
  }

  public void setIncludes(List<TypesIncludes> includes) {
    this.includes = includes;
  }

  public V1PluginDTO info(TypesInfo info) {
    this.info = info;
    return this;
  }

  /**
   * Get info
   * @return info
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public TypesInfo getInfo() {
    return info;
  }

  public void setInfo(TypesInfo info) {
    this.info = info;
  }

  public V1PluginDTO module(String module) {
    this.module = module;
    return this;
  }

  /**
   * Get module
   * @return module
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  public V1PluginDTO name(String name) {
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

  public V1PluginDTO subType(String subType) {
    this.subType = subType;
    return this;
  }

  /**
   * Get subType
   * @return subType
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getSubType() {
    return subType;
  }

  public void setSubType(String subType) {
    this.subType = subType;
  }

  public V1PluginDTO type(String type) {
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
    V1PluginDTO v1PluginDTO = (V1PluginDTO) o;
    return Objects.equals(this.baseURL, v1PluginDTO.baseURL) &&
        Objects.equals(this.category, v1PluginDTO.category) &&
        Objects.equals(this.defaultNavURL, v1PluginDTO.defaultNavURL) &&
        Objects.equals(this.id, v1PluginDTO.id) &&
        Objects.equals(this.includes, v1PluginDTO.includes) &&
        Objects.equals(this.info, v1PluginDTO.info) &&
        Objects.equals(this.module, v1PluginDTO.module) &&
        Objects.equals(this.name, v1PluginDTO.name) &&
        Objects.equals(this.subType, v1PluginDTO.subType) &&
        Objects.equals(this.type, v1PluginDTO.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(baseURL, category, defaultNavURL, id, includes, info, module, name, subType, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1PluginDTO {\n");
    
    sb.append("    baseURL: ").append(toIndentedString(baseURL)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
    sb.append("    defaultNavURL: ").append(toIndentedString(defaultNavURL)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    includes: ").append(toIndentedString(includes)).append("\n");
    sb.append("    info: ").append(toIndentedString(info)).append("\n");
    sb.append("    module: ").append(toIndentedString(module)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    subType: ").append(toIndentedString(subType)).append("\n");
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

