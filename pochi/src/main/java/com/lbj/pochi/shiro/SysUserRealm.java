package com.lbj.pochi.shiro;

import com.lbj.pochi.enums.ResultEnum;
import com.lbj.pochi.enums.StateEnums;
import com.lbj.pochi.exception.PochiException;
import com.lbj.pochi.pojo.SysUser;
import com.lbj.pochi.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 系统用户登录realm
 */
@Component("sysUserRealm")
public class SysUserRealm extends AuthorizingRealm {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 授权方法
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
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
        return new SimpleAuthenticationInfo(sysUser,sysUser.getPassword(),this.getName());
    }

}