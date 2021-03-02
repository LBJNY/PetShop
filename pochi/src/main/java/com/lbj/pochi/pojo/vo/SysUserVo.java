package com.lbj.pochi.pojo.vo;


import com.lbj.pochi.pojo.SysRole;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 系统用户视图类
 *
 */
@Data
public class SysUserVo implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 微信的openid
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
     * 备注
     */
    private String note;

    /**
     * 账号启用状态，1是0否
     */
    private Integer status;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 最后登录时间
     */
    private String loginTime;
    /**
     * 角色
     */
    private SysRole sysRole;

    /**
     * 权限列表
     */
    private List<String> auths;
}

