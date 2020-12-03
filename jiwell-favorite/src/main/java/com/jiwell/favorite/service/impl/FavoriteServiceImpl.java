package com.jiwell.favorite.service.impl;
import com.jiwell.auth.entity.UserInfo;
import com.jiwell.favorite.client.GoodsClient;
import com.jiwell.favorite.interceptor.LoginInterceptor;
import com.jiwell.favorite.mapper.FavoriteMapper;
import com.jiwell.favorite.service.FavoriteService;
import com.jiwell.favorite.pojo.Favorite;
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
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Autowired
    private GoodsClient goodsClient;
    /**
     * 根据userId查詢我的最愛Spu
     * @param userId
     * @return
     */
    @Override
    public List<SpuBo> queryMyFavoriteSpuByUserId(Long userId) {
        Favorite myFavorites = this.favoriteMapper.selectByPrimaryKey(userId);
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
    public List<Favorite> queryByUserId(Long userId) {
        //這樣寫有錯
        //List<Favorite> list = this.favoriteMapper.queryByUserId(userId);
        Example example = new Example(Favorite.class);
        example.createCriteria().andEqualTo("userId",userId);
        List<Favorite> list = this.favoriteMapper.selectByExample(example);
        if (list.isEmpty()){
            throw new MyException(LyException.CATEGORY_NOT_FOUND);
        }
        return list;
    }

    @Override
    public boolean saveFavorite(Favorite favorite) {
        Favorite myFavorites = this.favoriteMapper.selectByPrimaryKey(favorite.getUserId());
        if(myFavorites != null && !myFavorites.getSpuIds().isEmpty()) {
            List<String> ids = new ArrayList<>(Arrays.asList(myFavorites.getSpuIds().split(",")));
            for(String id:ids){
                if(id.equals(favorite.getSpuId())){
                    return true;
                }
            }
            if(ids.size() == 12){//同一個ID最多支援20我的最愛
                ids.set(0,favorite.getSpuId());
            }
            else{
                ids.add(favorite.getSpuId());
            }
            String idsJoined = String.join(",", ids);
            myFavorites.setSpuIds(idsJoined);
            return this.favoriteMapper.updateByPrimaryKeySelective(myFavorites) ==1;
        }
        else{
            return this.favoriteMapper.insertSelective(myFavorites) == 1;
        }
    }

//    @Override
//    public void updateFavorite(Favorite favorite) {
//        this.favoriteMapper.updateByPrimaryKeySelective(favorite);
//    }

    @Override
    public boolean deleteFavorite(Favorite favorite) {
        //this.favoriteMapper.deleteByPrimaryKey(favorite);
        long userId = favorite.getUserId();
        Favorite myFavorites = this.favoriteMapper.selectByPrimaryKey(userId);
        if(myFavorites == null){
            return false;
        }
        List<String> keepIds = new ArrayList<>();
        if(myFavorites != null && !myFavorites.getSpuIds().isEmpty()) {
            List<String> ids = new ArrayList<>(Arrays.asList(myFavorites.getSpuIds().split(",")));
            for(String id:ids){
                if(!id.equals(favorite.getSpuId())){
                    keepIds.add(id);
                }
            }
            String idsJoined = String.join(",", keepIds);
            myFavorites.setSpuIds(idsJoined);
            return this.favoriteMapper.updateByPrimaryKeySelective(myFavorites) ==1;
        }
        else{
            return true;
        }

    }

    @Override
    public boolean queryFavoriteIsExist(long spuId) {
        UserInfo userInfo = LoginInterceptor.getLoginUser();
        Favorite myFavorites = this.favoriteMapper.selectByPrimaryKey(userInfo.getId());
        if(myFavorites != null && !myFavorites.getSpuIds().isEmpty()) {
            List<String> ids = new ArrayList<>(Arrays.asList(myFavorites.getSpuIds().split(",")));
            for(String id:ids){
                if(id.equals(String.valueOf(spuId))){
                    return true;
                }
            }
        }
        return false;
    }

}
