package com.lbj.pochi.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.ui.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * 后台用户表(sys_user)实体类
 *
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class SysUser  implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;
    /**
     * 账号
     */
    private String username;
    /**
     * 微信小程序openid
     */
    private String openid;
    /**
     * 密码
     */
    private String password;
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
     * 备注信息
     */
    private String note;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 最后登录时间
     */
    private Date loginTime;
    /**
     * 帐号启用状态：0->禁用；1->启用
     */
    private Integer status;
    /**
     * 是否删除，1是0否
     */
    private Integer deleted;

}