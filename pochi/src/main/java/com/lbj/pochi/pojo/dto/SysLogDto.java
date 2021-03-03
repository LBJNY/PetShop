package com.lbj.pochi.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 这里可以使用page
 * 但是这个查询是mongoDb
 * 无需多维度查询
 * 使代码可读性更强
 * 这里使用实体类
 */
@Data
public class SysLogDto implements Serializable {

    /**
     * 日志编号
     */
    private Long logId;

    /**
     * 请求路径
     */
    private String logUrl;

    /**
     * 状态，1正常，0异常
     */
    private Integer logStatus;

    /**
     * controller
     */
    private String logController;

    /**
     * 请求方式，GET、POST、PUT、DELETE
     */
    private String logMethod;

    /**
     * 响应时间（毫秒）
     */
    private Long logTime;

    /**
     * 请求ip
     */
    private String logIp;

    /**
     * 创建时间
     */
    private List<String> createdDate;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 页面大小
     */
    private Integer pageSize;
    /**
     * 页数
     */
    private Integer pageNumber;

}