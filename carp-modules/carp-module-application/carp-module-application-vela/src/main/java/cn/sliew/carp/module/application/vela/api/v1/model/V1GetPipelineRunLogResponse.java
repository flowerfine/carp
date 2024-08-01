package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1GetPipelineRunLogResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1GetPipelineRunLogResponse   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("log")
  private String log = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("phase")
  private String phase = null;

  @JsonProperty("source")
  private String source = null;

  @JsonProperty("type")
  private String type = null;

  public V1GetPipelineRunLogResponse id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public V1GetPipelineRunLogResponse log(String log) {
    this.log = log;
    return this;
  }

  /**
   * Get log
   * @return log
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getLog() {
    return log;
  }

  public void setLog(String log) {
    this.log = log;
  }

  public V1GetPipelineRunLogResponse name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public V1GetPipelineRunLogResponse phase(String phase) {
    this.phase = phase;
    return this;
  }

  /**
   * Get phase
   * @return phase
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getPhase() {
    return phase;
  }

  public void setPhase(String phase) {
    this.phase = phase;
  }

  public V1GetPipelineRunLogResponse source(String source) {
    this.source = source;
    return this;
  }

  /**
   * Get source
   * @return source
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public V1GetPipelineRunLogResponse type(String type) {
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
    V1GetPipelineRunLogResponse v1GetPipelineRunLogResponse = (V1GetPipelineRunLogResponse) o;
    return Objects.equals(this.id, v1GetPipelineRunLogResponse.id) &&
        Objects.equals(this.log, v1GetPipelineRunLogResponse.log) &&
        Objects.equals(this.name, v1GetPipelineRunLogResponse.name) &&
        Objects.equals(this.phase, v1GetPipelineRunLogResponse.phase) &&
        Objects.equals(this.source, v1GetPipelineRunLogResponse.source) &&
        Objects.equals(this.type, v1GetPipelineRunLogResponse.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, log, name, phase, source, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1GetPipelineRunLogResponse {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    log: ").append(toIndentedString(log)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    phase: ").append(toIndentedString(phase)).append("\n");
    sb.append("    source: ").append(toIndentedString(source)).append("\n");
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

