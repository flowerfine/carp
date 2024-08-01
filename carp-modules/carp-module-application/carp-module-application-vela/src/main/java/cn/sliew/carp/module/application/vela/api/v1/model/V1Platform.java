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
 * V1Platform
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1Platform   {
  @JsonProperty("architecture")
  private String architecture = null;

  @JsonProperty("features")
  @Valid
  private List<String> features = null;

  @JsonProperty("os")
  private String os = null;

  @JsonProperty("os.features")
  @Valid
  private List<String> osFeatures = null;

  @JsonProperty("os.version")
  private String osVersion = null;

  @JsonProperty("variant")
  private String variant = null;

  public V1Platform architecture(String architecture) {
    this.architecture = architecture;
    return this;
  }

  /**
   * Get architecture
   * @return architecture
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getArchitecture() {
    return architecture;
  }

  public void setArchitecture(String architecture) {
    this.architecture = architecture;
  }

  public V1Platform features(List<String> features) {
    this.features = features;
    return this;
  }

  public V1Platform addFeaturesItem(String featuresItem) {
    if (this.features == null) {
      this.features = new ArrayList<String>();
    }
    this.features.add(featuresItem);
    return this;
  }

  /**
   * Get features
   * @return features
  **/
  @ApiModelProperty(value = "")


  public List<String> getFeatures() {
    return features;
  }

  public void setFeatures(List<String> features) {
    this.features = features;
  }

  public V1Platform os(String os) {
    this.os = os;
    return this;
  }

  /**
   * Get os
   * @return os
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getOs() {
    return os;
  }

  public void setOs(String os) {
    this.os = os;
  }

  public V1Platform osFeatures(List<String> osFeatures) {
    this.osFeatures = osFeatures;
    return this;
  }

  public V1Platform addOsFeaturesItem(String osFeaturesItem) {
    if (this.osFeatures == null) {
      this.osFeatures = new ArrayList<String>();
    }
    this.osFeatures.add(osFeaturesItem);
    return this;
  }

  /**
   * Get osFeatures
   * @return osFeatures
  **/
  @ApiModelProperty(value = "")


  public List<String> getOsFeatures() {
    return osFeatures;
  }

  public void setOsFeatures(List<String> osFeatures) {
    this.osFeatures = osFeatures;
  }

  public V1Platform osVersion(String osVersion) {
    this.osVersion = osVersion;
    return this;
  }

  /**
   * Get osVersion
   * @return osVersion
  **/
  @ApiModelProperty(value = "")


  public String getOsVersion() {
    return osVersion;
  }

  public void setOsVersion(String osVersion) {
    this.osVersion = osVersion;
  }

  public V1Platform variant(String variant) {
    this.variant = variant;
    return this;
  }

  /**
   * Get variant
   * @return variant
  **/
  @ApiModelProperty(value = "")


  public String getVariant() {
    return variant;
  }

  public void setVariant(String variant) {
    this.variant = variant;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1Platform v1Platform = (V1Platform) o;
    return Objects.equals(this.architecture, v1Platform.architecture) &&
        Objects.equals(this.features, v1Platform.features) &&
        Objects.equals(this.os, v1Platform.os) &&
        Objects.equals(this.osFeatures, v1Platform.osFeatures) &&
        Objects.equals(this.osVersion, v1Platform.osVersion) &&
        Objects.equals(this.variant, v1Platform.variant);
  }

  @Override
  public int hashCode() {
    return Objects.hash(architecture, features, os, osFeatures, osVersion, variant);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1Platform {\n");
    
    sb.append("    architecture: ").append(toIndentedString(architecture)).append("\n");
    sb.append("    features: ").append(toIndentedString(features)).append("\n");
    sb.append("    os: ").append(toIndentedString(os)).append("\n");
    sb.append("    osFeatures: ").append(toIndentedString(osFeatures)).append("\n");
    sb.append("    osVersion: ").append(toIndentedString(osVersion)).append("\n");
    sb.append("    variant: ").append(toIndentedString(variant)).append("\n");
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

