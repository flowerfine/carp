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
 * V1ConfigFile
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ConfigFile   {
  @JsonProperty("architecture")
  private String architecture = null;

  @JsonProperty("author")
  private String author = null;

  @JsonProperty("config")
  private V1Config config = null;

  @JsonProperty("container")
  private String container = null;

  @JsonProperty("created")
  private String created = null;

  @JsonProperty("docker_version")
  private String dockerVersion = null;

  @JsonProperty("history")
  @Valid
  private List<V1History> history = null;

  @JsonProperty("os")
  private String os = null;

  @JsonProperty("os.features")
  @Valid
  private List<String> osFeatures = null;

  @JsonProperty("os.version")
  private String osVersion = null;

  @JsonProperty("rootfs")
  private V1RootFS rootfs = null;

  @JsonProperty("variant")
  private String variant = null;

  public V1ConfigFile architecture(String architecture) {
    this.architecture = architecture;
    return this;
  }

  /**
   * Get architecture
   * @return architecture
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getArchitecture() {
    return architecture;
  }

  public void setArchitecture(String architecture) {
    this.architecture = architecture;
  }

  public V1ConfigFile author(String author) {
    this.author = author;
    return this;
  }

  /**
   * Get author
   * @return author
  **/
  @ApiModelProperty(value = "")


  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public V1ConfigFile config(V1Config config) {
    this.config = config;
    return this;
  }

  /**
   * Get config
   * @return config
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public V1Config getConfig() {
    return config;
  }

  public void setConfig(V1Config config) {
    this.config = config;
  }

  public V1ConfigFile container(String container) {
    this.container = container;
    return this;
  }

  /**
   * Get container
   * @return container
  **/
  @ApiModelProperty(value = "")


  public String getContainer() {
    return container;
  }

  public void setContainer(String container) {
    this.container = container;
  }

  public V1ConfigFile created(String created) {
    this.created = created;
    return this;
  }

  /**
   * Get created
   * @return created
  **/
  @ApiModelProperty(value = "")


  public String getCreated() {
    return created;
  }

  public void setCreated(String created) {
    this.created = created;
  }

  public V1ConfigFile dockerVersion(String dockerVersion) {
    this.dockerVersion = dockerVersion;
    return this;
  }

  /**
   * Get dockerVersion
   * @return dockerVersion
  **/
  @ApiModelProperty(value = "")


  public String getDockerVersion() {
    return dockerVersion;
  }

  public void setDockerVersion(String dockerVersion) {
    this.dockerVersion = dockerVersion;
  }

  public V1ConfigFile history(List<V1History> history) {
    this.history = history;
    return this;
  }

  public V1ConfigFile addHistoryItem(V1History historyItem) {
    if (this.history == null) {
      this.history = new ArrayList<V1History>();
    }
    this.history.add(historyItem);
    return this;
  }

  /**
   * Get history
   * @return history
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<V1History> getHistory() {
    return history;
  }

  public void setHistory(List<V1History> history) {
    this.history = history;
  }

  public V1ConfigFile os(String os) {
    this.os = os;
    return this;
  }

  /**
   * Get os
   * @return os
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getOs() {
    return os;
  }

  public void setOs(String os) {
    this.os = os;
  }

  public V1ConfigFile osFeatures(List<String> osFeatures) {
    this.osFeatures = osFeatures;
    return this;
  }

  public V1ConfigFile addOsFeaturesItem(String osFeaturesItem) {
    if (this.osFeatures == null) {
      this.osFeatures = new ArrayList<String>();
    }
    this.osFeatures.add(osFeaturesItem);
    return this;
  }

  /**
   * Get osFeatures
   * @return osFeatures
  **/
  @ApiModelProperty(value = "")


  public List<String> getOsFeatures() {
    return osFeatures;
  }

  public void setOsFeatures(List<String> osFeatures) {
    this.osFeatures = osFeatures;
  }

  public V1ConfigFile osVersion(String osVersion) {
    this.osVersion = osVersion;
    return this;
  }

  /**
   * Get osVersion
   * @return osVersion
  **/
  @ApiModelProperty(value = "")


  public String getOsVersion() {
    return osVersion;
  }

  public void setOsVersion(String osVersion) {
    this.osVersion = osVersion;
  }

  public V1ConfigFile rootfs(V1RootFS rootfs) {
    this.rootfs = rootfs;
    return this;
  }

  /**
   * Get rootfs
   * @return rootfs
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public V1RootFS getRootfs() {
    return rootfs;
  }

  public void setRootfs(V1RootFS rootfs) {
    this.rootfs = rootfs;
  }

  public V1ConfigFile variant(String variant) {
    this.variant = variant;
    return this;
  }

  /**
   * Get variant
   * @return variant
  **/
  @ApiModelProperty(value = "")


  public String getVariant() {
    return variant;
  }

  public void setVariant(String variant) {
    this.variant = variant;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1ConfigFile v1ConfigFile = (V1ConfigFile) o;
    return Objects.equals(this.architecture, v1ConfigFile.architecture) &&
        Objects.equals(this.author, v1ConfigFile.author) &&
        Objects.equals(this.config, v1ConfigFile.config) &&
        Objects.equals(this.container, v1ConfigFile.container) &&
        Objects.equals(this.created, v1ConfigFile.created) &&
        Objects.equals(this.dockerVersion, v1ConfigFile.dockerVersion) &&
        Objects.equals(this.history, v1ConfigFile.history) &&
        Objects.equals(this.os, v1ConfigFile.os) &&
        Objects.equals(this.osFeatures, v1ConfigFile.osFeatures) &&
        Objects.equals(this.osVersion, v1ConfigFile.osVersion) &&
        Objects.equals(this.rootfs, v1ConfigFile.rootfs) &&
        Objects.equals(this.variant, v1ConfigFile.variant);
  }

  @Override
  public int hashCode() {
    return Objects.hash(architecture, author, config, container, created, dockerVersion, history, os, osFeatures, osVersion, rootfs, variant);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ConfigFile {\n");
    
    sb.append("    architecture: ").append(toIndentedString(architecture)).append("\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    config: ").append(toIndentedString(config)).append("\n");
    sb.append("    container: ").append(toIndentedString(container)).append("\n");
    sb.append("    created: ").append(toIndentedString(created)).append("\n");
    sb.append("    dockerVersion: ").append(toIndentedString(dockerVersion)).append("\n");
    sb.append("    history: ").append(toIndentedString(history)).append("\n");
    sb.append("    os: ").append(toIndentedString(os)).append("\n");
    sb.append("    osFeatures: ").append(toIndentedString(osFeatures)).append("\n");
    sb.append("    osVersion: ").append(toIndentedString(osVersion)).append("\n");
    sb.append("    rootfs: ").append(toIndentedString(rootfs)).append("\n");
    sb.append("    variant: ").append(toIndentedString(variant)).append("\n");
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

