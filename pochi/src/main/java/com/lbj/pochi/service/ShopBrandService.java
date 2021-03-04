package com.lbj.pochi.service;

import com.lbj.pochi.pojo.ShopBrand;
import com.lbj.pochi.pojo.dto.ShopBrandDto;
import com.lbj.pochi.pojo.vo.ShopBrandVo;
import com.lbj.pochi.utils.Page;

/**
 * 品牌类service
 */
public interface ShopBrandService {
    /**
     * 分页查询
     * @param page
     * @return
     */
    Page<ShopBrand> getByPage(Page<ShopBrand> page);

    /**
     * 根据Id获取修改回显
     * @param id
     * @return
     */
    ShopBrandDto getUpdate(Long id);

    /**
     * 根据Id获取品牌
     * @param id
     * @return
     */
    ShopBrandVo get(Long id);

    /**
     * 根据Id删除
     * @param id
     */
    void delete(Long id);

    /**
     * 修改
     * @param shopBrandDto
     */
    void update(ShopBrandDto shopBrandDto);

    /**
     * 添加
     * @param shopBrandDto
     */
    void save(ShopBrandDto shopBrandDto);
}
