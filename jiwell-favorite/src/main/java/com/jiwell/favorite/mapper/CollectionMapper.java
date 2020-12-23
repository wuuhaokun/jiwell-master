package com.jiwell.favorite.mapper;

import com.jiwell.favorite.pojo.Collection;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author li
 */

@org.apache.ibatis.annotations.Mapper
public interface CollectionMapper extends Mapper<Collection> {
    /**
     * 根据id查名字
     * @param userId
     * @return
     */
    @Select("SELECT * FROM tb_shop_collection WHERE user_id = #{userId}")
    List<Collection> queryByUserId(@Param("userId") Long userId);
}
