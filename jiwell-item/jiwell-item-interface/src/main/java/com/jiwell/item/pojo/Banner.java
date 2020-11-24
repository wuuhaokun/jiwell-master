package com.jiwell.item.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "tb_banner")
/**
 * @author:li
 *
 */

public class Banner implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Banner創建時間
     */
    private String createTime;
    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 品牌圖片地址
     */
    private String image;
    /**
     * 所在頁面
     */

    private String page;
    /**
     * 參數，json格式
     */

    private String param;
    /**
     * 品牌主標題
     */

    private String title;
    /**
     * 連結方式
     */

    private String type;
    /**
     * 外部鏈接
     */

    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", createTime='" + createTime + '\'' +
                "brandId=" + brandId +
                ", image='" + image + '\'' +
                ", page='" + page + '\'' +
                ", param='" + param + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
