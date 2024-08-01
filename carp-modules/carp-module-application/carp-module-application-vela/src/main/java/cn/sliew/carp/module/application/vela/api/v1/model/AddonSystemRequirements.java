package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AddonSystemRequirements
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class AddonSystemRequirements   {
  @JsonProperty("kubernetes")
  private String kubernetes = null;

  @JsonProperty("vela")
  private String vela = null;

  public AddonSystemRequirements kubernetes(String kubernetes) {
    this.kubernetes = kubernetes;
    return this;
  }

  /**
   * Get kubernetes
   * @return kubernetes
  **/
  @ApiModelProperty(value = "")


  public String getKubernetes() {
    return kubernetes;
  }

  public void setKubernetes(String kubernetes) {
    this.kubernetes = kubernetes;
  }

  public AddonSystemRequirements vela(String vela) {
    this.vela = vela;
    return this;
  }

  /**
   * Get vela
   * @return vela
  **/
  @ApiModelProperty(value = "")


  public String getVela() {
    return vela;
  }

  public void setVela(String vela) {
    this.vela = vela;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddonSystemRequirements addonSystemRequirements = (AddonSystemRequirements) o;
    return Objects.equals(this.kubernetes, addonSystemRequirements.kubernetes) &&
        Objects.equals(this.vela, addonSystemRequirements.vela);
  }

  @Override
  public int hashCode() {
    return Objects.hash(kubernetes, vela);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddonSystemRequirements {\n");
    
    sb.append("    kubernetes: ").append(toIndentedString(kubernetes)).append("\n");
    sb.append("    vela: ").append(toIndentedString(vela)).append("\n");
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

