package com.lbj.pochi.controller;


import com.lbj.pochi.pojo.SysNotice;
import com.lbj.pochi.service.SysNoticeService;
import com.lbj.pochi.utils.Page;
import com.lbj.pochi.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 公告控制器
 *
 */
@RestController
@RequestMapping("/sysNotice")
public class SysNoticeController {

    @Autowired
    private SysNoticeService sysNoticeService;

    /**
     * 添加
     *
     * @param sysNotice
     * @return
     */
    @PostMapping(value = "/save")
    public Result<?> save(@RequestBody SysNotice sysNotice) {
        sysNoticeService.save(sysNotice);
        return new Result<>("添加成功");
    }

    /**
     * 修改
     *
     * @param sysNotice
     * @return
     */
    @PutMapping(value = "/update")
    public Result<?> update(@RequestBody SysNotice sysNotice) {
        sysNoticeService.update(sysNotice);
        return new Result<>("修改成功");
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/get/{id}")
    public Result<SysNotice> get(@PathVariable Long id) {
        SysNotice sysNotice = sysNoticeService.get(id);
        return new Result<>(sysNotice);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delete/{id}")
    public Result<?> delete(@PathVariable Long id) {
        sysNoticeService.delete(id);
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
        sysNoticeService.enable(id);
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
        sysNoticeService.disable(id);
        return new Result<>("弃用成功");
    }

    /**
     * 分页查询
     * @param page
     * @return
     */
    @RequestMapping(value = "/getByPage", method = RequestMethod.POST)
    public Result<Page<SysNotice>> getByPage(@RequestBody Page<SysNotice> page) {
        page = sysNoticeService.getByPage(page);
        return new Result<>(page);
    }

    /**
     * 前端首页查询通知
     * @return
     */
    @RequestMapping(value = "/getNoticeList", method = RequestMethod.GET)
    public Result<List<SysNotice>> getNoticeList() {
        List<SysNotice> notices = sysNoticeService.getNoticeList();
        return new Result<>(notices);
    }
}