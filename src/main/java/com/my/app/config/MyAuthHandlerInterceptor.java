package com.my.app.config;


import com.my.app.utils.TokenUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Component
@Slf4j
public class MyAuthHandlerInterceptor implements HandlerInterceptor {

    // 不用授权就能访问的路由
    private Set<String> paths = Set.of("/timestamp");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String requestURI = request.getRequestURI();
        log.info("MyAuthHandlerInterceptor requestURI:{}", requestURI);
        for (String url : paths) {
            String newUrl = url.replaceAll("\\*", "");
            if (requestURI.startsWith(newUrl)) {
                return true;
            }
        }
        // 检查是否授权
        boolean checkAuth = TokenUtils.checkToken(request);
        if (!checkAuth) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print("{\"code\":\"401\",\"message\":\"unauthorized\"}");
        }
        return checkAuth;
    }
}