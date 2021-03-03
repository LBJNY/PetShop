package com.lbj.pochi.service.impl;

import com.lbj.pochi.mapper.SysLogMapper;
import com.lbj.pochi.pojo.SysLog;
import com.lbj.pochi.pojo.dto.SysLogDto;
import com.lbj.pochi.repository.SysLogRepository;
import com.lbj.pochi.service.SysLogService;
import com.lbj.pochi.utils.DateUtils;
import com.lbj.pochi.utils.IdWorker;
import com.lbj.pochi.utils.Page;
import com.lbj.pochi.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 *
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogRepository sysLogRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private IdWorker idWorker;

    /**
     * 添加
     *
     * @param logger
     */
    @Override
    public void save(SysLog logger) {
        logger.setLogId(idWorker.nextId());
        logger.setCreatedBy("admin");
        logger.setCreatedDate(DateUtils.newDate());
        sysLogRepository.save(logger);
    }

    /**
     * 分页查询
     *
     * @param sysLogDto
     * @return
     */
    @Override
    public Page<SysLog> getByPage(SysLogDto sysLogDto) {
        //构造查询对象
        Query query = new Query();
        //排除列表页不需要的大字段
        query.fields().exclude("log_params");
        query.fields().exclude("log_result");
        query.fields().exclude("log_message");
        //设置查询参数
        //路径
        if (StringUtils.isNotEmpty(sysLogDto.getLogUrl())) {
            query.addCriteria(Criteria.where("log_url").regex(sysLogDto.getLogUrl()));
        }
        //状态值  logstatus
        if (sysLogDto.getLogStatus() != null) {
            query.addCriteria(Criteria.where("log_status").is(sysLogDto.getLogStatus()));
        }
        //根据controller查询
        if (StringUtils.isNotBlank(sysLogDto.getLogController())) {
            query.addCriteria(Criteria.where("log_controller").regex(sysLogDto.getLogController()));
        }
        //根据时间区间查询
        if (!CollectionUtils.isEmpty(sysLogDto.getCreatedDate())) {
            query.addCriteria(Criteria.where("created_date").gt(sysLogDto.getCreatedDate().get(0))
                    .andOperator(Criteria.where("created_date").lt(sysLogDto.getCreatedDate().get(1))));
        }
        //处理分页条件
        Integer pageNumber = sysLogDto.getPageNumber();
        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
            sysLogDto.setPageNumber(pageNumber);
        }
        Integer pageSize = sysLogDto.getPageSize();
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
            sysLogDto.setPageSize(pageSize);
        }
        long count = mongoTemplate.count(query, SysLog.class);
        //构造分页
        //  1.跳过多少条
        query.skip((pageNumber - 1) * pageSize);
        //  2.取出多少条
        query.limit(pageSize);
        //  3.构造排序对象,默认根据创建时间倒序排列,并根据响应时间倒序排列
        Sort.Order dateOrder = new Sort.Order(Sort.Direction.DESC, "created_date");
        Sort.Order timeOrder = new Sort.Order(Sort.Direction.DESC, "log_time");
        //  4.设置排序对象
        query.with(Sort.by(dateOrder, timeOrder));
        //查询
        List<SysLog> logList = mongoTemplate.find(query, SysLog.class);
        //创建page对象,封装返回结果
        Page<SysLog> page = new Page<>();
        page.setList(logList);
        page.setTotalCount((int) count);
        page.setTotalPage((int) Math.ceil(count * 1.0 / pageSize));
        return page;
    }

    /**
     * 根据ID删除
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        sysLogRepository.deleteById(id);
    }

    /**
     * 根据ID获取
     *
     * @param id
     * @return
     */
    @Override
    public SysLog get(Long id) {
        return sysLogRepository.findById(id).get();
    }
}