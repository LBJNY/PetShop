package com.lbj.pochi.mapper;


import com.lbj.pochi.pojo.SysBanner;
import com.lbj.pochi.pojo.SysHelp;
import com.lbj.pochi.utils.Page;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 帮助中心(sys_help)数据Mapper
 *
*/
@Component
public interface SysHelpMapper {

    /**
     * 添加
     * @param sysBanner
     */
    void save(SysHelp sysBanner);

    /**
     * 修改
     * @param sysBanner
     */
    void update(SysHelp sysBanner);

    /**
     * 根据ID获取
     * @param id
     * @return
     */
    SysHelp get(Long id);

    /**
     * 根据ID删除
     * @param id
     */
    void delete(Long id);

    /**
     * 分页获取
     * @param page
     * @return
     */
    List<SysHelp> getByPage(Page<SysHelp> page);

    /**
     * 根据条件获取总条数
     * @param page
     * @return
     */
    int countByPage(Page<SysHelp> page);
}
