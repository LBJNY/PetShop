package com.lbj.pochi.pojo.dto;

import com.lbj.pochi.pojo.ShopProduct;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品套装类dto
 */
@Data
public class ShopPackDto implements Serializable {

    /**
     * 编号，雪花算法
     */
    private Long id;

    /**
     * 套装名称
     */
    private String name;

    /**
     * 商品列表
     */
    private List<ShopProduct> productList;

}