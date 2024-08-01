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
 * TypesRoute
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class TypesRoute   {
  @JsonProperty("headers")
  @Valid
  private List<TypesHeader> headers = null;

  @JsonProperty("method")
  private String method = null;

  @JsonProperty("path")
  private String path = null;

  @JsonProperty("permission")
  private TypesPermission permission = null;

  @JsonProperty("resourceMap")
  @Valid
  private Map<String, String> resourceMap = null;

  public TypesRoute headers(List<TypesHeader> headers) {
    this.headers = headers;
    return this;
  }

  public TypesRoute addHeadersItem(TypesHeader headersItem) {
    if (this.headers == null) {
      this.headers = new ArrayList<TypesHeader>();
    }
    this.headers.add(headersItem);
    return this;
  }

  /**
   * Get headers
   * @return headers
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<TypesHeader> getHeaders() {
    return headers;
  }

  public void setHeaders(List<TypesHeader> headers) {
    this.headers = headers;
  }

  public TypesRoute method(String method) {
    this.method = method;
    return this;
  }

  /**
   * Get method
   * @return method
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public TypesRoute path(String path) {
    this.path = path;
    return this;
  }

  /**
   * Get path
   * @return path
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public TypesRoute permission(TypesPermission permission) {
    this.permission = permission;
    return this;
  }

  /**
   * Get permission
   * @return permission
  **/
  @ApiModelProperty(value = "")

  @Valid

  public TypesPermission getPermission() {
    return permission;
  }

  public void setPermission(TypesPermission permission) {
    this.permission = permission;
  }

  public TypesRoute resourceMap(Map<String, String> resourceMap) {
    this.resourceMap = resourceMap;
    return this;
  }

  public TypesRoute putResourceMapItem(String key, String resourceMapItem) {
    if (this.resourceMap == null) {
      this.resourceMap = new HashMap<String, String>();
    }
    this.resourceMap.put(key, resourceMapItem);
    return this;
  }

  /**
   * Get resourceMap
   * @return resourceMap
  **/
  @ApiModelProperty(value = "")


  public Map<String, String> getResourceMap() {
    return resourceMap;
  }

  public void setResourceMap(Map<String, String> resourceMap) {
    this.resourceMap = resourceMap;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TypesRoute typesRoute = (TypesRoute) o;
    return Objects.equals(this.headers, typesRoute.headers) &&
        Objects.equals(this.method, typesRoute.method) &&
        Objects.equals(this.path, typesRoute.path) &&
        Objects.equals(this.permission, typesRoute.permission) &&
        Objects.equals(this.resourceMap, typesRoute.resourceMap);
  }

  @Override
  public int hashCode() {
    return Objects.hash(headers, method, path, permission, resourceMap);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TypesRoute {\n");
    
    sb.append("    headers: ").append(toIndentedString(headers)).append("\n");
    sb.append("    method: ").append(toIndentedString(method)).append("\n");
    sb.append("    path: ").append(toIndentedString(path)).append("\n");
    sb.append("    permission: ").append(toIndentedString(permission)).append("\n");
    sb.append("    resourceMap: ").append(toIndentedString(resourceMap)).append("\n");
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

