package io.prismacloud.iac.jenkins;

import java.io.File;
import java.io.IOException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ZipUtilsTest {

  ZipUtils zipUtils;

  @Rule
  public TemporaryFolder temp = new TemporaryFolder();
  File tmpFolder;

  @Before
  public void setup() throws IOException {
    zipUtils = new ZipUtils();
    tmpFolder = temp.newFile();
  }

  @Test
  public void testzipFolder() throws IOException {
    zipUtils.zipFolder(tmpFolder.getAbsolutePath(), tmpFolder.getAbsolutePath()+"test.zip");
  }

  @Test(expected = NullPointerException.class)
  public void testzipFolderException() throws IOException {
    zipUtils.zipFolder("", tmpFolder.getAbsolutePath()+"test.zip");
  }

  @Test(expected = IOException.class)
  public void testzipFolderException2() throws IOException {
    zipUtils.zipFolder(tmpFolder.getAbsolutePath(), "");
  }
}
