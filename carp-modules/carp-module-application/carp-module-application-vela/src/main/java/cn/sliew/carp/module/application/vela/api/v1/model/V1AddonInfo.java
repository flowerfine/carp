package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;

import cn.sliew.carp.module.application.vela.api.v1.model.addon.AddonMeta;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1AddonInfo
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1AddonInfo   {
  @JsonProperty("Meta")
  private AddonMeta meta = null;

  @JsonProperty("registryName")
  private String registryName = null;

  public V1AddonInfo meta(AddonMeta meta) {
    this.meta = meta;
    return this;
  }

  /**
   * Get meta
   * @return meta
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public AddonMeta getMeta() {
    return meta;
  }

  public void setMeta(AddonMeta meta) {
    this.meta = meta;
  }

  public V1AddonInfo registryName(String registryName) {
    this.registryName = registryName;
    return this;
  }

  /**
   * Get registryName
   * @return registryName
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getRegistryName() {
    return registryName;
  }

  public void setRegistryName(String registryName) {
    this.registryName = registryName;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1AddonInfo v1AddonInfo = (V1AddonInfo) o;
    return Objects.equals(this.meta, v1AddonInfo.meta) &&
        Objects.equals(this.registryName, v1AddonInfo.registryName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(meta, registryName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1AddonInfo {\n");
    
    sb.append("    meta: ").append(toIndentedString(meta)).append("\n");
    sb.append("    registryName: ").append(toIndentedString(registryName)).append("\n");
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

