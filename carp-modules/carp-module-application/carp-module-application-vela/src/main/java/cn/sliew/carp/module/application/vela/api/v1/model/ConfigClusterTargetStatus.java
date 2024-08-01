package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ConfigClusterTargetStatus
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class ConfigClusterTargetStatus   {
  @JsonProperty("application")
  private ConfigNamespacedName application = null;

  @JsonProperty("clusterName")
  private String clusterName = null;

  @JsonProperty("message")
  private String message = null;

  @JsonProperty("namespace")
  private String namespace = null;

  @JsonProperty("status")
  private String status = null;

  public ConfigClusterTargetStatus application(ConfigNamespacedName application) {
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

  public ConfigNamespacedName getApplication() {
    return application;
  }

  public void setApplication(ConfigNamespacedName application) {
    this.application = application;
  }

  public ConfigClusterTargetStatus clusterName(String clusterName) {
    this.clusterName = clusterName;
    return this;
  }

  /**
   * Get clusterName
   * @return clusterName
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getClusterName() {
    return clusterName;
  }

  public void setClusterName(String clusterName) {
    this.clusterName = clusterName;
  }

  public ConfigClusterTargetStatus message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   * @return message
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ConfigClusterTargetStatus namespace(String namespace) {
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

  public ConfigClusterTargetStatus status(String status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConfigClusterTargetStatus configClusterTargetStatus = (ConfigClusterTargetStatus) o;
    return Objects.equals(this.application, configClusterTargetStatus.application) &&
        Objects.equals(this.clusterName, configClusterTargetStatus.clusterName) &&
        Objects.equals(this.message, configClusterTargetStatus.message) &&
        Objects.equals(this.namespace, configClusterTargetStatus.namespace) &&
        Objects.equals(this.status, configClusterTargetStatus.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(application, clusterName, message, namespace, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConfigClusterTargetStatus {\n");
    
    sb.append("    application: ").append(toIndentedString(application)).append("\n");
    sb.append("    clusterName: ").append(toIndentedString(clusterName)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    namespace: ").append(toIndentedString(namespace)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

