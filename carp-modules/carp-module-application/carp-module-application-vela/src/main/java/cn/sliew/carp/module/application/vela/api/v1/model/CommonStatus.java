package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CommonStatus
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class CommonStatus   {
  @JsonProperty("customStatus")
  private String customStatus = null;

  @JsonProperty("healthPolicy")
  private String healthPolicy = null;

  public CommonStatus customStatus(String customStatus) {
    this.customStatus = customStatus;
    return this;
  }

  /**
   * Get customStatus
   * @return customStatus
  **/
  @ApiModelProperty(value = "")


  public String getCustomStatus() {
    return customStatus;
  }

  public void setCustomStatus(String customStatus) {
    this.customStatus = customStatus;
  }

  public CommonStatus healthPolicy(String healthPolicy) {
    this.healthPolicy = healthPolicy;
    return this;
  }

  /**
   * Get healthPolicy
   * @return healthPolicy
  **/
  @ApiModelProperty(value = "")


  public String getHealthPolicy() {
    return healthPolicy;
  }

  public void setHealthPolicy(String healthPolicy) {
    this.healthPolicy = healthPolicy;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommonStatus commonStatus = (CommonStatus) o;
    return Objects.equals(this.customStatus, commonStatus.customStatus) &&
        Objects.equals(this.healthPolicy, commonStatus.healthPolicy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(customStatus, healthPolicy);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommonStatus {\n");
    
    sb.append("    customStatus: ").append(toIndentedString(customStatus)).append("\n");
    sb.append("    healthPolicy: ").append(toIndentedString(healthPolicy)).append("\n");
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

