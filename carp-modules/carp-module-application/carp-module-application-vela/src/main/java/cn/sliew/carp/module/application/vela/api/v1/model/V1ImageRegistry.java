package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1ImageRegistry
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ImageRegistry   {
  @JsonProperty("domain")
  private String domain = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("secretName")
  private String secretName = null;

  public V1ImageRegistry domain(String domain) {
    this.domain = domain;
    return this;
  }

  /**
   * Get domain
   * @return domain
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public V1ImageRegistry name(String name) {
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

  public V1ImageRegistry secretName(String secretName) {
    this.secretName = secretName;
    return this;
  }

  /**
   * Get secretName
   * @return secretName
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getSecretName() {
    return secretName;
  }

  public void setSecretName(String secretName) {
    this.secretName = secretName;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1ImageRegistry v1ImageRegistry = (V1ImageRegistry) o;
    return Objects.equals(this.domain, v1ImageRegistry.domain) &&
        Objects.equals(this.name, v1ImageRegistry.name) &&
        Objects.equals(this.secretName, v1ImageRegistry.secretName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(domain, name, secretName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ImageRegistry {\n");
    
    sb.append("    domain: ").append(toIndentedString(domain)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    secretName: ").append(toIndentedString(secretName)).append("\n");
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

