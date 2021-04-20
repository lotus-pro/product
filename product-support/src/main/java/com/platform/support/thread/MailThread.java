package com.platform.support.thread;

import com.alibaba.fastjson.JSONObject;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MailThread extends Thread {
    private String sendUserAccount;
    private String sendUserPassword;
    private String sendUserNickName;
    private String[] receiveUsers;
    private String[] copyUsers;
    private String[] darkUsers;
    private String subject;
    private String content;
    private File[] attachDocs;
    private boolean contentType;
    private JSONObject params;//参数集合

    public MailThread(String sendUserAccount, String sendUserPassword,
                      String sendUserNickName, String[] receiveUsers,
                      String[] copyUsers, String[] darkUsers, String subject,
                      String content, boolean contentType, File[] attachDocs) {
        super();
        this.sendUserAccount = sendUserAccount;
        this.sendUserPassword = sendUserPassword;
        this.sendUserNickName = sendUserNickName;
        this.receiveUsers = receiveUsers;
        this.copyUsers = copyUsers;
        this.darkUsers = darkUsers;
        this.content = content;
        this.subject = subject;
        this.contentType = contentType;
        this.attachDocs = attachDocs;
        setParams();
    }

    private void setParams(){
        params = new JSONObject();
        params.put("sendUserAccount", sendUserAccount);
        params.put("sendUserPassword", sendUserPassword);
        params.put("sendUserNickName", sendUserNickName);
        params.put("receiveUsers", receiveUsers);
        params.put("copyUsers", copyUsers);
        params.put("darkUsers", darkUsers);
        params.put("title", subject);
        params.put("text", content);
        if(attachDocs != null && attachDocs.length > 0){
            List<String> list = new ArrayList<String>();
            for(File f : attachDocs){
                list.add(f.getName());
            }
            params.put("attachDocs", list);
        }
    }

    public void run(){
        long startTime = System.currentTimeMillis();
        try {
            //获取邮件发送实例
            JavaMailSenderImpl mailSender = getJavaMailSenderImpl(sendUserAccount, sendUserPassword);
            //创建邮件帮助类
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true,"UTF-8");//必须true
            //设置邮件内容
            setMailContent(messageHelper,subject,content,contentType,attachDocs);
            //设置发送人
            setSenderUser(messageHelper, sendUserAccount,sendUserNickName);
            //设置接收人
            setReceiveUsers(messageHelper, receiveUsers, copyUsers, darkUsers);
            //发送
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            //邮件发送失败，可以将发送失败日志记录到数据库进行相关处理
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        String t = ((endTime-startTime)/(60*1000))+" 分 " + (((endTime -startTime)/1000.0)%60) + "秒";
        System.out.println("邮件发送耗时:" + t);
    }

    private  JavaMailSenderImpl  getJavaMailSenderImpl(String sendUserAccount,String sendUserPassword){
        //连接邮件服务器的参数配置
        Properties props = new Properties();
        //开启tls
        props.setProperty("mail.smtp.auth","true");
        props.setProperty("mail.smtp.ssl.enable", "true");
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        JavaMailSenderImpl impl = new JavaMailSenderImpl();
        impl.setHost("smtp.qq.com");
        impl.setUsername(sendUserAccount);
        impl.setPassword(sendUserPassword);
        impl.setPort(465);
        impl.setDefaultEncoding("UTF-8");
        impl.setProtocol("smtp");
        impl.setJavaMailProperties(props);
        //邮件详情日志打印
//        Session session = Session.getDefaultInstance(props);
//        session.setDebug(true);
//        impl.setSession(session);
        return impl;
    }

    private void setMailContent(MimeMessageHelper help, String subject, String content, boolean contentType, File[] attachDocs) throws MessagingException, UnsupportedEncodingException {
        //设置标题
        help.setSubject(subject);
        //设置文本内容
        help.setText(content, contentType);
        //添加附件
        if (attachDocs != null && attachDocs.length > 0) {
            for (File file : attachDocs) {
                //解决附件中文乱码
                help.addAttachment(MimeUtility.encodeWord(file.getName()), file);
            }
        }
    }

    /**
     * @param help
     * @param senderAccount 发送人账户
     * @param userName 发送人昵称
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    private void setSenderUser(MimeMessageHelper help,String senderAccount,String userName) throws MessagingException, UnsupportedEncodingException{
        if(userName != null){
            help.setFrom(senderAccount,userName);
        }else{
            help.setFrom(senderAccount);
        }
    }

    /**
     *
     * @param help
     * @param receiveUsers 接收人
     * @param copyUsers 抄送
     * @param darkUsers 密送
     * @throws MessagingException
     */
    private void setReceiveUsers(MimeMessageHelper help,String[] receiveUsers,String[] copyUsers,String[] darkUsers) throws MessagingException{
        if(receiveUsers != null){
            help.setTo(receiveUsers);
        }
        if(copyUsers != null && copyUsers.length > 0){
            help.setCc(copyUsers);
        }
//        if(darkUsers != null && darkUsers.length > 0){
//            help.setBcc(darkUsers);
//        }
    }
}
