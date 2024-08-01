package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CommonWorkloadTypeDescriptor
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class CommonWorkloadTypeDescriptor   {
  @JsonProperty("definition")
  private CommonWorkloadGVK definition = null;

  @JsonProperty("type")
  private String type = null;

  public CommonWorkloadTypeDescriptor definition(CommonWorkloadGVK definition) {
    this.definition = definition;
    return this;
  }

  /**
   * Get definition
   * @return definition
  **/
  @ApiModelProperty(value = "")

  @Valid

  public CommonWorkloadGVK getDefinition() {
    return definition;
  }

  public void setDefinition(CommonWorkloadGVK definition) {
    this.definition = definition;
  }

  public CommonWorkloadTypeDescriptor type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
  **/
  @ApiModelProperty(value = "")


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
    CommonWorkloadTypeDescriptor commonWorkloadTypeDescriptor = (CommonWorkloadTypeDescriptor) o;
    return Objects.equals(this.definition, commonWorkloadTypeDescriptor.definition) &&
        Objects.equals(this.type, commonWorkloadTypeDescriptor.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(definition, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommonWorkloadTypeDescriptor {\n");
    
    sb.append("    definition: ").append(toIndentedString(definition)).append("\n");
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

