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
 * V1ListRolesResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ListRolesResponse   {
  @JsonProperty("roles")
  @Valid
  private List<V1RoleBase> roles = new ArrayList<V1RoleBase>();

  @JsonProperty("total")
  private Long total = null;

  public V1ListRolesResponse roles(List<V1RoleBase> roles) {
    this.roles = roles;
    return this;
  }

  public V1ListRolesResponse addRolesItem(V1RoleBase rolesItem) {
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

  public List<V1RoleBase> getRoles() {
    return roles;
  }

  public void setRoles(List<V1RoleBase> roles) {
    this.roles = roles;
  }

  public V1ListRolesResponse total(Long total) {
    this.total = total;
    return this;
  }

  /**
   * Get total
   * @return total
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getTotal() {
    return total;
  }

  public void setTotal(Long total) {
    this.total = total;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1ListRolesResponse v1ListRolesResponse = (V1ListRolesResponse) o;
    return Objects.equals(this.roles, v1ListRolesResponse.roles) &&
        Objects.equals(this.total, v1ListRolesResponse.total);
  }

  @Override
  public int hashCode() {
    return Objects.hash(roles, total);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ListRolesResponse {\n");
    
    sb.append("    roles: ").append(toIndentedString(roles)).append("\n");
    sb.append("    total: ").append(toIndentedString(total)).append("\n");
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

