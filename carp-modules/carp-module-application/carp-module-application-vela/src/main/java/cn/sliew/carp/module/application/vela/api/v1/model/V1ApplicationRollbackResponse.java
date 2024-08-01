package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1ApplicationRollbackResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ApplicationRollbackResponse   {
  @JsonProperty("record")
  private V1WorkflowRecordBase record = null;

  public V1ApplicationRollbackResponse record(V1WorkflowRecordBase record) {
    this.record = record;
    return this;
  }

  /**
   * Get record
   * @return record
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public V1WorkflowRecordBase getRecord() {
    return record;
  }

  public void setRecord(V1WorkflowRecordBase record) {
    this.record = record;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1ApplicationRollbackResponse v1ApplicationRollbackResponse = (V1ApplicationRollbackResponse) o;
    return Objects.equals(this.record, v1ApplicationRollbackResponse.record);
  }

  @Override
  public int hashCode() {
    return Objects.hash(record);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ApplicationRollbackResponse {\n");
    
    sb.append("    record: ").append(toIndentedString(record)).append("\n");
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

