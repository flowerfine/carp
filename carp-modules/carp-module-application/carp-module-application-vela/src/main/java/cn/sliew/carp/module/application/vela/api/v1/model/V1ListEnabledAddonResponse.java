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
 * V1ListEnabledAddonResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ListEnabledAddonResponse   {
  @JsonProperty("enabledAddons")
  @Valid
  private List<V1AddonBaseStatus> enabledAddons = new ArrayList<V1AddonBaseStatus>();

  public V1ListEnabledAddonResponse enabledAddons(List<V1AddonBaseStatus> enabledAddons) {
    this.enabledAddons = enabledAddons;
    return this;
  }

  public V1ListEnabledAddonResponse addEnabledAddonsItem(V1AddonBaseStatus enabledAddonsItem) {
    this.enabledAddons.add(enabledAddonsItem);
    return this;
  }

  /**
   * Get enabledAddons
   * @return enabledAddons
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1AddonBaseStatus> getEnabledAddons() {
    return enabledAddons;
  }

  public void setEnabledAddons(List<V1AddonBaseStatus> enabledAddons) {
    this.enabledAddons = enabledAddons;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1ListEnabledAddonResponse v1ListEnabledAddonResponse = (V1ListEnabledAddonResponse) o;
    return Objects.equals(this.enabledAddons, v1ListEnabledAddonResponse.enabledAddons);
  }

  @Override
  public int hashCode() {
    return Objects.hash(enabledAddons);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ListEnabledAddonResponse {\n");
    
    sb.append("    enabledAddons: ").append(toIndentedString(enabledAddons)).append("\n");
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

