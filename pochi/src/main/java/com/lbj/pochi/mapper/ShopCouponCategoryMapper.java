package com.lbj.pochi.mapper;

import com.lbj.pochi.pojo.ShopCouponCategory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 优惠券-分类mapper
 */
@Component
public interface ShopCouponCategoryMapper {
    /**
     * 批量添加
     * @param shopCouponCategoryList
     */
    void saveBatch(List<ShopCouponCategory> shopCouponCategoryList);
}
