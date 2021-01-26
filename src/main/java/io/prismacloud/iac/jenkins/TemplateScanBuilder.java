package io.prismacloud.iac.jenkins;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import io.prismacloud.iac.commons.config.PrismaCloudConfiguration;
import io.prismacloud.iac.commons.model.IacTemplateParameters;
import io.prismacloud.iac.commons.service.impl.PrismaCloudServiceImpl;
import io.prismacloud.iac.commons.util.ConfigYmlTagsUtil;
import io.prismacloud.iac.commons.util.JSONUtils;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import hudson.AbortException;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.model.User;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.util.FormValidation;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;

import jenkins.model.Jenkins;
import jenkins.tasks.SimpleBuildStep;
import lombok.SneakyThrows;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;


public class TemplateScanBuilder extends Builder implements SimpleBuildStep {


    private String assetName;
    private String templateType;
    private String templateVersion;
    private String tags;
    private String failureCriteriaHigh;
    private String failureCriteriaMedium;
    private String failureCriteriaLow;
    private String failureCriteriaOperator;
    private boolean apiResponseError;

    @DataBoundConstructor
    public TemplateScanBuilder(
            String assetName,
            String templateType,
            String templateVersion,
            String tags,
            String failureCriteriaHigh,
            String failureCriteriaLow,
            String failureCriteriaMedium,
            String failureCriteriaOperator) {
        this.assetName = assetName;
        this.templateType = templateType;
        this.templateVersion = templateVersion;
        this.tags = tags;
        this.failureCriteriaHigh = failureCriteriaHigh;
        this.failureCriteriaMedium = failureCriteriaMedium;
        this.failureCriteriaLow = failureCriteriaLow;
        this.failureCriteriaOperator = failureCriteriaOperator;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getTemplateVersion() {
        return templateVersion;
    }

    public void setTemplateVersion(String templateVersion) {
        this.templateVersion = templateVersion;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getFailureCriteriaHigh() {
        return failureCriteriaHigh;
    }

    public void setFailureCriteriaHigh(String failureCriteriaHigh) {
        this.failureCriteriaHigh = failureCriteriaHigh;
    }

    public String getFailureCriteriaMedium() {
        return failureCriteriaMedium;
    }

    public void setFailureCriteriaMedium(String failureCriteriaMedium) {
        this.failureCriteriaMedium = failureCriteriaMedium;
    }

    public String getFailureCriteriaLow() {
        return failureCriteriaLow;
    }

    public void setFailureCriteriaLow(String failureCriteriaLow) {
        this.failureCriteriaLow = failureCriteriaLow;
    }

    public String getFailureCriteriaOperator() {
        return failureCriteriaOperator;
    }

    public void setFailureCriteriaOperator(String failureCriteriaOperator) {
        this.failureCriteriaOperator = failureCriteriaOperator;
    }

    /**
     * Below method is triggered when jenkins build is triggered.
     */
    @SneakyThrows
    @SuppressFBWarnings({"DM_EXIT", "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE", "RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE", "DLS_DEAD_LOCAL_STORE", "DM_STRING_VOID_CTOR"})
    public void perform(@Nonnull Run build, @Nonnull FilePath workspace, @Nonnull Launcher launcher, @Nonnull TaskListener listener) {

        listener.getLogger().println("Prisma Cloud IaC Scan: Custom Build Started");

        File sourceFolder = new File(workspace.getRemote());
        sourceFolder.setWritable(true);
        listener.getLogger().println("Prisma Cloud IaC Scan: Source Folder : " + sourceFolder);

        File destinationFile = new File(sourceFolder + "/iacscan.zip");
        destinationFile.setWritable(true);
        listener.getLogger().println("Prisma Cloud IaC Scan: Destination Folder : " + destinationFile);

        String jobName = build.getDisplayName();
        //Create zip file
        boolean isZipFileCreated = createZipFile(sourceFolder, destinationFile);

        listener.getLogger().println("Prisma Cloud IaC Scan: ZIP file Created : " + isZipFileCreated);

        Map<String, String> configFileTags;

        File configYmlFile = new File(sourceFolder, ".prismaCloud/config.yml");
        File configYamlFile = new File(sourceFolder, ".prismaCloud/config.yaml");
        if (configYmlFile.exists() && configYmlFile.isFile() && configYmlFile.canRead()) {
            configFileTags = ConfigYmlTagsUtil.readTags(listener.getLogger(), configYmlFile);
        } else if (configYamlFile.exists() && configYamlFile.isFile() && configYamlFile.canRead()) {
            configFileTags = ConfigYmlTagsUtil.readTags(listener.getLogger(), configYamlFile);
        } else {
            configFileTags = Collections.emptyMap();
        }

        IacTemplateParameters iacTemplateParameters = initializeIacTemplateParameters(sourceFolder, listener);

        listener.getLogger().println("Prisma Cloud IaC Scan: Config YML files check completed");

        boolean buildStatus = false;
        listener.getLogger().println("Prisma Cloud IaC Scan: ------------------------- Prisma Cloud IAC----------------------");

        if (isZipFileCreated) {
            listener.getLogger().println("Prisma Cloud IaC Scan: Calling Prisma Cloud IaC Scan API " + destinationFile.getAbsolutePath());
            String result = callPrismaCloudAsyncEndPoint(destinationFile.getAbsolutePath(), listener, workspace.getRemote(), jobName, configFileTags, iacTemplateParameters);
            buildStatus = checkSeverity(result, listener);
            listener.getLogger().println("Prisma Cloud IaC Scan: Build Status value before processing results : " + buildStatus);
            if (apiResponseError) {
                listener.getLogger().println("Prisma Cloud IaC Scan: Api Error detected.....");
            } else {
                listener.getLogger().println("Prisma Cloud IaC Scan: No Api Error detected....");
            }
            build.addAction(new ScanResultAction(result, buildStatus,
                    getSeverityMap(failureCriteriaHigh, failureCriteriaMedium, failureCriteriaLow, failureCriteriaOperator), apiResponseError, listener));
        } else {
            throw new AbortException("Prisma Cloud IaC Scan: Zipfile Failed to create");
        }

        listener.getLogger().println("Prisma Cloud IaC Scan: Build status value after processing results : " + buildStatus);
        //Report Build Complete, now show build status
        if (buildStatus == false) {
            throw new AbortException("Prisma Cloud IaC Scan: Build Failed");
        }
    }

    protected boolean createZipFile(File sourceFolder, File destinationFile) throws IOException {
        ZipUtils zipUtils = new ZipUtils();
        zipUtils.zipFolder(sourceFolder.getAbsolutePath(), destinationFile.getAbsolutePath());
        return destinationFile.isFile();
    }

    /**
     * Below method is used to display the plugin name
     * on the Jenkins User interface
     */
    @Extension
    @Symbol("prismaIaC")
    public static class Descriptor extends BuildStepDescriptor<Builder> {
        @Override
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return "Prisma Cloud IaC Scan";
        }

        public FormValidation doCheckFailureCriteriaHigh(@QueryParameter String failureCriteriaHigh) {
            try {
                if (Integer.parseInt(failureCriteriaHigh) >= 0) {
                    return FormValidation.ok();
                } else {
                    return FormValidation.error("Please Enter Valid Number");
                }
            } catch (NumberFormatException exception) {
                return FormValidation.error("Please Enter Valid Number");
            }
        }

        public FormValidation doCheckFailureCriteriaLow(@QueryParameter String failureCriteriaLow) {
            try {
                if (Integer.parseInt(failureCriteriaLow) >= 0) {
                    return FormValidation.ok();
                } else {
                    return FormValidation.error("Please Enter Valid Number");
                }
            } catch (NumberFormatException exception) {
                return FormValidation.error("Please Enter Valid Number");
            }
        }

        public FormValidation doCheckFailureCriteriaMedium(@QueryParameter String failureCriteriaMedium) {
            try {
                if (Integer.parseInt(failureCriteriaMedium) >= 0) {
                    return FormValidation.ok();
                } else {
                    return FormValidation.error("Please Enter Valid Number");
                }
            } catch (NumberFormatException exception) {
                return FormValidation.error("Please Enter Valid Number");
            }
        }

        public FormValidation doCheckFailureCriteriaOperator(@QueryParameter String failureCriteriaOperator) {
            try {
                if (failureCriteriaOperator.equalsIgnoreCase("and")
                        || failureCriteriaOperator.equalsIgnoreCase("or")) {
                    return FormValidation.ok();
                } else {
                    return FormValidation.error("Allowed Operators are [and,or]");
                }
            } catch (Exception exception) {
                return FormValidation.error("Allowed Operators are [and,or]");
            }
        }

        public FormValidation doCheckTemplateType(@QueryParameter String templateType) {
            if (templateType.length() >= 0 && (templateType.equalsIgnoreCase("TF")
                    || templateType.equalsIgnoreCase("CFT")
                    || templateType.equalsIgnoreCase("K8S"))) {
                return FormValidation.ok();
            } else {
                return FormValidation.error("Please Enter Valid Template Type [TF, CFT, K8S]");
            }
        }

        public FormValidation doCheckAssetName(@QueryParameter String assetName) {
            if (assetName.length() > 0 && assetName.length() < 255) {
                return FormValidation.ok();
            } else {
                return FormValidation.error("Asset Name charactor length should be in between 0 to 255");
            }
        }
    }

    /**
     * Below method calls the prisma cloud API and get result through common module
     */
    @SuppressFBWarnings({"UC_USELESS_OBJECT", "DLS_DEAD_LOCAL_STORE", "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE"})
    public String callPrismaCloudAsyncEndPoint(
            String filePath, TaskListener listener, String workspace, String jobName, Map<String, String> configFileTags, IacTemplateParameters iacTemplateParameters)
            throws IOException, InterruptedException {
        listener.getLogger().println("Prisma Cloud IaC Scan: callPrismaCloudAsyncEndPoint =====Workspace======" + workspace);
        Config descriptor = (Config) Jenkins.get().getDescriptor(Config.class);
        PrismaCloudServiceImpl prismaCloudService = new PrismaCloudServiceImpl();
        PrismaCloudConfiguration prismaCloudConfiguration = new PrismaCloudConfiguration();
        String apiUrl = descriptor.getPrismaCloudApiUrl();
        if (apiUrl.endsWith("/")) {
            apiUrl = apiUrl.substring(0, apiUrl.length() - 1);
        }

        listener.getLogger().println("Prisma Cloud IaC Scan: API URL : " + apiUrl);
        prismaCloudConfiguration.setAuthUrl(apiUrl + "/login");
        prismaCloudConfiguration.setScanUrl(apiUrl + "/iac/v2/scans");
        listener.getLogger().println("Prisma Cloud IaC Scan: Scan URL : " + apiUrl + "/iac/v2/scans");
        prismaCloudConfiguration.setAccessKey(descriptor.getPrismaCloudAccessKey());
        prismaCloudConfiguration.setSecretKey(descriptor.getPrismaCloudSecretKey());
        prismaCloudConfiguration.setTemplateType(templateType);
        prismaCloudConfiguration.setTemplateVersion(templateVersion);
        prismaCloudConfiguration.setAssetName(assetName);
        prismaCloudConfiguration.setTags(tags);
        prismaCloudConfiguration.setAssetType("Jenkins");
        prismaCloudConfiguration.setHigh(Integer.parseInt(failureCriteriaHigh));
        prismaCloudConfiguration.setMedium(Integer.parseInt(failureCriteriaMedium));
        prismaCloudConfiguration.setLow(Integer.parseInt(failureCriteriaLow));
        prismaCloudConfiguration.setOperator(failureCriteriaOperator);
        prismaCloudConfiguration.setBuildNumber(String.valueOf(getJenkinsBuildNumber()));
        prismaCloudConfiguration.setCurrentUserName(User.current().getFullName());
        prismaCloudConfiguration.setJobName(jobName);
        prismaCloudConfiguration.setConfigFileTags(configFileTags);
        prismaCloudConfiguration.setIacTemplateParameters(iacTemplateParameters);
        return prismaCloudService.getScanDetails(prismaCloudConfiguration, filePath);
    }

    @SneakyThrows
    @SuppressFBWarnings({"REC_CATCH_EXCEPTION"})
    protected boolean checkSeverity(String result, TaskListener listener) {
        listener.getLogger().println("Prisma Cloud IaC Scan: In checkSeverity");
        boolean processingStatus  = true;
        try {

            //Parsing result
            JsonReader reader = JSONUtils.parseJSONWitReader(result);
            JsonObject jsonObjectParent = JsonParser.parseReader(reader).getAsJsonObject();
            listener.getLogger().println("Prisma Cloud IaC Scan: *** processingStatus : " + jsonObjectParent.get("processingStatus"));
            JsonElement jsonError = jsonObjectParent.get("errors");

            if (jsonError != null) {
                listener.getLogger().println("Prisma Cloud IaC Scan: API Response Errors : errors element found " + result);
                listener.getLogger().println("Prisma Cloud IaC Scan: Please check Prisma Cloud IaC Scan Report");
                apiResponseError = true;

            }

            JsonElement jsonErr = jsonObjectParent.get("error");
            if (jsonErr != null) {
                listener.getLogger().println("Prisma Cloud IaC Scan: API Response Error : error element found" + result);
                listener.getLogger().println("Prisma Cloud IaC Scan: Please check Prisma Cloud IaC Scan Report");
                apiResponseError = true;

            }

            // Parse Metadata
            JsonElement jsonElementParent = jsonObjectParent.get("meta");
            JsonObject jsonObjectChild = jsonElementParent.getAsJsonObject();

            //Check if api has errorDetails
            JsonElement jsonElementError = jsonObjectChild.get("errorDetails");
            JsonArray jsonForElementArray = jsonElementError.getAsJsonArray();
            if (jsonForElementArray.size() > 0) {
                listener.getLogger().println("Prisma Cloud IaC Scan: API Response : error details" + result);
                listener.getLogger().println("Prisma Cloud IaC Scan: Please check Prisma Cloud IaC Scan Report");
                apiResponseError = true;

            }

            //Return status from API
            if (jsonObjectParent.get("processingStatus") != null) {
                String status = jsonObjectParent.get("processingStatus").toString().replace("\"", "");
                //Remove double quotes if exist
                listener.getLogger().println("Prisma Cloud IaC Scan: Status " + status);

                if(status.equalsIgnoreCase("failed")) {
                    processingStatus = false;
                }
            }
            return processingStatus;
        } catch (Exception exception) {
            listener.getLogger().println("Prisma Cloud IaC Scan: API Response " + result);
            apiResponseError = true;
            return false;
        }
    }

    @SuppressFBWarnings({"NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE"})
    protected int getJenkinsBuildNumber() {
        Jenkins instance = Jenkins.get();
        //Getting latest triggered build
        return instance.getView("All").getBuilds().getLastBuild().getNumber();
    }

    @SuppressFBWarnings({"NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE"})
    protected Map<String, String> getSeverityMap(String high, String medium, String low, String operator) {
        Map<String, String> severityLimitMap = new HashMap<>();
        severityLimitMap.put("High", high);
        severityLimitMap.put("Low", low);
        severityLimitMap.put("Medium", medium);
        severityLimitMap.put("Operator", operator);
        return severityLimitMap;
    }

    @SuppressFBWarnings({"NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE"})
    public IacTemplateParameters initializeIacTemplateParameters(File sourceFolder, TaskListener listener) {
        listener.getLogger().println("Prisma Cloud IaC Scan: Inside initializeIacTemplateParameters");

        IacTemplateParameters iacTemplateParameters;

        File configYmlFile = new File(sourceFolder, ".prismaCloud/config.yml");
        File configYamlFile = new File(sourceFolder, ".prismaCloud/config.yaml");
        if (configYmlFile.exists() && configYmlFile.isFile() && configYmlFile.canRead()) {
            iacTemplateParameters = ConfigYmlTagsUtil.readTemplateParams(listener.getLogger(), configYmlFile);
        } else if (configYamlFile.exists() && configYamlFile.isFile() && configYamlFile.canRead()) {
            iacTemplateParameters = ConfigYmlTagsUtil.readTemplateParams(listener.getLogger(), configYamlFile);
        } else {
            iacTemplateParameters = new IacTemplateParameters();
        }
        return iacTemplateParameters;
    }
}
