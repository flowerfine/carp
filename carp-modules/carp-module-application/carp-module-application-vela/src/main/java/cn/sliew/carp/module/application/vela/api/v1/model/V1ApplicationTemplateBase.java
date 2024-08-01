package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1ApplicationTemplateBase
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1ApplicationTemplateBase   {
  @JsonProperty("createTime")
  private OffsetDateTime createTime = null;

  @JsonProperty("templateName")
  private String templateName = null;

  @JsonProperty("updateTime")
  private OffsetDateTime updateTime = null;

  @JsonProperty("versions")
  @Valid
  private List<V1ApplicationTemplateVersion> versions = null;

  public V1ApplicationTemplateBase createTime(OffsetDateTime createTime) {
    this.createTime = createTime;
    return this;
  }

  /**
   * Get createTime
   * @return createTime
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public OffsetDateTime getCreateTime() {
    return createTime;
  }

  public void setCreateTime(OffsetDateTime createTime) {
    this.createTime = createTime;
  }

  public V1ApplicationTemplateBase templateName(String templateName) {
    this.templateName = templateName;
    return this;
  }

  /**
   * Get templateName
   * @return templateName
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getTemplateName() {
    return templateName;
  }

  public void setTemplateName(String templateName) {
    this.templateName = templateName;
  }

  public V1ApplicationTemplateBase updateTime(OffsetDateTime updateTime) {
    this.updateTime = updateTime;
    return this;
  }

  /**
   * Get updateTime
   * @return updateTime
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public OffsetDateTime getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(OffsetDateTime updateTime) {
    this.updateTime = updateTime;
  }

  public V1ApplicationTemplateBase versions(List<V1ApplicationTemplateVersion> versions) {
    this.versions = versions;
    return this;
  }

  public V1ApplicationTemplateBase addVersionsItem(V1ApplicationTemplateVersion versionsItem) {
    if (this.versions == null) {
      this.versions = new ArrayList<V1ApplicationTemplateVersion>();
    }
    this.versions.add(versionsItem);
    return this;
  }

  /**
   * Get versions
   * @return versions
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<V1ApplicationTemplateVersion> getVersions() {
    return versions;
  }

  public void setVersions(List<V1ApplicationTemplateVersion> versions) {
    this.versions = versions;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1ApplicationTemplateBase v1ApplicationTemplateBase = (V1ApplicationTemplateBase) o;
    return Objects.equals(this.createTime, v1ApplicationTemplateBase.createTime) &&
        Objects.equals(this.templateName, v1ApplicationTemplateBase.templateName) &&
        Objects.equals(this.updateTime, v1ApplicationTemplateBase.updateTime) &&
        Objects.equals(this.versions, v1ApplicationTemplateBase.versions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(createTime, templateName, updateTime, versions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ApplicationTemplateBase {\n");
    
    sb.append("    createTime: ").append(toIndentedString(createTime)).append("\n");
    sb.append("    templateName: ").append(toIndentedString(templateName)).append("\n");
    sb.append("    updateTime: ").append(toIndentedString(updateTime)).append("\n");
    sb.append("    versions: ").append(toIndentedString(versions)).append("\n");
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

