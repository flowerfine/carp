package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1CreateCloudClusterRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1CreateCloudClusterRequest   {
  @JsonProperty("accessKeyID")
  private String accessKeyID = null;

  @JsonProperty("accessKeySecret")
  private String accessKeySecret = null;

  @JsonProperty("cpuCoresPerWorker")
  private Long cpuCoresPerWorker = null;

  @JsonProperty("memoryPerWorker")
  private Long memoryPerWorker = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("workerNumber")
  private Integer workerNumber = null;

  @JsonProperty("zone")
  private String zone = null;

  public V1CreateCloudClusterRequest accessKeyID(String accessKeyID) {
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

  public V1CreateCloudClusterRequest accessKeySecret(String accessKeySecret) {
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

  public V1CreateCloudClusterRequest cpuCoresPerWorker(Long cpuCoresPerWorker) {
    this.cpuCoresPerWorker = cpuCoresPerWorker;
    return this;
  }

  /**
   * Get cpuCoresPerWorker
   * @return cpuCoresPerWorker
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getCpuCoresPerWorker() {
    return cpuCoresPerWorker;
  }

  public void setCpuCoresPerWorker(Long cpuCoresPerWorker) {
    this.cpuCoresPerWorker = cpuCoresPerWorker;
  }

  public V1CreateCloudClusterRequest memoryPerWorker(Long memoryPerWorker) {
    this.memoryPerWorker = memoryPerWorker;
    return this;
  }

  /**
   * Get memoryPerWorker
   * @return memoryPerWorker
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getMemoryPerWorker() {
    return memoryPerWorker;
  }

  public void setMemoryPerWorker(Long memoryPerWorker) {
    this.memoryPerWorker = memoryPerWorker;
  }

  public V1CreateCloudClusterRequest name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public V1CreateCloudClusterRequest workerNumber(Integer workerNumber) {
    this.workerNumber = workerNumber;
    return this;
  }

  /**
   * Get workerNumber
   * @return workerNumber
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getWorkerNumber() {
    return workerNumber;
  }

  public void setWorkerNumber(Integer workerNumber) {
    this.workerNumber = workerNumber;
  }

  public V1CreateCloudClusterRequest zone(String zone) {
    this.zone = zone;
    return this;
  }

  /**
   * Get zone
   * @return zone
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getZone() {
    return zone;
  }

  public void setZone(String zone) {
    this.zone = zone;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1CreateCloudClusterRequest v1CreateCloudClusterRequest = (V1CreateCloudClusterRequest) o;
    return Objects.equals(this.accessKeyID, v1CreateCloudClusterRequest.accessKeyID) &&
        Objects.equals(this.accessKeySecret, v1CreateCloudClusterRequest.accessKeySecret) &&
        Objects.equals(this.cpuCoresPerWorker, v1CreateCloudClusterRequest.cpuCoresPerWorker) &&
        Objects.equals(this.memoryPerWorker, v1CreateCloudClusterRequest.memoryPerWorker) &&
        Objects.equals(this.name, v1CreateCloudClusterRequest.name) &&
        Objects.equals(this.workerNumber, v1CreateCloudClusterRequest.workerNumber) &&
        Objects.equals(this.zone, v1CreateCloudClusterRequest.zone);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accessKeyID, accessKeySecret, cpuCoresPerWorker, memoryPerWorker, name, workerNumber, zone);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1CreateCloudClusterRequest {\n");
    
    sb.append("    accessKeyID: ").append(toIndentedString(accessKeyID)).append("\n");
    sb.append("    accessKeySecret: ").append(toIndentedString(accessKeySecret)).append("\n");
    sb.append("    cpuCoresPerWorker: ").append(toIndentedString(cpuCoresPerWorker)).append("\n");
    sb.append("    memoryPerWorker: ").append(toIndentedString(memoryPerWorker)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    workerNumber: ").append(toIndentedString(workerNumber)).append("\n");
    sb.append("    zone: ").append(toIndentedString(zone)).append("\n");
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

