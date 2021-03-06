package com.jiwell.item.mapper;

import com.jiwell.item.pojo.Brand;
import com.jiwell.item.pojo.Spu;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Author: 98050
 * Time: 2018-08-14 22:14
 * Feature:
 */
@org.apache.ibatis.annotations.Mapper
public interface SpuMapper extends Mapper<Spu>,SelectByIdListMapper<Spu,Long> {
}
