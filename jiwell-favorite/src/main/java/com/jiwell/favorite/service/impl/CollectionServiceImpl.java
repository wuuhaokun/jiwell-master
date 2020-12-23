package com.jiwell.favorite.service.impl;
import com.jiwell.auth.entity.UserInfo;
import com.jiwell.favorite.client.GoodsClient;
import com.jiwell.favorite.interceptor.LoginInterceptor;
import com.jiwell.favorite.mapper.CollectionMapper;
import com.jiwell.favorite.service.CollectionService;
import com.jiwell.favorite.pojo.Collection;
import com.jiwell.item.bo.SpuBo;
import com.jiwell.myexception.LyException;
import com.jiwell.myexception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 98050
 * Time: 2018-08-14 15:26
 * Feature:
 */
@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    private CollectionMapper collectionMapper;

    @Autowired
    private GoodsClient goodsClient;
    /**
     * 根据userId查詢我的最愛Spu
     * @param userId
     * @return
     */
    @Override
    public List<SpuBo> queryMyCollectionSpuByUserId(Long userId) {
        Collection myFavorites = this.collectionMapper.selectByPrimaryKey(userId);
        if(myFavorites != null && !myFavorites.getSpuIds().isEmpty()) {
            List<String> ids = new ArrayList<>(Arrays.asList(myFavorites.getSpuIds().split(",")));
            List<Long> numberIdList = new ArrayList<>();
            for(String number : ids) {
                numberIdList.add(Long.parseLong(number));
            }
            List<SpuBo> spuBoList= this.goodsClient.queryGoodsByIds(numberIdList);
            return spuBoList;
        }
        return null;
    }

    @Override
    public List<Collection> queryByUserId(Long userId) {
        //這樣寫有錯
        //List<Favorite> list = this.favoriteMapper.queryByUserId(userId);
        Example example = new Example(Collection.class);
        example.createCriteria().andEqualTo("userId",userId);
        List<Collection> list = this.collectionMapper.selectByExample(example);
        if (list.isEmpty()){
            throw new MyException(LyException.CATEGORY_NOT_FOUND);
        }
        return list;
    }

    @Override
    public boolean saveCollection(Collection collection) {
        Collection myCollection = this.collectionMapper.selectByPrimaryKey(collection.getUserId());
        if(myCollection != null && !myCollection.getSpuIds().isEmpty()) {
            List<String> ids = new ArrayList<>(Arrays.asList(myCollection.getSpuIds().split(",")));
            for(String id:ids){
                if(id.equals(collection.getSpuId())){
                    return true;
                }
            }
            if(ids.size() == 12){//同一個ID最多支援20我的最愛
                ids.set(0,collection.getSpuId());
            }
            else{
                ids.add(collection.getSpuId());
            }
            String idsJoined = String.join(",", ids);
            myCollection.setSpuIds(idsJoined);
            return this.collectionMapper.updateByPrimaryKeySelective(myCollection) ==1;
        }
        else{
            return this.collectionMapper.insertSelective(myCollection) == 1;
        }
    }

//    @Override
//    public void updateFavorite(Favorite favorite) {
//        this.favoriteMapper.updateByPrimaryKeySelective(favorite);
//    }

    @Override
    public boolean deleteCollection(Collection favorite) {
        //this.favoriteMapper.deleteByPrimaryKey(favorite);
        long userId = favorite.getUserId();
        Collection myCollection = this.collectionMapper.selectByPrimaryKey(userId);
        if(myCollection == null){
            return false;
        }
        List<String> keepIds = new ArrayList<>();
        if(myCollection != null && !myCollection.getSpuIds().isEmpty()) {
            List<String> ids = new ArrayList<>(Arrays.asList(myCollection.getSpuIds().split(",")));
            for(String id:ids){
                if(!id.equals(favorite.getSpuId())){
                    keepIds.add(id);
                }
            }
            String idsJoined = String.join(",", keepIds);
            myCollection.setSpuIds(idsJoined);
            return this.collectionMapper.updateByPrimaryKeySelective(myCollection) ==1;
        }
        else{
            return true;
        }

    }

    @Override
    public boolean queryCollectionIsExist(long spuId) {
        UserInfo userInfo = LoginInterceptor.getLoginUser();
        Collection myCollection = this.collectionMapper.selectByPrimaryKey(userInfo.getId());
        if(myCollection != null && !myCollection.getSpuIds().isEmpty()) {
            List<String> ids = new ArrayList<>(Arrays.asList(myCollection.getSpuIds().split(",")));
            for(String id:ids){
                if(id.equals(String.valueOf(spuId))){
                    return true;
                }
            }
        }
        return false;
    }
}
