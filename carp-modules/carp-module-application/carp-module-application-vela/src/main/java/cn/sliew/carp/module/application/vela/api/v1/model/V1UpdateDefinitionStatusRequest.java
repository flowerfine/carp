package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1UpdateDefinitionStatusRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1UpdateDefinitionStatusRequest   {
  @JsonProperty("hiddenInUI")
  private Boolean hiddenInUI = null;

  @JsonProperty("type")
  private String type = null;

  public V1UpdateDefinitionStatusRequest hiddenInUI(Boolean hiddenInUI) {
    this.hiddenInUI = hiddenInUI;
    return this;
  }

  /**
   * Get hiddenInUI
   * @return hiddenInUI
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Boolean isHiddenInUI() {
    return hiddenInUI;
  }

  public void setHiddenInUI(Boolean hiddenInUI) {
    this.hiddenInUI = hiddenInUI;
  }

  public V1UpdateDefinitionStatusRequest type(String type) {
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
    V1UpdateDefinitionStatusRequest v1UpdateDefinitionStatusRequest = (V1UpdateDefinitionStatusRequest) o;
    return Objects.equals(this.hiddenInUI, v1UpdateDefinitionStatusRequest.hiddenInUI) &&
        Objects.equals(this.type, v1UpdateDefinitionStatusRequest.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hiddenInUI, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1UpdateDefinitionStatusRequest {\n");
    
    sb.append("    hiddenInUI: ").append(toIndentedString(hiddenInUI)).append("\n");
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

