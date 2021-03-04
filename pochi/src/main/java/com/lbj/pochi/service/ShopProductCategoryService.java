package com.lbj.pochi.service;

import com.lbj.pochi.pojo.ShopProductCategory;
import com.lbj.pochi.pojo.vo.ShopProductCategoryVo;
import com.lbj.pochi.utils.Page;

import java.util.List;

/**
 * 商品分类service
 */
public interface ShopProductCategoryService {
    /**
     * 添加分类
     *
     * @param shopProductCategory
     */
    void save(ShopProductCategory shopProductCategory);

    /**
     * 修改分类
     *
     * @param shopProductCategory
     */
    void update(ShopProductCategory shopProductCategory);

    /**
     * 根据ID获取分类
     *
     * @param id
     * @return
     */
    ShopProductCategory get(Long id);

    /**
     * 根据Id删除
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    Page<ShopProductCategory> getByPage(Page<ShopProductCategory> page);

    /**
     * 树形列表查询
     *
     * @return
     */
    List<ShopProductCategoryVo> getTree();

    /**
     * 查询树形下拉框
     *
     * @return
     */
    List<ShopProductCategoryVo> getSelectTree();

}
