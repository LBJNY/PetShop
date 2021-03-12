package com.lbj.pochi.service.impl;

import com.lbj.pochi.enums.StateEnums;
import com.lbj.pochi.mapper.SysHelpMapper;
import com.lbj.pochi.pojo.LoginUser;
import com.lbj.pochi.pojo.SysBanner;
import com.lbj.pochi.pojo.SysHelp;
import com.lbj.pochi.pojo.vo.SysUserVo;
import com.lbj.pochi.service.SysHelpService;
import com.lbj.pochi.utils.Page;
import com.lbj.pochi.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 帮助中心service实现类
 */
@Service
public class SysHelpServiceImpl implements SysHelpService {

    @Autowired
    private SysHelpMapper sysHelpMapper;

    /**
     * 保存
     * @param sysHelp
     */
    @Override
    public void save(SysHelp sysHelp) {
        LoginUser loginUser = ShiroUtils.getLoginUser();
        sysHelp.setCreatedBy(loginUser.getUsername());
        sysHelp.setUpdateBy(loginUser.getUsername());
        sysHelpMapper.save(sysHelp);
    }

    /**
     * 修改
     * @param sysHelp
     */
    @Override
    public void update(SysHelp sysHelp) {
        LoginUser loginUser = ShiroUtils.getLoginUser();
        sysHelp.setUpdateBy(loginUser.getUsername());
        sysHelpMapper.update(sysHelp);
    }

    /**
     * 根据ID获取
     * @param id
     * @return
     */
    @Override
    public SysHelp get(Long id) {
        return sysHelpMapper.get(id);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Long id) {
        sysHelpMapper.delete(id);
    }


    /**
     * 分页获取
     * @param page
     * @return
     */
    @Override
    public Page<SysHelp> getByPage(Page<SysHelp> page) {
        List<SysHelp> list = sysHelpMapper.getByPage(page);
        int totalCount = sysHelpMapper.countByPage(page);
        page.setList(list);
        page.setTotalCount(totalCount);
        return page;
    }
}
