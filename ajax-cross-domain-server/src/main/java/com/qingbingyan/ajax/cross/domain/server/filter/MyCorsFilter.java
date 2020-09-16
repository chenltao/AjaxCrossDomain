package com.qingbingyan.ajax.cross.domain.server.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author bear
 * @Date 2020/9/12
 */
//@Component
public class MyCorsFilter implements Filter {

    private static final String OPTIONS = "OPTIONS";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest)req;

        //从请求中获取访问地址,设置允许访问的Origin, 这将允许所有 origin 跨域访问
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        //是否允许携带 cookie
        response.setHeader("Access-Control-Allow-Credentials","true");
        //允许的请求方式
        response.setHeader("Access-Control-Allow-Methods", "*");
        //缓存 跨域配置的时间
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");

        filterChain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}
