package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ModelCodeInfo
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class ModelCodeInfo   {
  @JsonProperty("branch")
  private String branch = null;

  @JsonProperty("commit")
  private String commit = null;

  @JsonProperty("user")
  private String user = null;

  public ModelCodeInfo branch(String branch) {
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

  public ModelCodeInfo commit(String commit) {
    this.commit = commit;
    return this;
  }

  /**
   * Get commit
   * @return commit
  **/
  @ApiModelProperty(value = "")


  public String getCommit() {
    return commit;
  }

  public void setCommit(String commit) {
    this.commit = commit;
  }

  public ModelCodeInfo user(String user) {
    this.user = user;
    return this;
  }

  /**
   * Get user
   * @return user
  **/
  @ApiModelProperty(value = "")


  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ModelCodeInfo modelCodeInfo = (ModelCodeInfo) o;
    return Objects.equals(this.branch, modelCodeInfo.branch) &&
        Objects.equals(this.commit, modelCodeInfo.commit) &&
        Objects.equals(this.user, modelCodeInfo.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(branch, commit, user);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ModelCodeInfo {\n");
    
    sb.append("    branch: ").append(toIndentedString(branch)).append("\n");
    sb.append("    commit: ").append(toIndentedString(commit)).append("\n");
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
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

