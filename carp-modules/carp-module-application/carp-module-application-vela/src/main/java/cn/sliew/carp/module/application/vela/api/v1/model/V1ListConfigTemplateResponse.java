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
 * V1ListConfigTemplateResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ListConfigTemplateResponse   {
  @JsonProperty("templates")
  @Valid
  private List<V1ConfigTemplate> templates = new ArrayList<V1ConfigTemplate>();

  public V1ListConfigTemplateResponse templates(List<V1ConfigTemplate> templates) {
    this.templates = templates;
    return this;
  }

  public V1ListConfigTemplateResponse addTemplatesItem(V1ConfigTemplate templatesItem) {
    this.templates.add(templatesItem);
    return this;
  }

  /**
   * Get templates
   * @return templates
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1ConfigTemplate> getTemplates() {
    return templates;
  }

  public void setTemplates(List<V1ConfigTemplate> templates) {
    this.templates = templates;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1ListConfigTemplateResponse v1ListConfigTemplateResponse = (V1ListConfigTemplateResponse) o;
    return Objects.equals(this.templates, v1ListConfigTemplateResponse.templates);
  }

  @Override
  public int hashCode() {
    return Objects.hash(templates);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ListConfigTemplateResponse {\n");
    
    sb.append("    templates: ").append(toIndentedString(templates)).append("\n");
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

