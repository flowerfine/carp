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
 * V1beta1TraitDefinitionSpec
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1beta1TraitDefinitionSpec   {
  @JsonProperty("appliesToWorkloads")
  @Valid
  private List<String> appliesToWorkloads = null;

  @JsonProperty("conflictsWith")
  @Valid
  private List<String> conflictsWith = null;

  @JsonProperty("controlPlaneOnly")
  private Boolean controlPlaneOnly = null;

  @JsonProperty("definitionRef")
  private CommonDefinitionReference definitionRef = null;

  @JsonProperty("extension")
  private String extension = null;

  @JsonProperty("manageWorkload")
  private Boolean manageWorkload = null;

  @JsonProperty("podDisruptive")
  private Boolean podDisruptive = null;

  @JsonProperty("revisionEnabled")
  private Boolean revisionEnabled = null;

  @JsonProperty("schematic")
  private CommonSchematic schematic = null;

  @JsonProperty("stage")
  private String stage = null;

  @JsonProperty("status")
  private CommonStatus status = null;

  @JsonProperty("workloadRefPath")
  private String workloadRefPath = null;

  public V1beta1TraitDefinitionSpec appliesToWorkloads(List<String> appliesToWorkloads) {
    this.appliesToWorkloads = appliesToWorkloads;
    return this;
  }

  public V1beta1TraitDefinitionSpec addAppliesToWorkloadsItem(String appliesToWorkloadsItem) {
    if (this.appliesToWorkloads == null) {
      this.appliesToWorkloads = new ArrayList<String>();
    }
    this.appliesToWorkloads.add(appliesToWorkloadsItem);
    return this;
  }

  /**
   * Get appliesToWorkloads
   * @return appliesToWorkloads
  **/
  @ApiModelProperty(value = "")


  public List<String> getAppliesToWorkloads() {
    return appliesToWorkloads;
  }

  public void setAppliesToWorkloads(List<String> appliesToWorkloads) {
    this.appliesToWorkloads = appliesToWorkloads;
  }

  public V1beta1TraitDefinitionSpec conflictsWith(List<String> conflictsWith) {
    this.conflictsWith = conflictsWith;
    return this;
  }

  public V1beta1TraitDefinitionSpec addConflictsWithItem(String conflictsWithItem) {
    if (this.conflictsWith == null) {
      this.conflictsWith = new ArrayList<String>();
    }
    this.conflictsWith.add(conflictsWithItem);
    return this;
  }

  /**
   * Get conflictsWith
   * @return conflictsWith
  **/
  @ApiModelProperty(value = "")


  public List<String> getConflictsWith() {
    return conflictsWith;
  }

  public void setConflictsWith(List<String> conflictsWith) {
    this.conflictsWith = conflictsWith;
  }

  public V1beta1TraitDefinitionSpec controlPlaneOnly(Boolean controlPlaneOnly) {
    this.controlPlaneOnly = controlPlaneOnly;
    return this;
  }

  /**
   * Get controlPlaneOnly
   * @return controlPlaneOnly
  **/
  @ApiModelProperty(value = "")


  public Boolean isControlPlaneOnly() {
    return controlPlaneOnly;
  }

  public void setControlPlaneOnly(Boolean controlPlaneOnly) {
    this.controlPlaneOnly = controlPlaneOnly;
  }

  public V1beta1TraitDefinitionSpec definitionRef(CommonDefinitionReference definitionRef) {
    this.definitionRef = definitionRef;
    return this;
  }

  /**
   * Get definitionRef
   * @return definitionRef
  **/
  @ApiModelProperty(value = "")

  @Valid

  public CommonDefinitionReference getDefinitionRef() {
    return definitionRef;
  }

  public void setDefinitionRef(CommonDefinitionReference definitionRef) {
    this.definitionRef = definitionRef;
  }

  public V1beta1TraitDefinitionSpec extension(String extension) {
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

  public V1beta1TraitDefinitionSpec manageWorkload(Boolean manageWorkload) {
    this.manageWorkload = manageWorkload;
    return this;
  }

  /**
   * Get manageWorkload
   * @return manageWorkload
  **/
  @ApiModelProperty(value = "")


  public Boolean isManageWorkload() {
    return manageWorkload;
  }

  public void setManageWorkload(Boolean manageWorkload) {
    this.manageWorkload = manageWorkload;
  }

  public V1beta1TraitDefinitionSpec podDisruptive(Boolean podDisruptive) {
    this.podDisruptive = podDisruptive;
    return this;
  }

  /**
   * Get podDisruptive
   * @return podDisruptive
  **/
  @ApiModelProperty(value = "")


  public Boolean isPodDisruptive() {
    return podDisruptive;
  }

  public void setPodDisruptive(Boolean podDisruptive) {
    this.podDisruptive = podDisruptive;
  }

  public V1beta1TraitDefinitionSpec revisionEnabled(Boolean revisionEnabled) {
    this.revisionEnabled = revisionEnabled;
    return this;
  }

  /**
   * Get revisionEnabled
   * @return revisionEnabled
  **/
  @ApiModelProperty(value = "")


  public Boolean isRevisionEnabled() {
    return revisionEnabled;
  }

  public void setRevisionEnabled(Boolean revisionEnabled) {
    this.revisionEnabled = revisionEnabled;
  }

  public V1beta1TraitDefinitionSpec schematic(CommonSchematic schematic) {
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

  public V1beta1TraitDefinitionSpec stage(String stage) {
    this.stage = stage;
    return this;
  }

  /**
   * Get stage
   * @return stage
  **/
  @ApiModelProperty(value = "")


  public String getStage() {
    return stage;
  }

  public void setStage(String stage) {
    this.stage = stage;
  }

  public V1beta1TraitDefinitionSpec status(CommonStatus status) {
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

  public V1beta1TraitDefinitionSpec workloadRefPath(String workloadRefPath) {
    this.workloadRefPath = workloadRefPath;
    return this;
  }

  /**
   * Get workloadRefPath
   * @return workloadRefPath
  **/
  @ApiModelProperty(value = "")


  public String getWorkloadRefPath() {
    return workloadRefPath;
  }

  public void setWorkloadRefPath(String workloadRefPath) {
    this.workloadRefPath = workloadRefPath;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1beta1TraitDefinitionSpec v1beta1TraitDefinitionSpec = (V1beta1TraitDefinitionSpec) o;
    return Objects.equals(this.appliesToWorkloads, v1beta1TraitDefinitionSpec.appliesToWorkloads) &&
        Objects.equals(this.conflictsWith, v1beta1TraitDefinitionSpec.conflictsWith) &&
        Objects.equals(this.controlPlaneOnly, v1beta1TraitDefinitionSpec.controlPlaneOnly) &&
        Objects.equals(this.definitionRef, v1beta1TraitDefinitionSpec.definitionRef) &&
        Objects.equals(this.extension, v1beta1TraitDefinitionSpec.extension) &&
        Objects.equals(this.manageWorkload, v1beta1TraitDefinitionSpec.manageWorkload) &&
        Objects.equals(this.podDisruptive, v1beta1TraitDefinitionSpec.podDisruptive) &&
        Objects.equals(this.revisionEnabled, v1beta1TraitDefinitionSpec.revisionEnabled) &&
        Objects.equals(this.schematic, v1beta1TraitDefinitionSpec.schematic) &&
        Objects.equals(this.stage, v1beta1TraitDefinitionSpec.stage) &&
        Objects.equals(this.status, v1beta1TraitDefinitionSpec.status) &&
        Objects.equals(this.workloadRefPath, v1beta1TraitDefinitionSpec.workloadRefPath);
  }

  @Override
  public int hashCode() {
    return Objects.hash(appliesToWorkloads, conflictsWith, controlPlaneOnly, definitionRef, extension, manageWorkload, podDisruptive, revisionEnabled, schematic, stage, status, workloadRefPath);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1beta1TraitDefinitionSpec {\n");
    
    sb.append("    appliesToWorkloads: ").append(toIndentedString(appliesToWorkloads)).append("\n");
    sb.append("    conflictsWith: ").append(toIndentedString(conflictsWith)).append("\n");
    sb.append("    controlPlaneOnly: ").append(toIndentedString(controlPlaneOnly)).append("\n");
    sb.append("    definitionRef: ").append(toIndentedString(definitionRef)).append("\n");
    sb.append("    extension: ").append(toIndentedString(extension)).append("\n");
    sb.append("    manageWorkload: ").append(toIndentedString(manageWorkload)).append("\n");
    sb.append("    podDisruptive: ").append(toIndentedString(podDisruptive)).append("\n");
    sb.append("    revisionEnabled: ").append(toIndentedString(revisionEnabled)).append("\n");
    sb.append("    schematic: ").append(toIndentedString(schematic)).append("\n");
    sb.append("    stage: ").append(toIndentedString(stage)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    workloadRefPath: ").append(toIndentedString(workloadRefPath)).append("\n");
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

