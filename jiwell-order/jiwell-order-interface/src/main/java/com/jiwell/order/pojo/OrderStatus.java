package com.jiwell.order.pojo;



import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Author: 98050
 * @Time: 2018-10-27 16:34
 * @Feature: 訂單狀態實體類
 */
@Table(name = "tb_order_status")
public class OrderStatus {

    /**
     * 初始階段：1、未付款、未發貨；初始化所有數據
     * 付款階段：2、已付款、未發貨；更改付款時間
     * 發貨階段：3、已發貨，未確認；更改發貨時間、物流名稱、物流單號
     * 成功階段：4、已確認，未評價；更改交易結束時間
     * 關閉階段：5、關閉； 更改更新時間，交易關閉時間。
     * 評價階段：6、已評價
     */

    @Id
    private Long orderId;

    private Integer status;

    /**
     * 創建時間
     */
    private Date createTime;

    /**
     * 付款時間
     */
    private Date paymentTime;

    /**
     * 發貨時間
     */
    private Date consignTime;

    /**
     * 交易結束時間
     */
    private Date endTime;

    /**
     * 交易關閉時間
     */
    private Date closeTime;

    /**
     * 評價時間
     */
    private Date commentTime;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Date getConsignTime() {
        return consignTime;
    }

    public void setConsignTime(Date consignTime) {
        this.consignTime = consignTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    @Override
    public String toString() {
        return "OrderStatus{" +
                "orderId=" + orderId +
                ", status=" + status +
                ", createTime=" + createTime +
                ", paymentTime=" + paymentTime +
                ", consignTime=" + consignTime +
                ", endTime=" + endTime +
                ", closeTime=" + closeTime +
                ", commentTime=" + commentTime +
                '}';
    }
}
