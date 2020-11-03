package io.prismacloud.iac.commons.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import java.util.UUID;
import javax.validation.Valid;

/**
 * JsonApiModelAsyncScanSessionData
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-10-09T17:48:15.906480+05:30[Asia/Kolkata]")

public class JsonApiModelAsyncScanSessionData   {
  @JsonProperty("id")
  private UUID id;

  @JsonProperty("links")
  private JsonApiModelAsyncScanSessionDataLinks links;

  public JsonApiModelAsyncScanSessionData id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * The scan UUID
   * @return id
  */
  @ApiModelProperty(value = "The scan UUID")

  @Valid

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public JsonApiModelAsyncScanSessionData links(JsonApiModelAsyncScanSessionDataLinks links) {
    this.links = links;
    return this;
  }

  /**
   * Get links
   * @return links
  */
  @ApiModelProperty(value = "")

  @Valid

  public JsonApiModelAsyncScanSessionDataLinks getLinks() {
    return links;
  }

  public void setLinks(JsonApiModelAsyncScanSessionDataLinks links) {
    this.links = links;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JsonApiModelAsyncScanSessionData jsonApiModelAsyncScanSessionData = (JsonApiModelAsyncScanSessionData) o;
    return Objects.equals(this.id, jsonApiModelAsyncScanSessionData.id) &&
        Objects.equals(this.links, jsonApiModelAsyncScanSessionData.links);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, links);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JsonApiModelAsyncScanSessionData {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
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

