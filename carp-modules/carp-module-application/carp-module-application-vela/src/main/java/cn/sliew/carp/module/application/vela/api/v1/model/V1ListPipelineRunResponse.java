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
 * V1ListPipelineRunResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ListPipelineRunResponse   {
  @JsonProperty("runs")
  @Valid
  private List<V1PipelineRunBriefing> runs = new ArrayList<V1PipelineRunBriefing>();

  @JsonProperty("total")
  private Long total = null;

  public V1ListPipelineRunResponse runs(List<V1PipelineRunBriefing> runs) {
    this.runs = runs;
    return this;
  }

  public V1ListPipelineRunResponse addRunsItem(V1PipelineRunBriefing runsItem) {
    this.runs.add(runsItem);
    return this;
  }

  /**
   * Get runs
   * @return runs
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1PipelineRunBriefing> getRuns() {
    return runs;
  }

  public void setRuns(List<V1PipelineRunBriefing> runs) {
    this.runs = runs;
  }

  public V1ListPipelineRunResponse total(Long total) {
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
    V1ListPipelineRunResponse v1ListPipelineRunResponse = (V1ListPipelineRunResponse) o;
    return Objects.equals(this.runs, v1ListPipelineRunResponse.runs) &&
        Objects.equals(this.total, v1ListPipelineRunResponse.total);
  }

  @Override
  public int hashCode() {
    return Objects.hash(runs, total);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ListPipelineRunResponse {\n");
    
    sb.append("    runs: ").append(toIndentedString(runs)).append("\n");
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

