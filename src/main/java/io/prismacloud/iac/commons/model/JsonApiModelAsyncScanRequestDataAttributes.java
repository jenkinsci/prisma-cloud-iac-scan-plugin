package io.prismacloud.iac.commons.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * JsonApiModelAsyncScanRequestDataAttributes
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-10-09T17:48:15.906480+05:30[Asia/Kolkata]")

public class JsonApiModelAsyncScanRequestDataAttributes   {
  @JsonProperty("assetName")
  private String assetName;

  @JsonProperty("assetType")
  private String assetType;

  @JsonProperty("tags")
  @Valid
  private Map<String, String> tags = null;

  @JsonProperty("scanAttributes")
  @Valid
  private Map<String, String> scanAttributes = null;

  @JsonProperty("failureCriteria")
  private JsonApiModelFailureCriteria failureCriteria;

  public JsonApiModelAsyncScanRequestDataAttributes assetName(String assetName) {
    this.assetName = assetName;
    return this;
  }

  /**
   * Registered asset name (255 character limit) that will appear as the resource name in the Prisma Cloud Devops Inventory
   * @return assetName
  */
  @ApiModelProperty(example = "my-asset", required = true, value = "Registered asset name (255 character limit) that will appear as the resource name in the Prisma Cloud Devops Inventory")
  @NotNull


  public String getAssetName() {
    return assetName;
  }

  public void setAssetName(String assetName) {
    this.assetName = assetName;
  }

  public JsonApiModelAsyncScanRequestDataAttributes assetType(String assetType) {
    this.assetType = assetType;
    return this;
  }

  /**
   * Supported asset types: * AzureDevOps: Azure DevOps Services * AWSCodePipeline: AWS CodePipeline * CircleCI: CircleCI project * GitHub: GitHub repo * GitLab: GitLab repo * GitLab-SCM: GitLab pipeline * IntelliJ: IntelliJ IDE plugin managed files * VSCode: VSCode plugin managed files * twistcli: Twistlock CLI attachment * Jenkins: Jenkins build server * BitbucketServer: Bitbucket server * BitbucketCloud: Bitbucket Cloud * IaC-API: direct IAC API attachment 
   * @return assetType
  */
  @ApiModelProperty(example = "AzureDevOps", required = true, value = "Supported asset types: * AzureDevOps: Azure DevOps Services * AWSCodePipeline: AWS CodePipeline * CircleCI: CircleCI project * GitHub: GitHub repo * GitLab: GitLab repo * GitLab-SCM: GitLab pipeline * IntelliJ: IntelliJ IDE plugin managed files * VSCode: VSCode plugin managed files * twistcli: Twistlock CLI attachment * Jenkins: Jenkins build server * BitbucketServer: Bitbucket server * BitbucketCloud: Bitbucket Cloud * IaC-API: direct IAC API attachment ")
  @NotNull


  public String getAssetType() {
    return assetType;
  }

  public void setAssetType(String assetType) {
    this.assetType = assetType;
  }

  public JsonApiModelAsyncScanRequestDataAttributes tags(Map<String, String> tags) {
    this.tags = tags;
    return this;
  }

  public JsonApiModelAsyncScanRequestDataAttributes putTagsItem(String key, String tagsItem) {
    if (this.tags == null) {
      this.tags = new HashMap<>();
    }
    this.tags.put(key, tagsItem);
    return this;
  }

  /**
   * Tags assigned to the asset or the scan job. Both tag key and value have 255 character limit.
   * @return tags
  */
  @ApiModelProperty(example = "{\"env\":\"dev\",\"region\":\"us-west-1\"}", value = "Tags assigned to the asset or the scan job. Both tag key and value have 255 character limit.")


  public Map<String, String> getTags() {
    return tags;
  }

  public void setTags(Map<String, String> tags) {
    this.tags = tags;
  }

  public JsonApiModelAsyncScanRequestDataAttributes scanAttributes(Map<String, String> scanAttributes) {
    this.scanAttributes = scanAttributes;
    return this;
  }

  public JsonApiModelAsyncScanRequestDataAttributes putScanAttributesItem(String key, String scanAttributesItem) {
    if (this.scanAttributes == null) {
      this.scanAttributes = new HashMap<>();
    }
    this.scanAttributes.put(key, scanAttributesItem);
    return this;
  }

  /**
   * Additonal attributes associated with the IaC scan job. The keys below should be used for better display: * buildNumber: For CI/CD, the build number associated with the IaC scan * projectName: For CI/CD, the repo or project name associated with the IaC scan * prName: For CI/CD, the pull/merge request name associated with the IaC scan * pipelineName: For CD pipeline, the pipeline name associated with the IaC scan * pipelineLambda: For CD pipeline, the lambda name associated with the IaC scan * pipelineJobId: For CD pipeline, the job ID associated with the IaC scan * pipelineStageName: For CD pipeline, the stage name associated with the IaC scan * pipelineActionName: For CD pipeline, the action name associated with the IaC scan 
   * @return scanAttributes
  */
  @ApiModelProperty(example = "{\"buildNumber\":999,\"projectName\":\"my-project\",\"prName\":\"SL-1234\",\"pipelineName\":\"PrismaCloudScan-Lamb\",\"pipelineLambda\":\"AWSCodePipeline-ng\",\"pipelineJobId\":\"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\"pipelineStageName\":\"SourceArtifact\",\"pipelineActionName\":\"SourceArtifact\"}", value = "Additonal attributes associated with the IaC scan job. The keys below should be used for better display: * buildNumber: For CI/CD, the build number associated with the IaC scan * projectName: For CI/CD, the repo or project name associated with the IaC scan * prName: For CI/CD, the pull/merge request name associated with the IaC scan * pipelineName: For CD pipeline, the pipeline name associated with the IaC scan * pipelineLambda: For CD pipeline, the lambda name associated with the IaC scan * pipelineJobId: For CD pipeline, the job ID associated with the IaC scan * pipelineStageName: For CD pipeline, the stage name associated with the IaC scan * pipelineActionName: For CD pipeline, the action name associated with the IaC scan ")


  public Map<String, String> getScanAttributes() {
    return scanAttributes;
  }

  public void setScanAttributes(Map<String, String> scanAttributes) {
    this.scanAttributes = scanAttributes;
  }

  public JsonApiModelAsyncScanRequestDataAttributes failureCriteria(JsonApiModelFailureCriteria failureCriteria) {
    this.failureCriteria = failureCriteria;
    return this;
  }

  /**
   * Get failureCriteria
   * @return failureCriteria
  */
  @ApiModelProperty(value = "")

  @Valid

  public JsonApiModelFailureCriteria getFailureCriteria() {
    return failureCriteria;
  }

  public void setFailureCriteria(JsonApiModelFailureCriteria failureCriteria) {
    this.failureCriteria = failureCriteria;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JsonApiModelAsyncScanRequestDataAttributes jsonApiModelAsyncScanRequestDataAttributes = (JsonApiModelAsyncScanRequestDataAttributes) o;
    return Objects.equals(this.assetName, jsonApiModelAsyncScanRequestDataAttributes.assetName) &&
        Objects.equals(this.assetType, jsonApiModelAsyncScanRequestDataAttributes.assetType) &&
        Objects.equals(this.tags, jsonApiModelAsyncScanRequestDataAttributes.tags) &&
        Objects.equals(this.scanAttributes, jsonApiModelAsyncScanRequestDataAttributes.scanAttributes) &&
        Objects.equals(this.failureCriteria, jsonApiModelAsyncScanRequestDataAttributes.failureCriteria);
  }

  @Override
  public int hashCode() {
    return Objects.hash(assetName, assetType, tags, scanAttributes, failureCriteria);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JsonApiModelAsyncScanRequestDataAttributes {\n");

    sb.append("    assetName: ").append(toIndentedString(assetName)).append("\n");
    sb.append("    assetType: ").append(toIndentedString(assetType)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
    sb.append("    scanAttributes: ").append(toIndentedString(scanAttributes)).append("\n");
    sb.append("    failureCriteria: ").append(toIndentedString(failureCriteria)).append("\n");
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

