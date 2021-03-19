package com.jiwell.comments.bo;

/**
 * @Author: 98050
 * @Time: 2018-11-26 21:40
 * @Feature:
 */
public class CommentRequestParam {
    /**
     * 商品id
     */
    private Long spuId;

    /**
     * 當前頁碼
     */
    private Integer page;

    /**
     * 每頁大小，不從頁面接收，而是固定大小
     */
    private static final Integer DEFAULT_SIZE = 20;

    /**
     * 默認頁
     */
    private static final Integer DEFAULT_PAGE = 1;

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public Integer getPage() {
        if (page == null){
            return DEFAULT_PAGE;
        }
        /**
         * 獲取頁碼時做一些校驗，不能小於1
         */
        return Math.max(DEFAULT_PAGE,page);
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getDefaultSize() {
        return DEFAULT_SIZE;
    }

}
