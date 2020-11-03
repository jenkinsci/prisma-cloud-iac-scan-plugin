package io.prismacloud.iac.commons.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The scan job status. Valid values are processing, error, passed, failed, failed_n_merged, failed_n_deployed.
 */
public enum JsonApiModelScanStatus {
  
  PROCESSING("processing"),
  
  ERROR("error"),
  
  PASSED("passed"),
  
  FAILED("failed"),
  
  FAILED_N_MERGED("failed_n_merged"),
  
  FAILED_N_DEPLOYED("failed_n_deployed");

  private String value;

  JsonApiModelScanStatus(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static JsonApiModelScanStatus fromValue(String value) {
    for (JsonApiModelScanStatus b : JsonApiModelScanStatus.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

