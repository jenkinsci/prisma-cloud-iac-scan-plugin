package io.prismacloud.iac.commons.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.validation.Valid;

/**
 * IacPolicyViolationAttributes
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-10-09T17:48:15.906480+05:30[Asia/Kolkata]")

public class IacPolicyViolationAttributes   {
  @JsonProperty("severity")
  private IacPolicySeverity severity;

  @JsonProperty("name")
  private String name;

  @JsonProperty("rule")
  private String rule;

  @JsonProperty("desc")
  private String desc;

  @JsonProperty("files")
  @Valid
  private List<String> files = null;

  @JsonProperty("policyId")
  private UUID policyId;

  public IacPolicyViolationAttributes severity(IacPolicySeverity severity) {
    this.severity = severity;
    return this;
  }

  /**
   * Get severity
   * @return severity
  */
  @ApiModelProperty(value = "")

  @Valid

  public IacPolicySeverity getSeverity() {
    return severity;
  }

  public void setSeverity(IacPolicySeverity severity) {
    this.severity = severity;
  }

  public IacPolicyViolationAttributes name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */
  @ApiModelProperty(value = "")


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public IacPolicyViolationAttributes rule(String rule) {
    this.rule = rule;
    return this;
  }

  /**
   * Get rule
   * @return rule
  */
  @ApiModelProperty(value = "")


  public String getRule() {
    return rule;
  }

  public void setRule(String rule) {
    this.rule = rule;
  }

  public IacPolicyViolationAttributes desc(String desc) {
    this.desc = desc;
    return this;
  }

  /**
   * Get desc
   * @return desc
  */
  @ApiModelProperty(value = "")


  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public IacPolicyViolationAttributes files(List<String> files) {
    this.files = files;
    return this;
  }

  public IacPolicyViolationAttributes addFilesItem(String filesItem) {
    if (this.files == null) {
      this.files = new ArrayList<>();
    }
    this.files.add(filesItem);
    return this;
  }

  /**
   * Get files
   * @return files
  */
  @ApiModelProperty(value = "")


  public List<String> getFiles() {
    return files;
  }

  public void setFiles(List<String> files) {
    this.files = files;
  }

  public IacPolicyViolationAttributes policyId(UUID policyId) {
    this.policyId = policyId;
    return this;
  }

  /**
   * Get policyId
   * @return policyId
  */
  @ApiModelProperty(value = "")

  @Valid

  public UUID getPolicyId() {
    return policyId;
  }

  public void setPolicyId(UUID policyId) {
    this.policyId = policyId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IacPolicyViolationAttributes iacPolicyViolationAttributes = (IacPolicyViolationAttributes) o;
    return Objects.equals(this.severity, iacPolicyViolationAttributes.severity) &&
        Objects.equals(this.name, iacPolicyViolationAttributes.name) &&
        Objects.equals(this.rule, iacPolicyViolationAttributes.rule) &&
        Objects.equals(this.desc, iacPolicyViolationAttributes.desc) &&
        Objects.equals(this.files, iacPolicyViolationAttributes.files) &&
        Objects.equals(this.policyId, iacPolicyViolationAttributes.policyId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(severity, name, rule, desc, files, policyId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IacPolicyViolationAttributes {\n");

    sb.append("    severity: ").append(toIndentedString(severity)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    rule: ").append(toIndentedString(rule)).append("\n");
    sb.append("    desc: ").append(toIndentedString(desc)).append("\n");
    sb.append("    files: ").append(toIndentedString(files)).append("\n");
    sb.append("    policyId: ").append(toIndentedString(policyId)).append("\n");
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

