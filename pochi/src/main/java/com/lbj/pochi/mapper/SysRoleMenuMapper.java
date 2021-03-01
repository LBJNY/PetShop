package com.lbj.pochi.mapper;

import com.lbj.pochi.pojo.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 角色和菜单关联表(sys_role_menu)数据Mapper
 *
*/
@Component
public interface SysRoleMenuMapper  {
    /**
     * 批量存入
     * @param roleMenuList
     */
    void saveBatch(List<SysRoleMenu> roleMenuList);

    /**
     * 根据角色Id删除
     * @param roleId
     */
    void deleteByRoleId(Long roleId);

    /**
     * 根据角色Id获取角色菜单集合
     * @param id
     * @return
     */
    List<SysRoleMenu> getByRoleId(Long id);
}
