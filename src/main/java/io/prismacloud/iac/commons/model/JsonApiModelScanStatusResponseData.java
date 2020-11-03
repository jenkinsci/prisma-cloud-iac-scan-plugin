package io.prismacloud.iac.commons.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import java.util.UUID;
import javax.validation.Valid;

/**
 * JsonApiModelScanStatusResponseData
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-10-09T17:48:15.906480+05:30[Asia/Kolkata]")

public class JsonApiModelScanStatusResponseData   {
  @JsonProperty("id")
  private UUID id;

  @JsonProperty("attributes")
  private JsonApiModelScanStatusResponseDataAttributes attributes;

  public JsonApiModelScanStatusResponseData id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @ApiModelProperty(value = "")

  @Valid

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public JsonApiModelScanStatusResponseData attributes(JsonApiModelScanStatusResponseDataAttributes attributes) {
    this.attributes = attributes;
    return this;
  }

  /**
   * Get attributes
   * @return attributes
  */
  @ApiModelProperty(value = "")

  @Valid

  public JsonApiModelScanStatusResponseDataAttributes getAttributes() {
    return attributes;
  }

  public void setAttributes(JsonApiModelScanStatusResponseDataAttributes attributes) {
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
    JsonApiModelScanStatusResponseData jsonApiModelScanStatusResponseData = (JsonApiModelScanStatusResponseData) o;
    return Objects.equals(this.id, jsonApiModelScanStatusResponseData.id) &&
        Objects.equals(this.attributes, jsonApiModelScanStatusResponseData.attributes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, attributes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JsonApiModelScanStatusResponseData {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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

