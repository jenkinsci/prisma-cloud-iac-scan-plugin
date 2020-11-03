package io.prismacloud.iac.commons.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * JsonApiModelAsyncScanRequestData
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-10-09T17:48:15.906480+05:30[Asia/Kolkata]")

public class JsonApiModelAsyncScanRequestData   {
  @JsonProperty("type")
  private String type;

  @JsonProperty("attributes")
  private JsonApiModelAsyncScanRequestDataAttributes attributes;

  public JsonApiModelAsyncScanRequestData type(String type) {
    this.type = type;
    return this;
  }

  /**
   * (Optional) {json.api} Any preferred value. For reference only.
   * @return type
  */
  @ApiModelProperty(example = "my-scan", value = "(Optional) {json.api} Any preferred value. For reference only.")


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public JsonApiModelAsyncScanRequestData attributes(JsonApiModelAsyncScanRequestDataAttributes attributes) {
    this.attributes = attributes;
    return this;
  }

  /**
   * Get attributes
   * @return attributes
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public JsonApiModelAsyncScanRequestDataAttributes getAttributes() {
    return attributes;
  }

  public void setAttributes(JsonApiModelAsyncScanRequestDataAttributes attributes) {
    this.attributes = attributes;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JsonApiModelAsyncScanRequestData jsonApiModelAsyncScanRequestData = (JsonApiModelAsyncScanRequestData) o;
    return Objects.equals(this.type, jsonApiModelAsyncScanRequestData.type) &&
        Objects.equals(this.attributes, jsonApiModelAsyncScanRequestData.attributes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, attributes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JsonApiModelAsyncScanRequestData {\n");

    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    attributes: ").append(toIndentedString(attributes)).append("\n");
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

