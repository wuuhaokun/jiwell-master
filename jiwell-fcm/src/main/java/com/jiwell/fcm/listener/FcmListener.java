package com.jiwell.fcm.listener;

import com.jiwell.fcm.entity.PushNotificationRequest;
import com.jiwell.fcm.firebase.FCMService;
import com.jiwell.fcm.service.JwFirebaseService;
import com.jiwell.fcm.service.PushNotificationService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @Author: 98050
 * @Time: 2018-10-22 19:21
 * @Feature:短信服务监听器
 */
@Component
public class FcmListener {

    @Autowired
    private FCMService fcmService;
    //private PushNotificationService pushNotificationService;

    @Autowired
    private JwFirebaseService jwFireBaseService;

//    @Value("#{${app.notifications.defaults}}")
//    private Map<String, String> defaults;
//    @Autowired
//    private SmsProperties smsProperties;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "jiwell.fcm.queue",durable = "true"),
            exchange = @Exchange(value = "jiwell.fcm.exchange",ignoreDeclarationExceptions = "true"),
            key = {"fcm.verify.code"}
    ))
    public void listenFcm(Map<String,String> msg){
        if (msg == null || msg.size() <= 0){
            //不做处理
            return;
        }
        String userId = msg.get("userId");
        String token = jwFireBaseService.queryTokenByUserId(Long.valueOf(userId));
        //添加tokens
        List<String> tokens = Arrays.asList(token.split(","));

        if(token == null || token == ""){
            return;
        }
        for (String firebaseToken:tokens) {
            PushNotificationRequest request = new PushNotificationRequest();
            request.setTitle("格上測試");
            request.setMessage("Welcome");
            request.setToken(firebaseToken);
            try {
                fcmService.sendMessageToToken(request);
            } catch (InterruptedException | ExecutionException e) {
                //logger.error(e.getMessage());
                return;
            }
        }


        //for(String firebaseToken in tokens)
        //pushNotificationService.sendAllPushNotification();

//        if (StringUtils.isNotBlank(phone) && StringUtils.isNotBlank(code)){
//            //发送消息
//            try {
//                //SendSmsResponse response = this.smsUtils.sendSms(phone, code, smsProperties.getSignName(), smsProperties.getVerifyCodeTemplate());
//            }catch (ClientException e){
//                return;
//            }
//        }else {
//            //不做处理
//            return;
//        }
    }
}
