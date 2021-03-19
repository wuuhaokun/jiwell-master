package com.jiwell.upload.controller;

import com.jiwell.upload.service.serviceimpl.UploadServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: 98050
 * Time: 2018-08-09 14:36
 * Feature:
 */
@RestController
@RequestMapping("upload")
public class UploadController {
    @Autowired
    private UploadServiceImpl uploadServiceImpl;

    /**
     * 圖片上傳
     * @param file
     * @return
     */
    @PostMapping("image")
    public ResponseEntity<String> uploadImage(@RequestParam("file")MultipartFile file){
        String url= this.uploadServiceImpl.upload(file);
        if(StringUtils.isBlank(url)){
            //url為空，證明上傳失敗
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(url);
    }

    /**
     * 刪除文件
     *
     * @param filePath 文件路徑(groupName/path)
     */
    @DeleteMapping("image")
    public ResponseEntity<Boolean> deleteImage(String filePath){
        Boolean result = this.uploadServiceImpl.deleteFile(filePath);
        if(result == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().build();
    }

}
