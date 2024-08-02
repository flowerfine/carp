package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;

import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1ApplicationRevisionBase;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1ListRevisionsResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ListRevisionsResponse   {
  @JsonProperty("revisions")
  @Valid
  private List<V1ApplicationRevisionBase> revisions = new ArrayList<V1ApplicationRevisionBase>();

  @JsonProperty("total")
  private Long total = null;

  public V1ListRevisionsResponse revisions(List<V1ApplicationRevisionBase> revisions) {
    this.revisions = revisions;
    return this;
  }

  public V1ListRevisionsResponse addRevisionsItem(V1ApplicationRevisionBase revisionsItem) {
    this.revisions.add(revisionsItem);
    return this;
  }

  /**
   * Get revisions
   * @return revisions
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1ApplicationRevisionBase> getRevisions() {
    return revisions;
  }

  public void setRevisions(List<V1ApplicationRevisionBase> revisions) {
    this.revisions = revisions;
  }

  public V1ListRevisionsResponse total(Long total) {
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
    V1ListRevisionsResponse v1ListRevisionsResponse = (V1ListRevisionsResponse) o;
    return Objects.equals(this.revisions, v1ListRevisionsResponse.revisions) &&
        Objects.equals(this.total, v1ListRevisionsResponse.total);
  }

  @Override
  public int hashCode() {
    return Objects.hash(revisions, total);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ListRevisionsResponse {\n");
    
    sb.append("    revisions: ").append(toIndentedString(revisions)).append("\n");
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

