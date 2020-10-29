package io.prismacloud.iac.commons.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.validation.Valid;

/**
 * IacTemplateParameters
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-10-09T17:48:15.906480+05:30[Asia/Kolkata]")

public class IacTemplateParameters   {
  @JsonProperty("variables")
  @Valid
  private Map<String, String> variables = null;

  @JsonProperty("variableFiles")
  @Valid
  private List<String> variableFiles = null;

  @JsonProperty("policyIdFilters")
  @Valid
  private List<String> policyIdFilters = null;

  @JsonProperty("files")
  @Valid
  private List<String> files = null;

  @JsonProperty("folders")
  @Valid
  private List<String> folders = null;

  public IacTemplateParameters variables(Map<String, String> variables) {
    this.variables = variables;
    return this;
  }

  public IacTemplateParameters putVariablesItem(String key, String variablesItem) {
    if (this.variables == null) {
      this.variables = new HashMap<>();
    }
    this.variables.put(key, variablesItem);
    return this;
  }

  /**
   * Template variables and values list to be used for evaluation
   * @return variables
  */
  @ApiModelProperty(example = "{\"region\":\"us-east-1\",\"image_id\":\"amzn2-ami-hvm-2.0\"}", value = "Template variables and values list to be used for evaluation")


  public Map<String, String> getVariables() {
    return variables;
  }

  public void setVariables(Map<String, String> variables) {
    this.variables = variables;
  }

  public IacTemplateParameters variableFiles(List<String> variableFiles) {
    this.variableFiles = variableFiles;
    return this;
  }

  public IacTemplateParameters addVariableFilesItem(String variableFilesItem) {
    if (this.variableFiles == null) {
      this.variableFiles = new ArrayList<>();
    }
    this.variableFiles.add(variableFilesItem);
    return this;
  }

  /**
   * Template variable files inside the template package to be used for evaluation
   * @return variableFiles
  */
  @ApiModelProperty(example = "[\"./dev.tfvars\",\"./us/qa.tfvars\"]", value = "Template variable files inside the template package to be used for evaluation")


  public List<String> getVariableFiles() {
    return variableFiles;
  }

  public void setVariableFiles(List<String> variableFiles) {
    this.variableFiles = variableFiles;
  }

  public IacTemplateParameters policyIdFilters(List<String> policyIdFilters) {
    this.policyIdFilters = policyIdFilters;
    return this;
  }

  public IacTemplateParameters addPolicyIdFiltersItem(String policyIdFiltersItem) {
    if (this.policyIdFilters == null) {
      this.policyIdFilters = new ArrayList<>();
    }
    this.policyIdFilters.add(policyIdFiltersItem);
    return this;
  }

  /**
   * If list is populated, IaC scan service will evaluate with only policies that have IDs in this list
   * @return policyIdFilters
  */
  @ApiModelProperty(value = "If list is populated, IaC scan service will evaluate with only policies that have IDs in this list")


  public List<String> getPolicyIdFilters() {
    return policyIdFilters;
  }

  public void setPolicyIdFilters(List<String> policyIdFilters) {
    this.policyIdFilters = policyIdFilters;
  }

  public IacTemplateParameters files(List<String> files) {
    this.files = files;
    return this;
  }

  public IacTemplateParameters addFilesItem(String filesItem) {
    if (this.files == null) {
      this.files = new ArrayList<>();
    }
    this.files.add(filesItem);
    return this;
  }

  /**
   * If list is populated, IaC scan service will evaluate only files in this list
   * @return files
  */
  @ApiModelProperty(example = "[\"./dev/auto_scale.tf\",\"./dev/app.tf\"]", value = "If list is populated, IaC scan service will evaluate only files in this list")


  public List<String> getFiles() {
    return files;
  }

  public void setFiles(List<String> files) {
    this.files = files;
  }

  public IacTemplateParameters folders(List<String> folders) {
    this.folders = folders;
    return this;
  }

  public IacTemplateParameters addFoldersItem(String foldersItem) {
    if (this.folders == null) {
      this.folders = new ArrayList<>();
    }
    this.folders.add(foldersItem);
    return this;
  }

  /**
   * If list is populated, IaC scan service evaluates only folders in the list
   * @return folders
  */
  @ApiModelProperty(example = "[\"./dev\",\"./under_develop\",\"./modules/dev\"]", value = "If list is populated, IaC scan service evaluates only folders in the list")


  public List<String> getFolders() {
    return folders;
  }

  public void setFolders(List<String> folders) {
    this.folders = folders;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IacTemplateParameters iacTemplateParameters = (IacTemplateParameters) o;
    return Objects.equals(this.variables, iacTemplateParameters.variables) &&
        Objects.equals(this.variableFiles, iacTemplateParameters.variableFiles) &&
        Objects.equals(this.policyIdFilters, iacTemplateParameters.policyIdFilters) &&
        Objects.equals(this.files, iacTemplateParameters.files) &&
        Objects.equals(this.folders, iacTemplateParameters.folders);
  }

  @Override
  public int hashCode() {
    return Objects.hash(variables, variableFiles, policyIdFilters, files, folders);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IacTemplateParameters {\n");

    sb.append("    variables: ").append(toIndentedString(variables)).append("\n");
    sb.append("    variableFiles: ").append(toIndentedString(variableFiles)).append("\n");
    sb.append("    policyIdFilters: ").append(toIndentedString(policyIdFilters)).append("\n");
    sb.append("    files: ").append(toIndentedString(files)).append("\n");
    sb.append("    folders: ").append(toIndentedString(folders)).append("\n");
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

