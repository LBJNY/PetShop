package com.lbj.pochi.service;

import com.lbj.pochi.pojo.SysMenu;
import com.lbj.pochi.pojo.vo.SysMenuVo;
import com.lbj.pochi.utils.Page;

import java.util.List;

/**
 * 菜单service类
 */
public interface SysMenuService {
    Page<SysMenu> getByPage(Page<SysMenu> page);

    SysMenu get(Long id);

    void delete(Long id);

    void update(SysMenu sysMenu);

    void save(SysMenu sysMenu);

    List<SysMenuVo> getTreeList();

    List<Long> getRoleSelectMenu(Long roleId);
}
