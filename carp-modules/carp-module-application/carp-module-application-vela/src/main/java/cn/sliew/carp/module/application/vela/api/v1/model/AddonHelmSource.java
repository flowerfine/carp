package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AddonHelmSource
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class AddonHelmSource   {
  @JsonProperty("insecureSkipTLS")
  private Boolean insecureSkipTLS = null;

  @JsonProperty("password")
  private String password = null;

  @JsonProperty("url")
  private String url = null;

  @JsonProperty("username")
  private String username = null;

  public AddonHelmSource insecureSkipTLS(Boolean insecureSkipTLS) {
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

  public AddonHelmSource password(String password) {
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

  public AddonHelmSource url(String url) {
    this.url = url;
    return this;
  }

  /**
   * Get url
   * @return url
  **/
  @ApiModelProperty(value = "")


  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public AddonHelmSource username(String username) {
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
    AddonHelmSource addonHelmSource = (AddonHelmSource) o;
    return Objects.equals(this.insecureSkipTLS, addonHelmSource.insecureSkipTLS) &&
        Objects.equals(this.password, addonHelmSource.password) &&
        Objects.equals(this.url, addonHelmSource.url) &&
        Objects.equals(this.username, addonHelmSource.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(insecureSkipTLS, password, url, username);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddonHelmSource {\n");
    
    sb.append("    insecureSkipTLS: ").append(toIndentedString(insecureSkipTLS)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
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

