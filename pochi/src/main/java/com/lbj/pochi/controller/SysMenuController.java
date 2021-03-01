package com.lbj.pochi.controller;

import com.lbj.pochi.pojo.SysMenu;
import com.lbj.pochi.pojo.vo.SysMenuVo;
import com.lbj.pochi.service.SysMenuService;
import com.lbj.pochi.utils.Page;
import com.lbj.pochi.utils.Result;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单controller
 */
@RestController
@RequestMapping("/sysMenu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 添加菜单
     * @param sysMenu
     * @return
     */
    @PostMapping("/save")
    public Result<?> save(@RequestBody SysMenu sysMenu){
        sysMenuService.save(sysMenu);
        return new Result<>("添加成功");
    }

    /**
     * 修改菜单
     * @param sysMenu
     * @return
     */
    @PutMapping("/update")
    public Result<?> update(@RequestBody SysMenu sysMenu){
        sysMenuService.update(sysMenu);
        return new Result<>("修改成功");
    }

    /**
     * 删除菜单
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Long id){
        sysMenuService.delete(id);
        return new Result<>("删除成功");
    }

    /**
     * 根据id获取菜单
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public Result<SysMenu> get(@PathVariable Long id){
        SysMenu sysMenu=sysMenuService.get(id);
        return new Result<>(sysMenu);
    }

    /**
     * 分页查询
     * @param page
     * @return
     */
    @PostMapping("/getByPage")
    public Result<Page<SysMenu>> getByPage(@RequestBody Page<SysMenu> page){
        page=sysMenuService.getByPage(page);
        return new Result<>(page);
    }

    /**
     * 获取菜单树列表
     * @return List<SysMenuVo>
     */
    @GetMapping("/getTreeList")
    public Result<List<SysMenuVo>> getTreeList(){
        List<SysMenuVo> list=sysMenuService.getTreeList();
        return new Result<>(list);
    }
    /**
     * 根据角色ID查询选中的菜单
     * 这里不查询父级菜单
     * @param roleId
     * @return
     */
    @GetMapping("/getRoleSelectMenu/{roleId}")
    public Result<List<Long>> getRoleSelectMenu(@PathVariable Long roleId) {
        List<Long> ids = sysMenuService.getRoleSelectMenu(roleId);
        return new Result<>(ids);
    }
}
