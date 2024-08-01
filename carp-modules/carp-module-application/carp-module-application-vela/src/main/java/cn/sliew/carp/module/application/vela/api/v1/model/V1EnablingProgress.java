package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1EnablingProgress
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1EnablingProgress   {
  @JsonProperty("enabled_components")
  private Integer enabledComponents = null;

  @JsonProperty("total_components")
  private Integer totalComponents = null;

  public V1EnablingProgress enabledComponents(Integer enabledComponents) {
    this.enabledComponents = enabledComponents;
    return this;
  }

  /**
   * Get enabledComponents
   * @return enabledComponents
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getEnabledComponents() {
    return enabledComponents;
  }

  public void setEnabledComponents(Integer enabledComponents) {
    this.enabledComponents = enabledComponents;
  }

  public V1EnablingProgress totalComponents(Integer totalComponents) {
    this.totalComponents = totalComponents;
    return this;
  }

  /**
   * Get totalComponents
   * @return totalComponents
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getTotalComponents() {
    return totalComponents;
  }

  public void setTotalComponents(Integer totalComponents) {
    this.totalComponents = totalComponents;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1EnablingProgress v1EnablingProgress = (V1EnablingProgress) o;
    return Objects.equals(this.enabledComponents, v1EnablingProgress.enabledComponents) &&
        Objects.equals(this.totalComponents, v1EnablingProgress.totalComponents);
  }

  @Override
  public int hashCode() {
    return Objects.hash(enabledComponents, totalComponents);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1EnablingProgress {\n");
    
    sb.append("    enabledComponents: ").append(toIndentedString(enabledComponents)).append("\n");
    sb.append("    totalComponents: ").append(toIndentedString(totalComponents)).append("\n");
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

