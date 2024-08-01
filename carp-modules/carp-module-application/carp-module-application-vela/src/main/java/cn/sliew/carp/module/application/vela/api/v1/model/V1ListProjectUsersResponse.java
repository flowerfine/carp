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
 * V1ListProjectUsersResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ListProjectUsersResponse   {
  @JsonProperty("total")
  private Long total = null;

  @JsonProperty("users")
  @Valid
  private List<V1ProjectUserBase> users = new ArrayList<V1ProjectUserBase>();

  public V1ListProjectUsersResponse total(Long total) {
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

  public V1ListProjectUsersResponse users(List<V1ProjectUserBase> users) {
    this.users = users;
    return this;
  }

  public V1ListProjectUsersResponse addUsersItem(V1ProjectUserBase usersItem) {
    this.users.add(usersItem);
    return this;
  }

  /**
   * Get users
   * @return users
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1ProjectUserBase> getUsers() {
    return users;
  }

  public void setUsers(List<V1ProjectUserBase> users) {
    this.users = users;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1ListProjectUsersResponse v1ListProjectUsersResponse = (V1ListProjectUsersResponse) o;
    return Objects.equals(this.total, v1ListProjectUsersResponse.total) &&
        Objects.equals(this.users, v1ListProjectUsersResponse.users);
  }

  @Override
  public int hashCode() {
    return Objects.hash(total, users);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ListProjectUsersResponse {\n");
    
    sb.append("    total: ").append(toIndentedString(total)).append("\n");
    sb.append("    users: ").append(toIndentedString(users)).append("\n");
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

