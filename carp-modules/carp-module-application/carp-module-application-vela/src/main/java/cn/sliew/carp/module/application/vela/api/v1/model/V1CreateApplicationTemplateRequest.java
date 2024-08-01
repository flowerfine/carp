package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1CreateApplicationTemplateRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1CreateApplicationTemplateRequest   {
  @JsonProperty("description")
  private String description = null;

  @JsonProperty("templateName")
  private String templateName = null;

  @JsonProperty("version")
  private String version = null;

  public V1CreateApplicationTemplateRequest description(String description) {
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

  public V1CreateApplicationTemplateRequest templateName(String templateName) {
    this.templateName = templateName;
    return this;
  }

  /**
   * Get templateName
   * @return templateName
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getTemplateName() {
    return templateName;
  }

  public void setTemplateName(String templateName) {
    this.templateName = templateName;
  }

  public V1CreateApplicationTemplateRequest version(String version) {
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
    V1CreateApplicationTemplateRequest v1CreateApplicationTemplateRequest = (V1CreateApplicationTemplateRequest) o;
    return Objects.equals(this.description, v1CreateApplicationTemplateRequest.description) &&
        Objects.equals(this.templateName, v1CreateApplicationTemplateRequest.templateName) &&
        Objects.equals(this.version, v1CreateApplicationTemplateRequest.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, templateName, version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1CreateApplicationTemplateRequest {\n");
    
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    templateName: ").append(toIndentedString(templateName)).append("\n");
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

