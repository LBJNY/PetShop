package com.lbj.pochi.service.impl;

import com.lbj.pochi.constant.CoreConstant;
import com.lbj.pochi.enums.ResultEnum;
import com.lbj.pochi.exception.PochiException;
import com.lbj.pochi.mapper.SysMenuMapper;
import com.lbj.pochi.pojo.SysMenu;
import com.lbj.pochi.pojo.SysUser;
import com.lbj.pochi.pojo.vo.SysMenuVo;
import com.lbj.pochi.service.SysMenuService;
import com.lbj.pochi.utils.Page;
import com.lbj.pochi.utils.Result;
import com.lbj.pochi.utils.ShiroUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单service实现类
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    /**
     * 分页获取菜单
     *
     * @param page
     * @return
     */
    @Override
    public Page<SysMenu> getByPage(Page<SysMenu> page) {
        Integer pageNumber = page.getCurrentPage();
        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
            page.setCurrentPage(pageNumber);
        }
        List<SysMenu> menuList = sysMenuMapper.getByPage(page);
        Integer pageCount = sysMenuMapper.countByPage(page);
        page.setList(menuList);
        page.setTotalCount(pageCount);
        return page;
    }

    /**
     * 根据菜单id获取菜单信息
     *
     * @param id
     * @return
     */
    @Override
    public SysMenu get(Long id) {
        return sysMenuMapper.getById(id);
    }

    /**
     * 根据id删除菜单
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        sysMenuMapper.deleteById(id);
    }

    /**
     * 更新菜单
     *
     * @param sysMenu
     */
    @Override
    public void update(SysMenu sysMenu) {
        //初始化默认值
        if (sysMenu.getParentId() == null) {
            sysMenu.setParentId(CoreConstant.DEFAULT_PARENT_ID);
        }
        //查看当前父节点下是否有相同节点
        SysMenu menu = sysMenuMapper.getByParentIdAndName(sysMenu);
        if (menu != null && !menu.getMenuId().equals(sysMenu.getMenuId())) {
            // 如果菜单存在，并且编号不相同，就说明存在了同名的菜单
            throw new PochiException(ResultEnum.MENU_EXISTS);
        }
        // 设置修改人
        SysUser loginUser = ShiroUtils.getLoginUser();
        sysMenu.setUpdateBy(loginUser.getUsername());
        sysMenuMapper.update(sysMenu);
    }

    /**
     * 保存菜单
     *
     * @param sysMenu
     */
    @Override
    public void save(SysMenu sysMenu) {
        //初始化默认值
        if (sysMenu.getParentId() == null) {
            sysMenu.setParentId(CoreConstant.DEFAULT_PARENT_ID);
        }
        //查看当前父节点下是否有相同节点
        SysMenu menu = sysMenuMapper.getByParentIdAndName(sysMenu);
        // 如果存在，说明菜单已存在
        if (menu != null) {
            throw new PochiException(ResultEnum.MENU_EXISTS);
        }
        // 菜单不存在，入表
        SysUser loginUser = ShiroUtils.getLoginUser();
        sysMenu.setCreateBy(loginUser.getUsername());
        sysMenu.setUpdateBy(loginUser.getUsername());
        // 添加
        sysMenuMapper.save(sysMenu);
    }

    /**
     * 获取树菜单
     */
    @Override
    public List<SysMenuVo> getTreeList() {
        //获取所有的菜单
        List<SysMenu> menuList = sysMenuMapper.getAll();
        //过滤出所有的顶级菜单
        return menuList.stream()
                //只要parentId=0就是顶级菜单
                .filter(e -> e.getParentId().equals(CoreConstant.DEFAULT_PARENT_ID))
                //将顶级菜单转化为SysMenu视图类
                .map(e -> {
                    SysMenuVo sysMenuVo = new SysMenuVo();
                    BeanUtils.copyProperties(e, sysMenuVo);
                    return sysMenuVo;
                })
                //根据顶级菜单分别获取所有的children 这里使用的是递归
                .map(e -> {
                    e.setChildren(getChildren(e, menuList));
                    //处理完之后给与子菜单一个null
                    if (CollectionUtils.isEmpty(e.getChildren())) {
                        e.setChildren(null);
                    }
                    return e;
                }).collect(Collectors.toList());

    }

    /**
     * 根据角色ID获取当前角色的菜单权限
     *
     * @param roleId
     * @return
     */
    @Override
    public List<Long> getRoleSelectMenu(Long roleId) {
        // 先查出来
        List<SysMenu> menuList = sysMenuMapper.getRoleSelectMenu(roleId);
        return menuList.stream().map(SysMenu::getMenuId).collect(Collectors.toList());
    }

    /**
     * 递归构造树形菜单
     *
     * @param sysMenu
     * @param menuList
     * @return
     */
    private List<SysMenuVo> getChildren(SysMenuVo sysMenu, List<SysMenu> menuList) {
        return menuList.stream()
                //只要parentId=sysMenu.getMenuId()就是当前菜单的子菜单
                .filter(e -> e.getParentId().equals(sysMenu.getMenuId()))
                //将顶级菜单转化为SysMenu视图类
                .map(e -> {
                    SysMenuVo sysMenuVo = new SysMenuVo();
                    BeanUtils.copyProperties(e, sysMenuVo);
                    return sysMenuVo;
                })
                //根据顶级菜单分别获取所有的children 这里使用的是递归
                .map(e -> {
                    e.setChildren(getChildren(e, menuList));
                    //处理完之后给与子菜单一个null
                    if (CollectionUtils.isEmpty(e.getChildren())) {
                        e.setChildren(null);
                    }
                    return e;
                }).collect(Collectors.toList());
    }
}
