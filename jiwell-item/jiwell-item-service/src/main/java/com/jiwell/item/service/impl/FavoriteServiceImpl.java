package com.jiwell.item.service.impl;
import com.jiwell.item.mapper.FavoriteMapper;
import com.jiwell.item.pojo.Category;
import com.jiwell.item.service.FavoriteService;
import com.jiwell.item.pojo.Favorite;
import com.jiwell.myexception.LyException;
import com.jiwell.myexception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author: 98050
 * Time: 2018-08-14 15:26
 * Feature:
 */
@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Override
    public List<Favorite> queryByUserId(Long userId) {
        //這樣寫有錯
        //List<Favorite> list = this.favoriteMapper.queryByUserId(userId);
        Example example = new Example(Favorite.class);
        example.createCriteria().andEqualTo("userId",userId);
        List<Favorite> list = this.favoriteMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)){
            throw new MyException(LyException.CATEGORY_NOT_FOUND);
        }
        return list;
    }

    @Override
    public void saveFavorite(Favorite favorite) {
        this.favoriteMapper.insert(favorite);
    }

    @Override
    public void updateFavorite(Favorite favorite) {
        this.favoriteMapper.updateByPrimaryKeySelective(favorite);
    }

    @Override
    public void deleteFavorite(Favorite favorite) {
        this.favoriteMapper.deleteByPrimaryKey(favorite);
    }
}
