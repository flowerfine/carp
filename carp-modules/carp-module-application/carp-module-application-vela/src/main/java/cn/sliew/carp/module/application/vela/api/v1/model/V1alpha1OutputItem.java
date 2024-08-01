package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1alpha1OutputItem
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1alpha1OutputItem   {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("valueFrom")
  private String valueFrom = null;

  public V1alpha1OutputItem name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public V1alpha1OutputItem valueFrom(String valueFrom) {
    this.valueFrom = valueFrom;
    return this;
  }

  /**
   * Get valueFrom
   * @return valueFrom
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getValueFrom() {
    return valueFrom;
  }

  public void setValueFrom(String valueFrom) {
    this.valueFrom = valueFrom;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1alpha1OutputItem v1alpha1OutputItem = (V1alpha1OutputItem) o;
    return Objects.equals(this.name, v1alpha1OutputItem.name) &&
        Objects.equals(this.valueFrom, v1alpha1OutputItem.valueFrom);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, valueFrom);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1alpha1OutputItem {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    valueFrom: ").append(toIndentedString(valueFrom)).append("\n");
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

