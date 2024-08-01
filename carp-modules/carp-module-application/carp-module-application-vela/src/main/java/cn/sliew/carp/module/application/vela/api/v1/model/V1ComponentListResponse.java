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
 * V1ComponentListResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ComponentListResponse   {
  @JsonProperty("components")
  @Valid
  private List<V1ComponentBase> components = new ArrayList<V1ComponentBase>();

  public V1ComponentListResponse components(List<V1ComponentBase> components) {
    this.components = components;
    return this;
  }

  public V1ComponentListResponse addComponentsItem(V1ComponentBase componentsItem) {
    this.components.add(componentsItem);
    return this;
  }

  /**
   * Get components
   * @return components
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1ComponentBase> getComponents() {
    return components;
  }

  public void setComponents(List<V1ComponentBase> components) {
    this.components = components;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1ComponentListResponse v1ComponentListResponse = (V1ComponentListResponse) o;
    return Objects.equals(this.components, v1ComponentListResponse.components);
  }

  @Override
  public int hashCode() {
    return Objects.hash(components);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ComponentListResponse {\n");
    
    sb.append("    components: ").append(toIndentedString(components)).append("\n");
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

