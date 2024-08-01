package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * SecretReference represents a Secret Reference. It has enough information to retrieve secret in any namespace
 */
@ApiModel(description = "SecretReference represents a Secret Reference. It has enough information to retrieve secret in any namespace")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1SecretReference   {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("namespace")
  private String namespace = null;

  public V1SecretReference name(String name) {
    this.name = name;
    return this;
  }

  /**
   * name is unique within a namespace to reference a secret resource.
   * @return name
  **/
  @ApiModelProperty(value = "name is unique within a namespace to reference a secret resource.")


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public V1SecretReference namespace(String namespace) {
    this.namespace = namespace;
    return this;
  }

  /**
   * namespace defines the space within which the secret name must be unique.
   * @return namespace
  **/
  @ApiModelProperty(value = "namespace defines the space within which the secret name must be unique.")


  public String getNamespace() {
    return namespace;
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1SecretReference v1SecretReference = (V1SecretReference) o;
    return Objects.equals(this.name, v1SecretReference.name) &&
        Objects.equals(this.namespace, v1SecretReference.namespace);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, namespace);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1SecretReference {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    namespace: ").append(toIndentedString(namespace)).append("\n");
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

