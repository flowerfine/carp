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
 * V1UpdateProjectUserRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1UpdateProjectUserRequest   {
  @JsonProperty("userRoles")
  @Valid
  private List<String> userRoles = new ArrayList<String>();

  public V1UpdateProjectUserRequest userRoles(List<String> userRoles) {
    this.userRoles = userRoles;
    return this;
  }

  public V1UpdateProjectUserRequest addUserRolesItem(String userRolesItem) {
    this.userRoles.add(userRolesItem);
    return this;
  }

  /**
   * Get userRoles
   * @return userRoles
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public List<String> getUserRoles() {
    return userRoles;
  }

  public void setUserRoles(List<String> userRoles) {
    this.userRoles = userRoles;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1UpdateProjectUserRequest v1UpdateProjectUserRequest = (V1UpdateProjectUserRequest) o;
    return Objects.equals(this.userRoles, v1UpdateProjectUserRequest.userRoles);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userRoles);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1UpdateProjectUserRequest {\n");
    
    sb.append("    userRoles: ").append(toIndentedString(userRoles)).append("\n");
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

