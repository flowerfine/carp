package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1AppDryRunReq
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1AppDryRunReq   {
  @JsonProperty("dryRunType")
  private String dryRunType = null;

  @JsonProperty("env")
  private String env = null;

  @JsonProperty("version")
  private String version = null;

  @JsonProperty("workflow")
  private String workflow = null;

  public V1AppDryRunReq dryRunType(String dryRunType) {
    this.dryRunType = dryRunType;
    return this;
  }

  /**
   * Get dryRunType
   * @return dryRunType
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getDryRunType() {
    return dryRunType;
  }

  public void setDryRunType(String dryRunType) {
    this.dryRunType = dryRunType;
  }

  public V1AppDryRunReq env(String env) {
    this.env = env;
    return this;
  }

  /**
   * Get env
   * @return env
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getEnv() {
    return env;
  }

  public void setEnv(String env) {
    this.env = env;
  }

  public V1AppDryRunReq version(String version) {
    this.version = version;
    return this;
  }

  /**
   * Get version
   * @return version
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public V1AppDryRunReq workflow(String workflow) {
    this.workflow = workflow;
    return this;
  }

  /**
   * Get workflow
   * @return workflow
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getWorkflow() {
    return workflow;
  }

  public void setWorkflow(String workflow) {
    this.workflow = workflow;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1AppDryRunReq v1AppDryRunReq = (V1AppDryRunReq) o;
    return Objects.equals(this.dryRunType, v1AppDryRunReq.dryRunType) &&
        Objects.equals(this.env, v1AppDryRunReq.env) &&
        Objects.equals(this.version, v1AppDryRunReq.version) &&
        Objects.equals(this.workflow, v1AppDryRunReq.workflow);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dryRunType, env, version, workflow);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1AppDryRunReq {\n");
    
    sb.append("    dryRunType: ").append(toIndentedString(dryRunType)).append("\n");
    sb.append("    env: ").append(toIndentedString(env)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    workflow: ").append(toIndentedString(workflow)).append("\n");
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

