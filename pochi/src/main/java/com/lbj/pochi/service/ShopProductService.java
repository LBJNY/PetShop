package com.lbj.pochi.service;

import com.lbj.pochi.pojo.ShopPack;
import com.lbj.pochi.pojo.ShopProduct;
import com.lbj.pochi.pojo.dto.ShopProductDto;
import com.lbj.pochi.pojo.vo.ShopProductVo;
import com.lbj.pochi.utils.Page;

import java.util.List;

/**
 * 商品service类
 */
public interface ShopProductService {
    /**
     * 添加商品
     * @param shopProductDto
     */
    void save(ShopProductDto shopProductDto);

    /**
     * 分页获取
     * @param page
     * @return
     */
    Page<ShopProductVo> getByPage(Page<ShopProductVo> page);

    /**
     * 获取修改回显数据
     * @param id
     * @return
     */
    ShopProductVo getUpdate(Long id);

    /**
     * 修改
     * @param shopProductDto
     */
    void update(ShopProductDto shopProductDto);

    /**
     * 删除
     * @param id
     */
    void delete(Long id);

    /**
     * 上架
     * @param id
     */
    void publish(Long id);

    /**
     * 下架
     * @param id
     */
    void unPublish(Long id);

    /**
     * 新品
     * @param id
     */
    void news(Long id);

    /**
     * 非新品
     * @param id
     */
    void old(Long id);

    /**
     * 推荐
     * @param id
     */
    void recommend(Long id);

    /**
     * 非推荐
     * @param id
     */
    void unRecommend(Long id);

    /**
     * 根据套装ID查询集合
     * @param packCode
     * @return
     */
    List<ShopProduct> getProductDetailList(Long packCode);

    /**
     *
     * @param page
     * @return
     */
    Page<ShopProductVo> getByPageHasNotPack(Page<ShopProductVo> page);
}
