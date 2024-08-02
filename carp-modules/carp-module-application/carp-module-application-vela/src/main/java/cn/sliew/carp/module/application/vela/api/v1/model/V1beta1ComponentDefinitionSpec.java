package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;

import cn.sliew.carp.module.application.vela.api.v1.model.common.CommonChildResourceKind;
import cn.sliew.carp.module.application.vela.api.v1.model.common.CommonSchematic;
import cn.sliew.carp.module.application.vela.api.v1.model.common.CommonStatus;
import cn.sliew.carp.module.application.vela.api.v1.model.common.CommonWorkloadTypeDescriptor;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1beta1ComponentDefinitionSpec
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1beta1ComponentDefinitionSpec   {
  @JsonProperty("childResourceKinds")
  @Valid
  private List<CommonChildResourceKind> childResourceKinds = null;

  @JsonProperty("extension")
  private String extension = null;

  @JsonProperty("podSpecPath")
  private String podSpecPath = null;

  @JsonProperty("revisionLabel")
  private String revisionLabel = null;

  @JsonProperty("schematic")
  private CommonSchematic schematic = null;

  @JsonProperty("status")
  private CommonStatus status = null;

  @JsonProperty("workload")
  private CommonWorkloadTypeDescriptor workload = null;

  public V1beta1ComponentDefinitionSpec childResourceKinds(List<CommonChildResourceKind> childResourceKinds) {
    this.childResourceKinds = childResourceKinds;
    return this;
  }

  public V1beta1ComponentDefinitionSpec addChildResourceKindsItem(CommonChildResourceKind childResourceKindsItem) {
    if (this.childResourceKinds == null) {
      this.childResourceKinds = new ArrayList<CommonChildResourceKind>();
    }
    this.childResourceKinds.add(childResourceKindsItem);
    return this;
  }

  /**
   * Get childResourceKinds
   * @return childResourceKinds
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<CommonChildResourceKind> getChildResourceKinds() {
    return childResourceKinds;
  }

  public void setChildResourceKinds(List<CommonChildResourceKind> childResourceKinds) {
    this.childResourceKinds = childResourceKinds;
  }

  public V1beta1ComponentDefinitionSpec extension(String extension) {
    this.extension = extension;
    return this;
  }

  /**
   * Get extension
   * @return extension
  **/
  @ApiModelProperty(value = "")


  public String getExtension() {
    return extension;
  }

  public void setExtension(String extension) {
    this.extension = extension;
  }

  public V1beta1ComponentDefinitionSpec podSpecPath(String podSpecPath) {
    this.podSpecPath = podSpecPath;
    return this;
  }

  /**
   * Get podSpecPath
   * @return podSpecPath
  **/
  @ApiModelProperty(value = "")


  public String getPodSpecPath() {
    return podSpecPath;
  }

  public void setPodSpecPath(String podSpecPath) {
    this.podSpecPath = podSpecPath;
  }

  public V1beta1ComponentDefinitionSpec revisionLabel(String revisionLabel) {
    this.revisionLabel = revisionLabel;
    return this;
  }

  /**
   * Get revisionLabel
   * @return revisionLabel
  **/
  @ApiModelProperty(value = "")


  public String getRevisionLabel() {
    return revisionLabel;
  }

  public void setRevisionLabel(String revisionLabel) {
    this.revisionLabel = revisionLabel;
  }

  public V1beta1ComponentDefinitionSpec schematic(CommonSchematic schematic) {
    this.schematic = schematic;
    return this;
  }

  /**
   * Get schematic
   * @return schematic
  **/
  @ApiModelProperty(value = "")

  @Valid

  public CommonSchematic getSchematic() {
    return schematic;
  }

  public void setSchematic(CommonSchematic schematic) {
    this.schematic = schematic;
  }

  public V1beta1ComponentDefinitionSpec status(CommonStatus status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  **/
  @ApiModelProperty(value = "")

  @Valid

  public CommonStatus getStatus() {
    return status;
  }

  public void setStatus(CommonStatus status) {
    this.status = status;
  }

  public V1beta1ComponentDefinitionSpec workload(CommonWorkloadTypeDescriptor workload) {
    this.workload = workload;
    return this;
  }

  /**
   * Get workload
   * @return workload
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public CommonWorkloadTypeDescriptor getWorkload() {
    return workload;
  }

  public void setWorkload(CommonWorkloadTypeDescriptor workload) {
    this.workload = workload;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1beta1ComponentDefinitionSpec v1beta1ComponentDefinitionSpec = (V1beta1ComponentDefinitionSpec) o;
    return Objects.equals(this.childResourceKinds, v1beta1ComponentDefinitionSpec.childResourceKinds) &&
        Objects.equals(this.extension, v1beta1ComponentDefinitionSpec.extension) &&
        Objects.equals(this.podSpecPath, v1beta1ComponentDefinitionSpec.podSpecPath) &&
        Objects.equals(this.revisionLabel, v1beta1ComponentDefinitionSpec.revisionLabel) &&
        Objects.equals(this.schematic, v1beta1ComponentDefinitionSpec.schematic) &&
        Objects.equals(this.status, v1beta1ComponentDefinitionSpec.status) &&
        Objects.equals(this.workload, v1beta1ComponentDefinitionSpec.workload);
  }

  @Override
  public int hashCode() {
    return Objects.hash(childResourceKinds, extension, podSpecPath, revisionLabel, schematic, status, workload);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1beta1ComponentDefinitionSpec {\n");
    
    sb.append("    childResourceKinds: ").append(toIndentedString(childResourceKinds)).append("\n");
    sb.append("    extension: ").append(toIndentedString(extension)).append("\n");
    sb.append("    podSpecPath: ").append(toIndentedString(podSpecPath)).append("\n");
    sb.append("    revisionLabel: ").append(toIndentedString(revisionLabel)).append("\n");
    sb.append("    schematic: ").append(toIndentedString(schematic)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    workload: ").append(toIndentedString(workload)).append("\n");
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

