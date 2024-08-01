package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CommonHTTPOption
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class CommonHTTPOption   {
  @JsonProperty("caFile")
  private String caFile = null;

  @JsonProperty("certFile")
  private String certFile = null;

  @JsonProperty("insecureSkipTLS")
  private Boolean insecureSkipTLS = null;

  @JsonProperty("keyFile")
  private String keyFile = null;

  @JsonProperty("password")
  private String password = null;

  @JsonProperty("username")
  private String username = null;

  public CommonHTTPOption caFile(String caFile) {
    this.caFile = caFile;
    return this;
  }

  /**
   * Get caFile
   * @return caFile
  **/
  @ApiModelProperty(value = "")


  public String getCaFile() {
    return caFile;
  }

  public void setCaFile(String caFile) {
    this.caFile = caFile;
  }

  public CommonHTTPOption certFile(String certFile) {
    this.certFile = certFile;
    return this;
  }

  /**
   * Get certFile
   * @return certFile
  **/
  @ApiModelProperty(value = "")


  public String getCertFile() {
    return certFile;
  }

  public void setCertFile(String certFile) {
    this.certFile = certFile;
  }

  public CommonHTTPOption insecureSkipTLS(Boolean insecureSkipTLS) {
    this.insecureSkipTLS = insecureSkipTLS;
    return this;
  }

  /**
   * Get insecureSkipTLS
   * @return insecureSkipTLS
  **/
  @ApiModelProperty(value = "")


  public Boolean isInsecureSkipTLS() {
    return insecureSkipTLS;
  }

  public void setInsecureSkipTLS(Boolean insecureSkipTLS) {
    this.insecureSkipTLS = insecureSkipTLS;
  }

  public CommonHTTPOption keyFile(String keyFile) {
    this.keyFile = keyFile;
    return this;
  }

  /**
   * Get keyFile
   * @return keyFile
  **/
  @ApiModelProperty(value = "")


  public String getKeyFile() {
    return keyFile;
  }

  public void setKeyFile(String keyFile) {
    this.keyFile = keyFile;
  }

  public CommonHTTPOption password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Get password
   * @return password
  **/
  @ApiModelProperty(value = "")


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public CommonHTTPOption username(String username) {
    this.username = username;
    return this;
  }

  /**
   * Get username
   * @return username
  **/
  @ApiModelProperty(value = "")


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommonHTTPOption commonHTTPOption = (CommonHTTPOption) o;
    return Objects.equals(this.caFile, commonHTTPOption.caFile) &&
        Objects.equals(this.certFile, commonHTTPOption.certFile) &&
        Objects.equals(this.insecureSkipTLS, commonHTTPOption.insecureSkipTLS) &&
        Objects.equals(this.keyFile, commonHTTPOption.keyFile) &&
        Objects.equals(this.password, commonHTTPOption.password) &&
        Objects.equals(this.username, commonHTTPOption.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(caFile, certFile, insecureSkipTLS, keyFile, password, username);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommonHTTPOption {\n");
    
    sb.append("    caFile: ").append(toIndentedString(caFile)).append("\n");
    sb.append("    certFile: ").append(toIndentedString(certFile)).append("\n");
    sb.append("    insecureSkipTLS: ").append(toIndentedString(insecureSkipTLS)).append("\n");
    sb.append("    keyFile: ").append(toIndentedString(keyFile)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
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

