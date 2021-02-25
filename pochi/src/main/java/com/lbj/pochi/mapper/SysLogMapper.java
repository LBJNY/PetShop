package com.lbj.pochi.mapper;

import com.lbj.pochi.pojo.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public interface SysLogMapper {

    /**
     * 保存日志
     * @param logger
     */
    void save(SysLog logger);
}
