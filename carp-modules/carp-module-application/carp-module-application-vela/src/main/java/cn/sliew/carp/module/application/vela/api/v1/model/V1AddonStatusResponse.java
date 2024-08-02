package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;

import cn.sliew.carp.module.application.vela.api.v1.model.common.CommonAppStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1AddonStatusResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1AddonStatusResponse   {
  @JsonProperty("allClusters")
  @Valid
  private List<V1NameAlias> allClusters = null;

  @JsonProperty("appStatus")
  private CommonAppStatus appStatus = null;

  @JsonProperty("args")
  private Object args = null;

  @JsonProperty("clusters")
  @Valid
  private Map<String, V1AddonStatusResponseClusters> clusters = null;

  @JsonProperty("enabling_progress")
  private V1EnablingProgress enablingProgress = null;

  @JsonProperty("installedVersion")
  private String installedVersion = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("phase")
  private String phase = null;

  public V1AddonStatusResponse allClusters(List<V1NameAlias> allClusters) {
    this.allClusters = allClusters;
    return this;
  }

  public V1AddonStatusResponse addAllClustersItem(V1NameAlias allClustersItem) {
    if (this.allClusters == null) {
      this.allClusters = new ArrayList<V1NameAlias>();
    }
    this.allClusters.add(allClustersItem);
    return this;
  }

  /**
   * Get allClusters
   * @return allClusters
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<V1NameAlias> getAllClusters() {
    return allClusters;
  }

  public void setAllClusters(List<V1NameAlias> allClusters) {
    this.allClusters = allClusters;
  }

  public V1AddonStatusResponse appStatus(CommonAppStatus appStatus) {
    this.appStatus = appStatus;
    return this;
  }

  /**
   * Get appStatus
   * @return appStatus
  **/
  @ApiModelProperty(value = "")

  @Valid

  public CommonAppStatus getAppStatus() {
    return appStatus;
  }

  public void setAppStatus(CommonAppStatus appStatus) {
    this.appStatus = appStatus;
  }

  public V1AddonStatusResponse args(Object args) {
    this.args = args;
    return this;
  }

  /**
   * Get args
   * @return args
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Object getArgs() {
    return args;
  }

  public void setArgs(Object args) {
    this.args = args;
  }

  public V1AddonStatusResponse clusters(Map<String, V1AddonStatusResponseClusters> clusters) {
    this.clusters = clusters;
    return this;
  }

  public V1AddonStatusResponse putClustersItem(String key, V1AddonStatusResponseClusters clustersItem) {
    if (this.clusters == null) {
      this.clusters = new HashMap<String, V1AddonStatusResponseClusters>();
    }
    this.clusters.put(key, clustersItem);
    return this;
  }

  /**
   * Get clusters
   * @return clusters
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Map<String, V1AddonStatusResponseClusters> getClusters() {
    return clusters;
  }

  public void setClusters(Map<String, V1AddonStatusResponseClusters> clusters) {
    this.clusters = clusters;
  }

  public V1AddonStatusResponse enablingProgress(V1EnablingProgress enablingProgress) {
    this.enablingProgress = enablingProgress;
    return this;
  }

  /**
   * Get enablingProgress
   * @return enablingProgress
  **/
  @ApiModelProperty(value = "")

  @Valid

  public V1EnablingProgress getEnablingProgress() {
    return enablingProgress;
  }

  public void setEnablingProgress(V1EnablingProgress enablingProgress) {
    this.enablingProgress = enablingProgress;
  }

  public V1AddonStatusResponse installedVersion(String installedVersion) {
    this.installedVersion = installedVersion;
    return this;
  }

  /**
   * Get installedVersion
   * @return installedVersion
  **/
  @ApiModelProperty(value = "")


  public String getInstalledVersion() {
    return installedVersion;
  }

  public void setInstalledVersion(String installedVersion) {
    this.installedVersion = installedVersion;
  }

  public V1AddonStatusResponse name(String name) {
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

  public V1AddonStatusResponse phase(String phase) {
    this.phase = phase;
    return this;
  }

  /**
   * Get phase
   * @return phase
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getPhase() {
    return phase;
  }

  public void setPhase(String phase) {
    this.phase = phase;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1AddonStatusResponse v1AddonStatusResponse = (V1AddonStatusResponse) o;
    return Objects.equals(this.allClusters, v1AddonStatusResponse.allClusters) &&
        Objects.equals(this.appStatus, v1AddonStatusResponse.appStatus) &&
        Objects.equals(this.args, v1AddonStatusResponse.args) &&
        Objects.equals(this.clusters, v1AddonStatusResponse.clusters) &&
        Objects.equals(this.enablingProgress, v1AddonStatusResponse.enablingProgress) &&
        Objects.equals(this.installedVersion, v1AddonStatusResponse.installedVersion) &&
        Objects.equals(this.name, v1AddonStatusResponse.name) &&
        Objects.equals(this.phase, v1AddonStatusResponse.phase);
  }

  @Override
  public int hashCode() {
    return Objects.hash(allClusters, appStatus, args, clusters, enablingProgress, installedVersion, name, phase);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1AddonStatusResponse {\n");
    
    sb.append("    allClusters: ").append(toIndentedString(allClusters)).append("\n");
    sb.append("    appStatus: ").append(toIndentedString(appStatus)).append("\n");
    sb.append("    args: ").append(toIndentedString(args)).append("\n");
    sb.append("    clusters: ").append(toIndentedString(clusters)).append("\n");
    sb.append("    enablingProgress: ").append(toIndentedString(enablingProgress)).append("\n");
    sb.append("    installedVersion: ").append(toIndentedString(installedVersion)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    phase: ").append(toIndentedString(phase)).append("\n");
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

