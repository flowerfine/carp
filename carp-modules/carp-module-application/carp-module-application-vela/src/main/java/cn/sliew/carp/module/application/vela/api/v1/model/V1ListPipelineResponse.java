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
 * V1ListPipelineResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ListPipelineResponse   {
  @JsonProperty("pipelines")
  @Valid
  private List<V1PipelineListItem> pipelines = new ArrayList<V1PipelineListItem>();

  @JsonProperty("total")
  private Integer total = null;

  public V1ListPipelineResponse pipelines(List<V1PipelineListItem> pipelines) {
    this.pipelines = pipelines;
    return this;
  }

  public V1ListPipelineResponse addPipelinesItem(V1PipelineListItem pipelinesItem) {
    this.pipelines.add(pipelinesItem);
    return this;
  }

  /**
   * Get pipelines
   * @return pipelines
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1PipelineListItem> getPipelines() {
    return pipelines;
  }

  public void setPipelines(List<V1PipelineListItem> pipelines) {
    this.pipelines = pipelines;
  }

  public V1ListPipelineResponse total(Integer total) {
    this.total = total;
    return this;
  }

  /**
   * Get total
   * @return total
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getTotal() {
    return total;
  }

  public void setTotal(Integer total) {
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
    V1ListPipelineResponse v1ListPipelineResponse = (V1ListPipelineResponse) o;
    return Objects.equals(this.pipelines, v1ListPipelineResponse.pipelines) &&
        Objects.equals(this.total, v1ListPipelineResponse.total);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pipelines, total);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ListPipelineResponse {\n");
    
    sb.append("    pipelines: ").append(toIndentedString(pipelines)).append("\n");
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

