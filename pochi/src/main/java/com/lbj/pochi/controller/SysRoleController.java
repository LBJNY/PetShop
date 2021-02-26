package com.lbj.pochi.controller;

import com.lbj.pochi.pojo.SysRole;
import com.lbj.pochi.service.SysRoleService;
import com.lbj.pochi.utils.Page;
import com.lbj.pochi.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sysRole")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 添加角色
     *
     * @param sysRole
     * @return
     */
    @PostMapping("/save")
    public Result<?> save(@RequestBody SysRole sysRole) {
        sysRoleService.save(sysRole);
        return new Result<>("添加成功");
    }

    /**
     * 修改角色
     *
     * @param sysRole
     * @return
     */
    @PutMapping("/update")
    public Result<?> update(@RequestBody SysRole sysRole) {
        sysRoleService.update(sysRole);
        return new Result<>("修改成功");
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Long id) {
        sysRoleService.delete(id);
        return new Result<>("删除成功");
    }

    /**
     * 根据id获取角色
     *
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public Result<SysRole> get(@PathVariable Long id) {
        SysRole sysRole = sysRoleService.get(id);
        return new Result<>(sysRole);
    }

    /**
     * 分页获取列表
     *
     * @param page
     * @return
     */
    @PostMapping("getByPage")
    public Result<Page<SysRole>> getByPage(@RequestBody Page<SysRole> page) {
        page = sysRoleService.getByPage(page);
        return new Result<>(page);
    }
    /**
     * 查询所有角色
     * @return
     */
    @GetMapping(value = "/getAll")
    public Result<List<SysRole>> getAll() {
        List<SysRole> list = sysRoleService.getAll();
        return new Result<>(list);
    }

}
