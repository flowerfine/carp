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
 * V1GetPipelineRunInputResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1GetPipelineRunInputResponse   {
  @JsonProperty("inputs")
  @Valid
  private List<V1StepInputBase> inputs = new ArrayList<V1StepInputBase>();

  public V1GetPipelineRunInputResponse inputs(List<V1StepInputBase> inputs) {
    this.inputs = inputs;
    return this;
  }

  public V1GetPipelineRunInputResponse addInputsItem(V1StepInputBase inputsItem) {
    this.inputs.add(inputsItem);
    return this;
  }

  /**
   * Get inputs
   * @return inputs
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1StepInputBase> getInputs() {
    return inputs;
  }

  public void setInputs(List<V1StepInputBase> inputs) {
    this.inputs = inputs;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1GetPipelineRunInputResponse v1GetPipelineRunInputResponse = (V1GetPipelineRunInputResponse) o;
    return Objects.equals(this.inputs, v1GetPipelineRunInputResponse.inputs);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inputs);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1GetPipelineRunInputResponse {\n");
    
    sb.append("    inputs: ").append(toIndentedString(inputs)).append("\n");
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

