package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;

import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1ClusterBase;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1ListClusterResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ListClusterResponse   {
  @JsonProperty("clusters")
  @Valid
  private List<V1ClusterBase> clusters = new ArrayList<V1ClusterBase>();

  @JsonProperty("total")
  private Long total = null;

  public V1ListClusterResponse clusters(List<V1ClusterBase> clusters) {
    this.clusters = clusters;
    return this;
  }

  public V1ListClusterResponse addClustersItem(V1ClusterBase clustersItem) {
    this.clusters.add(clustersItem);
    return this;
  }

  /**
   * Get clusters
   * @return clusters
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1ClusterBase> getClusters() {
    return clusters;
  }

  public void setClusters(List<V1ClusterBase> clusters) {
    this.clusters = clusters;
  }

  public V1ListClusterResponse total(Long total) {
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
    V1ListClusterResponse v1ListClusterResponse = (V1ListClusterResponse) o;
    return Objects.equals(this.clusters, v1ListClusterResponse.clusters) &&
        Objects.equals(this.total, v1ListClusterResponse.total);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clusters, total);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ListClusterResponse {\n");
    
    sb.append("    clusters: ").append(toIndentedString(clusters)).append("\n");
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

