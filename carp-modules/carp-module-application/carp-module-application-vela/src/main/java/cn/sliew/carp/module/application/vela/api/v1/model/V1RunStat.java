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
 * V1RunStat
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1RunStat   {
  @JsonProperty("activeNum")
  private Integer activeNum = null;

  @JsonProperty("total")
  private V1RunStatInfo total = null;

  @JsonProperty("week")
  @Valid
  private List<V1RunStatInfo> week = new ArrayList<V1RunStatInfo>();

  public V1RunStat activeNum(Integer activeNum) {
    this.activeNum = activeNum;
    return this;
  }

  /**
   * Get activeNum
   * @return activeNum
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getActiveNum() {
    return activeNum;
  }

  public void setActiveNum(Integer activeNum) {
    this.activeNum = activeNum;
  }

  public V1RunStat total(V1RunStatInfo total) {
    this.total = total;
    return this;
  }

  /**
   * Get total
   * @return total
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public V1RunStatInfo getTotal() {
    return total;
  }

  public void setTotal(V1RunStatInfo total) {
    this.total = total;
  }

  public V1RunStat week(List<V1RunStatInfo> week) {
    this.week = week;
    return this;
  }

  public V1RunStat addWeekItem(V1RunStatInfo weekItem) {
    this.week.add(weekItem);
    return this;
  }

  /**
   * Get week
   * @return week
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1RunStatInfo> getWeek() {
    return week;
  }

  public void setWeek(List<V1RunStatInfo> week) {
    this.week = week;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1RunStat v1RunStat = (V1RunStat) o;
    return Objects.equals(this.activeNum, v1RunStat.activeNum) &&
        Objects.equals(this.total, v1RunStat.total) &&
        Objects.equals(this.week, v1RunStat.week);
  }

  @Override
  public int hashCode() {
    return Objects.hash(activeNum, total, week);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1RunStat {\n");
    
    sb.append("    activeNum: ").append(toIndentedString(activeNum)).append("\n");
    sb.append("    total: ").append(toIndentedString(total)).append("\n");
    sb.append("    week: ").append(toIndentedString(week)).append("\n");
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

