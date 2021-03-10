package com.lbj.pochi.service;

import com.lbj.pochi.pojo.ShopPack;
import com.lbj.pochi.pojo.dto.ShopPackDto;
import com.lbj.pochi.utils.Page;

/**
 * 商品套装Service
 */
public interface ShopPackService {
    /**
     * 添加
     * @param shopPackDto
     */
    void save(ShopPackDto shopPackDto);

    /**
     * 修改
     * @param shopPackDto
     */
    void update(ShopPackDto shopPackDto);

    /**
     * 根据ID获取
     * @param id
     * @return
     */
    ShopPackDto getById(Long id);

    /**
     * 根据Id删除
     * @param id
     */
    void delete(Long id);

    /**
     * 分页获取
     * @param page
     * @return
     */
    Page<ShopPack> getByPage(Page<ShopPack> page);
}
