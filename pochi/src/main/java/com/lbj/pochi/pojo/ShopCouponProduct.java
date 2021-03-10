package com.lbj.pochi.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShopCouponProduct implements Serializable {

    private Long id;

    /**
     * 优惠券ID
     */
    private Long couponId;

    /**
     * 商品ID
     */
    private Long productId;

    public ShopCouponProduct(Long couponId, Long productId) {
        this.couponId = couponId;
        this.productId = productId;
    }
}
