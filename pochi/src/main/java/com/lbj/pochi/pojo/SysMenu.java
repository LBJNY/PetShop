package com.lbj.pochi.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 菜单权限表(sys_menu)实体类
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SysMenu  implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
	private Long menuId;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 父菜单ID
     */
    private Long parentId;
    /**
     * 显示顺序
     */
    private Integer orderNum;
    /**
     * 路由地址
     */
    private String routerPath;
    /**
     * 组件路径
     */
    private String componentUrl;
    /**
     * 菜单类型（1目录 2菜单 3权限）
     */
    private Integer menuType;
    /**
     * 菜单状态（1显示 0隐藏）
     */
	private Integer visible;
    /**
     * 菜单状态（1正常 0停用）
     */
    private Integer status;
    /**
     * 权限标识
     */
    private String permission;
    /**
     * 菜单图标
     */
    private String icon;
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
     * 是否删除，1是0否
     */
    private Integer deleted;

}