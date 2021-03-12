package com.lbj.pochi.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;

import java.util.ArrayList;
import java.util.Collection;

public class UserToken extends UsernamePasswordToken {

    private Class<? extends AuthorizingRealm> userType;

    public UserToken(final String username, final String password, Class<? extends AuthorizingRealm> userType) {
        super(username, password);
        this.userType = userType;
    }

    public Class<?> getUserType() {
        return userType;
    }

    public void setUserType(Class<? extends AuthorizingRealm> userType) {
        this.userType = userType;
    }

}