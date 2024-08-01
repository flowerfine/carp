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
 * V1ListApplicationPolicy
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ListApplicationPolicy   {
  @JsonProperty("policies")
  @Valid
  private List<V1PolicyBase> policies = new ArrayList<V1PolicyBase>();

  public V1ListApplicationPolicy policies(List<V1PolicyBase> policies) {
    this.policies = policies;
    return this;
  }

  public V1ListApplicationPolicy addPoliciesItem(V1PolicyBase policiesItem) {
    this.policies.add(policiesItem);
    return this;
  }

  /**
   * Get policies
   * @return policies
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1PolicyBase> getPolicies() {
    return policies;
  }

  public void setPolicies(List<V1PolicyBase> policies) {
    this.policies = policies;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1ListApplicationPolicy v1ListApplicationPolicy = (V1ListApplicationPolicy) o;
    return Objects.equals(this.policies, v1ListApplicationPolicy.policies);
  }

  @Override
  public int hashCode() {
    return Objects.hash(policies);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ListApplicationPolicy {\n");
    
    sb.append("    policies: ").append(toIndentedString(policies)).append("\n");
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

