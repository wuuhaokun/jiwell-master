package com.jiwell.item.api;

import com.jiwell.item.pojo.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: 98050
 * Time: 2018-10-11 20:05
 * Feature:商品分類服務接口
 */
@RequestMapping("category")
public interface CategoryApi {

    /**
     * 根據id，查詢分類名稱
     * @param ids
     * @return
     */
    @GetMapping("names")
    ResponseEntity<List<String>> queryNameByIds(@RequestParam("ids")List<Long> ids);

    /**
     * 根據分類id集合查詢分類名稱
     * @param ids
     * @return
     */
    @GetMapping("all")
    ResponseEntity<List<Category>> queryCategoryByIds(@RequestParam("ids")List<Long> ids);

    /**
     * 購買方法分類
     * @param
     * @return
     */
    @GetMapping("buyType")
    ResponseEntity<List<String>> queryBuyType();
}
