package io.prismacloud.iac.commons.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The levels of policy severity [high, medium, low]
 */
public enum IacPolicySeverity {
  
  HIGH("high"),
  
  MEDIUM("medium"),
  
  LOW("low");

  private String value;

  IacPolicySeverity(String value) {
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
  public static IacPolicySeverity fromValue(String value) {
    for (IacPolicySeverity b : IacPolicySeverity.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

