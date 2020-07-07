package com.jiwell.item.mapper;

import com.jiwell.item.pojo.Favorite;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author li
 */

@org.apache.ibatis.annotations.Mapper
public interface FavoriteMapper extends Mapper<Favorite> {
    /**
     * 根据id查名字
     * @param userId
     * @return
     */
    @Select("SELECT * FROM tb_shop_favorite WHERE user_id = #{userId}")
    List<Favorite> queryByUserId(@Param("userId") Long userId);
}
