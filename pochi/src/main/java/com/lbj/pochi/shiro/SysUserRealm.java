package com.lbj.pochi.shiro;

import com.lbj.pochi.enums.ResultEnum;
import com.lbj.pochi.enums.StateEnums;
import com.lbj.pochi.exception.PochiException;
import com.lbj.pochi.mapper.SysMenuMapper;
import com.lbj.pochi.mapper.SysUserMapper;
import com.lbj.pochi.pojo.SysMenu;
import com.lbj.pochi.pojo.SysUser;
import com.lbj.pochi.pojo.vo.SysUserVo;
import com.lbj.pochi.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

/**
 * 系统用户登录realm
 */
@Component("sysUserRealm")
public class SysUserRealm extends AuthorizingRealm {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuMapper sysMenuMapper;
    /**
     * 授权方法
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取登录用户
        SysUserVo sysUserVo = (SysUserVo) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(new HashSet<>(sysUserVo.getAuths()));
        return new SimpleAuthorizationInfo();
    }

    /**
     * 认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //处理登录逻辑
        UsernamePasswordToken usernamePasswordToken= (UsernamePasswordToken) token;
        String username=usernamePasswordToken.getUsername();
        SysUser sysUser = sysUserService.getByUserName(username);
        if (sysUser==null){
            throw new PochiException(ResultEnum.LOGIN_PARAM_ERROR);
        }
        if (StateEnums.NOT_ENABLE.getCode().equals(sysUser.getStatus())){
            //未启用用户
            throw new PochiException(ResultEnum.LOGIN_PARAM_ERROR);
        }
        if (StateEnums.DELETED.getCode().equals(sysUser.getDeleted())){
            //已删除用户
            throw new PochiException(ResultEnum.LOGIN_PARAM_ERROR);
        }
        // 创建SYsUserVo拷贝属性
        SysUserVo sysUserVo = new SysUserVo();
        BeanUtils.copyProperties(sysUser, sysUserVo);
        // 在这里查询权限
        List<String> auths = sysMenuMapper.getMenuCodeByUserId(sysUser.getId());
        if (CollectionUtils.isEmpty(auths)) {
            throw new PochiException("当前用户不具备任何权限，禁止登录");
        }
        sysUserVo.setAuths(auths);
        return new SimpleAuthenticationInfo(sysUserVo,sysUser.getPassword(),this.getName());
    }

}