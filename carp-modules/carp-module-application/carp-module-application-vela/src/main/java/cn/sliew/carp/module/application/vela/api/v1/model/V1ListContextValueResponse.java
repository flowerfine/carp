package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1ListContextValueResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ListContextValueResponse   {
  @JsonProperty("contexts")
  @Valid
  private Map<String, List<ModelValue>> contexts = new HashMap<String, List<ModelValue>>();

  @JsonProperty("total")
  private Integer total = null;

  public V1ListContextValueResponse contexts(Map<String, List<ModelValue>> contexts) {
    this.contexts = contexts;
    return this;
  }

  public V1ListContextValueResponse putContextsItem(String key, List<ModelValue> contextsItem) {
    this.contexts.put(key, contextsItem);
    return this;
  }

  /**
   * Get contexts
   * @return contexts
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public Map<String, List<ModelValue>> getContexts() {
    return contexts;
  }

  public void setContexts(Map<String, List<ModelValue>> contexts) {
    this.contexts = contexts;
  }

  public V1ListContextValueResponse total(Integer total) {
    this.total = total;
    return this;
  }

  /**
   * Get total
   * @return total
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getTotal() {
    return total;
  }

  public void setTotal(Integer total) {
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
    V1ListContextValueResponse v1ListContextValueResponse = (V1ListContextValueResponse) o;
    return Objects.equals(this.contexts, v1ListContextValueResponse.contexts) &&
        Objects.equals(this.total, v1ListContextValueResponse.total);
  }

  @Override
  public int hashCode() {
    return Objects.hash(contexts, total);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ListContextValueResponse {\n");
    
    sb.append("    contexts: ").append(toIndentedString(contexts)).append("\n");
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

