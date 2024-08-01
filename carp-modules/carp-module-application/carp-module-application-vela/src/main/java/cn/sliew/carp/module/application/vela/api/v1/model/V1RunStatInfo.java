package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1RunStatInfo
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1RunStatInfo   {
  @JsonProperty("fail")
  private Integer fail = null;

  @JsonProperty("success")
  private Integer success = null;

  @JsonProperty("total")
  private Integer total = null;

  public V1RunStatInfo fail(Integer fail) {
    this.fail = fail;
    return this;
  }

  /**
   * Get fail
   * @return fail
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getFail() {
    return fail;
  }

  public void setFail(Integer fail) {
    this.fail = fail;
  }

  public V1RunStatInfo success(Integer success) {
    this.success = success;
    return this;
  }

  /**
   * Get success
   * @return success
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getSuccess() {
    return success;
  }

  public void setSuccess(Integer success) {
    this.success = success;
  }

  public V1RunStatInfo total(Integer total) {
    this.total = total;
    return this;
  }

  /**
   * Get total
   * @return total
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getTotal() {
    return total;
  }

  public void setTotal(Integer total) {
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
    V1RunStatInfo v1RunStatInfo = (V1RunStatInfo) o;
    return Objects.equals(this.fail, v1RunStatInfo.fail) &&
        Objects.equals(this.success, v1RunStatInfo.success) &&
        Objects.equals(this.total, v1RunStatInfo.total);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fail, success, total);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1RunStatInfo {\n");
    
    sb.append("    fail: ").append(toIndentedString(fail)).append("\n");
    sb.append("    success: ").append(toIndentedString(success)).append("\n");
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

