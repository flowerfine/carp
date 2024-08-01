package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1CreateClusterNamespaceResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1CreateClusterNamespaceResponse   {
  @JsonProperty("exists")
  private Boolean exists = null;

  public V1CreateClusterNamespaceResponse exists(Boolean exists) {
    this.exists = exists;
    return this;
  }

  /**
   * Get exists
   * @return exists
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Boolean isExists() {
    return exists;
  }

  public void setExists(Boolean exists) {
    this.exists = exists;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1CreateClusterNamespaceResponse v1CreateClusterNamespaceResponse = (V1CreateClusterNamespaceResponse) o;
    return Objects.equals(this.exists, v1CreateClusterNamespaceResponse.exists);
  }

  @Override
  public int hashCode() {
    return Objects.hash(exists);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1CreateClusterNamespaceResponse {\n");
    
    sb.append("    exists: ").append(toIndentedString(exists)).append("\n");
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

