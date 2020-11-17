package com.jiwell.fcm.service;

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

@Service
public interface PushNotificationService {

    void sendSamplePushNotification();
    void sendPushNotification(PushNotificationRequest request);
    void sendPushNotificationWithoutData(PushNotificationRequest request);
    void sendPushNotificationToToken(PushNotificationRequest request);
    void sendAllPushNotification(PushNotificationRequest request);

}
