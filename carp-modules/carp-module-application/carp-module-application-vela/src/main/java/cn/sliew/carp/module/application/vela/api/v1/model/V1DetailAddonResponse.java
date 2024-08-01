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
 * V1DetailAddonResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1DetailAddonResponse   {
  @JsonProperty("annotations")
  @Valid
  private Map<String, String> annotations = null;

  @JsonProperty("availableVersions")
  @Valid
  private List<String> availableVersions = new ArrayList<String>();

  @JsonProperty("definitions")
  @Valid
  private List<V1AddonDefinition> definitions = new ArrayList<V1AddonDefinition>();

  @JsonProperty("dependencies")
  @Valid
  private List<AddonDependency> dependencies = null;

  @JsonProperty("deployTo")
  private AddonDeployTo deployTo = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("detail")
  private String detail = null;

  @JsonProperty("icon")
  private String icon = null;

  @JsonProperty("invisible")
  private Boolean invisible = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("needNamespace")
  @Valid
  private List<String> needNamespace = null;

  @JsonProperty("registryName")
  private String registryName = null;

  @JsonProperty("schema")
  private String schema = null;

  @JsonProperty("system")
  private AddonSystemRequirements system = null;

  @JsonProperty("tags")
  @Valid
  private List<String> tags = null;

  @JsonProperty("uiSchema")
  @Valid
  private List<SchemaUIParameter> uiSchema = new ArrayList<SchemaUIParameter>();

  @JsonProperty("url")
  private String url = null;

  @JsonProperty("uxPlugins")
  @Valid
  private Map<String, String> uxPlugins = null;

  @JsonProperty("version")
  private String version = null;

  public V1DetailAddonResponse annotations(Map<String, String> annotations) {
    this.annotations = annotations;
    return this;
  }

  public V1DetailAddonResponse putAnnotationsItem(String key, String annotationsItem) {
    if (this.annotations == null) {
      this.annotations = new HashMap<String, String>();
    }
    this.annotations.put(key, annotationsItem);
    return this;
  }

  /**
   * Get annotations
   * @return annotations
  **/
  @ApiModelProperty(value = "")


  public Map<String, String> getAnnotations() {
    return annotations;
  }

  public void setAnnotations(Map<String, String> annotations) {
    this.annotations = annotations;
  }

  public V1DetailAddonResponse availableVersions(List<String> availableVersions) {
    this.availableVersions = availableVersions;
    return this;
  }

  public V1DetailAddonResponse addAvailableVersionsItem(String availableVersionsItem) {
    this.availableVersions.add(availableVersionsItem);
    return this;
  }

  /**
   * Get availableVersions
   * @return availableVersions
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public List<String> getAvailableVersions() {
    return availableVersions;
  }

  public void setAvailableVersions(List<String> availableVersions) {
    this.availableVersions = availableVersions;
  }

  public V1DetailAddonResponse definitions(List<V1AddonDefinition> definitions) {
    this.definitions = definitions;
    return this;
  }

  public V1DetailAddonResponse addDefinitionsItem(V1AddonDefinition definitionsItem) {
    this.definitions.add(definitionsItem);
    return this;
  }

  /**
   * Get definitions
   * @return definitions
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1AddonDefinition> getDefinitions() {
    return definitions;
  }

  public void setDefinitions(List<V1AddonDefinition> definitions) {
    this.definitions = definitions;
  }

  public V1DetailAddonResponse dependencies(List<AddonDependency> dependencies) {
    this.dependencies = dependencies;
    return this;
  }

  public V1DetailAddonResponse addDependenciesItem(AddonDependency dependenciesItem) {
    if (this.dependencies == null) {
      this.dependencies = new ArrayList<AddonDependency>();
    }
    this.dependencies.add(dependenciesItem);
    return this;
  }

  /**
   * Get dependencies
   * @return dependencies
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<AddonDependency> getDependencies() {
    return dependencies;
  }

  public void setDependencies(List<AddonDependency> dependencies) {
    this.dependencies = dependencies;
  }

  public V1DetailAddonResponse deployTo(AddonDeployTo deployTo) {
    this.deployTo = deployTo;
    return this;
  }

  /**
   * Get deployTo
   * @return deployTo
  **/
  @ApiModelProperty(value = "")

  @Valid

  public AddonDeployTo getDeployTo() {
    return deployTo;
  }

  public void setDeployTo(AddonDeployTo deployTo) {
    this.deployTo = deployTo;
  }

  public V1DetailAddonResponse description(String description) {
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

  public V1DetailAddonResponse detail(String detail) {
    this.detail = detail;
    return this;
  }

  /**
   * Get detail
   * @return detail
  **/
  @ApiModelProperty(value = "")


  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public V1DetailAddonResponse icon(String icon) {
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

  public V1DetailAddonResponse invisible(Boolean invisible) {
    this.invisible = invisible;
    return this;
  }

  /**
   * Get invisible
   * @return invisible
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Boolean isInvisible() {
    return invisible;
  }

  public void setInvisible(Boolean invisible) {
    this.invisible = invisible;
  }

  public V1DetailAddonResponse name(String name) {
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

  public V1DetailAddonResponse needNamespace(List<String> needNamespace) {
    this.needNamespace = needNamespace;
    return this;
  }

  public V1DetailAddonResponse addNeedNamespaceItem(String needNamespaceItem) {
    if (this.needNamespace == null) {
      this.needNamespace = new ArrayList<String>();
    }
    this.needNamespace.add(needNamespaceItem);
    return this;
  }

  /**
   * Get needNamespace
   * @return needNamespace
  **/
  @ApiModelProperty(value = "")


  public List<String> getNeedNamespace() {
    return needNamespace;
  }

  public void setNeedNamespace(List<String> needNamespace) {
    this.needNamespace = needNamespace;
  }

  public V1DetailAddonResponse registryName(String registryName) {
    this.registryName = registryName;
    return this;
  }

  /**
   * Get registryName
   * @return registryName
  **/
  @ApiModelProperty(value = "")


  public String getRegistryName() {
    return registryName;
  }

  public void setRegistryName(String registryName) {
    this.registryName = registryName;
  }

  public V1DetailAddonResponse schema(String schema) {
    this.schema = schema;
    return this;
  }

  /**
   * Get schema
   * @return schema
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getSchema() {
    return schema;
  }

  public void setSchema(String schema) {
    this.schema = schema;
  }

  public V1DetailAddonResponse system(AddonSystemRequirements system) {
    this.system = system;
    return this;
  }

  /**
   * Get system
   * @return system
  **/
  @ApiModelProperty(value = "")

  @Valid

  public AddonSystemRequirements getSystem() {
    return system;
  }

  public void setSystem(AddonSystemRequirements system) {
    this.system = system;
  }

  public V1DetailAddonResponse tags(List<String> tags) {
    this.tags = tags;
    return this;
  }

  public V1DetailAddonResponse addTagsItem(String tagsItem) {
    if (this.tags == null) {
      this.tags = new ArrayList<String>();
    }
    this.tags.add(tagsItem);
    return this;
  }

  /**
   * Get tags
   * @return tags
  **/
  @ApiModelProperty(value = "")


  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public V1DetailAddonResponse uiSchema(List<SchemaUIParameter> uiSchema) {
    this.uiSchema = uiSchema;
    return this;
  }

  public V1DetailAddonResponse addUiSchemaItem(SchemaUIParameter uiSchemaItem) {
    this.uiSchema.add(uiSchemaItem);
    return this;
  }

  /**
   * Get uiSchema
   * @return uiSchema
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<SchemaUIParameter> getUiSchema() {
    return uiSchema;
  }

  public void setUiSchema(List<SchemaUIParameter> uiSchema) {
    this.uiSchema = uiSchema;
  }

  public V1DetailAddonResponse url(String url) {
    this.url = url;
    return this;
  }

  /**
   * Get url
   * @return url
  **/
  @ApiModelProperty(value = "")


  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public V1DetailAddonResponse uxPlugins(Map<String, String> uxPlugins) {
    this.uxPlugins = uxPlugins;
    return this;
  }

  public V1DetailAddonResponse putUxPluginsItem(String key, String uxPluginsItem) {
    if (this.uxPlugins == null) {
      this.uxPlugins = new HashMap<String, String>();
    }
    this.uxPlugins.put(key, uxPluginsItem);
    return this;
  }

  /**
   * Get uxPlugins
   * @return uxPlugins
  **/
  @ApiModelProperty(value = "")


  public Map<String, String> getUxPlugins() {
    return uxPlugins;
  }

  public void setUxPlugins(Map<String, String> uxPlugins) {
    this.uxPlugins = uxPlugins;
  }

  public V1DetailAddonResponse version(String version) {
    this.version = version;
    return this;
  }

  /**
   * Get version
   * @return version
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1DetailAddonResponse v1DetailAddonResponse = (V1DetailAddonResponse) o;
    return Objects.equals(this.annotations, v1DetailAddonResponse.annotations) &&
        Objects.equals(this.availableVersions, v1DetailAddonResponse.availableVersions) &&
        Objects.equals(this.definitions, v1DetailAddonResponse.definitions) &&
        Objects.equals(this.dependencies, v1DetailAddonResponse.dependencies) &&
        Objects.equals(this.deployTo, v1DetailAddonResponse.deployTo) &&
        Objects.equals(this.description, v1DetailAddonResponse.description) &&
        Objects.equals(this.detail, v1DetailAddonResponse.detail) &&
        Objects.equals(this.icon, v1DetailAddonResponse.icon) &&
        Objects.equals(this.invisible, v1DetailAddonResponse.invisible) &&
        Objects.equals(this.name, v1DetailAddonResponse.name) &&
        Objects.equals(this.needNamespace, v1DetailAddonResponse.needNamespace) &&
        Objects.equals(this.registryName, v1DetailAddonResponse.registryName) &&
        Objects.equals(this.schema, v1DetailAddonResponse.schema) &&
        Objects.equals(this.system, v1DetailAddonResponse.system) &&
        Objects.equals(this.tags, v1DetailAddonResponse.tags) &&
        Objects.equals(this.uiSchema, v1DetailAddonResponse.uiSchema) &&
        Objects.equals(this.url, v1DetailAddonResponse.url) &&
        Objects.equals(this.uxPlugins, v1DetailAddonResponse.uxPlugins) &&
        Objects.equals(this.version, v1DetailAddonResponse.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(annotations, availableVersions, definitions, dependencies, deployTo, description, detail, icon, invisible, name, needNamespace, registryName, schema, system, tags, uiSchema, url, uxPlugins, version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1DetailAddonResponse {\n");
    
    sb.append("    annotations: ").append(toIndentedString(annotations)).append("\n");
    sb.append("    availableVersions: ").append(toIndentedString(availableVersions)).append("\n");
    sb.append("    definitions: ").append(toIndentedString(definitions)).append("\n");
    sb.append("    dependencies: ").append(toIndentedString(dependencies)).append("\n");
    sb.append("    deployTo: ").append(toIndentedString(deployTo)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    detail: ").append(toIndentedString(detail)).append("\n");
    sb.append("    icon: ").append(toIndentedString(icon)).append("\n");
    sb.append("    invisible: ").append(toIndentedString(invisible)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    needNamespace: ").append(toIndentedString(needNamespace)).append("\n");
    sb.append("    registryName: ").append(toIndentedString(registryName)).append("\n");
    sb.append("    schema: ").append(toIndentedString(schema)).append("\n");
    sb.append("    system: ").append(toIndentedString(system)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
    sb.append("    uiSchema: ").append(toIndentedString(uiSchema)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    uxPlugins: ").append(toIndentedString(uxPlugins)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
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

