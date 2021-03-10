package com.lbj.pochi.controller;

import com.lbj.pochi.pojo.ShopPack;
import com.lbj.pochi.pojo.ShopProduct;
import com.lbj.pochi.pojo.dto.ShopProductDto;
import com.lbj.pochi.pojo.vo.ShopProductVo;
import com.lbj.pochi.service.ShopProductService;
import com.lbj.pochi.utils.Page;
import com.lbj.pochi.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品controller
 */
@RestController
@RequestMapping("/shopProduct")
public class ShopProductController {
    @Autowired
    private ShopProductService shopProductService;

    /**
     * 添加商品
     * @param shopProductDto
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Result<?> save(@RequestBody ShopProductDto shopProductDto){
        shopProductService.save(shopProductDto);
        return new Result<>("添加成功");
    }
    /**
     * 分页查询
     * @param page
     * @return
     */
    @RequestMapping(value = "/getByPage", method = RequestMethod.POST)
    public Result<Page<ShopProductVo>> getByPage(@RequestBody Page<ShopProductVo> page) {
        page = shopProductService.getByPage(page);
        return new Result<>(page);
    }
    /**
     * 查询回显数据
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getUpdate/{id}", method = RequestMethod.GET)
    public Result<ShopProductVo> getUpdate(@PathVariable Long id) {
        ShopProductVo vo = shopProductService.getUpdate(id);
        return new Result<>(vo);
    }
    /**
     * 修改商品
     *
     * @param shopProductDto
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<?> update(@RequestBody ShopProductDto shopProductDto) {
        shopProductService.update(shopProductDto);
        return new Result<>("修改成功");
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Result<?> delete(@PathVariable Long id) {
        shopProductService.delete(id);
        return new Result<>("删除成功");
    }

    /**
     * 上架
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/publish/{id}", method = RequestMethod.PUT)
    public Result<?> publish(@PathVariable Long id) {
        shopProductService.publish(id);
        return new Result<>("已上架");
    }

    /**
     * 下架
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/unPublish/{id}", method = RequestMethod.PUT)
    public Result<?> unPublish(@PathVariable Long id) {
        shopProductService.unPublish(id);
        return new Result<>("已下架");
    }

    /**
     * 新品
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/news/{id}", method = RequestMethod.PUT)
    public Result<?> news(@PathVariable Long id) {
        shopProductService.news(id);
        return new Result<>("修改成功");
    }

    /**
     * 非新品
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/old/{id}", method = RequestMethod.PUT)
    public Result<?> old(@PathVariable Long id) {
        shopProductService.old(id);
        return new Result<>("修改成功");
    }

    /**
     * 推荐
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/recommend/{id}", method = RequestMethod.PUT)
    public Result<?> recommend(@PathVariable Long id) {
        shopProductService.recommend(id);
        return new Result<>("推荐成功");
    }

    /**
     * 不推荐
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/unRecommend/{id}", method = RequestMethod.PUT)
    public Result<?> unRecommend(@PathVariable Long id) {
        shopProductService.unRecommend(id);
        return new Result<>("修改成功");
    }
    /**
     * 查询商品清单
     * @param packCode
     * @return
     */
    @RequestMapping(value = "/getProductDetailList/{packCode}", method = RequestMethod.GET)
    public Result<List<ShopProduct>> getProductDetailList(@PathVariable Long packCode) {
        List<ShopProduct> productList=shopProductService.getProductDetailList(packCode);
        return new Result<>(productList);
    }
    /**
     * 分页查询 没有套装
     * @param page
     * @return
     */
    @RequestMapping(value = "/getByPageHasNotPack", method = RequestMethod.POST)
    public Result<Page<ShopProductVo>> getByPageHasNotPack(@RequestBody Page<ShopProductVo> page) {
        page = shopProductService.getByPageHasNotPack(page);
        return new Result<>(page);
    }
}
