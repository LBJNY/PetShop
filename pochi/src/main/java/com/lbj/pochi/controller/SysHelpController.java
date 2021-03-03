package com.lbj.pochi.controller;

import com.lbj.pochi.pojo.SysBanner;
import com.lbj.pochi.pojo.SysHelp;
import com.lbj.pochi.service.SysBannerService;
import com.lbj.pochi.service.SysHelpService;
import com.lbj.pochi.utils.Page;
import com.lbj.pochi.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 轮播图控制器
 */
@RestController
@RequestMapping("/sysHelp")
public class SysHelpController {

    @Autowired
    private SysHelpService sysHelpService;

    /**
     * 添加
     *
     * @param sysHelp
     * @return
     */
    @PostMapping(value = "/save")
    public Result<?> save(@RequestBody SysHelp sysHelp) {
        sysHelpService.save(sysHelp);
        return new Result<>("添加成功");
    }

    /**
     * 修改
     *
     * @param sysHelp
     * @return
     */
    @PutMapping(value = "/update")
    public Result<?> update(@RequestBody SysHelp sysHelp) {
        sysHelpService.update(sysHelp);
        return new Result<>("修改成功");
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/get/{id}")
    public Result<SysHelp> get(@PathVariable Long id) {
        SysHelp sysHelp = sysHelpService.get(id);
        return new Result<>(sysHelp);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delete/{id}")
    public Result<?> delete(@PathVariable Long id) {
        sysHelpService.delete(id);
        return new Result<>("删除成功");
    }

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/getByPage", method = RequestMethod.POST)
    public Result<Page<SysHelp>> getByPage(@RequestBody Page<SysHelp> page) {
        page = sysHelpService.getByPage(page);
        return new Result<>(page);
    }

}