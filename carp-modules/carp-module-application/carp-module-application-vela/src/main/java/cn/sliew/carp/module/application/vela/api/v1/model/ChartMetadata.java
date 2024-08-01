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
 * ChartMetadata
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class ChartMetadata   {
  @JsonProperty("annotations")
  @Valid
  private Map<String, String> annotations = null;

  @JsonProperty("apiVersion")
  private String apiVersion = null;

  @JsonProperty("appVersion")
  private String appVersion = null;

  @JsonProperty("condition")
  private String condition = null;

  @JsonProperty("dependencies")
  @Valid
  private List<ChartDependency> dependencies = null;

  @JsonProperty("deprecated")
  private Boolean deprecated = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("home")
  private String home = null;

  @JsonProperty("icon")
  private String icon = null;

  @JsonProperty("keywords")
  @Valid
  private List<String> keywords = null;

  @JsonProperty("kubeVersion")
  private String kubeVersion = null;

  @JsonProperty("maintainers")
  @Valid
  private List<ChartMaintainer> maintainers = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("sources")
  @Valid
  private List<String> sources = null;

  @JsonProperty("tags")
  private String tags = null;

  @JsonProperty("type")
  private String type = null;

  @JsonProperty("version")
  private String version = null;

  public ChartMetadata annotations(Map<String, String> annotations) {
    this.annotations = annotations;
    return this;
  }

  public ChartMetadata putAnnotationsItem(String key, String annotationsItem) {
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

  public ChartMetadata apiVersion(String apiVersion) {
    this.apiVersion = apiVersion;
    return this;
  }

  /**
   * Get apiVersion
   * @return apiVersion
  **/
  @ApiModelProperty(value = "")


  public String getApiVersion() {
    return apiVersion;
  }

  public void setApiVersion(String apiVersion) {
    this.apiVersion = apiVersion;
  }

  public ChartMetadata appVersion(String appVersion) {
    this.appVersion = appVersion;
    return this;
  }

  /**
   * Get appVersion
   * @return appVersion
  **/
  @ApiModelProperty(value = "")


  public String getAppVersion() {
    return appVersion;
  }

  public void setAppVersion(String appVersion) {
    this.appVersion = appVersion;
  }

  public ChartMetadata condition(String condition) {
    this.condition = condition;
    return this;
  }

  /**
   * Get condition
   * @return condition
  **/
  @ApiModelProperty(value = "")


  public String getCondition() {
    return condition;
  }

  public void setCondition(String condition) {
    this.condition = condition;
  }

  public ChartMetadata dependencies(List<ChartDependency> dependencies) {
    this.dependencies = dependencies;
    return this;
  }

  public ChartMetadata addDependenciesItem(ChartDependency dependenciesItem) {
    if (this.dependencies == null) {
      this.dependencies = new ArrayList<ChartDependency>();
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

  public List<ChartDependency> getDependencies() {
    return dependencies;
  }

  public void setDependencies(List<ChartDependency> dependencies) {
    this.dependencies = dependencies;
  }

  public ChartMetadata deprecated(Boolean deprecated) {
    this.deprecated = deprecated;
    return this;
  }

  /**
   * Get deprecated
   * @return deprecated
  **/
  @ApiModelProperty(value = "")


  public Boolean isDeprecated() {
    return deprecated;
  }

  public void setDeprecated(Boolean deprecated) {
    this.deprecated = deprecated;
  }

  public ChartMetadata description(String description) {
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

  public ChartMetadata home(String home) {
    this.home = home;
    return this;
  }

  /**
   * Get home
   * @return home
  **/
  @ApiModelProperty(value = "")


  public String getHome() {
    return home;
  }

  public void setHome(String home) {
    this.home = home;
  }

  public ChartMetadata icon(String icon) {
    this.icon = icon;
    return this;
  }

  /**
   * Get icon
   * @return icon
  **/
  @ApiModelProperty(value = "")


  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public ChartMetadata keywords(List<String> keywords) {
    this.keywords = keywords;
    return this;
  }

  public ChartMetadata addKeywordsItem(String keywordsItem) {
    if (this.keywords == null) {
      this.keywords = new ArrayList<String>();
    }
    this.keywords.add(keywordsItem);
    return this;
  }

  /**
   * Get keywords
   * @return keywords
  **/
  @ApiModelProperty(value = "")


  public List<String> getKeywords() {
    return keywords;
  }

  public void setKeywords(List<String> keywords) {
    this.keywords = keywords;
  }

  public ChartMetadata kubeVersion(String kubeVersion) {
    this.kubeVersion = kubeVersion;
    return this;
  }

  /**
   * Get kubeVersion
   * @return kubeVersion
  **/
  @ApiModelProperty(value = "")


  public String getKubeVersion() {
    return kubeVersion;
  }

  public void setKubeVersion(String kubeVersion) {
    this.kubeVersion = kubeVersion;
  }

  public ChartMetadata maintainers(List<ChartMaintainer> maintainers) {
    this.maintainers = maintainers;
    return this;
  }

  public ChartMetadata addMaintainersItem(ChartMaintainer maintainersItem) {
    if (this.maintainers == null) {
      this.maintainers = new ArrayList<ChartMaintainer>();
    }
    this.maintainers.add(maintainersItem);
    return this;
  }

  /**
   * Get maintainers
   * @return maintainers
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<ChartMaintainer> getMaintainers() {
    return maintainers;
  }

  public void setMaintainers(List<ChartMaintainer> maintainers) {
    this.maintainers = maintainers;
  }

  public ChartMetadata name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(value = "")


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ChartMetadata sources(List<String> sources) {
    this.sources = sources;
    return this;
  }

  public ChartMetadata addSourcesItem(String sourcesItem) {
    if (this.sources == null) {
      this.sources = new ArrayList<String>();
    }
    this.sources.add(sourcesItem);
    return this;
  }

  /**
   * Get sources
   * @return sources
  **/
  @ApiModelProperty(value = "")


  public List<String> getSources() {
    return sources;
  }

  public void setSources(List<String> sources) {
    this.sources = sources;
  }

  public ChartMetadata tags(String tags) {
    this.tags = tags;
    return this;
  }

  /**
   * Get tags
   * @return tags
  **/
  @ApiModelProperty(value = "")


  public String getTags() {
    return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }

  public ChartMetadata type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
  **/
  @ApiModelProperty(value = "")


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public ChartMetadata version(String version) {
    this.version = version;
    return this;
  }

  /**
   * Get version
   * @return version
  **/
  @ApiModelProperty(value = "")


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
    ChartMetadata chartMetadata = (ChartMetadata) o;
    return Objects.equals(this.annotations, chartMetadata.annotations) &&
        Objects.equals(this.apiVersion, chartMetadata.apiVersion) &&
        Objects.equals(this.appVersion, chartMetadata.appVersion) &&
        Objects.equals(this.condition, chartMetadata.condition) &&
        Objects.equals(this.dependencies, chartMetadata.dependencies) &&
        Objects.equals(this.deprecated, chartMetadata.deprecated) &&
        Objects.equals(this.description, chartMetadata.description) &&
        Objects.equals(this.home, chartMetadata.home) &&
        Objects.equals(this.icon, chartMetadata.icon) &&
        Objects.equals(this.keywords, chartMetadata.keywords) &&
        Objects.equals(this.kubeVersion, chartMetadata.kubeVersion) &&
        Objects.equals(this.maintainers, chartMetadata.maintainers) &&
        Objects.equals(this.name, chartMetadata.name) &&
        Objects.equals(this.sources, chartMetadata.sources) &&
        Objects.equals(this.tags, chartMetadata.tags) &&
        Objects.equals(this.type, chartMetadata.type) &&
        Objects.equals(this.version, chartMetadata.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(annotations, apiVersion, appVersion, condition, dependencies, deprecated, description, home, icon, keywords, kubeVersion, maintainers, name, sources, tags, type, version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ChartMetadata {\n");
    
    sb.append("    annotations: ").append(toIndentedString(annotations)).append("\n");
    sb.append("    apiVersion: ").append(toIndentedString(apiVersion)).append("\n");
    sb.append("    appVersion: ").append(toIndentedString(appVersion)).append("\n");
    sb.append("    condition: ").append(toIndentedString(condition)).append("\n");
    sb.append("    dependencies: ").append(toIndentedString(dependencies)).append("\n");
    sb.append("    deprecated: ").append(toIndentedString(deprecated)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    home: ").append(toIndentedString(home)).append("\n");
    sb.append("    icon: ").append(toIndentedString(icon)).append("\n");
    sb.append("    keywords: ").append(toIndentedString(keywords)).append("\n");
    sb.append("    kubeVersion: ").append(toIndentedString(kubeVersion)).append("\n");
    sb.append("    maintainers: ").append(toIndentedString(maintainers)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    sources: ").append(toIndentedString(sources)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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

