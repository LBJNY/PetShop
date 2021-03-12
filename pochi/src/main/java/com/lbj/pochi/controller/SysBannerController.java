package com.lbj.pochi.controller;

import com.lbj.pochi.pojo.SysBanner;
import com.lbj.pochi.service.SysBannerService;
import com.lbj.pochi.utils.Page;
import com.lbj.pochi.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 轮播图控制器
 *
 */
@RestController
@RequestMapping("/sysBanner")
public class SysBannerController {

    @Autowired
    private SysBannerService sysBannerService;

    /**
     * 添加
     *
     * @param sysBanner
     * @return
     */
    @PostMapping(value = "/save")
    public Result<?> save(@RequestBody SysBanner sysBanner) {
        sysBannerService.save(sysBanner);
        return new Result<>("添加成功");
    }

    /**
     * 修改
     *
     * @param sysBanner
     * @return
     */
    @PutMapping(value = "/update")
    public Result<?> update(@RequestBody SysBanner sysBanner) {
        sysBannerService.update(sysBanner);
        return new Result<>("修改成功");
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/get/{id}")
    public Result<SysBanner> get(@PathVariable Long id) {
        SysBanner sysBanner = sysBannerService.get(id);
        return new Result<>(sysBanner);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delete/{id}")
    public Result<?> delete(@PathVariable Long id) {
        sysBannerService.delete(id);
        return new Result<>("删除成功");
    }

    /**
     * 根据id启用
     *
     * @param id
     * @return
     */
    @PutMapping(value = "/enable/{id}")
    public Result<?> enable(@PathVariable Long id) {
        sysBannerService.enable(id);
        return new Result<>("启用成功");
    }

    /**
     * 根据id弃用
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/disable/{id}", method = RequestMethod.PUT)
    public Result<?> disable(@PathVariable Long id) {
        sysBannerService.disable(id);
        return new Result<>("弃用成功");
    }

    /**
     * 分页查询
     * @param page
     * @return
     */
    @RequestMapping(value = "/getByPage", method = RequestMethod.POST)
    public Result<Page<SysBanner>> getByPage(@RequestBody Page<SysBanner> page) {
        page = sysBannerService.getByPage(page);
        return new Result<>(page);
    }

    /**
     * 首页查询轮播图
     * @return
     */
    @RequestMapping(value = "/getBannerList", method = RequestMethod.GET)
    public Result<List<Banner>> getBannerList() {
        List<Banner> list = sysBannerService.getBannerList();
        return new Result<>(list);
    }
}