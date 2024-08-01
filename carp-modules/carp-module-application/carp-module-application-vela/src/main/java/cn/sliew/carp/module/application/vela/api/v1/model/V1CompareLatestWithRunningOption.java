package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1CompareLatestWithRunningOption
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1CompareLatestWithRunningOption   {
  @JsonProperty("env")
  private String env = null;

  public V1CompareLatestWithRunningOption env(String env) {
    this.env = env;
    return this;
  }

  /**
   * Get env
   * @return env
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getEnv() {
    return env;
  }

  public void setEnv(String env) {
    this.env = env;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1CompareLatestWithRunningOption v1CompareLatestWithRunningOption = (V1CompareLatestWithRunningOption) o;
    return Objects.equals(this.env, v1CompareLatestWithRunningOption.env);
  }

  @Override
  public int hashCode() {
    return Objects.hash(env);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1CompareLatestWithRunningOption {\n");
    
    sb.append("    env: ").append(toIndentedString(env)).append("\n");
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

