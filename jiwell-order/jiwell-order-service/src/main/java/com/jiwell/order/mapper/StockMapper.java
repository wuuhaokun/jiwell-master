package com.jiwell.order.mapper;

import com.jiwell.item.pojo.Stock;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Author: 98050
 * @Time: 2018-11-10 17:58
 * @Feature:
 */
public interface StockMapper extends Mapper<Stock> {


    /**
     * 更新對應商品的庫存,且庫存必須大於0，否則回滾。
     * @param skuId
     * @param num
     */
    @Update("update tb_stock set stock = stock - #{num} where sku_id = #{skuId} and stock > 0")
    void reduceStock(@Param("skuId") Long skuId, @Param("num") Integer num);

    //kun add
    @Update("update tb_stock set stock = stock + #{num} where sku_id = #{skuId} and stock > 0")
    void increaseStock(@Param("skuId") Long skuId, @Param("num") Integer num);

    /**
     * 更新對應商品的庫存,且庫存必須大於0，否則回滾。
     * @param skuId
     * @param num
     */
    @Update("update tb_stock set seckill_stock = seckill_stock - #{num} where sku_id = #{skuId} and seckill_stock > 0")
    void reduceSeckStock(@Param("skuId")Long skuId, @Param("num")Integer num);
}
