package cn.sliew.carp.module.application.vela.api.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ObjectReference contains enough information to let you inspect or modify the referred object.
 */
@ApiModel(description = "ObjectReference contains enough information to let you inspect or modify the referred object.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2024-08-01T20:13:23.593+08:00")


public class CommonClusterObjectReference   {
  @JsonProperty("apiVersion")
  private String apiVersion = null;

  @JsonProperty("cluster")
  private String cluster = null;

  @JsonProperty("creator")
  private String creator = null;

  @JsonProperty("fieldPath")
  private String fieldPath = null;

  @JsonProperty("kind")
  private String kind = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("namespace")
  private String namespace = null;

  @JsonProperty("resourceVersion")
  private String resourceVersion = null;

  @JsonProperty("uid")
  private String uid = null;

  public CommonClusterObjectReference apiVersion(String apiVersion) {
    this.apiVersion = apiVersion;
    return this;
  }

  /**
   * API version of the referent.
   * @return apiVersion
  **/
  @ApiModelProperty(value = "API version of the referent.")


  public String getApiVersion() {
    return apiVersion;
  }

  public void setApiVersion(String apiVersion) {
    this.apiVersion = apiVersion;
  }

  public CommonClusterObjectReference cluster(String cluster) {
    this.cluster = cluster;
    return this;
  }

  /**
   * Get cluster
   * @return cluster
  **/
  @ApiModelProperty(value = "")


  public String getCluster() {
    return cluster;
  }

  public void setCluster(String cluster) {
    this.cluster = cluster;
  }

  public CommonClusterObjectReference creator(String creator) {
    this.creator = creator;
    return this;
  }

  /**
   * Get creator
   * @return creator
  **/
  @ApiModelProperty(value = "")


  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  public CommonClusterObjectReference fieldPath(String fieldPath) {
    this.fieldPath = fieldPath;
    return this;
  }

  /**
   * If referring to a piece of an object instead of an entire object, this string should contain a valid JSON/Go field access statement, such as desiredState.manifest.containers[2]. For example, if the object reference is to a container within a pod, this would take on a value like: \"spec.containers{name}\" (where \"name\" refers to the name of the container that triggered the event) or if no container name is specified \"spec.containers[2]\" (container with index 2 in this pod). This syntax is chosen only to have some well-defined way of referencing a part of an object.
   * @return fieldPath
  **/
  @ApiModelProperty(value = "If referring to a piece of an object instead of an entire object, this string should contain a valid JSON/Go field access statement, such as desiredState.manifest.containers[2]. For example, if the object reference is to a container within a pod, this would take on a value like: \"spec.containers{name}\" (where \"name\" refers to the name of the container that triggered the event) or if no container name is specified \"spec.containers[2]\" (container with index 2 in this pod). This syntax is chosen only to have some well-defined way of referencing a part of an object.")


  public String getFieldPath() {
    return fieldPath;
  }

  public void setFieldPath(String fieldPath) {
    this.fieldPath = fieldPath;
  }

  public CommonClusterObjectReference kind(String kind) {
    this.kind = kind;
    return this;
  }

  /**
   * Kind of the referent. More info: https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#types-kinds
   * @return kind
  **/
  @ApiModelProperty(value = "Kind of the referent. More info: https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#types-kinds")


  public String getKind() {
    return kind;
  }

  public void setKind(String kind) {
    this.kind = kind;
  }

  public CommonClusterObjectReference name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Name of the referent. More info: https://kubernetes.io/docs/concepts/overview/working-with-objects/names/#names
   * @return name
  **/
  @ApiModelProperty(value = "Name of the referent. More info: https://kubernetes.io/docs/concepts/overview/working-with-objects/names/#names")


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CommonClusterObjectReference namespace(String namespace) {
    this.namespace = namespace;
    return this;
  }

  /**
   * Namespace of the referent. More info: https://kubernetes.io/docs/concepts/overview/working-with-objects/namespaces/
   * @return namespace
  **/
  @ApiModelProperty(value = "Namespace of the referent. More info: https://kubernetes.io/docs/concepts/overview/working-with-objects/namespaces/")


  public String getNamespace() {
    return namespace;
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public CommonClusterObjectReference resourceVersion(String resourceVersion) {
    this.resourceVersion = resourceVersion;
    return this;
  }

  /**
   * Specific resourceVersion to which this reference is made, if any. More info: https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#concurrency-control-and-consistency
   * @return resourceVersion
  **/
  @ApiModelProperty(value = "Specific resourceVersion to which this reference is made, if any. More info: https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#concurrency-control-and-consistency")


  public String getResourceVersion() {
    return resourceVersion;
  }

  public void setResourceVersion(String resourceVersion) {
    this.resourceVersion = resourceVersion;
  }

  public CommonClusterObjectReference uid(String uid) {
    this.uid = uid;
    return this;
  }

  /**
   * UID of the referent. More info: https://kubernetes.io/docs/concepts/overview/working-with-objects/names/#uids
   * @return uid
  **/
  @ApiModelProperty(value = "UID of the referent. More info: https://kubernetes.io/docs/concepts/overview/working-with-objects/names/#uids")


  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommonClusterObjectReference commonClusterObjectReference = (CommonClusterObjectReference) o;
    return Objects.equals(this.apiVersion, commonClusterObjectReference.apiVersion) &&
        Objects.equals(this.cluster, commonClusterObjectReference.cluster) &&
        Objects.equals(this.creator, commonClusterObjectReference.creator) &&
        Objects.equals(this.fieldPath, commonClusterObjectReference.fieldPath) &&
        Objects.equals(this.kind, commonClusterObjectReference.kind) &&
        Objects.equals(this.name, commonClusterObjectReference.name) &&
        Objects.equals(this.namespace, commonClusterObjectReference.namespace) &&
        Objects.equals(this.resourceVersion, commonClusterObjectReference.resourceVersion) &&
        Objects.equals(this.uid, commonClusterObjectReference.uid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(apiVersion, cluster, creator, fieldPath, kind, name, namespace, resourceVersion, uid);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommonClusterObjectReference {\n");
    
    sb.append("    apiVersion: ").append(toIndentedString(apiVersion)).append("\n");
    sb.append("    cluster: ").append(toIndentedString(cluster)).append("\n");
    sb.append("    creator: ").append(toIndentedString(creator)).append("\n");
    sb.append("    fieldPath: ").append(toIndentedString(fieldPath)).append("\n");
    sb.append("    kind: ").append(toIndentedString(kind)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    namespace: ").append(toIndentedString(namespace)).append("\n");
    sb.append("    resourceVersion: ").append(toIndentedString(resourceVersion)).append("\n");
    sb.append("    uid: ").append(toIndentedString(uid)).append("\n");
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

