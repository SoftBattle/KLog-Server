package org.softbattle.klog_server.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

public class FileUtil {

    public static void upload(MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        String filePath = "?";

        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file.getBytes());
        out.flush();
        out.close();
    }

}

