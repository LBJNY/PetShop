package com.lbj.pochi.service;

import com.lbj.pochi.pojo.SysBanner;
import com.lbj.pochi.utils.Page;
import org.springframework.boot.Banner;

import java.util.List;

/**
 */
public interface SysBannerService {

    /**
     * 添加
     *
     * @param sysBanner
     */
    void save(SysBanner sysBanner);

    /**
     * 修改
     *
     * @param sysBanner
     */
    void update(SysBanner sysBanner);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    SysBanner get(Long id);

    /**
     * 根据id删除
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 启用
     *
     * @param id
     */
    void enable(Long id);

    /**
     * 弃用
     *
     * @param id
     */
    void disable(Long id);

    /**
     * 分页查询
     * @param page
     * @return
     */
    Page<SysBanner> getByPage(Page<SysBanner> page);

    /**
     * 首页获取轮播图
     * @return
     */
    List<Banner> getBannerList();
}