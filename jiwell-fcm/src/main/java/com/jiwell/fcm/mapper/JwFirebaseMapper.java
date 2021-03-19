package com.jiwell.fcm.mapper;

import com.jiwell.fcm.pojo.JwFirebase;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;


import java.util.List;

/**
 * @Author: 98050
 * @Time: 2018-10-21 18:40
 * @Feature:
 */
public interface JwFirebaseMapper extends Mapper<JwFirebase>{
//    @Select("SELECT *.token FROM tb_shop_favorite WHERE user_id = #{userId}")
//    String queryByUserId(Long userId);

    /**
     * 根據id查名字
     * @param userId
     * @return
     */
    @Select("SELECT token FROM tb_jwfirebase WHERE userId = #{userId}")
    String queryTokenByUserId(Long userId);

}

