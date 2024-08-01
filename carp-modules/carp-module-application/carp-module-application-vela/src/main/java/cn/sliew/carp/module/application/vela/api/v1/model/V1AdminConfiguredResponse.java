package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1AdminConfiguredResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1AdminConfiguredResponse   {
  @JsonProperty("configured")
  private Boolean configured = null;

  public V1AdminConfiguredResponse configured(Boolean configured) {
    this.configured = configured;
    return this;
  }

  /**
   * Get configured
   * @return configured
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Boolean isConfigured() {
    return configured;
  }

  public void setConfigured(Boolean configured) {
    this.configured = configured;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1AdminConfiguredResponse v1AdminConfiguredResponse = (V1AdminConfiguredResponse) o;
    return Objects.equals(this.configured, v1AdminConfiguredResponse.configured);
  }

  @Override
  public int hashCode() {
    return Objects.hash(configured);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1AdminConfiguredResponse {\n");
    
    sb.append("    configured: ").append(toIndentedString(configured)).append("\n");
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

