package io.prismacloud.iac.jenkins.dto;

import java.util.List;

public class ScanResult {
    String buildStatus;
    int totalIssues = 0;
    int highSeverity = 0;
    int mediumSeverity = 0;
    int lowSeverity = 0;
    String highPercentage;
    String mediumPercentage;
    String lowPercentage;

    List<Issue> issueList;

    public String getBuildStatus() {
        return buildStatus;
    }

    public void setBuildStatus(String buildStatus) {
        this.buildStatus = buildStatus;
    }

    public int getTotalIssues() {
        return totalIssues;
    }

    public void setTotalIssues(int totalIssues) {
        this.totalIssues = totalIssues;
    }

    public float getHighSeverity() {
        return highSeverity;
    }

    public void setHighSeverity(float highSeverity) {
        this.highSeverity = (int) highSeverity;
    }

    public float getMediumSeverity() {
        return mediumSeverity;
    }

    public void setMediumSeverity(float mediumSeverity) {
        this.mediumSeverity = (int) mediumSeverity;
    }

    public float getLowSeverity() {
        return lowSeverity;
    }

    public void setLowSeverity(float lowSeverity) {
        this.lowSeverity = (int) lowSeverity;
    }

    public List<Issue> getIssueList() {
        return issueList;
    }

    public void setIssueList(List<Issue> issueList) {
        this.issueList = issueList;
    }

    public String getHighPercentage() {
        return highPercentage;
    }

    public void setHighPercentage(String highPercentage) {
        this.highPercentage = highPercentage;
    }

    public String getMediumPercentage() {
        return mediumPercentage;
    }

    public void setMediumPercentage(String mediumPercentage) {
        this.mediumPercentage = mediumPercentage;
    }

    public String getLowPercentage() {
        return lowPercentage;
    }

    public void setLowPercentage(String lowPercentage) {
        this.lowPercentage = lowPercentage;
    }
}
