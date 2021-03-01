package com.lbj.pochi.pojo;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 角色和菜单关联表(sys_role_menu)实体类
 *
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class SysRoleMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	private Long id;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 菜单ID
     */
    private Long menuId;

}