package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1Hash
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1Hash   {
  @JsonProperty("Algorithm")
  private String algorithm = null;

  @JsonProperty("Hex")
  private String hex = null;

  public V1Hash algorithm(String algorithm) {
    this.algorithm = algorithm;
    return this;
  }

  /**
   * Get algorithm
   * @return algorithm
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getAlgorithm() {
    return algorithm;
  }

  public void setAlgorithm(String algorithm) {
    this.algorithm = algorithm;
  }

  public V1Hash hex(String hex) {
    this.hex = hex;
    return this;
  }

  /**
   * Get hex
   * @return hex
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getHex() {
    return hex;
  }

  public void setHex(String hex) {
    this.hex = hex;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1Hash v1Hash = (V1Hash) o;
    return Objects.equals(this.algorithm, v1Hash.algorithm) &&
        Objects.equals(this.hex, v1Hash.hex);
  }

  @Override
  public int hashCode() {
    return Objects.hash(algorithm, hex);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1Hash {\n");
    
    sb.append("    algorithm: ").append(toIndentedString(algorithm)).append("\n");
    sb.append("    hex: ").append(toIndentedString(hex)).append("\n");
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

