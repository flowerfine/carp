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
 * CommonApplicationComponentStatus
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class CommonApplicationComponentStatus   {
  @JsonProperty("cluster")
  private String cluster = null;

  @JsonProperty("env")
  private String env = null;

  @JsonProperty("healthy")
  private Boolean healthy = null;

  @JsonProperty("message")
  private String message = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("namespace")
  private String namespace = null;

  @JsonProperty("scopes")
  @Valid
  private List<V1ObjectReference> scopes = null;

  @JsonProperty("traits")
  @Valid
  private List<CommonApplicationTraitStatus> traits = null;

  @JsonProperty("workloadDefinition")
  private CommonWorkloadGVK workloadDefinition = null;

  public CommonApplicationComponentStatus cluster(String cluster) {
    this.cluster = cluster;
    return this;
  }

  /**
   * Get cluster
   * @return cluster
  **/
  @ApiModelProperty(value = "")


  public String getCluster() {
    return cluster;
  }

  public void setCluster(String cluster) {
    this.cluster = cluster;
  }

  public CommonApplicationComponentStatus env(String env) {
    this.env = env;
    return this;
  }

  /**
   * Get env
   * @return env
  **/
  @ApiModelProperty(value = "")


  public String getEnv() {
    return env;
  }

  public void setEnv(String env) {
    this.env = env;
  }

  public CommonApplicationComponentStatus healthy(Boolean healthy) {
    this.healthy = healthy;
    return this;
  }

  /**
   * Get healthy
   * @return healthy
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Boolean isHealthy() {
    return healthy;
  }

  public void setHealthy(Boolean healthy) {
    this.healthy = healthy;
  }

  public CommonApplicationComponentStatus message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   * @return message
  **/
  @ApiModelProperty(value = "")


  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public CommonApplicationComponentStatus name(String name) {
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

  public CommonApplicationComponentStatus namespace(String namespace) {
    this.namespace = namespace;
    return this;
  }

  /**
   * Get namespace
   * @return namespace
  **/
  @ApiModelProperty(value = "")


  public String getNamespace() {
    return namespace;
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public CommonApplicationComponentStatus scopes(List<V1ObjectReference> scopes) {
    this.scopes = scopes;
    return this;
  }

  public CommonApplicationComponentStatus addScopesItem(V1ObjectReference scopesItem) {
    if (this.scopes == null) {
      this.scopes = new ArrayList<V1ObjectReference>();
    }
    this.scopes.add(scopesItem);
    return this;
  }

  /**
   * Get scopes
   * @return scopes
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<V1ObjectReference> getScopes() {
    return scopes;
  }

  public void setScopes(List<V1ObjectReference> scopes) {
    this.scopes = scopes;
  }

  public CommonApplicationComponentStatus traits(List<CommonApplicationTraitStatus> traits) {
    this.traits = traits;
    return this;
  }

  public CommonApplicationComponentStatus addTraitsItem(CommonApplicationTraitStatus traitsItem) {
    if (this.traits == null) {
      this.traits = new ArrayList<CommonApplicationTraitStatus>();
    }
    this.traits.add(traitsItem);
    return this;
  }

  /**
   * Get traits
   * @return traits
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<CommonApplicationTraitStatus> getTraits() {
    return traits;
  }

  public void setTraits(List<CommonApplicationTraitStatus> traits) {
    this.traits = traits;
  }

  public CommonApplicationComponentStatus workloadDefinition(CommonWorkloadGVK workloadDefinition) {
    this.workloadDefinition = workloadDefinition;
    return this;
  }

  /**
   * Get workloadDefinition
   * @return workloadDefinition
  **/
  @ApiModelProperty(value = "")

  @Valid

  public CommonWorkloadGVK getWorkloadDefinition() {
    return workloadDefinition;
  }

  public void setWorkloadDefinition(CommonWorkloadGVK workloadDefinition) {
    this.workloadDefinition = workloadDefinition;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommonApplicationComponentStatus commonApplicationComponentStatus = (CommonApplicationComponentStatus) o;
    return Objects.equals(this.cluster, commonApplicationComponentStatus.cluster) &&
        Objects.equals(this.env, commonApplicationComponentStatus.env) &&
        Objects.equals(this.healthy, commonApplicationComponentStatus.healthy) &&
        Objects.equals(this.message, commonApplicationComponentStatus.message) &&
        Objects.equals(this.name, commonApplicationComponentStatus.name) &&
        Objects.equals(this.namespace, commonApplicationComponentStatus.namespace) &&
        Objects.equals(this.scopes, commonApplicationComponentStatus.scopes) &&
        Objects.equals(this.traits, commonApplicationComponentStatus.traits) &&
        Objects.equals(this.workloadDefinition, commonApplicationComponentStatus.workloadDefinition);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cluster, env, healthy, message, name, namespace, scopes, traits, workloadDefinition);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommonApplicationComponentStatus {\n");
    
    sb.append("    cluster: ").append(toIndentedString(cluster)).append("\n");
    sb.append("    env: ").append(toIndentedString(env)).append("\n");
    sb.append("    healthy: ").append(toIndentedString(healthy)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    namespace: ").append(toIndentedString(namespace)).append("\n");
    sb.append("    scopes: ").append(toIndentedString(scopes)).append("\n");
    sb.append("    traits: ").append(toIndentedString(traits)).append("\n");
    sb.append("    workloadDefinition: ").append(toIndentedString(workloadDefinition)).append("\n");
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

