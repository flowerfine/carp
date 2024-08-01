package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * SchemaStyle
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class SchemaStyle   {
  @JsonProperty("colSpan")
  private Integer colSpan = null;

  public SchemaStyle colSpan(Integer colSpan) {
    this.colSpan = colSpan;
    return this;
  }

  /**
   * Get colSpan
   * @return colSpan
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getColSpan() {
    return colSpan;
  }

  public void setColSpan(Integer colSpan) {
    this.colSpan = colSpan;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SchemaStyle schemaStyle = (SchemaStyle) o;
    return Objects.equals(this.colSpan, schemaStyle.colSpan);
  }

  @Override
  public int hashCode() {
    return Objects.hash(colSpan);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SchemaStyle {\n");
    
    sb.append("    colSpan: ").append(toIndentedString(colSpan)).append("\n");
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

