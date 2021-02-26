package com.lbj.pochi.service;

import com.lbj.pochi.pojo.SysRole;
import com.lbj.pochi.utils.Page;

import java.util.List;

public interface SysRoleService {
    /**
     * 添加角色
     *
     * @param sysRole
     */
    void save(SysRole sysRole);

    /**
     * 修改角色
     *
     * @param sysRole
     */
    void update(SysRole sysRole);

    /**
     * 删除角色--逻辑删除
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 获取角色
     *
     * @param id
     * @return
     */
    SysRole get(Long id);

    /**
     * 分页获取角色列表
     *
     * @param page
     * @return
     */
    Page<SysRole> getByPage(Page<SysRole> page);

    List<SysRole> getAll();
}
