package io.prismacloud.iac.commons.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import java.util.UUID;
import javax.validation.Valid;

/**
 * IacPolicyViolation
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-10-09T17:48:15.906480+05:30[Asia/Kolkata]")

public class IacPolicyViolation   {
  @JsonProperty("id")
  private UUID id;

  @JsonProperty("attributes")
  private IacPolicyViolationAttributes attributes;

  public IacPolicyViolation id(UUID id) {
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

  public IacPolicyViolation attributes(IacPolicyViolationAttributes attributes) {
    this.attributes = attributes;
    return this;
  }

  /**
   * Get attributes
   * @return attributes
  */
  @ApiModelProperty(value = "")

  @Valid

  public IacPolicyViolationAttributes getAttributes() {
    return attributes;
  }

  public void setAttributes(IacPolicyViolationAttributes attributes) {
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
    IacPolicyViolation iacPolicyViolation = (IacPolicyViolation) o;
    return Objects.equals(this.id, iacPolicyViolation.id) &&
        Objects.equals(this.attributes, iacPolicyViolation.attributes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, attributes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IacPolicyViolation {\n");

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

