package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;

import cn.sliew.carp.module.application.vela.api.v1.model.common.CommonHTTPOption;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1InstallPluginRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1InstallPluginRequest   {
  @JsonProperty("disable")
  private Boolean disable = null;

  @JsonProperty("options")
  private CommonHTTPOption options = null;

  @JsonProperty("url")
  private String url = null;

  public V1InstallPluginRequest disable(Boolean disable) {
    this.disable = disable;
    return this;
  }

  /**
   * Get disable
   * @return disable
  **/
  @ApiModelProperty(value = "")


  public Boolean isDisable() {
    return disable;
  }

  public void setDisable(Boolean disable) {
    this.disable = disable;
  }

  public V1InstallPluginRequest options(CommonHTTPOption options) {
    this.options = options;
    return this;
  }

  /**
   * Get options
   * @return options
  **/
  @ApiModelProperty(value = "")

  @Valid

  public CommonHTTPOption getOptions() {
    return options;
  }

  public void setOptions(CommonHTTPOption options) {
    this.options = options;
  }

  public V1InstallPluginRequest url(String url) {
    this.url = url;
    return this;
  }

  /**
   * Get url
   * @return url
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1InstallPluginRequest v1InstallPluginRequest = (V1InstallPluginRequest) o;
    return Objects.equals(this.disable, v1InstallPluginRequest.disable) &&
        Objects.equals(this.options, v1InstallPluginRequest.options) &&
        Objects.equals(this.url, v1InstallPluginRequest.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(disable, options, url);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1InstallPluginRequest {\n");
    
    sb.append("    disable: ").append(toIndentedString(disable)).append("\n");
    sb.append("    options: ").append(toIndentedString(options)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
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

