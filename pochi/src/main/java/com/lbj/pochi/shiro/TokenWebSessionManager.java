package com.lbj.pochi.shiro;

import com.lbj.pochi.constant.CoreConstant;
import com.lbj.pochi.utils.StringUtils;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.UUID;

@Configuration
public class TokenWebSessionManager extends DefaultWebSessionManager {
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        //1.从请求头获取token
        String token = WebUtils.toHttp(request).getHeader(CoreConstant.TOKEN_HEADER);
        //2.如果token存在,就返回token,否则生成一个返回
        if (StringUtils.isNotBlank(token)){
            return token;
        }
        return UUID.randomUUID().toString();
    }
}
