package com.jaaaain.intercepter;

import com.alibaba.fastjson.JSONObject;
import com.jaaaain.constant.JwtClaimsConstant;
import com.jaaaain.context.BaseContext;
import com.jaaaain.exception.BizExceptionEnum;
import com.jaaaain.properties.JwtProPerties;
import com.jaaaain.result.Result;
import com.jaaaain.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component // 定义拦截器
public class LoginCheckIntercepter implements HandlerInterceptor {

    @Autowired
    private JwtProPerties jwtProPerties;

    @Override // 目标资源方法执行前，判断是否放行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("登录拦截器: preHandle");
        // 1. 获取请求路径
        String url = request.getRequestURL().toString();
        log.info("进入拦截器，请求的路径: {}", url);

        String token = request.getHeader(jwtProPerties.getTokenName());
        System.out.println("拦截器中获取到的 Authorization: " + token);

        // 检查 token 是否为空
        if (token == null || !token.startsWith("Bearer ")) {
            System.out.println("没有有效的 Authorization token");
            Result error = Result.error(BizExceptionEnum.NOT_LOGIN.getMsg());
            response.getWriter().write(JSONObject.toJSONString(error));
            return false;
        }

        // 4. 去掉 'Bearer ' 前缀
        token = token.substring(7); // 去掉 'Bearer ' 前缀
        // 5. 验证令牌
        try {
            // 这里修正 Token 解析的参数顺序: token 在前，secretKey 在后
            Claims claim = JwtUtil.parseJWT(token, jwtProPerties.getSecretKey());
            log.info("解析成功: {}", claim);
            Long userId = Long.valueOf(claim.get(JwtClaimsConstant.USER_ID).toString());
            Integer isAdmin = Integer.valueOf(claim.get(JwtClaimsConstant.IS_ADMIN).toString());
            log.info("当前用户的id: {}; 是否为管理员: {}", userId, isAdmin);
            // 存储当前进程的身份数据
            BaseContext.setCurrentId(userId);
            BaseContext.setCurrentRole(isAdmin);
        } catch (ExpiredJwtException e) {
            log.info("令牌过期，返回未登录的信息: {}", e.getMessage());
            return sendErrorResponse(response, BizExceptionEnum.TOKEN_EXPIRED.getMsg());
        } catch (SignatureException e) {
            log.info("令牌签名无效，返回未登录的信息: {}", e.getMessage());
            return sendErrorResponse(response, BizExceptionEnum.TOKEN_INVALID.getMsg());
        } catch (Exception e) {
            log.info("令牌解析失败，返回未登录的信息: {}", e.getMessage());
            return sendErrorResponse(response, BizExceptionEnum.PARSING_FAILED.getMsg());
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
