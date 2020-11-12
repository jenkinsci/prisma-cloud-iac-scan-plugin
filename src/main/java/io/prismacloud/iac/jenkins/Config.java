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

import com.prismacloud.config.PrismaCloudConfiguration;
import com.prismacloud.service.impl.PrismaCloudServiceImpl;
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
    private String address;
    private Secret username;
    private Secret password;

    public static Config get() { return (Config)((Object)GlobalConfiguration.all().get(Config.class));}

    public Config() {
        this.load();
    }

    public String getUsername() {
        return Secret.toString((Secret)this.username);
    }

    public String getPassword() {
        return Secret.toString((Secret)this.password);
    }

    @CheckForNull
    public String getAddress() {
        return this.address;
    }

    @DataBoundSetter
    public void setAddress(String address) {
        this.address = address;
        this.save();
    }

    @DataBoundSetter
    public void setUsername(String username) {
        this.username = Secret.fromString((String)username);
        this.save();
    }

    @DataBoundSetter
    public void setPassword(String password) {
        this.password = Secret.fromString((String)password);
        this.save();
    }

    @POST
    public FormValidation doCheckUsername(@QueryParameter String value) {
        if (value.length() == 0) {
            return FormValidation.error((String)"Please set access key");
        }
        return FormValidation.ok();
    }

    @POST
    public FormValidation doCheckPassword(@QueryParameter String value) {
        if (value.length() == 0) {
            return FormValidation.error((String)"Please set secret key");
        }
        return FormValidation.ok();
    }

    @POST
    public FormValidation doCheckAddress(@QueryParameter String value) {
        if (value.length() == 0) {
            return FormValidation.error((String)"Please set auth URL");
        }
        if (value.indexOf(58) == -1 || value.indexOf("https://") == -1) {
            return FormValidation.error((String)"Format is https://<authURL>");
        }
        return FormValidation.ok();
    }

    public FormValidation doTestConnection(@QueryParameter(value="address") String address, @QueryParameter(value="username") String username, @QueryParameter(value="password") String password) {
        Jenkins.get().checkPermission(Jenkins.ADMINISTER);
        try {
            PrismaCloudServiceImpl prismaCloudService = new PrismaCloudServiceImpl();
            PrismaCloudConfiguration prismaCloudConfiguration = new PrismaCloudConfiguration();
            prismaCloudConfiguration.setAuthUrl(address.concat("/login"));
            prismaCloudConfiguration.setAccessKey(username);
            prismaCloudConfiguration.setSecretKey(password);
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

