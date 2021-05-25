package com.platform.common.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

    /**
     * 批量文件文件下载
     * @param response  http响应
     * @param remoteFiles 服务器文件路径+名称  所有文件上传均在/data/父级目录下，校验直接抛错
     */
    public static void downloadBatchFile(HttpServletResponse response, List<String> remoteFiles) throws Exception {
        String zipFileName = DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN) + ".zip";
        response.reset(); // 重点突出
        response.setCharacterEncoding("UTF-8"); // 重点突出
        response.setContentType("application/x-msdownload"); // 不同类型的文件对应不同的MIME类型 // 重点突出
        // 对文件名进行编码处理中文问题
        zipFileName = new String(zipFileName.getBytes(), StandardCharsets.UTF_8);
        // inline在浏览器中直接显示，不提示用户下载
        // attachment弹出对话框，提示用户进行下载保存本地
        // 默认为inline方式
        response.setHeader("Content-Disposition", "attachment;filename=" + zipFileName);
        //文件压缩打包
        ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
        zipFile(remoteFiles, zos);
    }

    /**
     * 压缩文件
     * @param filePaths 需要压缩的文件路径集合
     * @throws IOException
     */
    private static void zipFile(List<String> filePaths, ZipOutputStream zos) {
        //设置读取数据缓存大小
        byte[] buffer = new byte[4096];
        try {
            //循环读取文件路径集合，获取每一个文件的路径
            for (String filePath : filePaths) {
                File inputFile = new File(filePath);
                //判断文件是否存在
                if (inputFile.exists()) {
                    //判断是否属于文件，还是文件夹
                    if (inputFile.isFile()) {
                        //创建输入流读取文件
                        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inputFile));
                        //将文件写入zip内，即将文件进行打包
                        zos.putNextEntry(new ZipEntry(inputFile.getName()));
                        //写入文件的方法，同上
                        int size = 0;
                        //设置读取数据缓存大小
                        while ((size = bis.read(buffer)) > 0) {
                            zos.write(buffer, 0, size);
                        }
                        //关闭输入输出流
                        zos.closeEntry();
                        bis.close();
                    } else {  //如果是文件夹，则使用穷举的方法获取文件，写入zip
                        File[] files = inputFile.listFiles();
                        List<String> filePathsTem = new ArrayList<String>();
                        for (File fileTem : files) {
                            filePathsTem.add(fileTem.toString());
                        }
                        zipFile(filePathsTem, zos);
                    }
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if (null != zos) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
