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
 * AddonMeta
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class AddonMeta   {
  @JsonProperty("annotations")
  @Valid
  private Map<String, String> annotations = null;

  @JsonProperty("dependencies")
  @Valid
  private List<AddonDependency> dependencies = null;

  @JsonProperty("deployTo")
  private AddonDeployTo deployTo = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("icon")
  private String icon = null;

  @JsonProperty("invisible")
  private Boolean invisible = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("needNamespace")
  @Valid
  private List<String> needNamespace = null;

  @JsonProperty("system")
  private AddonSystemRequirements system = null;

  @JsonProperty("tags")
  @Valid
  private List<String> tags = null;

  @JsonProperty("url")
  private String url = null;

  @JsonProperty("uxPlugins")
  @Valid
  private Map<String, String> uxPlugins = null;

  @JsonProperty("version")
  private String version = null;

  public AddonMeta annotations(Map<String, String> annotations) {
    this.annotations = annotations;
    return this;
  }

  public AddonMeta putAnnotationsItem(String key, String annotationsItem) {
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

  public AddonMeta dependencies(List<AddonDependency> dependencies) {
    this.dependencies = dependencies;
    return this;
  }

  public AddonMeta addDependenciesItem(AddonDependency dependenciesItem) {
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

  public AddonMeta deployTo(AddonDeployTo deployTo) {
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

  public AddonMeta description(String description) {
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

  public AddonMeta icon(String icon) {
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

  public AddonMeta invisible(Boolean invisible) {
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

  public AddonMeta name(String name) {
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

  public AddonMeta needNamespace(List<String> needNamespace) {
    this.needNamespace = needNamespace;
    return this;
  }

  public AddonMeta addNeedNamespaceItem(String needNamespaceItem) {
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

  public AddonMeta system(AddonSystemRequirements system) {
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

  public AddonMeta tags(List<String> tags) {
    this.tags = tags;
    return this;
  }

  public AddonMeta addTagsItem(String tagsItem) {
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

  public AddonMeta url(String url) {
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

  public AddonMeta uxPlugins(Map<String, String> uxPlugins) {
    this.uxPlugins = uxPlugins;
    return this;
  }

  public AddonMeta putUxPluginsItem(String key, String uxPluginsItem) {
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

  public AddonMeta version(String version) {
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
    AddonMeta addonMeta = (AddonMeta) o;
    return Objects.equals(this.annotations, addonMeta.annotations) &&
        Objects.equals(this.dependencies, addonMeta.dependencies) &&
        Objects.equals(this.deployTo, addonMeta.deployTo) &&
        Objects.equals(this.description, addonMeta.description) &&
        Objects.equals(this.icon, addonMeta.icon) &&
        Objects.equals(this.invisible, addonMeta.invisible) &&
        Objects.equals(this.name, addonMeta.name) &&
        Objects.equals(this.needNamespace, addonMeta.needNamespace) &&
        Objects.equals(this.system, addonMeta.system) &&
        Objects.equals(this.tags, addonMeta.tags) &&
        Objects.equals(this.url, addonMeta.url) &&
        Objects.equals(this.uxPlugins, addonMeta.uxPlugins) &&
        Objects.equals(this.version, addonMeta.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(annotations, dependencies, deployTo, description, icon, invisible, name, needNamespace, system, tags, url, uxPlugins, version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddonMeta {\n");
    
    sb.append("    annotations: ").append(toIndentedString(annotations)).append("\n");
    sb.append("    dependencies: ").append(toIndentedString(dependencies)).append("\n");
    sb.append("    deployTo: ").append(toIndentedString(deployTo)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    icon: ").append(toIndentedString(icon)).append("\n");
    sb.append("    invisible: ").append(toIndentedString(invisible)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    needNamespace: ").append(toIndentedString(needNamespace)).append("\n");
    sb.append("    system: ").append(toIndentedString(system)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
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

