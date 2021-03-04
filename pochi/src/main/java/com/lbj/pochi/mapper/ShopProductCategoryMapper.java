package com.lbj.pochi.mapper;


import com.lbj.pochi.pojo.ShopProductCategory;
import com.lbj.pochi.utils.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 产品分类(shop_product_category)数据Mapper
 *
*/
@Component
public interface ShopProductCategoryMapper  {


    /**
     * 分页
     * @param page
     * @return
     */
     List<ShopProductCategory> getByPage(Page<ShopProductCategory> page);

    /**
     * 分页查询总条数
     * @param page
     * @return
     */
     int countByPage(Page<ShopProductCategory> page);

    /**
     * 根据父级菜单ID和菜单名称查询
     * @param shopProductCategory
     * @return
     */
    ShopProductCategory getByParentIdAndName(ShopProductCategory shopProductCategory);

    /**
     * 添加
     * @param shopProductCategory
     */
    void save(ShopProductCategory shopProductCategory);

    /**
     * 修改
     * @param shopProductCategory
     */
    void update(ShopProductCategory shopProductCategory);

    /**
     * 根据ID获取
     * @param id
     * @return
     */
    ShopProductCategory getById(Long id);

    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(Long id);

    /**
     * 获取所有分类
     * @return
     */
    List<ShopProductCategory> getAll();

    /**
     * 获取所有的 1,2级菜单
     * @return
     */
    List<ShopProductCategory> getSelectList();

    /**
     * 获取所有1,2级别分类
     * @param categoryIds
     * @return
     */
    List<ShopProductCategory> getByIds(List<Long> categoryIds);

    /**
     * 获取所有二级分类
     * @return
     */
    List<ShopProductCategory> getAllSecond();

    /**
     * 查询所有顶级分类
     * @return
     */
    List<ShopProductCategory> getAllTop();
}
