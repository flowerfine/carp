package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CommonTerraform
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class CommonTerraform   {
  @JsonProperty("configuration")
  private String _configuration = null;

  @JsonProperty("customRegion")
  private String customRegion = null;

  @JsonProperty("deleteResource")
  private Boolean deleteResource = null;

  @JsonProperty("gitCredentialsSecretReference")
  private V1SecretReference gitCredentialsSecretReference = null;

  @JsonProperty("path")
  private String path = null;

  @JsonProperty("providerRef")
  private TypesReference providerRef = null;

  @JsonProperty("type")
  private String type = null;

  @JsonProperty("writeConnectionSecretToRef")
  private TypesSecretReference writeConnectionSecretToRef = null;

  public CommonTerraform _configuration(String _configuration) {
    this._configuration = _configuration;
    return this;
  }

  /**
   * Get _configuration
   * @return _configuration
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getConfiguration() {
    return _configuration;
  }

  public void setConfiguration(String _configuration) {
    this._configuration = _configuration;
  }

  public CommonTerraform customRegion(String customRegion) {
    this.customRegion = customRegion;
    return this;
  }

  /**
   * Get customRegion
   * @return customRegion
  **/
  @ApiModelProperty(value = "")


  public String getCustomRegion() {
    return customRegion;
  }

  public void setCustomRegion(String customRegion) {
    this.customRegion = customRegion;
  }

  public CommonTerraform deleteResource(Boolean deleteResource) {
    this.deleteResource = deleteResource;
    return this;
  }

  /**
   * Get deleteResource
   * @return deleteResource
  **/
  @ApiModelProperty(value = "")


  public Boolean isDeleteResource() {
    return deleteResource;
  }

  public void setDeleteResource(Boolean deleteResource) {
    this.deleteResource = deleteResource;
  }

  public CommonTerraform gitCredentialsSecretReference(V1SecretReference gitCredentialsSecretReference) {
    this.gitCredentialsSecretReference = gitCredentialsSecretReference;
    return this;
  }

  /**
   * Get gitCredentialsSecretReference
   * @return gitCredentialsSecretReference
  **/
  @ApiModelProperty(value = "")

  @Valid

  public V1SecretReference getGitCredentialsSecretReference() {
    return gitCredentialsSecretReference;
  }

  public void setGitCredentialsSecretReference(V1SecretReference gitCredentialsSecretReference) {
    this.gitCredentialsSecretReference = gitCredentialsSecretReference;
  }

  public CommonTerraform path(String path) {
    this.path = path;
    return this;
  }

  /**
   * Get path
   * @return path
  **/
  @ApiModelProperty(value = "")


  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public CommonTerraform providerRef(TypesReference providerRef) {
    this.providerRef = providerRef;
    return this;
  }

  /**
   * Get providerRef
   * @return providerRef
  **/
  @ApiModelProperty(value = "")

  @Valid

  public TypesReference getProviderRef() {
    return providerRef;
  }

  public void setProviderRef(TypesReference providerRef) {
    this.providerRef = providerRef;
  }

  public CommonTerraform type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
  **/
  @ApiModelProperty(value = "")


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public CommonTerraform writeConnectionSecretToRef(TypesSecretReference writeConnectionSecretToRef) {
    this.writeConnectionSecretToRef = writeConnectionSecretToRef;
    return this;
  }

  /**
   * Get writeConnectionSecretToRef
   * @return writeConnectionSecretToRef
  **/
  @ApiModelProperty(value = "")

  @Valid

  public TypesSecretReference getWriteConnectionSecretToRef() {
    return writeConnectionSecretToRef;
  }

  public void setWriteConnectionSecretToRef(TypesSecretReference writeConnectionSecretToRef) {
    this.writeConnectionSecretToRef = writeConnectionSecretToRef;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommonTerraform commonTerraform = (CommonTerraform) o;
    return Objects.equals(this._configuration, commonTerraform._configuration) &&
        Objects.equals(this.customRegion, commonTerraform.customRegion) &&
        Objects.equals(this.deleteResource, commonTerraform.deleteResource) &&
        Objects.equals(this.gitCredentialsSecretReference, commonTerraform.gitCredentialsSecretReference) &&
        Objects.equals(this.path, commonTerraform.path) &&
        Objects.equals(this.providerRef, commonTerraform.providerRef) &&
        Objects.equals(this.type, commonTerraform.type) &&
        Objects.equals(this.writeConnectionSecretToRef, commonTerraform.writeConnectionSecretToRef);
  }

  @Override
  public int hashCode() {
    return Objects.hash(_configuration, customRegion, deleteResource, gitCredentialsSecretReference, path, providerRef, type, writeConnectionSecretToRef);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommonTerraform {\n");
    
    sb.append("    _configuration: ").append(toIndentedString(_configuration)).append("\n");
    sb.append("    customRegion: ").append(toIndentedString(customRegion)).append("\n");
    sb.append("    deleteResource: ").append(toIndentedString(deleteResource)).append("\n");
    sb.append("    gitCredentialsSecretReference: ").append(toIndentedString(gitCredentialsSecretReference)).append("\n");
    sb.append("    path: ").append(toIndentedString(path)).append("\n");
    sb.append("    providerRef: ").append(toIndentedString(providerRef)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    writeConnectionSecretToRef: ").append(toIndentedString(writeConnectionSecretToRef)).append("\n");
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

