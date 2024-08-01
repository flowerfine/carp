package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1ApplicationResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ApplicationResponse   {
  @JsonProperty("apiVersion")
  private String apiVersion = null;

  @JsonProperty("kind")
  private String kind = null;

  @JsonProperty("spec")
  private V1beta1ApplicationSpec spec = null;

  @JsonProperty("status")
  private CommonAppStatus status = null;

  public V1ApplicationResponse apiVersion(String apiVersion) {
    this.apiVersion = apiVersion;
    return this;
  }

  /**
   * Get apiVersion
   * @return apiVersion
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getApiVersion() {
    return apiVersion;
  }

  public void setApiVersion(String apiVersion) {
    this.apiVersion = apiVersion;
  }

  public V1ApplicationResponse kind(String kind) {
    this.kind = kind;
    return this;
  }

  /**
   * Get kind
   * @return kind
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getKind() {
    return kind;
  }

  public void setKind(String kind) {
    this.kind = kind;
  }

  public V1ApplicationResponse spec(V1beta1ApplicationSpec spec) {
    this.spec = spec;
    return this;
  }

  /**
   * Get spec
   * @return spec
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public V1beta1ApplicationSpec getSpec() {
    return spec;
  }

  public void setSpec(V1beta1ApplicationSpec spec) {
    this.spec = spec;
  }

  public V1ApplicationResponse status(CommonAppStatus status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public CommonAppStatus getStatus() {
    return status;
  }

  public void setStatus(CommonAppStatus status) {
    this.status = status;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1ApplicationResponse v1ApplicationResponse = (V1ApplicationResponse) o;
    return Objects.equals(this.apiVersion, v1ApplicationResponse.apiVersion) &&
        Objects.equals(this.kind, v1ApplicationResponse.kind) &&
        Objects.equals(this.spec, v1ApplicationResponse.spec) &&
        Objects.equals(this.status, v1ApplicationResponse.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(apiVersion, kind, spec, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ApplicationResponse {\n");
    
    sb.append("    apiVersion: ").append(toIndentedString(apiVersion)).append("\n");
    sb.append("    kind: ").append(toIndentedString(kind)).append("\n");
    sb.append("    spec: ").append(toIndentedString(spec)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

