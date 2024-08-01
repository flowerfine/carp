package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CommonSchematic
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class CommonSchematic   {
  @JsonProperty("cue")
  private CommonCUE cue = null;

  @JsonProperty("terraform")
  private CommonTerraform terraform = null;

  public CommonSchematic cue(CommonCUE cue) {
    this.cue = cue;
    return this;
  }

  /**
   * Get cue
   * @return cue
  **/
  @ApiModelProperty(value = "")

  @Valid

  public CommonCUE getCue() {
    return cue;
  }

  public void setCue(CommonCUE cue) {
    this.cue = cue;
  }

  public CommonSchematic terraform(CommonTerraform terraform) {
    this.terraform = terraform;
    return this;
  }

  /**
   * Get terraform
   * @return terraform
  **/
  @ApiModelProperty(value = "")

  @Valid

  public CommonTerraform getTerraform() {
    return terraform;
  }

  public void setTerraform(CommonTerraform terraform) {
    this.terraform = terraform;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommonSchematic commonSchematic = (CommonSchematic) o;
    return Objects.equals(this.cue, commonSchematic.cue) &&
        Objects.equals(this.terraform, commonSchematic.terraform);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cue, terraform);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommonSchematic {\n");
    
    sb.append("    cue: ").append(toIndentedString(cue)).append("\n");
    sb.append("    terraform: ").append(toIndentedString(terraform)).append("\n");
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

