package com.jaaaain.intercepter;

import com.alibaba.fastjson.JSONObject;
import com.jaaaain.context.BaseContext;
import com.jaaaain.exception.BizExceptionEnum;
import com.jaaaain.result.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component // 定义拦截器
public class AdminCheckIntercepter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("管理员拦截器: preHandle");
        // 1. 获取请求路径
        String url = request.getRequestURL().toString();
        log.info("进入拦截器，请求的路径: {}", url);
        Integer isAdmin = BaseContext.getCurrentRole();
        if(isAdmin != 1){
            sendErrorResponse(response, BizExceptionEnum.NOT_ADMIN.getMsg());
        }
        return true;
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
        log.info("管理员拦截器: postHandle");
    }

    @Override // 视图渲染完毕后执行
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("管理员拦截器: afterCompletion");
    }

}
