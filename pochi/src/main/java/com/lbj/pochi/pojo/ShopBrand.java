package com.lbj.pochi.pojo;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 品牌表(shop_brand)实体类
 *
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ShopBrand implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	private Long id;
    /**
     * 品牌名称
     */
    private String name;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 显示，1是0否
     */
    private Integer showStatus;
    /**
     * 品牌logo
     */
    private String logo;

}