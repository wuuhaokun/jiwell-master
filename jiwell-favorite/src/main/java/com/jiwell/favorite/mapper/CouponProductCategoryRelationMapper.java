package com.jiwell.favorite.mapper;

import com.jiwell.favorite.mapper.Example.CouponProductCategoryRelationExample;
import com.jiwell.favorite.pojo.CouponProductCategoryRelation;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CouponProductCategoryRelationMapper {
    long countByExample(CouponProductCategoryRelationExample example);

    int deleteByExample(CouponProductCategoryRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CouponProductCategoryRelation record);

    int insertSelective(CouponProductCategoryRelation record);

    List<CouponProductCategoryRelation> selectByExample(CouponProductCategoryRelationExample example);

    CouponProductCategoryRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CouponProductCategoryRelation record, @Param("example") CouponProductCategoryRelationExample example);

    int updateByExample(@Param("record") CouponProductCategoryRelation record, @Param("example") CouponProductCategoryRelationExample example);

    int updateByPrimaryKeySelective(CouponProductCategoryRelation record);

    int updateByPrimaryKey(CouponProductCategoryRelation record);
}
