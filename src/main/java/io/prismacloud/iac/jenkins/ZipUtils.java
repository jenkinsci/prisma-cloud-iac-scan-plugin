package io.prismacloud.iac.jenkins;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    Logger logger = LoggerFactory.getLogger(ZipUtils.class);

    public void zipFolder(String sourceFilePath, String zipFilePath) throws IOException {
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFilePath));
        File file = new File(sourceFilePath);
        file.setWritable(true);
        zipFiles(file.getParent() + File.separator, file.getName(), zipOutputStream);
        zipOutputStream.close();
    }

    @SuppressFBWarnings({"OBL_UNSATISFIED_OBLIGATION", "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE","OS_OPEN_STREAM"})
    private void zipFiles(String folderPath, String filePath, ZipOutputStream zipOutputStream) throws IOException {
        if (zipOutputStream == null)
            return;
        File file = new File(folderPath + filePath);
        file.setWritable(true);


        if (!file.getName().equalsIgnoreCase("iacscan.zip") &&
            !(file.isDirectory() && file.getName().startsWith("."))) {
            if (file.isFile()) {
                ZipEntry zipEntry = new ZipEntry(filePath);
                FileInputStream fileInputStream = new FileInputStream(file);
                zipOutputStream.putNextEntry(zipEntry);
                int len;
                byte[] buffer = new byte[1024];
                while ((len = fileInputStream.read(buffer)) != -1) {
                    zipOutputStream.write(buffer, 0, len);
                }
                zipOutputStream.closeEntry();

            } else {

                String[] fileList = file.list();
                if (fileList.length <= 0) {
                    ZipEntry zipEntry = new ZipEntry(filePath + File.separator);
                    zipOutputStream.putNextEntry(zipEntry);
                    zipOutputStream.closeEntry();
                }

                logger.info("Adding file recursively to ZIP");

                for (String aFileList : fileList) {
                    zipFiles(folderPath, filePath + File.separator + aFileList, zipOutputStream);
                }
            }
        }
    }
}