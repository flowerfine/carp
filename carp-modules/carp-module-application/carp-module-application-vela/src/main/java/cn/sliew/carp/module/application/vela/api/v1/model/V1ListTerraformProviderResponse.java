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
 * V1ListTerraformProviderResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ListTerraformProviderResponse   {
  @JsonProperty("providers")
  @Valid
  private List<V1TerraformProvider> providers = new ArrayList<V1TerraformProvider>();

  public V1ListTerraformProviderResponse providers(List<V1TerraformProvider> providers) {
    this.providers = providers;
    return this;
  }

  public V1ListTerraformProviderResponse addProvidersItem(V1TerraformProvider providersItem) {
    this.providers.add(providersItem);
    return this;
  }

  /**
   * Get providers
   * @return providers
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1TerraformProvider> getProviders() {
    return providers;
  }

  public void setProviders(List<V1TerraformProvider> providers) {
    this.providers = providers;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1ListTerraformProviderResponse v1ListTerraformProviderResponse = (V1ListTerraformProviderResponse) o;
    return Objects.equals(this.providers, v1ListTerraformProviderResponse.providers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(providers);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ListTerraformProviderResponse {\n");
    
    sb.append("    providers: ").append(toIndentedString(providers)).append("\n");
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

