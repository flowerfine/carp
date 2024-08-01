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
 * ChartDependency
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class ChartDependency   {
  @JsonProperty("alias")
  private String alias = null;

  @JsonProperty("condition")
  private String condition = null;

  @JsonProperty("enabled")
  private Boolean enabled = null;

  @JsonProperty("import-values")
  @Valid
  private List<ChartDependencyImportValues> importValues = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("repository")
  private String repository = null;

  @JsonProperty("tags")
  @Valid
  private List<String> tags = null;

  @JsonProperty("version")
  private String version = null;

  public ChartDependency alias(String alias) {
    this.alias = alias;
    return this;
  }

  /**
   * Get alias
   * @return alias
  **/
  @ApiModelProperty(value = "")


  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public ChartDependency condition(String condition) {
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

  public ChartDependency enabled(Boolean enabled) {
    this.enabled = enabled;
    return this;
  }

  /**
   * Get enabled
   * @return enabled
  **/
  @ApiModelProperty(value = "")


  public Boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public ChartDependency importValues(List<ChartDependencyImportValues> importValues) {
    this.importValues = importValues;
    return this;
  }

  public ChartDependency addImportValuesItem(ChartDependencyImportValues importValuesItem) {
    if (this.importValues == null) {
      this.importValues = new ArrayList<ChartDependencyImportValues>();
    }
    this.importValues.add(importValuesItem);
    return this;
  }

  /**
   * Get importValues
   * @return importValues
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<ChartDependencyImportValues> getImportValues() {
    return importValues;
  }

  public void setImportValues(List<ChartDependencyImportValues> importValues) {
    this.importValues = importValues;
  }

  public ChartDependency name(String name) {
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

  public ChartDependency repository(String repository) {
    this.repository = repository;
    return this;
  }

  /**
   * Get repository
   * @return repository
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getRepository() {
    return repository;
  }

  public void setRepository(String repository) {
    this.repository = repository;
  }

  public ChartDependency tags(List<String> tags) {
    this.tags = tags;
    return this;
  }

  public ChartDependency addTagsItem(String tagsItem) {
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

  public ChartDependency version(String version) {
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
    ChartDependency chartDependency = (ChartDependency) o;
    return Objects.equals(this.alias, chartDependency.alias) &&
        Objects.equals(this.condition, chartDependency.condition) &&
        Objects.equals(this.enabled, chartDependency.enabled) &&
        Objects.equals(this.importValues, chartDependency.importValues) &&
        Objects.equals(this.name, chartDependency.name) &&
        Objects.equals(this.repository, chartDependency.repository) &&
        Objects.equals(this.tags, chartDependency.tags) &&
        Objects.equals(this.version, chartDependency.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, condition, enabled, importValues, name, repository, tags, version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ChartDependency {\n");
    
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    condition: ").append(toIndentedString(condition)).append("\n");
    sb.append("    enabled: ").append(toIndentedString(enabled)).append("\n");
    sb.append("    importValues: ").append(toIndentedString(importValues)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    repository: ").append(toIndentedString(repository)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
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

