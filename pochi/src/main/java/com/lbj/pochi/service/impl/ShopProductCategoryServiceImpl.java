package com.lbj.pochi.service.impl;

import com.lbj.pochi.constant.CoreConstant;
import com.lbj.pochi.enums.ResultEnum;
import com.lbj.pochi.enums.StateEnums;
import com.lbj.pochi.exception.PochiException;
import com.lbj.pochi.mapper.ShopProductCategoryMapper;
import com.lbj.pochi.pojo.ShopProductCategory;
import com.lbj.pochi.pojo.SysBanner;
import com.lbj.pochi.pojo.SysMenu;
import com.lbj.pochi.pojo.vo.ShopProductCategoryVo;
import com.lbj.pochi.pojo.vo.SysMenuVo;
import com.lbj.pochi.service.ShopProductCategoryService;
import com.lbj.pochi.utils.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品分类serviceImpl
 */
@Service
public class ShopProductCategoryServiceImpl implements ShopProductCategoryService {
    @Autowired
    private ShopProductCategoryMapper shopProductCategoryMapper;

    /**
     * 添加商品分类
     *
     * @param shopProductCategory
     */
    @Override
    public void save(ShopProductCategory shopProductCategory) {
        //初始化默认值
        if (shopProductCategory.getParentId() == null) {
            shopProductCategory.setParentId(CoreConstant.DEFAULT_PARENT_ID);
        }
        //查看当前父节点下是否有相同节点
        ShopProductCategory category = shopProductCategoryMapper.getByParentIdAndName(shopProductCategory);
        // 如果存在，说明菜单已存在
        if (category != null) {
            throw new PochiException(ResultEnum.CATEGORY_EXISTS);
        }
        shopProductCategoryMapper.save(shopProductCategory);
    }

    /**
     * 修改商品
     *
     * @param shopProductCategory
     */
    @Override
    public void update(ShopProductCategory shopProductCategory) {
        //初始化默认值
        if (shopProductCategory.getParentId() == null) {
            shopProductCategory.setParentId(CoreConstant.DEFAULT_PARENT_ID);
        }
        //查看当前父节点下是否有相同节点
        ShopProductCategory category = shopProductCategoryMapper.getByParentIdAndName(shopProductCategory);
        if (category != null && !category.getId().equals(shopProductCategory.getId())) {
            // 如果菜单存在，并且编号不相同，就说明存在了同名的菜单
            throw new PochiException(ResultEnum.CATEGORY_EXISTS);
        }
        shopProductCategoryMapper.update(shopProductCategory);
    }

    /**
     * 根据ID获取
     *
     * @param id
     * @return
     */
    @Override
    public ShopProductCategory get(Long id) {
        return shopProductCategoryMapper.getById(id);
    }

    /**
     * 根据ID删除
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        shopProductCategoryMapper.deleteById(id);
    }

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    @Override
    public Page<ShopProductCategory> getByPage(Page<ShopProductCategory> page) {
        List<ShopProductCategory> list = shopProductCategoryMapper.getByPage(page);
        int totalCount = shopProductCategoryMapper.countByPage(page);
        page.setList(list);
        page.setTotalCount(totalCount);
        return page;
    }

    /**
     * 树形列表查询
     *
     * @return
     */
    @Override
    public List<ShopProductCategoryVo> getTree() {
        /**
         * 拿出所有的分类
         */
        List<ShopProductCategory> categoryList = shopProductCategoryMapper.getAll();
        return buildTree(categoryList);
    }

    /**
     * 递归构建 子节点
     *
     * @param categoryVo
     * @param categoryList
     * @return
     */
    private List<ShopProductCategoryVo> getChildren(ShopProductCategoryVo categoryVo, List<ShopProductCategory> categoryList) {
        return categoryList.stream()
                //只要parentId=getId()就是当前菜单的子菜单
                .filter(e -> e.getParentId().equals(categoryVo.getId()))
                //将顶级菜单转化为SysMenu视图类
                .map(e -> {
                    ShopProductCategoryVo shopProductCategoryVo = new ShopProductCategoryVo();
                    BeanUtils.copyProperties(e, shopProductCategoryVo);
                    return shopProductCategoryVo;
                })
                //根据顶级分类分别获取所有的children 这里使用的是递归
                .map(e -> {
                    e.setChildren(getChildren(e, categoryList));
                    //处理完之后给与子分类一个null
                    /*if (CollectionUtils.isEmpty(e.getChildren())) {
                        e.setChildren(null);
                    }*/
                    return e;
                }).collect(Collectors.toList());
    }

    /**
     * 查询树形下拉框
     *
     * @return
     */
    @Override
    public List<ShopProductCategoryVo> getSelectTree() {
        // 查询出所有非三级分类
        List<ShopProductCategory> categoryList = shopProductCategoryMapper.getSelectList();
        return buildTree(categoryList);
    }

    /**
     * 获取所有二级分类
     *
     * @return
     */
    @Override
    public List<ShopProductCategory> getAllSecond() {
        List<ShopProductCategory> secondList = shopProductCategoryMapper.getAllSecond();
        List<ShopProductCategory> topList = shopProductCategoryMapper.getAllTop();
        //遍历一级分类和二级分类
        secondList.forEach(s -> {
            //便利以及分类,取出id和s.parentId相同的数据
            ShopProductCategory parent = topList.stream().filter(t -> t.getId().equals(s.getParentId()))
                    .findFirst().orElse(null);
            if (parent!=null){
                s.setName(parent.getName()+CoreConstant.CONCAT_NAME+s.getName());
            }
        });
        return secondList;
    }

    /**
     * 创建树  此处主要进行顶级分类过滤  然后调用递归
     *
     * @param categoryList
     * @return
     */
    private List<ShopProductCategoryVo> buildTree(List<ShopProductCategory> categoryList) {
        return categoryList.stream().filter(e -> e.getParentId().equals(CoreConstant.DEFAULT_PARENT_ID))
                .map(e -> {
                    // 构造成VO
                    ShopProductCategoryVo vo = new ShopProductCategoryVo();
                    BeanUtils.copyProperties(e, vo);
                    return vo;
                }).map(e -> {
                    // 构造子节点
                    e.setChildren(getChildren(e, categoryList));
                    return e;
                }).collect(Collectors.toList());
    }
}
