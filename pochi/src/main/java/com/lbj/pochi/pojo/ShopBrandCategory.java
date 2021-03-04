package com.lbj.pochi.pojo;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 品牌分类表(shop_brand_category)实体类
 *
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ShopBrandCategory  implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	private Long id;
    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 分类id
     */
    private Long categoryId;

}