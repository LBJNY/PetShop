package com.lbj.pochi.controller;

import com.lbj.pochi.pojo.ShopCoupon;
import com.lbj.pochi.pojo.dto.ShopCouponDto;
import com.lbj.pochi.service.ShopCouponService;
import com.lbj.pochi.utils.Page;
import com.lbj.pochi.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 优惠券Controller
 */
@RestController
@RequestMapping("/shopCoupon")
public class ShopCouponController {

    @Autowired
    private ShopCouponService shopCouponService;

    /**
     * 添加
     * @param shopCouponDto
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result<?> save(@RequestBody ShopCouponDto shopCouponDto) {
        shopCouponService.save(shopCouponDto);
        return new Result<>("添加成功");
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Result<?> delete(@PathVariable Long id) {
        shopCouponService.delete(id);
        return new Result<>("删除成功");
    }

    /**
     * 下架
     * @param id
     * @return
     */
    @RequestMapping(value = "/down/{id}", method = RequestMethod.PUT)
    public Result<?> down(@PathVariable Long id) {
        shopCouponService.down(id);
        return new Result<>("下架成功");
    }

    /**
     * 分页查询
     * @param page
     * @return
     */
    @RequestMapping(value = "/getByPage", method = RequestMethod.POST)
    public Result<Page<ShopCoupon>> getByPage(@RequestBody Page<ShopCoupon> page) {
        page = shopCouponService.getByPage(page);
        return new Result<>(page);
    }

}
