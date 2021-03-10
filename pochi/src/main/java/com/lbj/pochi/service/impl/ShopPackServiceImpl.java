package com.lbj.pochi.service.impl;

import com.lbj.pochi.mapper.ShopPackMapper;
import com.lbj.pochi.mapper.ShopProductMapper;
import com.lbj.pochi.mapper.ShopProductPackMapper;
import com.lbj.pochi.pojo.ShopPack;
import com.lbj.pochi.pojo.ShopProduct;
import com.lbj.pochi.pojo.ShopProductPack;
import com.lbj.pochi.pojo.dto.ShopPackDto;
import com.lbj.pochi.pojo.vo.SysUserVo;
import com.lbj.pochi.service.ShopPackService;
import com.lbj.pochi.utils.IdWorker;
import com.lbj.pochi.utils.Page;
import com.lbj.pochi.utils.ShiroUtils;
import com.lbj.pochi.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品套装ServiceImpl
 */
@Service
public class ShopPackServiceImpl implements ShopPackService {
    @Autowired
    private ShopPackMapper shopPackMapper;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private ShopProductMapper shopProductMapper;
    @Autowired
    private ShopProductPackMapper shopProductPackMapper;

    /**
     * 添加
     *
     * @param shopPackDto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(ShopPackDto shopPackDto) {
        //拷贝属性
        ShopPack shopPack = new ShopPack();
        BeanUtils.copyProperties(shopPackDto, shopPack);
        // ShopPack入库
        SysUserVo loginUser = ShiroUtils.getLoginUser();
        shopPack.setCreateBy(loginUser.getUsername());
        Long packCode = idWorker.nextId();
        shopPack.setId(packCode);
        int count = 0;
        List<ShopProduct> productList = shopPackDto.getProductList();
        if (!CollectionUtils.isEmpty(productList)) {
            count = productList.size();
        }
        shopPack.setProductCount(count);
        //判断传过来的商品集合是否为空
        if (!CollectionUtils.isEmpty(productList)) {
            // 保存商品套装关联表
            List<Long> productIds = productList.stream().map(ShopProduct::getId).collect(Collectors.toList());
            List<ShopProduct> products = shopProductMapper.getByIds(productIds);
            // 构造ShopProductPack
            List<ShopProductPack> packList = products.stream().map(e -> {
                ShopProductPack shopProductPack = new ShopProductPack();
                shopProductPack.setProductId(e.getId());
                shopProductPack.setPackCode(packCode);
                shopProductPack.setPrice(e.getPrice());
                shopProductPack.setStock(e.getStock());
                shopProductPack.setLowStock(e.getLowStock());
                if (StringUtils.isBlank(e.getSpecs())){
                    shopProductPack.setSpecName(e.getName());
                }
                shopProductPack.setProductName(e.getName());
                return shopProductPack;
            }).collect(Collectors.toList());
            shopProductPackMapper.saveBatch(packList);
        }
    }

    /**
     * 修改
     * 先删除在添加
     *
     * @param shopPackDto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ShopPackDto shopPackDto) {
        Long packCode = shopPackDto.getId();
        //拷贝属性
        ShopPack shopPack = new ShopPack();
        BeanUtils.copyProperties(shopPackDto, shopPack);
        int count = 0;
        List<ShopProduct> productList = shopPackDto.getProductList();
        if (!CollectionUtils.isEmpty(productList)) {
            count = productList.size();
        }
        shopPack.setProductCount(count);
        // 修改
        shopPackMapper.update(shopPack);
        // 删除关联表数据
        shopProductPackMapper.deleteByPackCode(packCode);
        //判断传过来的商品集合是否为空
        if (!CollectionUtils.isEmpty(productList)) {
            // 保存商品套装关联表
            List<Long> productIds = productList.stream().map(ShopProduct::getId).collect(Collectors.toList());
            List<ShopProduct> products = shopProductMapper.getByIds(productIds);
            // 构造ShopProductPack
            List<ShopProductPack> packList = products.stream().map(e -> {
                ShopProductPack shopProductPack = new ShopProductPack();
                shopProductPack.setProductId(e.getId());
                shopProductPack.setPackCode(packCode);
                shopProductPack.setPrice(e.getPrice());
                shopProductPack.setStock(e.getStock());
                shopProductPack.setLowStock(e.getLowStock());
                if (StringUtils.isBlank(e.getSpecs())){
                    shopProductPack.setSpecName(e.getName());
                }else {
                    shopProductPack.setSpecName(e.getSpecs());
                }
                shopProductPack.setProductName(e.getName());
                return shopProductPack;
            }).collect(Collectors.toList());
            shopProductPackMapper.saveBatch(packList);
        }
    }

    /**
     * 根据ID获取
     *
     * @param id
     * @return
     */
    @Override
    public ShopPackDto getById(Long id) {
        ShopPack shopPack = shopPackMapper.get(id);
        ShopPackDto shopPackDto = new ShopPackDto();
        BeanUtils.copyProperties(shopPack, shopPackDto);
        List<ShopProductPack> shopProductPackList = shopProductPackMapper.getByPackCode(id);
        if (!CollectionUtils.isEmpty(shopProductPackList)) {
            List<Long> ids = shopProductPackList.stream().map(ShopProductPack::getProductId).collect(Collectors.toList());
            //从商品列表查出所有商品
            List<ShopProduct> shopProducts=shopProductMapper.getByIds(ids);
            shopPackDto.setProductList(shopProducts);
        }
        return shopPackDto;
    }

    /**
     * 根据ID删除
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        //先删除当前表
        shopPackMapper.delete(id);
        //再删除关联表
        shopProductPackMapper.deleteByPackCode(id);
    }

    /**
     * 分页获取
     *
     * @param page
     * @return
     */
    @Override
    public Page<ShopPack> getByPage(Page<ShopPack> page) {
        List<ShopPack> list = shopPackMapper.getByPage(page);
        Integer totalCount = shopPackMapper.countByPage(page);
        page.setList(list);
        page.setTotalCount(totalCount);
        return page;
    }
}
