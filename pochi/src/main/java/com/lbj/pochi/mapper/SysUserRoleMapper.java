package com.lbj.pochi.mapper;

import com.lbj.pochi.pojo.SysUserRole;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户和角色关联表(sys_user_role)数据Mapper
 *
*/
@Component
public interface SysUserRoleMapper {

    /**
     * 添加用户角色信息
     * @param sysUserRole
     */
    void save(SysUserRole sysUserRole);

    /**
     * 根据用户ID删除用户角色信息
     * @param id
     */
    void deleteByUserId(Long id);

    /**
     * 根据用户ID查询用户角色信息
     * @param id
     * @return
     */
    List<SysUserRole> getByUserId(Long id);
}
