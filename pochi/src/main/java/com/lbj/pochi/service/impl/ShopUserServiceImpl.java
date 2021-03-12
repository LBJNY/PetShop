package com.lbj.pochi.service.impl;

import com.lbj.pochi.enums.ResultEnum;
import com.lbj.pochi.enums.StateEnums;
import com.lbj.pochi.exception.PochiException;
import com.lbj.pochi.mapper.ShopUserMapper;
import com.lbj.pochi.mapper.ShopUserStatisticMapper;
import com.lbj.pochi.pojo.LoginUser;
import com.lbj.pochi.pojo.ShopUser;
import com.lbj.pochi.pojo.ShopUserStatistic;
import com.lbj.pochi.pojo.dto.ShopUserBindDto;
import com.lbj.pochi.service.ShopUserService;
import com.lbj.pochi.utils.IdWorker;
import com.lbj.pochi.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商城用户ServiceImpl
 */
@Service
public class ShopUserServiceImpl implements ShopUserService {
    @Autowired
    private ShopUserMapper shopUserMapper;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private ShopUserStatisticMapper shopUserStatisticMapper;

    /**
     * 根据用户ID查询用户是否存在
     *
     * @param username
     * @return
     */
    @Override
    public ShopUser getByOpenId(String username) {
        return shopUserMapper.getByOpenId(username);
    }

    /**
     * 注册
     *
     * @param shopUser
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(ShopUser shopUser) {
        //保存用户信息
        long userId = idWorker.nextId();
        shopUser.setId(userId);
        shopUserMapper.save(shopUser);

        //创建用户统计对象
        ShopUserStatistic shopUserStatistic = new ShopUserStatistic();
        long statisticId = idWorker.nextId();
        shopUserStatistic.setId(statisticId);
        shopUserStatistic.setUserId(userId);
        shopUserStatisticMapper.save(shopUserStatistic);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ShopUser bindUser(ShopUserBindDto shopUserBindDto) {
        Integer bindType = shopUserBindDto.getBindType();
        //绑定新用户
        if (StateEnums.NEW_USER.getCode().equals(bindType)) {
            ShopUser shopUser = shopUserMapper.getByPhone(shopUserBindDto.getPhone());
            if (shopUser != null) {
                throw new PochiException(ResultEnum.USER_REAL_EXISTS);
            }
            LoginUser loginUser = ShiroUtils.getLoginUser();
            String openId = loginUser.getOpenId();
            shopUser = shopUserMapper.getByOpenId(openId);
            shopUser.setPhone(shopUserBindDto.getPhone());
            shopUser.setPassword(shopUserBindDto.getPassword());
            shopUserMapper.updateLoginInfo(shopUser);
            return shopUser;
        } else {
            LoginUser loginUser = ShiroUtils.getLoginUser();
            // 绑定现有账户
            // 查询现有账户，更新openid
            ShopUser shopUser = shopUserMapper.getByPhone(shopUserBindDto.getPhone());
            if (shopUser == null) {
                throw new PochiException(ResultEnum.USER_NOT_FOUND);
            }
            if (!shopUser.getPassword().equals(shopUserBindDto.getPassword())) {
                throw new PochiException(ResultEnum.USER_NOT_FOUND);
            }
            shopUser.setOpenId(loginUser.getOpenId());
            shopUserMapper.updateLoginInfo(shopUser);
            // 删除当前登录用户ID对应的账号，这个账号是没有手机号的
            shopUserMapper.clearById(loginUser.getId());
            return shopUser;
        }
    }
}
