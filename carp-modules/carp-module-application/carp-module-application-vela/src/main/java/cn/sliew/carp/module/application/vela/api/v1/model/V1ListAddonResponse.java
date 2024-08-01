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
 * V1ListAddonResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ListAddonResponse   {
  @JsonProperty("addons")
  @Valid
  private List<V1AddonInfo> addons = new ArrayList<V1AddonInfo>();

  @JsonProperty("message")
  private String message = null;

  public V1ListAddonResponse addons(List<V1AddonInfo> addons) {
    this.addons = addons;
    return this;
  }

  public V1ListAddonResponse addAddonsItem(V1AddonInfo addonsItem) {
    this.addons.add(addonsItem);
    return this;
  }

  /**
   * Get addons
   * @return addons
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1AddonInfo> getAddons() {
    return addons;
  }

  public void setAddons(List<V1AddonInfo> addons) {
    this.addons = addons;
  }

  public V1ListAddonResponse message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   * @return message
  **/
  @ApiModelProperty(value = "")


  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1ListAddonResponse v1ListAddonResponse = (V1ListAddonResponse) o;
    return Objects.equals(this.addons, v1ListAddonResponse.addons) &&
        Objects.equals(this.message, v1ListAddonResponse.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(addons, message);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ListAddonResponse {\n");
    
    sb.append("    addons: ").append(toIndentedString(addons)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
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

