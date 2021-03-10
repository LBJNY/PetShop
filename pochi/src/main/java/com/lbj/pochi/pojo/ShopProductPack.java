package com.lbj.pochi.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品套装类
 */
@Data
public class ShopProductPack implements Serializable {

    /**
     * 编号，自增
     */
    private Long id;

    /**
     * 商品编号
     */
    private Long productId;

    /**
     * 套装编号
     */
    private Long packCode;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 警戒库存
     */
    private Integer lowStock;

    /**
     * 规格
     */
    private String specName;

    /**
     * 销量
     */
    private Integer sale;

    /**
     * 商品名称
     */
    private String productName;

}
