package io.prismacloud.iac.commons.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * IacApiError
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-10-09T17:48:15.906480+05:30[Asia/Kolkata]")

public class IacApiError   {
  @JsonProperty("status")
  private String status;

  @JsonProperty("code")
  private String code;

  @JsonProperty("detail")
  private String detail;

  @JsonProperty("source")
  private String source;

  public IacApiError status(String status) {
    this.status = status;
    return this;
  }

  /**
   * HTTP status code
   * @return status
  */
  @ApiModelProperty(required = true, value = "HTTP status code")
  @NotNull


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public IacApiError code(String code) {
    this.code = code;
    return this;
  }

  /**
   * Application specific error code
   * @return code
  */
  @ApiModelProperty(example = "405", value = "Application specific error code")


  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public IacApiError detail(String detail) {
    this.detail = detail;
    return this;
  }

  /**
   * Detailed description of the error
   * @return detail
  */
  @ApiModelProperty(example = "Invalid template file provided", required = true, value = "Detailed description of the error")
  @NotNull


  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public IacApiError source(String source) {
    this.source = source;
    return this;
  }

  /**
   * Indicates the part of the request document that caused the error
   * @return source
  */
  @ApiModelProperty(example = "Scan-worker-service", value = "Indicates the part of the request document that caused the error")


  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IacApiError iacApiError = (IacApiError) o;
    return Objects.equals(this.status, iacApiError.status) &&
        Objects.equals(this.code, iacApiError.code) &&
        Objects.equals(this.detail, iacApiError.detail) &&
        Objects.equals(this.source, iacApiError.source);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, code, detail, source);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IacApiError {\n");

    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    detail: ").append(toIndentedString(detail)).append("\n");
    sb.append("    source: ").append(toIndentedString(source)).append("\n");
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

