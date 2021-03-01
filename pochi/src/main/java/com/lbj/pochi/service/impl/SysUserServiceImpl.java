package com.lbj.pochi.service.impl;

import com.lbj.pochi.enums.StateEnums;
import com.lbj.pochi.mapper.SysRoleMapper;
import com.lbj.pochi.mapper.SysUserMapper;
import com.lbj.pochi.mapper.SysUserRoleMapper;
import com.lbj.pochi.pojo.SysRole;
import com.lbj.pochi.pojo.SysUser;
import com.lbj.pochi.pojo.SysUserRole;
import com.lbj.pochi.pojo.vo.SysUserVo;
import com.lbj.pochi.service.SysUserService;
import com.lbj.pochi.utils.IdWorker;
import com.lbj.pochi.utils.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
/**
 * 用户service实现类
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private IdWorker idWorker;

    /**
     * 根据用户名更新登陆时间
     * @param username 用户名
     */
    @Override
    public void updateLoginTime(String username) {
        sysUserMapper.updateLoginTime(username);
    }

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    @Override
    public SysUser getByUserName(String username) {
        return sysUserMapper.getByUserName(username);
    }

    /**
     * 添加用户
     * @param sysUserVo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysUserVo sysUserVo) {
        //拷贝属性
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserVo, sysUser);
        //设置用户Id
        long id = idWorker.nextId();
        sysUser.setId(id);
        sysUserMapper.save(sysUser);
        if (sysUserVo.getSysRole() != null && sysUserVo.getSysRole().getRoleId() != null) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(id);
            sysUserRole.setRoleId(sysUserVo.getSysRole().getRoleId());
            sysUserRoleMapper.save(sysUserRole);
        }
    }

    /**
     * 更新用户信息
     * @param sysUserVo
     */
    @Override
    public void update(SysUserVo sysUserVo) {
        //拷贝数据
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserVo, sysUser);
        sysUserMapper.update(sysUser);
        //不管前端有没有选择角色,都把旧的角色信息删掉,如果选择了,就在添加新的角色信息
        sysUserRoleMapper.deleteByUserId(sysUserVo.getId());
        if (sysUserVo.getSysRole() != null && sysUserVo.getSysRole().getRoleId() != null) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(sysUserVo.getId());
            sysUserRole.setRoleId(sysUserVo.getSysRole().getRoleId());
            sysUserRoleMapper.save(sysUserRole);
        }
    }

    /**
     * 根据id删除用户信息
     * @param id
     */
    @Override
    public void delete(Long id) {
        sysUserMapper.delete(id);
    }

    /**
     * 启用用户
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void enable(Long id) {
        SysUser sysUser = sysUserMapper.getById(id);
        sysUser.setStatus(StateEnums.ENABLED.getCode());
        sysUserMapper.updateStatus(sysUser);
    }

    /**
     * 禁用用户
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void disable(Long id) {
        SysUser sysUser = sysUserMapper.getById(id);
        sysUser.setStatus(StateEnums.NOT_ENABLE.getCode());
        sysUserMapper.updateStatus(sysUser);
    }

    /**
     * 根据用户id获取用户
     * @param id
     * @return
     */
    @Override
    public SysUserVo get(Long id) {
        SysUser sysUser = sysUserMapper.getById(id);
        //拷贝信息
        SysUserVo sysUserVo = new SysUserVo();
        BeanUtils.copyProperties(sysUser, sysUserVo);
        //查询角色信息
        List<SysUserRole> sysUserRoleList = sysUserRoleMapper.getByUserId(sysUser.getId());
        if (!CollectionUtils.isEmpty(sysUserRoleList)) {//看当前用户是否有角色信息
            //说明有角色信息  取出角色ID
            List<Long> roleIds=sysUserRoleList.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
            //根据角色id查询所有角色信息
            List<SysRole> roleList=sysRoleMapper.getByIds(roleIds);
            if (!CollectionUtils.isEmpty(roleList)) {
                sysUserVo.setSysRole(roleList.get(0));
            }
        }else {
            sysUserVo.setSysRole(new SysRole());
        }
        return sysUserVo;
    }

    /**
     * 分页查询用户信息
     * @param page
     * @return
     */
    @Override
    public Page<SysUser> getByPage(Page<SysUser> page) {
        Integer pageNumber = page.getCurrentPage();
        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
            page.setCurrentPage(pageNumber);
        }
        List<SysUser> userList = sysUserMapper.getByPage(page);
        Integer pageCount = sysUserMapper.countByPage(page);
        page.setList(userList);
        page.setTotalCount(pageCount);
        return page;
    }
}
