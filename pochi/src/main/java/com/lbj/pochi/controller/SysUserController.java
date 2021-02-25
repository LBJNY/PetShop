package com.lbj.pochi.controller;

import com.lbj.pochi.enums.ResultEnum;
import com.lbj.pochi.enums.StateEnums;
import com.lbj.pochi.pojo.SysUser;
import com.lbj.pochi.pojo.TokenVo;
import com.lbj.pochi.service.SysUserService;
import com.lbj.pochi.utils.Page;
import com.lbj.pochi.utils.Result;
import com.lbj.pochi.utils.ShiroUtils;
import com.lbj.pochi.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
 * 系统用户控制层
 *
 * @Author: li
 */
@RestController
@RequestMapping("/sysUser")
@Slf4j
public class SysUserController {
    /**
     * 登录时返回的token
     */
    private Serializable token;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    @PostMapping(value = "/getByPage")
    public Result<Page<SysUser>> getByPage(@RequestBody Page<SysUser> page) {
        page = sysUserService.getByPage(page);
        return new Result<>(page);
    }
    /**
     * 保存用户
     *
     * @param sysUser
     * @return
     */
    @PostMapping("/save")
    public Result<?> save(@RequestBody SysUser sysUser) {
        //做参数校验--用户名&&密码
        if (StringUtils.isBlank(sysUser.getUsername())) {
            return new Result<>(ResultEnum.PARAMS_NULL, "用户名不能为空!");
        }
        if (StringUtils.isBlank(sysUser.getPassword())) {
            return new Result<>(ResultEnum.PARAMS_NULL, "密码不能为空!");
        }
        sysUserService.save(sysUser);
        return new Result<>("添加成功!");
    }

    /**
     * 更新用户--一般不提供更新密码功能
     *
     * @param sysUser
     * @return
     */
    @PutMapping("/update")
    public Result<?> update(@RequestBody SysUser sysUser) {
        sysUserService.update(sysUser);
        return new Result<>("修改成功!");
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Long id) {
        sysUserService.delete(id);
        return new Result<>("删除成功!");
    }

    @PutMapping("/enable/{id}")
    public Result<?> enable(@PathVariable Long id) {
        sysUserService.enable(id);
        return new Result<>("启用成功!");
    }

    @PutMapping("/disable/{id}")
    public Result<?> disable(@PathVariable Long id) {
        sysUserService.disable(id);
        return new Result<>("禁用成功!");
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/get/{id}")
    public Result<SysUser> get(@PathVariable Long id) {
        SysUser sysUser = sysUserService.get(id);
        return new Result<>(sysUser);
    }

    /**
     * 登录
     *
     * @param sysUser
     * @return
     */
    @PostMapping(value = "/login")
    public Result<TokenVo> login(@RequestBody SysUser sysUser) {
        //校验用户名密码
        if (sysUser == null || StringUtils.isBlank(sysUser.getUsername()) || StringUtils.isBlank(sysUser.getPassword())) {
            return new Result<>(ResultEnum.LOGIN_PARAM_ERROR);
        }
        //使用shiro进行登录
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken authenticationToken = new UsernamePasswordToken(sysUser.getUsername(), sysUser.getPassword());
        try {
            subject.login(authenticationToken);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(ResultEnum.LOGIN_PARAM_ERROR);
        }
        //登陆成功
        Serializable sessionId = subject.getSession().getId();
        //更新登陆时间
        sysUserService.updateLoginTime(sysUser.getUsername());

        return new Result<>(new TokenVo(sessionId));
    }

    /**
     * 获取登录用户信息
     *
     * @return
     */
    @GetMapping("/info")
    public Result<SysUser> info() {
        SysUser loginUser = ShiroUtils.getLoginUser();
        loginUser.setPassword(null);
        loginUser.setStatus(null);
        loginUser.setDeleted(null);
        return new Result<>(loginUser);
    }

    @GetMapping("logout")
    public Result<?> logout() {
        //清除shiro缓存
        SecurityUtils.getSubject().logout();
        return new Result<>("退出登录");
    }
}
