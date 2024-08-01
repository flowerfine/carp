package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1ChartVersionListResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ChartVersionListResponse   {
  @JsonProperty("versions")
  @Valid
  private List<RepoChartVersion> versions = new ArrayList<RepoChartVersion>();

  public V1ChartVersionListResponse versions(List<RepoChartVersion> versions) {
    this.versions = versions;
    return this;
  }

  public V1ChartVersionListResponse addVersionsItem(RepoChartVersion versionsItem) {
    this.versions.add(versionsItem);
    return this;
  }

  /**
   * Get versions
   * @return versions
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<RepoChartVersion> getVersions() {
    return versions;
  }

  public void setVersions(List<RepoChartVersion> versions) {
    this.versions = versions;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1ChartVersionListResponse v1ChartVersionListResponse = (V1ChartVersionListResponse) o;
    return Objects.equals(this.versions, v1ChartVersionListResponse.versions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(versions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ChartVersionListResponse {\n");
    
    sb.append("    versions: ").append(toIndentedString(versions)).append("\n");
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

