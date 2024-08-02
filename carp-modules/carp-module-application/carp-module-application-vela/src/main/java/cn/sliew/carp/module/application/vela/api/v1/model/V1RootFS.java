package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;

import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1Hash;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1RootFS
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1RootFS   {
  @JsonProperty("diff_ids")
  @Valid
  private List<V1Hash> diffIds = new ArrayList<V1Hash>();

  @JsonProperty("type")
  private String type = null;

  public V1RootFS diffIds(List<V1Hash> diffIds) {
    this.diffIds = diffIds;
    return this;
  }

  public V1RootFS addDiffIdsItem(V1Hash diffIdsItem) {
    this.diffIds.add(diffIdsItem);
    return this;
  }

  /**
   * Get diffIds
   * @return diffIds
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1Hash> getDiffIds() {
    return diffIds;
  }

  public void setDiffIds(List<V1Hash> diffIds) {
    this.diffIds = diffIds;
  }

  public V1RootFS type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1RootFS v1RootFS = (V1RootFS) o;
    return Objects.equals(this.diffIds, v1RootFS.diffIds) &&
        Objects.equals(this.type, v1RootFS.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(diffIds, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1RootFS {\n");
    
    sb.append("    diffIds: ").append(toIndentedString(diffIds)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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

