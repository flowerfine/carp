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
 * TypesInfo
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class TypesInfo   {
  @JsonProperty("author")
  private TypesInfoLink author = null;

  @JsonProperty("build")
  private TypesBuildInfo build = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("links")
  @Valid
  private List<TypesInfoLink> links = new ArrayList<TypesInfoLink>();

  @JsonProperty("logos")
  private TypesLogos logos = null;

  @JsonProperty("screenshots")
  @Valid
  private List<TypesScreenshots> screenshots = new ArrayList<TypesScreenshots>();

  @JsonProperty("updated")
  private String updated = null;

  @JsonProperty("version")
  private String version = null;

  public TypesInfo author(TypesInfoLink author) {
    this.author = author;
    return this;
  }

  /**
   * Get author
   * @return author
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public TypesInfoLink getAuthor() {
    return author;
  }

  public void setAuthor(TypesInfoLink author) {
    this.author = author;
  }

  public TypesInfo build(TypesBuildInfo build) {
    this.build = build;
    return this;
  }

  /**
   * Get build
   * @return build
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public TypesBuildInfo getBuild() {
    return build;
  }

  public void setBuild(TypesBuildInfo build) {
    this.build = build;
  }

  public TypesInfo description(String description) {
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

  public TypesInfo links(List<TypesInfoLink> links) {
    this.links = links;
    return this;
  }

  public TypesInfo addLinksItem(TypesInfoLink linksItem) {
    this.links.add(linksItem);
    return this;
  }

  /**
   * Get links
   * @return links
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<TypesInfoLink> getLinks() {
    return links;
  }

  public void setLinks(List<TypesInfoLink> links) {
    this.links = links;
  }

  public TypesInfo logos(TypesLogos logos) {
    this.logos = logos;
    return this;
  }

  /**
   * Get logos
   * @return logos
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public TypesLogos getLogos() {
    return logos;
  }

  public void setLogos(TypesLogos logos) {
    this.logos = logos;
  }

  public TypesInfo screenshots(List<TypesScreenshots> screenshots) {
    this.screenshots = screenshots;
    return this;
  }

  public TypesInfo addScreenshotsItem(TypesScreenshots screenshotsItem) {
    this.screenshots.add(screenshotsItem);
    return this;
  }

  /**
   * Get screenshots
   * @return screenshots
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<TypesScreenshots> getScreenshots() {
    return screenshots;
  }

  public void setScreenshots(List<TypesScreenshots> screenshots) {
    this.screenshots = screenshots;
  }

  public TypesInfo updated(String updated) {
    this.updated = updated;
    return this;
  }

  /**
   * Get updated
   * @return updated
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getUpdated() {
    return updated;
  }

  public void setUpdated(String updated) {
    this.updated = updated;
  }

  public TypesInfo version(String version) {
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
    TypesInfo typesInfo = (TypesInfo) o;
    return Objects.equals(this.author, typesInfo.author) &&
        Objects.equals(this.build, typesInfo.build) &&
        Objects.equals(this.description, typesInfo.description) &&
        Objects.equals(this.links, typesInfo.links) &&
        Objects.equals(this.logos, typesInfo.logos) &&
        Objects.equals(this.screenshots, typesInfo.screenshots) &&
        Objects.equals(this.updated, typesInfo.updated) &&
        Objects.equals(this.version, typesInfo.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(author, build, description, links, logos, screenshots, updated, version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TypesInfo {\n");
    
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    build: ").append(toIndentedString(build)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
    sb.append("    logos: ").append(toIndentedString(logos)).append("\n");
    sb.append("    screenshots: ").append(toIndentedString(screenshots)).append("\n");
    sb.append("    updated: ").append(toIndentedString(updated)).append("\n");
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

