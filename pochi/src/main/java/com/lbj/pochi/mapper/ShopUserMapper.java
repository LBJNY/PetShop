package com.lbj.pochi.mapper;

import com.lbj.pochi.pojo.ShopUser;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 会员表Mapper
 * </p>
 * extends MybatisMapper<ShopUser, Long>
 */
@Component
public interface ShopUserMapper {

    /**
     * 根据openID查询
     *
     * @param username
     * @return
     */
    ShopUser getByOpenId(String username);

    /**
     * 添加用户---用户注册
     *
     * @param shopUser
     */
    void save(ShopUser shopUser);

    /**
     * 根据手机号获取用户
     *
     * @param phone
     * @return
     */
    ShopUser getByPhone(String phone);

    /**
     * 更新用户信息
     *
     * @param shopUser
     */
    void updateLoginInfo(ShopUser shopUser);

    /**
     * 删除用户---物理层面
     *
     * @param id
     */
    void clearById(Long id);
}