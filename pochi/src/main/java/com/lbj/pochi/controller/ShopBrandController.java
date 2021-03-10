package com.lbj.pochi.controller;

import com.lbj.pochi.pojo.ShopBrand;
import com.lbj.pochi.pojo.ShopProductCategory;
import com.lbj.pochi.pojo.dto.ShopBrandDto;
import com.lbj.pochi.pojo.vo.ShopBrandVo;
import com.lbj.pochi.service.ShopBrandService;
import com.lbj.pochi.utils.Page;
import com.lbj.pochi.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 品牌类controller
 */
@RestController
@RequestMapping("/shopBrand")
public class ShopBrandController {
    @Autowired
    private ShopBrandService shopBrandService;
    /**
     * 添加
     * @param shopBrandDto
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result<?> save(@RequestBody ShopBrandDto shopBrandDto) {
        shopBrandService.save(shopBrandDto);
        return new Result<>("添加成功");
    }

    /**
     * 修改
     * @param shopBrandDto
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<?> update(@RequestBody ShopBrandDto shopBrandDto) {
        shopBrandService.update(shopBrandDto);
        return new Result<>("修改成功");
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Result<?> delete(@PathVariable Long id) {
        shopBrandService.delete(id);
        return new Result<>("删除成功");
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Result<ShopBrandVo> get(@PathVariable Long id) {
        ShopBrandVo vo = shopBrandService.get(id);
        return new Result<>(vo);
    }

    /**
     * 根据id查询用于修改的数据回显
     * @param id
     * @return
     */
    @RequestMapping(value = "/getUpdate/{id}", method = RequestMethod.GET)
    public Result<ShopBrandDto> getUpdate(@PathVariable Long id) {
        ShopBrandDto vo = shopBrandService.getUpdate(id);
        return new Result<>(vo);
    }

    /**
     * 分页查询
     * @param page
     * @return
     */
    @RequestMapping(value = "/getByPage",  method = RequestMethod.POST)
    public Result<Page<ShopBrand>> getByPage(@RequestBody Page<ShopBrand> page) {
        page = shopBrandService.getByPage(page);
        return new Result<>(page);
    }
    /**
     * 分页查询
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "/getByCategoryId/{categoryId}",  method = RequestMethod.GET)
    public Result<List<ShopBrand>> getByCategoryId(@PathVariable Long categoryId) {
        List<ShopBrand> list= shopBrandService.getByCategoryId(categoryId);
        return new Result<>(list);
    }
    /**
     * 分页查询
     * @param name
     * @return
     */
    @RequestMapping(value = "/getByName/{name}",  method = RequestMethod.GET)
    public Result<List<ShopBrand>> getByName(@PathVariable String name) {
        List<ShopBrand> list= shopBrandService.getByName(name);
        return new Result<>(list);
    }

}
