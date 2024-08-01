package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1DexConfigResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1DexConfigResponse   {
  @JsonProperty("clientID")
  private String clientID = null;

  @JsonProperty("clientSecret")
  private String clientSecret = null;

  @JsonProperty("issuer")
  private String issuer = null;

  @JsonProperty("redirectURL")
  private String redirectURL = null;

  public V1DexConfigResponse clientID(String clientID) {
    this.clientID = clientID;
    return this;
  }

  /**
   * Get clientID
   * @return clientID
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getClientID() {
    return clientID;
  }

  public void setClientID(String clientID) {
    this.clientID = clientID;
  }

  public V1DexConfigResponse clientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
    return this;
  }

  /**
   * Get clientSecret
   * @return clientSecret
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getClientSecret() {
    return clientSecret;
  }

  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
  }

  public V1DexConfigResponse issuer(String issuer) {
    this.issuer = issuer;
    return this;
  }

  /**
   * Get issuer
   * @return issuer
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getIssuer() {
    return issuer;
  }

  public void setIssuer(String issuer) {
    this.issuer = issuer;
  }

  public V1DexConfigResponse redirectURL(String redirectURL) {
    this.redirectURL = redirectURL;
    return this;
  }

  /**
   * Get redirectURL
   * @return redirectURL
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getRedirectURL() {
    return redirectURL;
  }

  public void setRedirectURL(String redirectURL) {
    this.redirectURL = redirectURL;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1DexConfigResponse v1DexConfigResponse = (V1DexConfigResponse) o;
    return Objects.equals(this.clientID, v1DexConfigResponse.clientID) &&
        Objects.equals(this.clientSecret, v1DexConfigResponse.clientSecret) &&
        Objects.equals(this.issuer, v1DexConfigResponse.issuer) &&
        Objects.equals(this.redirectURL, v1DexConfigResponse.redirectURL);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clientID, clientSecret, issuer, redirectURL);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1DexConfigResponse {\n");
    
    sb.append("    clientID: ").append(toIndentedString(clientID)).append("\n");
    sb.append("    clientSecret: ").append(toIndentedString(clientSecret)).append("\n");
    sb.append("    issuer: ").append(toIndentedString(issuer)).append("\n");
    sb.append("    redirectURL: ").append(toIndentedString(redirectURL)).append("\n");
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

