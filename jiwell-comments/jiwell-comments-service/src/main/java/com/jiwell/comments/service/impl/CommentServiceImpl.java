package com.jiwell.comments.service.impl;
import com.jiwell.comments.bo.CommentRequestParam;
import com.jiwell.comments.client.OrderClient;
import com.jiwell.comments.dao.CommentDao;
import com.jiwell.comments.pojo.Review;
import com.jiwell.comments.service.CommentService;
import com.jiwell.utils.IdWorker;
import com.mongodb.client.result.UpdateResult;
import org.mockito.internal.matchers.Find;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Author: 98050
 * @Time: 2018-11-26 15:41
 * @Feature:
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private OrderClient orderClient;


    @Override
    public Review findOne(String id) {
        //判断空
        Optional<Review> optional = commentDao.findById(id);
        return optional.orElse(null);
    }

    /**
     * 新增
     * 注意：一個用戶只能發表一個頂級評論，可以追評（在一個訂單中）
     * @param review
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(Long orderId, Review review) {
        //1.檢查用戶是否在該商品下發表過頂級評論過
        if (review.getIsparent()) {
            Query query2 = new Query();
            query2.addCriteria(Criteria.where("userid").is(review.getUserid()));
            query2.addCriteria(Criteria.where("orderid").is(review.getOrderid()));
            query2.addCriteria(Criteria.where("spuid").is(review.getSpuid()));
            List<Review> old = this.mongoTemplate.find(query2, Review.class);
            if (old.size() > 0 && old.get(0).getIsparent()) {
                return false;
            }
        }
        //2.修改訂單狀態,6代表評價狀態
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request1 = servletRequestAttributes.getRequest();
        HttpServletResponse response1 = servletRequestAttributes.getResponse();

        ResponseEntity<Boolean> responseEntity = this.orderClient.updateOrderStatus(orderId, 6);
        if (responseEntity.getStatusCode() != HttpStatus.OK){
            //更新失敗，返回false
            return false;
        }
        //原始的寫法在某些版本會有問題。
        boolean result = this.orderClient.updateOrderStatus(orderId, 6).getBody();
        if (!result){
            return false;
        }
        //3.添加評論
        /**
         * 設置主鍵
         */
        review.set_id(idWorker.nextId() + "");
        review.setPublishtime(new Date());
        review.setComment(0);
        review.setThumbup(0);
        review.setVisits(0);
        if (review.getParentid() != null && !"".equals(review.getParentid())){
            //如果存在上級id，則上級評論數加1，將上級評論的isParent設置為true，瀏覽量加一
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(review.getParentid()));
            Update update = new Update();
            update.inc("comment",1);
            update.set("isparent",true);
            update.inc("visits",1);
            this.mongoTemplate.updateFirst(query,update,"review");
        }
        commentDao.save(review);
        return true;
    }

    /**
     * 修改
     *
     * @param review
     */
    @Override
    public void update(Review review) {
        commentDao.save(review);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void deleteById(String id) {
        commentDao.deleteById(id);
    }

    /**
     * 查詢某一商品下的所有頂級評論
     * @param commentRequestParam
     * @return
     */
    @Override
    public Page<Review> findReviewBySpuId(CommentRequestParam commentRequestParam) {
        PageRequest pageRequest = PageRequest.of(commentRequestParam.getPage()-1, commentRequestParam.getDefaultSize());
        return this.commentDao.findReviewBySpuid(commentRequestParam.getSpuId()+"",pageRequest);
    }

    /**
     * 評論點贊(需要改進)
     * @param id
     */
    @Override
    public boolean updateThumbup(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc("thumbup",1);
        UpdateResult result = this.mongoTemplate.updateFirst(query,update,"review");
        return result.isModifiedCountAvailable();
    }

    /**
     * 訪問量加一
     * @param id
     */
    @Override
    public boolean updateVisits(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc("visits",1);
        UpdateResult result = this.mongoTemplate.updateFirst(query,update,"review");
        return result.isModifiedCountAvailable();
    }

    /**
     * 取得點贊數
     * @param id
     */
    //@Override
//    public boolean getThumbup(String id) {
////        Query query = new Query();
////        query.addCriteria(Criteria.where("_id").is(id));
////        //Find find = new Find();
////        UpdateResult result = this.mongoTemplate.findById(query);//updateFirst(query,find,"thumbup");
////        return result.isModifiedCountAvailable();
//
//        Query query = new Query(Criteria.where("spuid").is(id));
//        long cnt = this.mongoTemplate.count(query, "thumbup");
//        System.out.println("query: " + query + " | cnt " + cnt);
//        return true;
//    }
}

