package com.jiwell.favorite.service.impl;

import com.jiwell.favorite.dao.PortalProductDao;
import com.jiwell.favorite.domain.Cart;
import com.jiwell.favorite.domain.CartPromotionItem;
import com.jiwell.favorite.domain.PromotionProduct;
import com.jiwell.favorite.model.ProductFullReduction;
import com.jiwell.favorite.model.ProductLadder;
import com.jiwell.favorite.service.PromotionService;
import com.jiwell.item.pojo.Stock;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Created by macro on 2018/8/27.
 * 促销管理Service实现类
 */
@Service
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private PortalProductDao  portalProductDao;

    @Override
    public List<CartPromotionItem> calcCartPromotion(List<Cart> cartItemList) {
        //1.先根据productId对CartItem进行分组，以spu为单位进行计算优惠
        Map<Long, List<Cart>> productCartMap = groupCartItemBySpu(cartItemList);
        //2.查询所有商品的优惠相关信息
        List<PromotionProduct> promotionProductList = getPromotionProductList(cartItemList);
        //3.根据商品促销类型计算商品促销优惠价格
        List<CartPromotionItem> cartPromotionItemList = new ArrayList<>();
        for (Map.Entry<Long, List<Cart>> entry : productCartMap.entrySet()) {
            Long productId = entry.getKey();
            PromotionProduct promotionProduct = getPromotionProductById(productId, promotionProductList);
            List<Cart> itemList = entry.getValue();
            Integer promotionType = promotionProduct.getPromotionType();
            if (promotionType == 1) {
                //单品促销
                for (Cart item : itemList) {
                    CartPromotionItem cartPromotionItem = new CartPromotionItem();
                    BeanUtils.copyProperties(item,cartPromotionItem);
                    cartPromotionItem.setPromotionMessage("单品促销");
                    //商品原价-促销价
                    //Stock skuStock = getOriginalPrice(promotionProduct, item.getProductSkuId());
                    Stock skuStock = getOriginalPrice(promotionProduct, item.getSpuId());
                    //kun 移除
                    //BigDecimal originalPrice = skuStock.getPrice();
                    BigDecimal originalPrice = BigDecimal.valueOf(item.getPrice());
                    //cartPromotionItem.setReduceAmount(originalPrice.subtract(skuStock.getPromotionPrice()));
                    cartPromotionItem.setReduceAmount(originalPrice.subtract(BigDecimal.valueOf(item.getPrice())));
                    //cartPromotionItem.setRealStock(skuStock.getStock()-skuStock.getLockStock());
                    cartPromotionItem.setIntegration(promotionProduct.getGiftPoint());
                    cartPromotionItem.setGrowth(promotionProduct.getGiftGrowth());
                    cartPromotionItemList.add(cartPromotionItem);
                }
            } else if (promotionType == 3) {
                //打折优惠
                int count = getCartItemCount(itemList);
                ProductLadder ladder = getProductLadder(count, promotionProduct.getProductLadderList());
                if(ladder!=null){
                    for (Cart item : itemList) {
                        CartPromotionItem cartPromotionItem = new CartPromotionItem();
                        BeanUtils.copyProperties(item,cartPromotionItem);
                        String message = getLadderPromotionMessage(ladder);
                        cartPromotionItem.setPromotionMessage(message);
                        //商品原价-折扣*商品原价
                        Stock skuStock = getOriginalPrice(promotionProduct,item.getSkuId());
                        //kun 移除
                        //BigDecimal originalPrice = skuStock.getPrice();
                        BigDecimal originalPrice = BigDecimal.valueOf(item.getPrice());
                        BigDecimal reduceAmount = originalPrice.subtract(ladder.getDiscount().multiply(originalPrice));
                        cartPromotionItem.setReduceAmount(reduceAmount);
                        //kun先移除
                        //cartPromotionItem.setRealStock(skuStock.getStock()-skuStock.getLockStock());
                        cartPromotionItem.setIntegration(promotionProduct.getGiftPoint());
                        cartPromotionItem.setGrowth(promotionProduct.getGiftGrowth());
                        cartPromotionItemList.add(cartPromotionItem);
                    }
                }else{
                    handleNoReduce(cartPromotionItemList,itemList,promotionProduct);
                }
            } else if (promotionType == 4) {
                //满减
                BigDecimal totalAmount= getCartItemAmount(itemList,promotionProductList);
                ProductFullReduction fullReduction = getProductFullReduction(totalAmount,promotionProduct.getProductFullReductionList());
                if(fullReduction!=null){
                    for (Cart item : itemList) {
                        CartPromotionItem cartPromotionItem = new CartPromotionItem();
                        BeanUtils.copyProperties(item,cartPromotionItem);
                        String message = getFullReductionPromotionMessage(fullReduction);
                        cartPromotionItem.setPromotionMessage(message);
                        //(商品原价/总价)*满减金额
                        Stock skuStock= getOriginalPrice(promotionProduct, item.getSkuId());
                        //kun 移除
                        //BigDecimal originalPrice = skuStock.getPrice();
                        BigDecimal originalPrice = BigDecimal.valueOf(item.getPrice());
                        BigDecimal reduceAmount = originalPrice.divide(totalAmount,RoundingMode.HALF_EVEN).multiply(fullReduction.getReducePrice());
                        cartPromotionItem.setReduceAmount(reduceAmount);
                        //cartPromotionItem.setRealStock(skuStock.getStock()-skuStock.getLockStock());
                        cartPromotionItem.setIntegration(promotionProduct.getGiftPoint());
                        cartPromotionItem.setGrowth(promotionProduct.getGiftGrowth());
                        cartPromotionItemList.add(cartPromotionItem);
                    }
                }else{
                    handleNoReduce(cartPromotionItemList,itemList,promotionProduct);
                }
            } else {
                //无优惠
                handleNoReduce(cartPromotionItemList, itemList,promotionProduct);
            }
        }
        return cartPromotionItemList;
    }

    /**
     * 查询所有商品的优惠相关信息
     */
    private List<PromotionProduct> getPromotionProductList(List<Cart> cartItemList) {
        List<Long> productIdList = new ArrayList<>();
        for(Cart cartItem:cartItemList){
            productIdList.add(cartItem.getSkuId() /*.getProductId() kun edit*/);
        }
        return portalProductDao.getPromotionProductList(productIdList);
    }

    /**
     * 以spu为单位对购物车中商品进行分组
     */
    private Map<Long, List<Cart>> groupCartItemBySpu(List<Cart> cartItemList) {
        Map<Long, List<Cart>> productCartMap = new TreeMap<>();
        for (Cart cartItem : cartItemList) {
            List<Cart> productCartItemList = productCartMap.get(cartItem.getSkuId() /*.getProductId() kun edit*/);
            if (productCartItemList == null) {
                productCartItemList = new ArrayList<>();
                productCartItemList.add(cartItem);
                productCartMap.put(cartItem.getSkuId() /*.getProductId() kun edit*/, productCartItemList);
            } else {
                productCartItemList.add(cartItem);
            }
        }
        return productCartMap;
    }

    /**
     * 获取满减促销消息
     */
    private String getFullReductionPromotionMessage(ProductFullReduction fullReduction) {
        StringBuilder sb = new StringBuilder();
        sb.append("满减优惠：");
        sb.append("满");
        sb.append(fullReduction.getFullPrice());
        sb.append("元，");
        sb.append("减");
        sb.append(fullReduction.getReducePrice());
        sb.append("元");
        return sb.toString();
    }

    /**
     * 对没满足优惠条件的商品进行处理
     */
    private void handleNoReduce(List<CartPromotionItem> cartPromotionItemList, List<Cart> itemList,PromotionProduct promotionProduct) {
        for (Cart item : itemList) {
            CartPromotionItem cartPromotionItem = new CartPromotionItem();
            BeanUtils.copyProperties(item,cartPromotionItem);
            cartPromotionItem.setPromotionMessage("无优惠");
            cartPromotionItem.setReduceAmount(new BigDecimal(0));
            Stock skuStock = getOriginalPrice(promotionProduct, cartPromotionItem.getSkuId() /*.getProductId() kun edit*/);
            if(skuStock!=null){
                //cartPromotionItem.setRealStock(skuStock.getStock()-skuStock.getLockStock());
            }
            cartPromotionItem.setIntegration(promotionProduct.getGiftPoint());
            cartPromotionItem.setGrowth(promotionProduct.getGiftGrowth());
            cartPromotionItemList.add(cartPromotionItem);
        }
    }

    private ProductFullReduction getProductFullReduction(BigDecimal totalAmount,List<ProductFullReduction> fullReductionList) {
        //按条件从高到低排序
        fullReductionList.sort(new Comparator<ProductFullReduction>() {
            @Override
            public int compare(ProductFullReduction o1, ProductFullReduction o2) {
                return o2.getFullPrice().subtract(o1.getFullPrice()).intValue();
            }
        });
        for(ProductFullReduction fullReduction:fullReductionList){
            if(totalAmount.subtract(fullReduction.getFullPrice()).intValue()>=0){
                return fullReduction;
            }
        }
        return null;
    }

    /**
     * 获取打折优惠的促销信息
     */
    private String getLadderPromotionMessage(ProductLadder ladder) {
        StringBuilder sb = new StringBuilder();
        sb.append("打折优惠：");
        sb.append("满");
        sb.append(ladder.getCount());
        sb.append("件，");
        sb.append("打");
        sb.append(ladder.getDiscount().multiply(new BigDecimal(10)));
        sb.append("折");
        return sb.toString();
    }

    /**
     * 根据购买商品数量获取满足条件的打折优惠策略
     */
    private ProductLadder getProductLadder(int count, List<ProductLadder> productLadderList) {
        //按数量从大到小排序
        productLadderList.sort(new Comparator<ProductLadder>() {
            @Override
            public int compare(ProductLadder o1, ProductLadder o2) {
                return o2.getCount() - o1.getCount();
            }
        });
        for (ProductLadder productLadder : productLadderList) {
            if (count >= productLadder.getCount()) {
                return productLadder;
            }
        }
        return null;
    }

    /**
     * 获取购物车中指定商品的数量
     */
    private int getCartItemCount(List<Cart> itemList) {
        int count = 0;
        for (Cart item : itemList) {
            count += item.getNum();
        }
        return count;
    }

    /**
     * 获取购物车中指定商品的总价
     */
    private BigDecimal getCartItemAmount(List<Cart> itemList, List<PromotionProduct> promotionProductList) {
        BigDecimal amount = new BigDecimal(0);
        for (Cart item : itemList) {
            //计算出商品原价
            PromotionProduct promotionProduct = getPromotionProductById(item.getSpuId(), promotionProductList);
            Stock skuStock = getOriginalPrice(promotionProduct,item.getSkuId());
            //amount = amount.add(skuStock.getPrice().multiply(new BigDecimal(item.getNum())));
            amount = amount.add(BigDecimal.valueOf(item.getPrice()).multiply(new BigDecimal(item.getNum())));
        }
        return amount;
    }

    /**
     * 获取商品的原价
     */
    private Stock getOriginalPrice(PromotionProduct promotionProduct, Long productSkuId) {
        //kun 先移除
//        for (Stock skuStock : promotionProduct.getSkuStockList()) {
//            if (productSkuId.equals(skuStock.getId())) {
//                return skuStock;
//            }
//        }
        return null;
    }

    /**
     * 根据商品id获取商品的促销信息
     */
    private PromotionProduct getPromotionProductById(Long productId, List<PromotionProduct> promotionProductList) {
        for (PromotionProduct promotionProduct : promotionProductList) {
            if (productId.equals(promotionProduct.getId())) {
                return promotionProduct;
            }
        }
        return null;
    }
}
