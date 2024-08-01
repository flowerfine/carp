package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1ApplicationStatisticsResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ApplicationStatisticsResponse   {
  @JsonProperty("envCount")
  private Long envCount = null;

  @JsonProperty("revisionCount")
  private Long revisionCount = null;

  @JsonProperty("targetCount")
  private Long targetCount = null;

  @JsonProperty("workflowCount")
  private Long workflowCount = null;

  public V1ApplicationStatisticsResponse envCount(Long envCount) {
    this.envCount = envCount;
    return this;
  }

  /**
   * Get envCount
   * @return envCount
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getEnvCount() {
    return envCount;
  }

  public void setEnvCount(Long envCount) {
    this.envCount = envCount;
  }

  public V1ApplicationStatisticsResponse revisionCount(Long revisionCount) {
    this.revisionCount = revisionCount;
    return this;
  }

  /**
   * Get revisionCount
   * @return revisionCount
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getRevisionCount() {
    return revisionCount;
  }

  public void setRevisionCount(Long revisionCount) {
    this.revisionCount = revisionCount;
  }

  public V1ApplicationStatisticsResponse targetCount(Long targetCount) {
    this.targetCount = targetCount;
    return this;
  }

  /**
   * Get targetCount
   * @return targetCount
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getTargetCount() {
    return targetCount;
  }

  public void setTargetCount(Long targetCount) {
    this.targetCount = targetCount;
  }

  public V1ApplicationStatisticsResponse workflowCount(Long workflowCount) {
    this.workflowCount = workflowCount;
    return this;
  }

  /**
   * Get workflowCount
   * @return workflowCount
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getWorkflowCount() {
    return workflowCount;
  }

  public void setWorkflowCount(Long workflowCount) {
    this.workflowCount = workflowCount;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1ApplicationStatisticsResponse v1ApplicationStatisticsResponse = (V1ApplicationStatisticsResponse) o;
    return Objects.equals(this.envCount, v1ApplicationStatisticsResponse.envCount) &&
        Objects.equals(this.revisionCount, v1ApplicationStatisticsResponse.revisionCount) &&
        Objects.equals(this.targetCount, v1ApplicationStatisticsResponse.targetCount) &&
        Objects.equals(this.workflowCount, v1ApplicationStatisticsResponse.workflowCount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(envCount, revisionCount, targetCount, workflowCount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ApplicationStatisticsResponse {\n");
    
    sb.append("    envCount: ").append(toIndentedString(envCount)).append("\n");
    sb.append("    revisionCount: ").append(toIndentedString(revisionCount)).append("\n");
    sb.append("    targetCount: ").append(toIndentedString(targetCount)).append("\n");
    sb.append("    workflowCount: ").append(toIndentedString(workflowCount)).append("\n");
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

