package com.jiwell.favorite.mapper;

//import com.macro.mall.model.SmsCouponHistory;
//import com.macro.mall.model.SmsCouponHistoryExample;
import java.util.List;

import com.jiwell.favorite.mapper.Example.CouponHistoryExample;
import com.jiwell.favorite.pojo.Coupon;
import com.jiwell.favorite.pojo.CouponHistory;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface CouponHistoryMapper extends Mapper<CouponHistory>{
    long countByExample(CouponHistoryExample example);

//    int deleteByExample(CouponHistoryExample example);
//
//    int deleteByPrimaryKey(Long id);
//
//    int insert(CouponHistory record);
//
//    int insertSelective(CouponHistory record);
//
    List<CouponHistory> selectByExample(CouponHistoryExample example);
//
//    CouponHistory selectByPrimaryKey(Long id);
//
//    int updateByExampleSelective(@Param("record") CouponHistory record, @Param("example") CouponHistoryExample example);
//
//    int updateByExample(@Param("record") CouponHistory record, @Param("example") CouponHistoryExample example);
//
//    int updateByPrimaryKeySelective(CouponHistory record);
//
//    int updateByPrimaryKey(CouponHistory record);
}
