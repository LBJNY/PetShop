package com.lbj.pochi.service.impl;

import com.lbj.pochi.enums.StateEnums;
import com.lbj.pochi.mapper.SysBannerMapper;
import com.lbj.pochi.pojo.SysBanner;
import com.lbj.pochi.pojo.SysUser;
import com.lbj.pochi.pojo.vo.SysUserVo;
import com.lbj.pochi.service.SysBannerService;
import com.lbj.pochi.utils.Page;
import com.lbj.pochi.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 */
@Service
public class SysBannerServiceImpl implements SysBannerService {
    @Autowired
    private SysBannerMapper sysBannerMapper;

    @Override
    public void save(SysBanner sysBanner) {
        SysUserVo loginUser = ShiroUtils.getLoginUser();
        sysBanner.setCreateBy(loginUser.getUsername());
        sysBanner.setUpdateBy(loginUser.getUsername());
        sysBannerMapper.save(sysBanner);
    }

    @Override
    public void update(SysBanner sysBanner) {
        SysUserVo loginUser = ShiroUtils.getLoginUser();
        sysBanner.setUpdateBy(loginUser.getUsername());
        sysBannerMapper.update(sysBanner);
    }

    @Override
    public SysBanner get(Long id) {
        return sysBannerMapper.get(id);
    }

    @Override
    public void delete(Long id) {
        sysBannerMapper.delete(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void enable(Long id) {
        SysBanner banner = sysBannerMapper.get(id);
        banner.setStatus(StateEnums.ENABLED.getCode());
        sysBannerMapper.updateStatus(banner);
    }

    @Override
    public void disable(Long id) {
        SysBanner banner = sysBannerMapper.get(id);
        banner.setStatus(StateEnums.NOT_ENABLE.getCode());
        sysBannerMapper.updateStatus(banner);
    }

    @Override
    public Page<SysBanner> getByPage(Page<SysBanner> page) {
        List<SysBanner> list = sysBannerMapper.getByPage(page);
        int totalCount = sysBannerMapper.countByPage(page);
        page.setList(list);
        page.setTotalCount(totalCount);
        return page;
    }


}