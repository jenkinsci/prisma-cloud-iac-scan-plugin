package io.prismacloud.iac.commons.service;


import hudson.model.TaskListener;
import io.prismacloud.iac.commons.config.PrismaCloudConfiguration;
import java.io.IOException;

/**
 * PrismaCloudService interface defines all the services which will called during scan
 */
public interface PrismaCloudService {
  public String getAccessToken(PrismaCloudConfiguration prismaCloudConfiguration) throws IOException;
  public String getScanDetails(PrismaCloudConfiguration prismaCloudConfiguration, String filePath) throws IOException, InterruptedException;
}
