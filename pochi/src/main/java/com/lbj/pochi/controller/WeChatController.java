package com.lbj.pochi.controller;

import com.alibaba.fastjson.JSON;
import com.lbj.pochi.config.WeChatConfig;
import com.lbj.pochi.enums.ResultEnum;
import com.lbj.pochi.pojo.LoginUser;
import com.lbj.pochi.pojo.ShopUser;
import com.lbj.pochi.pojo.TokenVo;
import com.lbj.pochi.pojo.WeChatResult;
import com.lbj.pochi.pojo.dto.ShopUserBindDto;
import com.lbj.pochi.pojo.dto.WeChatRegisterDto;
import com.lbj.pochi.service.ShopUserService;
import com.lbj.pochi.shiro.ShopUserRealm;
import com.lbj.pochi.shiro.UserRealm;
import com.lbj.pochi.shiro.UserToken;
import com.lbj.pochi.utils.HttpUtils;
import com.lbj.pochi.utils.Result;
import com.lbj.pochi.utils.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信
 */
@RestController
@RequestMapping("/wx")
public class WeChatController {

    @Autowired
    private WeChatConfig weChatConfig;
    @Autowired
    private ShopUserService shopUserService;

    @RequestMapping(value = "/wxLogin/{code}", method = RequestMethod.GET)
    public Result<Object> weLogin(@PathVariable String code) throws IOException {
        String body = HttpUtils.get(weChatConfig.getAuthUrl(code)).body();
        WeChatResult weChatResult = JSON.parseObject(body, WeChatResult.class);
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken authenticationToken = new UserToken(weChatResult.getOpenId(), weChatResult.getOpenId(), UserRealm.class);
        try {
            subject.login(authenticationToken);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            // 说明openid对应用户不存在
            return new Result<>(weChatResult);
        }
        Serializable sessionId = subject.getSession().getId();
        Map<String, Object> returnMap = new HashMap<>(2);
        returnMap.put("token", sessionId);
        return new Result<>(returnMap);
    }

    /**
     * 注册登录
     *
     * @param weChatRegisterDto
     * @return
     */
    @RequestMapping(value = "/registerLogin", method = RequestMethod.POST)
    public Result<?> registerLogin(@RequestBody WeChatRegisterDto weChatRegisterDto) {
        ShopUser shopUser = shopUserService.getByOpenId(weChatRegisterDto.getOpenId());
        if (shopUser == null) {
            // 注册
            shopUserService.register(weChatRegisterDto.toShopUser());
        }
        //  剩下的逻辑和登录一模一样
        // shiro登录
        Subject subject = SecurityUtils.getSubject();
        // 我们约定，openid为username，unionid为password
        AuthenticationToken authenticationToken = new UserToken(weChatRegisterDto.getOpenId(), weChatRegisterDto.getOpenId(), ShopUserRealm.class);
        try {
            subject.login(authenticationToken);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(ResultEnum.LOGIN_ERROR);
        }
        // 获取sessionId
        Serializable token = subject.getSession().getId();
        return new Result<>(new TokenVo(token));
    }

    /**
     * 绑定用户
     *
     * @param shopUserBindDto
     * @return
     */
    @RequestMapping(value = "/bindUser", method = RequestMethod.POST)
    public Result<?> bindUser(@RequestBody ShopUserBindDto shopUserBindDto) {
        ShopUser shopUser = shopUserService.bindUser(shopUserBindDto);
        // 转换成登录用户
        LoginUser loginUser = shopUser.toLoginUser();
        // 更新当前登录用户
        ShiroUtils.setUser(loginUser);
        return new Result<>("绑定成功");
    }

    /**
     * 获取登录用户
     *
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Result<LoginUser> info() {
        LoginUser loginUser = ShiroUtils.getLoginUser();
        return new Result<>(loginUser);
    }
}
