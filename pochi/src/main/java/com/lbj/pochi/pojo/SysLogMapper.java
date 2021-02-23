package com.lbj.pochi.pojo;

import com.lbj.pochi.pojo.SysLog;
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
