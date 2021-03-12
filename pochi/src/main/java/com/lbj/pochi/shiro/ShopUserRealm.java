package com.lbj.pochi.shiro;


import com.lbj.pochi.pojo.LoginUser;
import com.lbj.pochi.pojo.ShopUser;
import com.lbj.pochi.service.ShopUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("shopUserRealm")
public class ShopUserRealm extends AuthorizingRealm {

    @Autowired
    private ShopUserService shopUserService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return new SimpleAuthorizationInfo();
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UserToken userToken = (UserToken) authenticationToken;
        String username = userToken.getUsername();
        ShopUser user = shopUserService.getByOpenId(username);
        if (user == null) {
            throw new AuthenticationException("用户不存在！");
        }
        LoginUser loginUser = user.toLoginUser();
        return new SimpleAuthenticationInfo(loginUser, user.getOpenId(), getName());
    }
}
