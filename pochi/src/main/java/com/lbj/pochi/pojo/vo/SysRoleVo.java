package com.lbj.pochi.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysRoleVo implements Serializable {

    /**
     * 角色ID，自增
     */
    private Long roleId;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 排序值，越小越靠前
     */
    private Integer roleSort;

    /**
     * 菜单ID集合
     */
    private List<Long> authIds;

}
