package com.lbj.pochi.repository;

import com.lbj.pochi.pojo.SysLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SysLogRepository extends MongoRepository<SysLog, Long> {
}