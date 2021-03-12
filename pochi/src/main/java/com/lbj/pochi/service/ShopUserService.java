package com.lbj.pochi.service;

import com.lbj.pochi.pojo.ShopUser;
import com.lbj.pochi.pojo.dto.ShopUserBindDto;

/**
 * 商铺用户Service
 */
public interface ShopUserService {
    /**
     * 根据openID查询用户是否存在
     * @param username
     * @return
     */
    ShopUser getByOpenId(String username);

    /**
     * 注册
     * @param toShopUser
     */
    void register(ShopUser toShopUser);

    /**
     * 绑定手机号
     * @param shopUserBindDto
     * @return
     */
    ShopUser bindUser(ShopUserBindDto shopUserBindDto);
}
