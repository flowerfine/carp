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
 * V1ListWorkflowRecordsResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ListWorkflowRecordsResponse   {
  @JsonProperty("records")
  @Valid
  private List<V1WorkflowRecord> records = new ArrayList<V1WorkflowRecord>();

  @JsonProperty("total")
  private Long total = null;

  public V1ListWorkflowRecordsResponse records(List<V1WorkflowRecord> records) {
    this.records = records;
    return this;
  }

  public V1ListWorkflowRecordsResponse addRecordsItem(V1WorkflowRecord recordsItem) {
    this.records.add(recordsItem);
    return this;
  }

  /**
   * Get records
   * @return records
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1WorkflowRecord> getRecords() {
    return records;
  }

  public void setRecords(List<V1WorkflowRecord> records) {
    this.records = records;
  }

  public V1ListWorkflowRecordsResponse total(Long total) {
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
    V1ListWorkflowRecordsResponse v1ListWorkflowRecordsResponse = (V1ListWorkflowRecordsResponse) o;
    return Objects.equals(this.records, v1ListWorkflowRecordsResponse.records) &&
        Objects.equals(this.total, v1ListWorkflowRecordsResponse.total);
  }

  @Override
  public int hashCode() {
    return Objects.hash(records, total);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ListWorkflowRecordsResponse {\n");
    
    sb.append("    records: ").append(toIndentedString(records)).append("\n");
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

