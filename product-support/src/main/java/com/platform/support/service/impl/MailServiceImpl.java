package com.platform.support.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.platform.common.enums.MailStatusEnum;
import com.platform.common.pojo.support.T1PubMessage;
import com.platform.common.util.IDGenerate;
import com.platform.support.mapper.T1PubMessageMapper;
import com.platform.support.service.MailService;
import com.platform.support.thread.MailThread;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    JavaMailSender jms;
    @Autowired
    T1PubMessageMapper pubMessageMapper;
    private ExecutorService executor = Executors.newFixedThreadPool(3);

    @Override
    public void sendMail() throws Exception {
        String sendUserAccount = "1107037470@qq.com";
        String sendUserPassword = "sebjutgfduztjgej";
        String sendUserNickName = "曾正";
        String[] receiveUsers =new String[]{ "18162224875@163.com"};
        String[] copyUsers = new String[]{"1107037470@qq.com"};
        String[] darkUsers = new String[]{"1107037470@qq.com"};
        String title = "诗酒人生";
        String text = "<a href='http://www.w3school.com.cn'>W3School</a>";
//		File[] bodyImgs = new File[]{new File("e:/1.jpg")};
//		File[] bodyImgs = null;
        //文件名不要出现空格
        File[] attachDocs = new File[]{new File("D:/test/5d5676620d3c4c41a7ba37a63dcb0b27.docx")};
//		File[] attachDocs = null;
        sendMail(sendUserAccount, sendUserPassword, sendUserNickName, receiveUsers, copyUsers, darkUsers, title, text, true, attachDocs);
        System.out.println("已异步发送。。。");
    }

    @XxlJob("ScanNoSendMail")
    public void sendEMail() throws Exception {
        LambdaQueryWrapper<T1PubMessage> wrapper = new LambdaQueryWrapper();
        wrapper.eq(T1PubMessage::getSendStatus, MailStatusEnum.NO_SEND.getCode());
        List<T1PubMessage> messageList = pubMessageMapper.selectList(wrapper);
        messageList.forEach( message -> {
            String sender = message.getSender();
            String nickName = message.getNickName();
            String carbonCopy = message.getCarbonCopy();
            String darkCopy = message.getDarkCopy();
            String receiver = message.getReceiver();
            String subject = message.getSubject();
            String contentType = message.getContentType();
            String content = message.getContent();
            boolean emailType = contentType.equals("2") ? false : true;
            T1PubMessage pubMessage = new T1PubMessage();
            pubMessage.setMessageNo(message.getMessageNo());
            String[] darkCopyList = StringUtils.isNotEmpty(darkCopy) ? darkCopy.split(";") : null;
            try {
                File[] attachDocs = new File[]{new File("D:/test/5d5676620d3c4c41a7ba37a63dcb0b27.docx")};
                sendMail(sender, "sebjutgfduztjgej", nickName, receiver.split(";"),
                        carbonCopy.split(";"), darkCopyList, subject, content, emailType, attachDocs);
                pubMessage.setSendStatus(MailStatusEnum.SUCCESS.getCode());
                pubMessageMapper.updateById(pubMessage);
                log.info("已异步发送。。。");
            } catch (Exception e) {
                log.error(e+"");
                pubMessage.setSendStatus(MailStatusEnum.FAIL.getCode());
                pubMessageMapper.updateById(pubMessage);
            }
        });
    }

    public static void main(String[] args) throws Exception {
        String sendUserAccount = "1107037470@qq.com";
        String sendUserPassword = "sebjutgfduztjgej";
        String sendUserNickName = "曾正";
        String[] receiveUsers =new String[]{ "18162224875@163.com"};
        String[] copyUsers = new String[]{"1107037470@qq.com"};
        String[] darkUsers = new String[]{"1107037470@qq.com"};
        String title = "诗酒人生";
        String text = "<a href='http://www.w3school.com.cn'>W3School</a>";
        File[] bodyImgs = new File[]{new File("e:/1.png")};
        //文件名不要出现空格
        File[] attachDocs = new File[]{new File("D:/test/5d5676620d3c4c41a7ba37a63dcb0b27.docx")};
//		File[] attachDocs = null;
//        sendMail(sendUserAccount, sendUserPassword, sendUserNickName, receiveUsers, copyUsers, darkUsers, title, text, bodyImgs, attachDocs);
        System.out.println("已异步发送。。。");
        System.out.println(IDGenerate.getUUIDString());
    }

    public void sendMail(String sendUserAccount, String sendUserPassword, String sendUserNickName, String[] receiveUsers, String[] copyUsers, String[] darkUsers,
                                String title, String text, boolean contentType, File[] attachDocs){

        MailThread mail = new MailThread(sendUserAccount, sendUserPassword, sendUserNickName, receiveUsers, copyUsers, darkUsers, title, text,  contentType, attachDocs);
        executor.execute(mail);
//        executor.shutdown();
    }

}
