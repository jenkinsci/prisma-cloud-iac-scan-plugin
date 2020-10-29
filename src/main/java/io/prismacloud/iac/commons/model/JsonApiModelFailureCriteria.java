package io.prismacloud.iac.commons.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * Thresholds that define scan asset result failures after evaluation.
 */
@ApiModel(description = "Thresholds that define scan asset result failures after evaluation.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-10-09T17:48:15.906480+05:30[Asia/Kolkata]")

public class JsonApiModelFailureCriteria   {
  @JsonProperty("high")
  private Integer high;

  @JsonProperty("medium")
  private Integer medium;

  @JsonProperty("low")
  private Integer low;

  @JsonProperty("operator")
  private String operator;

  public JsonApiModelFailureCriteria high(Integer high) {
    this.high = high;
    return this;
  }

  /**
   * Threshold for the number of high severity violations that define an asset failure.
   * @return high
  */
  @ApiModelProperty(example = "1", required = true, value = "Threshold for the number of high severity violations that define an asset failure.")
  @NotNull


  public Integer getHigh() {
    return high;
  }

  public void setHigh(Integer high) {
    this.high = high;
  }

  public JsonApiModelFailureCriteria medium(Integer medium) {
    this.medium = medium;
    return this;
  }

  /**
   * Threshold for the number of medium severity violations that define an asset failure.
   * @return medium
  */
  @ApiModelProperty(example = "10", required = true, value = "Threshold for the number of medium severity violations that define an asset failure.")
  @NotNull


  public Integer getMedium() {
    return medium;
  }

  public void setMedium(Integer medium) {
    this.medium = medium;
  }

  public JsonApiModelFailureCriteria low(Integer low) {
    this.low = low;
    return this;
  }

  /**
   * Threshold for the number of low severity violations that define an asset failure.
   * @return low
  */
  @ApiModelProperty(example = "30", required = true, value = "Threshold for the number of low severity violations that define an asset failure.")
  @NotNull


  public Integer getLow() {
    return low;
  }

  public void setLow(Integer low) {
    this.low = low;
  }

  public JsonApiModelFailureCriteria operator(String operator) {
    this.operator = operator;
    return this;
  }

  /**
   * Logic operator on failures [and, or]
   * @return operator
  */
  @ApiModelProperty(example = "or", required = true, value = "Logic operator on failures [and, or]")
  @NotNull


  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JsonApiModelFailureCriteria jsonApiModelFailureCriteria = (JsonApiModelFailureCriteria) o;
    return Objects.equals(this.high, jsonApiModelFailureCriteria.high) &&
        Objects.equals(this.medium, jsonApiModelFailureCriteria.medium) &&
        Objects.equals(this.low, jsonApiModelFailureCriteria.low) &&
        Objects.equals(this.operator, jsonApiModelFailureCriteria.operator);
  }

  @Override
  public int hashCode() {
    return Objects.hash(high, medium, low, operator);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JsonApiModelFailureCriteria {\n");

    sb.append("    high: ").append(toIndentedString(high)).append("\n");
    sb.append("    medium: ").append(toIndentedString(medium)).append("\n");
    sb.append("    low: ").append(toIndentedString(low)).append("\n");
    sb.append("    operator: ").append(toIndentedString(operator)).append("\n");
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

