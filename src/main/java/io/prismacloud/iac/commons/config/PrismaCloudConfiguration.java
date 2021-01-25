package io.prismacloud.iac.commons.config;

import io.prismacloud.iac.commons.model.IacTemplateParameters;

import java.util.Map;

/**
 * This class holds the configuration for the prisma cloud settings.
 */
public class PrismaCloudConfiguration {

  private String accessKey;
  private String secretKey;
  private String authUrl;
  private String scanUrl;
  private String templateType;
  private String templateVersion;
  private String assetName;
  private String tags;
  private String buildNumber;
  private String jobName;
  private String currentUserName;
  private String assetType;
  private int high;
  private int medium;
  private int low;
  private String operator;
  private Map<String, String> configFileTags;
  private IacTemplateParameters iacTemplateParameters;

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public int getHigh() {
    return high;
  }

  public void setHigh(int high) {
    this.high = high;
  }

  public int getMedium() {
    return medium;
  }

  public void setMedium(int medium) {
    this.medium = medium;
  }

  public int getLow() {
    return low;
  }

  public void setLow(int low) {
    this.low = low;
  }

  public String getAssetType() {
    return assetType;
  }

  public void setAssetType(String assetType) {
    this.assetType = assetType;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public String getAuthUrl() {
    return authUrl;
  }

  public String getScanUrl() { return scanUrl; }

  public String getAccessKey() {
    return accessKey;
  }

  public void setAccessKey(String accessKey) {
    this.accessKey = accessKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public void setAuthUrl(String authUrl) {
    this.authUrl = authUrl;
  }

  public void setScanUrl(String scanUrl) {
    this.scanUrl = scanUrl;
  }

  public String getTemplateType() { return templateType; }

  public void setTemplateType(String templateType) { this.templateType = templateType; }

  public String getTemplateVersion() { return templateVersion; }

  public void setTemplateVersion(String templateVersion) { this.templateVersion = templateVersion; }

  public String getAssetName() { return assetName; }

  public void setAssetName(String assetName) { this.assetName = assetName; }

  public String getTags() { return tags; }

  public void setTags(String tags) { this.tags = tags; }

  public String getBuildNumber() { return buildNumber; }

  public void setBuildNumber(String buildNumber) { this.buildNumber = buildNumber;}

  public String getJobName() { return jobName; }

  public void setJobName(String jobName) { this.jobName = jobName; }

  public String getCurrentUserName() { return currentUserName; }

  public void setCurrentUserName(String currentUserName) { this.currentUserName = currentUserName; }

  public Map<String, String> getConfigFileTags() {
    return configFileTags;
  }

  public void setConfigFileTags(Map<String, String> configFileTags) {
    this.configFileTags = configFileTags;
  }

  public IacTemplateParameters getIacTemplateParameters() { return iacTemplateParameters; }

  public void setIacTemplateParameters(IacTemplateParameters iacTemplateParameters) { this.iacTemplateParameters = iacTemplateParameters; }
}
