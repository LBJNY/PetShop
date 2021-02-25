package com.lbj.pochi.service.impl;

import com.lbj.pochi.enums.StateEnums;
import com.lbj.pochi.mapper.SysUserMapper;
import com.lbj.pochi.pojo.SysUser;
import com.lbj.pochi.service.SysUserService;
import com.lbj.pochi.utils.IdWorker;
import com.lbj.pochi.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private IdWorker idWorker;

    @Override
    public void updateLoginTime(String username) {
        sysUserMapper.updateLoginTime(username);
    }

    @Override
    public SysUser getByUserName(String username) {
        return sysUserMapper.getByUserName(username);
    }

    @Override
    public void save(SysUser sysUser) {
        //设置用户Id
        sysUser.setId(idWorker.nextId());
        sysUserMapper.save(sysUser);
    }

    @Override
    public void update(SysUser sysUser) {
        sysUserMapper.update(sysUser);
    }

    @Override
    public void delete(Long id) {
        sysUserMapper.delete(id);
    }

    @Override
    public void enable(Long id) {
        SysUser sysUser = sysUserMapper.getById(id);
        sysUser.setStatus(StateEnums.ENABLED.getCode());
        sysUserMapper.updateStatus(sysUser);
    }

    @Override
    public void disable(Long id) {
        SysUser sysUser = sysUserMapper.getById(id);
        sysUser.setStatus(StateEnums.NOT_ENABLE.getCode());
        sysUserMapper.updateStatus(sysUser);
    }

    @Override
    public SysUser get(Long id) {
        return sysUserMapper.getById(id);
    }

    @Override
    public Page<SysUser> getByPage(Page<SysUser> page) {
        Integer pageNumber = page.getCurrentPage();
        if (pageNumber == null || pageNumber < 1) {
            pageNumber=1;
            page.setCurrentPage(pageNumber);
        }
        List<SysUser> userList=sysUserMapper.getByPage(page);
        Integer pageCount=sysUserMapper.countByPage(page);
        page.setList(userList);
        page.setTotalCount(pageCount);
        return page;
    }
}
