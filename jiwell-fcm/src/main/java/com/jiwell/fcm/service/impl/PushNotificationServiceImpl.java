package com.jiwell.fcm.service.impl;

//import com.jiwell.fcm.firebase.FCMService;
//import com.jiwell.fcm.entity.PushNotificationRequest;
//import com.jiwell.fcm.mapper.JwFirebaseMapper;
//import com.jiwell.fcm.pojo.JwFirebase;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.ExecutionException;
//
//@Service
//public class PushNotificationService {
//
//    @Value("#{${app.notifications.defaults}}")
//    private Map<String, String> defaults;
//
//    private Logger logger = LoggerFactory.getLogger(PushNotificationService.class);
//    private FCMService fcmService;
//
//    public PushNotificationService(FCMService fcmService) {
//        this.fcmService = fcmService;
//    }
//
//    @Scheduled(initialDelay = 60000, fixedDelay = 60000)
//    public void sendSamplePushNotification() {
//        try {
//            fcmService.sendMessageWithoutData(getSamplePushNotificationRequest());
//        } catch (InterruptedException | ExecutionException e) {
//            logger.error(e.getMessage());
//        }
//    }
//
//    public void sendPushNotification(PushNotificationRequest request) {
//        try {
//            fcmService.sendMessage(getSamplePayloadData(), request);
//        } catch (InterruptedException | ExecutionException e) {
//            logger.error(e.getMessage());
//        }
//    }
//
//    public void sendPushNotificationWithoutData(PushNotificationRequest request) {
//        try {
//            fcmService.sendMessageWithoutData(request);
//        } catch (InterruptedException | ExecutionException e) {
//            logger.error(e.getMessage());
//        }
//    }
//
//    public void sendPushNotificationToToken(PushNotificationRequest request) {
//        try {
//            fcmService.sendMessageToToken(request);
//        } catch (InterruptedException | ExecutionException e) {
//            logger.error(e.getMessage());
//        }
//    }
//
//    private Map<String, String> getSamplePayloadData() {
//        Map<String, String> pushData = new HashMap<>();
//        pushData.put("messageId", defaults.get("payloadMessageId"));
//        pushData.put("text", defaults.get("payloadData") + " " + LocalDateTime.now());
//        return pushData;
//    }
//
//    private PushNotificationRequest getSamplePushNotificationRequest() {
//        PushNotificationRequest request = new PushNotificationRequest(defaults.get("title"),
//                defaults.get("message"),
//                defaults.get("topic"));
//        return request;
//    }
//
//    public void sendAllPushNotification(PushNotificationRequest request) {
//        try {
//            fcmService.sendAllMessage(request);
//        } catch (InterruptedException | ExecutionException e) {
//            logger.error(e.getMessage());
//        }
//    }
//
//}


import com.jiwell.fcm.service.PushNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiwell.fcm.firebase.FCMService;
import com.jiwell.fcm.entity.PushNotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @Author: 98050
 * @Time: 2018-10-23 22:47
 * @Feature:
 */
@Service
public class PushNotificationServiceImpl implements PushNotificationService {

//    @Autowired
//    private JwFirebaseMapper jwFirebaseMapper;

    @Value("#{${app.notifications.defaults}}")
    private Map<String, String> defaults;

    private Logger logger = LoggerFactory.getLogger(PushNotificationService.class);

    @Autowired
    FCMService fcmService;

    /**
     * sendSamplePushNotification
     * @param
     * @return
     */

    @Scheduled(initialDelay = 60000, fixedDelay = 60000)
    @Override
    public void sendSamplePushNotification() {
        try {
            fcmService.sendMessageWithoutData(getSamplePushNotificationRequest());
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void sendPushNotification(PushNotificationRequest request) {
        try {
            fcmService.sendMessage(getSamplePayloadData(), request);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void sendPushNotificationWithoutData(PushNotificationRequest request) {
        try {
            fcmService.sendMessageWithoutData(request);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void sendPushNotificationToToken(PushNotificationRequest request) {
        try {
            fcmService.sendMessageToToken(request);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void sendAllPushNotification(PushNotificationRequest request) {
        try {
            fcmService.sendAllMessage(request);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }

    private Map<String, String> getSamplePayloadData() {
        Map<String, String> pushData = new HashMap<>();
        pushData.put("messageId", defaults.get("payloadMessageId"));
        pushData.put("text", defaults.get("payloadData") + " " + LocalDateTime.now());
        return pushData;
    }

    private PushNotificationRequest getSamplePushNotificationRequest() {
        PushNotificationRequest request = new PushNotificationRequest(defaults.get("title"),
                defaults.get("message"),
                defaults.get("topic"));
        return request;
    }
}
