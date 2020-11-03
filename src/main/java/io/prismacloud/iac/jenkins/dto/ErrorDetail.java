package io.prismacloud.iac.jenkins.dto;

public class ErrorDetail {
  private String apiErrorMessage ;
  private String apiErrorStatus;

  public String getApiErrorMessage() { return apiErrorMessage; }

  public void setApiErrorMessage(String apiErrorMessage) { this.apiErrorMessage = apiErrorMessage; }

  public String getApiErrorStatus() { return apiErrorStatus; }

  public void setApiErrorStatus(String apiErrorStatus) { this.apiErrorStatus = apiErrorStatus; }
}
