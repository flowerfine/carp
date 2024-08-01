package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CommonRevision
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class CommonRevision   {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("revision")
  private Long revision = null;

  @JsonProperty("revisionHash")
  private String revisionHash = null;

  public CommonRevision name(String name) {
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

  public CommonRevision revision(Long revision) {
    this.revision = revision;
    return this;
  }

  /**
   * Get revision
   * @return revision
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getRevision() {
    return revision;
  }

  public void setRevision(Long revision) {
    this.revision = revision;
  }

  public CommonRevision revisionHash(String revisionHash) {
    this.revisionHash = revisionHash;
    return this;
  }

  /**
   * Get revisionHash
   * @return revisionHash
  **/
  @ApiModelProperty(value = "")


  public String getRevisionHash() {
    return revisionHash;
  }

  public void setRevisionHash(String revisionHash) {
    this.revisionHash = revisionHash;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommonRevision commonRevision = (CommonRevision) o;
    return Objects.equals(this.name, commonRevision.name) &&
        Objects.equals(this.revision, commonRevision.revision) &&
        Objects.equals(this.revisionHash, commonRevision.revisionHash);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, revision, revisionHash);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommonRevision {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    revision: ").append(toIndentedString(revision)).append("\n");
    sb.append("    revisionHash: ").append(toIndentedString(revisionHash)).append("\n");
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

