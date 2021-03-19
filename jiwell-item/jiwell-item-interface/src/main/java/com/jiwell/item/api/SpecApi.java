package com.jiwell.item.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: 98050
 * Time: 2018-10-11 20:05
 * Feature:
 */
@RequestMapping("spec")
public interface SpecApi {
    /**
     * 查詢商品分類對應的規格參數模板
     * @param id
     * @return
     */
    @GetMapping("{id}")
    ResponseEntity<String> querySpecificationByCategoryId(@PathVariable("id") Long id);
}
