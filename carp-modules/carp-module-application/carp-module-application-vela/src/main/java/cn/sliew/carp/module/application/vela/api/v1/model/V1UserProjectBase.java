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
 * V1UserProjectBase
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1UserProjectBase   {
  @JsonProperty("alias")
  private String alias = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("joinTime")
  private OffsetDateTime joinTime = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("owner")
  private V1NameAlias owner = null;

  @JsonProperty("roles")
  @Valid
  private List<V1NameAlias> roles = new ArrayList<V1NameAlias>();

  public V1UserProjectBase alias(String alias) {
    this.alias = alias;
    return this;
  }

  /**
   * Get alias
   * @return alias
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public V1UserProjectBase description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public V1UserProjectBase joinTime(OffsetDateTime joinTime) {
    this.joinTime = joinTime;
    return this;
  }

  /**
   * Get joinTime
   * @return joinTime
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public OffsetDateTime getJoinTime() {
    return joinTime;
  }

  public void setJoinTime(OffsetDateTime joinTime) {
    this.joinTime = joinTime;
  }

  public V1UserProjectBase name(String name) {
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

  public V1UserProjectBase owner(V1NameAlias owner) {
    this.owner = owner;
    return this;
  }

  /**
   * Get owner
   * @return owner
  **/
  @ApiModelProperty(value = "")

  @Valid

  public V1NameAlias getOwner() {
    return owner;
  }

  public void setOwner(V1NameAlias owner) {
    this.owner = owner;
  }

  public V1UserProjectBase roles(List<V1NameAlias> roles) {
    this.roles = roles;
    return this;
  }

  public V1UserProjectBase addRolesItem(V1NameAlias rolesItem) {
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
    V1UserProjectBase v1UserProjectBase = (V1UserProjectBase) o;
    return Objects.equals(this.alias, v1UserProjectBase.alias) &&
        Objects.equals(this.description, v1UserProjectBase.description) &&
        Objects.equals(this.joinTime, v1UserProjectBase.joinTime) &&
        Objects.equals(this.name, v1UserProjectBase.name) &&
        Objects.equals(this.owner, v1UserProjectBase.owner) &&
        Objects.equals(this.roles, v1UserProjectBase.roles);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, description, joinTime, name, owner, roles);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1UserProjectBase {\n");
    
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    joinTime: ").append(toIndentedString(joinTime)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    owner: ").append(toIndentedString(owner)).append("\n");
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

