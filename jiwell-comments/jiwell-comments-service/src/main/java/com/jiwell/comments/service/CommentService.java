package com.jiwell.comments.service;

import com.jiwell.comments.bo.CommentRequestParam;
import com.jiwell.comments.pojo.Review;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @Author: 98050
 * @Time: 2018-11-26 15:40
 * @Feature:
 */
public interface CommentService {

    /**
     * 根據評論id查詢
     *
     * @param id
     * @return
     */
    Review findOne(String id);

    /**
     * 新增評論
     * @param review
     * @param orderId
     * @return
     */
    boolean add(Long orderId, Review review);

    /**
     * 修改評論
     *
     * @param review
     */
    void update(Review review);

    /**
     * 刪除指定評論
     *
     * @param id
     */
    void deleteById(String id);

    /**
     * 查詢某一商品下的所有頂級評論
     * @param commentRequestParam
     * @return
     */
    Page<Review> findReviewBySpuId(CommentRequestParam commentRequestParam);

    /**
     * 評論點贊
     * @param id
     */
    boolean updateThumbup(String id);

    /**
     * 瀏覽量＋1
     * @param id
     */
    boolean updateVisits(String id);
}
