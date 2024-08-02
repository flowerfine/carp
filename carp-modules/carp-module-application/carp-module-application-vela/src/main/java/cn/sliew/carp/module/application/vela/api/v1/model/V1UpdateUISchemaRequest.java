package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;

import cn.sliew.carp.module.application.vela.api.v1.model.common.SchemaUIParameter;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1UpdateUISchemaRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1UpdateUISchemaRequest   {
  @JsonProperty("type")
  private String type = null;

  @JsonProperty("uiSchema")
  @Valid
  private List<SchemaUIParameter> uiSchema = new ArrayList<SchemaUIParameter>();

  public V1UpdateUISchemaRequest type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public V1UpdateUISchemaRequest uiSchema(List<SchemaUIParameter> uiSchema) {
    this.uiSchema = uiSchema;
    return this;
  }

  public V1UpdateUISchemaRequest addUiSchemaItem(SchemaUIParameter uiSchemaItem) {
    this.uiSchema.add(uiSchemaItem);
    return this;
  }

  /**
   * Get uiSchema
   * @return uiSchema
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<SchemaUIParameter> getUiSchema() {
    return uiSchema;
  }

  public void setUiSchema(List<SchemaUIParameter> uiSchema) {
    this.uiSchema = uiSchema;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1UpdateUISchemaRequest v1UpdateUISchemaRequest = (V1UpdateUISchemaRequest) o;
    return Objects.equals(this.type, v1UpdateUISchemaRequest.type) &&
        Objects.equals(this.uiSchema, v1UpdateUISchemaRequest.uiSchema);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, uiSchema);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1UpdateUISchemaRequest {\n");
    
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    uiSchema: ").append(toIndentedString(uiSchema)).append("\n");
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

