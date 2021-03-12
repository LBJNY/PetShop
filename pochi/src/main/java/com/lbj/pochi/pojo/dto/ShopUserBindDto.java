package com.lbj.pochi.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShopUserBindDto implements Serializable {

    private String phone;

    private String password;

    /**
     * 绑定类型，1绑定现有账户 ，2绑定新账户
     */
    private Integer bindType;

}