package com.jiwell.favorite.pojo;

import javax.persistence.*;
import java.util.Date;

@Table(name = "tb_shop_collection")
public class Collection {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long id;
    private Long userId;

    private Date createTime;

    private Date modifyTime;

    @Transient
    private String spuId;

    private String spuIds;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getSpuId() {
        return spuId;
    }

    public void setSpuId(String spuId) {
        this.spuId = spuId;
    }

    public String getSpuIds() {
        return spuIds;
    }

    public void setSpuIds(String spuIds) {
        this.spuIds = spuIds;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                " userId=" + userId +
                ", createTime='" + createTime + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                ", spuId='" + spuId + '\'' +
                ", spuIds='" + spuIds + '\'' +
                '}';
    }
}
