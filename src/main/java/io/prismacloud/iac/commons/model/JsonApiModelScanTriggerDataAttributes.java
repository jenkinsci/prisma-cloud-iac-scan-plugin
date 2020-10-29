package io.prismacloud.iac.commons.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * JsonApiModelScanTriggerDataAttributes
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-10-09T17:48:15.906480+05:30[Asia/Kolkata]")

public class JsonApiModelScanTriggerDataAttributes   {
  @JsonProperty("templateType")
  private String templateType;

  @JsonProperty("templateVersion")
  private String templateVersion;

  @JsonProperty("templateParameters")
  private IacTemplateParameters templateParameters;

  public JsonApiModelScanTriggerDataAttributes templateType(String templateType) {
    this.templateType = templateType;
    return this;
  }

  /**
   * IaC template type. Supported types are cft, k8s, tf
   * @return templateType
  */
  @ApiModelProperty(example = "cft", required = true, value = "IaC template type. Supported types are cft, k8s, tf")
  @NotNull


  public String getTemplateType() {
    return templateType;
  }

  public void setTemplateType(String templateType) {
    this.templateType = templateType;
  }

  public JsonApiModelScanTriggerDataAttributes templateVersion(String templateVersion) {
    this.templateVersion = templateVersion;
    return this;
  }

  /**
   * (optional) Template version
   * @return templateVersion
  */
  @ApiModelProperty(example = "0.13", value = "(optional) Template version")


  public String getTemplateVersion() {
    return templateVersion;
  }

  public void setTemplateVersion(String templateVersion) {
    this.templateVersion = templateVersion;
  }

  public JsonApiModelScanTriggerDataAttributes templateParameters(IacTemplateParameters templateParameters) {
    this.templateParameters = templateParameters;
    return this;
  }

  /**
   * Get templateParameters
   * @return templateParameters
  */
  @ApiModelProperty(value = "")

  @Valid

  public IacTemplateParameters getTemplateParameters() {
    return templateParameters;
  }

  public void setTemplateParameters(IacTemplateParameters templateParameters) {
    this.templateParameters = templateParameters;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JsonApiModelScanTriggerDataAttributes jsonApiModelScanTriggerDataAttributes = (JsonApiModelScanTriggerDataAttributes) o;
    return Objects.equals(this.templateType, jsonApiModelScanTriggerDataAttributes.templateType) &&
        Objects.equals(this.templateVersion, jsonApiModelScanTriggerDataAttributes.templateVersion) &&
        Objects.equals(this.templateParameters, jsonApiModelScanTriggerDataAttributes.templateParameters);
  }

  @Override
  public int hashCode() {
    return Objects.hash(templateType, templateVersion, templateParameters);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JsonApiModelScanTriggerDataAttributes {\n");

    sb.append("    templateType: ").append(toIndentedString(templateType)).append("\n");
    sb.append("    templateVersion: ").append(toIndentedString(templateVersion)).append("\n");
    sb.append("    templateParameters: ").append(toIndentedString(templateParameters)).append("\n");
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

