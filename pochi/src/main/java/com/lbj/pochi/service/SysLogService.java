package com.lbj.pochi.service;

import com.lbj.pochi.pojo.SysLog;
import com.lbj.pochi.pojo.dto.SysLogDto;
import com.lbj.pochi.utils.Page;

/**
 */
public interface SysLogService {

    /**
     * 保存日志
     * @param logger
     */
    void save(SysLog logger);

    /**
     * 分页查询日志
     * @param sysLogDto
     * @return
     */
    Page<SysLog> getByPage(SysLogDto sysLogDto);

    /**
     * 根据id删除日志
     * @param id
     */
    void delete(String id);

    /**
     * 根据id获取日志
     * @param id
     * @return
     */
    SysLog get(String id);
}