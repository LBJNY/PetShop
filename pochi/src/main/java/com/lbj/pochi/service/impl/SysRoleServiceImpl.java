package com.lbj.pochi.service.impl;

import com.lbj.pochi.mapper.SysRoleMapper;
import com.lbj.pochi.pojo.SysRole;
import com.lbj.pochi.pojo.SysUser;
import com.lbj.pochi.service.SysRoleService;
import com.lbj.pochi.utils.Page;
import com.lbj.pochi.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Override
    public void save(SysRole sysRole) {
        // 设置创建人和修改人为用户名
        SysUser loginUser = ShiroUtils.getLoginUser();
        assert loginUser != null;
        String username=loginUser.getUsername();
        sysRole.setCreateBy(username);
        sysRole.setUpdateBy(username);
        sysRoleMapper.save(sysRole);
    }

    @Override
    public void update(SysRole sysRole) {
        // 设置创建人和修改人为用户名
        SysUser loginUser = ShiroUtils.getLoginUser();
        assert loginUser != null;
        String username=loginUser.getUsername();
        sysRole.setUpdateBy(username);
        sysRoleMapper.update(sysRole);
    }

    @Override
    public void delete(Long id) {
        sysRoleMapper.delete(id);
    }

    @Override
    public SysRole get(Long id) {
        return sysRoleMapper.get(id);
    }

    @Override
    public Page<SysRole> getByPage(Page<SysRole> page) {
        // 设置默认的当前页和每页条数
        Integer pageNumber = page.getCurrentPage();
        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
            page.setCurrentPage(pageNumber);
        }
        List<SysRole> roleList = sysRoleMapper.getByPage(page);
        Integer totalCount = sysRoleMapper.countByPage(page);
        page.setList(roleList);
        page.setTotalCount(totalCount);
        return page;
    }

    @Override
    public List<SysRole> getAll() {
        return sysRoleMapper.getAll();
    }
}
