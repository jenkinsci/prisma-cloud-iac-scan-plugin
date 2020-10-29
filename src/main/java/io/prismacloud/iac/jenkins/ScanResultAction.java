package io.prismacloud.iac.jenkins;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.prismacloud.util.JSONUtils;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import hudson.model.Run;
import io.prismacloud.iac.jenkins.dto.ErrorDetail;
import io.prismacloud.iac.jenkins.dto.Issue;
import io.prismacloud.iac.jenkins.dto.ScanResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import jenkins.model.RunAction2;
import org.apache.commons.collections.CollectionUtils;

public class ScanResultAction implements RunAction2 {

  private transient Run run;
  private List<Issue> scanResults;
  private String statusMessage ;
  private boolean apiError;
  private float high;
  private float medium;
  private float low;
  private String highPercentage;
  private String mediumPercentage;
  private String lowPercentage;
  private int total;
  private String status;
  private List<ErrorDetail> errorResponseList;

  private Map<String, String> severityMap;

  /**
   *
   * @param scanResultJsonFile
   * @param buildStatus
   * @param map
   */
  @SuppressFBWarnings({"UC_USELESS_OBJECT"})
  public ScanResultAction(String scanResultJsonFile, boolean buildStatus,
                          Map<String, String> map, boolean apiResponseError) {

    if (apiResponseError) {
      //Read main Response
      JsonReader reader = JSONUtils.parseJSONWitReader(scanResultJsonFile);
      JsonObject jsonObjectParent = JsonParser.parseReader(reader).getAsJsonObject();
      errorResponseList = new ArrayList<>();

      //Check Response error
      JsonElement jsonError = jsonObjectParent.get("errors");
      JsonElement jsonErr = jsonObjectParent.get("error");

      if (jsonError != null) {
        JsonArray jsonErrorArray = jsonError.getAsJsonArray();
        errorResponseList = getErrorResponse(jsonErrorArray);
      } else if (jsonErr != null) {
        ErrorDetail prismaError = new ErrorDetail();
        prismaError.setApiErrorStatus(jsonErr.toString().replace("\"", ""));
        prismaError.setApiErrorMessage(jsonObjectParent.get("message").toString().replace("\"", ""));
        errorResponseList.add(prismaError);
      } else {
        JsonElement jsonElementParent = jsonObjectParent.get("meta");
        JsonObject jsonObjectChild = jsonElementParent.getAsJsonObject();

        //Get Error details
        JsonElement jsonElementError = jsonObjectChild.get("errorDetails");
        JsonArray jsonArray = jsonElementError.getAsJsonArray();
        errorResponseList = getErrorResponse(jsonArray);
      }
      this.apiError = apiResponseError;
    } else {
      JsonReader reader = JSONUtils.parseJSONWitReader(scanResultJsonFile);
      JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

      if (jsonObject.has("meta")) {
        JsonObject summery = jsonObject.get("meta").getAsJsonObject();
        JsonElement summeryElement = summery.get("matchedPoliciesSummary");
        ScanResult scanResult = new ScanResult();
        JsonObject matchedPoliciesSummery = summeryElement.getAsJsonObject();
        high = Integer.parseInt(matchedPoliciesSummery.get("high").toString());
        medium = Integer.parseInt(matchedPoliciesSummery.get("medium").toString());
        low = Integer.parseInt(matchedPoliciesSummery.get("low").toString());


        total = (int) (high + medium + low);

        highPercentage = ((high / total) * 100) + "%";
        mediumPercentage = ((medium / total) * 100) + "%";
        lowPercentage = ((low / total) * 100) + "%";

        scanResult.setHighSeverity(high);
        scanResult.setHighPercentage(highPercentage);
        scanResult.setMediumSeverity(medium);
        scanResult.setMediumPercentage(mediumPercentage);
        scanResult.setLowSeverity(low);
        scanResult.setLowPercentage(lowPercentage);
        scanResult.setTotalIssues(total);

        //set build status
        if (buildStatus) {
          status = "Passed";
        } else {
          status = "Failed";
        }
        severityMap = map;
        severityMap.put("Status", status);

        JsonArray rulesMatchedJsonArray = jsonObject.get("data").getAsJsonArray();

        if (jsonObject.has("data")) {
          scanResults = new ArrayList<>();
          List<Issue> highSeverityIssueList = new ArrayList<>();
          List<Issue> mediunSeverityIssueList = new ArrayList<>();
          List<Issue> lowSeverityIssueList = new ArrayList<>();

          for (int index = 0; index < rulesMatchedJsonArray.size(); index++) {

            JsonObject jsonAttributes =
                rulesMatchedJsonArray.get(index).getAsJsonObject().get("attributes")
                    .getAsJsonObject();
            String severity = jsonAttributes.get("severity").getAsString();
            String name = jsonAttributes.get("name").getAsString();
            String file = getfileNames(jsonAttributes.get("files").getAsJsonArray());
            String rule = jsonAttributes.get("rule").getAsString();
            String desc = jsonAttributes.get("desc").getAsString();
            Issue issue = new Issue();
            issue.setSeverity(severity);
            issue.setName(name);
            issue.setFile(file);
            issue.setRule(rule);
            issue.setDesc(desc);

            if (severity.equalsIgnoreCase("high")) {
              highSeverityIssueList.add(issue);
            } else if (severity.equalsIgnoreCase("medium")) {
              mediunSeverityIssueList.add(issue);
            } else if (severity.equalsIgnoreCase("low")) {
              lowSeverityIssueList.add(issue);
            }
          }
          scanResults.addAll(highSeverityIssueList);
          scanResults.addAll(mediunSeverityIssueList);
          scanResults.addAll(lowSeverityIssueList);
        } else {
          this.statusMessage = "No Issues found in Prisma Cloud IAC Scan.";
        }
      }
    }
  }


  private List<ErrorDetail> getErrorResponse(JsonArray jsonArray) {
    Iterator<JsonElement> iterator = jsonArray.iterator();
    List<ErrorDetail> resultList = new ArrayList();
    while (iterator.hasNext()) {
      JsonElement jsonElement = iterator.next();
      JsonObject jsonObject = jsonElement.getAsJsonObject();
      JsonElement status = jsonObject.get("status");
      JsonElement detail = jsonObject.get("detail");
      ErrorDetail prismaError = new ErrorDetail();
      prismaError.setApiErrorStatus(status.toString().substring(1, status.toString().length() - 1));
      prismaError.setApiErrorMessage(detail.toString().substring(1, detail.toString().length() - 1));
      resultList.add(prismaError);
    }
     return resultList;
  }

  public boolean isApiError() { return apiError; }

  public String getStatusMessage() {
    return statusMessage;
  }


  public List<ErrorDetail> getErrorResponseList() {
    if (CollectionUtils.isNotEmpty(errorResponseList)) {
      return errorResponseList;
    }
    return Collections.emptyList();
  }

  public List<Issue> getScanResults() {
    if(CollectionUtils.isNotEmpty(scanResults)) {
      return scanResults;
    }
    return Collections.emptyList();
  }

  public int getTotal() {
    return total;
  }

  public int getHigh() { return  (int) high; }

  public int getMedium() {
    return (int)  medium;
  }

  public int getLow() {
    return (int)  low;
  }

  public String getHighPercentage() { return highPercentage; }

  public String getMediumPercentage() { return mediumPercentage; }

  public String getLowPercentage() { return lowPercentage; }

  public String getStatus() {
    return status;
  }

  public Map<String, String> getSeverityMap() {
    return severityMap;
  }

  @Override
  public String getIconFileName() {
    return "/plugin/prisma-cloud-iac-scan/icons/prismacloud-24x24.png";
  }

  @Override
  public String getDisplayName() {
    return "Prisma Cloud IaC Scan Report";
  }

  @Override
  public String getUrlName() {
    return "iac_scan_result";
  }

  @Override
  public void onAttached(Run<?, ?> run) {
    this.run = run;
  }

  @Override
  public void onLoad(Run<?, ?> run) {
    this.run = run;
  }

  public Run getRun() {
    return run;
  }

  private String getfileNames(JsonArray array) {
    List<String> files = new ArrayList<>();
    for (JsonElement jsonElement : array) {
      files.add(jsonElement.getAsString());
    }
    return String.join(",", files);
  }
}