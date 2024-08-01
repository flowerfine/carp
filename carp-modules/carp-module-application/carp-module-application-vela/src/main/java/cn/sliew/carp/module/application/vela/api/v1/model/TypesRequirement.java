package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TypesRequirement
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class TypesRequirement   {
  @JsonProperty("velauxVersion")
  private String velauxVersion = null;

  public TypesRequirement velauxVersion(String velauxVersion) {
    this.velauxVersion = velauxVersion;
    return this;
  }

  /**
   * Get velauxVersion
   * @return velauxVersion
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getVelauxVersion() {
    return velauxVersion;
  }

  public void setVelauxVersion(String velauxVersion) {
    this.velauxVersion = velauxVersion;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TypesRequirement typesRequirement = (TypesRequirement) o;
    return Objects.equals(this.velauxVersion, typesRequirement.velauxVersion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(velauxVersion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TypesRequirement {\n");
    
    sb.append("    velauxVersion: ").append(toIndentedString(velauxVersion)).append("\n");
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

