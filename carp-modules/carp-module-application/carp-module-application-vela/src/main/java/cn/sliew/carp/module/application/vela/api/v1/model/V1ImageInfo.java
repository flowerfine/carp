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
 * V1ImageInfo
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ImageInfo   {
  @JsonProperty("info")
  private V1ConfigFile info = null;

  @JsonProperty("manifest")
  private V1Manifest manifest = null;

  @JsonProperty("message")
  private String message = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("registry")
  private String registry = null;

  @JsonProperty("secretNames")
  @Valid
  private List<String> secretNames = new ArrayList<String>();

  @JsonProperty("size")
  private Long size = null;

  public V1ImageInfo info(V1ConfigFile info) {
    this.info = info;
    return this;
  }

  /**
   * Get info
   * @return info
  **/
  @ApiModelProperty(value = "")

  @Valid

  public V1ConfigFile getInfo() {
    return info;
  }

  public void setInfo(V1ConfigFile info) {
    this.info = info;
  }

  public V1ImageInfo manifest(V1Manifest manifest) {
    this.manifest = manifest;
    return this;
  }

  /**
   * Get manifest
   * @return manifest
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public V1Manifest getManifest() {
    return manifest;
  }

  public void setManifest(V1Manifest manifest) {
    this.manifest = manifest;
  }

  public V1ImageInfo message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   * @return message
  **/
  @ApiModelProperty(value = "")


  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public V1ImageInfo name(String name) {
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

  public V1ImageInfo registry(String registry) {
    this.registry = registry;
    return this;
  }

  /**
   * Get registry
   * @return registry
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getRegistry() {
    return registry;
  }

  public void setRegistry(String registry) {
    this.registry = registry;
  }

  public V1ImageInfo secretNames(List<String> secretNames) {
    this.secretNames = secretNames;
    return this;
  }

  public V1ImageInfo addSecretNamesItem(String secretNamesItem) {
    this.secretNames.add(secretNamesItem);
    return this;
  }

  /**
   * Get secretNames
   * @return secretNames
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public List<String> getSecretNames() {
    return secretNames;
  }

  public void setSecretNames(List<String> secretNames) {
    this.secretNames = secretNames;
  }

  public V1ImageInfo size(Long size) {
    this.size = size;
    return this;
  }

  /**
   * Get size
   * @return size
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getSize() {
    return size;
  }

  public void setSize(Long size) {
    this.size = size;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1ImageInfo v1ImageInfo = (V1ImageInfo) o;
    return Objects.equals(this.info, v1ImageInfo.info) &&
        Objects.equals(this.manifest, v1ImageInfo.manifest) &&
        Objects.equals(this.message, v1ImageInfo.message) &&
        Objects.equals(this.name, v1ImageInfo.name) &&
        Objects.equals(this.registry, v1ImageInfo.registry) &&
        Objects.equals(this.secretNames, v1ImageInfo.secretNames) &&
        Objects.equals(this.size, v1ImageInfo.size);
  }

  @Override
  public int hashCode() {
    return Objects.hash(info, manifest, message, name, registry, secretNames, size);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ImageInfo {\n");
    
    sb.append("    info: ").append(toIndentedString(info)).append("\n");
    sb.append("    manifest: ").append(toIndentedString(manifest)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    registry: ").append(toIndentedString(registry)).append("\n");
    sb.append("    secretNames: ").append(toIndentedString(secretNames)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
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

