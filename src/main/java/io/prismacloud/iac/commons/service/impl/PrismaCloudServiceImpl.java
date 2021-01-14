package io.prismacloud.iac.commons.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.prismacloud.iac.commons.config.PrismaCloudConfiguration;
import io.prismacloud.iac.commons.model.JsonApiModelFailureCriteria;
import io.prismacloud.iac.commons.util.ConfigYmlTagsUtil;
import io.prismacloud.iac.commons.util.JSONUtils;
import io.prismacloud.iac.commons.service.PrismaCloudService;
import io.prismacloud.iac.commons.model.IacTemplateParameters;
import io.prismacloud.iac.commons.model.JsonApiModelAsyncScanRequest;
import io.prismacloud.iac.commons.model.JsonApiModelAsyncScanRequestData;
import io.prismacloud.iac.commons.model.JsonApiModelAsyncScanRequestDataAttributes;
import io.prismacloud.iac.commons.model.JsonApiModelScanTrigger;
import io.prismacloud.iac.commons.model.JsonApiModelScanTriggerData;
import io.prismacloud.iac.commons.model.JsonApiModelScanTriggerDataAttributes;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sacumen (www.sacumen.com)
 * PrismaCloudServiceImpl is the implementation of the all services defined by PrismaCloudService
 */

public class PrismaCloudServiceImpl implements PrismaCloudService {

  Logger logger = LoggerFactory.getLogger(PrismaCloudServiceImpl.class);

  /**
   * This method returns the valid token using access_key and secret key.
   */
    @Override
    public String getAccessToken(PrismaCloudConfiguration prismaCloudConfiguration)
        throws IOException {
        logger.info("Entered into PrismaCloudServiceImpl.getAccessToken");
        return generateToken(prismaCloudConfiguration);
    }

  /**
   * This method scan the zip file and returns JSON as string.
   */
    @Override
    public String getScanDetails(PrismaCloudConfiguration prismaCloudConfiguration, String filePath)
        throws IOException, InterruptedException {
        logger.info("Entered into PrismaCloudServiceImpl.getScanDetails");
        return getScanResult(prismaCloudConfiguration, filePath);
    }

    /**
     * This method is used for API token generation.
     */
    @SuppressFBWarnings({"RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE"})
    private String generateToken(PrismaCloudConfiguration prismaCloudConfiguration)
        throws ParseException, IOException {
        logger.debug("Entered into PrismaCloudServiceImpl.generateToken");

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse authResponse = getJwtToken(client, prismaCloudConfiguration)) {
            int statusCode = authResponse.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                String responseBody = EntityUtils.toString(authResponse.getEntity());
                JsonReader reader = JSONUtils.parseJSONWitReader(responseBody);
                JsonParser jsonParser =new JsonParser();
                JsonObject responseJsonObject = jsonParser.parse(reader).getAsJsonObject();
                return responseJsonObject.get("token").getAsString();
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * Below method is used for API token generation
     */
    private CloseableHttpResponse getJwtToken(CloseableHttpClient client, PrismaCloudConfiguration prismaCloudConfiguration) throws IOException {
        logger.debug("Entered into PrismaCloudServiceImpl.getJwtToken");
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("username", prismaCloudConfiguration.getAccessKey());
        requestBody.addProperty("password", prismaCloudConfiguration.getSecretKey());
        StringEntity entity = new StringEntity(requestBody.toString());
        HttpPost httpPost = new HttpPost(prismaCloudConfiguration.getAuthUrl());
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("x-redlock-auth", null);
        httpPost.setEntity(entity);
        return client.execute(httpPost);
    }

    /**
     *Below method is used to get scan details from prisma clod API
     */
    @SuppressFBWarnings({"RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE"})
  public String getScanResult(PrismaCloudConfiguration prismaCloudConfiguration, String filePath)
      throws IOException, InterruptedException {
    String responseBody = "";
    String authToken = generateToken(prismaCloudConfiguration);
    String processingStatus = "processing";

    try (CloseableHttpClient client = HttpClients.createDefault(); CloseableHttpResponse scanUrlResponse = getPrismaCloudScanDetails(client, prismaCloudConfiguration, authToken)) {
      if (scanUrlResponse.getStatusLine().getStatusCode() == 200 || scanUrlResponse.getStatusLine().getStatusCode() == 201) {
        logger.info("Got the getPrismaCloudScanDetails");
        responseBody = EntityUtils.toString(scanUrlResponse.getEntity());
        JsonReader reader = JSONUtils.parseJSONWitReader(responseBody);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(reader).getAsJsonObject();
        JsonElement firstelement = jsonParser.parse(jsonObject.get("data").toString());
        JsonElement secondelement = firstelement.getAsJsonObject().get("links");
        String scanID = firstelement.getAsJsonObject().get("id").getAsString();
        String s3LocationURL = secondelement.getAsJsonObject().get("url").toString();
        logger.info("********s3LocationURL******* " + s3LocationURL.substring(1, s3LocationURL.length() - 1));

        try (CloseableHttpResponse uploadFileResponse = uploadFileToS3(client, s3LocationURL.substring(1, s3LocationURL.length() - 1), filePath)) {
          if (uploadFileResponse.getStatusLine().getStatusCode() == 200 || uploadFileResponse.getStatusLine().getStatusCode() == 201) {
            logger.info("Uploaded the file to S3 bucket successfully");
            try (CloseableHttpResponse triggerScanResponse = triggerScan(client, scanID, authToken, prismaCloudConfiguration)) {
              if (triggerScanResponse.getStatusLine().getStatusCode() == 200) {
                logger.info("Triggered the scan with scanID "+scanID);
                while("processing".equals(processingStatus)){
                  try (CloseableHttpResponse jobStatusResponse = getScanJobStatus(client, scanID, authToken, prismaCloudConfiguration)) {
                    if (jobStatusResponse.getStatusLine().getStatusCode() == 200) {
                      Object obj = new JsonParser().parse(EntityUtils.toString(jobStatusResponse.getEntity(), StandardCharsets.UTF_8));
                      JsonObject statusObject = (JsonObject) obj;
                      if(statusObject.has("data")){
                        JsonObject attributes = statusObject.get("data").getAsJsonObject().get("attributes").getAsJsonObject();
                        processingStatus = attributes.get("status").getAsString();
                        if (!processingStatus.equals("processing")){
                          break;
                        }
                      }
                      Thread.sleep(5000);
                    } else {
                      logger.info("Get job status failed");
                      return EntityUtils.toString(jobStatusResponse.getEntity(), StandardCharsets.UTF_8);
                    }
                  }
                }
                try ( CloseableHttpResponse scanResultResponse = getScanResult(client, scanID, authToken, prismaCloudConfiguration)) {
                      if (scanResultResponse.getStatusLine().getStatusCode() == 200) {
                        logger.info("Getting Scan result========for scanID========"+scanID);
                        String result = EntityUtils.toString(scanResultResponse.getEntity());
                        Object obj = new JsonParser().parse(result);
                        JsonObject jsonObjectParent = (JsonObject) obj;
                        jsonObjectParent.addProperty("processingStatus", processingStatus);
                        return jsonObjectParent.toString();
                      }
                    }
              } else {
                logger.info("Trigger Scan Failed");
                return EntityUtils.toString(triggerScanResponse.getEntity(), StandardCharsets.UTF_8);
              }
            }
          } else {
            logger.info("File upload failed");
              return EntityUtils.toString(uploadFileResponse.getEntity(), StandardCharsets.UTF_8);
          }
        }
      } else {
        logger.info("Problem while calling scan details");
          return EntityUtils.toString(scanUrlResponse.getEntity(), StandardCharsets.UTF_8);
      }
    }
    return StringUtils.EMPTY;
  }

    /**
     * Below methos is used to get scan details from prisma cloud API
     */
  private CloseableHttpResponse getPrismaCloudScanDetails(CloseableHttpClient client, PrismaCloudConfiguration prismaCloudConfiguration, String authToken) throws IOException {
    logger.info("Entered into PrismaCloudServiceImpl.getScanResult");
    StringEntity entity = getRequest(prismaCloudConfiguration);
    HttpPost httpPost = new HttpPost(prismaCloudConfiguration.getScanUrl());
    httpPost.setHeader("Accept", "application/vnd.api+json");
    httpPost.setHeader("Content-Type", "application/vnd.api+json");
    httpPost.setHeader("x-redlock-auth", authToken);
    httpPost.setEntity(entity);
    return client.execute(httpPost);
  }

    /**
     * Below methos is used to upload the file to given S3 Bucket
     */
  private CloseableHttpResponse uploadFileToS3(CloseableHttpClient client, String s3LocationURL, String filePath) throws IOException {
    logger.info("Entered into PrismaCloudServiceImpl.uploadFileToS3");
    HttpPut httpPut = new HttpPut(s3LocationURL);
    File fileToUpload = new File(filePath);
    fileToUpload.setReadable(true);
    httpPut.setEntity(new FileEntity(fileToUpload, ContentType.APPLICATION_OCTET_STREAM));
    return client.execute(httpPut);
  }

    /**
     * Below method is used to start scan process for uploaded file.
     */
  private CloseableHttpResponse triggerScan(CloseableHttpClient client, String scanId, String authToken, PrismaCloudConfiguration prismaCloudConfiguration) throws IOException {
    logger.info("Entered into PrismaCloudServiceImpl.triggerScan");
    JsonApiModelScanTrigger jsonApiModelScanTrigger = new JsonApiModelScanTrigger();
    JsonApiModelScanTriggerData jsonApiModelScanTriggerData = new JsonApiModelScanTriggerData();
    jsonApiModelScanTriggerData.setId(UUID.fromString(scanId));

    JsonApiModelScanTriggerDataAttributes jsonApiModelScanTriggerDataAttributes = new JsonApiModelScanTriggerDataAttributes();

    if (prismaCloudConfiguration.getTemplateType()
        .equalsIgnoreCase("tf")) {
      jsonApiModelScanTriggerDataAttributes.setTemplateType("tf");
    } else if (prismaCloudConfiguration.getTemplateType()
        .equalsIgnoreCase("cft")) {
      jsonApiModelScanTriggerDataAttributes.setTemplateType("cft");
    } else if (prismaCloudConfiguration.getTemplateType()
        .equalsIgnoreCase("k8s")) {
      jsonApiModelScanTriggerDataAttributes.setTemplateType("k8s");
    }else {
      jsonApiModelScanTriggerDataAttributes.setTemplateType("");
    }

    jsonApiModelScanTriggerDataAttributes.setTemplateVersion(prismaCloudConfiguration.getTemplateVersion());
    IacTemplateParameters iacTemplateParameters = new IacTemplateParameters();

    jsonApiModelScanTriggerDataAttributes.setTemplateParameters(iacTemplateParameters);
    jsonApiModelScanTriggerDataAttributes.setTemplateVersion(prismaCloudConfiguration.getTemplateVersion());
    jsonApiModelScanTriggerData.setAttributes(jsonApiModelScanTriggerDataAttributes);
    jsonApiModelScanTrigger.setData(jsonApiModelScanTriggerData);

    ObjectMapper mapper = new ObjectMapper();
    StringEntity entity = new StringEntity(mapper.writeValueAsString(jsonApiModelScanTrigger));
    HttpPost httpPost = new HttpPost(prismaCloudConfiguration.getScanUrl().concat("/").concat(scanId));
    httpPost.setHeader("Accept", "application/vnd.api+json");
    httpPost.setHeader("Content-Type", "application/vnd.api+json");
    httpPost.setHeader("x-redlock-auth", authToken);
    httpPost.setEntity(entity);
    return client.execute(httpPost);
  }

    /**
     * Below method is used to get scan status for uploaded file.
     */
  private CloseableHttpResponse getScanJobStatus(CloseableHttpClient client, String scanId, String authToken, PrismaCloudConfiguration prismaCloudConfiguration) throws IOException {
    logger.info("Entered into PrismaCloudServiceImpl.getScanJobStatus");
    JsonObject requestBody = new JsonObject();
    requestBody.addProperty("scanId", scanId);

    HttpGet httpGet = new HttpGet(prismaCloudConfiguration.getScanUrl().concat("/").concat(scanId).concat("/status"));
    httpGet.setHeader("Accept", "application/vnd.api+json");
    httpGet.setHeader("Content-Type", "application/vnd.api+json");
    httpGet.setHeader("x-redlock-auth", authToken);
    return client.execute(httpGet);
  }

    /**
     * Below method is used to get scan result of uploaded file.
     */
  private CloseableHttpResponse getScanResult(CloseableHttpClient client, String scanId, String authToken, PrismaCloudConfiguration prismaCloudConfiguration) throws IOException {
    logger.info("Entered into PrismaCloudServiceImpl.getScanResult");
    JsonObject requestBody = new JsonObject();
    requestBody.addProperty("scanId", scanId);

    HttpGet httpGet = new HttpGet(prismaCloudConfiguration.getScanUrl().concat("/").concat(scanId).concat("/results"));
    httpGet.setHeader("Accept", "application/vnd.api+json");
    httpGet.setHeader("Content-Type", "application/vnd.api+json");
    httpGet.setHeader("x-redlock-auth", authToken);
    return client.execute(httpGet);
  }

    /**
     * Below method is used to format/create request for API call
     */
  private StringEntity getRequest(PrismaCloudConfiguration prismaCloudConfiguration) throws UnsupportedEncodingException, JsonProcessingException {
    // Form json request
    JsonApiModelAsyncScanRequest jsonApiModelAsyncScanRequest = new JsonApiModelAsyncScanRequest();
    JsonApiModelAsyncScanRequestData jsonApiModelAsyncScanRequestData = new JsonApiModelAsyncScanRequestData();
    JsonApiModelAsyncScanRequestDataAttributes jsonApiModelAsyncScanRequestDataAttributes = new JsonApiModelAsyncScanRequestDataAttributes();

    //Setting tags
    Map<String, String> iacScanTagsMap = new HashMap<>();
    Map<String, String> configFileTags = prismaCloudConfiguration.getConfigFileTags();
    if (configFileTags != null && !configFileTags.isEmpty()) {
      iacScanTagsMap.putAll(configFileTags);
    }

    String tagsString = prismaCloudConfiguration.getTags();
    if (tagsString != null && !(tagsString = tagsString.trim()).isEmpty()) {
      String[] tagsArray = tagsString.split(",");
      for (String s : tagsArray) {
        ConfigYmlTagsUtil.parseAndSetTag(s, iacScanTagsMap);
      }
    }
    jsonApiModelAsyncScanRequestDataAttributes.setTags(iacScanTagsMap);

    //setting scan type
    jsonApiModelAsyncScanRequestData.setType("async-scan");
    //Setting asset attribute
    jsonApiModelAsyncScanRequestDataAttributes.setAssetName(prismaCloudConfiguration.getAssetName());
    jsonApiModelAsyncScanRequestDataAttributes.setAssetType(prismaCloudConfiguration.getAssetType());

    //Setting scanAttributes
    Map<String, String> jsonApiModelScanAttributes = new HashMap<>();
    jsonApiModelScanAttributes.put("buildNumber", prismaCloudConfiguration.getBuildNumber());
    jsonApiModelScanAttributes.put("projectName", prismaCloudConfiguration.getJobName());
    jsonApiModelAsyncScanRequestDataAttributes.setScanAttributes(jsonApiModelScanAttributes);

    JsonApiModelFailureCriteria jsonApiModelFailureCriteria = new JsonApiModelFailureCriteria();
    jsonApiModelFailureCriteria.setHigh(prismaCloudConfiguration.getHigh());
    jsonApiModelFailureCriteria.setMedium(prismaCloudConfiguration.getMedium());
    jsonApiModelFailureCriteria.setLow(prismaCloudConfiguration.getLow());
    jsonApiModelFailureCriteria.setOperator(prismaCloudConfiguration.getOperator());
    jsonApiModelAsyncScanRequestDataAttributes.setFailureCriteria(jsonApiModelFailureCriteria);

    jsonApiModelAsyncScanRequestData.setAttributes(jsonApiModelAsyncScanRequestDataAttributes);
    jsonApiModelAsyncScanRequest.setData(jsonApiModelAsyncScanRequestData);
    ObjectMapper mapper = new ObjectMapper();
    return new StringEntity(mapper.writeValueAsString(jsonApiModelAsyncScanRequest));
  }
}