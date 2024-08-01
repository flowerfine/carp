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
 * V1ListAddonRegistryResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ListAddonRegistryResponse   {
  @JsonProperty("registries")
  @Valid
  private List<V1AddonRegistry> registries = new ArrayList<V1AddonRegistry>();

  public V1ListAddonRegistryResponse registries(List<V1AddonRegistry> registries) {
    this.registries = registries;
    return this;
  }

  public V1ListAddonRegistryResponse addRegistriesItem(V1AddonRegistry registriesItem) {
    this.registries.add(registriesItem);
    return this;
  }

  /**
   * Get registries
   * @return registries
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1AddonRegistry> getRegistries() {
    return registries;
  }

  public void setRegistries(List<V1AddonRegistry> registries) {
    this.registries = registries;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1ListAddonRegistryResponse v1ListAddonRegistryResponse = (V1ListAddonRegistryResponse) o;
    return Objects.equals(this.registries, v1ListAddonRegistryResponse.registries);
  }

  @Override
  public int hashCode() {
    return Objects.hash(registries);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ListAddonRegistryResponse {\n");
    
    sb.append("    registries: ").append(toIndentedString(registries)).append("\n");
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

