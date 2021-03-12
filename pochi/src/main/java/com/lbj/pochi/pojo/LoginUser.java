package com.lbj.pochi.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户登录返回信息表
 */
@Data
public class LoginUser implements Serializable {


    private Long id;

    /**
     * 账号
     */
    private String username;

    /**
     * 微信小程序openid
     */
    private String openId;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String header;

    /**
     * 最后登录时间
     */
    private String loginTime;

    /**
     * 权限列表
     */
    private List<String> auths;

}