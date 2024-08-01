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
 * V1ListEnvResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ListEnvResponse   {
  @JsonProperty("envs")
  @Valid
  private List<V1Env> envs = new ArrayList<V1Env>();

  @JsonProperty("total")
  private Long total = null;

  public V1ListEnvResponse envs(List<V1Env> envs) {
    this.envs = envs;
    return this;
  }

  public V1ListEnvResponse addEnvsItem(V1Env envsItem) {
    this.envs.add(envsItem);
    return this;
  }

  /**
   * Get envs
   * @return envs
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1Env> getEnvs() {
    return envs;
  }

  public void setEnvs(List<V1Env> envs) {
    this.envs = envs;
  }

  public V1ListEnvResponse total(Long total) {
    this.total = total;
    return this;
  }

  /**
   * Get total
   * @return total
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getTotal() {
    return total;
  }

  public void setTotal(Long total) {
    this.total = total;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1ListEnvResponse v1ListEnvResponse = (V1ListEnvResponse) o;
    return Objects.equals(this.envs, v1ListEnvResponse.envs) &&
        Objects.equals(this.total, v1ListEnvResponse.total);
  }

  @Override
  public int hashCode() {
    return Objects.hash(envs, total);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ListEnvResponse {\n");
    
    sb.append("    envs: ").append(toIndentedString(envs)).append("\n");
    sb.append("    total: ").append(toIndentedString(total)).append("\n");
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

