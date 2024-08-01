package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * RepoChartVersion
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class RepoChartVersion   {
  @JsonProperty("Metadata")
  private ChartMetadata metadata = null;

  @JsonProperty("checksum")
  private String checksum = null;

  @JsonProperty("created")
  private OffsetDateTime created = null;

  @JsonProperty("digest")
  private String digest = null;

  @JsonProperty("engine")
  private String engine = null;

  @JsonProperty("removed")
  private Boolean removed = null;

  @JsonProperty("tillerVersion")
  private String tillerVersion = null;

  @JsonProperty("url")
  private String url = null;

  @JsonProperty("urls")
  @Valid
  private List<String> urls = new ArrayList<String>();

  public RepoChartVersion metadata(ChartMetadata metadata) {
    this.metadata = metadata;
    return this;
  }

  /**
   * Get metadata
   * @return metadata
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public ChartMetadata getMetadata() {
    return metadata;
  }

  public void setMetadata(ChartMetadata metadata) {
    this.metadata = metadata;
  }

  public RepoChartVersion checksum(String checksum) {
    this.checksum = checksum;
    return this;
  }

  /**
   * Get checksum
   * @return checksum
  **/
  @ApiModelProperty(value = "")


  public String getChecksum() {
    return checksum;
  }

  public void setChecksum(String checksum) {
    this.checksum = checksum;
  }

  public RepoChartVersion created(OffsetDateTime created) {
    this.created = created;
    return this;
  }

  /**
   * Get created
   * @return created
  **/
  @ApiModelProperty(value = "")

  @Valid

  public OffsetDateTime getCreated() {
    return created;
  }

  public void setCreated(OffsetDateTime created) {
    this.created = created;
  }

  public RepoChartVersion digest(String digest) {
    this.digest = digest;
    return this;
  }

  /**
   * Get digest
   * @return digest
  **/
  @ApiModelProperty(value = "")


  public String getDigest() {
    return digest;
  }

  public void setDigest(String digest) {
    this.digest = digest;
  }

  public RepoChartVersion engine(String engine) {
    this.engine = engine;
    return this;
  }

  /**
   * Get engine
   * @return engine
  **/
  @ApiModelProperty(value = "")


  public String getEngine() {
    return engine;
  }

  public void setEngine(String engine) {
    this.engine = engine;
  }

  public RepoChartVersion removed(Boolean removed) {
    this.removed = removed;
    return this;
  }

  /**
   * Get removed
   * @return removed
  **/
  @ApiModelProperty(value = "")


  public Boolean isRemoved() {
    return removed;
  }

  public void setRemoved(Boolean removed) {
    this.removed = removed;
  }

  public RepoChartVersion tillerVersion(String tillerVersion) {
    this.tillerVersion = tillerVersion;
    return this;
  }

  /**
   * Get tillerVersion
   * @return tillerVersion
  **/
  @ApiModelProperty(value = "")


  public String getTillerVersion() {
    return tillerVersion;
  }

  public void setTillerVersion(String tillerVersion) {
    this.tillerVersion = tillerVersion;
  }

  public RepoChartVersion url(String url) {
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

  public RepoChartVersion urls(List<String> urls) {
    this.urls = urls;
    return this;
  }

  public RepoChartVersion addUrlsItem(String urlsItem) {
    this.urls.add(urlsItem);
    return this;
  }

  /**
   * Get urls
   * @return urls
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


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
    RepoChartVersion repoChartVersion = (RepoChartVersion) o;
    return Objects.equals(this.metadata, repoChartVersion.metadata) &&
        Objects.equals(this.checksum, repoChartVersion.checksum) &&
        Objects.equals(this.created, repoChartVersion.created) &&
        Objects.equals(this.digest, repoChartVersion.digest) &&
        Objects.equals(this.engine, repoChartVersion.engine) &&
        Objects.equals(this.removed, repoChartVersion.removed) &&
        Objects.equals(this.tillerVersion, repoChartVersion.tillerVersion) &&
        Objects.equals(this.url, repoChartVersion.url) &&
        Objects.equals(this.urls, repoChartVersion.urls);
  }

  @Override
  public int hashCode() {
    return Objects.hash(metadata, checksum, created, digest, engine, removed, tillerVersion, url, urls);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RepoChartVersion {\n");
    
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    checksum: ").append(toIndentedString(checksum)).append("\n");
    sb.append("    created: ").append(toIndentedString(created)).append("\n");
    sb.append("    digest: ").append(toIndentedString(digest)).append("\n");
    sb.append("    engine: ").append(toIndentedString(engine)).append("\n");
    sb.append("    removed: ").append(toIndentedString(removed)).append("\n");
    sb.append("    tillerVersion: ").append(toIndentedString(tillerVersion)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
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

