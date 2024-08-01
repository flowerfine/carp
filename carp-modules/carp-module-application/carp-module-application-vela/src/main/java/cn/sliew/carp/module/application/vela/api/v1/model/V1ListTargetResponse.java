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
 * V1ListTargetResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ListTargetResponse   {
  @JsonProperty("targets")
  @Valid
  private List<V1TargetBase> targets = new ArrayList<V1TargetBase>();

  @JsonProperty("total")
  private Long total = null;

  public V1ListTargetResponse targets(List<V1TargetBase> targets) {
    this.targets = targets;
    return this;
  }

  public V1ListTargetResponse addTargetsItem(V1TargetBase targetsItem) {
    this.targets.add(targetsItem);
    return this;
  }

  /**
   * Get targets
   * @return targets
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1TargetBase> getTargets() {
    return targets;
  }

  public void setTargets(List<V1TargetBase> targets) {
    this.targets = targets;
  }

  public V1ListTargetResponse total(Long total) {
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
    V1ListTargetResponse v1ListTargetResponse = (V1ListTargetResponse) o;
    return Objects.equals(this.targets, v1ListTargetResponse.targets) &&
        Objects.equals(this.total, v1ListTargetResponse.total);
  }

  @Override
  public int hashCode() {
    return Objects.hash(targets, total);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ListTargetResponse {\n");
    
    sb.append("    targets: ").append(toIndentedString(targets)).append("\n");
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

