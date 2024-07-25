package com.jaaaain.intercepter;

import com.alibaba.fastjson.JSONObject;
import com.jaaaain.result.Result;
import com.jaaaain.properties.JwtProPerties;
import com.jaaaain.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component // 定义拦截器
public class LoginCheckIntercepter implements HandlerInterceptor {
    @Autowired
    private JwtProPerties jwtProPerties;

    @Override // 目标资源方法执行前，判断是否放行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("登录拦截器: preHandle");

        // 1. 获取请求路径
        String url = request.getRequestURL().toString();
        log.info("请求的路径: {}",url);

        // 2. 判断放行（是否为不需要进行拦截和身份校验的路径，如login登录本来就是为没令牌身份的陌生人准备的）
        if (url.contains("login")) {
            log.info("登录操作,放行");
            return true;
        }

        // 3. 获取请求头中的令牌（token），因为未放行的请求都是应该需要携带令牌身份的路径，没身份证不能访问的路径
        String jwt = request.getHeader(jwtProPerties.getTokenName());

        // 4. 判断令牌是否存在;  5. 判断令牌是否正确;
        // 不存在或解析失败，则不通过身份校验，返回错误结果，使前端将用户导向登录界面登录(麻烦你们先去保安那边做个登记~~)
        if (!StringUtils.hasLength(jwt)) {
            log.info("请求头token为空，返回未登录的信息");
            Result error = Result.error("NOT_LOGIN");
            // 手动转换 对象--JSON （fastJSON）
            String notLogin = JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);
            return false;
        }
        try {
            Claims claim = JwtUtil.parseJWT(jwtProPerties.getSecretKey(),jwt);
            log.info("解析成功: {}",claim.getSubject());
        } catch (Exception e) {
            log.info("令牌解析失败，返回未登录的信息");
            Result error = Result.error("NOT_LOGIN");
            // 手动转换 对象--JSON （fastJSON）
            String notLogin = JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);
            return false;
        }

        return true;
    }
    @Override // 目标资源方法执行后执行
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("登录拦截器: postHandle");
    }
    @Override // 视图渲染完毕后执行
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("登录拦截器: afterCompletion");
    }

}
