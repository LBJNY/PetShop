package com.lbj.pochi.service;

import com.lbj.pochi.pojo.SysHelp;
import com.lbj.pochi.utils.Page;

/**
 * 帮助service
 */
public interface SysHelpService {
    /**
     * 增加
     * @param sysHelp
     */
    void save(SysHelp sysHelp);

    /**
     * 修改
     * @param sysHelp
     */
    void update(SysHelp sysHelp);

    /**
     * 根据ID获取
     * @param id
     * @return
     */
    SysHelp get(Long id);

    /**
     * 根据Id删除
     * @param id
     */
    void delete(Long id);

    /**
     * 分页获取
     * @param page
     * @return
     */
    Page<SysHelp> getByPage(Page<SysHelp> page);
}
