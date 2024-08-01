package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1beta1WorkflowStepDefinitionSpec
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1beta1WorkflowStepDefinitionSpec   {
  @JsonProperty("definitionRef")
  private CommonDefinitionReference definitionRef = null;

  @JsonProperty("schematic")
  private CommonSchematic schematic = null;

  public V1beta1WorkflowStepDefinitionSpec definitionRef(CommonDefinitionReference definitionRef) {
    this.definitionRef = definitionRef;
    return this;
  }

  /**
   * Get definitionRef
   * @return definitionRef
  **/
  @ApiModelProperty(value = "")

  @Valid

  public CommonDefinitionReference getDefinitionRef() {
    return definitionRef;
  }

  public void setDefinitionRef(CommonDefinitionReference definitionRef) {
    this.definitionRef = definitionRef;
  }

  public V1beta1WorkflowStepDefinitionSpec schematic(CommonSchematic schematic) {
    this.schematic = schematic;
    return this;
  }

  /**
   * Get schematic
   * @return schematic
  **/
  @ApiModelProperty(value = "")

  @Valid

  public CommonSchematic getSchematic() {
    return schematic;
  }

  public void setSchematic(CommonSchematic schematic) {
    this.schematic = schematic;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1beta1WorkflowStepDefinitionSpec v1beta1WorkflowStepDefinitionSpec = (V1beta1WorkflowStepDefinitionSpec) o;
    return Objects.equals(this.definitionRef, v1beta1WorkflowStepDefinitionSpec.definitionRef) &&
        Objects.equals(this.schematic, v1beta1WorkflowStepDefinitionSpec.schematic);
  }

  @Override
  public int hashCode() {
    return Objects.hash(definitionRef, schematic);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1beta1WorkflowStepDefinitionSpec {\n");
    
    sb.append("    definitionRef: ").append(toIndentedString(definitionRef)).append("\n");
    sb.append("    schematic: ").append(toIndentedString(schematic)).append("\n");
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

