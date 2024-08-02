package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;

import cn.sliew.carp.module.application.vela.api.v1.model.common.ModelProjectRef;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1SystemInfo
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1SystemInfo   {
  @JsonProperty("dexUserDefaultPlatformRoles")
  @Valid
  private List<String> dexUserDefaultPlatformRoles = null;

  @JsonProperty("dexUserDefaultProjects")
  @Valid
  private List<ModelProjectRef> dexUserDefaultProjects = null;

  @JsonProperty("enableCollection")
  private Boolean enableCollection = null;

  @JsonProperty("installTime")
  private OffsetDateTime installTime = null;

  @JsonProperty("loginType")
  private String loginType = null;

  @JsonProperty("platformID")
  private String platformID = null;

  public V1SystemInfo dexUserDefaultPlatformRoles(List<String> dexUserDefaultPlatformRoles) {
    this.dexUserDefaultPlatformRoles = dexUserDefaultPlatformRoles;
    return this;
  }

  public V1SystemInfo addDexUserDefaultPlatformRolesItem(String dexUserDefaultPlatformRolesItem) {
    if (this.dexUserDefaultPlatformRoles == null) {
      this.dexUserDefaultPlatformRoles = new ArrayList<String>();
    }
    this.dexUserDefaultPlatformRoles.add(dexUserDefaultPlatformRolesItem);
    return this;
  }

  /**
   * Get dexUserDefaultPlatformRoles
   * @return dexUserDefaultPlatformRoles
  **/
  @ApiModelProperty(value = "")


  public List<String> getDexUserDefaultPlatformRoles() {
    return dexUserDefaultPlatformRoles;
  }

  public void setDexUserDefaultPlatformRoles(List<String> dexUserDefaultPlatformRoles) {
    this.dexUserDefaultPlatformRoles = dexUserDefaultPlatformRoles;
  }

  public V1SystemInfo dexUserDefaultProjects(List<ModelProjectRef> dexUserDefaultProjects) {
    this.dexUserDefaultProjects = dexUserDefaultProjects;
    return this;
  }

  public V1SystemInfo addDexUserDefaultProjectsItem(ModelProjectRef dexUserDefaultProjectsItem) {
    if (this.dexUserDefaultProjects == null) {
      this.dexUserDefaultProjects = new ArrayList<ModelProjectRef>();
    }
    this.dexUserDefaultProjects.add(dexUserDefaultProjectsItem);
    return this;
  }

  /**
   * Get dexUserDefaultProjects
   * @return dexUserDefaultProjects
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<ModelProjectRef> getDexUserDefaultProjects() {
    return dexUserDefaultProjects;
  }

  public void setDexUserDefaultProjects(List<ModelProjectRef> dexUserDefaultProjects) {
    this.dexUserDefaultProjects = dexUserDefaultProjects;
  }

  public V1SystemInfo enableCollection(Boolean enableCollection) {
    this.enableCollection = enableCollection;
    return this;
  }

  /**
   * Get enableCollection
   * @return enableCollection
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Boolean isEnableCollection() {
    return enableCollection;
  }

  public void setEnableCollection(Boolean enableCollection) {
    this.enableCollection = enableCollection;
  }

  public V1SystemInfo installTime(OffsetDateTime installTime) {
    this.installTime = installTime;
    return this;
  }

  /**
   * Get installTime
   * @return installTime
  **/
  @ApiModelProperty(value = "")

  @Valid

  public OffsetDateTime getInstallTime() {
    return installTime;
  }

  public void setInstallTime(OffsetDateTime installTime) {
    this.installTime = installTime;
  }

  public V1SystemInfo loginType(String loginType) {
    this.loginType = loginType;
    return this;
  }

  /**
   * Get loginType
   * @return loginType
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getLoginType() {
    return loginType;
  }

  public void setLoginType(String loginType) {
    this.loginType = loginType;
  }

  public V1SystemInfo platformID(String platformID) {
    this.platformID = platformID;
    return this;
  }

  /**
   * Get platformID
   * @return platformID
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getPlatformID() {
    return platformID;
  }

  public void setPlatformID(String platformID) {
    this.platformID = platformID;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1SystemInfo v1SystemInfo = (V1SystemInfo) o;
    return Objects.equals(this.dexUserDefaultPlatformRoles, v1SystemInfo.dexUserDefaultPlatformRoles) &&
        Objects.equals(this.dexUserDefaultProjects, v1SystemInfo.dexUserDefaultProjects) &&
        Objects.equals(this.enableCollection, v1SystemInfo.enableCollection) &&
        Objects.equals(this.installTime, v1SystemInfo.installTime) &&
        Objects.equals(this.loginType, v1SystemInfo.loginType) &&
        Objects.equals(this.platformID, v1SystemInfo.platformID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dexUserDefaultPlatformRoles, dexUserDefaultProjects, enableCollection, installTime, loginType, platformID);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1SystemInfo {\n");
    
    sb.append("    dexUserDefaultPlatformRoles: ").append(toIndentedString(dexUserDefaultPlatformRoles)).append("\n");
    sb.append("    dexUserDefaultProjects: ").append(toIndentedString(dexUserDefaultProjects)).append("\n");
    sb.append("    enableCollection: ").append(toIndentedString(enableCollection)).append("\n");
    sb.append("    installTime: ").append(toIndentedString(installTime)).append("\n");
    sb.append("    loginType: ").append(toIndentedString(loginType)).append("\n");
    sb.append("    platformID: ").append(toIndentedString(platformID)).append("\n");
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

