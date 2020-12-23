package com.jiwell.favorite.mapper;

//import com.macro.mall.model.SmsCouponProductRelation;
//import com.macro.mall.model.SmsCouponProductRelationExample;
import java.util.List;

import com.jiwell.favorite.mapper.Example.CouponProductRelationExample;
import com.jiwell.favorite.pojo.CouponProductRelation;
import org.apache.ibatis.annotations.Param;

public interface CouponProductRelationMapper {
    long countByExample(CouponProductRelationExample example);

    int deleteByExample(CouponProductRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CouponProductRelation record);

    int insertSelective(CouponProductRelation record);

    List<CouponProductRelation> selectByExample(CouponProductRelationExample example);

    CouponProductRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CouponProductRelation record, @Param("example") CouponProductRelationExample example);

    int updateByExample(@Param("record") CouponProductRelation record, @Param("example") CouponProductRelationExample example);

    int updateByPrimaryKeySelective(CouponProductRelation record);

    int updateByPrimaryKey(CouponProductRelation record);
}
