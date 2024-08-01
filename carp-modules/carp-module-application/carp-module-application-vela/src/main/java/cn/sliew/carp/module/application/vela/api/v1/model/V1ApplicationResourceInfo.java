package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1ApplicationResourceInfo
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ApplicationResourceInfo   {
  @JsonProperty("componentNum")
  private Long componentNum = null;

  public V1ApplicationResourceInfo componentNum(Long componentNum) {
    this.componentNum = componentNum;
    return this;
  }

  /**
   * Get componentNum
   * @return componentNum
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getComponentNum() {
    return componentNum;
  }

  public void setComponentNum(Long componentNum) {
    this.componentNum = componentNum;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1ApplicationResourceInfo v1ApplicationResourceInfo = (V1ApplicationResourceInfo) o;
    return Objects.equals(this.componentNum, v1ApplicationResourceInfo.componentNum);
  }

  @Override
  public int hashCode() {
    return Objects.hash(componentNum);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ApplicationResourceInfo {\n");
    
    sb.append("    componentNum: ").append(toIndentedString(componentNum)).append("\n");
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

