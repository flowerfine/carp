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
 * V1SystemInfoRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1SystemInfoRequest   {
  @JsonProperty("dexUserDefaultProjects")
  @Valid
  private List<ModelProjectRef> dexUserDefaultProjects = null;

  @JsonProperty("enableCollection")
  private Boolean enableCollection = null;

  @JsonProperty("loginType")
  private String loginType = null;

  @JsonProperty("velaAddress")
  private String velaAddress = null;

  public V1SystemInfoRequest dexUserDefaultProjects(List<ModelProjectRef> dexUserDefaultProjects) {
    this.dexUserDefaultProjects = dexUserDefaultProjects;
    return this;
  }

  public V1SystemInfoRequest addDexUserDefaultProjectsItem(ModelProjectRef dexUserDefaultProjectsItem) {
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

  public V1SystemInfoRequest enableCollection(Boolean enableCollection) {
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

  public V1SystemInfoRequest loginType(String loginType) {
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

  public V1SystemInfoRequest velaAddress(String velaAddress) {
    this.velaAddress = velaAddress;
    return this;
  }

  /**
   * Get velaAddress
   * @return velaAddress
  **/
  @ApiModelProperty(value = "")


  public String getVelaAddress() {
    return velaAddress;
  }

  public void setVelaAddress(String velaAddress) {
    this.velaAddress = velaAddress;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1SystemInfoRequest v1SystemInfoRequest = (V1SystemInfoRequest) o;
    return Objects.equals(this.dexUserDefaultProjects, v1SystemInfoRequest.dexUserDefaultProjects) &&
        Objects.equals(this.enableCollection, v1SystemInfoRequest.enableCollection) &&
        Objects.equals(this.loginType, v1SystemInfoRequest.loginType) &&
        Objects.equals(this.velaAddress, v1SystemInfoRequest.velaAddress);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dexUserDefaultProjects, enableCollection, loginType, velaAddress);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1SystemInfoRequest {\n");
    
    sb.append("    dexUserDefaultProjects: ").append(toIndentedString(dexUserDefaultProjects)).append("\n");
    sb.append("    enableCollection: ").append(toIndentedString(enableCollection)).append("\n");
    sb.append("    loginType: ").append(toIndentedString(loginType)).append("\n");
    sb.append("    velaAddress: ").append(toIndentedString(velaAddress)).append("\n");
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

