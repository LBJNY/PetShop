package com.lbj.pochi.service.impl;

import com.lbj.pochi.mapper.SysLogMapper;
import com.lbj.pochi.pojo.SysLog;
import com.lbj.pochi.pojo.dto.SysLogDto;
import com.lbj.pochi.repository.SysLogRepository;
import com.lbj.pochi.service.SysLogService;
import com.lbj.pochi.utils.DateUtils;
import com.lbj.pochi.utils.IdWorker;
import com.lbj.pochi.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogRepository sysLogRepository;
    @Autowired
    private IdWorker idWorker;

    @Override
    public void save(SysLog logger) {
        logger.setLogId(idWorker.nextId());
        logger.setCreatedBy("admin");
        logger.setCreatedDate(DateUtils.newDate());
        sysLogRepository.save(logger);
    }

    @Override
    public Page<SysLog> getByPage(SysLogDto sysLogDto) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public SysLog get(String id) {
        return null;
    }
}