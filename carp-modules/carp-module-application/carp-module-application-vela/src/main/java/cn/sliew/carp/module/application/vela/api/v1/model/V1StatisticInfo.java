package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1StatisticInfo
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1StatisticInfo   {
  @JsonProperty("appCount")
  private String appCount = null;

  @JsonProperty("clusterCount")
  private String clusterCount = null;

  @JsonProperty("componentDefinitionTopList")
  @Valid
  private List<String> componentDefinitionTopList = null;

  @JsonProperty("enableAddonList")
  @Valid
  private Map<String, String> enableAddonList = null;

  @JsonProperty("policyDefinitionTopList")
  @Valid
  private List<String> policyDefinitionTopList = null;

  @JsonProperty("traitDefinitionTopList")
  @Valid
  private List<String> traitDefinitionTopList = null;

  @JsonProperty("updateTime")
  private OffsetDateTime updateTime = null;

  @JsonProperty("workflowDefinitionTopList")
  @Valid
  private List<String> workflowDefinitionTopList = null;

  public V1StatisticInfo appCount(String appCount) {
    this.appCount = appCount;
    return this;
  }

  /**
   * Get appCount
   * @return appCount
  **/
  @ApiModelProperty(value = "")


  public String getAppCount() {
    return appCount;
  }

  public void setAppCount(String appCount) {
    this.appCount = appCount;
  }

  public V1StatisticInfo clusterCount(String clusterCount) {
    this.clusterCount = clusterCount;
    return this;
  }

  /**
   * Get clusterCount
   * @return clusterCount
  **/
  @ApiModelProperty(value = "")


  public String getClusterCount() {
    return clusterCount;
  }

  public void setClusterCount(String clusterCount) {
    this.clusterCount = clusterCount;
  }

  public V1StatisticInfo componentDefinitionTopList(List<String> componentDefinitionTopList) {
    this.componentDefinitionTopList = componentDefinitionTopList;
    return this;
  }

  public V1StatisticInfo addComponentDefinitionTopListItem(String componentDefinitionTopListItem) {
    if (this.componentDefinitionTopList == null) {
      this.componentDefinitionTopList = new ArrayList<String>();
    }
    this.componentDefinitionTopList.add(componentDefinitionTopListItem);
    return this;
  }

  /**
   * Get componentDefinitionTopList
   * @return componentDefinitionTopList
  **/
  @ApiModelProperty(value = "")


  public List<String> getComponentDefinitionTopList() {
    return componentDefinitionTopList;
  }

  public void setComponentDefinitionTopList(List<String> componentDefinitionTopList) {
    this.componentDefinitionTopList = componentDefinitionTopList;
  }

  public V1StatisticInfo enableAddonList(Map<String, String> enableAddonList) {
    this.enableAddonList = enableAddonList;
    return this;
  }

  public V1StatisticInfo putEnableAddonListItem(String key, String enableAddonListItem) {
    if (this.enableAddonList == null) {
      this.enableAddonList = new HashMap<String, String>();
    }
    this.enableAddonList.put(key, enableAddonListItem);
    return this;
  }

  /**
   * Get enableAddonList
   * @return enableAddonList
  **/
  @ApiModelProperty(value = "")


  public Map<String, String> getEnableAddonList() {
    return enableAddonList;
  }

  public void setEnableAddonList(Map<String, String> enableAddonList) {
    this.enableAddonList = enableAddonList;
  }

  public V1StatisticInfo policyDefinitionTopList(List<String> policyDefinitionTopList) {
    this.policyDefinitionTopList = policyDefinitionTopList;
    return this;
  }

  public V1StatisticInfo addPolicyDefinitionTopListItem(String policyDefinitionTopListItem) {
    if (this.policyDefinitionTopList == null) {
      this.policyDefinitionTopList = new ArrayList<String>();
    }
    this.policyDefinitionTopList.add(policyDefinitionTopListItem);
    return this;
  }

  /**
   * Get policyDefinitionTopList
   * @return policyDefinitionTopList
  **/
  @ApiModelProperty(value = "")


  public List<String> getPolicyDefinitionTopList() {
    return policyDefinitionTopList;
  }

  public void setPolicyDefinitionTopList(List<String> policyDefinitionTopList) {
    this.policyDefinitionTopList = policyDefinitionTopList;
  }

  public V1StatisticInfo traitDefinitionTopList(List<String> traitDefinitionTopList) {
    this.traitDefinitionTopList = traitDefinitionTopList;
    return this;
  }

  public V1StatisticInfo addTraitDefinitionTopListItem(String traitDefinitionTopListItem) {
    if (this.traitDefinitionTopList == null) {
      this.traitDefinitionTopList = new ArrayList<String>();
    }
    this.traitDefinitionTopList.add(traitDefinitionTopListItem);
    return this;
  }

  /**
   * Get traitDefinitionTopList
   * @return traitDefinitionTopList
  **/
  @ApiModelProperty(value = "")


  public List<String> getTraitDefinitionTopList() {
    return traitDefinitionTopList;
  }

  public void setTraitDefinitionTopList(List<String> traitDefinitionTopList) {
    this.traitDefinitionTopList = traitDefinitionTopList;
  }

  public V1StatisticInfo updateTime(OffsetDateTime updateTime) {
    this.updateTime = updateTime;
    return this;
  }

  /**
   * Get updateTime
   * @return updateTime
  **/
  @ApiModelProperty(value = "")

  @Valid

  public OffsetDateTime getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(OffsetDateTime updateTime) {
    this.updateTime = updateTime;
  }

  public V1StatisticInfo workflowDefinitionTopList(List<String> workflowDefinitionTopList) {
    this.workflowDefinitionTopList = workflowDefinitionTopList;
    return this;
  }

  public V1StatisticInfo addWorkflowDefinitionTopListItem(String workflowDefinitionTopListItem) {
    if (this.workflowDefinitionTopList == null) {
      this.workflowDefinitionTopList = new ArrayList<String>();
    }
    this.workflowDefinitionTopList.add(workflowDefinitionTopListItem);
    return this;
  }

  /**
   * Get workflowDefinitionTopList
   * @return workflowDefinitionTopList
  **/
  @ApiModelProperty(value = "")


  public List<String> getWorkflowDefinitionTopList() {
    return workflowDefinitionTopList;
  }

  public void setWorkflowDefinitionTopList(List<String> workflowDefinitionTopList) {
    this.workflowDefinitionTopList = workflowDefinitionTopList;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1StatisticInfo v1StatisticInfo = (V1StatisticInfo) o;
    return Objects.equals(this.appCount, v1StatisticInfo.appCount) &&
        Objects.equals(this.clusterCount, v1StatisticInfo.clusterCount) &&
        Objects.equals(this.componentDefinitionTopList, v1StatisticInfo.componentDefinitionTopList) &&
        Objects.equals(this.enableAddonList, v1StatisticInfo.enableAddonList) &&
        Objects.equals(this.policyDefinitionTopList, v1StatisticInfo.policyDefinitionTopList) &&
        Objects.equals(this.traitDefinitionTopList, v1StatisticInfo.traitDefinitionTopList) &&
        Objects.equals(this.updateTime, v1StatisticInfo.updateTime) &&
        Objects.equals(this.workflowDefinitionTopList, v1StatisticInfo.workflowDefinitionTopList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(appCount, clusterCount, componentDefinitionTopList, enableAddonList, policyDefinitionTopList, traitDefinitionTopList, updateTime, workflowDefinitionTopList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1StatisticInfo {\n");
    
    sb.append("    appCount: ").append(toIndentedString(appCount)).append("\n");
    sb.append("    clusterCount: ").append(toIndentedString(clusterCount)).append("\n");
    sb.append("    componentDefinitionTopList: ").append(toIndentedString(componentDefinitionTopList)).append("\n");
    sb.append("    enableAddonList: ").append(toIndentedString(enableAddonList)).append("\n");
    sb.append("    policyDefinitionTopList: ").append(toIndentedString(policyDefinitionTopList)).append("\n");
    sb.append("    traitDefinitionTopList: ").append(toIndentedString(traitDefinitionTopList)).append("\n");
    sb.append("    updateTime: ").append(toIndentedString(updateTime)).append("\n");
    sb.append("    workflowDefinitionTopList: ").append(toIndentedString(workflowDefinitionTopList)).append("\n");
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

