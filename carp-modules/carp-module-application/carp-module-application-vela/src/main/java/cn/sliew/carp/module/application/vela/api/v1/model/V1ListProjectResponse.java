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
 * V1ListProjectResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ListProjectResponse   {
  @JsonProperty("projects")
  @Valid
  private List<V1ProjectBase> projects = new ArrayList<V1ProjectBase>();

  @JsonProperty("total")
  private Long total = null;

  public V1ListProjectResponse projects(List<V1ProjectBase> projects) {
    this.projects = projects;
    return this;
  }

  public V1ListProjectResponse addProjectsItem(V1ProjectBase projectsItem) {
    this.projects.add(projectsItem);
    return this;
  }

  /**
   * Get projects
   * @return projects
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1ProjectBase> getProjects() {
    return projects;
  }

  public void setProjects(List<V1ProjectBase> projects) {
    this.projects = projects;
  }

  public V1ListProjectResponse total(Long total) {
    this.total = total;
    return this;
  }

  /**
   * Get total
   * @return total
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getTotal() {
    return total;
  }

  public void setTotal(Long total) {
    this.total = total;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1ListProjectResponse v1ListProjectResponse = (V1ListProjectResponse) o;
    return Objects.equals(this.projects, v1ListProjectResponse.projects) &&
        Objects.equals(this.total, v1ListProjectResponse.total);
  }

  @Override
  public int hashCode() {
    return Objects.hash(projects, total);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ListProjectResponse {\n");
    
    sb.append("    projects: ").append(toIndentedString(projects)).append("\n");
    sb.append("    total: ").append(toIndentedString(total)).append("\n");
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

