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
 * ModelProviderInfo
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class ModelProviderInfo   {
  @JsonProperty("clusterID")
  private String clusterID = null;

  @JsonProperty("clusterName")
  private String clusterName = null;

  @JsonProperty("labels")
  @Valid
  private Map<String, String> labels = new HashMap<String, String>();

  @JsonProperty("provider")
  private String provider = null;

  @JsonProperty("regionID")
  private String regionID = null;

  @JsonProperty("vpcID")
  private String vpcID = null;

  @JsonProperty("zone")
  private String zone = null;

  @JsonProperty("zoneID")
  private String zoneID = null;

  public ModelProviderInfo clusterID(String clusterID) {
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

  public ModelProviderInfo clusterName(String clusterName) {
    this.clusterName = clusterName;
    return this;
  }

  /**
   * Get clusterName
   * @return clusterName
  **/
  @ApiModelProperty(value = "")


  public String getClusterName() {
    return clusterName;
  }

  public void setClusterName(String clusterName) {
    this.clusterName = clusterName;
  }

  public ModelProviderInfo labels(Map<String, String> labels) {
    this.labels = labels;
    return this;
  }

  public ModelProviderInfo putLabelsItem(String key, String labelsItem) {
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

  public ModelProviderInfo provider(String provider) {
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

  public ModelProviderInfo regionID(String regionID) {
    this.regionID = regionID;
    return this;
  }

  /**
   * Get regionID
   * @return regionID
  **/
  @ApiModelProperty(value = "")


  public String getRegionID() {
    return regionID;
  }

  public void setRegionID(String regionID) {
    this.regionID = regionID;
  }

  public ModelProviderInfo vpcID(String vpcID) {
    this.vpcID = vpcID;
    return this;
  }

  /**
   * Get vpcID
   * @return vpcID
  **/
  @ApiModelProperty(value = "")


  public String getVpcID() {
    return vpcID;
  }

  public void setVpcID(String vpcID) {
    this.vpcID = vpcID;
  }

  public ModelProviderInfo zone(String zone) {
    this.zone = zone;
    return this;
  }

  /**
   * Get zone
   * @return zone
  **/
  @ApiModelProperty(value = "")


  public String getZone() {
    return zone;
  }

  public void setZone(String zone) {
    this.zone = zone;
  }

  public ModelProviderInfo zoneID(String zoneID) {
    this.zoneID = zoneID;
    return this;
  }

  /**
   * Get zoneID
   * @return zoneID
  **/
  @ApiModelProperty(value = "")


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
    ModelProviderInfo modelProviderInfo = (ModelProviderInfo) o;
    return Objects.equals(this.clusterID, modelProviderInfo.clusterID) &&
        Objects.equals(this.clusterName, modelProviderInfo.clusterName) &&
        Objects.equals(this.labels, modelProviderInfo.labels) &&
        Objects.equals(this.provider, modelProviderInfo.provider) &&
        Objects.equals(this.regionID, modelProviderInfo.regionID) &&
        Objects.equals(this.vpcID, modelProviderInfo.vpcID) &&
        Objects.equals(this.zone, modelProviderInfo.zone) &&
        Objects.equals(this.zoneID, modelProviderInfo.zoneID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clusterID, clusterName, labels, provider, regionID, vpcID, zone, zoneID);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ModelProviderInfo {\n");
    
    sb.append("    clusterID: ").append(toIndentedString(clusterID)).append("\n");
    sb.append("    clusterName: ").append(toIndentedString(clusterName)).append("\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
    sb.append("    provider: ").append(toIndentedString(provider)).append("\n");
    sb.append("    regionID: ").append(toIndentedString(regionID)).append("\n");
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

