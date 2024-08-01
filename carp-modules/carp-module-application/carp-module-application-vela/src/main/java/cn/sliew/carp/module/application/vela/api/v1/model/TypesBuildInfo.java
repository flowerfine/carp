package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TypesBuildInfo
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class TypesBuildInfo   {
  @JsonProperty("branch")
  private String branch = null;

  @JsonProperty("hash")
  private String hash = null;

  @JsonProperty("repo")
  private String repo = null;

  @JsonProperty("time")
  private Long time = null;

  public TypesBuildInfo branch(String branch) {
    this.branch = branch;
    return this;
  }

  /**
   * Get branch
   * @return branch
  **/
  @ApiModelProperty(value = "")


  public String getBranch() {
    return branch;
  }

  public void setBranch(String branch) {
    this.branch = branch;
  }

  public TypesBuildInfo hash(String hash) {
    this.hash = hash;
    return this;
  }

  /**
   * Get hash
   * @return hash
  **/
  @ApiModelProperty(value = "")


  public String getHash() {
    return hash;
  }

  public void setHash(String hash) {
    this.hash = hash;
  }

  public TypesBuildInfo repo(String repo) {
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

  public TypesBuildInfo time(Long time) {
    this.time = time;
    return this;
  }

  /**
   * Get time
   * @return time
  **/
  @ApiModelProperty(value = "")


  public Long getTime() {
    return time;
  }

  public void setTime(Long time) {
    this.time = time;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TypesBuildInfo typesBuildInfo = (TypesBuildInfo) o;
    return Objects.equals(this.branch, typesBuildInfo.branch) &&
        Objects.equals(this.hash, typesBuildInfo.hash) &&
        Objects.equals(this.repo, typesBuildInfo.repo) &&
        Objects.equals(this.time, typesBuildInfo.time);
  }

  @Override
  public int hashCode() {
    return Objects.hash(branch, hash, repo, time);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TypesBuildInfo {\n");
    
    sb.append("    branch: ").append(toIndentedString(branch)).append("\n");
    sb.append("    hash: ").append(toIndentedString(hash)).append("\n");
    sb.append("    repo: ").append(toIndentedString(repo)).append("\n");
    sb.append("    time: ").append(toIndentedString(time)).append("\n");
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

