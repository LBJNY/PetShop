package com.lbj.pochi.service.impl;

import com.lbj.pochi.constant.CoreConstant;
import com.lbj.pochi.enums.ResultEnum;
import com.lbj.pochi.enums.StateEnums;
import com.lbj.pochi.exception.PochiException;
import com.lbj.pochi.mapper.SysMenuMapper;
import com.lbj.pochi.pojo.SysMenu;
import com.lbj.pochi.pojo.SysUser;
import com.lbj.pochi.pojo.vo.RouterVo;
import com.lbj.pochi.pojo.vo.SysMenuVo;
import com.lbj.pochi.pojo.vo.SysUserVo;
import com.lbj.pochi.service.SysMenuService;
import com.lbj.pochi.utils.Page;
import com.lbj.pochi.utils.Result;
import com.lbj.pochi.utils.ShiroUtils;
import com.lbj.pochi.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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
        SysUserVo loginUser = ShiroUtils.getLoginUser();
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
        SysUserVo loginUser = ShiroUtils.getLoginUser();
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
     * 构建路由
     *
     * @return
     */
    public List<RouterVo> getRouters() {
        // 1. 查询出当前登录用户所拥有的启用中的所有菜单（权限不要查）
        SysUserVo loginUser = ShiroUtils.getLoginUser();
        List<SysMenu> menuList = sysMenuMapper.getEnableMenuByUserId(loginUser.getId());
        // 2. 构造成树形结构，也就是 `SysMenuVo`
        List<SysMenuVo> menuVoList = menuList.stream().filter(e -> e.getParentId().equals(CoreConstant.DEFAULT_PARENT_ID))
                .map(e -> {
                    // 构造SysMenuVo
                    SysMenuVo sysMenuVo = new SysMenuVo();
                    // 拷贝属性
                    BeanUtils.copyProperties(e, sysMenuVo);
                    return sysMenuVo;
                })
                .map(e -> {
                    // 构造树形结构
                    e.setChildren(getChildren(e, menuList));
                    if (e.getChildren() == null) {
                        // 设置为空集合
                        e.setChildren(new ArrayList<>(0));
                    }
                    return e;
                }).collect(Collectors.toList());
        // 3. 构造成路由树
        return buildMenus(menuVoList);
    }

    /**
     * 递归构建菜单
     *
     * @param menuVoList
     * @return
     */
    private List<RouterVo> buildMenus(List<SysMenuVo> menuVoList) {
        // 1. 遍历上面的菜单树
        return menuVoList.stream().map(e -> {
            // 2. 创建 `RouterVo` 对象，将菜单数据转换成路由视图对象
            RouterVo router = new RouterVo();
            router.setHidden(CoreConstant.HIDDEN_STATE.equals(e.getVisible()));
            router.setName(e.getMenuName());
            //    2.1. 构造跳转路径，用多个 '/' 拼接
            router.setPath(getRouterPath(e));
            //    2.2. 构造组件路径，用多个 '/' 拼接
            router.setComponent(getComponent(e));
            //    2.3. 构造meta数据
            router.setMeta(new RouterVo.MetaVo(e.getMenuName(), e.getIcon()));
            // 3. 如果当前是目录，并且子菜单不为空，就递归构造子菜单
            List<SysMenuVo> children = e.getChildren();
            if (!CollectionUtils.isEmpty(children) && StateEnums.FOLDER.getCode().equals(e.getMenuType())) {
                router.setAlwaysShow(true);
                // 设置redirect
                router.setRedirect(CoreConstant.NO_REDIRECT);
                // 递归构造菜单
                router.setChildren(buildMenus(children));
            } else {
                // 子菜单为空的情况下，children不能给null，否则会报错
                router.setChildren(new ArrayList<>(0));
            }
            return router;
        }).collect(Collectors.toList());
    }

    /**
     * 获取组件路径
     * @param e
     * @return
     */
    private String getComponent(SysMenuVo e) {
        String component = CoreConstant.DEFAULT_COMPONENT;
        if(StringUtils.isNotEmpty(e.getComponentUrl())) {
            component = e.getComponentUrl();
        }
        return component;
    }

    /**
     * 获取路由路径
     * @param e
     * @return
     */
    private String getRouterPath(SysMenuVo e) {
        if(StateEnums.FOLDER.getCode().equals(e.getMenuType())) {
            return CoreConstant.URL_SPLITE + e.getRouterPath();
        }else {
            return e.getRouterPath();
        }
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
