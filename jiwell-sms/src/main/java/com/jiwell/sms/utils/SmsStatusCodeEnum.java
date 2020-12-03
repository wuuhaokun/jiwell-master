package com.jiwell.sms.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SmsStatusCodeEnum {

    STATUS_STAR("*", "系統發生錯誤，請聯絡三竹資訊窗口人員"),
    STATUS_a("a", "簡訊發送功能暫時停止服務，請稍候再試"),
    STATUS_b("b", "簡訊發送功能暫時停止服務，請稍候再試"),
    STATUS_c("c", "請輸入帳號"),
    STATUS_d("d", "請輸入密碼"),
    STATUS_e("e", "帳號、密碼錯誤"),
    STATUS_f("f", "帳號已過期"),
    STATUS_h("h", "帳號已被停用"),
    STATUS_k("k", "無效的連線位址"),
    STATUS_m("m", "必須變更密碼，在變更密碼前，無法使用簡訊發送服務"),
    STATUS_n("n", "密碼已逾期，在變更密碼前，將無法使用簡訊發送服務"),
    STATUS_p("p", "沒有權限使用外部Http程式"),
    STATUS_r("r", "系統暫停服務，請稍後再試"),
    STATUS_s("s", "帳務處理失敗，無法發送簡訊"),
    STATUS_t("t", "簡訊已過期"),
    STATUS_u("u", "簡訊內容不得為空白"),
    STATUS_v("v", "無效的手機號碼"),
    STATUS_0("0", "預約傳送中"),
    STATUS_1("1", "已送達業者"),
    STATUS_2("2", "已送達業者"),
    STATUS_3("3", "已送達業者"),
    STATUS_4("4", "已送達手機"),
    STATUS_5("5", "內容有錯誤"),
    STATUS_6("6", "門號有錯誤"),
    STATUS_7("7", "簡訊已停用"),
    STATUS_8("8", "逾時無送達"),
    STATUS_9("9", "預約已取消");

    @Getter
    private String code;

    @Getter
    private String description;

}
