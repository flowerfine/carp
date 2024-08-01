package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * SchemaCondition
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class SchemaCondition   {
  @JsonProperty("action")
  private String action = null;

  @JsonProperty("jsonKey")
  private String jsonKey = null;

  @JsonProperty("op")
  private String op = null;

  @JsonProperty("value")
  private SchemaConditionValue value = null;

  public SchemaCondition action(String action) {
    this.action = action;
    return this;
  }

  /**
   * Get action
   * @return action
  **/
  @ApiModelProperty(value = "")


  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public SchemaCondition jsonKey(String jsonKey) {
    this.jsonKey = jsonKey;
    return this;
  }

  /**
   * Get jsonKey
   * @return jsonKey
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getJsonKey() {
    return jsonKey;
  }

  public void setJsonKey(String jsonKey) {
    this.jsonKey = jsonKey;
  }

  public SchemaCondition op(String op) {
    this.op = op;
    return this;
  }

  /**
   * Get op
   * @return op
  **/
  @ApiModelProperty(value = "")


  public String getOp() {
    return op;
  }

  public void setOp(String op) {
    this.op = op;
  }

  public SchemaCondition value(SchemaConditionValue value) {
    this.value = value;
    return this;
  }

  /**
   * Get value
   * @return value
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public SchemaConditionValue getValue() {
    return value;
  }

  public void setValue(SchemaConditionValue value) {
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
    SchemaCondition schemaCondition = (SchemaCondition) o;
    return Objects.equals(this.action, schemaCondition.action) &&
        Objects.equals(this.jsonKey, schemaCondition.jsonKey) &&
        Objects.equals(this.op, schemaCondition.op) &&
        Objects.equals(this.value, schemaCondition.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(action, jsonKey, op, value);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SchemaCondition {\n");
    
    sb.append("    action: ").append(toIndentedString(action)).append("\n");
    sb.append("    jsonKey: ").append(toIndentedString(jsonKey)).append("\n");
    sb.append("    op: ").append(toIndentedString(op)).append("\n");
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

