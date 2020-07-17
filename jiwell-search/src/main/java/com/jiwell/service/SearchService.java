package com.jiwell.service;

import com.jiwell.bo.SearchRequest;
import com.jiwell.item.pojo.Spu;
import com.jiwell.pojo.Goods;
import com.jiwell.vo.SearchResult;

import java.io.IOException;

/**
 * @Author: 98050
 * Time: 2018-10-17 19:33
 * Feature: 搜索功能
 */
public interface SearchService {

    /**
     * 查询商品信息
     * @param spu
     * @return
     * @throws IOException
     */
    Goods buildGoods(Spu spu) throws IOException;

    /**
     * 商品搜索
     * @param searchRequest
     * @return
     */
    SearchResult<Goods> search(SearchRequest searchRequest);


    /**
     * 根据goods的id创建相应的索引
     * @param id
     * @throws IOException
     */
    void createIndex(Long id) throws IOException;

    /**
     * 根据goods的id删除相应的索引
     * @param id
     */
    void deleteIndex(Long id);
}
