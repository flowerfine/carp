package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1ListPluginResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ListPluginResponse   {
  @JsonProperty("plugins")
  @Valid
  private List<V1PluginDTO> plugins = new ArrayList<V1PluginDTO>();

  public V1ListPluginResponse plugins(List<V1PluginDTO> plugins) {
    this.plugins = plugins;
    return this;
  }

  public V1ListPluginResponse addPluginsItem(V1PluginDTO pluginsItem) {
    this.plugins.add(pluginsItem);
    return this;
  }

  /**
   * Get plugins
   * @return plugins
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1PluginDTO> getPlugins() {
    return plugins;
  }

  public void setPlugins(List<V1PluginDTO> plugins) {
    this.plugins = plugins;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1ListPluginResponse v1ListPluginResponse = (V1ListPluginResponse) o;
    return Objects.equals(this.plugins, v1ListPluginResponse.plugins);
  }

  @Override
  public int hashCode() {
    return Objects.hash(plugins);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ListPluginResponse {\n");
    
    sb.append("    plugins: ").append(toIndentedString(plugins)).append("\n");
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

