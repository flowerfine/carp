package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;

import cn.sliew.carp.module.application.vela.api.v1.model.common.SchemaUIParameter;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1ConfigTemplateDetail
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ConfigTemplateDetail   {
  @JsonProperty("alias")
  private String alias = null;

  @JsonProperty("createTime")
  private OffsetDateTime createTime = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("namespace")
  private String namespace = null;

  @JsonProperty("schema")
  private String schema = null;

  @JsonProperty("scope")
  private String scope = null;

  @JsonProperty("sensitive")
  private Boolean sensitive = null;

  @JsonProperty("uiSchema")
  @Valid
  private List<SchemaUIParameter> uiSchema = new ArrayList<SchemaUIParameter>();

  public V1ConfigTemplateDetail alias(String alias) {
    this.alias = alias;
    return this;
  }

  /**
   * Get alias
   * @return alias
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public V1ConfigTemplateDetail createTime(OffsetDateTime createTime) {
    this.createTime = createTime;
    return this;
  }

  /**
   * Get createTime
   * @return createTime
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public OffsetDateTime getCreateTime() {
    return createTime;
  }

  public void setCreateTime(OffsetDateTime createTime) {
    this.createTime = createTime;
  }

  public V1ConfigTemplateDetail description(String description) {
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

  public V1ConfigTemplateDetail name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public V1ConfigTemplateDetail namespace(String namespace) {
    this.namespace = namespace;
    return this;
  }

  /**
   * Get namespace
   * @return namespace
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getNamespace() {
    return namespace;
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public V1ConfigTemplateDetail schema(String schema) {
    this.schema = schema;
    return this;
  }

  /**
   * Get schema
   * @return schema
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getSchema() {
    return schema;
  }

  public void setSchema(String schema) {
    this.schema = schema;
  }

  public V1ConfigTemplateDetail scope(String scope) {
    this.scope = scope;
    return this;
  }

  /**
   * Get scope
   * @return scope
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getScope() {
    return scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  public V1ConfigTemplateDetail sensitive(Boolean sensitive) {
    this.sensitive = sensitive;
    return this;
  }

  /**
   * Get sensitive
   * @return sensitive
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Boolean isSensitive() {
    return sensitive;
  }

  public void setSensitive(Boolean sensitive) {
    this.sensitive = sensitive;
  }

  public V1ConfigTemplateDetail uiSchema(List<SchemaUIParameter> uiSchema) {
    this.uiSchema = uiSchema;
    return this;
  }

  public V1ConfigTemplateDetail addUiSchemaItem(SchemaUIParameter uiSchemaItem) {
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
    V1ConfigTemplateDetail v1ConfigTemplateDetail = (V1ConfigTemplateDetail) o;
    return Objects.equals(this.alias, v1ConfigTemplateDetail.alias) &&
        Objects.equals(this.createTime, v1ConfigTemplateDetail.createTime) &&
        Objects.equals(this.description, v1ConfigTemplateDetail.description) &&
        Objects.equals(this.name, v1ConfigTemplateDetail.name) &&
        Objects.equals(this.namespace, v1ConfigTemplateDetail.namespace) &&
        Objects.equals(this.schema, v1ConfigTemplateDetail.schema) &&
        Objects.equals(this.scope, v1ConfigTemplateDetail.scope) &&
        Objects.equals(this.sensitive, v1ConfigTemplateDetail.sensitive) &&
        Objects.equals(this.uiSchema, v1ConfigTemplateDetail.uiSchema);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, createTime, description, name, namespace, schema, scope, sensitive, uiSchema);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ConfigTemplateDetail {\n");
    
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    createTime: ").append(toIndentedString(createTime)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    namespace: ").append(toIndentedString(namespace)).append("\n");
    sb.append("    schema: ").append(toIndentedString(schema)).append("\n");
    sb.append("    scope: ").append(toIndentedString(scope)).append("\n");
    sb.append("    sensitive: ").append(toIndentedString(sensitive)).append("\n");
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

