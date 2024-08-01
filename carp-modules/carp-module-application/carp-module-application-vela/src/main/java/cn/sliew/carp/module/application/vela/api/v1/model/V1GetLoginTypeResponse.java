package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1GetLoginTypeResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1GetLoginTypeResponse   {
  @JsonProperty("loginType")
  private String loginType = null;

  public V1GetLoginTypeResponse loginType(String loginType) {
    this.loginType = loginType;
    return this;
  }

  /**
   * Get loginType
   * @return loginType
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getLoginType() {
    return loginType;
  }

  public void setLoginType(String loginType) {
    this.loginType = loginType;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1GetLoginTypeResponse v1GetLoginTypeResponse = (V1GetLoginTypeResponse) o;
    return Objects.equals(this.loginType, v1GetLoginTypeResponse.loginType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(loginType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1GetLoginTypeResponse {\n");
    
    sb.append("    loginType: ").append(toIndentedString(loginType)).append("\n");
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

