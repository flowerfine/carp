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
 * V1CreateConfigDistributionRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1CreateConfigDistributionRequest   {
  @JsonProperty("configs")
  @Valid
  private List<V1NamespacedName> configs = new ArrayList<V1NamespacedName>();

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("targets")
  @Valid
  private List<V1ClusterTarget> targets = new ArrayList<V1ClusterTarget>();

  public V1CreateConfigDistributionRequest configs(List<V1NamespacedName> configs) {
    this.configs = configs;
    return this;
  }

  public V1CreateConfigDistributionRequest addConfigsItem(V1NamespacedName configsItem) {
    this.configs.add(configsItem);
    return this;
  }

  /**
   * Get configs
   * @return configs
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1NamespacedName> getConfigs() {
    return configs;
  }

  public void setConfigs(List<V1NamespacedName> configs) {
    this.configs = configs;
  }

  public V1CreateConfigDistributionRequest name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public V1CreateConfigDistributionRequest targets(List<V1ClusterTarget> targets) {
    this.targets = targets;
    return this;
  }

  public V1CreateConfigDistributionRequest addTargetsItem(V1ClusterTarget targetsItem) {
    this.targets.add(targetsItem);
    return this;
  }

  /**
   * Get targets
   * @return targets
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1ClusterTarget> getTargets() {
    return targets;
  }

  public void setTargets(List<V1ClusterTarget> targets) {
    this.targets = targets;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1CreateConfigDistributionRequest v1CreateConfigDistributionRequest = (V1CreateConfigDistributionRequest) o;
    return Objects.equals(this.configs, v1CreateConfigDistributionRequest.configs) &&
        Objects.equals(this.name, v1CreateConfigDistributionRequest.name) &&
        Objects.equals(this.targets, v1CreateConfigDistributionRequest.targets);
  }

  @Override
  public int hashCode() {
    return Objects.hash(configs, name, targets);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1CreateConfigDistributionRequest {\n");
    
    sb.append("    configs: ").append(toIndentedString(configs)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    targets: ").append(toIndentedString(targets)).append("\n");
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

