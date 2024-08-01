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
 * V1ClusterResourceInfo
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ClusterResourceInfo   {
  @JsonProperty("cpuCapacity")
  private Long cpuCapacity = null;

  @JsonProperty("cpuUsed")
  private Long cpuUsed = null;

  @JsonProperty("gpuCapacity")
  private Long gpuCapacity = null;

  @JsonProperty("gpuUsed")
  private Long gpuUsed = null;

  @JsonProperty("masterNumber")
  private Integer masterNumber = null;

  @JsonProperty("memoryCapacity")
  private Long memoryCapacity = null;

  @JsonProperty("memoryUsed")
  private Long memoryUsed = null;

  @JsonProperty("podCapacity")
  private Long podCapacity = null;

  @JsonProperty("podUsed")
  private Long podUsed = null;

  @JsonProperty("storageClassList")
  @Valid
  private List<String> storageClassList = null;

  @JsonProperty("workerNumber")
  private Integer workerNumber = null;

  public V1ClusterResourceInfo cpuCapacity(Long cpuCapacity) {
    this.cpuCapacity = cpuCapacity;
    return this;
  }

  /**
   * Get cpuCapacity
   * @return cpuCapacity
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getCpuCapacity() {
    return cpuCapacity;
  }

  public void setCpuCapacity(Long cpuCapacity) {
    this.cpuCapacity = cpuCapacity;
  }

  public V1ClusterResourceInfo cpuUsed(Long cpuUsed) {
    this.cpuUsed = cpuUsed;
    return this;
  }

  /**
   * Get cpuUsed
   * @return cpuUsed
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getCpuUsed() {
    return cpuUsed;
  }

  public void setCpuUsed(Long cpuUsed) {
    this.cpuUsed = cpuUsed;
  }

  public V1ClusterResourceInfo gpuCapacity(Long gpuCapacity) {
    this.gpuCapacity = gpuCapacity;
    return this;
  }

  /**
   * Get gpuCapacity
   * @return gpuCapacity
  **/
  @ApiModelProperty(value = "")


  public Long getGpuCapacity() {
    return gpuCapacity;
  }

  public void setGpuCapacity(Long gpuCapacity) {
    this.gpuCapacity = gpuCapacity;
  }

  public V1ClusterResourceInfo gpuUsed(Long gpuUsed) {
    this.gpuUsed = gpuUsed;
    return this;
  }

  /**
   * Get gpuUsed
   * @return gpuUsed
  **/
  @ApiModelProperty(value = "")


  public Long getGpuUsed() {
    return gpuUsed;
  }

  public void setGpuUsed(Long gpuUsed) {
    this.gpuUsed = gpuUsed;
  }

  public V1ClusterResourceInfo masterNumber(Integer masterNumber) {
    this.masterNumber = masterNumber;
    return this;
  }

  /**
   * Get masterNumber
   * @return masterNumber
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getMasterNumber() {
    return masterNumber;
  }

  public void setMasterNumber(Integer masterNumber) {
    this.masterNumber = masterNumber;
  }

  public V1ClusterResourceInfo memoryCapacity(Long memoryCapacity) {
    this.memoryCapacity = memoryCapacity;
    return this;
  }

  /**
   * Get memoryCapacity
   * @return memoryCapacity
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getMemoryCapacity() {
    return memoryCapacity;
  }

  public void setMemoryCapacity(Long memoryCapacity) {
    this.memoryCapacity = memoryCapacity;
  }

  public V1ClusterResourceInfo memoryUsed(Long memoryUsed) {
    this.memoryUsed = memoryUsed;
    return this;
  }

  /**
   * Get memoryUsed
   * @return memoryUsed
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getMemoryUsed() {
    return memoryUsed;
  }

  public void setMemoryUsed(Long memoryUsed) {
    this.memoryUsed = memoryUsed;
  }

  public V1ClusterResourceInfo podCapacity(Long podCapacity) {
    this.podCapacity = podCapacity;
    return this;
  }

  /**
   * Get podCapacity
   * @return podCapacity
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getPodCapacity() {
    return podCapacity;
  }

  public void setPodCapacity(Long podCapacity) {
    this.podCapacity = podCapacity;
  }

  public V1ClusterResourceInfo podUsed(Long podUsed) {
    this.podUsed = podUsed;
    return this;
  }

  /**
   * Get podUsed
   * @return podUsed
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getPodUsed() {
    return podUsed;
  }

  public void setPodUsed(Long podUsed) {
    this.podUsed = podUsed;
  }

  public V1ClusterResourceInfo storageClassList(List<String> storageClassList) {
    this.storageClassList = storageClassList;
    return this;
  }

  public V1ClusterResourceInfo addStorageClassListItem(String storageClassListItem) {
    if (this.storageClassList == null) {
      this.storageClassList = new ArrayList<String>();
    }
    this.storageClassList.add(storageClassListItem);
    return this;
  }

  /**
   * Get storageClassList
   * @return storageClassList
  **/
  @ApiModelProperty(value = "")


  public List<String> getStorageClassList() {
    return storageClassList;
  }

  public void setStorageClassList(List<String> storageClassList) {
    this.storageClassList = storageClassList;
  }

  public V1ClusterResourceInfo workerNumber(Integer workerNumber) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1ClusterResourceInfo v1ClusterResourceInfo = (V1ClusterResourceInfo) o;
    return Objects.equals(this.cpuCapacity, v1ClusterResourceInfo.cpuCapacity) &&
        Objects.equals(this.cpuUsed, v1ClusterResourceInfo.cpuUsed) &&
        Objects.equals(this.gpuCapacity, v1ClusterResourceInfo.gpuCapacity) &&
        Objects.equals(this.gpuUsed, v1ClusterResourceInfo.gpuUsed) &&
        Objects.equals(this.masterNumber, v1ClusterResourceInfo.masterNumber) &&
        Objects.equals(this.memoryCapacity, v1ClusterResourceInfo.memoryCapacity) &&
        Objects.equals(this.memoryUsed, v1ClusterResourceInfo.memoryUsed) &&
        Objects.equals(this.podCapacity, v1ClusterResourceInfo.podCapacity) &&
        Objects.equals(this.podUsed, v1ClusterResourceInfo.podUsed) &&
        Objects.equals(this.storageClassList, v1ClusterResourceInfo.storageClassList) &&
        Objects.equals(this.workerNumber, v1ClusterResourceInfo.workerNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cpuCapacity, cpuUsed, gpuCapacity, gpuUsed, masterNumber, memoryCapacity, memoryUsed, podCapacity, podUsed, storageClassList, workerNumber);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ClusterResourceInfo {\n");
    
    sb.append("    cpuCapacity: ").append(toIndentedString(cpuCapacity)).append("\n");
    sb.append("    cpuUsed: ").append(toIndentedString(cpuUsed)).append("\n");
    sb.append("    gpuCapacity: ").append(toIndentedString(gpuCapacity)).append("\n");
    sb.append("    gpuUsed: ").append(toIndentedString(gpuUsed)).append("\n");
    sb.append("    masterNumber: ").append(toIndentedString(masterNumber)).append("\n");
    sb.append("    memoryCapacity: ").append(toIndentedString(memoryCapacity)).append("\n");
    sb.append("    memoryUsed: ").append(toIndentedString(memoryUsed)).append("\n");
    sb.append("    podCapacity: ").append(toIndentedString(podCapacity)).append("\n");
    sb.append("    podUsed: ").append(toIndentedString(podUsed)).append("\n");
    sb.append("    storageClassList: ").append(toIndentedString(storageClassList)).append("\n");
    sb.append("    workerNumber: ").append(toIndentedString(workerNumber)).append("\n");
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

