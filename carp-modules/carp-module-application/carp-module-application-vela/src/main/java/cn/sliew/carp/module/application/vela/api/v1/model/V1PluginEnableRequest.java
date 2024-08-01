package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1PluginEnableRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1PluginEnableRequest   {
  @JsonProperty("jsonData")
  private Object jsonData = null;

  @JsonProperty("secureJsonData")
  private Object secureJsonData = null;

  public V1PluginEnableRequest jsonData(Object jsonData) {
    this.jsonData = jsonData;
    return this;
  }

  /**
   * Get jsonData
   * @return jsonData
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Object getJsonData() {
    return jsonData;
  }

  public void setJsonData(Object jsonData) {
    this.jsonData = jsonData;
  }

  public V1PluginEnableRequest secureJsonData(Object secureJsonData) {
    this.secureJsonData = secureJsonData;
    return this;
  }

  /**
   * Get secureJsonData
   * @return secureJsonData
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Object getSecureJsonData() {
    return secureJsonData;
  }

  public void setSecureJsonData(Object secureJsonData) {
    this.secureJsonData = secureJsonData;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1PluginEnableRequest v1PluginEnableRequest = (V1PluginEnableRequest) o;
    return Objects.equals(this.jsonData, v1PluginEnableRequest.jsonData) &&
        Objects.equals(this.secureJsonData, v1PluginEnableRequest.secureJsonData);
  }

  @Override
  public int hashCode() {
    return Objects.hash(jsonData, secureJsonData);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1PluginEnableRequest {\n");
    
    sb.append("    jsonData: ").append(toIndentedString(jsonData)).append("\n");
    sb.append("    secureJsonData: ").append(toIndentedString(secureJsonData)).append("\n");
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

