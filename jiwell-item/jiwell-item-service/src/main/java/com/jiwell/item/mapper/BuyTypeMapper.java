package com.jiwell.item.mapper;

import com.jiwell.item.pojo.BuyType;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Author: 98050
 * @Time: 2018-08-07 19:15
 * @Feature:
 */
@org.apache.ibatis.annotations.Mapper
public interface BuyTypeMapper extends Mapper<BuyType>, SelectByIdListMapper<BuyType,Long> {
}
