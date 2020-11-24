package io.prismacloud.iac.jenkins;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import io.prismacloud.iac.commons.config.PrismaCloudConfiguration;
import io.prismacloud.iac.commons.service.impl.PrismaCloudServiceImpl;
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
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import jenkins.model.Jenkins;
import jenkins.tasks.SimpleBuildStep;
import lombok.SneakyThrows;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

public class TemplateScanBuilder  extends Builder implements SimpleBuildStep {

	private String hostname;
	private String low;
	private String medium;
	private String operator;
	private String high;
	private String assetname;
	private String templatetype;
	private String templateversion;
	private String tags;
    private boolean apiResponseError;
    private String jobName;

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getHigh() { return high; }

	public void setHigh(String high) {
		this.high = high;
	}

	public String getLow() {
		return low;
	}

	public void setLow(String low) {
		this.low = low;
	}

	public String getMedium() {
		return medium;
	}

	public void setMedium(String medium) {
		this.medium = medium;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getAssetname() { return assetname; }

	public void setAssetname(String assetname) { this.assetname = assetname; }

	public String getTemplatetype() { return templatetype; }

	public void setTemplatetype(String templatetype) { this.templatetype = templatetype; }

	public String getTemplateversion() { return templateversion; }

	public void setTemplateversion(String templateversion) { this.templateversion = templateversion; }

	public String getTags() { return tags; }

	public void setTags(String tags) { this.tags = tags; }


    @DataBoundConstructor
    public TemplateScanBuilder(String hostName, String high, String low, String medium, String operator, String assetname, String tags, String templatetype, String templateversion) {
    	this.hostname = hostName;
    	this.high = high;
    	this.low = low;
    	this.medium = medium;
    	this.operator = operator;
    	this.assetname = assetname;
    	this.tags = tags;
    	this.templatetype = templatetype;
    	this.templateversion = templateversion;
    }

	/**
	 * Below method is triggered when jenkins build is triggered.
	 */
	@SneakyThrows
	@SuppressFBWarnings({"DM_EXIT", "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE", "RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE","DLS_DEAD_LOCAL_STORE", "DM_STRING_VOID_CTOR"})
    public void perform(@Nonnull Run build, @Nonnull FilePath workspace, @Nonnull Launcher launcher, @Nonnull TaskListener listener) {

		File sourceFolder = new File(workspace.getRemote());
		sourceFolder.setWritable(true);

		File destinationFile = new File(sourceFolder+"/iacscan.zip");
		destinationFile.setWritable(true);

		this.jobName = build.getDisplayName();
 		//Create zip file
		boolean isZipFileCreated = createZipFile(sourceFolder, destinationFile);

		String result = new String();
		boolean buildStatus = false;
		listener.getLogger().println("------------------------- Prisma Cloud IAC----------------------");

		if (isZipFileCreated) {
			listener.getLogger().println("Calling Prisma Cloud IaC Scan API " + destinationFile.getAbsolutePath());
			result = callPrismaCloudAsyncEndPoint(destinationFile.getAbsolutePath(), listener, workspace.getRemote());
			buildStatus = checkSeverity(result, listener);
			build.addAction(new ScanResultAction(result, buildStatus, getSevirityMap(high, medium, low, operator), apiResponseError));
		} else {
			throw new AbortException("Zipfile Failed to create");
		}

		//Report Build Complete, now show build status
		if (buildStatus == false) {
			throw new AbortException("Build Failed");
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

		public FormValidation doCheckHigh(@QueryParameter String high) {
			try {
				if (Integer.parseInt(high) >= 0) {
					return FormValidation.ok();
				} else {
					return FormValidation.error("Please Enter Valid Number");
				}
			} catch (NumberFormatException exception) {
				return FormValidation.error("Please Enter Valid Number");
			}
		}

		public FormValidation doCheckLow(@QueryParameter String low) {
			try {
				if (Integer.parseInt(low) >= 0) {
					return FormValidation.ok();
				} else {
					return FormValidation.error("Please Enter Valid Number");
				}
			} catch (NumberFormatException exception) {
				return FormValidation.error("Please Enter Valid Number");
			}
		}

		public FormValidation doCheckMedium(@QueryParameter String medium) {
			try {
				if (Integer.parseInt(medium) >= 0) {
					return FormValidation.ok();
				} else {
					return FormValidation.error("Please Enter Valid Number");
				}
			} catch (NumberFormatException exception) {
				return FormValidation.error("Please Enter Valid Number");
			}
		}

		public FormValidation doCheckOperator(@QueryParameter String operator) {
			try {
				if (operator.equalsIgnoreCase("and")
						|| operator.equalsIgnoreCase("or")) {
					return FormValidation.ok();
				} else {
					return FormValidation.error("Allowed Operators are [and,or]");
				}
			} catch (Exception exception) {
				return FormValidation.error("Allowed Operators are [and,or]");
			}
		}

		public FormValidation doCheckTemplatetype(@QueryParameter String templatetype) {
				if (templatetype.length() >= 0 && (templatetype.equalsIgnoreCase("TF")
						|| templatetype.equalsIgnoreCase("CFT")
						|| templatetype.equalsIgnoreCase("K8S"))) {
					return FormValidation.ok();
				} else {
					return FormValidation.error("Please Enter Valid Template Type [TF, CFT, K8S]");
				}
		}

		public FormValidation doCheckAssetname(@QueryParameter String assetname) {
			if (assetname.length() > 0 && assetname.length()  < 255 ) {
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
	public String callPrismaCloudAsyncEndPoint(String filePath, TaskListener listener, String workspace) throws IOException, InterruptedException {
		listener.getLogger().println("callPrismaCloudAsyncEndPoint =====Workspace======"+workspace);
		Config descriptor = (Config)Jenkins.get().getDescriptor(Config.class);
		PrismaCloudServiceImpl prismaCloudService = new PrismaCloudServiceImpl();
		PrismaCloudConfiguration prismaCloudConfiguration = new PrismaCloudConfiguration();
		prismaCloudConfiguration.setAuthUrl(descriptor.getAddress()+"/login");
		prismaCloudConfiguration.setAccessKey(descriptor.getUsername());
		prismaCloudConfiguration.setScanUrl(descriptor.getAddress()+"/iac/v2/scans");
		prismaCloudConfiguration.setSecretKey(descriptor.getPassword());
		prismaCloudConfiguration.setTemplateType(templatetype);
		prismaCloudConfiguration.setTemplateVersion(templateversion);
		prismaCloudConfiguration.setAssetName(assetname);
		prismaCloudConfiguration.setTags(tags);
		prismaCloudConfiguration.setAssetType("Jenkins");
		prismaCloudConfiguration.setHigh(Integer.parseInt(high));
		prismaCloudConfiguration.setLow(Integer.parseInt(low));
		prismaCloudConfiguration.setMedium(Integer.parseInt(medium));
		prismaCloudConfiguration.setOperator(operator);
		prismaCloudConfiguration.setBuildNumber(String.valueOf(getJenkinsBuildNumber()));
		prismaCloudConfiguration.setCurrentUserName(User.current().getFullName());
		prismaCloudConfiguration.setJobName(this.jobName);
		return prismaCloudService.getScanDetails(prismaCloudConfiguration, filePath);
	}

	@SneakyThrows
	@SuppressFBWarnings({"REC_CATCH_EXCEPTION"})
	protected boolean checkSeverity(String result, TaskListener listener) {
		//listener.getLogger().println("In checkSeverity");
		try {

			//Parsing result
			JsonReader reader = JSONUtils.parseJSONWitReader(result);
			JsonObject jsonObjectParent = JsonParser.parseReader(reader).getAsJsonObject();
			listener.getLogger().println("*** processingStatus : " + jsonObjectParent.get("processingStatus"));
			JsonElement jsonError = jsonObjectParent.get("errors");

			if(jsonError != null) {
				listener.getLogger().println("API Response " + result);
				listener.getLogger().println("Please check Prisma Cloud IaC Scan Report");
				apiResponseError = true;
				return true;
			}

			JsonElement jsonErr = jsonObjectParent.get("error");
			if(jsonErr != null) {
				listener.getLogger().println("API Response " + result);
				listener.getLogger().println("Please check Prisma Cloud IaC Scan Report");
				apiResponseError = true;
				return true;
			}

			// Parse Metadata
			JsonElement jsonElementParent = jsonObjectParent.get("meta");
			JsonObject jsonObjectChild = jsonElementParent.getAsJsonObject();

			//Check if apihas errorDetails
			JsonElement jsonElementError = jsonObjectChild.get("errorDetails");
			JsonArray jsonForElementArray = jsonElementError.getAsJsonArray();
			if (jsonForElementArray.size() > 0) {
				listener.getLogger().println("API Response " + result);
				listener.getLogger().println("Please check Prisma Cloud IaC Scan Report");
				apiResponseError = true;
				return true;
			}

			//Return status from API
			if(jsonObjectParent.get("processingStatus") != null) {
				String status = jsonObjectParent.get("processingStatus").toString().replace("\"", "");
				//Remove double quotes if exist
				listener.getLogger().println("status " + status);
				if(status.equalsIgnoreCase("passed")){
			       return true;
				}
			}
			return false;
		} catch (Exception exception) {
			listener.getLogger().println("API Response " + result);
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
	protected Map<String, String> getSevirityMap(String high, String medium, String low, String operator) {
		Map<String, String> severityLimitMap = new HashMap<>();
		severityLimitMap.put("High", high);
		severityLimitMap.put("Low", low);
		severityLimitMap.put("Medium", medium);
		severityLimitMap.put("Operator", operator);
		return severityLimitMap;
	}
}
