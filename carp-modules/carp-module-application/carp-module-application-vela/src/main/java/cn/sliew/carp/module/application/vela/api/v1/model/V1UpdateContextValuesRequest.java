package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;

import cn.sliew.carp.module.application.vela.api.v1.model.common.ModelValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1UpdateContextValuesRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1UpdateContextValuesRequest   {
  @JsonProperty("values")
  @Valid
  private List<ModelValue> values = new ArrayList<ModelValue>();

  public V1UpdateContextValuesRequest values(List<ModelValue> values) {
    this.values = values;
    return this;
  }

  public V1UpdateContextValuesRequest addValuesItem(ModelValue valuesItem) {
    this.values.add(valuesItem);
    return this;
  }

  /**
   * Get values
   * @return values
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<ModelValue> getValues() {
    return values;
  }

  public void setValues(List<ModelValue> values) {
    this.values = values;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1UpdateContextValuesRequest v1UpdateContextValuesRequest = (V1UpdateContextValuesRequest) o;
    return Objects.equals(this.values, v1UpdateContextValuesRequest.values);
  }

  @Override
  public int hashCode() {
    return Objects.hash(values);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1UpdateContextValuesRequest {\n");
    
    sb.append("    values: ").append(toIndentedString(values)).append("\n");
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

