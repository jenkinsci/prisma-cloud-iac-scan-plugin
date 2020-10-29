package io.prismacloud.iac.commons.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;

/**
 * JsonApiModelScanDetailsMeta
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-10-09T17:48:15.906480+05:30[Asia/Kolkata]")

public class JsonApiModelScanDetailsMeta   {
  @JsonProperty("matchedPoliciesSummary")
  private IacMatchedPoliciesSummary matchedPoliciesSummary;

  @JsonProperty("errorDetails")
  @Valid
  private List<IacApiError> errorDetails = null;

  public JsonApiModelScanDetailsMeta matchedPoliciesSummary(IacMatchedPoliciesSummary matchedPoliciesSummary) {
    this.matchedPoliciesSummary = matchedPoliciesSummary;
    return this;
  }

  /**
   * Get matchedPoliciesSummary
   * @return matchedPoliciesSummary
  */
  @ApiModelProperty(value = "")

  @Valid

  public IacMatchedPoliciesSummary getMatchedPoliciesSummary() {
    return matchedPoliciesSummary;
  }

  public void setMatchedPoliciesSummary(IacMatchedPoliciesSummary matchedPoliciesSummary) {
    this.matchedPoliciesSummary = matchedPoliciesSummary;
  }

  public JsonApiModelScanDetailsMeta errorDetails(List<IacApiError> errorDetails) {
    this.errorDetails = errorDetails;
    return this;
  }

  public JsonApiModelScanDetailsMeta addErrorDetailsItem(IacApiError errorDetailsItem) {
    if (this.errorDetails == null) {
      this.errorDetails = new ArrayList<>();
    }
    this.errorDetails.add(errorDetailsItem);
    return this;
  }

  /**
   * Get errorDetails
   * @return errorDetails
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<IacApiError> getErrorDetails() {
    return errorDetails;
  }

  public void setErrorDetails(List<IacApiError> errorDetails) {
    this.errorDetails = errorDetails;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JsonApiModelScanDetailsMeta jsonApiModelScanDetailsMeta = (JsonApiModelScanDetailsMeta) o;
    return Objects.equals(this.matchedPoliciesSummary, jsonApiModelScanDetailsMeta.matchedPoliciesSummary) &&
        Objects.equals(this.errorDetails, jsonApiModelScanDetailsMeta.errorDetails);
  }

  @Override
  public int hashCode() {
    return Objects.hash(matchedPoliciesSummary, errorDetails);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JsonApiModelScanDetailsMeta {\n");

    sb.append("    matchedPoliciesSummary: ").append(toIndentedString(matchedPoliciesSummary)).append("\n");
    sb.append("    errorDetails: ").append(toIndentedString(errorDetails)).append("\n");
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

