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
 * V1ListCloudClusterCreationResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ListCloudClusterCreationResponse   {
  @JsonProperty("creations")
  @Valid
  private List<V1CreateCloudClusterResponse> creations = new ArrayList<V1CreateCloudClusterResponse>();

  public V1ListCloudClusterCreationResponse creations(List<V1CreateCloudClusterResponse> creations) {
    this.creations = creations;
    return this;
  }

  public V1ListCloudClusterCreationResponse addCreationsItem(V1CreateCloudClusterResponse creationsItem) {
    this.creations.add(creationsItem);
    return this;
  }

  /**
   * Get creations
   * @return creations
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1CreateCloudClusterResponse> getCreations() {
    return creations;
  }

  public void setCreations(List<V1CreateCloudClusterResponse> creations) {
    this.creations = creations;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1ListCloudClusterCreationResponse v1ListCloudClusterCreationResponse = (V1ListCloudClusterCreationResponse) o;
    return Objects.equals(this.creations, v1ListCloudClusterCreationResponse.creations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(creations);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ListCloudClusterCreationResponse {\n");
    
    sb.append("    creations: ").append(toIndentedString(creations)).append("\n");
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

