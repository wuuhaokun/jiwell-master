package com.jiwell.upload.service.serviceimpl;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.jiwell.upload.service.UploadService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: 98050
 * Time: 2018-08-09 14:44
 * Feature:
 */
@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private FastFileStorageClient storageClient;

    private static final Logger logger= LoggerFactory.getLogger(UploadServiceImpl.class);

    /**
     * 支持上傳的文件類型
     */
    private static final List<String> suffixes = Arrays.asList("image/png","image/jpeg","image/jpg");


    @Override
    public String upload(MultipartFile file) {
        /**
         * 1.圖片信息校驗
         * 1)校驗文件類型
         * 2)校驗圖片內容
         * 2.保存圖片
         * 1)生成保存目錄
         * 2)保存圖片
         * 3)拼接圖片地址
         */
        try {
            String type = file.getContentType();
            if (!suffixes.contains(type)) {
                logger.info("上傳文件失敗，文件類型不匹配：{}", type);
                return null;
            }
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) {
                logger.info("上傳失敗，文件內容不符合要求");
                return null;
            }

// File dir = new File("G:\\jiwell\\upload");
// if (!dir.exists()){
// dir.mkdirs();
// }
// file.transferTo(new File(dir, Objects.requireNonNull(file.getOriginalFilename())));

            StorePath storePath = this.storageClient.uploadFile(
                    file.getInputStream(), file.getSize(), getExtension(file.getOriginalFilename()), null);

            //String url = "http://image.ji-well.com/upload/"+file.getOriginalFilename();
            String url = "http://image.ji-well.com/"+storePath.getFullPath();
// System.out.println(url);
            return url;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Boolean deleteFile(String filePath){
        try {
            this.storageClient.deleteFile(filePath);
            return true;
        }catch (Exception e){
            return null;
        }
    }

    public String getExtension(String fileName){
        return StringUtils.substringAfterLast(fileName,".");
    }
}
