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
 * V1ListApplicationEnvBinding
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ListApplicationEnvBinding   {
  @JsonProperty("envBindings")
  @Valid
  private List<V1EnvBindingBase> envBindings = new ArrayList<V1EnvBindingBase>();

  public V1ListApplicationEnvBinding envBindings(List<V1EnvBindingBase> envBindings) {
    this.envBindings = envBindings;
    return this;
  }

  public V1ListApplicationEnvBinding addEnvBindingsItem(V1EnvBindingBase envBindingsItem) {
    this.envBindings.add(envBindingsItem);
    return this;
  }

  /**
   * Get envBindings
   * @return envBindings
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1EnvBindingBase> getEnvBindings() {
    return envBindings;
  }

  public void setEnvBindings(List<V1EnvBindingBase> envBindings) {
    this.envBindings = envBindings;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1ListApplicationEnvBinding v1ListApplicationEnvBinding = (V1ListApplicationEnvBinding) o;
    return Objects.equals(this.envBindings, v1ListApplicationEnvBinding.envBindings);
  }

  @Override
  public int hashCode() {
    return Objects.hash(envBindings);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ListApplicationEnvBinding {\n");
    
    sb.append("    envBindings: ").append(toIndentedString(envBindings)).append("\n");
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

