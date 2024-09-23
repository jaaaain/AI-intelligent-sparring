package com.jaaaain.intercepter;

import com.alibaba.fastjson.JSONObject;
import com.jaaaain.properties.JwtProPerties;
import com.jaaaain.result.Result;
import com.jaaaain.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureException;
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

        System.out.println("进入拦截器，拦截请求路径: " + request.getRequestURL().toString());
        // 1. 获取请求路径
        String url = request.getRequestURL().toString();
        log.info("请求的路径: {}", url);
        // 2. 判断是否放行（例如登录路径不需要拦截）
        if (url.contains("login")) {
            log.info("登录操作, 放行");
            return true;
        }

        String token = request.getHeader("Authorization");
        System.out.println("拦截器中获取到的 Authorization: " + token);

        // 检查 token 是否为空
        if (token == null || !token.startsWith("Bearer ")) {
            System.out.println("没有有效的 Authorization token");
            Result error = Result.error("NOT_LOGIN");
            response.getWriter().write(JSONObject.toJSONString(error));
            return false;
        }
        log.info("登录拦截器: preHandle");


        // 3. 获取请求头中的令牌（token）
        String jwt = request.getHeader(jwtProPerties.getTokenName());

        // 4. 去掉 'Bearer ' 前缀
        jwt = jwt.substring(7); // 去掉 'Bearer ' 前缀
        // 5. 验证令牌
        try {
            // **这里修正 Token 解析的参数顺序**: token 在前，secretKey 在后
            Claims claim = JwtUtil.parseJWT(jwt, jwtProPerties.getSecretKey());
            System.out.println("解析时的 secretKey: " + jwtProPerties.getSecretKey());
            log.info("解析成功: {}", claim);
        } catch (ExpiredJwtException e) {
            log.info("令牌过期，返回未登录的信息: {}", e.getMessage());
            return sendErrorResponse(response, "TOKEN_EXPIRED");
        } catch (SignatureException e) {
            log.info("令牌签名无效，返回未登录的信息: {}", e.getMessage());
            return sendErrorResponse(response, "INVALID_SIGNATURE");
        } catch (Exception e) {
            log.info("令牌解析失败，返回未登录的信息: {}", e.getMessage());
            return sendErrorResponse(response, "NOT_LOGIN");
        }


        return true; // 验证通过，放行
    }

    // 发送错误响应的方法
    private boolean sendErrorResponse(HttpServletResponse response, String errorMessage) throws Exception {
        Result error = Result.error(errorMessage);
        String errorResponse = JSONObject.toJSONString(error);
        response.getWriter().write(errorResponse);
        return false;
    }

    @Override // 目标资源方法执行后执行
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("登录拦截器: postHandle");
    }

    @Override // 视图渲染完毕后执行
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("登录拦截器: afterCompletion");
    }

}
