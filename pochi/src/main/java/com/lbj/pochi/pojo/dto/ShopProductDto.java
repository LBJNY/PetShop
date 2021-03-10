package com.lbj.pochi.pojo.dto;

import com.lbj.pochi.pojo.ShopPack;
import com.lbj.pochi.pojo.ShopProduct;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商品dto类
 */
@Data
public class ShopProductDto implements Serializable {

    private Long id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品图片
     */
    private String pic;

    /**
     * 副标题
     */
    private String subTitle;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 库存预警值
     */
    private Integer lowStock;

    /**
     * 轮播图图片，至少1张
     */
    private List<String> albumPicList;

    /**
     * 详情
     */
    private String productContent;

    private String specs;
    private BigDecimal point;

    /**
     * 商品分类id
     */
    private Long categoryId;

    /**
     * 套装编号
     */
    private Long packCode;

    /**
     * 品牌id
     */
    private Long brandId;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 运费
     */
    private BigDecimal transFee;

    /**
     * 是否上架，1是0否
     */
    private Integer publishStatus;

    /**
     * 是否新品，1是0否
     */
    private Integer newStatus;

    /**
     * 是否推荐，1是0否
     */
    private Integer recommendStatus;

    /**
     * 是否审核，1通过，0未审核，-1不通过
     */
    private Integer verifyStatus;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 促销价格
     */
    private BigDecimal promotionPrice;

    private String productComment;

    /**
     * 商品列表
     */
    private List<ShopProduct> productList;

    /**
     * 商品套装
     */
    private ShopPack shopPack;

}