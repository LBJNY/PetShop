package com.lbj.pochi.service;

import com.lbj.pochi.pojo.SysMenu;
import com.lbj.pochi.pojo.vo.RouterVo;
import com.lbj.pochi.pojo.vo.SysMenuVo;
import com.lbj.pochi.utils.Page;

import java.util.List;

/**
 * 菜单service类
 */
public interface SysMenuService {
    /**
     * 分页获取菜单
     *
     * @param page
     * @return
     */
    Page<SysMenu> getByPage(Page<SysMenu> page);

    /**
     * 根据id获取菜单
     *
     * @param id
     * @return
     */
    SysMenu get(Long id);

    /**
     * 根据Id删除菜单
     * 逻辑删除
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 更新菜单
     *
     * @param sysMenu
     */
    void update(SysMenu sysMenu);

    /**
     * 添加菜单
     *
     * @param sysMenu
     */
    void save(SysMenu sysMenu);

    /**
     * 获取树形菜单列表
     *
     * @return
     */
    List<SysMenuVo> getTreeList();

    /**
     * 根据角色Id获取菜单ID集合
     *
     * @param roleId
     * @return
     */
    List<Long> getRoleSelectMenu(Long roleId);

    /**
     * 获取路由集合
     *
     * @return
     */
    List<RouterVo> getRouters();
}
