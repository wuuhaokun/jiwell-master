package com.jiwell.fcm.controller;
import com.jiwell.fcm.pojo.JwFirebase;
import com.jiwell.fcm.service.JwFirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class JwFirebaseController {

    @Autowired
    private JwFirebaseService jwFireBaseService;

    @PostMapping("/firebase/token")
    public ResponseEntity<Void> saveJwFirebase(@RequestBody JwFirebase jwFirebase){
        Boolean result = this.jwFireBaseService.saveJwFirebase(jwFirebase);
        if(result == null || !result){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

//    @PostMapping("/firebase/token")
//    public ResponseEntity<Void> saveJwFirebase(@RequestBody JwFirebase jwFirebase){
//        Boolean result = this.jwFireBaseService.saveJwFirebase(jwFirebase);
//        if(result == null || !result){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }

}
