package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;

import cn.sliew.carp.module.application.vela.api.v1.model.common.CommonDefinitionReference;
import cn.sliew.carp.module.application.vela.api.v1.model.common.CommonSchematic;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1beta1PolicyDefinitionSpec
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1beta1PolicyDefinitionSpec   {
  @JsonProperty("definitionRef")
  private CommonDefinitionReference definitionRef = null;

  @JsonProperty("manageHealthCheck")
  private Boolean manageHealthCheck = null;

  @JsonProperty("schematic")
  private CommonSchematic schematic = null;

  public V1beta1PolicyDefinitionSpec definitionRef(CommonDefinitionReference definitionRef) {
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

  public V1beta1PolicyDefinitionSpec manageHealthCheck(Boolean manageHealthCheck) {
    this.manageHealthCheck = manageHealthCheck;
    return this;
  }

  /**
   * Get manageHealthCheck
   * @return manageHealthCheck
  **/
  @ApiModelProperty(value = "")


  public Boolean isManageHealthCheck() {
    return manageHealthCheck;
  }

  public void setManageHealthCheck(Boolean manageHealthCheck) {
    this.manageHealthCheck = manageHealthCheck;
  }

  public V1beta1PolicyDefinitionSpec schematic(CommonSchematic schematic) {
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
    V1beta1PolicyDefinitionSpec v1beta1PolicyDefinitionSpec = (V1beta1PolicyDefinitionSpec) o;
    return Objects.equals(this.definitionRef, v1beta1PolicyDefinitionSpec.definitionRef) &&
        Objects.equals(this.manageHealthCheck, v1beta1PolicyDefinitionSpec.manageHealthCheck) &&
        Objects.equals(this.schematic, v1beta1PolicyDefinitionSpec.schematic);
  }

  @Override
  public int hashCode() {
    return Objects.hash(definitionRef, manageHealthCheck, schematic);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1beta1PolicyDefinitionSpec {\n");
    
    sb.append("    definitionRef: ").append(toIndentedString(definitionRef)).append("\n");
    sb.append("    manageHealthCheck: ").append(toIndentedString(manageHealthCheck)).append("\n");
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

