package com.lbj.pochi.service;

import com.lbj.pochi.pojo.SysLog;

/**
 */
public interface SysLogService {

    /**
     * 保存日志
     * @param logger
     */
    void save(SysLog logger);
}