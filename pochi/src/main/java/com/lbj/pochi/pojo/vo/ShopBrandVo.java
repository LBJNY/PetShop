package com.lbj.pochi.pojo.vo;

import com.lbj.pochi.pojo.ShopProductCategory;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 品牌视图类Vo
 */
@Data
public class ShopBrandVo implements Serializable {

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
    private Long sort;

    /**
     * 是否显示
     */
    private Integer showStatus;
    /**
     * logo
     */
    private String logo;
    /**
     * 分类集合
     */
    private List<ShopProductCategory> categoryList;

}
