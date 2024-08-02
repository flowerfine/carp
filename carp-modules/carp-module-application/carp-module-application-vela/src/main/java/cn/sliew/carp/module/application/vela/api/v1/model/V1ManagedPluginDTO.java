package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;

import cn.sliew.carp.module.application.vela.api.v1.model.common.*;
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
 * V1ManagedPluginDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ManagedPluginDTO   {
  @JsonProperty("authSecret")
  private TypesKubernetesSecret authSecret = null;

  @JsonProperty("authType")
  private String authType = null;

  @JsonProperty("backend")
  private Boolean backend = null;

  @JsonProperty("backendService")
  private TypesKubernetesService backendService = null;

  @JsonProperty("backendType")
  private String backendType = null;

  @JsonProperty("baseURL")
  private String baseURL = null;

  @JsonProperty("category")
  private String category = null;

  @JsonProperty("class")
  private String propertyClass = null;

  @JsonProperty("defaultNavURL")
  private String defaultNavURL = null;

  @JsonProperty("enabled")
  private Boolean enabled = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("includes")
  @Valid
  private List<TypesIncludes> includes = new ArrayList<TypesIncludes>();

  @JsonProperty("info")
  private TypesInfo info = null;

  @JsonProperty("jsonSetting")
  private Object jsonSetting = null;

  @JsonProperty("kubePermissions")
  @Valid
  private List<V1PolicyRule> kubePermissions = null;

  @JsonProperty("module")
  private String module = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("proxy")
  private Boolean proxy = null;

  @JsonProperty("requirement")
  private TypesRequirement requirement = null;

  @JsonProperty("routes")
  @Valid
  private List<TypesRoute> routes = null;

  @JsonProperty("secureJsonFields")
  @Valid
  private Map<String, Boolean> secureJsonFields = new HashMap<String, Boolean>();

  @JsonProperty("subType")
  private String subType = null;

  @JsonProperty("type")
  private String type = null;

  public V1ManagedPluginDTO authSecret(TypesKubernetesSecret authSecret) {
    this.authSecret = authSecret;
    return this;
  }

  /**
   * Get authSecret
   * @return authSecret
  **/
  @ApiModelProperty(value = "")

  @Valid

  public TypesKubernetesSecret getAuthSecret() {
    return authSecret;
  }

  public void setAuthSecret(TypesKubernetesSecret authSecret) {
    this.authSecret = authSecret;
  }

  public V1ManagedPluginDTO authType(String authType) {
    this.authType = authType;
    return this;
  }

  /**
   * Get authType
   * @return authType
  **/
  @ApiModelProperty(value = "")


  public String getAuthType() {
    return authType;
  }

  public void setAuthType(String authType) {
    this.authType = authType;
  }

  public V1ManagedPluginDTO backend(Boolean backend) {
    this.backend = backend;
    return this;
  }

  /**
   * Get backend
   * @return backend
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Boolean isBackend() {
    return backend;
  }

  public void setBackend(Boolean backend) {
    this.backend = backend;
  }

  public V1ManagedPluginDTO backendService(TypesKubernetesService backendService) {
    this.backendService = backendService;
    return this;
  }

  /**
   * Get backendService
   * @return backendService
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public TypesKubernetesService getBackendService() {
    return backendService;
  }

  public void setBackendService(TypesKubernetesService backendService) {
    this.backendService = backendService;
  }

  public V1ManagedPluginDTO backendType(String backendType) {
    this.backendType = backendType;
    return this;
  }

  /**
   * Get backendType
   * @return backendType
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getBackendType() {
    return backendType;
  }

  public void setBackendType(String backendType) {
    this.backendType = backendType;
  }

  public V1ManagedPluginDTO baseURL(String baseURL) {
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

  public V1ManagedPluginDTO category(String category) {
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

  public V1ManagedPluginDTO propertyClass(String propertyClass) {
    this.propertyClass = propertyClass;
    return this;
  }

  /**
   * Get propertyClass
   * @return propertyClass
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getPropertyClass() {
    return propertyClass;
  }

  public void setPropertyClass(String propertyClass) {
    this.propertyClass = propertyClass;
  }

  public V1ManagedPluginDTO defaultNavURL(String defaultNavURL) {
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

  public V1ManagedPluginDTO enabled(Boolean enabled) {
    this.enabled = enabled;
    return this;
  }

  /**
   * Get enabled
   * @return enabled
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public V1ManagedPluginDTO id(String id) {
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

  public V1ManagedPluginDTO includes(List<TypesIncludes> includes) {
    this.includes = includes;
    return this;
  }

  public V1ManagedPluginDTO addIncludesItem(TypesIncludes includesItem) {
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

  public V1ManagedPluginDTO info(TypesInfo info) {
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

  public V1ManagedPluginDTO jsonSetting(Object jsonSetting) {
    this.jsonSetting = jsonSetting;
    return this;
  }

  /**
   * Get jsonSetting
   * @return jsonSetting
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Object getJsonSetting() {
    return jsonSetting;
  }

  public void setJsonSetting(Object jsonSetting) {
    this.jsonSetting = jsonSetting;
  }

  public V1ManagedPluginDTO kubePermissions(List<V1PolicyRule> kubePermissions) {
    this.kubePermissions = kubePermissions;
    return this;
  }

  public V1ManagedPluginDTO addKubePermissionsItem(V1PolicyRule kubePermissionsItem) {
    if (this.kubePermissions == null) {
      this.kubePermissions = new ArrayList<V1PolicyRule>();
    }
    this.kubePermissions.add(kubePermissionsItem);
    return this;
  }

  /**
   * Get kubePermissions
   * @return kubePermissions
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<V1PolicyRule> getKubePermissions() {
    return kubePermissions;
  }

  public void setKubePermissions(List<V1PolicyRule> kubePermissions) {
    this.kubePermissions = kubePermissions;
  }

  public V1ManagedPluginDTO module(String module) {
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

  public V1ManagedPluginDTO name(String name) {
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

  public V1ManagedPluginDTO proxy(Boolean proxy) {
    this.proxy = proxy;
    return this;
  }

  /**
   * Get proxy
   * @return proxy
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Boolean isProxy() {
    return proxy;
  }

  public void setProxy(Boolean proxy) {
    this.proxy = proxy;
  }

  public V1ManagedPluginDTO requirement(TypesRequirement requirement) {
    this.requirement = requirement;
    return this;
  }

  /**
   * Get requirement
   * @return requirement
  **/
  @ApiModelProperty(value = "")

  @Valid

  public TypesRequirement getRequirement() {
    return requirement;
  }

  public void setRequirement(TypesRequirement requirement) {
    this.requirement = requirement;
  }

  public V1ManagedPluginDTO routes(List<TypesRoute> routes) {
    this.routes = routes;
    return this;
  }

  public V1ManagedPluginDTO addRoutesItem(TypesRoute routesItem) {
    if (this.routes == null) {
      this.routes = new ArrayList<TypesRoute>();
    }
    this.routes.add(routesItem);
    return this;
  }

  /**
   * Get routes
   * @return routes
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<TypesRoute> getRoutes() {
    return routes;
  }

  public void setRoutes(List<TypesRoute> routes) {
    this.routes = routes;
  }

  public V1ManagedPluginDTO secureJsonFields(Map<String, Boolean> secureJsonFields) {
    this.secureJsonFields = secureJsonFields;
    return this;
  }

  public V1ManagedPluginDTO putSecureJsonFieldsItem(String key, Boolean secureJsonFieldsItem) {
    this.secureJsonFields.put(key, secureJsonFieldsItem);
    return this;
  }

  /**
   * Get secureJsonFields
   * @return secureJsonFields
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Map<String, Boolean> getSecureJsonFields() {
    return secureJsonFields;
  }

  public void setSecureJsonFields(Map<String, Boolean> secureJsonFields) {
    this.secureJsonFields = secureJsonFields;
  }

  public V1ManagedPluginDTO subType(String subType) {
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

  public V1ManagedPluginDTO type(String type) {
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
    V1ManagedPluginDTO v1ManagedPluginDTO = (V1ManagedPluginDTO) o;
    return Objects.equals(this.authSecret, v1ManagedPluginDTO.authSecret) &&
        Objects.equals(this.authType, v1ManagedPluginDTO.authType) &&
        Objects.equals(this.backend, v1ManagedPluginDTO.backend) &&
        Objects.equals(this.backendService, v1ManagedPluginDTO.backendService) &&
        Objects.equals(this.backendType, v1ManagedPluginDTO.backendType) &&
        Objects.equals(this.baseURL, v1ManagedPluginDTO.baseURL) &&
        Objects.equals(this.category, v1ManagedPluginDTO.category) &&
        Objects.equals(this.propertyClass, v1ManagedPluginDTO.propertyClass) &&
        Objects.equals(this.defaultNavURL, v1ManagedPluginDTO.defaultNavURL) &&
        Objects.equals(this.enabled, v1ManagedPluginDTO.enabled) &&
        Objects.equals(this.id, v1ManagedPluginDTO.id) &&
        Objects.equals(this.includes, v1ManagedPluginDTO.includes) &&
        Objects.equals(this.info, v1ManagedPluginDTO.info) &&
        Objects.equals(this.jsonSetting, v1ManagedPluginDTO.jsonSetting) &&
        Objects.equals(this.kubePermissions, v1ManagedPluginDTO.kubePermissions) &&
        Objects.equals(this.module, v1ManagedPluginDTO.module) &&
        Objects.equals(this.name, v1ManagedPluginDTO.name) &&
        Objects.equals(this.proxy, v1ManagedPluginDTO.proxy) &&
        Objects.equals(this.requirement, v1ManagedPluginDTO.requirement) &&
        Objects.equals(this.routes, v1ManagedPluginDTO.routes) &&
        Objects.equals(this.secureJsonFields, v1ManagedPluginDTO.secureJsonFields) &&
        Objects.equals(this.subType, v1ManagedPluginDTO.subType) &&
        Objects.equals(this.type, v1ManagedPluginDTO.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(authSecret, authType, backend, backendService, backendType, baseURL, category, propertyClass, defaultNavURL, enabled, id, includes, info, jsonSetting, kubePermissions, module, name, proxy, requirement, routes, secureJsonFields, subType, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ManagedPluginDTO {\n");
    
    sb.append("    authSecret: ").append(toIndentedString(authSecret)).append("\n");
    sb.append("    authType: ").append(toIndentedString(authType)).append("\n");
    sb.append("    backend: ").append(toIndentedString(backend)).append("\n");
    sb.append("    backendService: ").append(toIndentedString(backendService)).append("\n");
    sb.append("    backendType: ").append(toIndentedString(backendType)).append("\n");
    sb.append("    baseURL: ").append(toIndentedString(baseURL)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
    sb.append("    propertyClass: ").append(toIndentedString(propertyClass)).append("\n");
    sb.append("    defaultNavURL: ").append(toIndentedString(defaultNavURL)).append("\n");
    sb.append("    enabled: ").append(toIndentedString(enabled)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    includes: ").append(toIndentedString(includes)).append("\n");
    sb.append("    info: ").append(toIndentedString(info)).append("\n");
    sb.append("    jsonSetting: ").append(toIndentedString(jsonSetting)).append("\n");
    sb.append("    kubePermissions: ").append(toIndentedString(kubePermissions)).append("\n");
    sb.append("    module: ").append(toIndentedString(module)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    proxy: ").append(toIndentedString(proxy)).append("\n");
    sb.append("    requirement: ").append(toIndentedString(requirement)).append("\n");
    sb.append("    routes: ").append(toIndentedString(routes)).append("\n");
    sb.append("    secureJsonFields: ").append(toIndentedString(secureJsonFields)).append("\n");
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

