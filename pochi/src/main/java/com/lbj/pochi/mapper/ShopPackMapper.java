package com.lbj.pochi.mapper;

import com.lbj.pochi.pojo.ShopPack;
import com.lbj.pochi.pojo.ShopProductPack;
import com.lbj.pochi.utils.Page;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 商品套装Mapper
 */
@Component
public interface ShopPackMapper {

    /**
     * 修改
     *
     * @param shopPack
     */
    void update(ShopPack shopPack);

    /**
     * 删除
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 分页获取
     *
     * @param page
     * @return
     */
    List<ShopPack> getByPage(Page<ShopPack> page);

    /**
     * 获取总条数
     *
     * @param page
     * @return
     */
    Integer countByPage(Page<ShopPack> page);

    /**
     * 根据ID获取
     *
     * @param id
     * @return
     */
    ShopPack get(Long id);

    /**
     * 保存套装
     * @param shopPack
     */
    void save(ShopPack shopPack);

    /**
     * 更新商品数
     * @param packCode
     * @param size
     */
    void updateProductCount(Long packCode, int size);
}
