package com.platform.support.util;

import com.deepoove.poi.XWPFTemplate;
import com.google.common.collect.Maps;
import com.platform.common.util.IDGenerate;
import org.apache.commons.io.FilenameUtils;

import java.io.FileOutputStream;
import java.util.HashMap;

/**
 * 模板附件通过参数自动生成新的文件
 * 模板参数 {{org_full_name}}
 */
public class DocConvertUtil {

    public static void replaceTemplateAttach()
            throws Exception {
        String uuid = IDGenerate.getUUIDString();
        HashMap<String, Object> maps = Maps.newHashMap();
        maps.put("org_full_name", "长亮科技有限公司");
        maps.put("org_name", "长亮科技");
        maps.put("email", "zengzheng@sunline.cn");
        maps.put("usercode", "zengzheng");
        maps.put("newAttachName", "曾正入职邮件");
        XWPFTemplate template = null;
        String tempTemplatePath = "D:/test/hrgift." + FilenameUtils.getExtension("aaa.docx");//本地测试
        String newFilePath = "D:/test/"+uuid+".docx";
        //生成对应的模板文件
        template = XWPFTemplate.compile(tempTemplatePath).render(maps);
        FileOutputStream fos = new FileOutputStream(newFilePath);
        template.write(fos);
        fos.flush();
    }

    public static void main(String[] args) throws Exception {
        replaceTemplateAttach();
    }

}
