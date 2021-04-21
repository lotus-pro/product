package com.platform.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

public class FilePathUtil {

    private static final Logger log = LoggerFactory.getLogger(FilePathUtil.class);

    private FilePathUtil() {
    }

    public static String getBasedir() {
        return System.getProperty("system.basedir");
    }

    public static String getTempPath() {
        String tempPath = "temp";
        String basedir = getBasedir();
        if (StringUtils.isEmpty(basedir)) {
            try {
                return ResourceUtils.getURL("classpath:").getPath().concat(tempPath).concat(File.separator);
            } catch (FileNotFoundException var3) {
                log.error("", var3);
                return "";
            }
        } else {
            return basedir.concat(File.separator).concat(tempPath).concat(File.separator);
        }
    }
}
