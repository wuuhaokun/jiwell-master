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
     * 根据brand id删除中间表相关数据
     * @param orderId
     */
    @Delete("DELETE FROM tb_order_detail WHERE order_id = #{orderId}")
    void deleteByOrderIdInOrderDetail(@Param("orderId") Long orderId);
}
