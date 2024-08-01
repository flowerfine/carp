package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1LoginUserInfoResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1LoginUserInfoResponse   {
  @JsonProperty("alias")
  private String alias = null;

  @JsonProperty("createTime")
  private OffsetDateTime createTime = null;

  @JsonProperty("disabled")
  private Boolean disabled = null;

  @JsonProperty("email")
  private String email = null;

  @JsonProperty("lastLoginTime")
  private OffsetDateTime lastLoginTime = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("platformPermissions")
  @Valid
  private List<V1PermissionBase> platformPermissions = new ArrayList<V1PermissionBase>();

  @JsonProperty("projectPermissions")
  @Valid
  private Map<String, List<V1PermissionBase>> projectPermissions = new HashMap<String, List<V1PermissionBase>>();

  @JsonProperty("projects")
  @Valid
  private List<V1UserProjectBase> projects = new ArrayList<V1UserProjectBase>();

  public V1LoginUserInfoResponse alias(String alias) {
    this.alias = alias;
    return this;
  }

  /**
   * Get alias
   * @return alias
  **/
  @ApiModelProperty(value = "")


  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public V1LoginUserInfoResponse createTime(OffsetDateTime createTime) {
    this.createTime = createTime;
    return this;
  }

  /**
   * Get createTime
   * @return createTime
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public OffsetDateTime getCreateTime() {
    return createTime;
  }

  public void setCreateTime(OffsetDateTime createTime) {
    this.createTime = createTime;
  }

  public V1LoginUserInfoResponse disabled(Boolean disabled) {
    this.disabled = disabled;
    return this;
  }

  /**
   * Get disabled
   * @return disabled
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Boolean isDisabled() {
    return disabled;
  }

  public void setDisabled(Boolean disabled) {
    this.disabled = disabled;
  }

  public V1LoginUserInfoResponse email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public V1LoginUserInfoResponse lastLoginTime(OffsetDateTime lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
    return this;
  }

  /**
   * Get lastLoginTime
   * @return lastLoginTime
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public OffsetDateTime getLastLoginTime() {
    return lastLoginTime;
  }

  public void setLastLoginTime(OffsetDateTime lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }

  public V1LoginUserInfoResponse name(String name) {
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

  public V1LoginUserInfoResponse platformPermissions(List<V1PermissionBase> platformPermissions) {
    this.platformPermissions = platformPermissions;
    return this;
  }

  public V1LoginUserInfoResponse addPlatformPermissionsItem(V1PermissionBase platformPermissionsItem) {
    this.platformPermissions.add(platformPermissionsItem);
    return this;
  }

  /**
   * Get platformPermissions
   * @return platformPermissions
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1PermissionBase> getPlatformPermissions() {
    return platformPermissions;
  }

  public void setPlatformPermissions(List<V1PermissionBase> platformPermissions) {
    this.platformPermissions = platformPermissions;
  }

  public V1LoginUserInfoResponse projectPermissions(Map<String, List<V1PermissionBase>> projectPermissions) {
    this.projectPermissions = projectPermissions;
    return this;
  }

  public V1LoginUserInfoResponse putProjectPermissionsItem(String key, List<V1PermissionBase> projectPermissionsItem) {
    this.projectPermissions.put(key, projectPermissionsItem);
    return this;
  }

  /**
   * Get projectPermissions
   * @return projectPermissions
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public Map<String, List<V1PermissionBase>> getProjectPermissions() {
    return projectPermissions;
  }

  public void setProjectPermissions(Map<String, List<V1PermissionBase>> projectPermissions) {
    this.projectPermissions = projectPermissions;
  }

  public V1LoginUserInfoResponse projects(List<V1UserProjectBase> projects) {
    this.projects = projects;
    return this;
  }

  public V1LoginUserInfoResponse addProjectsItem(V1UserProjectBase projectsItem) {
    this.projects.add(projectsItem);
    return this;
  }

  /**
   * Get projects
   * @return projects
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1UserProjectBase> getProjects() {
    return projects;
  }

  public void setProjects(List<V1UserProjectBase> projects) {
    this.projects = projects;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1LoginUserInfoResponse v1LoginUserInfoResponse = (V1LoginUserInfoResponse) o;
    return Objects.equals(this.alias, v1LoginUserInfoResponse.alias) &&
        Objects.equals(this.createTime, v1LoginUserInfoResponse.createTime) &&
        Objects.equals(this.disabled, v1LoginUserInfoResponse.disabled) &&
        Objects.equals(this.email, v1LoginUserInfoResponse.email) &&
        Objects.equals(this.lastLoginTime, v1LoginUserInfoResponse.lastLoginTime) &&
        Objects.equals(this.name, v1LoginUserInfoResponse.name) &&
        Objects.equals(this.platformPermissions, v1LoginUserInfoResponse.platformPermissions) &&
        Objects.equals(this.projectPermissions, v1LoginUserInfoResponse.projectPermissions) &&
        Objects.equals(this.projects, v1LoginUserInfoResponse.projects);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, createTime, disabled, email, lastLoginTime, name, platformPermissions, projectPermissions, projects);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1LoginUserInfoResponse {\n");
    
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    createTime: ").append(toIndentedString(createTime)).append("\n");
    sb.append("    disabled: ").append(toIndentedString(disabled)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    lastLoginTime: ").append(toIndentedString(lastLoginTime)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    platformPermissions: ").append(toIndentedString(platformPermissions)).append("\n");
    sb.append("    projectPermissions: ").append(toIndentedString(projectPermissions)).append("\n");
    sb.append("    projects: ").append(toIndentedString(projects)).append("\n");
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

