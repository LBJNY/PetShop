package com.lbj.pochi.shiro;

import com.alibaba.fastjson.JSON;
import com.lbj.pochi.enums.ResultEnum;
import com.lbj.pochi.utils.Result;
import org.apache.shiro.web.filter.authc.UserFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 重写登录失效--重定向
 */
public class LoginFilter extends UserFilter {
    /**
     * 用于处理未登录时,页面重定向的逻辑
     * 因此,只要进入这个方法,就意味着登录失效了
     * 只需要在这个方法里给前端返回登陆失败状态即可
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        //设置响应头为json
        response.setContentType("application/json; charset=utf-8");
        //直接返回未登录的json报文
        response.getWriter().write(JSON.toJSONString(new Result<>(ResultEnum.NOT_LOGIN)));
    }
}
