package com.lbj.pochi.mapper;


import com.lbj.pochi.pojo.ShopBrand;
import com.lbj.pochi.utils.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 品牌表(shop_brand)数据Mapper
 *
*/
@Component
public interface ShopBrandMapper  {

    List<ShopBrand> getByPage(Page<ShopBrand> page);

    Integer countByPage(Page<ShopBrand> page);

    ShopBrand get(Long id);

    void save(ShopBrand shopBrand);

    void update(ShopBrand shopBrand);

    void delete(Long id);
}
