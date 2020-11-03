package io.prismacloud.iac.commons.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * JsonApiModelScanDetails
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-10-09T17:48:15.906480+05:30[Asia/Kolkata]")

public class JsonApiModelScanDetails   {
  @JsonProperty("meta")
  private JsonApiModelScanDetailsMeta meta;

  @JsonProperty("data")
  @Valid
  private List<IacPolicyViolation> data = new ArrayList<>();

  public JsonApiModelScanDetails meta(JsonApiModelScanDetailsMeta meta) {
    this.meta = meta;
    return this;
  }

  /**
   * Get meta
   * @return meta
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public JsonApiModelScanDetailsMeta getMeta() {
    return meta;
  }

  public void setMeta(JsonApiModelScanDetailsMeta meta) {
    this.meta = meta;
  }

  public JsonApiModelScanDetails data(List<IacPolicyViolation> data) {
    this.data = data;
    return this;
  }

  public JsonApiModelScanDetails addDataItem(IacPolicyViolation dataItem) {
    this.data.add(dataItem);
    return this;
  }

  /**
   * Get data
   * @return data
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<IacPolicyViolation> getData() {
    return data;
  }

  public void setData(List<IacPolicyViolation> data) {
    this.data = data;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JsonApiModelScanDetails jsonApiModelScanDetails = (JsonApiModelScanDetails) o;
    return Objects.equals(this.meta, jsonApiModelScanDetails.meta) &&
        Objects.equals(this.data, jsonApiModelScanDetails.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(meta, data);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JsonApiModelScanDetails {\n");

    sb.append("    meta: ").append(toIndentedString(meta)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
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

