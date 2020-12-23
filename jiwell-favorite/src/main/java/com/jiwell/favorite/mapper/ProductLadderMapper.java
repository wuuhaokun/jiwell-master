package com.jiwell.favorite.mapper;

//import com.macro.mall.model.PmsProductLadder;
//import com.macro.mall.model.PmsProductLadderExample;
import java.util.List;

import com.jiwell.favorite.mapper.Example.ProductLadderExample;
import com.jiwell.favorite.model.ProductLadder;
import org.apache.ibatis.annotations.Param;

public interface ProductLadderMapper {
    long countByExample(ProductLadderExample example);

    int deleteByExample(ProductLadderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProductLadder record);

    int insertSelective(ProductLadder record);

    List<ProductLadder> selectByExample(ProductLadderExample example);

    ProductLadder selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProductLadder record, @Param("example") ProductLadderExample example);

    int updateByExample(@Param("record") ProductLadder record, @Param("example") ProductLadderExample example);

    int updateByPrimaryKeySelective(ProductLadder record);

    int updateByPrimaryKey(ProductLadder record);
}
