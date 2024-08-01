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
 * SchemaGroupOption
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class SchemaGroupOption   {
  @JsonProperty("keys")
  @Valid
  private List<String> keys = new ArrayList<String>();

  @JsonProperty("label")
  private String label = null;

  public SchemaGroupOption keys(List<String> keys) {
    this.keys = keys;
    return this;
  }

  public SchemaGroupOption addKeysItem(String keysItem) {
    this.keys.add(keysItem);
    return this;
  }

  /**
   * Get keys
   * @return keys
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public List<String> getKeys() {
    return keys;
  }

  public void setKeys(List<String> keys) {
    this.keys = keys;
  }

  public SchemaGroupOption label(String label) {
    this.label = label;
    return this;
  }

  /**
   * Get label
   * @return label
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SchemaGroupOption schemaGroupOption = (SchemaGroupOption) o;
    return Objects.equals(this.keys, schemaGroupOption.keys) &&
        Objects.equals(this.label, schemaGroupOption.label);
  }

  @Override
  public int hashCode() {
    return Objects.hash(keys, label);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SchemaGroupOption {\n");
    
    sb.append("    keys: ").append(toIndentedString(keys)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
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

