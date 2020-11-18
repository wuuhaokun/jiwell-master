package com.jiwell.mail.service;

import freemarker.template.TemplateException;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Map;

/**
 * Author:       Caychen
 * Date:         2018/9/21
 * Interface:    org.com.cay.springboot.MailService
 * Desc:
 */
public interface MailService {

    void sendSimpleMail(String subject, String text,String mail);

    void sendHtmlMail(String subject, String text,String mail, Map<String, String> attachmentMap) throws MessagingException;

    void sendTemplateMail(String subject,String mail, Map<String, Object> params) throws MessagingException, IOException, TemplateException;
}
