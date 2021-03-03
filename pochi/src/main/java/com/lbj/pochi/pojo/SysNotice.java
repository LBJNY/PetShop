package com.lbj.pochi.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 公告(sys_notice)实体类
 *
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class SysNotice implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * noticeId
     */
    private Long noticeId;
    /**
     * 公告标题
     */
    private String noticeTitle;
    /**
     * 公告内容
     */
    private String noticeContent;
    /**
     * 排序值
     */
    private Integer sorted;
    /**
     * createdBy
     */
    private String createdBy;
    /**
     * createdTime
     */
    private Date createdTime;
    /**
     * 是否删除，1是0否
     */
    private Integer deleted;
    /**
     * 是否启用，1是0否
     */
    private Integer enabled;

}