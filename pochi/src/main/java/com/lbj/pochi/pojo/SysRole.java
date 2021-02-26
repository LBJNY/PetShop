package com.lbj.pochi.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 角色信息表(sys_role)实体类
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SysRole  implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
	private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 显示顺序 排序值，越小越靠前
     */
    private Integer roleSort;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
	private Date updateTime;
    /**
     * 删除标志 （0代表存在 1代表删除）
     */
    private Integer deleted;

}