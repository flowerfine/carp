package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AddonGitlabAddonSource
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class AddonGitlabAddonSource   {
  @JsonProperty("path")
  private String path = null;

  @JsonProperty("repo")
  private String repo = null;

  @JsonProperty("token")
  private String token = null;

  @JsonProperty("url")
  private String url = null;

  public AddonGitlabAddonSource path(String path) {
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

  public AddonGitlabAddonSource repo(String repo) {
    this.repo = repo;
    return this;
  }

  /**
   * Get repo
   * @return repo
  **/
  @ApiModelProperty(value = "")


  public String getRepo() {
    return repo;
  }

  public void setRepo(String repo) {
    this.repo = repo;
  }

  public AddonGitlabAddonSource token(String token) {
    this.token = token;
    return this;
  }

  /**
   * Get token
   * @return token
  **/
  @ApiModelProperty(value = "")


  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public AddonGitlabAddonSource url(String url) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddonGitlabAddonSource addonGitlabAddonSource = (AddonGitlabAddonSource) o;
    return Objects.equals(this.path, addonGitlabAddonSource.path) &&
        Objects.equals(this.repo, addonGitlabAddonSource.repo) &&
        Objects.equals(this.token, addonGitlabAddonSource.token) &&
        Objects.equals(this.url, addonGitlabAddonSource.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(path, repo, token, url);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddonGitlabAddonSource {\n");
    
    sb.append("    path: ").append(toIndentedString(path)).append("\n");
    sb.append("    repo: ").append(toIndentedString(repo)).append("\n");
    sb.append("    token: ").append(toIndentedString(token)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
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

