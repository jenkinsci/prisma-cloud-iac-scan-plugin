package io.prismacloud.iac.commons.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;

/**
 * IacMatchedPoliciesSummary
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-10-09T17:48:15.906480+05:30[Asia/Kolkata]")

public class IacMatchedPoliciesSummary   {
  @JsonProperty("high")
  private Long high;

  @JsonProperty("medium")
  private Long medium;

  @JsonProperty("low")
  private Long low;

  public IacMatchedPoliciesSummary high(Long high) {
    this.high = high;
    return this;
  }

  /**
   * Number of high severity policies matched
   * @return high
  */
  @ApiModelProperty(value = "Number of high severity policies matched")


  public Long getHigh() {
    return high;
  }

  public void setHigh(Long high) {
    this.high = high;
  }

  public IacMatchedPoliciesSummary medium(Long medium) {
    this.medium = medium;
    return this;
  }

  /**
   * Number of medium severity policies matched
   * @return medium
  */
  @ApiModelProperty(value = "Number of medium severity policies matched")


  public Long getMedium() {
    return medium;
  }

  public void setMedium(Long medium) {
    this.medium = medium;
  }

  public IacMatchedPoliciesSummary low(Long low) {
    this.low = low;
    return this;
  }

  /**
   * Number of low severity policies matched
   * @return low
  */
  @ApiModelProperty(value = "Number of low severity policies matched")


  public Long getLow() {
    return low;
  }

  public void setLow(Long low) {
    this.low = low;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IacMatchedPoliciesSummary iacMatchedPoliciesSummary = (IacMatchedPoliciesSummary) o;
    return Objects.equals(this.high, iacMatchedPoliciesSummary.high) &&
        Objects.equals(this.medium, iacMatchedPoliciesSummary.medium) &&
        Objects.equals(this.low, iacMatchedPoliciesSummary.low);
  }

  @Override
  public int hashCode() {
    return Objects.hash(high, medium, low);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IacMatchedPoliciesSummary {\n");

    sb.append("    high: ").append(toIndentedString(high)).append("\n");
    sb.append("    medium: ").append(toIndentedString(medium)).append("\n");
    sb.append("    low: ").append(toIndentedString(low)).append("\n");
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

