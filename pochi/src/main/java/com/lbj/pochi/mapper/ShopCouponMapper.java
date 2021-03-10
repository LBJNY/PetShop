package com.lbj.pochi.mapper;

import com.lbj.pochi.pojo.ShopCoupon;
import com.lbj.pochi.utils.Page;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 优惠券Mapper
 */
@Component
public interface ShopCouponMapper {
    /**
     * 添加
     * @param shopCoupon
     */
    void save(ShopCoupon shopCoupon);

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
     * 分页获取
     * @param page
     * @return
     */
    List<ShopCoupon> getByPage(Page<ShopCoupon> page);

    /**
     * 条件-总条数
     * @param page
     * @return
     */
    Integer countByPage(Page<ShopCoupon> page);
}
