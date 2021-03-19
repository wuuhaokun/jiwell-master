package com.jiwell.order.pojo;




import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @Author: 98050
 * @Time: 2018-10-27 16:33
 * @Feature: 訂單類實體
 */
@Table(name = "tb_order")
public class Order {

    @Id
    private Long orderId;

    /**
     * 總金額
     */
    @NotNull
    private Double totalPay;
    /**
     * 實付金額
     */
    @NotNull
    private Double actualPay;

    /**
     * 支付類型，1、在線支付，2、貨到付款
     */
    @NotNull
    private Integer paymentType;

    /**
     * 參與促銷活動的id
     */
    private String promotionIds;

    /**
     * 郵費
     */
    private String postFee;

    /**
     * 創建時間
     */
    private Date createTime;

    /**
     * 物流名稱
     */
    private String shippingName;

    /**
     * 物流單號
     */
    private String shippingCode;

    /**
     * 用戶id
     */
    private Long userId;

    /**
     * 買家留言
     */
    private String buyerMessage;

    /**
     * 買家暱稱
     */
    private String buyerNick;

    /**
     * 買家是否已經評價
     */
    private Boolean buyerRate;

    /**
     * 收貨人全名
     */
    private String receiver;

    /**
     * 移動電話
     */
    private String receiverMobile;

    /**
     * 省份
     */
    private String receiverState;

    /**
     * 城市
     */
    private String receiverCity;

    /**
     * 區/縣
     */
    private String receiverDistrict;

    /**
     * 收貨地址，如：xx路xx號
     */
    private String receiverAddress;

    /**
     * 郵政編碼,如：310001
     */
    private String receiverZip;

    /**
     * 發票類型，0無發票，1普通發票，2電子發票，3增值稅發票
     */
    private Integer invoiceType;

    /**
     * 訂單來源 1:app端，2：pc端，3：M端，4：微信端，5：手機qq端
     */
    private Integer sourceType;

    @Transient
    private List<OrderDetail> orderDetails;

    @Transient
    private Integer status;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Double getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(Double totalPay) {
        this.totalPay = totalPay;
    }

    public Double getActualPay() {
        return actualPay;
    }

    public void setActualPay(Double actualPay) {
        this.actualPay = actualPay;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public String getPromotionIds() {
        return promotionIds;
    }

    public void setPromotionIds(String promotionIds) {
        this.promotionIds = promotionIds;
    }

    public String getPostFee() {
        return postFee;
    }

    public void setPostFee(String postFee) {
        this.postFee = postFee;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public String getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBuyerMessage() {
        return buyerMessage;
    }

    public void setBuyerMessage(String buyerMessage) {
        this.buyerMessage = buyerMessage;
    }

    public String getBuyerNick() {
        return buyerNick;
    }

    public void setBuyerNick(String buyerNick) {
        this.buyerNick = buyerNick;
    }

    public Boolean getBuyerRate() {
        return buyerRate;
    }

    public void setBuyerRate(Boolean buyerRate) {
        this.buyerRate = buyerRate;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiverState() {
        return receiverState;
    }

    public void setReceiverState(String receiverState) {
        this.receiverState = receiverState;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getReceiverDistrict() {
        return receiverDistrict;
    }

    public void setReceiverDistrict(String receiverDistrict) {
        this.receiverDistrict = receiverDistrict;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverZip() {
        return receiverZip;
    }

    public void setReceiverZip(String receiverZip) {
        this.receiverZip = receiverZip;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", totalPay=" + totalPay +
                ", actualPay=" + actualPay +
                ", paymentType=" + paymentType +
                ", promotionIds='" + promotionIds + '\'' +
                ", postFee='" + postFee + '\'' +
                ", createTime=" + createTime +
                ", shippingName='" + shippingName + '\'' +
                ", shippingCode='" + shippingCode + '\'' +
                ", userId=" + userId +
                ", buyerMessage='" + buyerMessage + '\'' +
                ", buyerNick='" + buyerNick + '\'' +
                ", buyerRate=" + buyerRate +
                ", receiver='" + receiver + '\'' +
                ", receiverMobile='" + receiverMobile + '\'' +
                ", receiverState='" + receiverState + '\'' +
                ", receiverCity='" + receiverCity + '\'' +
                ", receiverDistrict='" + receiverDistrict + '\'' +
                ", receiverAddress='" + receiverAddress + '\'' +
                ", receiverZip='" + receiverZip + '\'' +
                ", invoiceType=" + invoiceType +
                ", sourceType=" + sourceType +
                ", orderDetails=" + orderDetails +
                ", status=" + status +
                '}';
    }
}
