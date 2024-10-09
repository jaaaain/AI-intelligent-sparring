package com.jaaaain.exception;

import com.jaaaain.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

// 异常处理器
@Slf4j
@RestControllerAdvice // 捕获所有Controller抛出的异常 + ResponseBody返回JSON
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class) // 捕获所有这类异常
    public Result MethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("参数 异常信息：{}", e.getMessage());
        BindingResult result = e.getBindingResult();
        Map<String, String> errorMap = new HashMap<>();
        result.getFieldErrors().forEach(fieldError -> {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            errorMap.put(field, message);
        });
        return Result.error("参数不合法",errorMap);
    }

    /**
     * 捕获业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(BizException.class)
    public Result exceptionHandler(BizException e){
        log.error("业务 异常信息：{}", e.getMessage());
        return Result.error(e.getMessage());
    }

    /**
     * 处理SQL异常
     * @param e
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result exceptionHandler(SQLIntegrityConstraintViolationException e){
        log.error("数据库 异常信息：{}", e.getMessage());
        String message = e.getMessage();
        if(message.contains("Duplicate entry")){
            String[] split = message.split(" ");
            String username = split[2];
            String msg = username + BizExceptionEnum.ALREADY_EXISTS.getMsg();
            return Result.error(msg);
        }else{
            return Result.error(BizExceptionEnum.UNKNOWN_ERROR.getMsg());
        }
    }
}
