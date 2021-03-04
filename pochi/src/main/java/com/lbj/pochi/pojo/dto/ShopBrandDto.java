package com.lbj.pochi.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 品牌dto
 */
@Data
public class ShopBrandDto implements Serializable {

    /**
     * 编号
     */
    private Long id;
    /**
     * 名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否显示
     */
    private Integer showStatus;
    /**
     * logo
     */
    private String logo;
    /**
     * 分类ID集合
     */
    private List<Long> categoryIds;

}