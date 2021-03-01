package com.lbj.pochi.service;

import com.lbj.pochi.pojo.SysUser;
import com.lbj.pochi.pojo.vo.SysUserVo;
import com.lbj.pochi.utils.Page;

/**
 * 用户service类
 */
public interface SysUserService {
    /**
     * 更新用户登陆时间
     *
     * @param username 用户名
     */
    void updateLoginTime(String username);

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    SysUser getByUserName(String username);

    /**
     * 添加用户信息
     *
     * @param sysUserVo
     */
    void save(SysUserVo sysUserVo);

    /**
     * 更新用户信息
     *
     * @param sysUserVo
     */
    void update(SysUserVo sysUserVo);

    /**
     * 删除用户信息
     * 此处为逻辑删除,并不是真正的删除
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 启用用户
     * 先查询是否有该用户
     *
     * @param id
     */
    void enable(Long id);

    /**
     * 禁用用户
     * 先查询是否有该用户
     *
     * @param id
     */
    void disable(Long id);

    /**
     * 根据用户id查询用户信息
     *
     * @param id
     * @return
     */
    SysUserVo get(Long id);

    /**
     * 分页查询用户信息
     *
     * @param page
     * @return
     */
    Page<SysUser> getByPage(Page<SysUser> page);
}
