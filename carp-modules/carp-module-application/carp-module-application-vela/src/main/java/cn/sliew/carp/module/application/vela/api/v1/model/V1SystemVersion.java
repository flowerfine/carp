package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1SystemVersion
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1SystemVersion   {
  @JsonProperty("gitVersion")
  private String gitVersion = null;

  @JsonProperty("velaVersion")
  private String velaVersion = null;

  public V1SystemVersion gitVersion(String gitVersion) {
    this.gitVersion = gitVersion;
    return this;
  }

  /**
   * Get gitVersion
   * @return gitVersion
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getGitVersion() {
    return gitVersion;
  }

  public void setGitVersion(String gitVersion) {
    this.gitVersion = gitVersion;
  }

  public V1SystemVersion velaVersion(String velaVersion) {
    this.velaVersion = velaVersion;
    return this;
  }

  /**
   * Get velaVersion
   * @return velaVersion
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getVelaVersion() {
    return velaVersion;
  }

  public void setVelaVersion(String velaVersion) {
    this.velaVersion = velaVersion;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1SystemVersion v1SystemVersion = (V1SystemVersion) o;
    return Objects.equals(this.gitVersion, v1SystemVersion.gitVersion) &&
        Objects.equals(this.velaVersion, v1SystemVersion.velaVersion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(gitVersion, velaVersion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1SystemVersion {\n");
    
    sb.append("    gitVersion: ").append(toIndentedString(gitVersion)).append("\n");
    sb.append("    velaVersion: ").append(toIndentedString(velaVersion)).append("\n");
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

