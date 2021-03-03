package com.lbj.pochi.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 帮助中心(sys_help)实体类
 *
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class SysHelp implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	private Long id;
    /**
     * 帮助内容
     */
    private String content;
    /**
     * 标题
     */
    private String title;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
	private Date updateTime;
    /**
     * 是否删除，1是0否
     */
    private Integer deleted;

}