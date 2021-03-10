package com.lbj.pochi.mapper;



import com.lbj.pochi.pojo.ShopBrandCategory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 品牌分类表(shop_brand_category)数据Mapper
 *
*/
@Component
public interface ShopBrandCategoryMapper  {

    /**
     * 批量添加
     * @param shopBrandCategories
     */
    void saveBatch(List<ShopBrandCategory> shopBrandCategories);

    /**
     * 批量删除
     * @param id
     */
    void deleteByBrandId(Long id);

    /**
     * 获取
     * @param id
     * @return
     */
    List<ShopBrandCategory> getByBrandId(Long id);

    /**
     * 根据分类id查询
     * @param categoryId
     * @return
     */
    List<ShopBrandCategory> getByCategoryId(Long categoryId);
}
