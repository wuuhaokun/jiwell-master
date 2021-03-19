package com.jiwell.mail.service.impl;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import com.jiwell.mail.properties.MailProperties;
import com.jiwell.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Author:       Caychen
 * Class:        MailServiceImpl
 * Date:         2018/9/21
 * Desc:
 */
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailProperties mailProperties;

    @Override
    public void sendSimpleMail(String subject, String text,String mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailProperties.getFrom());
        //mailMessage.setTo(mailProperties.getTo());
        mailMessage.setTo(mail);//可以送多個以豆點隔開 如:sirius@gmail.com,jiwell.wu@gmail.com
        mailMessage.setSubject(subject);
        mailMessage.setText(text);

        javaMailSender.send(mailMessage);
    }
    @Override
    public void sendHtmlMail(String subject, String text,String mail, Map<String, String> attachmentMap) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        //是否發送的郵件是富文本（附件，圖片，html等）
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

        messageHelper.setFrom(mailProperties.getFrom());
        messageHelper.setTo(mailProperties.getTo());

        messageHelper.setSubject(subject);
        messageHelper.setText(text, true);

        if(attachmentMap != null){
            attachmentMap.entrySet().stream().forEach(entrySet -> {
                try {
                    File file = new File(entrySet.getValue());
                    if(file.exists()){
                        messageHelper.addAttachment(entrySet.getKey(), new FileSystemResource(file));
                    }
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
        }
        javaMailSender.send(mimeMessage);
    }

    @Override
    public void sendTemplateMail(String subject,String mail, Map<String, Object> params) throws MessagingException, IOException, TemplateException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom(mailProperties.getFrom());
        helper.setTo(mailProperties.getTo());

        Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
        configuration.setClassForTemplateLoading(this.getClass(), "/templates");

        String html = FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate("mail.ftl"), params);

        helper.setSubject(subject);
        helper.setText(html, true);

        javaMailSender.send(mimeMessage);
    }
}

//https://www.linjia.site/2017/06/16/Javamail使用gmail发送邮件/
