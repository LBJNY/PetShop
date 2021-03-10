package com.lbj.pochi.service.impl;

import com.lbj.pochi.constant.CoreConstant;
import com.lbj.pochi.enums.StateEnums;
import com.lbj.pochi.mapper.*;
import com.lbj.pochi.pojo.*;
import com.lbj.pochi.pojo.dto.ShopProductDto;
import com.lbj.pochi.pojo.vo.ShopProductVo;
import com.lbj.pochi.pojo.vo.SysUserVo;
import com.lbj.pochi.service.ShopProductService;
import com.lbj.pochi.utils.IdWorker;
import com.lbj.pochi.utils.Page;
import com.lbj.pochi.utils.ShiroUtils;
import com.lbj.pochi.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品serviceImpl类
 */
@Service
public class ShopProductServiceImpl implements ShopProductService {
    @Autowired
    private ShopProductMapper shopProductMapper;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private ShopBrandMapper shopBrandMapper;
    @Autowired
    private ShopProductCategoryMapper shopProductCategoryMapper;
    @Autowired
    private ShopProductPackMapper shopProductPackMapper;
    @Autowired
    private ShopPackMapper shopPackMapper;

    /**
     * 保存商品
     *
     * @param shopProductDto dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(ShopProductDto shopProductDto) {
        //拷贝属性
        ShopProduct shopProduct = new ShopProduct();
        BeanUtils.copyProperties(shopProductDto, shopProduct);
        //使用雪花算法生成ID
        shopProduct.setId(idWorker.nextId());
        //判断轮播图是否为空
        if (!CollectionUtils.isEmpty(shopProductDto.getAlbumPicList())) {
            //轮播图列表如果不为空,使用 ',' 将路径拼接
            shopProduct.setAlbumPics(StringUtils.join(shopProductDto.getAlbumPicList(), ","));
        } else {
            //为空 使用商品图片代替
            shopProduct.setAlbumPics(shopProductDto.getPic());
        }
         /*
            品牌名称
            一般来讲，任何场景下我们都不能信任前端所传递的参数
            我们只能接收前端传递的品牌ID，名称我们自己去表里查
            如果我们系统的安全性做的比较足的话
            那么前端参数被伪造的概率是相当低的
            这个时候我们就可以相信前端传的参数
            我们本次课程系统安全性较差，因此不相信前端的参数
         */
        //获取商品分类名称
        ShopBrand brand = shopBrandMapper.get(shopProduct.getBrandId());
        shopProduct.setBrandName(brand.getName());
        //设置各项参数
        if (StringUtils.isBlank(shopProduct.getSubTitle())) {
            shopProduct.setSubTitle(shopProduct.getName());
        }
        // 创建人、修改人
        SysUserVo loginUser = ShiroUtils.getLoginUser();
        shopProduct.setCreateBy(loginUser.getUsername());
        shopProduct.setUpdateBy(loginUser.getUsername());
        shopProductMapper.save(shopProduct);
        // 处理套装
        handleProductPack(shopProductDto, shopProduct, loginUser);
    }

    /**
     * 处理添加商品时的套装逻辑
     *
     * @param shopProductDto 待添加商品的参数
     * @param shopProduct    当前商品
     * @param loginUser      当前登录用户
     */
    private void handleProductPack(ShopProductDto shopProductDto, ShopProduct shopProduct, SysUserVo loginUser) {
        //商品添加处理套装
        ShopPack shopPack = shopProductDto.getShopPack();
        List<ShopProduct> productList = shopProductDto.getProductList();
        //* 用户选择了关联套装和关联商品
        if (shopPack != null && !CollectionUtils.isEmpty(productList)) {
            Long packCode = shopPack.getId();
            //  * 该商品没有加入其它套装：将该商品和选择的商品都加入到套装中。
            // 保存商品套装关联表
            List<Long> productIds = productList.stream().map(ShopProduct::getId).collect(Collectors.toList());
            List<ShopProduct> products = shopProductMapper.getByIds(productIds);
            // 将该商品加入到products---将当前商品也添加到套装类里面
            products.add(shopProduct);
            //构建ShopPack
            handleProductPack(packCode, products);
        } else if (shopPack != null) {
            //* 用户没同时选择关联套装和关联商品
            Long packCode = shopPack.getId();
            //  * 用户选择了关联套装
            //      * 该商品没套装：将该商品加入到选择的套装中
            List<ShopProduct> products = new ArrayList<>();
            // 将该商品加入到products
            products.add(shopProduct);
            // 构造ShopProductPack
            handleProductPack(packCode, products);
        } else if (!CollectionUtils.isEmpty(productList)) {
            //* 用户选择了关联商品
            //  * 该商品没套装：将该商品和选择的商品打包成一个套装
            //  * 该商品有套装：将该商品和选择的商品打包成一个套装，并减少原套装的商品数。
            // 拷贝属性
            shopPack = new ShopPack();
            // ShopPack入库
            shopPack.setCreateBy(loginUser.getUsername());
            shopPack.setName(shopProduct.getName());
            shopPack.setProductCount(productList.size() + 1);
            long packCode = idWorker.nextId();
            shopPack.setId(packCode);
            // 保存
            shopPackMapper.save(shopPack);
            // 保存商品套装关联表
            List<Long> productIds = productList.stream().map(ShopProduct::getId).collect(Collectors.toList());
            List<ShopProduct> products = shopProductMapper.getByIds(productIds);
            // 将该商品加入到products
            products.add(shopProduct);
            // 构造ShopProductPack
            handleProductPack(packCode, products);
        }
    }

    /**
     * 处理商品的套装逻辑
     *
     * @param packCode
     * @param products
     */
    private void handleProductPack(Long packCode, List<ShopProduct> products) {
        // 构造ShopProductPack
        List<ShopProductPack> packList = products.stream().map(e -> {
            ShopProductPack shopProductPack = new ShopProductPack();
            shopProductPack.setProductId(e.getId());
            shopProductPack.setPackCode(packCode);
            shopProductPack.setPrice(e.getPrice());
            shopProductPack.setStock(e.getStock());
            shopProductPack.setLowStock(e.getLowStock());
            shopProductPack.setSpecName(e.getSpecs());
            if (StringUtils.isBlank(shopProductPack.getSpecName())) {
                shopProductPack.setSpecName(e.getName());
            }
            shopProductPack.setProductName(e.getName());
            return shopProductPack;
        }).collect(Collectors.toList());
        shopProductPackMapper.saveBatch(packList);
        //更新商品数
        shopPackMapper.updateProductCount(packCode, packList.size());
    }

    /**
     * 分页获取商品
     *
     * @param page
     * @return
     */
    @Override
    public Page<ShopProductVo> getByPage(Page<ShopProductVo> page) {
        //查询列表
        List<ShopProduct> list = shopProductMapper.getByPage(page);
        int totalCount = shopProductMapper.countByPage(page);
        //将查询出来的列表转换为视图类
        List<ShopProductVo> collect = list.stream().map(e -> {
            ShopProductVo shopProductVo = new ShopProductVo();
            BeanUtils.copyProperties(e, shopProductVo);
            String albumPics = e.getAlbumPics();
            if (!StringUtils.isBlank(e.getAlbumPics())) {
                shopProductVo.setAlbumPicList(Arrays.asList(albumPics.split(",")));
            }
            return shopProductVo;
        }).collect(Collectors.toList());

        //对分类做处理
        List<Long> categoryIds = collect.stream().map(ShopProductVo::getCategoryId).collect(Collectors.toList());
        List<ShopProductCategory> byIds = shopProductCategoryMapper.getByIds(categoryIds);
        // 封装进去
        collect.forEach(e -> {
            ShopProductCategory category = byIds.stream().filter(c -> c.getId().equals(e.getCategoryId())).findFirst().orElse(new ShopProductCategory());
            e.setCategory(category);
        });
        page.setList(collect);
        page.setTotalCount(totalCount);
        return page;
    }

    /**
     * 获取回回显数据
     *
     * @param id
     * @return
     */
    @Override
    public ShopProductVo getUpdate(Long id) {
        // 根据id查询
        ShopProduct product = shopProductMapper.getById(id);
        // 转换成VO
        ShopProductVo shopProductVo = new ShopProductVo();
        BeanUtils.copyProperties(product, shopProductVo);
        // 处理轮播图
        if (StringUtils.isNotBlank(product.getAlbumPics())) {
            shopProductVo.setAlbumPicList(Arrays.asList(product.getAlbumPics().split(",")));
        }
        // 处理categoryIds
        // 拿到子节点id
        Long categoryId = product.getCategoryId();
        Deque<Long> deque = new ArrayDeque<>(4);
        while (categoryId != null && !categoryId.equals(CoreConstant.DEFAULT_PARENT_ID)) {
            // 根据子节点ID查询分类
            ShopProductCategory category = shopProductCategoryMapper.get(categoryId);
            if (category != null) {
                // 入列
                deque.push(category.getId());
                categoryId = category.getParentId();
            } else {
                break;
            }
        }
        // 封装到vo里
        shopProductVo.setCategoryIds(new ArrayList<>(deque));
        // 根据商品查询该商品所在关联
        ShopProductPack productPack = shopProductPackMapper.getByProductId(shopProductVo.getId());
        //根据取出的套装编号查询套装
        if (productPack != null) {
            //根据取出来的套装编号查询套装
            ShopPack shopPack = shopPackMapper.get(productPack.getPackCode());
            shopProductVo.setShopPack(shopPack);
            //根据套装编号 查询商品关联
            List<ShopProductPack> productPacks = shopProductPackMapper.getByPackCode(productPack.getPackCode());
            //获取id集合
            if (!CollectionUtils.isEmpty(productPacks)) {
                List<Long> productIds = productPacks.stream().map(ShopProductPack::getProductId).collect(Collectors.toList());
                //根据商品ID集合查询商品
                List<ShopProduct> productList = shopProductMapper.getByIds(productIds);
                shopProductVo.setProductList(productList);
            }
        }
        return shopProductVo;
    }

    /**
     * 更新
     *
     * @param shopProductDto
     */
    @Override
    public void update(ShopProductDto shopProductDto) {
        //查询列表
        ShopProduct shopProduct = new ShopProduct();
        BeanUtils.copyProperties(shopProductDto, shopProduct);
        //处理轮播图
        List<String> albumPicList = shopProductDto.getAlbumPicList();
        if (CollectionUtils.isEmpty(albumPicList)) {
            shopProduct.setAlbumPics(shopProduct.getPic());
        } else {
            shopProduct.setAlbumPics(StringUtils.join(albumPicList, ","));
        }
        //获取商品分类名称
        ShopBrand brand = shopBrandMapper.get(shopProduct.getBrandId());
        shopProduct.setBrandName(brand.getName());
        //设置各项参数
        if (StringUtils.isBlank(shopProduct.getSubTitle())) {
            shopProduct.setSubTitle(shopProduct.getName());
        }
        // 创建人、修改人
        SysUserVo loginUser = ShiroUtils.getLoginUser();
        shopProduct.setUpdateBy(loginUser.getUsername());
        shopProductMapper.update(shopProduct);

        // 处理商品套装
        handleUpdateProductPack(shopProductDto, shopProduct, loginUser);
    }

    /**
     * 处理修改时的商品套装逻辑
     *
     * @param shopProductDto 前端传参的商品对象
     * @param shopProduct    当前商品
     * @param loginUser      当前登录用户
     */
    private void handleUpdateProductPack(ShopProductDto shopProductDto, ShopProduct shopProduct, SysUserVo loginUser) {
        //商品添加处理套装
        ShopPack shopPack = shopProductDto.getShopPack();
        List<ShopProduct> productList = shopProductDto.getProductList();
        //* 用户选择了关联套装和关联商品
        if (shopPack != null && !CollectionUtils.isEmpty(productList)) {
            Long packCode = shopPack.getId();
            //  * 该商品没有加入其它套装：将该商品和选择的商品都加入到套装中。
            // 保存商品套装关联表
            List<Long> productIds = productList.stream().map(ShopProduct::getId).collect(Collectors.toList());
            List<ShopProduct> products = shopProductMapper.getByIds(productIds);
            // 将该商品加入到products---将当前商品也添加到套装类里面
            products.add(shopProduct);
            //构建ShopPack
            handleUpdateProductPack(shopProductDto, shopPack, products);
        } else if (shopPack != null) {
            //* 用户没同时选择关联套装和关联商品
            //  * 用户选择了关联套装
            //      * 该商品没套装：将该商品加入到选择的套装中
            List<ShopProduct> products = new ArrayList<>();
            // 将该商品加入到products
            products.add(shopProduct);
            // 构造ShopProductPack
            handleUpdateProductPack(shopProductDto, shopPack, products);
        } else if (!CollectionUtils.isEmpty(productList)) {
            //* 用户选择了关联商品
            //  * 该商品没套装：将该商品和选择的商品打包成一个套装
            //  * 该商品有套装：将该商品和选择的商品打包成一个套装，并减少原套装的商品数。
            // 拷贝属性
            List<ShopProduct> products = new ArrayList<>();
            // ShopPack入库
            shopPack.setCreateBy(loginUser.getUsername());
            shopPack.setName(shopProduct.getName());
            shopPack.setProductCount(productList.size() + 1);
            long packCode = idWorker.nextId();
            shopPack.setId(packCode);
            // 保存
            shopPackMapper.save(shopPack);
            // 构造ShopProductPack
            handleUpdateProductPack(shopProductDto, shopPack, products);
        }
    }

    /**
     * 处理修改商品时的套装逻辑
     *
     * @param shopProductDto
     * @param shopPack
     * @param products
     */
    private void handleUpdateProductPack(ShopProductDto shopProductDto, ShopPack shopPack, List<ShopProduct> products) {
        // 这里应该查询出products所有商品所在的关联关系，并去更新商品套装
        List<Long> productIds = products.stream().map(ShopProduct::getId).collect(Collectors.toList());
        List<ShopProductPack> shopProductPackList = shopProductPackMapper.getByProductIds(productIds);
        if (!CollectionUtils.isEmpty(shopProductPackList)) {
            shopProductPackList.forEach(e -> {
                // 减少原套装的商品数
                Long oldPackCode = e.getPackCode();
                ShopPack oldPack = shopPackMapper.get(oldPackCode);
                oldPack.setProductCount(oldPack.getProductCount() - 1);
                // 如果商品数等于0，就删除该套装，否则修改
                if (oldPack.getProductCount() == 0) {
                    shopPackMapper.delete(oldPackCode);
                } else {
                    shopPackMapper.update(oldPack);
                }
            });
        }
        // 删除旧的关联关系
        shopProductPackMapper.deleteByProductIds(productIds);
        handleProductPack(shopPack.getId(), products);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        shopProductMapper.delete(id);
    }

    /**
     * 上架
     *
     * @param id
     */
    @Override
    public void publish(Long id) {
        ShopProduct product = shopProductMapper.getById(id);
        product.setPublishStatus(StateEnums.ENABLED.getCode());
        shopProductMapper.updatePublish(product);
    }

    /**
     * 下架
     *
     * @param id
     */
    @Override
    public void unPublish(Long id) {
        ShopProduct product = shopProductMapper.getById(id);
        product.setPublishStatus(StateEnums.NOT_ENABLE.getCode());
        shopProductMapper.updatePublish(product);
    }

    /**
     * 新品
     *
     * @param id
     */
    @Override
    public void news(Long id) {
        ShopProduct product = shopProductMapper.getById(id);
        product.setNewStatus(StateEnums.ENABLED.getCode());
        shopProductMapper.updateNewStatus(product);
    }

    /**
     * 非新品
     *
     * @param id
     */
    @Override
    public void old(Long id) {
        ShopProduct product = shopProductMapper.getById(id);
        product.setNewStatus(StateEnums.NOT_ENABLE.getCode());
        shopProductMapper.updateNewStatus(product);
    }

    /**
     * 推荐
     *
     * @param id
     */
    @Override
    public void recommend(Long id) {
        ShopProduct product = shopProductMapper.getById(id);
        product.setRecommendStatus(StateEnums.ENABLED.getCode());
        shopProductMapper.updateRecommend(product);
    }

    /**
     * 非推荐
     *
     * @param id
     */
    @Override
    public void unRecommend(Long id) {
        ShopProduct product = shopProductMapper.getById(id);
        product.setRecommendStatus(StateEnums.NOT_ENABLE.getCode());
        shopProductMapper.updateRecommend(product);
    }

    /**
     * 根据packIds获取pack
     *
     * @param packCode
     * @return
     */
    @Override
    public List<ShopProduct> getProductDetailList(Long packCode) {
        List<ShopProductPack> packList = shopProductPackMapper.getByPackCode(packCode);
        if (CollectionUtils.isEmpty(packList)) {
            return new ArrayList<>(0);
        }
        // 获取商品id
        List<Long> productIds = packList.stream().map(ShopProductPack::getProductId).collect(Collectors.toList());
        return shopProductMapper.getByIds(productIds);
    }

    /**
     * 分页查询 没有套装
     *
     * @param page
     * @return
     */
    @Override
    public Page<ShopProductVo> getByPageHasNotPack(Page<ShopProductVo> page) {
        // 查询list和总条数
        List<ShopProduct> list = shopProductMapper.getByPageHasNotPack(page);
        Integer totalCount = shopProductMapper.countByPageHasNotPack(page);
        // ShopProduct的list转成vo的list
        List<ShopProductVo> productVoList = list.stream().map(e -> {
            ShopProductVo vo = new ShopProductVo();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());
        // 处理分类名称
        List<Long> categoryIds = productVoList.stream().map(ShopProductVo::getCategoryId).collect(Collectors.toList());
        List<ShopProductCategory> categoryList = shopProductCategoryMapper.getByIds(categoryIds);
        // 封装进去
        productVoList.forEach(e -> {
            ShopProductCategory category = categoryList.stream().filter(c -> c.getId().equals(e.getCategoryId())).findFirst().orElse(new ShopProductCategory());
            e.setCategory(category);
        });
        page.setList(productVoList);
        page.setTotalCount(totalCount);
        return page;
    }
}
