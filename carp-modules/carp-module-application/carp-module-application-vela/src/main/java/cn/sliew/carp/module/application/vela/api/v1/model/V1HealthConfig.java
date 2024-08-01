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
 * V1HealthConfig
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1HealthConfig   {
  @JsonProperty("Interval")
  private Long interval = null;

  @JsonProperty("Retries")
  private Integer retries = null;

  @JsonProperty("StartPeriod")
  private Long startPeriod = null;

  @JsonProperty("Test")
  @Valid
  private List<String> test = null;

  @JsonProperty("Timeout")
  private Long timeout = null;

  public V1HealthConfig interval(Long interval) {
    this.interval = interval;
    return this;
  }

  /**
   * Get interval
   * @return interval
  **/
  @ApiModelProperty(value = "")


  public Long getInterval() {
    return interval;
  }

  public void setInterval(Long interval) {
    this.interval = interval;
  }

  public V1HealthConfig retries(Integer retries) {
    this.retries = retries;
    return this;
  }

  /**
   * Get retries
   * @return retries
  **/
  @ApiModelProperty(value = "")


  public Integer getRetries() {
    return retries;
  }

  public void setRetries(Integer retries) {
    this.retries = retries;
  }

  public V1HealthConfig startPeriod(Long startPeriod) {
    this.startPeriod = startPeriod;
    return this;
  }

  /**
   * Get startPeriod
   * @return startPeriod
  **/
  @ApiModelProperty(value = "")


  public Long getStartPeriod() {
    return startPeriod;
  }

  public void setStartPeriod(Long startPeriod) {
    this.startPeriod = startPeriod;
  }

  public V1HealthConfig test(List<String> test) {
    this.test = test;
    return this;
  }

  public V1HealthConfig addTestItem(String testItem) {
    if (this.test == null) {
      this.test = new ArrayList<String>();
    }
    this.test.add(testItem);
    return this;
  }

  /**
   * Get test
   * @return test
  **/
  @ApiModelProperty(value = "")


  public List<String> getTest() {
    return test;
  }

  public void setTest(List<String> test) {
    this.test = test;
  }

  public V1HealthConfig timeout(Long timeout) {
    this.timeout = timeout;
    return this;
  }

  /**
   * Get timeout
   * @return timeout
  **/
  @ApiModelProperty(value = "")


  public Long getTimeout() {
    return timeout;
  }

  public void setTimeout(Long timeout) {
    this.timeout = timeout;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1HealthConfig v1HealthConfig = (V1HealthConfig) o;
    return Objects.equals(this.interval, v1HealthConfig.interval) &&
        Objects.equals(this.retries, v1HealthConfig.retries) &&
        Objects.equals(this.startPeriod, v1HealthConfig.startPeriod) &&
        Objects.equals(this.test, v1HealthConfig.test) &&
        Objects.equals(this.timeout, v1HealthConfig.timeout);
  }

  @Override
  public int hashCode() {
    return Objects.hash(interval, retries, startPeriod, test, timeout);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1HealthConfig {\n");
    
    sb.append("    interval: ").append(toIndentedString(interval)).append("\n");
    sb.append("    retries: ").append(toIndentedString(retries)).append("\n");
    sb.append("    startPeriod: ").append(toIndentedString(startPeriod)).append("\n");
    sb.append("    test: ").append(toIndentedString(test)).append("\n");
    sb.append("    timeout: ").append(toIndentedString(timeout)).append("\n");
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

