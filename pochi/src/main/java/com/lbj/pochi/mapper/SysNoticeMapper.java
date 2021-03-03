package com.lbj.pochi.mapper;


import com.lbj.pochi.pojo.SysNotice;
import com.lbj.pochi.utils.Page;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 公告(sys_notice)数据Mapper
 *
*/
@Component
public interface SysNoticeMapper {

    /**
     * 添加公告
     * @param sysNotice
     */
    void save(SysNotice sysNotice);

    /**
     * 修改公告
     * @param sysNotice
     */
    void update(SysNotice sysNotice);

    /**
     * 根据Id获取公告
     * @param id
     * @return
     */
    SysNotice get(Long id);

    /**
     * 删除公告
     * @param id
     */
    void delete(Long id);

    /**
     * 更新公告启用状态
     * @param sysNotice
     */
    void updateEnable(SysNotice sysNotice);

    /**
     * 分页获取公告列表
     * @param page
     * @return
     */
    List<SysNotice> getByPage(Page<SysNotice> page);

    /**
     * 根据条件获取总条数
     * @param page
     * @return
     */
    Integer countByPage(Page<SysNotice> page);
}
