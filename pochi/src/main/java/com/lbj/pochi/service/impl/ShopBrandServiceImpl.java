package com.lbj.pochi.service.impl;

import com.lbj.pochi.mapper.ShopBrandCategoryMapper;
import com.lbj.pochi.mapper.ShopBrandMapper;
import com.lbj.pochi.mapper.ShopProductCategoryMapper;
import com.lbj.pochi.pojo.ShopBrand;
import com.lbj.pochi.pojo.ShopBrandCategory;
import com.lbj.pochi.pojo.ShopProductCategory;
import com.lbj.pochi.pojo.SysNotice;
import com.lbj.pochi.pojo.dto.ShopBrandDto;
import com.lbj.pochi.pojo.vo.ShopBrandVo;
import com.lbj.pochi.service.ShopBrandService;
import com.lbj.pochi.utils.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 品牌类serviceImpl
 */
@Service
public class ShopBrandServiceImpl implements ShopBrandService {
    @Autowired
    private ShopBrandMapper shopBrandMapper;
    @Autowired
    private ShopBrandCategoryMapper shopBrandCategoryMapper;
    @Autowired
    private ShopProductCategoryMapper shopProductCategoryMapper;

    /**
     * 分页查询
     * @param page
     * @return
     */
    @Override
    public Page<ShopBrand> getByPage(Page<ShopBrand> page) {
        List<ShopBrand> list = shopBrandMapper.getByPage(page);
        Integer totalCount = shopBrandMapper.countByPage(page);
        page.setList(list);
        page.setTotalCount(totalCount);
        return page;
    }

    /**
     * 获取更新回显
     * @param id
     * @return
     */
    @Override
    public ShopBrandDto getUpdate(Long id) {
        //根据ID获取
        ShopBrand shopBrand = shopBrandMapper.get(id);
        //转换为ShopBrandDto
        ShopBrandDto shopBrandDto = new ShopBrandDto();
        BeanUtils.copyProperties(shopBrand, shopBrandDto);
        //查询集合
        List<ShopBrandCategory> brandCategoryList = shopBrandCategoryMapper.getByBrandId(id);
        // 判断集合是否为空，不为空就取出分类ID集合
        if (!CollectionUtils.isEmpty(brandCategoryList)) {
            List<Long> categoryIds = brandCategoryList.stream().map(ShopBrandCategory::getCategoryId).collect(Collectors.toList());
            // set进dto
            shopBrandDto.setCategoryIds(categoryIds);
        }
        return shopBrandDto;
    }

    /**
     * 根据Id获取
     * @param id
     * @return
     */
    @Override
    public ShopBrandVo get(Long id) {
        // 查询出品牌
        ShopBrand shopBrand = shopBrandMapper.get(id);
        // 转成VO
        ShopBrandVo shopBrandVo = new ShopBrandVo();
        BeanUtils.copyProperties(shopBrand, shopBrandVo);
        // 根据品牌id查询品牌-分类关联关系
        List<ShopBrandCategory> shopBrandCategoryList = shopBrandCategoryMapper.getByBrandId(id);
        if (!CollectionUtils.isEmpty(shopBrandCategoryList)) {
            // 取出分类ID
            List<Long> categoryIds = shopBrandCategoryList.stream().map(ShopBrandCategory::getCategoryId).collect(Collectors.toList());
            // 根据关联关系查询分类
            List<ShopProductCategory> categoryList = shopProductCategoryMapper.getByIds(categoryIds);
            shopBrandVo.setCategoryList(categoryList);
        }
        return shopBrandVo;
    }

    /**
     * 删除
     * 同时删除关联表
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        //删除品牌类
        shopBrandMapper.delete(id);
        //删除品牌分类关联表冗余信息
        shopBrandCategoryMapper.deleteByBrandId(id);
    }

    /**
     * 更新数据
     * 1.修改品牌类
     * 2.删除关联表
     * 3.添加关联表
     * @param shopBrandDto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ShopBrandDto shopBrandDto) {
        //拷贝属性
        ShopBrand shopBrand = new ShopBrand();
        BeanUtils.copyProperties(shopBrandDto, shopBrand);
        //修改
        shopBrandMapper.update(shopBrand);
        // 将品牌-分类删除
        shopBrandCategoryMapper.deleteByBrandId(shopBrand.getId());
        // 将品牌-分类存库
        // 判断分类ID集合是否为空，不为空就入库
        if (!CollectionUtils.isEmpty(shopBrandDto.getCategoryIds())) {
            List<ShopBrandCategory> brandCategoryList = shopBrandDto.getCategoryIds().stream().map(cid -> {
                ShopBrandCategory shopBrandCategory = new ShopBrandCategory();
                shopBrandCategory.setCategoryId(cid);
                shopBrandCategory.setBrandId(shopBrand.getId());
                return shopBrandCategory;
            }).collect(Collectors.toList());
            // 存库
            shopBrandCategoryMapper.saveBatch(brandCategoryList);
        }
    }

    /**
     * 添加
     * 同时添加关联表
     * @param shopBrandDto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(ShopBrandDto shopBrandDto) {
        //拷贝属性
        ShopBrand shopBrand = new ShopBrand();
        BeanUtils.copyProperties(shopBrandDto, shopBrand);
        //保存
        shopBrandMapper.save(shopBrand);
        // 将品牌-分类存库
        // 判断分类ID集合是否为空，不为空就入库
        if (!CollectionUtils.isEmpty(shopBrandDto.getCategoryIds())) {
            List<ShopBrandCategory> brandCategoryList = shopBrandDto.getCategoryIds().stream().map(cid -> {
                ShopBrandCategory shopBrandCategory = new ShopBrandCategory();
                shopBrandCategory.setCategoryId(cid);
                shopBrandCategory.setBrandId(shopBrand.getId());
                return shopBrandCategory;
            }).collect(Collectors.toList());
            // 存库
            shopBrandCategoryMapper.saveBatch(brandCategoryList);
        }
    }

    /**
     * 根据分类Id查询品牌
     * @param categoryId
     * @return
     */
    @Override
    public List<ShopBrand> getByCategoryId(Long categoryId) {
        List<ShopBrandCategory> shopBrandCategories=shopBrandCategoryMapper.getByCategoryId(categoryId);
        if (CollectionUtils.isEmpty(shopBrandCategories)){
            return new ArrayList<>(0);
        }
        List<Long> brandIds = shopBrandCategories.stream().map(ShopBrandCategory::getBrandId).collect(Collectors.toList());
        return shopBrandMapper.getByIds(brandIds);
    }

    /**
     * 根据名称查询
     * @param name
     * @return
     */
    @Override
    public List<ShopBrand> getByName(String name) {
        return shopBrandMapper.getByName(name);
    }
}
