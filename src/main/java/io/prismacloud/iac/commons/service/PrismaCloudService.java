package io.prismacloud.iac.commons.service;


import hudson.EnvVars;
import hudson.FilePath;
import io.prismacloud.iac.commons.config.PrismaCloudConfiguration;
import java.io.IOException;

/**
 * PrismaCloudService interface defines all the services which will called during scan
 */
public interface PrismaCloudService {
  public String getAccessToken(PrismaCloudConfiguration prismaCloudConfiguration) throws IOException;
  public String getScanDetails(EnvVars envVars, PrismaCloudConfiguration prismaCloudConfiguration, FilePath filePath) throws IOException, InterruptedException;
}
