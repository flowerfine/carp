package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;

import cn.sliew.carp.module.application.vela.api.v1.model.common.CommonAppStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1ApplicationStatusResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ApplicationStatusResponse   {
  @JsonProperty("envName")
  private String envName = null;

  @JsonProperty("status")
  private CommonAppStatus status = null;

  public V1ApplicationStatusResponse envName(String envName) {
    this.envName = envName;
    return this;
  }

  /**
   * Get envName
   * @return envName
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getEnvName() {
    return envName;
  }

  public void setEnvName(String envName) {
    this.envName = envName;
  }

  public V1ApplicationStatusResponse status(CommonAppStatus status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public CommonAppStatus getStatus() {
    return status;
  }

  public void setStatus(CommonAppStatus status) {
    this.status = status;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1ApplicationStatusResponse v1ApplicationStatusResponse = (V1ApplicationStatusResponse) o;
    return Objects.equals(this.envName, v1ApplicationStatusResponse.envName) &&
        Objects.equals(this.status, v1ApplicationStatusResponse.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(envName, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ApplicationStatusResponse {\n");
    
    sb.append("    envName: ").append(toIndentedString(envName)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

