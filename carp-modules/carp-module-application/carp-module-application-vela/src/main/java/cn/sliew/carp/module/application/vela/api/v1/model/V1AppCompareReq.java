package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1AppCompareReq
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1AppCompareReq   {
  @JsonProperty("compareLatestWithRunning")
  private V1CompareLatestWithRunningOption compareLatestWithRunning = null;

  @JsonProperty("compareRevisionWithLatest")
  private V1CompareRevisionWithLatestOption compareRevisionWithLatest = null;

  @JsonProperty("compareRevisionWithRunning")
  private V1CompareRevisionWithRunningOption compareRevisionWithRunning = null;

  public V1AppCompareReq compareLatestWithRunning(V1CompareLatestWithRunningOption compareLatestWithRunning) {
    this.compareLatestWithRunning = compareLatestWithRunning;
    return this;
  }

  /**
   * Get compareLatestWithRunning
   * @return compareLatestWithRunning
  **/
  @ApiModelProperty(value = "")

  @Valid

  public V1CompareLatestWithRunningOption getCompareLatestWithRunning() {
    return compareLatestWithRunning;
  }

  public void setCompareLatestWithRunning(V1CompareLatestWithRunningOption compareLatestWithRunning) {
    this.compareLatestWithRunning = compareLatestWithRunning;
  }

  public V1AppCompareReq compareRevisionWithLatest(V1CompareRevisionWithLatestOption compareRevisionWithLatest) {
    this.compareRevisionWithLatest = compareRevisionWithLatest;
    return this;
  }

  /**
   * Get compareRevisionWithLatest
   * @return compareRevisionWithLatest
  **/
  @ApiModelProperty(value = "")

  @Valid

  public V1CompareRevisionWithLatestOption getCompareRevisionWithLatest() {
    return compareRevisionWithLatest;
  }

  public void setCompareRevisionWithLatest(V1CompareRevisionWithLatestOption compareRevisionWithLatest) {
    this.compareRevisionWithLatest = compareRevisionWithLatest;
  }

  public V1AppCompareReq compareRevisionWithRunning(V1CompareRevisionWithRunningOption compareRevisionWithRunning) {
    this.compareRevisionWithRunning = compareRevisionWithRunning;
    return this;
  }

  /**
   * Get compareRevisionWithRunning
   * @return compareRevisionWithRunning
  **/
  @ApiModelProperty(value = "")

  @Valid

  public V1CompareRevisionWithRunningOption getCompareRevisionWithRunning() {
    return compareRevisionWithRunning;
  }

  public void setCompareRevisionWithRunning(V1CompareRevisionWithRunningOption compareRevisionWithRunning) {
    this.compareRevisionWithRunning = compareRevisionWithRunning;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1AppCompareReq v1AppCompareReq = (V1AppCompareReq) o;
    return Objects.equals(this.compareLatestWithRunning, v1AppCompareReq.compareLatestWithRunning) &&
        Objects.equals(this.compareRevisionWithLatest, v1AppCompareReq.compareRevisionWithLatest) &&
        Objects.equals(this.compareRevisionWithRunning, v1AppCompareReq.compareRevisionWithRunning);
  }

  @Override
  public int hashCode() {
    return Objects.hash(compareLatestWithRunning, compareRevisionWithLatest, compareRevisionWithRunning);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1AppCompareReq {\n");
    
    sb.append("    compareLatestWithRunning: ").append(toIndentedString(compareLatestWithRunning)).append("\n");
    sb.append("    compareRevisionWithLatest: ").append(toIndentedString(compareRevisionWithLatest)).append("\n");
    sb.append("    compareRevisionWithRunning: ").append(toIndentedString(compareRevisionWithRunning)).append("\n");
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

