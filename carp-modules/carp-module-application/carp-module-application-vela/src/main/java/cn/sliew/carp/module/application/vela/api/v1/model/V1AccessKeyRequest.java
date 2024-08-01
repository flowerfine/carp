package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1AccessKeyRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1AccessKeyRequest   {
  @JsonProperty("accessKeyID")
  private String accessKeyID = null;

  @JsonProperty("accessKeySecret")
  private String accessKeySecret = null;

  public V1AccessKeyRequest accessKeyID(String accessKeyID) {
    this.accessKeyID = accessKeyID;
    return this;
  }

  /**
   * Get accessKeyID
   * @return accessKeyID
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getAccessKeyID() {
    return accessKeyID;
  }

  public void setAccessKeyID(String accessKeyID) {
    this.accessKeyID = accessKeyID;
  }

  public V1AccessKeyRequest accessKeySecret(String accessKeySecret) {
    this.accessKeySecret = accessKeySecret;
    return this;
  }

  /**
   * Get accessKeySecret
   * @return accessKeySecret
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getAccessKeySecret() {
    return accessKeySecret;
  }

  public void setAccessKeySecret(String accessKeySecret) {
    this.accessKeySecret = accessKeySecret;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1AccessKeyRequest v1AccessKeyRequest = (V1AccessKeyRequest) o;
    return Objects.equals(this.accessKeyID, v1AccessKeyRequest.accessKeyID) &&
        Objects.equals(this.accessKeySecret, v1AccessKeyRequest.accessKeySecret);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accessKeyID, accessKeySecret);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1AccessKeyRequest {\n");
    
    sb.append("    accessKeyID: ").append(toIndentedString(accessKeyID)).append("\n");
    sb.append("    accessKeySecret: ").append(toIndentedString(accessKeySecret)).append("\n");
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

