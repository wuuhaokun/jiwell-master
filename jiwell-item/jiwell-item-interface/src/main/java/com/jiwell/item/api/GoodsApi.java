package com.jiwell.item.api;

import com.jiwell.common.pojo.PageResult;
import com.jiwell.item.bo.SpuBo;
import com.jiwell.item.pojo.SeckillGoods;
import com.jiwell.item.pojo.Sku;
import com.jiwell.item.pojo.SpuDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: 98050
 * Time: 2018-10-11 20:05
 * Feature:商品服務接口
 */
@RequestMapping("goods")
public interface GoodsApi {

    /**
     * 分页查询
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param key
     * @param saleable
     * @return
     */
    @GetMapping("/spu/page")
    PageResult<SpuBo> querySpuByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable",defaultValue = "true") Boolean saleable,
            @RequestParam(value = "cid3",defaultValue = "-1") long cid3);
    /**
     * 根據spu商品id查詢詳情
     * @param id
     * @return
     */
    //@GetMapping("/spu/detail/{id}")
    @RequestMapping(method = RequestMethod.GET, value = "/spu/detail/{id}")
    SpuDetail querySpuDetailBySpuId(@PathVariable("id") Long id);

    /**
     * 根據Spu的id​​查詢其下所有的sku
     * @param id
     * @return
     */
    @GetMapping("sku/list/{id}")
    List<Sku> querySkuBySpuId(@PathVariable("id") Long id);

    /**
     * 根據id查詢商品
     * @param id
     * @return
     */
    @GetMapping("/spu/{id}")
    SpuBo queryGoodsById(@PathVariable("id") Long id);

    /**
     * 根據id查詢商品
     * @param ids
     * @return
     */
    @GetMapping("spu/list")
    //SpuBo queryGoodsById(@PathVariable("id") Long id);
    List<SpuBo> queryGoodsByIds(@RequestParam("ids") List<Long> ids);
    /**
     * 根據sku的id查詢sku
     * @param id
     * @return
     */
    @GetMapping("/sku/{id}")
    Sku querySkuById(@PathVariable("id") Long id);


    /**
     * 查詢秒殺商品
     * @return
     */
    @GetMapping("/seckill/list")
    ResponseEntity<List<SeckillGoods>> querySeckillGoods();}
