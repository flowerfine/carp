package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1History
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1History   {
  @JsonProperty("author")
  private String author = null;

  @JsonProperty("comment")
  private String comment = null;

  @JsonProperty("created")
  private String created = null;

  @JsonProperty("created_by")
  private String createdBy = null;

  @JsonProperty("empty_layer")
  private Boolean emptyLayer = null;

  public V1History author(String author) {
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

  public V1History comment(String comment) {
    this.comment = comment;
    return this;
  }

  /**
   * Get comment
   * @return comment
  **/
  @ApiModelProperty(value = "")


  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public V1History created(String created) {
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

  public V1History createdBy(String createdBy) {
    this.createdBy = createdBy;
    return this;
  }

  /**
   * Get createdBy
   * @return createdBy
  **/
  @ApiModelProperty(value = "")


  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public V1History emptyLayer(Boolean emptyLayer) {
    this.emptyLayer = emptyLayer;
    return this;
  }

  /**
   * Get emptyLayer
   * @return emptyLayer
  **/
  @ApiModelProperty(value = "")


  public Boolean isEmptyLayer() {
    return emptyLayer;
  }

  public void setEmptyLayer(Boolean emptyLayer) {
    this.emptyLayer = emptyLayer;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1History v1History = (V1History) o;
    return Objects.equals(this.author, v1History.author) &&
        Objects.equals(this.comment, v1History.comment) &&
        Objects.equals(this.created, v1History.created) &&
        Objects.equals(this.createdBy, v1History.createdBy) &&
        Objects.equals(this.emptyLayer, v1History.emptyLayer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(author, comment, created, createdBy, emptyLayer);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1History {\n");
    
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("    created: ").append(toIndentedString(created)).append("\n");
    sb.append("    createdBy: ").append(toIndentedString(createdBy)).append("\n");
    sb.append("    emptyLayer: ").append(toIndentedString(emptyLayer)).append("\n");
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

