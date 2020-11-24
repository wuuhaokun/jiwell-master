package com.jiwell.item.mapper;

import com.jiwell.item.pojo.Banner;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Author: 98050
 * @Time: 2018-08-07 19:15
 * @Feature:
 */
@org.apache.ibatis.annotations.Mapper
public interface BannerMapper extends Mapper<Banner>, SelectByIdListMapper<Banner,Long> {
}
