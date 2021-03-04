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

    void saveBatch(List<ShopBrandCategory> shopBrandCategories);

    void deleteByBrandId(Long id);

    List<ShopBrandCategory> getByBrandId(Long id);
}
