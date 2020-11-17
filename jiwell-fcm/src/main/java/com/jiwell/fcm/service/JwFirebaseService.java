package com.jiwell.fcm.service;
import com.jiwell.fcm.pojo.JwFirebase;
import org.springframework.stereotype.Service;

@Service
public interface JwFirebaseService {

    boolean saveJwFirebase(JwFirebase jwFirebase);

    String queryTokenByUserId(Long userId);


}
