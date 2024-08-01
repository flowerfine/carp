package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1AppCompareResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1AppCompareResponse   {
  @JsonProperty("baseAppYAML")
  private String baseAppYAML = null;

  @JsonProperty("diffReport")
  private String diffReport = null;

  @JsonProperty("isDiff")
  private Boolean isDiff = null;

  @JsonProperty("targetAppYAML")
  private String targetAppYAML = null;

  public V1AppCompareResponse baseAppYAML(String baseAppYAML) {
    this.baseAppYAML = baseAppYAML;
    return this;
  }

  /**
   * Get baseAppYAML
   * @return baseAppYAML
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getBaseAppYAML() {
    return baseAppYAML;
  }

  public void setBaseAppYAML(String baseAppYAML) {
    this.baseAppYAML = baseAppYAML;
  }

  public V1AppCompareResponse diffReport(String diffReport) {
    this.diffReport = diffReport;
    return this;
  }

  /**
   * Get diffReport
   * @return diffReport
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getDiffReport() {
    return diffReport;
  }

  public void setDiffReport(String diffReport) {
    this.diffReport = diffReport;
  }

  public V1AppCompareResponse isDiff(Boolean isDiff) {
    this.isDiff = isDiff;
    return this;
  }

  /**
   * Get isDiff
   * @return isDiff
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Boolean isIsDiff() {
    return isDiff;
  }

  public void setIsDiff(Boolean isDiff) {
    this.isDiff = isDiff;
  }

  public V1AppCompareResponse targetAppYAML(String targetAppYAML) {
    this.targetAppYAML = targetAppYAML;
    return this;
  }

  /**
   * Get targetAppYAML
   * @return targetAppYAML
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getTargetAppYAML() {
    return targetAppYAML;
  }

  public void setTargetAppYAML(String targetAppYAML) {
    this.targetAppYAML = targetAppYAML;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1AppCompareResponse v1AppCompareResponse = (V1AppCompareResponse) o;
    return Objects.equals(this.baseAppYAML, v1AppCompareResponse.baseAppYAML) &&
        Objects.equals(this.diffReport, v1AppCompareResponse.diffReport) &&
        Objects.equals(this.isDiff, v1AppCompareResponse.isDiff) &&
        Objects.equals(this.targetAppYAML, v1AppCompareResponse.targetAppYAML);
  }

  @Override
  public int hashCode() {
    return Objects.hash(baseAppYAML, diffReport, isDiff, targetAppYAML);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1AppCompareResponse {\n");
    
    sb.append("    baseAppYAML: ").append(toIndentedString(baseAppYAML)).append("\n");
    sb.append("    diffReport: ").append(toIndentedString(diffReport)).append("\n");
    sb.append("    isDiff: ").append(toIndentedString(isDiff)).append("\n");
    sb.append("    targetAppYAML: ").append(toIndentedString(targetAppYAML)).append("\n");
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

