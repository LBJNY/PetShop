package com.lbj.pochi.mapper;

import com.lbj.pochi.pojo.ShopPack;
import com.lbj.pochi.pojo.ShopProduct;
import com.lbj.pochi.pojo.vo.ShopProductVo;
import com.lbj.pochi.utils.Page;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * shopProduct的Mapper
 */
@Component
public interface ShopProductMapper {

    /**
     * 保存商品
     */
    void save(ShopProduct shopProduct);

    /**
     * 分页获取
     * @param page
     * @return
     */
    List<ShopProduct> getByPage(Page<ShopProductVo> page);

    /**
     * 根据条件获取总条数
     * @param page
     * @return
     */
    int countByPage(Page<ShopProductVo> page);

    /**
     * 根据ID获取商品信息
     * @param id
     * @return
     */
    ShopProduct getById(Long id);

    /**
     * 修改
     * @param shopProduct
     */
    void update(ShopProduct shopProduct);

    /**
     * 是否推荐
     * @param product
     */
    void updateRecommend(ShopProduct product);

    /**
     * 是否新品
     * @param product
     */
    void updateNewStatus(ShopProduct product);

    /**
     * 是否上架
     * @param product
     */
    void updatePublish(ShopProduct product);

    /**
     * 删除
     * @param id
     */
    void delete(Long id);

    /**
     * 根据ID集合获取商品
     * @param ids
     * @return
     */
    List<ShopProduct> getByIds(List<Long> ids);

    /**
     * 分页查询 没有套装
     * @param page
     * @return
     */
    List<ShopProduct> getByPageHasNotPack(Page<ShopProductVo> page);

    /**
     * 分页查询 没有套装条数
     * @param page
     * @return
     */
    int countByPageHasNotPack(Page<ShopProductVo> page);


}
