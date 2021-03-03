package com.lbj.pochi.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 轮播图实体
 *
 */
@Data
public class SysBanner implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 图片路径
     */
    private String pic;

    /**
     * 状态，1启用0弃用
     */
    private Integer status;

    /**
     * 点击数
     */
    private Integer clickCount;

    /**
     * 下单数
     */
    private Integer orderCount;

    /**
     * 链接地址
     */
    private String url;

    /**
     * 备注
     */
    private String note;
    /**
     * 排序值
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 修改时间
     */
    private String updateTime;
    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 逻辑删除，1是0否
     */
    private Integer deleted;

}