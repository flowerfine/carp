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
 * TypesJSONData
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class TypesJSONData   {
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

  @JsonProperty("category")
  private String category = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("includes")
  @Valid
  private List<TypesIncludes> includes = new ArrayList<TypesIncludes>();

  @JsonProperty("info")
  private TypesInfo info = null;

  @JsonProperty("kubePermissions")
  @Valid
  private List<V1PolicyRule> kubePermissions = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("proxy")
  private Boolean proxy = null;

  @JsonProperty("requirement")
  private TypesRequirement requirement = null;

  @JsonProperty("routes")
  @Valid
  private List<TypesRoute> routes = null;

  @JsonProperty("subType")
  private String subType = null;

  @JsonProperty("type")
  private String type = null;

  public TypesJSONData authSecret(TypesKubernetesSecret authSecret) {
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

  public TypesJSONData authType(String authType) {
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

  public TypesJSONData backend(Boolean backend) {
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

  public TypesJSONData backendService(TypesKubernetesService backendService) {
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

  public TypesJSONData backendType(String backendType) {
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

  public TypesJSONData category(String category) {
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

  public TypesJSONData id(String id) {
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

  public TypesJSONData includes(List<TypesIncludes> includes) {
    this.includes = includes;
    return this;
  }

  public TypesJSONData addIncludesItem(TypesIncludes includesItem) {
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

  public TypesJSONData info(TypesInfo info) {
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

  public TypesJSONData kubePermissions(List<V1PolicyRule> kubePermissions) {
    this.kubePermissions = kubePermissions;
    return this;
  }

  public TypesJSONData addKubePermissionsItem(V1PolicyRule kubePermissionsItem) {
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

  public TypesJSONData name(String name) {
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

  public TypesJSONData proxy(Boolean proxy) {
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

  public TypesJSONData requirement(TypesRequirement requirement) {
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

  public TypesJSONData routes(List<TypesRoute> routes) {
    this.routes = routes;
    return this;
  }

  public TypesJSONData addRoutesItem(TypesRoute routesItem) {
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

  public TypesJSONData subType(String subType) {
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

  public TypesJSONData type(String type) {
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
    TypesJSONData typesJSONData = (TypesJSONData) o;
    return Objects.equals(this.authSecret, typesJSONData.authSecret) &&
        Objects.equals(this.authType, typesJSONData.authType) &&
        Objects.equals(this.backend, typesJSONData.backend) &&
        Objects.equals(this.backendService, typesJSONData.backendService) &&
        Objects.equals(this.backendType, typesJSONData.backendType) &&
        Objects.equals(this.category, typesJSONData.category) &&
        Objects.equals(this.id, typesJSONData.id) &&
        Objects.equals(this.includes, typesJSONData.includes) &&
        Objects.equals(this.info, typesJSONData.info) &&
        Objects.equals(this.kubePermissions, typesJSONData.kubePermissions) &&
        Objects.equals(this.name, typesJSONData.name) &&
        Objects.equals(this.proxy, typesJSONData.proxy) &&
        Objects.equals(this.requirement, typesJSONData.requirement) &&
        Objects.equals(this.routes, typesJSONData.routes) &&
        Objects.equals(this.subType, typesJSONData.subType) &&
        Objects.equals(this.type, typesJSONData.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(authSecret, authType, backend, backendService, backendType, category, id, includes, info, kubePermissions, name, proxy, requirement, routes, subType, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TypesJSONData {\n");
    
    sb.append("    authSecret: ").append(toIndentedString(authSecret)).append("\n");
    sb.append("    authType: ").append(toIndentedString(authType)).append("\n");
    sb.append("    backend: ").append(toIndentedString(backend)).append("\n");
    sb.append("    backendService: ").append(toIndentedString(backendService)).append("\n");
    sb.append("    backendType: ").append(toIndentedString(backendType)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    includes: ").append(toIndentedString(includes)).append("\n");
    sb.append("    info: ").append(toIndentedString(info)).append("\n");
    sb.append("    kubePermissions: ").append(toIndentedString(kubePermissions)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    proxy: ").append(toIndentedString(proxy)).append("\n");
    sb.append("    requirement: ").append(toIndentedString(requirement)).append("\n");
    sb.append("    routes: ").append(toIndentedString(routes)).append("\n");
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

