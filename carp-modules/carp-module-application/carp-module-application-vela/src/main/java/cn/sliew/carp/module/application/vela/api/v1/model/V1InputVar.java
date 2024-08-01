package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1InputVar
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1InputVar   {
  @JsonProperty("from")
  private String from = null;

  @JsonProperty("fromStep")
  private String fromStep = null;

  @JsonProperty("parameterKey")
  private String parameterKey = null;

  @JsonProperty("value")
  private String value = null;

  public V1InputVar from(String from) {
    this.from = from;
    return this;
  }

  /**
   * Get from
   * @return from
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public V1InputVar fromStep(String fromStep) {
    this.fromStep = fromStep;
    return this;
  }

  /**
   * Get fromStep
   * @return fromStep
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getFromStep() {
    return fromStep;
  }

  public void setFromStep(String fromStep) {
    this.fromStep = fromStep;
  }

  public V1InputVar parameterKey(String parameterKey) {
    this.parameterKey = parameterKey;
    return this;
  }

  /**
   * Get parameterKey
   * @return parameterKey
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getParameterKey() {
    return parameterKey;
  }

  public void setParameterKey(String parameterKey) {
    this.parameterKey = parameterKey;
  }

  public V1InputVar value(String value) {
    this.value = value;
    return this;
  }

  /**
   * Get value
   * @return value
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1InputVar v1InputVar = (V1InputVar) o;
    return Objects.equals(this.from, v1InputVar.from) &&
        Objects.equals(this.fromStep, v1InputVar.fromStep) &&
        Objects.equals(this.parameterKey, v1InputVar.parameterKey) &&
        Objects.equals(this.value, v1InputVar.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(from, fromStep, parameterKey, value);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1InputVar {\n");
    
    sb.append("    from: ").append(toIndentedString(from)).append("\n");
    sb.append("    fromStep: ").append(toIndentedString(fromStep)).append("\n");
    sb.append("    parameterKey: ").append(toIndentedString(parameterKey)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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

