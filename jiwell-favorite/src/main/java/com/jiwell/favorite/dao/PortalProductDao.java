package com.jiwell.favorite.dao;

//import com.macro.mall.portal.domain.CartProduct;
//import com.macro.mall.portal.domain.PromotionProduct;
import com.jiwell.favorite.domain.PromotionProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 前台系統自定義商品Dao
 * Created by macro on 2018/8/2.
 */
public interface PortalProductDao {
    //CartProduct getCartProduct(@Param("id") Long id);
    List<PromotionProduct> getPromotionProductList(@Param("ids") List<Long> ids);
}
