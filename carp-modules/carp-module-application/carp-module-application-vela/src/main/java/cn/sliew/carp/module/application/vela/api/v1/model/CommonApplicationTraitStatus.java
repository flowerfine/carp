package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CommonApplicationTraitStatus
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class CommonApplicationTraitStatus   {
  @JsonProperty("healthy")
  private Boolean healthy = null;

  @JsonProperty("message")
  private String message = null;

  @JsonProperty("type")
  private String type = null;

  public CommonApplicationTraitStatus healthy(Boolean healthy) {
    this.healthy = healthy;
    return this;
  }

  /**
   * Get healthy
   * @return healthy
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Boolean isHealthy() {
    return healthy;
  }

  public void setHealthy(Boolean healthy) {
    this.healthy = healthy;
  }

  public CommonApplicationTraitStatus message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   * @return message
  **/
  @ApiModelProperty(value = "")


  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public CommonApplicationTraitStatus type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommonApplicationTraitStatus commonApplicationTraitStatus = (CommonApplicationTraitStatus) o;
    return Objects.equals(this.healthy, commonApplicationTraitStatus.healthy) &&
        Objects.equals(this.message, commonApplicationTraitStatus.message) &&
        Objects.equals(this.type, commonApplicationTraitStatus.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(healthy, message, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommonApplicationTraitStatus {\n");
    
    sb.append("    healthy: ").append(toIndentedString(healthy)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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

