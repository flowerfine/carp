package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;

import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1Descriptor;
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
 * V1Manifest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1Manifest   {
  @JsonProperty("annotations")
  @Valid
  private Map<String, String> annotations = null;

  @JsonProperty("config")
  private V1Descriptor config = null;

  @JsonProperty("layers")
  @Valid
  private List<V1Descriptor> layers = new ArrayList<V1Descriptor>();

  @JsonProperty("mediaType")
  private String mediaType = null;

  @JsonProperty("schemaVersion")
  private Long schemaVersion = null;

  @JsonProperty("subject")
  private V1Descriptor subject = null;

  public V1Manifest annotations(Map<String, String> annotations) {
    this.annotations = annotations;
    return this;
  }

  public V1Manifest putAnnotationsItem(String key, String annotationsItem) {
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

  public V1Manifest config(V1Descriptor config) {
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

  public V1Descriptor getConfig() {
    return config;
  }

  public void setConfig(V1Descriptor config) {
    this.config = config;
  }

  public V1Manifest layers(List<V1Descriptor> layers) {
    this.layers = layers;
    return this;
  }

  public V1Manifest addLayersItem(V1Descriptor layersItem) {
    this.layers.add(layersItem);
    return this;
  }

  /**
   * Get layers
   * @return layers
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1Descriptor> getLayers() {
    return layers;
  }

  public void setLayers(List<V1Descriptor> layers) {
    this.layers = layers;
  }

  public V1Manifest mediaType(String mediaType) {
    this.mediaType = mediaType;
    return this;
  }

  /**
   * Get mediaType
   * @return mediaType
  **/
  @ApiModelProperty(value = "")


  public String getMediaType() {
    return mediaType;
  }

  public void setMediaType(String mediaType) {
    this.mediaType = mediaType;
  }

  public V1Manifest schemaVersion(Long schemaVersion) {
    this.schemaVersion = schemaVersion;
    return this;
  }

  /**
   * Get schemaVersion
   * @return schemaVersion
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getSchemaVersion() {
    return schemaVersion;
  }

  public void setSchemaVersion(Long schemaVersion) {
    this.schemaVersion = schemaVersion;
  }

  public V1Manifest subject(V1Descriptor subject) {
    this.subject = subject;
    return this;
  }

  /**
   * Get subject
   * @return subject
  **/
  @ApiModelProperty(value = "")

  @Valid

  public V1Descriptor getSubject() {
    return subject;
  }

  public void setSubject(V1Descriptor subject) {
    this.subject = subject;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1Manifest v1Manifest = (V1Manifest) o;
    return Objects.equals(this.annotations, v1Manifest.annotations) &&
        Objects.equals(this.config, v1Manifest.config) &&
        Objects.equals(this.layers, v1Manifest.layers) &&
        Objects.equals(this.mediaType, v1Manifest.mediaType) &&
        Objects.equals(this.schemaVersion, v1Manifest.schemaVersion) &&
        Objects.equals(this.subject, v1Manifest.subject);
  }

  @Override
  public int hashCode() {
    return Objects.hash(annotations, config, layers, mediaType, schemaVersion, subject);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1Manifest {\n");
    
    sb.append("    annotations: ").append(toIndentedString(annotations)).append("\n");
    sb.append("    config: ").append(toIndentedString(config)).append("\n");
    sb.append("    layers: ").append(toIndentedString(layers)).append("\n");
    sb.append("    mediaType: ").append(toIndentedString(mediaType)).append("\n");
    sb.append("    schemaVersion: ").append(toIndentedString(schemaVersion)).append("\n");
    sb.append("    subject: ").append(toIndentedString(subject)).append("\n");
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

