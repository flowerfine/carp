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
 * V1ListDefinitionResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ListDefinitionResponse   {
  @JsonProperty("definitions")
  @Valid
  private List<V1DefinitionBase> definitions = new ArrayList<V1DefinitionBase>();

  public V1ListDefinitionResponse definitions(List<V1DefinitionBase> definitions) {
    this.definitions = definitions;
    return this;
  }

  public V1ListDefinitionResponse addDefinitionsItem(V1DefinitionBase definitionsItem) {
    this.definitions.add(definitionsItem);
    return this;
  }

  /**
   * Get definitions
   * @return definitions
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1DefinitionBase> getDefinitions() {
    return definitions;
  }

  public void setDefinitions(List<V1DefinitionBase> definitions) {
    this.definitions = definitions;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1ListDefinitionResponse v1ListDefinitionResponse = (V1ListDefinitionResponse) o;
    return Objects.equals(this.definitions, v1ListDefinitionResponse.definitions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(definitions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ListDefinitionResponse {\n");
    
    sb.append("    definitions: ").append(toIndentedString(definitions)).append("\n");
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

