package io.prismacloud.iac.commons.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;

/**
 * JsonApiModelAsyncScanSessionDataLinks
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-10-09T17:48:15.906480+05:30[Asia/Kolkata]")

public class JsonApiModelAsyncScanSessionDataLinks   {
  @JsonProperty("url")
  private String url;

  public JsonApiModelAsyncScanSessionDataLinks url(String url) {
    this.url = url;
    return this;
  }

  /**
   * The presigned URL for template data uploading (only HTTP PUT is supported)
   * @return url
  */
  @ApiModelProperty(value = "The presigned URL for template data uploading (only HTTP PUT is supported)")


  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JsonApiModelAsyncScanSessionDataLinks jsonApiModelAsyncScanSessionDataLinks = (JsonApiModelAsyncScanSessionDataLinks) o;
    return Objects.equals(this.url, jsonApiModelAsyncScanSessionDataLinks.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(url);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JsonApiModelAsyncScanSessionDataLinks {\n");

    sb.append("    url: ").append(toIndentedString(url)).append("\n");
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

