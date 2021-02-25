package com.lbj.pochi.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 登录Token返回视图类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenVo implements Serializable {
    /**
     * 登录时返回的token
     */
    private Serializable token;

}
