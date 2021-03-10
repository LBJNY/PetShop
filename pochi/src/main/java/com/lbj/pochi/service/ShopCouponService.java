package com.lbj.pochi.service;

import com.lbj.pochi.pojo.ShopCoupon;
import com.lbj.pochi.pojo.dto.ShopCouponDto;
import com.lbj.pochi.utils.Page;

/**
 * 优惠券service
 */
public interface ShopCouponService {

    /**
     * 添加
     * @param shopCouponDto
     */
    void save(ShopCouponDto shopCouponDto);

    /**
     * 删除
     * @param id
     */
    void delete(Long id);

    /**
     * 下架
     * @param id
     */
    void down(Long id);

    /**
     * 分页
     * @param page
     * @return
     */
    Page<ShopCoupon> getByPage(Page<ShopCoupon> page);
}
