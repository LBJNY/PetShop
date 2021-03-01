package com.lbj.pochi.service.impl;

import com.lbj.pochi.mapper.SysRoleMapper;
import com.lbj.pochi.mapper.SysRoleMenuMapper;
import com.lbj.pochi.pojo.SysRole;
import com.lbj.pochi.pojo.SysRoleMenu;
import com.lbj.pochi.pojo.SysUser;
import com.lbj.pochi.pojo.vo.SysRoleVo;
import com.lbj.pochi.service.SysRoleService;
import com.lbj.pochi.utils.Page;
import com.lbj.pochi.utils.ShiroUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色service实现类
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 添加角色
     *
     * @param sysRoleVo
     */
    @Override
    public void save(SysRoleVo sysRoleVo) {
        // 设置创建人和修改人为用户名
        SysUser loginUser = ShiroUtils.getLoginUser();
        //拷贝属性值
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(sysRoleVo, sysRole);

        assert loginUser != null;
        String username = loginUser.getUsername();
        sysRole.setCreateBy(username);
        sysRole.setUpdateBy(username);
        sysRoleMapper.save(sysRole);

        // 下面开始添加角色权限数据
        saveRoleMenu(sysRoleVo, sysRole);
    }

    /**
     * 更新角色
     *
     * @param sysRoleVo
     */
    @Override
    public void update(SysRoleVo sysRoleVo) {
        // 设置创建人和修改人为用户名
        SysUser loginUser = ShiroUtils.getLoginUser();
        //拷贝属性值
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(sysRoleVo, sysRole);

        assert loginUser != null;
        String username = loginUser.getUsername();
        sysRole.setUpdateBy(username);
        sysRoleMapper.update(sysRole);

        // 删除当前角色的所有权限
        sysRoleMenuMapper.deleteByRoleId(sysRole.getRoleId());
        // 下面开始添加角色权限数据
        saveRoleMenu(sysRoleVo, sysRole);
    }

    /**
     * 保存角色菜单数据
     *
     * @param sysRoleVo
     * @param sysRole
     */
    private void saveRoleMenu(SysRoleVo sysRoleVo, SysRole sysRole) {
        if (!CollectionUtils.isEmpty(sysRoleVo.getAuthIds())) {
            List<SysRoleMenu> roleMenuList = sysRoleVo.getAuthIds().stream().map(id -> {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                // 设置菜单ID和角色ID
                sysRoleMenu.setMenuId(id);
                sysRoleMenu.setRoleId(sysRole.getRoleId());
                return sysRoleMenu;
            }).collect(Collectors.toList());
            // 存库
            sysRoleMenuMapper.saveBatch(roleMenuList);
        }
    }

    /**
     * 删除角色
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        sysRoleMapper.delete(id);
    }

    /**
     * 获取角色
     *
     * @param id
     * @return
     */
    @Override
    public SysRoleVo get(Long id) {
        SysRole sysRole = sysRoleMapper.get(id);
        // 拷贝属性
        SysRoleVo vo = new SysRoleVo();
        BeanUtils.copyProperties(sysRole, vo);
        // 查询这个角色存在的所有权限
        List<SysRoleMenu> roleMenuList = sysRoleMenuMapper.getByRoleId(id);
        // 如果角色权限集合不为空，取出菜单ID集合
        if (!CollectionUtils.isEmpty(roleMenuList)) {
            // 取出权限ID集合
            List<Long> authIds = roleMenuList.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
            vo.setAuthIds(authIds);
        }
        return vo;
    }

    /**
     * 分页查询角色信息
     *
     * @param page
     * @return
     */
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

    /**
     * 获取所有角色信息
     *
     * @return
     */
    @Override
    public List<SysRole> getAll() {
        return sysRoleMapper.getAll();
    }
}
