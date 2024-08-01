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
 * TypesIncludes
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class TypesIncludes   {
  @JsonProperty("catalog")
  private String catalog = null;

  @JsonProperty("icon")
  private String icon = null;

  @JsonProperty("label")
  private String label = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("permission")
  private TypesPermission permission = null;

  @JsonProperty("relatedRoute")
  @Valid
  private List<String> relatedRoute = new ArrayList<String>();

  @JsonProperty("to")
  private String to = null;

  @JsonProperty("type")
  private String type = null;

  @JsonProperty("workspace")
  private TypesWorkspace workspace = null;

  public TypesIncludes catalog(String catalog) {
    this.catalog = catalog;
    return this;
  }

  /**
   * Get catalog
   * @return catalog
  **/
  @ApiModelProperty(value = "")


  public String getCatalog() {
    return catalog;
  }

  public void setCatalog(String catalog) {
    this.catalog = catalog;
  }

  public TypesIncludes icon(String icon) {
    this.icon = icon;
    return this;
  }

  /**
   * Get icon
   * @return icon
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public TypesIncludes label(String label) {
    this.label = label;
    return this;
  }

  /**
   * Get label
   * @return label
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public TypesIncludes name(String name) {
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

  public TypesIncludes permission(TypesPermission permission) {
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

  public TypesIncludes relatedRoute(List<String> relatedRoute) {
    this.relatedRoute = relatedRoute;
    return this;
  }

  public TypesIncludes addRelatedRouteItem(String relatedRouteItem) {
    this.relatedRoute.add(relatedRouteItem);
    return this;
  }

  /**
   * Get relatedRoute
   * @return relatedRoute
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public List<String> getRelatedRoute() {
    return relatedRoute;
  }

  public void setRelatedRoute(List<String> relatedRoute) {
    this.relatedRoute = relatedRoute;
  }

  public TypesIncludes to(String to) {
    this.to = to;
    return this;
  }

  /**
   * Get to
   * @return to
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public TypesIncludes type(String type) {
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

  public TypesIncludes workspace(TypesWorkspace workspace) {
    this.workspace = workspace;
    return this;
  }

  /**
   * Get workspace
   * @return workspace
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public TypesWorkspace getWorkspace() {
    return workspace;
  }

  public void setWorkspace(TypesWorkspace workspace) {
    this.workspace = workspace;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TypesIncludes typesIncludes = (TypesIncludes) o;
    return Objects.equals(this.catalog, typesIncludes.catalog) &&
        Objects.equals(this.icon, typesIncludes.icon) &&
        Objects.equals(this.label, typesIncludes.label) &&
        Objects.equals(this.name, typesIncludes.name) &&
        Objects.equals(this.permission, typesIncludes.permission) &&
        Objects.equals(this.relatedRoute, typesIncludes.relatedRoute) &&
        Objects.equals(this.to, typesIncludes.to) &&
        Objects.equals(this.type, typesIncludes.type) &&
        Objects.equals(this.workspace, typesIncludes.workspace);
  }

  @Override
  public int hashCode() {
    return Objects.hash(catalog, icon, label, name, permission, relatedRoute, to, type, workspace);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TypesIncludes {\n");
    
    sb.append("    catalog: ").append(toIndentedString(catalog)).append("\n");
    sb.append("    icon: ").append(toIndentedString(icon)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    permission: ").append(toIndentedString(permission)).append("\n");
    sb.append("    relatedRoute: ").append(toIndentedString(relatedRoute)).append("\n");
    sb.append("    to: ").append(toIndentedString(to)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    workspace: ").append(toIndentedString(workspace)).append("\n");
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

