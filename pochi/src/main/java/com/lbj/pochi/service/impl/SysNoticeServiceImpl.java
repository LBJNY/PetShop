package com.lbj.pochi.service.impl;

import com.lbj.pochi.enums.StateEnums;
import com.lbj.pochi.mapper.SysNoticeMapper;
import com.lbj.pochi.pojo.LoginUser;
import com.lbj.pochi.pojo.SysNotice;
import com.lbj.pochi.pojo.vo.SysUserVo;
import com.lbj.pochi.service.SysNoticeService;
import com.lbj.pochi.utils.Page;
import com.lbj.pochi.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公告service类
 */
@Service
public class SysNoticeServiceImpl implements SysNoticeService {
    @Autowired
    private SysNoticeMapper sysNoticeMapper;


    /**
     * 添加
     * @param sysNotice
     */
    @Override
    public void save(SysNotice sysNotice) {
        // 创建人默认值
        LoginUser loginUser = ShiroUtils.getLoginUser();
        sysNotice.setCreatedBy(loginUser.getUsername());
        sysNoticeMapper.save(sysNotice);
    }

    /**
     * 修改
     * @param sysNotice
     */
    @Override
    public void update(SysNotice sysNotice) {
        sysNoticeMapper.update(sysNotice);
    }

    /**
     * 根据Id获取
     * @param id
     * @return
     */
    @Override
    public SysNotice get(Long id) {
        return sysNoticeMapper.get(id);
    }

    /**
     * 根据Id删除
     * @param id
     */
    @Override
    public void delete(Long id) {
        sysNoticeMapper.delete(id);
    }

    /**
     * 启用
     * @param id
     */
    @Override
    public void enable(Long id) {
        SysNotice sysNotice = sysNoticeMapper.get(id);
        sysNotice.setEnabled(StateEnums.ENABLED.getCode());
        sysNoticeMapper.updateEnable(sysNotice);
    }

    /**
     * 禁用
     * @param id
     */
    @Override
    public void disable(Long id) {
        SysNotice sysNotice = sysNoticeMapper.get(id);
        sysNotice.setEnabled(StateEnums.NOT_ENABLE.getCode());
        sysNoticeMapper.updateEnable(sysNotice);
    }

    /**
     * 分页获取
     * @param page
     * @return
     */
    @Override
    public Page<SysNotice> getByPage(Page<SysNotice> page) {
        List<SysNotice> list = sysNoticeMapper.getByPage(page);
        Integer totalCount = sysNoticeMapper.countByPage(page);
        page.setList(list);
        page.setTotalCount(totalCount);
        return page;
    }

    @Override
    public List<SysNotice> getNoticeList() {
        return sysNoticeMapper.getNoticeList();
    }
}
