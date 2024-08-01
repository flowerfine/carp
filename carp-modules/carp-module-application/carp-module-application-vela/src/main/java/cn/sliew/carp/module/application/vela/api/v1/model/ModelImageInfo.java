package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ModelImageInfo
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class ModelImageInfo   {
  @JsonProperty("repository")
  private ModelImageRepository repository = null;

  @JsonProperty("resource")
  private ModelImageResource resource = null;

  @JsonProperty("type")
  private String type = null;

  public ModelImageInfo repository(ModelImageRepository repository) {
    this.repository = repository;
    return this;
  }

  /**
   * Get repository
   * @return repository
  **/
  @ApiModelProperty(value = "")

  @Valid

  public ModelImageRepository getRepository() {
    return repository;
  }

  public void setRepository(ModelImageRepository repository) {
    this.repository = repository;
  }

  public ModelImageInfo resource(ModelImageResource resource) {
    this.resource = resource;
    return this;
  }

  /**
   * Get resource
   * @return resource
  **/
  @ApiModelProperty(value = "")

  @Valid

  public ModelImageResource getResource() {
    return resource;
  }

  public void setResource(ModelImageResource resource) {
    this.resource = resource;
  }

  public ModelImageInfo type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ModelImageInfo modelImageInfo = (ModelImageInfo) o;
    return Objects.equals(this.repository, modelImageInfo.repository) &&
        Objects.equals(this.resource, modelImageInfo.resource) &&
        Objects.equals(this.type, modelImageInfo.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(repository, resource, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ModelImageInfo {\n");
    
    sb.append("    repository: ").append(toIndentedString(repository)).append("\n");
    sb.append("    resource: ").append(toIndentedString(resource)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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

