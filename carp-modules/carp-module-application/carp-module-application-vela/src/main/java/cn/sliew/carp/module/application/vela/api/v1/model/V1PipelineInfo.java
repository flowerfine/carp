package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1PipelineInfo
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1PipelineInfo   {
  @JsonProperty("lastRun")
  private V1PipelineRun lastRun = null;

  @JsonProperty("runStat")
  private V1RunStat runStat = null;

  public V1PipelineInfo lastRun(V1PipelineRun lastRun) {
    this.lastRun = lastRun;
    return this;
  }

  /**
   * Get lastRun
   * @return lastRun
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public V1PipelineRun getLastRun() {
    return lastRun;
  }

  public void setLastRun(V1PipelineRun lastRun) {
    this.lastRun = lastRun;
  }

  public V1PipelineInfo runStat(V1RunStat runStat) {
    this.runStat = runStat;
    return this;
  }

  /**
   * Get runStat
   * @return runStat
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public V1RunStat getRunStat() {
    return runStat;
  }

  public void setRunStat(V1RunStat runStat) {
    this.runStat = runStat;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1PipelineInfo v1PipelineInfo = (V1PipelineInfo) o;
    return Objects.equals(this.lastRun, v1PipelineInfo.lastRun) &&
        Objects.equals(this.runStat, v1PipelineInfo.runStat);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lastRun, runStat);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1PipelineInfo {\n");
    
    sb.append("    lastRun: ").append(toIndentedString(lastRun)).append("\n");
    sb.append("    runStat: ").append(toIndentedString(runStat)).append("\n");
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

