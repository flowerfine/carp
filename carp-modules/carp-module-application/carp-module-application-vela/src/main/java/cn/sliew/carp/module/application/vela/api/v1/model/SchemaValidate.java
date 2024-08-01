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
 * SchemaValidate
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class SchemaValidate   {
  @JsonProperty("defaultValue")
  private SchemaValidateDefaultValue defaultValue = null;

  @JsonProperty("immutable")
  private Boolean immutable = null;

  @JsonProperty("max")
  private Double max = null;

  @JsonProperty("maxLength")
  private Integer maxLength = null;

  @JsonProperty("min")
  private Double min = null;

  @JsonProperty("minLength")
  private Integer minLength = null;

  @JsonProperty("options")
  @Valid
  private List<SchemaOption> options = null;

  @JsonProperty("pattern")
  private String pattern = null;

  @JsonProperty("required")
  private Boolean required = null;

  public SchemaValidate defaultValue(SchemaValidateDefaultValue defaultValue) {
    this.defaultValue = defaultValue;
    return this;
  }

  /**
   * Get defaultValue
   * @return defaultValue
  **/
  @ApiModelProperty(value = "")

  @Valid

  public SchemaValidateDefaultValue getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(SchemaValidateDefaultValue defaultValue) {
    this.defaultValue = defaultValue;
  }

  public SchemaValidate immutable(Boolean immutable) {
    this.immutable = immutable;
    return this;
  }

  /**
   * Get immutable
   * @return immutable
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Boolean isImmutable() {
    return immutable;
  }

  public void setImmutable(Boolean immutable) {
    this.immutable = immutable;
  }

  public SchemaValidate max(Double max) {
    this.max = max;
    return this;
  }

  /**
   * Get max
   * @return max
  **/
  @ApiModelProperty(value = "")


  public Double getMax() {
    return max;
  }

  public void setMax(Double max) {
    this.max = max;
  }

  public SchemaValidate maxLength(Integer maxLength) {
    this.maxLength = maxLength;
    return this;
  }

  /**
   * Get maxLength
   * @return maxLength
  **/
  @ApiModelProperty(value = "")


  public Integer getMaxLength() {
    return maxLength;
  }

  public void setMaxLength(Integer maxLength) {
    this.maxLength = maxLength;
  }

  public SchemaValidate min(Double min) {
    this.min = min;
    return this;
  }

  /**
   * Get min
   * @return min
  **/
  @ApiModelProperty(value = "")


  public Double getMin() {
    return min;
  }

  public void setMin(Double min) {
    this.min = min;
  }

  public SchemaValidate minLength(Integer minLength) {
    this.minLength = minLength;
    return this;
  }

  /**
   * Get minLength
   * @return minLength
  **/
  @ApiModelProperty(value = "")


  public Integer getMinLength() {
    return minLength;
  }

  public void setMinLength(Integer minLength) {
    this.minLength = minLength;
  }

  public SchemaValidate options(List<SchemaOption> options) {
    this.options = options;
    return this;
  }

  public SchemaValidate addOptionsItem(SchemaOption optionsItem) {
    if (this.options == null) {
      this.options = new ArrayList<SchemaOption>();
    }
    this.options.add(optionsItem);
    return this;
  }

  /**
   * Get options
   * @return options
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<SchemaOption> getOptions() {
    return options;
  }

  public void setOptions(List<SchemaOption> options) {
    this.options = options;
  }

  public SchemaValidate pattern(String pattern) {
    this.pattern = pattern;
    return this;
  }

  /**
   * Get pattern
   * @return pattern
  **/
  @ApiModelProperty(value = "")


  public String getPattern() {
    return pattern;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  public SchemaValidate required(Boolean required) {
    this.required = required;
    return this;
  }

  /**
   * Get required
   * @return required
  **/
  @ApiModelProperty(value = "")


  public Boolean isRequired() {
    return required;
  }

  public void setRequired(Boolean required) {
    this.required = required;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SchemaValidate schemaValidate = (SchemaValidate) o;
    return Objects.equals(this.defaultValue, schemaValidate.defaultValue) &&
        Objects.equals(this.immutable, schemaValidate.immutable) &&
        Objects.equals(this.max, schemaValidate.max) &&
        Objects.equals(this.maxLength, schemaValidate.maxLength) &&
        Objects.equals(this.min, schemaValidate.min) &&
        Objects.equals(this.minLength, schemaValidate.minLength) &&
        Objects.equals(this.options, schemaValidate.options) &&
        Objects.equals(this.pattern, schemaValidate.pattern) &&
        Objects.equals(this.required, schemaValidate.required);
  }

  @Override
  public int hashCode() {
    return Objects.hash(defaultValue, immutable, max, maxLength, min, minLength, options, pattern, required);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SchemaValidate {\n");
    
    sb.append("    defaultValue: ").append(toIndentedString(defaultValue)).append("\n");
    sb.append("    immutable: ").append(toIndentedString(immutable)).append("\n");
    sb.append("    max: ").append(toIndentedString(max)).append("\n");
    sb.append("    maxLength: ").append(toIndentedString(maxLength)).append("\n");
    sb.append("    min: ").append(toIndentedString(min)).append("\n");
    sb.append("    minLength: ").append(toIndentedString(minLength)).append("\n");
    sb.append("    options: ").append(toIndentedString(options)).append("\n");
    sb.append("    pattern: ").append(toIndentedString(pattern)).append("\n");
    sb.append("    required: ").append(toIndentedString(required)).append("\n");
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

