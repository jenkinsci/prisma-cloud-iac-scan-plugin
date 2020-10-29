package io.prismacloud.iac.commons.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * IacApiErrorResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-10-09T17:48:15.906480+05:30[Asia/Kolkata]")

public class IacApiErrorResponse   {
  @JsonProperty("errors")
  @Valid
  private List<IacApiError> errors = new ArrayList<>();

  public IacApiErrorResponse errors(List<IacApiError> errors) {
    this.errors = errors;
    return this;
  }

  public IacApiErrorResponse addErrorsItem(IacApiError errorsItem) {
    this.errors.add(errorsItem);
    return this;
  }

  /**
   * Get errors
   * @return errors
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<IacApiError> getErrors() {
    return errors;
  }

  public void setErrors(List<IacApiError> errors) {
    this.errors = errors;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IacApiErrorResponse iacApiErrorResponse = (IacApiErrorResponse) o;
    return Objects.equals(this.errors, iacApiErrorResponse.errors);
  }

  @Override
  public int hashCode() {
    return Objects.hash(errors);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IacApiErrorResponse {\n");

    sb.append("    errors: ").append(toIndentedString(errors)).append("\n");
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

