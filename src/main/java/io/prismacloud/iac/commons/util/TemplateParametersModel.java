package io.prismacloud.iac.commons.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;


import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TemplateParametersModel {

    private Map<String, String> variables = null;

    private List<String> variableFiles = null;

    private List<String> policyIdFilters = null;

    private List<String> files = null;

    private List<String> folders = null;

    private Boolean scanPlanFilesOnly = false;

    public Map<String, String> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, String> variables) {
        this.variables = variables;
    }

    public List<String> getVariableFiles() {
        return variableFiles;
    }

    public void setVariableFiles(List<String> variableFiles) {
        this.variableFiles = variableFiles;
    }

    public List<String> getPolicyIdFilters() {
        return policyIdFilters;
    }

    public void setPolicyIdFilters(List<String> policyIdFilters) {
        this.policyIdFilters = policyIdFilters;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public List<String> getFolders() {
        return folders;
    }

    public void setFolders(List<String> folders) {
        this.folders = folders;
    }

    public Boolean getScanPlanFilesOnly() {
        return scanPlanFilesOnly;
    }

    public void setScanPlanFilesOnly(Boolean scanPlanFilesOnly) {
        this.scanPlanFilesOnly = scanPlanFilesOnly;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TemplateParametersModel templateParameters = (TemplateParametersModel) o;
        return Objects.equals(this.variables, templateParameters.variables) &&
                Objects.equals(this.variableFiles, templateParameters.variableFiles) &&
                Objects.equals(this.policyIdFilters, templateParameters.policyIdFilters) &&
                Objects.equals(this.files, templateParameters.files) &&
                Objects.equals(this.folders, templateParameters.folders) &&
                Objects.equals(this.scanPlanFilesOnly, templateParameters.scanPlanFilesOnly);
    }


    @Override
    public int hashCode() {
        return Objects.hash(variables, variableFiles, policyIdFilters, files, folders, scanPlanFilesOnly);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TemplateParametersModel {\n");

        sb.append("    variables: ").append(toIndentedString(variables)).append("\n");
        sb.append("    variableFiles: ").append(toIndentedString(variableFiles)).append("\n");
        sb.append("    policyIdFilters: ").append(toIndentedString(policyIdFilters)).append("\n");
        sb.append("    files: ").append(toIndentedString(files)).append("\n");
        sb.append("    folders: ").append(toIndentedString(folders)).append("\n");
        sb.append("    scanPlanFilesOnly: ").append(toIndentedString(scanPlanFilesOnly)).append("\n");
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
