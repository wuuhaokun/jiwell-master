package com.jiwell.favorite.service;

import com.jiwell.favorite.pojo.Favorite;
import com.jiwell.item.bo.SpuBo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Author: 98050
 * Time: 2018-08-14 15:26
 * Feature:
 */
public interface FavoriteService {

    /**
     * 根据userId查詢我的最愛Spu
     * @param userId
     * @return
     */
    List<SpuBo> queryMyFavoriteSpuByUserId(Long userId);

//    /**
//     * 根据userId查詢我的最愛Spu
//     * @param userId
//     * @return
//     */
//    List<String> querySpuIdsByUserId(Long userId);

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
    boolean saveFavorite(Favorite favorite);

//    /**
//     * 修改规格参数模板
//     * @param favorite
//     */
//    void updateFavorite(Favorite favorite);

    /**
     * 删除规格参数模板
     * @param favorite
     */
    boolean deleteFavorite(Favorite favorite);

    /**
     * 查詢SpuId是否己是我的最愛
     * @param favorite
     */
    boolean queryFavoriteIsExist(long spuId);
}
