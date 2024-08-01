package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AddonOSSAddonSource
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class AddonOSSAddonSource   {
  @JsonProperty("bucket")
  private String bucket = null;

  @JsonProperty("end_point")
  private String endPoint = null;

  @JsonProperty("path")
  private String path = null;

  public AddonOSSAddonSource bucket(String bucket) {
    this.bucket = bucket;
    return this;
  }

  /**
   * Get bucket
   * @return bucket
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getBucket() {
    return bucket;
  }

  public void setBucket(String bucket) {
    this.bucket = bucket;
  }

  public AddonOSSAddonSource endPoint(String endPoint) {
    this.endPoint = endPoint;
    return this;
  }

  /**
   * Get endPoint
   * @return endPoint
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getEndPoint() {
    return endPoint;
  }

  public void setEndPoint(String endPoint) {
    this.endPoint = endPoint;
  }

  public AddonOSSAddonSource path(String path) {
    this.path = path;
    return this;
  }

  /**
   * Get path
   * @return path
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddonOSSAddonSource addonOSSAddonSource = (AddonOSSAddonSource) o;
    return Objects.equals(this.bucket, addonOSSAddonSource.bucket) &&
        Objects.equals(this.endPoint, addonOSSAddonSource.endPoint) &&
        Objects.equals(this.path, addonOSSAddonSource.path);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bucket, endPoint, path);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddonOSSAddonSource {\n");
    
    sb.append("    bucket: ").append(toIndentedString(bucket)).append("\n");
    sb.append("    endPoint: ").append(toIndentedString(endPoint)).append("\n");
    sb.append("    path: ").append(toIndentedString(path)).append("\n");
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

