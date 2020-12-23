package com.jiwell.favorite.mapper;

import com.jiwell.favorite.mapper.Example.CouponExample;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
import com.jiwell.favorite.pojo.Coupon;
import org.apache.ibatis.annotations.Param;
import java.util.List;

//@org.apache.ibatis.annotations.Mapper
//@Component
public interface CouponMapper /*extends Mapper<Coupon>*/ {
    long countByExample(CouponExample example);

    int deleteByExample(CouponExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Coupon record);

    int insertSelective(Coupon record);

    List<Coupon> selectByExample(CouponExample example);

    Coupon selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Coupon record, @Param("example") CouponExample example);

    int updateByExample(@Param("record") Coupon record, @Param("example") CouponExample example);

    int updateByPrimaryKeySelective(Coupon record);

    int updateByPrimaryKey(Coupon record);
}
