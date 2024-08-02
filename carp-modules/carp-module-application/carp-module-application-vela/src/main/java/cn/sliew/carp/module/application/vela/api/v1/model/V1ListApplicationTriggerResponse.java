package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;

import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1ApplicationTriggerBase;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1ListApplicationTriggerResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ListApplicationTriggerResponse   {
  @JsonProperty("triggers")
  @Valid
  private List<V1ApplicationTriggerBase> triggers = new ArrayList<V1ApplicationTriggerBase>();

  public V1ListApplicationTriggerResponse triggers(List<V1ApplicationTriggerBase> triggers) {
    this.triggers = triggers;
    return this;
  }

  public V1ListApplicationTriggerResponse addTriggersItem(V1ApplicationTriggerBase triggersItem) {
    this.triggers.add(triggersItem);
    return this;
  }

  /**
   * Get triggers
   * @return triggers
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<V1ApplicationTriggerBase> getTriggers() {
    return triggers;
  }

  public void setTriggers(List<V1ApplicationTriggerBase> triggers) {
    this.triggers = triggers;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1ListApplicationTriggerResponse v1ListApplicationTriggerResponse = (V1ListApplicationTriggerResponse) o;
    return Objects.equals(this.triggers, v1ListApplicationTriggerResponse.triggers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(triggers);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ListApplicationTriggerResponse {\n");
    
    sb.append("    triggers: ").append(toIndentedString(triggers)).append("\n");
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

