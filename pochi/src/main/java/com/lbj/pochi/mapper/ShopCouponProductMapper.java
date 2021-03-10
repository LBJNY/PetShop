package com.lbj.pochi.mapper;

import com.lbj.pochi.pojo.ShopCouponProduct;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 优惠券-商品mapper
 */
@Component
public interface ShopCouponProductMapper {
    /**
     * 批量添加
     * @param shopCouponProductList
     */
    void saveBatch(List<ShopCouponProduct> shopCouponProductList);
}
