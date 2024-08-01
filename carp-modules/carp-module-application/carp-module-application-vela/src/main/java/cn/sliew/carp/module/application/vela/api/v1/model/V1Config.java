package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * V1Config
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class V1Config   {
  @JsonProperty("ArgsEscaped")
  private Boolean argsEscaped = null;

  @JsonProperty("AttachStderr")
  private Boolean attachStderr = null;

  @JsonProperty("AttachStdin")
  private Boolean attachStdin = null;

  @JsonProperty("AttachStdout")
  private Boolean attachStdout = null;

  @JsonProperty("Cmd")
  @Valid
  private List<String> cmd = null;

  @JsonProperty("Domainname")
  private String domainname = null;

  @JsonProperty("Entrypoint")
  @Valid
  private List<String> entrypoint = null;

  @JsonProperty("Env")
  @Valid
  private List<String> env = null;

  @JsonProperty("ExposedPorts")
  @Valid
  private Map<String, V1ConfigExposedPorts> exposedPorts = null;

  @JsonProperty("Healthcheck")
  private V1HealthConfig healthcheck = null;

  @JsonProperty("Hostname")
  private String hostname = null;

  @JsonProperty("Image")
  private String image = null;

  @JsonProperty("Labels")
  @Valid
  private Map<String, String> labels = null;

  @JsonProperty("MacAddress")
  private String macAddress = null;

  @JsonProperty("NetworkDisabled")
  private Boolean networkDisabled = null;

  @JsonProperty("OnBuild")
  @Valid
  private List<String> onBuild = null;

  @JsonProperty("OpenStdin")
  private Boolean openStdin = null;

  @JsonProperty("Shell")
  @Valid
  private List<String> shell = null;

  @JsonProperty("StdinOnce")
  private Boolean stdinOnce = null;

  @JsonProperty("StopSignal")
  private String stopSignal = null;

  @JsonProperty("Tty")
  private Boolean tty = null;

  @JsonProperty("User")
  private String user = null;

  @JsonProperty("Volumes")
  @Valid
  private Map<String, V1ConfigVolumes> volumes = null;

  @JsonProperty("WorkingDir")
  private String workingDir = null;

  public V1Config argsEscaped(Boolean argsEscaped) {
    this.argsEscaped = argsEscaped;
    return this;
  }

  /**
   * Get argsEscaped
   * @return argsEscaped
  **/
  @ApiModelProperty(value = "")


  public Boolean isArgsEscaped() {
    return argsEscaped;
  }

  public void setArgsEscaped(Boolean argsEscaped) {
    this.argsEscaped = argsEscaped;
  }

  public V1Config attachStderr(Boolean attachStderr) {
    this.attachStderr = attachStderr;
    return this;
  }

  /**
   * Get attachStderr
   * @return attachStderr
  **/
  @ApiModelProperty(value = "")


  public Boolean isAttachStderr() {
    return attachStderr;
  }

  public void setAttachStderr(Boolean attachStderr) {
    this.attachStderr = attachStderr;
  }

  public V1Config attachStdin(Boolean attachStdin) {
    this.attachStdin = attachStdin;
    return this;
  }

  /**
   * Get attachStdin
   * @return attachStdin
  **/
  @ApiModelProperty(value = "")


  public Boolean isAttachStdin() {
    return attachStdin;
  }

  public void setAttachStdin(Boolean attachStdin) {
    this.attachStdin = attachStdin;
  }

  public V1Config attachStdout(Boolean attachStdout) {
    this.attachStdout = attachStdout;
    return this;
  }

  /**
   * Get attachStdout
   * @return attachStdout
  **/
  @ApiModelProperty(value = "")


  public Boolean isAttachStdout() {
    return attachStdout;
  }

  public void setAttachStdout(Boolean attachStdout) {
    this.attachStdout = attachStdout;
  }

  public V1Config cmd(List<String> cmd) {
    this.cmd = cmd;
    return this;
  }

  public V1Config addCmdItem(String cmdItem) {
    if (this.cmd == null) {
      this.cmd = new ArrayList<String>();
    }
    this.cmd.add(cmdItem);
    return this;
  }

  /**
   * Get cmd
   * @return cmd
  **/
  @ApiModelProperty(value = "")


  public List<String> getCmd() {
    return cmd;
  }

  public void setCmd(List<String> cmd) {
    this.cmd = cmd;
  }

  public V1Config domainname(String domainname) {
    this.domainname = domainname;
    return this;
  }

  /**
   * Get domainname
   * @return domainname
  **/
  @ApiModelProperty(value = "")


  public String getDomainname() {
    return domainname;
  }

  public void setDomainname(String domainname) {
    this.domainname = domainname;
  }

  public V1Config entrypoint(List<String> entrypoint) {
    this.entrypoint = entrypoint;
    return this;
  }

  public V1Config addEntrypointItem(String entrypointItem) {
    if (this.entrypoint == null) {
      this.entrypoint = new ArrayList<String>();
    }
    this.entrypoint.add(entrypointItem);
    return this;
  }

  /**
   * Get entrypoint
   * @return entrypoint
  **/
  @ApiModelProperty(value = "")


  public List<String> getEntrypoint() {
    return entrypoint;
  }

  public void setEntrypoint(List<String> entrypoint) {
    this.entrypoint = entrypoint;
  }

  public V1Config env(List<String> env) {
    this.env = env;
    return this;
  }

  public V1Config addEnvItem(String envItem) {
    if (this.env == null) {
      this.env = new ArrayList<String>();
    }
    this.env.add(envItem);
    return this;
  }

  /**
   * Get env
   * @return env
  **/
  @ApiModelProperty(value = "")


  public List<String> getEnv() {
    return env;
  }

  public void setEnv(List<String> env) {
    this.env = env;
  }

  public V1Config exposedPorts(Map<String, V1ConfigExposedPorts> exposedPorts) {
    this.exposedPorts = exposedPorts;
    return this;
  }

  public V1Config putExposedPortsItem(String key, V1ConfigExposedPorts exposedPortsItem) {
    if (this.exposedPorts == null) {
      this.exposedPorts = new HashMap<String, V1ConfigExposedPorts>();
    }
    this.exposedPorts.put(key, exposedPortsItem);
    return this;
  }

  /**
   * Get exposedPorts
   * @return exposedPorts
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Map<String, V1ConfigExposedPorts> getExposedPorts() {
    return exposedPorts;
  }

  public void setExposedPorts(Map<String, V1ConfigExposedPorts> exposedPorts) {
    this.exposedPorts = exposedPorts;
  }

  public V1Config healthcheck(V1HealthConfig healthcheck) {
    this.healthcheck = healthcheck;
    return this;
  }

  /**
   * Get healthcheck
   * @return healthcheck
  **/
  @ApiModelProperty(value = "")

  @Valid

  public V1HealthConfig getHealthcheck() {
    return healthcheck;
  }

  public void setHealthcheck(V1HealthConfig healthcheck) {
    this.healthcheck = healthcheck;
  }

  public V1Config hostname(String hostname) {
    this.hostname = hostname;
    return this;
  }

  /**
   * Get hostname
   * @return hostname
  **/
  @ApiModelProperty(value = "")


  public String getHostname() {
    return hostname;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  public V1Config image(String image) {
    this.image = image;
    return this;
  }

  /**
   * Get image
   * @return image
  **/
  @ApiModelProperty(value = "")


  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public V1Config labels(Map<String, String> labels) {
    this.labels = labels;
    return this;
  }

  public V1Config putLabelsItem(String key, String labelsItem) {
    if (this.labels == null) {
      this.labels = new HashMap<String, String>();
    }
    this.labels.put(key, labelsItem);
    return this;
  }

  /**
   * Get labels
   * @return labels
  **/
  @ApiModelProperty(value = "")


  public Map<String, String> getLabels() {
    return labels;
  }

  public void setLabels(Map<String, String> labels) {
    this.labels = labels;
  }

  public V1Config macAddress(String macAddress) {
    this.macAddress = macAddress;
    return this;
  }

  /**
   * Get macAddress
   * @return macAddress
  **/
  @ApiModelProperty(value = "")


  public String getMacAddress() {
    return macAddress;
  }

  public void setMacAddress(String macAddress) {
    this.macAddress = macAddress;
  }

  public V1Config networkDisabled(Boolean networkDisabled) {
    this.networkDisabled = networkDisabled;
    return this;
  }

  /**
   * Get networkDisabled
   * @return networkDisabled
  **/
  @ApiModelProperty(value = "")


  public Boolean isNetworkDisabled() {
    return networkDisabled;
  }

  public void setNetworkDisabled(Boolean networkDisabled) {
    this.networkDisabled = networkDisabled;
  }

  public V1Config onBuild(List<String> onBuild) {
    this.onBuild = onBuild;
    return this;
  }

  public V1Config addOnBuildItem(String onBuildItem) {
    if (this.onBuild == null) {
      this.onBuild = new ArrayList<String>();
    }
    this.onBuild.add(onBuildItem);
    return this;
  }

  /**
   * Get onBuild
   * @return onBuild
  **/
  @ApiModelProperty(value = "")


  public List<String> getOnBuild() {
    return onBuild;
  }

  public void setOnBuild(List<String> onBuild) {
    this.onBuild = onBuild;
  }

  public V1Config openStdin(Boolean openStdin) {
    this.openStdin = openStdin;
    return this;
  }

  /**
   * Get openStdin
   * @return openStdin
  **/
  @ApiModelProperty(value = "")


  public Boolean isOpenStdin() {
    return openStdin;
  }

  public void setOpenStdin(Boolean openStdin) {
    this.openStdin = openStdin;
  }

  public V1Config shell(List<String> shell) {
    this.shell = shell;
    return this;
  }

  public V1Config addShellItem(String shellItem) {
    if (this.shell == null) {
      this.shell = new ArrayList<String>();
    }
    this.shell.add(shellItem);
    return this;
  }

  /**
   * Get shell
   * @return shell
  **/
  @ApiModelProperty(value = "")


  public List<String> getShell() {
    return shell;
  }

  public void setShell(List<String> shell) {
    this.shell = shell;
  }

  public V1Config stdinOnce(Boolean stdinOnce) {
    this.stdinOnce = stdinOnce;
    return this;
  }

  /**
   * Get stdinOnce
   * @return stdinOnce
  **/
  @ApiModelProperty(value = "")


  public Boolean isStdinOnce() {
    return stdinOnce;
  }

  public void setStdinOnce(Boolean stdinOnce) {
    this.stdinOnce = stdinOnce;
  }

  public V1Config stopSignal(String stopSignal) {
    this.stopSignal = stopSignal;
    return this;
  }

  /**
   * Get stopSignal
   * @return stopSignal
  **/
  @ApiModelProperty(value = "")


  public String getStopSignal() {
    return stopSignal;
  }

  public void setStopSignal(String stopSignal) {
    this.stopSignal = stopSignal;
  }

  public V1Config tty(Boolean tty) {
    this.tty = tty;
    return this;
  }

  /**
   * Get tty
   * @return tty
  **/
  @ApiModelProperty(value = "")


  public Boolean isTty() {
    return tty;
  }

  public void setTty(Boolean tty) {
    this.tty = tty;
  }

  public V1Config user(String user) {
    this.user = user;
    return this;
  }

  /**
   * Get user
   * @return user
  **/
  @ApiModelProperty(value = "")


  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public V1Config volumes(Map<String, V1ConfigVolumes> volumes) {
    this.volumes = volumes;
    return this;
  }

  public V1Config putVolumesItem(String key, V1ConfigVolumes volumesItem) {
    if (this.volumes == null) {
      this.volumes = new HashMap<String, V1ConfigVolumes>();
    }
    this.volumes.put(key, volumesItem);
    return this;
  }

  /**
   * Get volumes
   * @return volumes
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Map<String, V1ConfigVolumes> getVolumes() {
    return volumes;
  }

  public void setVolumes(Map<String, V1ConfigVolumes> volumes) {
    this.volumes = volumes;
  }

  public V1Config workingDir(String workingDir) {
    this.workingDir = workingDir;
    return this;
  }

  /**
   * Get workingDir
   * @return workingDir
  **/
  @ApiModelProperty(value = "")


  public String getWorkingDir() {
    return workingDir;
  }

  public void setWorkingDir(String workingDir) {
    this.workingDir = workingDir;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1Config v1Config = (V1Config) o;
    return Objects.equals(this.argsEscaped, v1Config.argsEscaped) &&
        Objects.equals(this.attachStderr, v1Config.attachStderr) &&
        Objects.equals(this.attachStdin, v1Config.attachStdin) &&
        Objects.equals(this.attachStdout, v1Config.attachStdout) &&
        Objects.equals(this.cmd, v1Config.cmd) &&
        Objects.equals(this.domainname, v1Config.domainname) &&
        Objects.equals(this.entrypoint, v1Config.entrypoint) &&
        Objects.equals(this.env, v1Config.env) &&
        Objects.equals(this.exposedPorts, v1Config.exposedPorts) &&
        Objects.equals(this.healthcheck, v1Config.healthcheck) &&
        Objects.equals(this.hostname, v1Config.hostname) &&
        Objects.equals(this.image, v1Config.image) &&
        Objects.equals(this.labels, v1Config.labels) &&
        Objects.equals(this.macAddress, v1Config.macAddress) &&
        Objects.equals(this.networkDisabled, v1Config.networkDisabled) &&
        Objects.equals(this.onBuild, v1Config.onBuild) &&
        Objects.equals(this.openStdin, v1Config.openStdin) &&
        Objects.equals(this.shell, v1Config.shell) &&
        Objects.equals(this.stdinOnce, v1Config.stdinOnce) &&
        Objects.equals(this.stopSignal, v1Config.stopSignal) &&
        Objects.equals(this.tty, v1Config.tty) &&
        Objects.equals(this.user, v1Config.user) &&
        Objects.equals(this.volumes, v1Config.volumes) &&
        Objects.equals(this.workingDir, v1Config.workingDir);
  }

  @Override
  public int hashCode() {
    return Objects.hash(argsEscaped, attachStderr, attachStdin, attachStdout, cmd, domainname, entrypoint, env, exposedPorts, healthcheck, hostname, image, labels, macAddress, networkDisabled, onBuild, openStdin, shell, stdinOnce, stopSignal, tty, user, volumes, workingDir);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1Config {\n");
    
    sb.append("    argsEscaped: ").append(toIndentedString(argsEscaped)).append("\n");
    sb.append("    attachStderr: ").append(toIndentedString(attachStderr)).append("\n");
    sb.append("    attachStdin: ").append(toIndentedString(attachStdin)).append("\n");
    sb.append("    attachStdout: ").append(toIndentedString(attachStdout)).append("\n");
    sb.append("    cmd: ").append(toIndentedString(cmd)).append("\n");
    sb.append("    domainname: ").append(toIndentedString(domainname)).append("\n");
    sb.append("    entrypoint: ").append(toIndentedString(entrypoint)).append("\n");
    sb.append("    env: ").append(toIndentedString(env)).append("\n");
    sb.append("    exposedPorts: ").append(toIndentedString(exposedPorts)).append("\n");
    sb.append("    healthcheck: ").append(toIndentedString(healthcheck)).append("\n");
    sb.append("    hostname: ").append(toIndentedString(hostname)).append("\n");
    sb.append("    image: ").append(toIndentedString(image)).append("\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
    sb.append("    macAddress: ").append(toIndentedString(macAddress)).append("\n");
    sb.append("    networkDisabled: ").append(toIndentedString(networkDisabled)).append("\n");
    sb.append("    onBuild: ").append(toIndentedString(onBuild)).append("\n");
    sb.append("    openStdin: ").append(toIndentedString(openStdin)).append("\n");
    sb.append("    shell: ").append(toIndentedString(shell)).append("\n");
    sb.append("    stdinOnce: ").append(toIndentedString(stdinOnce)).append("\n");
    sb.append("    stopSignal: ").append(toIndentedString(stopSignal)).append("\n");
    sb.append("    tty: ").append(toIndentedString(tty)).append("\n");
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
    sb.append("    volumes: ").append(toIndentedString(volumes)).append("\n");
    sb.append("    workingDir: ").append(toIndentedString(workingDir)).append("\n");
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

