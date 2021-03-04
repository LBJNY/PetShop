package com.lbj.pochi.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 商品分类实体类
 */
@Data
public class ShopProductCategory implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 父级ID
     */
    private Long parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 是否显示在导航栏
     */
    private Integer navStatus;

    /**
     * 排序，越小越靠前
     */
    private Integer sort;

    /**
     * 图标
     */
    private String icon;

}
