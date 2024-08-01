package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TypesLogos
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class TypesLogos   {
  @JsonProperty("large")
  private String large = null;

  @JsonProperty("small")
  private String small = null;

  public TypesLogos large(String large) {
    this.large = large;
    return this;
  }

  /**
   * Get large
   * @return large
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getLarge() {
    return large;
  }

  public void setLarge(String large) {
    this.large = large;
  }

  public TypesLogos small(String small) {
    this.small = small;
    return this;
  }

  /**
   * Get small
   * @return small
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getSmall() {
    return small;
  }

  public void setSmall(String small) {
    this.small = small;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TypesLogos typesLogos = (TypesLogos) o;
    return Objects.equals(this.large, typesLogos.large) &&
        Objects.equals(this.small, typesLogos.small);
  }

  @Override
  public int hashCode() {
    return Objects.hash(large, small);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TypesLogos {\n");
    
    sb.append("    large: ").append(toIndentedString(large)).append("\n");
    sb.append("    small: ").append(toIndentedString(small)).append("\n");
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

