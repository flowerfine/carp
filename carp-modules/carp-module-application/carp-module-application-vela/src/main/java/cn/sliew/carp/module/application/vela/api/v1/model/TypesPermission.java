package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TypesPermission
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class TypesPermission   {
  @JsonProperty("action")
  private String action = null;

  @JsonProperty("resource")
  private String resource = null;

  public TypesPermission action(String action) {
    this.action = action;
    return this;
  }

  /**
   * Get action
   * @return action
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public TypesPermission resource(String resource) {
    this.resource = resource;
    return this;
  }

  /**
   * Get resource
   * @return resource
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getResource() {
    return resource;
  }

  public void setResource(String resource) {
    this.resource = resource;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TypesPermission typesPermission = (TypesPermission) o;
    return Objects.equals(this.action, typesPermission.action) &&
        Objects.equals(this.resource, typesPermission.resource);
  }

  @Override
  public int hashCode() {
    return Objects.hash(action, resource);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TypesPermission {\n");
    
    sb.append("    action: ").append(toIndentedString(action)).append("\n");
    sb.append("    resource: ").append(toIndentedString(resource)).append("\n");
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

