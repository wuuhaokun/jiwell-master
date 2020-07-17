package com.jiwell.item.service;

import com.jiwell.item.pojo.Favorite;

import java.util.List;

/**
 * @Author: 98050
 * Time: 2018-08-14 15:26
 * Feature:
 */
public interface FavoriteService {
    /**
     * 根据category id查询规格参数模板
     * @param userId
     * @return
     */
    List<Favorite> queryByUserId(Long userId);

    /**
     * 添加规格参数模板
     * @param favorite
     */
    void saveFavorite(Favorite favorite);

    /**
     * 修改规格参数模板
     * @param favorite
     */
    void updateFavorite(Favorite favorite);

    /**
     * 删除规格参数模板
     * @param favorite
     */
    void deleteFavorite(Favorite favorite);
}
