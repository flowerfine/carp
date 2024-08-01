package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;
import java.util.Map;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CloudproviderCloudCluster
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class CloudproviderCloudCluster   {
  @JsonProperty("apiServerURL")
  private String apiServerURL = null;

  @JsonProperty("dashboardURL")
  private String dashboardURL = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("labels")
  @Valid
  private Map<String, String> labels = new HashMap<String, String>();

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("provider")
  private String provider = null;

  @JsonProperty("regionID")
  private String regionID = null;

  @JsonProperty("status")
  private String status = null;

  @JsonProperty("type")
  private String type = null;

  @JsonProperty("vpcID")
  private String vpcID = null;

  @JsonProperty("zone")
  private String zone = null;

  @JsonProperty("zoneID")
  private String zoneID = null;

  public CloudproviderCloudCluster apiServerURL(String apiServerURL) {
    this.apiServerURL = apiServerURL;
    return this;
  }

  /**
   * Get apiServerURL
   * @return apiServerURL
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getApiServerURL() {
    return apiServerURL;
  }

  public void setApiServerURL(String apiServerURL) {
    this.apiServerURL = apiServerURL;
  }

  public CloudproviderCloudCluster dashboardURL(String dashboardURL) {
    this.dashboardURL = dashboardURL;
    return this;
  }

  /**
   * Get dashboardURL
   * @return dashboardURL
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getDashboardURL() {
    return dashboardURL;
  }

  public void setDashboardURL(String dashboardURL) {
    this.dashboardURL = dashboardURL;
  }

  public CloudproviderCloudCluster id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public CloudproviderCloudCluster labels(Map<String, String> labels) {
    this.labels = labels;
    return this;
  }

  public CloudproviderCloudCluster putLabelsItem(String key, String labelsItem) {
    this.labels.put(key, labelsItem);
    return this;
  }

  /**
   * Get labels
   * @return labels
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Map<String, String> getLabels() {
    return labels;
  }

  public void setLabels(Map<String, String> labels) {
    this.labels = labels;
  }

  public CloudproviderCloudCluster name(String name) {
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

  public CloudproviderCloudCluster provider(String provider) {
    this.provider = provider;
    return this;
  }

  /**
   * Get provider
   * @return provider
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getProvider() {
    return provider;
  }

  public void setProvider(String provider) {
    this.provider = provider;
  }

  public CloudproviderCloudCluster regionID(String regionID) {
    this.regionID = regionID;
    return this;
  }

  /**
   * Get regionID
   * @return regionID
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getRegionID() {
    return regionID;
  }

  public void setRegionID(String regionID) {
    this.regionID = regionID;
  }

  public CloudproviderCloudCluster status(String status) {
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

  public CloudproviderCloudCluster type(String type) {
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

  public CloudproviderCloudCluster vpcID(String vpcID) {
    this.vpcID = vpcID;
    return this;
  }

  /**
   * Get vpcID
   * @return vpcID
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getVpcID() {
    return vpcID;
  }

  public void setVpcID(String vpcID) {
    this.vpcID = vpcID;
  }

  public CloudproviderCloudCluster zone(String zone) {
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

  public CloudproviderCloudCluster zoneID(String zoneID) {
    this.zoneID = zoneID;
    return this;
  }

  /**
   * Get zoneID
   * @return zoneID
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getZoneID() {
    return zoneID;
  }

  public void setZoneID(String zoneID) {
    this.zoneID = zoneID;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CloudproviderCloudCluster cloudproviderCloudCluster = (CloudproviderCloudCluster) o;
    return Objects.equals(this.apiServerURL, cloudproviderCloudCluster.apiServerURL) &&
        Objects.equals(this.dashboardURL, cloudproviderCloudCluster.dashboardURL) &&
        Objects.equals(this.id, cloudproviderCloudCluster.id) &&
        Objects.equals(this.labels, cloudproviderCloudCluster.labels) &&
        Objects.equals(this.name, cloudproviderCloudCluster.name) &&
        Objects.equals(this.provider, cloudproviderCloudCluster.provider) &&
        Objects.equals(this.regionID, cloudproviderCloudCluster.regionID) &&
        Objects.equals(this.status, cloudproviderCloudCluster.status) &&
        Objects.equals(this.type, cloudproviderCloudCluster.type) &&
        Objects.equals(this.vpcID, cloudproviderCloudCluster.vpcID) &&
        Objects.equals(this.zone, cloudproviderCloudCluster.zone) &&
        Objects.equals(this.zoneID, cloudproviderCloudCluster.zoneID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(apiServerURL, dashboardURL, id, labels, name, provider, regionID, status, type, vpcID, zone, zoneID);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CloudproviderCloudCluster {\n");
    
    sb.append("    apiServerURL: ").append(toIndentedString(apiServerURL)).append("\n");
    sb.append("    dashboardURL: ").append(toIndentedString(dashboardURL)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    provider: ").append(toIndentedString(provider)).append("\n");
    sb.append("    regionID: ").append(toIndentedString(regionID)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    vpcID: ").append(toIndentedString(vpcID)).append("\n");
    sb.append("    zone: ").append(toIndentedString(zone)).append("\n");
    sb.append("    zoneID: ").append(toIndentedString(zoneID)).append("\n");
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

