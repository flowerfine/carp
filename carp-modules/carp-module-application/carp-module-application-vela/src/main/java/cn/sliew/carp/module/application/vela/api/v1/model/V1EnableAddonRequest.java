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
 * V1EnableAddonRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1EnableAddonRequest   {
  @JsonProperty("args")
  private Object args = null;

  @JsonProperty("clusters")
  @Valid
  private List<String> clusters = null;

  @JsonProperty("registryName")
  private String registryName = null;

  @JsonProperty("version")
  private String version = null;

  public V1EnableAddonRequest args(Object args) {
    this.args = args;
    return this;
  }

  /**
   * Get args
   * @return args
  **/
  @ApiModelProperty(value = "")


  public Object getArgs() {
    return args;
  }

  public void setArgs(Object args) {
    this.args = args;
  }

  public V1EnableAddonRequest clusters(List<String> clusters) {
    this.clusters = clusters;
    return this;
  }

  public V1EnableAddonRequest addClustersItem(String clustersItem) {
    if (this.clusters == null) {
      this.clusters = new ArrayList<String>();
    }
    this.clusters.add(clustersItem);
    return this;
  }

  /**
   * Get clusters
   * @return clusters
  **/
  @ApiModelProperty(value = "")


  public List<String> getClusters() {
    return clusters;
  }

  public void setClusters(List<String> clusters) {
    this.clusters = clusters;
  }

  public V1EnableAddonRequest registryName(String registryName) {
    this.registryName = registryName;
    return this;
  }

  /**
   * Get registryName
   * @return registryName
  **/
  @ApiModelProperty(value = "")


  public String getRegistryName() {
    return registryName;
  }

  public void setRegistryName(String registryName) {
    this.registryName = registryName;
  }

  public V1EnableAddonRequest version(String version) {
    this.version = version;
    return this;
  }

  /**
   * Get version
   * @return version
  **/
  @ApiModelProperty(value = "")


  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1EnableAddonRequest v1EnableAddonRequest = (V1EnableAddonRequest) o;
    return Objects.equals(this.args, v1EnableAddonRequest.args) &&
        Objects.equals(this.clusters, v1EnableAddonRequest.clusters) &&
        Objects.equals(this.registryName, v1EnableAddonRequest.registryName) &&
        Objects.equals(this.version, v1EnableAddonRequest.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(args, clusters, registryName, version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1EnableAddonRequest {\n");
    
    sb.append("    args: ").append(toIndentedString(args)).append("\n");
    sb.append("    clusters: ").append(toIndentedString(clusters)).append("\n");
    sb.append("    registryName: ").append(toIndentedString(registryName)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
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

