package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.Map;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1HandleApplicationTriggerWebhookRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1HandleApplicationTriggerWebhookRequest   {
  @JsonProperty("action")
  private String action = null;

  @JsonProperty("codeInfo")
  private ModelCodeInfo codeInfo = null;

  @JsonProperty("step")
  private String step = null;

  @JsonProperty("upgrade")
  @Valid
  private Map<String, ModelJSONStruct> upgrade = null;

  public V1HandleApplicationTriggerWebhookRequest action(String action) {
    this.action = action;
    return this;
  }

  /**
   * Get action
   * @return action
  **/
  @ApiModelProperty(value = "")


  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public V1HandleApplicationTriggerWebhookRequest codeInfo(ModelCodeInfo codeInfo) {
    this.codeInfo = codeInfo;
    return this;
  }

  /**
   * Get codeInfo
   * @return codeInfo
  **/
  @ApiModelProperty(value = "")

  @Valid

  public ModelCodeInfo getCodeInfo() {
    return codeInfo;
  }

  public void setCodeInfo(ModelCodeInfo codeInfo) {
    this.codeInfo = codeInfo;
  }

  public V1HandleApplicationTriggerWebhookRequest step(String step) {
    this.step = step;
    return this;
  }

  /**
   * Get step
   * @return step
  **/
  @ApiModelProperty(value = "")


  public String getStep() {
    return step;
  }

  public void setStep(String step) {
    this.step = step;
  }

  public V1HandleApplicationTriggerWebhookRequest upgrade(Map<String, ModelJSONStruct> upgrade) {
    this.upgrade = upgrade;
    return this;
  }

  public V1HandleApplicationTriggerWebhookRequest putUpgradeItem(String key, ModelJSONStruct upgradeItem) {
    if (this.upgrade == null) {
      this.upgrade = new HashMap<String, ModelJSONStruct>();
    }
    this.upgrade.put(key, upgradeItem);
    return this;
  }

  /**
   * Get upgrade
   * @return upgrade
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Map<String, ModelJSONStruct> getUpgrade() {
    return upgrade;
  }

  public void setUpgrade(Map<String, ModelJSONStruct> upgrade) {
    this.upgrade = upgrade;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1HandleApplicationTriggerWebhookRequest v1HandleApplicationTriggerWebhookRequest = (V1HandleApplicationTriggerWebhookRequest) o;
    return Objects.equals(this.action, v1HandleApplicationTriggerWebhookRequest.action) &&
        Objects.equals(this.codeInfo, v1HandleApplicationTriggerWebhookRequest.codeInfo) &&
        Objects.equals(this.step, v1HandleApplicationTriggerWebhookRequest.step) &&
        Objects.equals(this.upgrade, v1HandleApplicationTriggerWebhookRequest.upgrade);
  }

  @Override
  public int hashCode() {
    return Objects.hash(action, codeInfo, step, upgrade);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1HandleApplicationTriggerWebhookRequest {\n");
    
    sb.append("    action: ").append(toIndentedString(action)).append("\n");
    sb.append("    codeInfo: ").append(toIndentedString(codeInfo)).append("\n");
    sb.append("    step: ").append(toIndentedString(step)).append("\n");
    sb.append("    upgrade: ").append(toIndentedString(upgrade)).append("\n");
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

