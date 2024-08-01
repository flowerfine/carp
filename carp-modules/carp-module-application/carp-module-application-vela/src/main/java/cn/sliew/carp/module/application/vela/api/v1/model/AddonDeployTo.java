package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AddonDeployTo
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class AddonDeployTo   {
  @JsonProperty("disableControlPlane")
  private Boolean disableControlPlane = null;

  @JsonProperty("runtimeCluster")
  private Boolean runtimeCluster = null;

  public AddonDeployTo disableControlPlane(Boolean disableControlPlane) {
    this.disableControlPlane = disableControlPlane;
    return this;
  }

  /**
   * Get disableControlPlane
   * @return disableControlPlane
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Boolean isDisableControlPlane() {
    return disableControlPlane;
  }

  public void setDisableControlPlane(Boolean disableControlPlane) {
    this.disableControlPlane = disableControlPlane;
  }

  public AddonDeployTo runtimeCluster(Boolean runtimeCluster) {
    this.runtimeCluster = runtimeCluster;
    return this;
  }

  /**
   * Get runtimeCluster
   * @return runtimeCluster
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Boolean isRuntimeCluster() {
    return runtimeCluster;
  }

  public void setRuntimeCluster(Boolean runtimeCluster) {
    this.runtimeCluster = runtimeCluster;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddonDeployTo addonDeployTo = (AddonDeployTo) o;
    return Objects.equals(this.disableControlPlane, addonDeployTo.disableControlPlane) &&
        Objects.equals(this.runtimeCluster, addonDeployTo.runtimeCluster);
  }

  @Override
  public int hashCode() {
    return Objects.hash(disableControlPlane, runtimeCluster);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddonDeployTo {\n");
    
    sb.append("    disableControlPlane: ").append(toIndentedString(disableControlPlane)).append("\n");
    sb.append("    runtimeCluster: ").append(toIndentedString(runtimeCluster)).append("\n");
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

