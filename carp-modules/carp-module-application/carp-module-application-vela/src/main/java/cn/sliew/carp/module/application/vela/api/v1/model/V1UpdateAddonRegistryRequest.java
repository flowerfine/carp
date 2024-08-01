package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1UpdateAddonRegistryRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1UpdateAddonRegistryRequest   {
  @JsonProperty("git")
  private AddonGitAddonSource git = null;

  @JsonProperty("gitee")
  private AddonGiteeAddonSource gitee = null;

  @JsonProperty("gitlab")
  private AddonGitlabAddonSource gitlab = null;

  @JsonProperty("helm")
  private AddonHelmSource helm = null;

  @JsonProperty("oss")
  private AddonOSSAddonSource oss = null;

  public V1UpdateAddonRegistryRequest git(AddonGitAddonSource git) {
    this.git = git;
    return this;
  }

  /**
   * Get git
   * @return git
  **/
  @ApiModelProperty(value = "")

  @Valid

  public AddonGitAddonSource getGit() {
    return git;
  }

  public void setGit(AddonGitAddonSource git) {
    this.git = git;
  }

  public V1UpdateAddonRegistryRequest gitee(AddonGiteeAddonSource gitee) {
    this.gitee = gitee;
    return this;
  }

  /**
   * Get gitee
   * @return gitee
  **/
  @ApiModelProperty(value = "")

  @Valid

  public AddonGiteeAddonSource getGitee() {
    return gitee;
  }

  public void setGitee(AddonGiteeAddonSource gitee) {
    this.gitee = gitee;
  }

  public V1UpdateAddonRegistryRequest gitlab(AddonGitlabAddonSource gitlab) {
    this.gitlab = gitlab;
    return this;
  }

  /**
   * Get gitlab
   * @return gitlab
  **/
  @ApiModelProperty(value = "")

  @Valid

  public AddonGitlabAddonSource getGitlab() {
    return gitlab;
  }

  public void setGitlab(AddonGitlabAddonSource gitlab) {
    this.gitlab = gitlab;
  }

  public V1UpdateAddonRegistryRequest helm(AddonHelmSource helm) {
    this.helm = helm;
    return this;
  }

  /**
   * Get helm
   * @return helm
  **/
  @ApiModelProperty(value = "")

  @Valid

  public AddonHelmSource getHelm() {
    return helm;
  }

  public void setHelm(AddonHelmSource helm) {
    this.helm = helm;
  }

  public V1UpdateAddonRegistryRequest oss(AddonOSSAddonSource oss) {
    this.oss = oss;
    return this;
  }

  /**
   * Get oss
   * @return oss
  **/
  @ApiModelProperty(value = "")

  @Valid

  public AddonOSSAddonSource getOss() {
    return oss;
  }

  public void setOss(AddonOSSAddonSource oss) {
    this.oss = oss;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1UpdateAddonRegistryRequest v1UpdateAddonRegistryRequest = (V1UpdateAddonRegistryRequest) o;
    return Objects.equals(this.git, v1UpdateAddonRegistryRequest.git) &&
        Objects.equals(this.gitee, v1UpdateAddonRegistryRequest.gitee) &&
        Objects.equals(this.gitlab, v1UpdateAddonRegistryRequest.gitlab) &&
        Objects.equals(this.helm, v1UpdateAddonRegistryRequest.helm) &&
        Objects.equals(this.oss, v1UpdateAddonRegistryRequest.oss);
  }

  @Override
  public int hashCode() {
    return Objects.hash(git, gitee, gitlab, helm, oss);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1UpdateAddonRegistryRequest {\n");
    
    sb.append("    git: ").append(toIndentedString(git)).append("\n");
    sb.append("    gitee: ").append(toIndentedString(gitee)).append("\n");
    sb.append("    gitlab: ").append(toIndentedString(gitlab)).append("\n");
    sb.append("    helm: ").append(toIndentedString(helm)).append("\n");
    sb.append("    oss: ").append(toIndentedString(oss)).append("\n");
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

