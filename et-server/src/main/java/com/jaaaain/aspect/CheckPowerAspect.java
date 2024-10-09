package com.jaaaain.aspect;

import com.jaaaain.annotation.CheckPower;
import com.jaaaain.context.BaseContext;
import com.jaaaain.entity.ChatSession;
import com.jaaaain.exception.BizException;
import com.jaaaain.exception.BizExceptionEnum;
import com.jaaaain.mapper.ChatSessionMapper;
import com.jaaaain.service.ChatSessionService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * AOP权限校验
 */
@Aspect
@Component
public class CheckPowerAspect {
    @Autowired
    private ChatSessionMapper chatSessionMapper;

    /**
     * 只有管理员和本人可访问个人信息
     * @param joinPoint
     * @param checkPower
     */
    @Before("@annotation(checkPower)")
    public void checkPower(final JoinPoint joinPoint, final CheckPower checkPower) {
        Long currentUserId = BaseContext.getCurrentId();
        Integer currentRole = BaseContext.getCurrentRole();
        if (currentRole!=1) {
            // 非管理员不能获取他人对话记录
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            String sessionId = methodSignature.getDeclaringType().getName();
            ChatSession chatSession = chatSessionMapper.queryById(sessionId);
            if (!chatSession.getUserId().equals(currentUserId)) { // 非管理员不能获取他人对话记录
                throw new BizException(BizExceptionEnum.NOT_THE_PERSON);
            }
        }

    }
}
