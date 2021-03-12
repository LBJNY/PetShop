package com.lbj.pochi.controller;

import com.lbj.pochi.pojo.ShopProductCategory;
import com.lbj.pochi.pojo.vo.ShopProductCategoryVo;
import com.lbj.pochi.service.ShopProductCategoryService;
import com.lbj.pochi.utils.Page;
import com.lbj.pochi.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品分类controller
 */
@RestController
@RequestMapping("/shopProductCategory")
public class ShopProductCategoryController {
    @Autowired
    private ShopProductCategoryService shopProductCategoryService;

    /**
     * 添加分类
     *
     * @param shopProductCategory
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result<?> save(@RequestBody ShopProductCategory shopProductCategory) {
        shopProductCategoryService.save(shopProductCategory);
        return new Result<>("添加成功");
    }

    /**
     * 修改分类
     *
     * @param shopProductCategory
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<?> update(@RequestBody ShopProductCategory shopProductCategory) {
        shopProductCategoryService.update(shopProductCategory);
        return new Result<>("修改成功");
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Result<ShopProductCategory> get(@PathVariable Long id) {
        ShopProductCategory category = shopProductCategoryService.get(id);
        return new Result<>(category);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Result<?> delete(@PathVariable Long id) {
        shopProductCategoryService.delete(id);
        return new Result<>("删除成功");
    }

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/getByPage", method = RequestMethod.POST)
    public Result<Page<ShopProductCategory>> getByPage(@RequestBody Page<ShopProductCategory> page) {
        page = shopProductCategoryService.getByPage(page);
        return new Result<>(page);
    }

    /**
     * 树形列表查询
     *
     * @return
     */
    @RequestMapping(value = "/getTree", method = RequestMethod.GET)
    public Result<List<ShopProductCategoryVo>> getTree() {
        List<ShopProductCategoryVo> list = shopProductCategoryService.getTree();
        return new Result<>(list);
    }

    /**
     * 查询树形下拉框
     *
     * @return
     */
    @RequestMapping(value = "/getSelectTree", method = RequestMethod.GET)
    public Result<List<ShopProductCategoryVo>> getSelectTree() {
        List<ShopProductCategoryVo> list = shopProductCategoryService.getSelectTree();
        return new Result<>(list);
    }

    /**
     * 查询所有二级分类
     *
     * @return
     */
    @RequestMapping(value = "/getAllSecond", method = RequestMethod.GET)
    public Result<List<ShopProductCategory>> getAll() {
        List<ShopProductCategory> list = shopProductCategoryService.getAllSecond();
        return new Result<>(list);
    }

    /**
     * 查询首页的分类
     *
     * @return
     */
    @RequestMapping(value = "/getNavList", method = RequestMethod.GET)
    public Result<List<ShopProductCategory>> getNavList() {
        List<ShopProductCategory> list = shopProductCategoryService.getNavList();
        return new Result<>(list);
    }

}
