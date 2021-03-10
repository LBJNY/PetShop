package com.lbj.pochi.mapper;


import com.lbj.pochi.pojo.ShopBrand;
import com.lbj.pochi.utils.Page;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 品牌表(shop_brand)数据Mapper
 *
*/
@Component
public interface ShopBrandMapper  {

    /**
     * 分页查询
     * @param page
     * @return
     */
    List<ShopBrand> getByPage(Page<ShopBrand> page);

    /**
     * 获取总条数
     * @param page
     * @return
     */
    Integer countByPage(Page<ShopBrand> page);

    /**
     * 根据Id获取
     * @param id
     * @return
     */
    ShopBrand get(Long id);

    /**
     * 添加
     * @param shopBrand
     */
    void save(ShopBrand shopBrand);

    /**
     * 更新
     * @param shopBrand
     */
    void update(ShopBrand shopBrand);

    /**
     * 删除
     * @param id
     */
    void delete(Long id);
    /**
     * 根据品牌Ids查询
     * @param brandIds
     * @return
     */
    List<ShopBrand> getByIds(List<Long> brandIds);

    /**
     * 根据名称查询
     * @param name
     * @return
     */
    List<ShopBrand> getByName(String name);
}
