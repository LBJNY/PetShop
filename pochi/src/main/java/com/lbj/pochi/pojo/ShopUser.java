package com.lbj.pochi.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 会员表实体类
 * </p>
 */
@Data
//@Table(name = "shop_user")
public class ShopUser implements Serializable {

    private static final long serialVersionUID = 981816007678159811L;

    /**
     * id
     */
//    @Id
//    @Column(name = "id")
    private Long id;

    /**
     * 手机号
     */
//    @Column(name = "phone")
    private String phone;

    /**
     * 密码
     */
//    @Column(name = "password")
    private String password;

    /**
     * 昵称
     */
//    @Column(name = "nickname")
    private String nickname;

    /**
     * 状态，1正常0封禁
     */
//    @Column(name = "status")
    private Integer status;

    /**
     * 头像
     */
//    @Column(name = "header")
    private String header;

    /**
     * 性别，1男2女
     */
//    @Column(name = "gender")
    private Integer gender;

    /**
     * 签名
     */
//    @Column(name = "note")
    private String note;

    /**
     * openid
     */
    //    @Column(name = "openid")
    private String openId;

    /**
     * 积分
     */
    //    @Column(name = "point")
    private BigDecimal point;

    /**
     * 历史积分
     */
    //    @Column(name = "history_point")
    private BigDecimal historyPoint;

    /**
     * 创建时间
     */
    //    @Column(name = "create_time")
    private String createTime;

    /**
     * 更新时间
     */
    //    @Column(name = "update_time")
    private String updateTime;

    /**
     * 是否删除，1是0否
     */
    //    @Column(name = "deleted")
    private Integer deleted;

    public LoginUser toLoginUser() {
        LoginUser user = new LoginUser();
        user.setId(id);
        user.setUsername(phone);
        //user.setPassword(password);
        user.setNickName(nickname);
        user.setHeader(header);
        user.setOpenId(openId);
        return user;
    }

}
