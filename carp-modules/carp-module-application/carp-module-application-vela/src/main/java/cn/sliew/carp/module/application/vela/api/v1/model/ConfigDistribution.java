package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ConfigDistribution
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class ConfigDistribution   {
  @JsonProperty("application")
  private TypesNamespacedName application = null;

  @JsonProperty("configs")
  @Valid
  private List<ConfigNamespacedName> configs = new ArrayList<ConfigNamespacedName>();

  @JsonProperty("createdTime")
  private OffsetDateTime createdTime = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("namespace")
  private String namespace = null;

  @JsonProperty("status")
  private CommonAppStatus status = null;

  @JsonProperty("targets")
  @Valid
  private List<ConfigClusterTarget> targets = new ArrayList<ConfigClusterTarget>();

  public ConfigDistribution application(TypesNamespacedName application) {
    this.application = application;
    return this;
  }

  /**
   * Get application
   * @return application
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public TypesNamespacedName getApplication() {
    return application;
  }

  public void setApplication(TypesNamespacedName application) {
    this.application = application;
  }

  public ConfigDistribution configs(List<ConfigNamespacedName> configs) {
    this.configs = configs;
    return this;
  }

  public ConfigDistribution addConfigsItem(ConfigNamespacedName configsItem) {
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

  public List<ConfigNamespacedName> getConfigs() {
    return configs;
  }

  public void setConfigs(List<ConfigNamespacedName> configs) {
    this.configs = configs;
  }

  public ConfigDistribution createdTime(OffsetDateTime createdTime) {
    this.createdTime = createdTime;
    return this;
  }

  /**
   * Get createdTime
   * @return createdTime
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public OffsetDateTime getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(OffsetDateTime createdTime) {
    this.createdTime = createdTime;
  }

  public ConfigDistribution name(String name) {
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

  public ConfigDistribution namespace(String namespace) {
    this.namespace = namespace;
    return this;
  }

  /**
   * Get namespace
   * @return namespace
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getNamespace() {
    return namespace;
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public ConfigDistribution status(CommonAppStatus status) {
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

  public ConfigDistribution targets(List<ConfigClusterTarget> targets) {
    this.targets = targets;
    return this;
  }

  public ConfigDistribution addTargetsItem(ConfigClusterTarget targetsItem) {
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

  public List<ConfigClusterTarget> getTargets() {
    return targets;
  }

  public void setTargets(List<ConfigClusterTarget> targets) {
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
    ConfigDistribution configDistribution = (ConfigDistribution) o;
    return Objects.equals(this.application, configDistribution.application) &&
        Objects.equals(this.configs, configDistribution.configs) &&
        Objects.equals(this.createdTime, configDistribution.createdTime) &&
        Objects.equals(this.name, configDistribution.name) &&
        Objects.equals(this.namespace, configDistribution.namespace) &&
        Objects.equals(this.status, configDistribution.status) &&
        Objects.equals(this.targets, configDistribution.targets);
  }

  @Override
  public int hashCode() {
    return Objects.hash(application, configs, createdTime, name, namespace, status, targets);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConfigDistribution {\n");
    
    sb.append("    application: ").append(toIndentedString(application)).append("\n");
    sb.append("    configs: ").append(toIndentedString(configs)).append("\n");
    sb.append("    createdTime: ").append(toIndentedString(createdTime)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    namespace: ").append(toIndentedString(namespace)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

