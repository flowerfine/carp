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
 * SchemaUIParameter
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class SchemaUIParameter   {
  @JsonProperty("additional")
  private Boolean additional = null;

  @JsonProperty("additionalParameter")
  private SchemaUIParameter additionalParameter = null;

  @JsonProperty("conditions")
  @Valid
  private List<SchemaCondition> conditions = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("disable")
  private Boolean disable = null;

  @JsonProperty("jsonKey")
  private String jsonKey = null;

  @JsonProperty("label")
  private String label = null;

  @JsonProperty("sort")
  private Integer sort = null;

  @JsonProperty("style")
  private SchemaStyle style = null;

  @JsonProperty("subParameterGroupOption")
  @Valid
  private List<SchemaGroupOption> subParameterGroupOption = null;

  @JsonProperty("subParameters")
  @Valid
  private List<SchemaUIParameter> subParameters = null;

  @JsonProperty("uiType")
  private String uiType = null;

  @JsonProperty("validate")
  private SchemaValidate validate = null;

  public SchemaUIParameter additional(Boolean additional) {
    this.additional = additional;
    return this;
  }

  /**
   * Get additional
   * @return additional
  **/
  @ApiModelProperty(value = "")


  public Boolean isAdditional() {
    return additional;
  }

  public void setAdditional(Boolean additional) {
    this.additional = additional;
  }

  public SchemaUIParameter additionalParameter(SchemaUIParameter additionalParameter) {
    this.additionalParameter = additionalParameter;
    return this;
  }

  /**
   * Get additionalParameter
   * @return additionalParameter
  **/
  @ApiModelProperty(value = "")

  @Valid

  public SchemaUIParameter getAdditionalParameter() {
    return additionalParameter;
  }

  public void setAdditionalParameter(SchemaUIParameter additionalParameter) {
    this.additionalParameter = additionalParameter;
  }

  public SchemaUIParameter conditions(List<SchemaCondition> conditions) {
    this.conditions = conditions;
    return this;
  }

  public SchemaUIParameter addConditionsItem(SchemaCondition conditionsItem) {
    if (this.conditions == null) {
      this.conditions = new ArrayList<SchemaCondition>();
    }
    this.conditions.add(conditionsItem);
    return this;
  }

  /**
   * Get conditions
   * @return conditions
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<SchemaCondition> getConditions() {
    return conditions;
  }

  public void setConditions(List<SchemaCondition> conditions) {
    this.conditions = conditions;
  }

  public SchemaUIParameter description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public SchemaUIParameter disable(Boolean disable) {
    this.disable = disable;
    return this;
  }

  /**
   * Get disable
   * @return disable
  **/
  @ApiModelProperty(value = "")


  public Boolean isDisable() {
    return disable;
  }

  public void setDisable(Boolean disable) {
    this.disable = disable;
  }

  public SchemaUIParameter jsonKey(String jsonKey) {
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

  public SchemaUIParameter label(String label) {
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

  public SchemaUIParameter sort(Integer sort) {
    this.sort = sort;
    return this;
  }

  /**
   * Get sort
   * @return sort
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getSort() {
    return sort;
  }

  public void setSort(Integer sort) {
    this.sort = sort;
  }

  public SchemaUIParameter style(SchemaStyle style) {
    this.style = style;
    return this;
  }

  /**
   * Get style
   * @return style
  **/
  @ApiModelProperty(value = "")

  @Valid

  public SchemaStyle getStyle() {
    return style;
  }

  public void setStyle(SchemaStyle style) {
    this.style = style;
  }

  public SchemaUIParameter subParameterGroupOption(List<SchemaGroupOption> subParameterGroupOption) {
    this.subParameterGroupOption = subParameterGroupOption;
    return this;
  }

  public SchemaUIParameter addSubParameterGroupOptionItem(SchemaGroupOption subParameterGroupOptionItem) {
    if (this.subParameterGroupOption == null) {
      this.subParameterGroupOption = new ArrayList<SchemaGroupOption>();
    }
    this.subParameterGroupOption.add(subParameterGroupOptionItem);
    return this;
  }

  /**
   * Get subParameterGroupOption
   * @return subParameterGroupOption
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<SchemaGroupOption> getSubParameterGroupOption() {
    return subParameterGroupOption;
  }

  public void setSubParameterGroupOption(List<SchemaGroupOption> subParameterGroupOption) {
    this.subParameterGroupOption = subParameterGroupOption;
  }

  public SchemaUIParameter subParameters(List<SchemaUIParameter> subParameters) {
    this.subParameters = subParameters;
    return this;
  }

  public SchemaUIParameter addSubParametersItem(SchemaUIParameter subParametersItem) {
    if (this.subParameters == null) {
      this.subParameters = new ArrayList<SchemaUIParameter>();
    }
    this.subParameters.add(subParametersItem);
    return this;
  }

  /**
   * Get subParameters
   * @return subParameters
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<SchemaUIParameter> getSubParameters() {
    return subParameters;
  }

  public void setSubParameters(List<SchemaUIParameter> subParameters) {
    this.subParameters = subParameters;
  }

  public SchemaUIParameter uiType(String uiType) {
    this.uiType = uiType;
    return this;
  }

  /**
   * Get uiType
   * @return uiType
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getUiType() {
    return uiType;
  }

  public void setUiType(String uiType) {
    this.uiType = uiType;
  }

  public SchemaUIParameter validate(SchemaValidate validate) {
    this.validate = validate;
    return this;
  }

  /**
   * Get validate
   * @return validate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public SchemaValidate getValidate() {
    return validate;
  }

  public void setValidate(SchemaValidate validate) {
    this.validate = validate;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SchemaUIParameter schemaUIParameter = (SchemaUIParameter) o;
    return Objects.equals(this.additional, schemaUIParameter.additional) &&
        Objects.equals(this.additionalParameter, schemaUIParameter.additionalParameter) &&
        Objects.equals(this.conditions, schemaUIParameter.conditions) &&
        Objects.equals(this.description, schemaUIParameter.description) &&
        Objects.equals(this.disable, schemaUIParameter.disable) &&
        Objects.equals(this.jsonKey, schemaUIParameter.jsonKey) &&
        Objects.equals(this.label, schemaUIParameter.label) &&
        Objects.equals(this.sort, schemaUIParameter.sort) &&
        Objects.equals(this.style, schemaUIParameter.style) &&
        Objects.equals(this.subParameterGroupOption, schemaUIParameter.subParameterGroupOption) &&
        Objects.equals(this.subParameters, schemaUIParameter.subParameters) &&
        Objects.equals(this.uiType, schemaUIParameter.uiType) &&
        Objects.equals(this.validate, schemaUIParameter.validate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(additional, additionalParameter, conditions, description, disable, jsonKey, label, sort, style, subParameterGroupOption, subParameters, uiType, validate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SchemaUIParameter {\n");
    
    sb.append("    additional: ").append(toIndentedString(additional)).append("\n");
    sb.append("    additionalParameter: ").append(toIndentedString(additionalParameter)).append("\n");
    sb.append("    conditions: ").append(toIndentedString(conditions)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    disable: ").append(toIndentedString(disable)).append("\n");
    sb.append("    jsonKey: ").append(toIndentedString(jsonKey)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    sort: ").append(toIndentedString(sort)).append("\n");
    sb.append("    style: ").append(toIndentedString(style)).append("\n");
    sb.append("    subParameterGroupOption: ").append(toIndentedString(subParameterGroupOption)).append("\n");
    sb.append("    subParameters: ").append(toIndentedString(subParameters)).append("\n");
    sb.append("    uiType: ").append(toIndentedString(uiType)).append("\n");
    sb.append("    validate: ").append(toIndentedString(validate)).append("\n");
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

