package com.jiwell.favorite.service;

import com.jiwell.favorite.pojo.Collection;
import com.jiwell.item.bo.SpuBo;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @Author: 98050
 * Time: 2018-08-14 15:26
 * Feature:
 */
public interface CollectionService {

    /**
     * 根据userId查詢我的最愛Spu
     * @param userId
     * @return
     */
    List<SpuBo> queryMyCollectionSpuByUserId(Long userId);

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
    List<Collection> queryByUserId(Long userId);

    /**
     * 添加规格参数模板
     * @param favorite
     */
    boolean saveCollection(Collection favorite);

//    /**
//     * 修改规格参数模板
//     * @param favorite
//     */
//    void updateFavorite(Favorite favorite);

    /**
     * 删除规格参数模板
     * @param favorite
     */
    boolean deleteCollection(Collection favorite);

    /**
     * 查詢SpuId是否己是我的最愛
     * @param spuId
     */
    boolean queryCollectionIsExist(long spuId);
}
