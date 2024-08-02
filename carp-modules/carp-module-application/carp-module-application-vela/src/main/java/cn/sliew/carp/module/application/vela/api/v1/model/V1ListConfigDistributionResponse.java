package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;

import cn.sliew.carp.module.application.vela.api.v1.model.common.ConfigDistribution;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1ListConfigDistributionResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ListConfigDistributionResponse   {
  @JsonProperty("distributions")
  @Valid
  private List<ConfigDistribution> distributions = new ArrayList<ConfigDistribution>();

  public V1ListConfigDistributionResponse distributions(List<ConfigDistribution> distributions) {
    this.distributions = distributions;
    return this;
  }

  public V1ListConfigDistributionResponse addDistributionsItem(ConfigDistribution distributionsItem) {
    this.distributions.add(distributionsItem);
    return this;
  }

  /**
   * Get distributions
   * @return distributions
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<ConfigDistribution> getDistributions() {
    return distributions;
  }

  public void setDistributions(List<ConfigDistribution> distributions) {
    this.distributions = distributions;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1ListConfigDistributionResponse v1ListConfigDistributionResponse = (V1ListConfigDistributionResponse) o;
    return Objects.equals(this.distributions, v1ListConfigDistributionResponse.distributions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(distributions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ListConfigDistributionResponse {\n");
    
    sb.append("    distributions: ").append(toIndentedString(distributions)).append("\n");
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

