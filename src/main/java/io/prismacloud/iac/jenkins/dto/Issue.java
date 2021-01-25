package io.prismacloud.iac.jenkins.dto;

import java.util.List;

public class Issue {
  private String severity;
  private String name;
  private List<String> files;
  private String rule;
  private String desc;
  private String docUrl;

  public String getSeverity() {
    return severity;
  }

  public void setSeverity(String severity) {
    this.severity = severity;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRule() {
    return rule;
  }

  public void setRule(String rule) {
    this.rule = rule;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getDocUrl() { return docUrl; }

  public void setDocUrl(String docUrl) { this.docUrl = docUrl; }

  public List<String> getFiles() {
    return files;
  }

  public void setFiles(List<String> files) {
    this.files = files;
  }
}
