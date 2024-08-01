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
 * V1GetPipelineRunOutputResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1GetPipelineRunOutputResponse   {
  @JsonProperty("outputs")
  @Valid
  private List<V1StepOutputBase> outputs = new ArrayList<V1StepOutputBase>();

  public V1GetPipelineRunOutputResponse outputs(List<V1StepOutputBase> outputs) {
    this.outputs = outputs;
    return this;
  }

  public V1GetPipelineRunOutputResponse addOutputsItem(V1StepOutputBase outputsItem) {
    this.outputs.add(outputsItem);
    return this;
  }

  /**
   * Get outputs
   * @return outputs
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1StepOutputBase> getOutputs() {
    return outputs;
  }

  public void setOutputs(List<V1StepOutputBase> outputs) {
    this.outputs = outputs;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1GetPipelineRunOutputResponse v1GetPipelineRunOutputResponse = (V1GetPipelineRunOutputResponse) o;
    return Objects.equals(this.outputs, v1GetPipelineRunOutputResponse.outputs);
  }

  @Override
  public int hashCode() {
    return Objects.hash(outputs);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1GetPipelineRunOutputResponse {\n");
    
    sb.append("    outputs: ").append(toIndentedString(outputs)).append("\n");
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

