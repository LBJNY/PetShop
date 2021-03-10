package com.lbj.pochi.mapper;

import com.lbj.pochi.pojo.ShopProductPack;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 商品套装Mapper
 */
@Component
public interface ShopProductPackMapper {

    /**
     * 批量保存
     * @param packList
     */
    void saveBatch(List<ShopProductPack> packList);

    /**
     * 根据套装编号删除
     * @param packCode
     */
    void deleteByPackCode(long packCode);

    /**
     * 根据套装编号查询
     * @param id
     * @return
     */
    List<ShopProductPack> getByPackCode(Long id);

    /**
     * 根据Id删除
     * @param id
     */
    void deleteById(Long id);

    /**
     * 根据商品ID获取商品套装
     * @param id
     * @return
     */
    ShopProductPack getByProductId(Long id);

    /**
     * 根据商品ID集合删除
     * @param productIds
     */
    void deleteByProductIds(List<Long> productIds);

    /**
     * 根据id获取 商品-套装
     * @param productIds
     * @return
     */
    List<ShopProductPack> getByProductIds(List<Long> productIds);
}
