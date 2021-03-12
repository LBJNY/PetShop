package com.lbj.pochi.mapper;

import com.lbj.pochi.pojo.ShopUserStatistic;
import org.springframework.stereotype.Component;

/**
 * 用户统计对象Mapper
 */
@Component
public interface ShopUserStatisticMapper {
    /**
     * 添加用户统计对象
     * @param shopUserStatistic
     */
    void save(ShopUserStatistic shopUserStatistic);
}
