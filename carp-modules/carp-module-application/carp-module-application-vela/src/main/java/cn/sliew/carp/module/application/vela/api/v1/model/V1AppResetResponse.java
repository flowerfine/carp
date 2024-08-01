package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1AppResetResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1AppResetResponse   {
  @JsonProperty("isReset")
  private Boolean isReset = null;

  public V1AppResetResponse isReset(Boolean isReset) {
    this.isReset = isReset;
    return this;
  }

  /**
   * Get isReset
   * @return isReset
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Boolean isIsReset() {
    return isReset;
  }

  public void setIsReset(Boolean isReset) {
    this.isReset = isReset;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1AppResetResponse v1AppResetResponse = (V1AppResetResponse) o;
    return Objects.equals(this.isReset, v1AppResetResponse.isReset);
  }

  @Override
  public int hashCode() {
    return Objects.hash(isReset);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1AppResetResponse {\n");
    
    sb.append("    isReset: ").append(toIndentedString(isReset)).append("\n");
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

