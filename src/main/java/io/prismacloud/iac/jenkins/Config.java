/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  hudson.Extension
 *  hudson.PluginWrapper
 *  hudson.ProxyConfiguration
 *  hudson.util.FormValidation
 *  hudson.util.Secret
 *  interfaces.Logger
 *  javax.annotation.CheckForNull
 *  jenkins.model.GlobalConfiguration
 *  org.kohsuke.stapler.DataBoundSetter
 *  org.kohsuke.stapler.QueryParameter
 */
package io.prismacloud.iac.jenkins;

import io.prismacloud.iac.commons.config.PrismaCloudConfiguration;
import io.prismacloud.iac.commons.service.impl.PrismaCloudServiceImpl;
import hudson.Extension;
import hudson.util.FormValidation;
import hudson.util.Secret;
import javax.annotation.CheckForNull;
import jenkins.model.GlobalConfiguration;
import jenkins.model.Jenkins;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.verb.POST;

@Extension
public final class Config
extends GlobalConfiguration {
    private static final String versionCheckMessage = "Jenkins plugin version: %s doesn't match Console version: %s. You can download a matching version of Jenkins plugin from ";
    private String prismaCloudApiUrl;
    private Secret prismaCloudAccessKey;
    private Secret prismaCloudSecretKey;

    public static Config get() { return (Config)((Object)GlobalConfiguration.all().get(Config.class));}

    public Config() {
        this.load();
    }

    public String getPrismaCloudAccessKey() {
        return Secret.toString((Secret)this.prismaCloudAccessKey);
    }

    public String getPrismaCloudSecretKey() {
        return Secret.toString((Secret)this.prismaCloudSecretKey);
    }

    @CheckForNull
    public String getPrismaCloudApiUrl() {
        return this.prismaCloudApiUrl;
    }

    @DataBoundSetter
    public void setPrismaCloudApiUrl(String prismaCloudApiUrl) {
        this.prismaCloudApiUrl = prismaCloudApiUrl;
        this.save();
    }

    @DataBoundSetter
    public void setPrismaCloudAccessKey(String prismaCloudAccessKey) {
        this.prismaCloudAccessKey = Secret.fromString((String)prismaCloudAccessKey);
        this.save();
    }

    @DataBoundSetter
    public void setPrismaCloudSecretKey(String prismaCloudSecretKey) {
        this.prismaCloudSecretKey = Secret.fromString((String)prismaCloudSecretKey);
        this.save();
    }

    @POST
    public FormValidation doCheckPrismaCloudAccessKey(@QueryParameter String prismaCloudAccessKey) {
        if (prismaCloudAccessKey.length() == 0) {
            return FormValidation.error((String)"Please set access key");
        }
        return FormValidation.ok();
    }

    @POST
    public FormValidation doCheckPrismaCloudSecretKey(@QueryParameter String prismaCloudSecretKey) {
        if (prismaCloudSecretKey.length() == 0) {
            return FormValidation.error((String)"Please set secret key");
        }
        return FormValidation.ok();
    }

    @POST
    public FormValidation doCheckPrismaCloudApiUrl(@QueryParameter String prismaCloudApiUrl) {
        if (prismaCloudApiUrl.length() == 0) {
            return FormValidation.error((String)"Please set Prisma Cloud API URL");
        }
        if (prismaCloudApiUrl.indexOf(58) == -1 || prismaCloudApiUrl.indexOf("https://") == -1) {
            return FormValidation.error((String)"Format is https://<api_domain>");
        }
        return FormValidation.ok();
    }

    public FormValidation doTestConnection(
        @QueryParameter(value="prismaCloudApiUrl") String prismaCloudApiUrl,
        @QueryParameter(value="prismaCloudAccessKey") String prismaCloudAccessKey,
        @QueryParameter(value="prismaCloudSecretKey") String prismaCloudSecretKey) {
        Jenkins.get().checkPermission(Jenkins.ADMINISTER);
        try {
            PrismaCloudServiceImpl prismaCloudService = new PrismaCloudServiceImpl();
            PrismaCloudConfiguration prismaCloudConfiguration = new PrismaCloudConfiguration();
            String apiUrl = prismaCloudApiUrl;
            if (apiUrl.endsWith("/")) {
                apiUrl = apiUrl.substring(0, apiUrl.length() - 1);
            }
            prismaCloudConfiguration.setAuthUrl(apiUrl + "/login");
            prismaCloudConfiguration.setAccessKey(prismaCloudAccessKey);
            prismaCloudConfiguration.setSecretKey(prismaCloudSecretKey);
            String authToken = prismaCloudService.getAccessToken(prismaCloudConfiguration);
            if (authToken.equals("")) {
                return FormValidation.error((String)("Failed to authenticate with server"));
            }
        }
        catch (Exception e) {
            return FormValidation.error((String)("Client error: " + e.getMessage()));
        }
        return FormValidation.ok((String)"Successfully authenticated with server");
    }

    private String getPluginVersion() {
        return this.getPlugin().getVersion();
    }
}

