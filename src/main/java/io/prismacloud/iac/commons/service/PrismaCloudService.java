package io.prismacloud.iac.commons.service;


import com.prismacloud.config.PrismaCloudConfiguration;
import java.io.IOException;

/**
 * PrismaCloudService interface defines all the services which will called during scan
 */
public interface PrismaCloudService {
  public String getAccessToken(PrismaCloudConfiguration prismaCloudConfiguration) throws IOException;
  public String getScanDetails(PrismaCloudConfiguration prismaCloudConfiguration, String filePath) throws IOException, InterruptedException;
}
