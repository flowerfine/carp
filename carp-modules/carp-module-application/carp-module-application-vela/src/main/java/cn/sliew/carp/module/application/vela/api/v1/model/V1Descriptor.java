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
 * V1Descriptor
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1Descriptor   {
  @JsonProperty("annotations")
  @Valid
  private Map<String, String> annotations = null;

  @JsonProperty("artifactType")
  private String artifactType = null;

  @JsonProperty("data")
  private String data = null;

  @JsonProperty("digest")
  private String digest = null;

  @JsonProperty("mediaType")
  private String mediaType = null;

  @JsonProperty("platform")
  private V1Platform platform = null;

  @JsonProperty("size")
  private Long size = null;

  @JsonProperty("urls")
  @Valid
  private List<String> urls = null;

  public V1Descriptor annotations(Map<String, String> annotations) {
    this.annotations = annotations;
    return this;
  }

  public V1Descriptor putAnnotationsItem(String key, String annotationsItem) {
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

  public V1Descriptor artifactType(String artifactType) {
    this.artifactType = artifactType;
    return this;
  }

  /**
   * Get artifactType
   * @return artifactType
  **/
  @ApiModelProperty(value = "")


  public String getArtifactType() {
    return artifactType;
  }

  public void setArtifactType(String artifactType) {
    this.artifactType = artifactType;
  }

  public V1Descriptor data(String data) {
    this.data = data;
    return this;
  }

  /**
   * Get data
   * @return data
  **/
  @ApiModelProperty(value = "")


  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public V1Descriptor digest(String digest) {
    this.digest = digest;
    return this;
  }

  /**
   * Get digest
   * @return digest
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getDigest() {
    return digest;
  }

  public void setDigest(String digest) {
    this.digest = digest;
  }

  public V1Descriptor mediaType(String mediaType) {
    this.mediaType = mediaType;
    return this;
  }

  /**
   * Get mediaType
   * @return mediaType
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getMediaType() {
    return mediaType;
  }

  public void setMediaType(String mediaType) {
    this.mediaType = mediaType;
  }

  public V1Descriptor platform(V1Platform platform) {
    this.platform = platform;
    return this;
  }

  /**
   * Get platform
   * @return platform
  **/
  @ApiModelProperty(value = "")

  @Valid

  public V1Platform getPlatform() {
    return platform;
  }

  public void setPlatform(V1Platform platform) {
    this.platform = platform;
  }

  public V1Descriptor size(Long size) {
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

  public V1Descriptor urls(List<String> urls) {
    this.urls = urls;
    return this;
  }

  public V1Descriptor addUrlsItem(String urlsItem) {
    if (this.urls == null) {
      this.urls = new ArrayList<String>();
    }
    this.urls.add(urlsItem);
    return this;
  }

  /**
   * Get urls
   * @return urls
  **/
  @ApiModelProperty(value = "")


  public List<String> getUrls() {
    return urls;
  }

  public void setUrls(List<String> urls) {
    this.urls = urls;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1Descriptor v1Descriptor = (V1Descriptor) o;
    return Objects.equals(this.annotations, v1Descriptor.annotations) &&
        Objects.equals(this.artifactType, v1Descriptor.artifactType) &&
        Objects.equals(this.data, v1Descriptor.data) &&
        Objects.equals(this.digest, v1Descriptor.digest) &&
        Objects.equals(this.mediaType, v1Descriptor.mediaType) &&
        Objects.equals(this.platform, v1Descriptor.platform) &&
        Objects.equals(this.size, v1Descriptor.size) &&
        Objects.equals(this.urls, v1Descriptor.urls);
  }

  @Override
  public int hashCode() {
    return Objects.hash(annotations, artifactType, data, digest, mediaType, platform, size, urls);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1Descriptor {\n");
    
    sb.append("    annotations: ").append(toIndentedString(annotations)).append("\n");
    sb.append("    artifactType: ").append(toIndentedString(artifactType)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    digest: ").append(toIndentedString(digest)).append("\n");
    sb.append("    mediaType: ").append(toIndentedString(mediaType)).append("\n");
    sb.append("    platform: ").append(toIndentedString(platform)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
    sb.append("    urls: ").append(toIndentedString(urls)).append("\n");
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

