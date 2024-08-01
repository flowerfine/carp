package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
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
 * V1DetailUserResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1DetailUserResponse   {
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

  @JsonProperty("projects")
  @Valid
  private List<V1UserProjectBase> projects = new ArrayList<V1UserProjectBase>();

  @JsonProperty("roles")
  @Valid
  private List<V1NameAlias> roles = new ArrayList<V1NameAlias>();

  public V1DetailUserResponse alias(String alias) {
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

  public V1DetailUserResponse createTime(OffsetDateTime createTime) {
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

  public V1DetailUserResponse disabled(Boolean disabled) {
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

  public V1DetailUserResponse email(String email) {
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

  public V1DetailUserResponse lastLoginTime(OffsetDateTime lastLoginTime) {
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

  public V1DetailUserResponse name(String name) {
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

  public V1DetailUserResponse projects(List<V1UserProjectBase> projects) {
    this.projects = projects;
    return this;
  }

  public V1DetailUserResponse addProjectsItem(V1UserProjectBase projectsItem) {
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

  public V1DetailUserResponse roles(List<V1NameAlias> roles) {
    this.roles = roles;
    return this;
  }

  public V1DetailUserResponse addRolesItem(V1NameAlias rolesItem) {
    this.roles.add(rolesItem);
    return this;
  }

  /**
   * Get roles
   * @return roles
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1NameAlias> getRoles() {
    return roles;
  }

  public void setRoles(List<V1NameAlias> roles) {
    this.roles = roles;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1DetailUserResponse v1DetailUserResponse = (V1DetailUserResponse) o;
    return Objects.equals(this.alias, v1DetailUserResponse.alias) &&
        Objects.equals(this.createTime, v1DetailUserResponse.createTime) &&
        Objects.equals(this.disabled, v1DetailUserResponse.disabled) &&
        Objects.equals(this.email, v1DetailUserResponse.email) &&
        Objects.equals(this.lastLoginTime, v1DetailUserResponse.lastLoginTime) &&
        Objects.equals(this.name, v1DetailUserResponse.name) &&
        Objects.equals(this.projects, v1DetailUserResponse.projects) &&
        Objects.equals(this.roles, v1DetailUserResponse.roles);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, createTime, disabled, email, lastLoginTime, name, projects, roles);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1DetailUserResponse {\n");
    
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    createTime: ").append(toIndentedString(createTime)).append("\n");
    sb.append("    disabled: ").append(toIndentedString(disabled)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    lastLoginTime: ").append(toIndentedString(lastLoginTime)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    projects: ").append(toIndentedString(projects)).append("\n");
    sb.append("    roles: ").append(toIndentedString(roles)).append("\n");
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

