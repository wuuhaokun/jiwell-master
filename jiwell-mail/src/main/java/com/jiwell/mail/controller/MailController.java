package com.jiwell.mail.controller;

import com.jiwell.mail.service.MailService;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Map;

/**
 * @Author: 98050
 * Time: 2018-08-07 19:18
 * Feature:
 */
@RestController
@RequestMapping("mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("send/simpleMail")
    public ResponseEntity<Void> sendSimpleMail(String subject, String text, String mail){
        mailService.sendSimpleMail(subject, text,mail);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("send/htmlMail")
    public ResponseEntity<Void> sendHtmlMail(String subject, String text, String mail, Map<String, String> attachmentMap) throws MessagingException {
        mailService.sendHtmlMail(subject, text,mail, attachmentMap);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("send/TemplateMail")
    public ResponseEntity<Void> sendTemplateMail(String subject, String mail, Map<String, Object> params) throws MessagingException, IOException, TemplateException {
        mailService.sendTemplateMail(subject,mail, params);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

//Mac 專用的應用程式密碼:nsgupoiakdhltfte
