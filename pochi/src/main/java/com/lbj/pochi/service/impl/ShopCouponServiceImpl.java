package com.lbj.pochi.service.impl;

import com.lbj.pochi.mapper.ShopCouponCategoryMapper;
import com.lbj.pochi.mapper.ShopCouponMapper;
import com.lbj.pochi.mapper.ShopCouponProductMapper;
import com.lbj.pochi.pojo.ShopCoupon;
import com.lbj.pochi.pojo.ShopCouponCategory;
import com.lbj.pochi.pojo.ShopCouponProduct;
import com.lbj.pochi.pojo.dto.ShopCouponDto;
import com.lbj.pochi.pojo.vo.SysUserVo;
import com.lbj.pochi.service.ShopCouponService;
import com.lbj.pochi.utils.IdWorker;
import com.lbj.pochi.utils.Page;
import com.lbj.pochi.utils.ShiroUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 优惠券serviceImpl
 */
@Service
public class ShopCouponServiceImpl implements ShopCouponService {
    @Autowired
    private ShopCouponMapper shopCouponMapper;
    @Autowired
    private ShopCouponCategoryMapper shopCouponCategoryMapper;
    @Autowired
    private ShopCouponProductMapper shopCouponProductMapper;
    @Autowired
    private IdWorker idWorker;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(ShopCouponDto shopCouponDto) {
        Long id=idWorker.nextId();
        // 拷贝属性
        ShopCoupon shopCoupon=new ShopCoupon();
        BeanUtils.copyProperties(shopCouponDto,shopCoupon);
        //设置属性
        SysUserVo sysUserVo= ShiroUtils.getLoginUser();
        shopCoupon.setCreateBy(sysUserVo.getUsername());
        shopCoupon.setUpdateBy(sysUserVo.getUsername());
        shopCoupon.setId(id);
        shopCoupon.setRestCount(shopCouponDto.getPublishCount());
        shopCouponMapper.save(shopCoupon);
        //如果有分类--指定分类
        if (!CollectionUtils.isEmpty(shopCouponDto.getCategoryList())){
            List<ShopCouponCategory> shopCouponCategoryList = shopCouponDto.getCategoryList().stream().map(e -> new ShopCouponCategory(id, e.getId())).collect(Collectors.toList());
            shopCouponCategoryMapper.saveBatch(shopCouponCategoryList);
        }
        //如果有商品--指定商品
        if (!CollectionUtils.isEmpty(shopCouponDto.getProductList())){
            List<ShopCouponProduct> shopCouponProductList = shopCouponDto.getCategoryList().stream().map(e -> new ShopCouponProduct(id, e.getId())).collect(Collectors.toList());
            shopCouponProductMapper.saveBatch(shopCouponProductList);
        }
    }

    @Override
    public void delete(Long id) {
        shopCouponMapper.delete(id);
    }

    @Override
    public void down(Long id) {
        shopCouponMapper.down(id);
    }

    @Override
    public Page<ShopCoupon> getByPage(Page<ShopCoupon> page) {
        List<ShopCoupon> list = shopCouponMapper.getByPage(page);
        Integer totalCount = shopCouponMapper.countByPage(page);
        page.setList(list);
        page.setTotalCount(totalCount);
        return page;
    }
}
