package com.lbj.pochi.service.impl;

import com.lbj.pochi.mapper.SysLogMapper;
import com.lbj.pochi.pojo.SysLog;
import com.lbj.pochi.service.SysLogService;
import com.lbj.pochi.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;
    @Autowired
    private IdWorker idWorker;

    @Override
    public void save(SysLog logger) {
        logger.setLogId(idWorker.nextId());
        logger.setCreatedBy("admin");
        sysLogMapper.save(logger);
    }
}