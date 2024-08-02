package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;

import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1alpha1WorkflowExecuteMode;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1RunPipelineRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1RunPipelineRequest   {
  @JsonProperty("contextName")
  private String contextName = null;

  @JsonProperty("mode")
  private V1alpha1WorkflowExecuteMode mode = null;

  public V1RunPipelineRequest contextName(String contextName) {
    this.contextName = contextName;
    return this;
  }

  /**
   * Get contextName
   * @return contextName
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getContextName() {
    return contextName;
  }

  public void setContextName(String contextName) {
    this.contextName = contextName;
  }

  public V1RunPipelineRequest mode(V1alpha1WorkflowExecuteMode mode) {
    this.mode = mode;
    return this;
  }

  /**
   * Get mode
   * @return mode
  **/
  @ApiModelProperty(value = "")

  @Valid

  public V1alpha1WorkflowExecuteMode getMode() {
    return mode;
  }

  public void setMode(V1alpha1WorkflowExecuteMode mode) {
    this.mode = mode;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1RunPipelineRequest v1RunPipelineRequest = (V1RunPipelineRequest) o;
    return Objects.equals(this.contextName, v1RunPipelineRequest.contextName) &&
        Objects.equals(this.mode, v1RunPipelineRequest.mode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(contextName, mode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1RunPipelineRequest {\n");
    
    sb.append("    contextName: ").append(toIndentedString(contextName)).append("\n");
    sb.append("    mode: ").append(toIndentedString(mode)).append("\n");
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

