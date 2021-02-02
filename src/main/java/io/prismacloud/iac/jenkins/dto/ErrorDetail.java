package io.prismacloud.iac.jenkins.dto;

import java.util.List;

public class ErrorDetail {
    private String apiErrorStatus;
    private List<String> apiErrorMessageList;


    public String getApiErrorStatus() {
        return apiErrorStatus;
    }

    public void setApiErrorStatus(String apiErrorStatus) {
        this.apiErrorStatus = apiErrorStatus;
    }

    public List<String> getApiErrorMessageList() {
        return apiErrorMessageList;
    }

    public void setApiErrorMessageList(List<String> apiErrorMessageList) {
        this.apiErrorMessageList = apiErrorMessageList;
    }


}
