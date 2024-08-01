package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1CreateCloudClusterResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1CreateCloudClusterResponse   {
  @JsonProperty("clusterID")
  private String clusterID = null;

  @JsonProperty("clusterName")
  private String clusterName = null;

  @JsonProperty("status")
  private String status = null;

  public V1CreateCloudClusterResponse clusterID(String clusterID) {
    this.clusterID = clusterID;
    return this;
  }

  /**
   * Get clusterID
   * @return clusterID
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getClusterID() {
    return clusterID;
  }

  public void setClusterID(String clusterID) {
    this.clusterID = clusterID;
  }

  public V1CreateCloudClusterResponse clusterName(String clusterName) {
    this.clusterName = clusterName;
    return this;
  }

  /**
   * Get clusterName
   * @return clusterName
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getClusterName() {
    return clusterName;
  }

  public void setClusterName(String clusterName) {
    this.clusterName = clusterName;
  }

  public V1CreateCloudClusterResponse status(String status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1CreateCloudClusterResponse v1CreateCloudClusterResponse = (V1CreateCloudClusterResponse) o;
    return Objects.equals(this.clusterID, v1CreateCloudClusterResponse.clusterID) &&
        Objects.equals(this.clusterName, v1CreateCloudClusterResponse.clusterName) &&
        Objects.equals(this.status, v1CreateCloudClusterResponse.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clusterID, clusterName, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1CreateCloudClusterResponse {\n");
    
    sb.append("    clusterID: ").append(toIndentedString(clusterID)).append("\n");
    sb.append("    clusterName: ").append(toIndentedString(clusterName)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

