package com.jiwell.mail.listener;

import com.jiwell.mail.service.MailService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: 98050
 * @Time: 2018-10-22 19:21
 * @Feature:Mail服務監聽器
 */
@Component
public class MailListener {

    @Autowired
    private MailService mailService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "jiwell.mail.queue",durable = "true"),
            exchange = @Exchange(value = "jiwell.mail.exchange",ignoreDeclarationExceptions = "true"),
            key = {"mail.verify.code"}
    ))
    public void listenSms(Map<String,String> msg){
        if (msg == null || msg.size() <= 0){
            //不做处理
            return;
        }
        //String mail = msg.get("phone");
        String password = msg.get("password");
        String mail = "w.sirius@gmail.com";
        String code = msg.get("code");
        String verifyCode = "你的驗証碼是" + (code == null?password:code);
//        if(!password.isEmpty()){
//            verifyCode = "你的驗証碼是" + (code.isEmpty()?password:code);
//        }
        try {
            mailService.sendSimpleMail("jiwell驗証碼", verifyCode,mail);
        }catch (Exception e){
            return;
        }
    }
}
