package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1alpha1InputItem
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1alpha1InputItem   {
  @JsonProperty("from")
  private String from = null;

  @JsonProperty("parameterKey")
  private String parameterKey = null;

  public V1alpha1InputItem from(String from) {
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

  public V1alpha1InputItem parameterKey(String parameterKey) {
    this.parameterKey = parameterKey;
    return this;
  }

  /**
   * Get parameterKey
   * @return parameterKey
  **/
  @ApiModelProperty(value = "")


  public String getParameterKey() {
    return parameterKey;
  }

  public void setParameterKey(String parameterKey) {
    this.parameterKey = parameterKey;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1alpha1InputItem v1alpha1InputItem = (V1alpha1InputItem) o;
    return Objects.equals(this.from, v1alpha1InputItem.from) &&
        Objects.equals(this.parameterKey, v1alpha1InputItem.parameterKey);
  }

  @Override
  public int hashCode() {
    return Objects.hash(from, parameterKey);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1alpha1InputItem {\n");
    
    sb.append("    from: ").append(toIndentedString(from)).append("\n");
    sb.append("    parameterKey: ").append(toIndentedString(parameterKey)).append("\n");
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

