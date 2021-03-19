package com.jiwell.order.mapper;

import com.jiwell.order.pojo.OrderDetail;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

import java.util.List;

/**
 * @Author: 98050
 * @Time: 2018-10-27 16:33
 * @Feature:
 */
public interface OrderDetailMapper extends Mapper<OrderDetail>, InsertListMapper<OrderDetail> {

    /**
     * 根據brand id刪除中間表相關數據
     * @param orderId
     */
    @Delete("DELETE FROM tb_order_detail WHERE order_id = #{orderId}")
    void deleteByOrderIdInOrderDetail(@Param("orderId") Long orderId);
}
