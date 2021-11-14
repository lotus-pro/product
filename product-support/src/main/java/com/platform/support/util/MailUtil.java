package com.platform.support.util;

import com.platform.common.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Objects;


@Slf4j
@Component("mailUtil")
public class MailUtil {

    //默认编码
    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final String HTML_MAIL = "html";
    public static final String HTML_TEXT = "text";
    //本身邮件的发送者，来自邮件配置
    @Value("${spring.mail.username}")
    private String userName;
    @Value("${spring.mail.nickname}")
    private String nickname;

    @Autowired(required = false)
    private JavaMailSender mailSender;

    public void sendMail(MailDto mailDto) {
        String subject = mailDto.getSubject();
        String[] receiver = mailDto.getReceiver();
        String content = mailDto.getContent();
        //检验参数：邮件主题、收件人、邮件内容必须不为空才能够保证基本的逻辑执行
        if (StringUtils.isBlank(subject) || StringUtils.isBlank(content) || Objects.isNull(receiver) || receiver.length == 0) {
            log.error("邮件-> {} 无法继续执行，因为缺少基本的参数：邮件主题、收件人、邮件内容", subject);
            throw new CommonException("模板邮件无法继续发送，因为缺少必要的参数！");
        }
        sendSimpleMail(mailDto);
    }

    private void sendSimpleMail(MailDto mailDto) {
        String[] attachments = mailDto.getAttachments();
        String subject = mailDto.getSubject();
        String mailType = mailDto.getMailType();
        mailType = StringUtils.isBlank(mailType) ? HTML_MAIL : HTML_TEXT;
        boolean isHtml = mailType.equals(HTML_MAIL) ? true : false;
        log.info("开始发送邮件：主题->{}", subject);
        //附件处理，需要处理附件时，需要使用二进制信息，使用 MimeMessage 类来进行处理
        try {
            if (Objects.nonNull(attachments) && attachments.length > 0) {
                //附件处理需要进行二进制传输
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, DEFAULT_ENCODING);
                //设置邮件的基本信息：处理基本信息出现错误
                if (!handleBasicInfo(helper, mailDto, isHtml)) {
                    log.error("邮件基本信息出错: 主题->{}", subject);
                    return;
                }
                //处理附件
                handleAttachment(helper, subject, attachments);
                //发送该邮件
                mailSender.send(mimeMessage);
            } else {
                //创建一个简单邮件信息对象
                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                //设置邮件的基本信息
                handleBasicInfo(simpleMailMessage, mailDto);
                //发送邮件
                mailSender.send(simpleMailMessage);
            }
            log.info("发送邮件成功: 主题->{}", subject);
        } catch (MessagingException e) {
            log.error("发送邮件失败: 主题->{}", subject);
        }
    }

    /**
     * 处理二进制邮件的基本信息，比如需要带附件的文本邮件、HTML文件、图片邮件、模板邮件等等
     *
     * @param mimeMessageHelper：二进制文件的包装类
     * @param isHtml：是否是HTML文件，用于区分带附件的简单文本邮件和真正的HTML文件
     * @return ：返回这个过程中是否出现异常，当出现异常时会取消邮件的发送
     */
    private boolean handleBasicInfo(MimeMessageHelper mimeMessageHelper, MailDto mailDto, boolean isHtml) {
        String subject = mailDto.getSubject();
        String[] receiver = mailDto.getReceiver();
        String content = mailDto.getContent();
        String[] carbonCopy = mailDto.getCarbonCopy();
        String[] blindCarbonCopy = mailDto.getBlindCarbonCopy();
        try {
            //设置必要的邮件元素
            //设置发件人
            mimeMessageHelper.setFrom(nickname + '<' + userName + '>');
            //设置邮件的主题
            mimeMessageHelper.setSubject(subject);
            //设置邮件的内容，区别是否是HTML邮件
            mimeMessageHelper.setText(content, isHtml);
            //设置邮件的收件人
            mimeMessageHelper.setTo(receiver);
            //设置非必要的邮件元素，在使用helper进行封装时，这些数据都不能够为空
            if (Objects.nonNull(carbonCopy) && carbonCopy.length > 0) {
                //设置邮件的抄送人：MimeMessageHelper # Assert.notNull(cc, "Cc address array must not be null");
                mimeMessageHelper.setCc(carbonCopy);
            }
            if (Objects.nonNull(blindCarbonCopy) && blindCarbonCopy.length > 0)
                //设置邮件的密送人：MimeMessageHelper # Assert.notNull(bcc, "Bcc address array must not be null");
                mimeMessageHelper.setBcc(blindCarbonCopy);
            return true;
        } catch (MessagingException e) {
            log.error("邮件基本信息出错->{}", subject);
        }
        return false;
    }

    /**
     * 用于填充简单文本邮件的基本信息
     *
     * @param simpleMailMessage：文本邮件信息对象
     */
    private void handleBasicInfo(SimpleMailMessage simpleMailMessage, MailDto mailDto) {
        String subject = mailDto.getSubject();
        String[] receiver = mailDto.getReceiver();
        String content = mailDto.getContent();
        String[] carbonCopy = mailDto.getCarbonCopy();
        String[] blindCarbonCopy = mailDto.getBlindCarbonCopy();
        //设置发件人
        simpleMailMessage.setFrom(nickname + '<' + userName + '>');
        //设置邮件的主题
        simpleMailMessage.setSubject(subject);
        //设置邮件的内容
        simpleMailMessage.setText(content);
        //设置邮件的收件人
        simpleMailMessage.setTo(receiver);
        //设置邮件的抄送人
        simpleMailMessage.setCc(carbonCopy);
        //设置邮件的密送人
        simpleMailMessage.setBcc(blindCarbonCopy);
    }

    /**
     * 用于处理附件信息，附件需要 MimeMessage 对象
     *
     * @param mimeMessageHelper：处理附件的信息对象
     * @param subject：邮件的主题，用于日志记录
     * @param attachmentFilePaths：附件文件的路径，该路径要求可以定位到本机的一个资源
     */
    private void handleAttachment(MimeMessageHelper mimeMessageHelper, String subject, String[] attachmentFilePaths) {
        //判断是否需要处理邮件的附件
        if (Objects.nonNull(attachmentFilePaths) && attachmentFilePaths.length > 0) {
            FileSystemResource resource;
            String fileName;
            //循环处理邮件的附件
            for (String attachmentFilePath : attachmentFilePaths) {
                //获取该路径所对应的文件资源对象
                resource = new FileSystemResource(new File(attachmentFilePath));
                //判断该资源是否存在，当不存在时仅仅会打印一条警告日志，不会中断处理程序。
                // 也就是说在附件出现异常的情况下，邮件是可以正常发送的，所以请确定你发送的邮件附件在本机存在
                if (!resource.exists()) {
                    log.warn("邮件->{} 的附件->{} 不存在！", subject, attachmentFilePath);
                    //开启下一个资源的处理
                    continue;
                }
                //获取资源的名称
                fileName = resource.getFilename();
                try {
                    //添加附件
                    mimeMessageHelper.addAttachment(fileName, resource);
                } catch (MessagingException e) {
                    log.error("邮件->{} 添加附件->{} 出现异常->{}", subject, attachmentFilePath, e.getMessage());
                }
            }
        }
    }
}
