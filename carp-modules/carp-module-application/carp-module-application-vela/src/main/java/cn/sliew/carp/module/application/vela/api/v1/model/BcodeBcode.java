package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * BcodeBcode
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class BcodeBcode   {
  @JsonProperty("BusinessCode")
  private Integer businessCode = null;

  @JsonProperty("Message")
  private String message = null;

  public BcodeBcode businessCode(Integer businessCode) {
    this.businessCode = businessCode;
    return this;
  }

  /**
   * Get businessCode
   * @return businessCode
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getBusinessCode() {
    return businessCode;
  }

  public void setBusinessCode(Integer businessCode) {
    this.businessCode = businessCode;
  }

  public BcodeBcode message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   * @return message
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BcodeBcode bcodeBcode = (BcodeBcode) o;
    return Objects.equals(this.businessCode, bcodeBcode.businessCode) &&
        Objects.equals(this.message, bcodeBcode.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(businessCode, message);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BcodeBcode {\n");
    
    sb.append("    businessCode: ").append(toIndentedString(businessCode)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
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

