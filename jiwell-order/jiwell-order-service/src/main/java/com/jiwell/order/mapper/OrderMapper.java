package com.jiwell.order.mapper;

import com.jiwell.order.pojo.Order;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


/**
 * @Author: 98050
 * @Time: 2018-10-27 16:31
 * @Feature:
 */
public interface OrderMapper extends Mapper<Order> {
    /**
     * 分頁查詢訂單
     * @param userId
     * @param status
     * @return
     */
    List<Order> queryOrderList(@Param("userId") Long userId, @Param("status") Integer status);
}
