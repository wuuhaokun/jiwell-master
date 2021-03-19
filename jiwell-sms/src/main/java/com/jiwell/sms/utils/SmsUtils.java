package com.jiwell.sms.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.jiwell.sms.pojo.SmsProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @Author: 98050
 * @Time: 2018-10-22 18:59
 * @Feature: 短信服務工具類
 */
@Component
//@EnableConfigurationProperties(SmsProperties.class)
public class SmsUtils {

    @Autowired
    private SmsProperties properties;

    /**
     * 產品名稱:云通信短信API產品,開發者無需替換
     */
    static final String product = "Dysmsapi";
    /**
     * 產品域名,開發者無需替換
     */
    static final String domain = "dysmsapi.aliyuncs.com";

    static final Logger logger = LoggerFactory.getLogger(SmsUtils.class);

    public  SendSmsResponse sendSms(String phone,String code,String signName,String template) throws ClientException {

//可自助調整超時時間
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暫不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", properties.getAccessKeyId(), properties.getAccessKeySecret());
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //組裝請求對象-具體描述見控制台-文檔部分內容
        SendSmsRequest request = new SendSmsRequest();
        request.setMethod(MethodType.POST);
        //必填:待發送手機號
        request.setPhoneNumbers(phone);
        //必填:短信簽名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(template);
        //可選:模板中的變量替換JSON串,如模板內容為"親愛的${name},您的驗證碼為${code}"時,此處的值為
        request.setTemplateParam("{\"code\":\"" + code + "\"}");

        //選填-上行短信擴展碼(無特殊需求用戶請忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可選:outId為提供給業務方擴展字段,最終在短信回執消息中將此值帶回給調用者
        request.setOutId("123456");

        //hint 此處可能會拋出異常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
    }

    public  Boolean sendSmexpress(String phone,String code,String signName,String template) throws ClientException {
//        StringBuffer reqUrl = new StringBuffer(); reqUrl.append("https://{三竹網域名稱}/b2c/mtk/SmBulkSend?"); reqUrl.append("username=username"); reqUrl.append("&password=password"); reqUrl.append("&Encoding_PostIn=UTF-8"");
//        StringBuffer body = new StringBuffer(); body.append("001$$0900000000$$20170101000000$$20170102000000$$$$$$簡訊SmBulkSend測 試").append("\r\n"); body.append("002$$0900000001$$20170101000000$$20170102000000$$$$$$簡訊SmBulkSend測 試").append("\r\n");
//        URL url = new URL(reqUrl.toString());
//        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
//        urlConnection.setRequestMethod("POST"); urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); urlConnection.setDoOutput(true);
//        urlConnection.connect();
//        DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream()); out.write(body.toString().getBytes("UTF-8"));
//        out.flush();
//        out.close();
//        RestTemplate restTemplate = new RestTemplate();
//        String url = "http://47.xxx.xxx.96/register/checkEmail";
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
//        map.add("email", "844072586@qq.com");
//
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
//        ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
//        System.out.println(response.getBody());
        return true;
    }

}
//三竹網域名稱 ＝ http://msg2.mitake.com.tw/
//http://smexpress.mitake.com.tw/SmSendGet.asp

//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.List;
//
//import lombok.extern.slf4j.Slf4j;
//
//import org.apache.commons.lang.StringUtils;
//
//import com.google.common.collect.Lists;
//
///**
// * username = 使用者帳號。SmGateway資料庫表格SMUser中需有此使用者，且狀態為啟用。 <br>
// * password = 使用者密碼 <br>
// * dstaddr = 受訊方手機號碼 <br>
// * DestName = 收訊人名稱。若其他系統需要與簡訊資料進行系統整合，此欄位可填入來源系統所產生的Key值，以對應回來源資料庫 <br>
// * dlvtime = 簡訊預約時間。格式為YYYY-MM-DD HH:NN:SS或YYYYMMDDHHNNSS，或是整數值代表幾秒後傳送。<br>
// * vldtime = 簡訊有效期限。格式為YYYY-MM-DD HH:NN:SS或YYYYMMDDHHNNSS，或是整數值代表傳送後幾秒後內有效。 <br>
// * smbody = 簡訊內容。必須為BIG-5編碼，長度70個中文字或是160個英數字。若有換行的需求，請填入ASCII Code 6代表換行。 <br>
// */
//@Slf4j
//public class SmsUtils {
//
//    private final static String USER_AGENT = "Mozilla/5.0";
//
//    private final String SMS_URL = "http://smexpress.mitake.com.tw/SmSendGet.asp?";
//    private final String USER_NAME = "0987301663";
//    private final String PASSWORD = "8786107";
//
////    public static void main(String[] args) throws Exception {
////        String phone = "09";
////        String smBody = "您的驗證碼為○○○○○○，請於收到簡訊10分鐘內完成驗證。";
////        new SmsUtils().sendSms(phone, smBody);
////    }
//
//    public void sendSms(String phoneNumber, String body) throws Exception {
//        body = URLEncoder.encode(body, "Big5");
//        sendGet(phoneNumber, body);
//    }
//
//    private void sendGet(String phoneNumber, String body) throws IOException {
//
//        StringBuilder url = new StringBuilder();
//        url.append(SMS_URL);
//        url.append("username=").append(USER_NAME);
//        url.append("&password=").append(PASSWORD);
//        url.append("&dstaddr=").append(phoneNumber);
//        url.append("&smbody=").append(body);
//
//        URL obj = null;
//        BufferedReader bufferedReader = null;
//        try {
//            obj = new URL(url.toString());
//            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//
//            // optional default is GET
//            con.setRequestMethod("GET");
//
//            // add request header
//            con.setRequestProperty("User-Agent", USER_AGENT);
//
//            int responseCode = con.getResponseCode();
//
//            log.debug("\nSending 'GET' request to URL : " + url);
//            log.debug("Response Code : " + responseCode);
//
//            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
//            String inputLine = "";
//            StringBuffer response = new StringBuffer();
//
//            while ((inputLine = bufferedReader.readLine()) != null) {
//                response.append(inputLine);
//            }
//
//            // print result
//            String responseStr = response.toString();
//            log.debug(responseStr);
//
//            int from = responseStr.indexOf("statuscode=");
//            int to = responseStr.indexOf("AccountPoint=");
//            String tmpStr = StringUtils.substring(responseStr, from, to);
//
//            // 取得status code
//            String statusCode = StringUtils.substring(tmpStr, tmpStr.length() - 1, tmpStr.length());
//            log.info("statusCode = " + statusCode);
//
//            // 將status code 轉成 中文訊息
//            String executionResult = getDescription(statusCode);
//            log.info("executionResult = " + executionResult);
//
//            List<String> successList = Lists.newArrayList("0", "1", "2", "3", "4"); // 成功的SATUSCODE
//            if (!successList.contains(statusCode)) {
//                throw new RuntimeException(executionResult);
//            }
//
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } finally {
//            if (bufferedReader != null) {
//                bufferedReader.close();
//            }
//        }
//    }
//
//    /**
//     * 將status code 轉成 中文訊息
//     *
//     * @param statusCode
//     * @return 中文訊息
//     */
//    private String getDescription(String statusCode) {
//        String description = "";
//        for (SmsStatusCodeEnum smsStatusCode : SmsStatusCodeEnum.values()) {
//            if (smsStatusCode.getCode().equals(statusCode)) {
//                description = smsStatusCode.getDescription();
//            }
//        }
//        return description;
//    }
//}
