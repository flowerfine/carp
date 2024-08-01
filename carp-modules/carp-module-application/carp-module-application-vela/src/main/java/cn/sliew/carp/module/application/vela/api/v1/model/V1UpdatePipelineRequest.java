package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1UpdatePipelineRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1UpdatePipelineRequest   {
  @JsonProperty("alias")
  private String alias = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("spec")
  private ModelWorkflowSpec spec = null;

  public V1UpdatePipelineRequest alias(String alias) {
    this.alias = alias;
    return this;
  }

  /**
   * Get alias
   * @return alias
  **/
  @ApiModelProperty(value = "")


  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public V1UpdatePipelineRequest description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  **/
  @ApiModelProperty(value = "")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public V1UpdatePipelineRequest spec(ModelWorkflowSpec spec) {
    this.spec = spec;
    return this;
  }

  /**
   * Get spec
   * @return spec
  **/
  @ApiModelProperty(value = "")

  @Valid

  public ModelWorkflowSpec getSpec() {
    return spec;
  }

  public void setSpec(ModelWorkflowSpec spec) {
    this.spec = spec;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1UpdatePipelineRequest v1UpdatePipelineRequest = (V1UpdatePipelineRequest) o;
    return Objects.equals(this.alias, v1UpdatePipelineRequest.alias) &&
        Objects.equals(this.description, v1UpdatePipelineRequest.description) &&
        Objects.equals(this.spec, v1UpdatePipelineRequest.spec);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, description, spec);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1UpdatePipelineRequest {\n");
    
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    spec: ").append(toIndentedString(spec)).append("\n");
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

